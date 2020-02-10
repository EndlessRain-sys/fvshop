package com.hdw.fvshop.service;

import com.hdw.fvshop.dto.ProductCategoryExecution;
import com.hdw.fvshop.entity.ProductCategory;

import java.util.List;

public interface ProductCategoryService {
    List<ProductCategory> getProductCategoryList(long shopId);
    ProductCategoryExecution batchAddProductCategory(List<ProductCategory> productCategoryList);
    ProductCategoryExecution deleteProductCategory(long productCategoryId, long shopId);
}
