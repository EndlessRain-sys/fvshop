package com.hdw.fvshop.service;

import com.hdw.fvshop.dto.ImageHolder;
import com.hdw.fvshop.dto.ShopExecution;
import com.hdw.fvshop.entity.Shop;

public interface ShopService {
    public ShopExecution getShopList(Shop shopCondition, int pageIndex, int pageSize);
    Shop getShopById(long shopId);
    ShopExecution addShop(Shop shop, ImageHolder thumbnail);
    ShopExecution modifyShop(Shop shop, ImageHolder thumbnail);
}
