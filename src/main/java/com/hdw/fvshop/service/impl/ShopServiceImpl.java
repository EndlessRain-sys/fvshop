package com.hdw.fvshop.service.impl;

import com.hdw.fvshop.dao.ShopDao;
import com.hdw.fvshop.dto.ImageHolder;
import com.hdw.fvshop.dto.ShopExecution;
import com.hdw.fvshop.entity.Shop;
import com.hdw.fvshop.enums.ShopStateEnum;
import com.hdw.fvshop.exception.OperationException;
import com.hdw.fvshop.service.ShopService;
import com.hdw.fvshop.util.HttpServletRequestUtil;
import com.hdw.fvshop.util.ImageUtil;
import com.hdw.fvshop.util.PageCalculator;
import com.hdw.fvshop.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class ShopServiceImpl implements ShopService {
    @Autowired
    private ShopDao shopDao;
    @Override
    public ShopExecution getShopList(Shop shopCondition, int pageIndex, int pageSize) {
        int rowIndex = PageCalculator.calculateRowIndex(pageIndex, pageSize);
        List<Shop> shopList = shopDao.queryShopList(shopCondition, rowIndex, pageSize);
        int shopCount = shopDao.queryShopCount(shopCondition);
        ShopExecution se = new ShopExecution();
        if(shopList != null){
            se.setShopList(shopList);
            se.setCount(shopCount);
        }else {
            se.setState(ShopStateEnum.INNER_ERROR.getState());
        }
        return se;
    }

    @Override
    public Shop getShopById(long shopId) {
        return shopDao.queryByShopId(shopId);
    }

    @Override
    @Transactional
    public ShopExecution addShop(Shop shop, ImageHolder thumbnail) {
        if(shop != null){
            shop.setEnableStatus(0);
            shop.setCreateTime(new Date());
            shop.setLastEditTime(new Date());
            int effectedNum = shopDao.insertShop(shop);
            if(effectedNum != 0){
                if(thumbnail.getImage() != null){
                    // 存储图片
                    try{
                        addThumbnail(shop, thumbnail);
                    }catch (Exception e){
                        throw new OperationException("addShopImg error" + e.getMessage());
                    }
                    // 更新店铺的图片地址
                    /*effectedNum = shopDao.updateShop(shop);
                    if (effectedNum <= 0) {
                        throw new OperationException("更新图片地址失败");
                    }*/
                }
            }else{
                throw new OperationException("addShop error");
            }
        }else{
            return new ShopExecution(ShopStateEnum.NULL_SHOP);
        }
        return new ShopExecution(ShopStateEnum.CHECK, shop);
    }

    @Override
    @Transactional
    public ShopExecution modifyShop(Shop shop, ImageHolder thumbnail) {
        if(shop == null) {
            return new ShopExecution(ShopStateEnum.INNER_ERROR);
        } else {
            try {
                if (thumbnail.getImage() != null) {
                    String tempShopImg = shopDao.queryByShopId(shop.getShopId()).getShopImg();
                    if (tempShopImg != null ){
                        ImageUtil.deleteFileOrPath(tempShopImg);
                    }
                    addThumbnail(shop, thumbnail);
                }
                shop.setLastEditTime(new Date());
                int effectedNum = shopDao.updateShop(shop);
                if (effectedNum <= 0) {
                    return new ShopExecution(ShopStateEnum.INNER_ERROR);
                } else {
                    shop = shopDao.queryByShopId(shop.getShopId());
                    return new ShopExecution(ShopStateEnum.SUCCESS, shop);
                }
            } catch (Exception e) {
                throw new OperationException("modifyShop error:" + e.toString());
            }
        }
    }

    private void addThumbnail(Shop shop, ImageHolder thumbnail){
        String targetAddr = PathUtil.getShopImagePath(shop.getShopId());
        String shopImgAddr = ImageUtil.generateNormalImg(thumbnail, targetAddr);
        shop.setShopImg(shopImgAddr);
    }
}
