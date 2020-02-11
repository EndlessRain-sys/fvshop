package com.hdw.fvshop.service;

import com.hdw.fvshop.dto.ImageHolder;
import com.hdw.fvshop.dto.ProductExecution;
import com.hdw.fvshop.entity.Product;
import org.springframework.stereotype.Service;

import java.util.List;


public interface ProductService {
    ProductExecution getProductList(Product productCondition, int pageIndex, int pageSize);
    Product getProductById(long productId);
    ProductExecution addProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgList);
    ProductExecution modifyProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgList);
}
