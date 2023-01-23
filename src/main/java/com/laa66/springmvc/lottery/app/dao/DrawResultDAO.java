package com.laa66.springmvc.lottery.app.dao;

import com.laa66.springmvc.lottery.app.entity.DrawResult;

import java.util.List;

public interface DrawResultDAO {
    void saveDrawResult(DrawResult drawResult);
    void deleteDrawResult(int id);
    List<DrawResult> getDrawResults();
    DrawResult getLastDrawResult();
}
