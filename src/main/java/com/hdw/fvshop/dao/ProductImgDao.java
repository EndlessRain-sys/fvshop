package com.hdw.fvshop.dao;

import com.hdw.fvshop.entity.ProductImg;

import java.util.List;

public interface ProductImgDao {
    List<ProductImg> queryProductImgList(long productId);
    int batchInsertProductImg(List<ProductImg> productImgList);
    int deleteProductImgByProductId(long productId);
}
