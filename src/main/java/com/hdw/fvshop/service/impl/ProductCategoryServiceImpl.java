package com.hdw.fvshop.service.impl;

import com.hdw.fvshop.dao.ProductCategoryDao;
import com.hdw.fvshop.dto.ProductCategoryExecution;
import com.hdw.fvshop.entity.ProductCategory;
import com.hdw.fvshop.enums.ProductCategoryStateEnum;
import com.hdw.fvshop.exception.OperationException;
import com.hdw.fvshop.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {
    @Autowired
    private ProductCategoryDao productCategoryDao;

    @Override
    public List<ProductCategory> getProductCategoryList(long shopId) {
        return productCategoryDao.queryProductCategoryList(shopId);
    }

    @Override
    @Transactional
    public ProductCategoryExecution batchAddProductCategory(List<ProductCategory> productCategoryList) {
        if (productCategoryList != null){
            int effectedNum = productCategoryDao.batchInsertProductCategory(productCategoryList);
            if (effectedNum > 0){
                return new ProductCategoryExecution(ProductCategoryStateEnum.SUCCESS);
            } else {
                throw new OperationException("商品类别添加失败");
            }
        } else return new ProductCategoryExecution(ProductCategoryStateEnum.EMPTY_LIST);
    }

    @Override
    @Transactional
    public ProductCategoryExecution deleteProductCategory(long productCategoryId, long shopId) {
        int effectedNum = productCategoryDao.deleteProductCategory(productCategoryId, shopId);
        if (effectedNum > 0){
            return new ProductCategoryExecution(ProductCategoryStateEnum.SUCCESS);
        } else {
            throw new OperationException("商品类别删除失败");
        }
    }
}
