package com.hdw.fvshop.web.shopadmin;

import com.hdw.fvshop.dto.ProductCategoryExecution;
import com.hdw.fvshop.entity.ProductCategory;
import com.hdw.fvshop.entity.Shop;
import com.hdw.fvshop.enums.ProductCategoryStateEnum;
import com.hdw.fvshop.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/shopadmin")
public class ProductManagementController {
    @Autowired
    private ProductCategoryService productCategoryService;
    @RequestMapping(value = "/getproductcategorylist", method = RequestMethod.GET)
    @ResponseBody
    Map<String, Object> getProductCategoryList(HttpServletRequest request){
        Map<String, Object> modelMap = new HashMap<String, Object>();
        Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
        long shopId = currentShop.getShopId();
        List<ProductCategory> productCategoryList = null;
        if (currentShop != null) {
            productCategoryList = productCategoryService.getProductCategoryList(shopId);
            modelMap.put("success", true);
            modelMap.put("productCategoryList", productCategoryList);
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", ProductCategoryStateEnum.INNER_ERROR.getStateInfo());
        }
        return modelMap;
    }

    @RequestMapping(value = "/addproductcategorys", method = RequestMethod.POST)
    @ResponseBody
    Map<String, Object> addProductCategorys(@RequestBody List<ProductCategory> productCategoryList, HttpServletRequest request){
        Map<String, Object> modelMap = new HashMap<String, Object>();
        Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
        for (int i = 0; i < productCategoryList.size(); i++) {
            productCategoryList.get(i).setShopId(currentShop.getShopId());
        }
        if (productCategoryList != null){
            ProductCategoryExecution productCategoryExecution = productCategoryService.batchAddProductCategory(productCategoryList);
            if (productCategoryExecution.getState() == ProductCategoryStateEnum.SUCCESS.getState()) {
                modelMap.put("success", true);
            } else {
                modelMap.put("success", false);
                modelMap.put("errMsg", productCategoryExecution.getStateInfo());
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请输入至少一个类别");
        }
        return modelMap;
    }

    @RequestMapping(value = "/removeproductcategory", method = RequestMethod.POST)
    @ResponseBody
    Map<String, Object> removeProductCategory(Long productCategoryId, HttpServletRequest request){
        Map<String, Object> modelMap = new HashMap<String, Object>();
        if (productCategoryId != null && productCategoryId >= 0){
            Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
            ProductCategoryExecution productCategoryExecution = productCategoryService.deleteProductCategory(productCategoryId, currentShop.getShopId());
            if (productCategoryExecution.getState() == ProductCategoryStateEnum.SUCCESS.getState()) {
                modelMap.put("success", true);
            } else {
                modelMap.put("success", false);
                modelMap.put("errMsg", productCategoryExecution.getStateInfo());
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "productCategoryId == null");
        }
        return modelMap;
    }
}
