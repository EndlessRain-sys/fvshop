package com.hdw.fvshop.service.impl;

import com.hdw.fvshop.dao.AreaDao;
import com.hdw.fvshop.entity.Area;
import com.hdw.fvshop.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AreaServiceImpl implements AreaService {
    @Autowired
    private AreaDao areaDao;
    @Override
    public List<Area> getAreaList() {
        return areaDao.queryArea();
    }

}
