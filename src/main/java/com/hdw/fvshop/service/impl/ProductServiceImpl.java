package com.hdw.fvshop.service.impl;

import com.hdw.fvshop.dao.ProductDao;
import com.hdw.fvshop.dao.ProductImgDao;
import com.hdw.fvshop.dto.ImageHolder;
import com.hdw.fvshop.dto.ProductExecution;
import com.hdw.fvshop.entity.Product;
import com.hdw.fvshop.entity.ProductImg;
import com.hdw.fvshop.enums.ProductStateEnum;
import com.hdw.fvshop.exception.OperationException;
import com.hdw.fvshop.service.ProductService;
import com.hdw.fvshop.util.ImageUtil;
import com.hdw.fvshop.util.PageCalculator;
import com.hdw.fvshop.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductDao productDao;
    @Autowired
    private ProductImgDao productImgDao;

    @Override
    public ProductExecution getProductList(Product productCondition, int pageIndex, int pageSize) {
        int rowIndex = PageCalculator.calculateRowIndex(pageIndex, pageSize);
        List<Product> productList = productDao.queryProductList(productCondition, rowIndex, pageSize);
        int productCount = productDao.queryProductCount(productCondition);
        ProductExecution productExecution = new ProductExecution();
        productExecution.setProductList(productList);
        productExecution.setCount(productCount);
        return productExecution;
    }

    @Override
    public Product getProductById(long productId) {
        return productDao.queryProductById(productId);
    }

    @Override
    public ProductExecution addProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgList) {
        if (product != null && product.getShop() != null) {
            product.setCreateTime(new Date());
            product.setLastEditTime(new Date());
            product.setEnableStatus(1);
            if (thumbnail != null) {
                addThumbnail(product, thumbnail);
            }
            if (productImgList != null && productImgList.size() > 0) {
                addProductImgList(product, productImgList);
            }
            try{
                int effectedNum = productDao.insertProduct(product);
                if (effectedNum <= 0){
                    throw new OperationException("创建商品失败");
                }
            } catch (Exception e){
                throw new OperationException("创建商品失败" + e.toString());
            }
            return new ProductExecution(ProductStateEnum.SUCCESS, product);
        } else {
            return new ProductExecution(ProductStateEnum.EMPTY);
        }
    }

    @Override
    public ProductExecution modifyProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgList) {
        if (product != null && product.getShop() != null) {
            product.setLastEditTime(new Date());
            if (thumbnail != null) {
                Product tempProduct = productDao.queryProductById(product.getProductId());
                ImageUtil.deleteFileOrPath(tempProduct.getThumbnailAddr());
                addThumbnail(product, thumbnail);
            }
            if (productImgList != null && productImgList.size() > 0) {
                deleteProductImgList(product.getProductId());
                addProductImgList(product, productImgList);
            }
            try {
                int effectedNum = productDao.updateProduct(product);
                if (effectedNum <= 0) {
                    throw new OperationException("创建商品失败");
                }
            } catch (Exception e) {
                throw new OperationException("创建商品失败" + e.toString());
            }
            return new ProductExecution(ProductStateEnum.SUCCESS, product);
        } else {
            return new ProductExecution(ProductStateEnum.EMPTY);
        }
    }

    private void addThumbnail(Product product, ImageHolder thumbnail){
        String dest = PathUtil.getShopImagePath(product.getShop().getShopId());
        String thumbnailAddr = ImageUtil.generateThumbnail(thumbnail, dest);
        product.setThumbnailAddr(thumbnailAddr);
    }

    private void addProductImgList(Product product, List<ImageHolder> productImgHolderList){
        String dest = PathUtil.getShopImagePath(product.getShop().getShopId());
        List<ProductImg> productImgList = new ArrayList<ProductImg>();
        for (ImageHolder productImgHolder : productImgHolderList) {
            String imgAddr = ImageUtil.generateNormalImg(productImgHolder, dest);
            ProductImg productImg = new ProductImg();
            productImg.setCreateTime(new Date());
            productImg.setImgAddr(imgAddr);
            productImg.setProductId(product.getProductId());
            productImgList.add(productImg);
        }
        if (productImgList.size() > 0 && productImgList != null) {
            try {
                int effectedNum = productImgDao.batchInsertProductImg(productImgList);
                if (effectedNum <= 0) {
                    throw new OperationException("商品详情图片添加失败");
                }
            } catch (Exception e) {
                throw new OperationException("商品详情图片添加失败" + e.toString());
            }
        }
     }

     private void deleteProductImgList(long productId){
         List<ProductImg> productImgList = productImgDao.queryProductImgList(productId);
         for (int i = 0; i < productImgList.size(); i++) {
             ImageUtil.deleteFileOrPath(productImgList.get(i).getImgAddr());
         }
         productImgDao.deleteProductImgByProductId(productId);
     }
}
