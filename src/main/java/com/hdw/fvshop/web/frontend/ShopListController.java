package com.hdw.fvshop.web.frontend;

import com.hdw.fvshop.dto.ShopExecution;
import com.hdw.fvshop.entity.Area;
import com.hdw.fvshop.entity.Shop;
import com.hdw.fvshop.entity.ShopCategory;
import com.hdw.fvshop.service.AreaService;
import com.hdw.fvshop.service.ShopCategoryService;
import com.hdw.fvshop.service.ShopService;
import com.hdw.fvshop.util.HttpServletRequestUtil;
import org.omg.CORBA.OBJ_ADAPTER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/frontend")
public class ShopListController {
    @Autowired
    private ShopService shopService;
    @Autowired
    private ShopCategoryService shopCategoryService;
    @Autowired
    private AreaService areaService;

    @RequestMapping(value = "listshopspageinfo", method = RequestMethod.GET)
    @ResponseBody
    Map<String, Object> listShopPageInfo(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        long parentId = HttpServletRequestUtil.getLong(request, "parentId");
        List<ShopCategory> shopCategoryList = new ArrayList<ShopCategory>();
        if (parentId != -1) {
            try {
                ShopCategory shopCategoryCondition = new ShopCategory();
                ShopCategory parent = new ShopCategory();
                parent.setShopCategoryId(parentId);
                shopCategoryCondition.setParent(parent);
                shopCategoryList = shopCategoryService.getShopCategoryList(shopCategoryCondition);
            } catch (Exception e) {
                modelMap.put("success" ,false);
                modelMap.put("errMsg", e.toString());
                return modelMap;
            }
        } else {
            try {
                shopCategoryList = shopCategoryService.getShopCategoryList(null);
            } catch (Exception e) {
                modelMap.put("success" ,false);
                modelMap.put("errMsg", e.toString());
                return modelMap;
            }
        }
        modelMap.put("shopCategoryList" ,shopCategoryList);
        List<Area> areaList = new ArrayList<Area>();
        try {
            areaList = areaService.getAreaList();
            modelMap.put("success", true);
            modelMap.put("areaList", areaList);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }
        return modelMap;
    }

    @RequestMapping(value = "listshops", method = RequestMethod.GET)
    @ResponseBody
    Map<String, Object> listShops(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
        int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
        if (pageIndex > 0 && pageSize > 0) {
            long parentId = HttpServletRequestUtil.getLong(request, "parentId");
            long shopCategoryId = HttpServletRequestUtil.getLong(request, "shopCategoryList");
            int areaId = HttpServletRequestUtil.getInt(request, "areaId");
            String shopName = HttpServletRequestUtil.getString(request, "shopName");
            Shop shopCondition = new Shop();
            try {
                if (parentId != -1L) {
                    ShopCategory parentCategory = new ShopCategory();
                    ShopCategory childCategory = new ShopCategory();
                    parentCategory.setShopCategoryId(parentId);
                    childCategory.setParent(parentCategory);
                    shopCondition.setShopCategory(childCategory);
                }
                if (shopCategoryId != -1L) {
                    ShopCategory shopCategory = new ShopCategory();
                    shopCategory.setShopCategoryId(shopCategoryId);
                    shopCondition.setShopCategory(shopCategory);
                }
                if (areaId != -1) {
                    Area area = new Area();
                    area.setAreaId(areaId);
                    shopCondition.setArea(area);
                }
                if (shopName != null) {
                    shopCondition.setShopName(shopName);
                }
                shopCondition.setEnableStatus(1);
                ShopExecution shopExecution = shopService.getShopList(shopCondition, pageIndex, pageSize);
                modelMap.put("success", true);
                modelMap.put("shopList", shopExecution.getShopList());
                modelMap.put("count", shopExecution.getCount());
            } catch (Exception e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.toString());
                return modelMap;
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "pageSize 或 pageIndex 为空");
        }
        return modelMap;
    }
}
