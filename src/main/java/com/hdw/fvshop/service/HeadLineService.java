package com.hdw.fvshop.service;

import com.hdw.fvshop.entity.HeadLine;

import java.util.List;

public interface HeadLineService {
    List<HeadLine> getHeadLineList(HeadLine headLineCondition);
}
