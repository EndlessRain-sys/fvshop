package com.hdw.fvshop.dao;

import com.hdw.fvshop.entity.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductDao {
    List<Product> queryProductList(@Param("productCondition") Product productCondition, @Param("rowIndex") int rowIndex,
                                   @Param("rowPage") int rowPage);
    int queryProductCount(Product productCondition);
    Product queryProductById(long productId);
    int insertProduct(Product product);
    int updateProduct(Product product);
    int updateProductCategoryToNull(long productCategoryId);
    int deleteProduct(@Param("productId") long productId, @Param("shopId") long shopId);
}
