package com.hdw.fvshop.dao;

import com.hdw.fvshop.entity.Shop;
import org.apache.ibatis.annotations.Param;

import java.util.List;

    /**
     * 分页查询店铺
     */

public interface ShopDao {
    List<Shop> queryShopList(@Param("shopCondition") Shop shopCondition, @Param("rowIndex") int rowIndex,
                             @Param("pageSize") int pageSize);

    /**
     * 返回queryShopList总数
     *
      * @param shopCondition
     * @return
     */
    int queryShopCount(@Param("shopCondition") Shop shopCondition);

    /**
     * 通过shopId查询店铺
     *
     * @param shopId
     * @return
     */
    Shop queryByShopId(@Param("shopId") long shopId);

    int insertShop(Shop shop);

    int updateShop(Shop shop);
}
