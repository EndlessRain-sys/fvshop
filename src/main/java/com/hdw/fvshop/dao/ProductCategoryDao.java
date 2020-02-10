package com.hdw.fvshop.dao;

import com.hdw.fvshop.entity.ProductCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductCategoryDao {
    List<ProductCategory> queryProductCategoryList(long shopId);
    int batchInsertProductCategory(List<ProductCategory> productCategoryList);
    int deleteProductCategory(@Param("productCategoryId") long productCategoryId, @Param("shopId") long shopId);
}
