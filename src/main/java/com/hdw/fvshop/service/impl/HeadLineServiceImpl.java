package com.hdw.fvshop.service.impl;

import com.hdw.fvshop.dao.HeadLineDao;
import com.hdw.fvshop.entity.HeadLine;
import com.hdw.fvshop.service.HeadLineService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class HeadLineServiceImpl implements HeadLineService {
    @Autowired
    private HeadLineDao headLineDao;

    @Override
    public List<HeadLine> getHeadLineList(HeadLine headLineCondition) {
        return headLineDao.queryHeadLine(headLineCondition);
    }
}
