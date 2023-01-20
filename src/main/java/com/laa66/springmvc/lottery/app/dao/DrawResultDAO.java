package com.laa66.springmvc.lottery.app.dao;

import com.laa66.springmvc.lottery.app.entity.DrawResult;

import java.time.LocalDateTime;
import java.util.List;

public interface DrawResultDAO {
    void saveDrawResult(DrawResult drawResult);
    void deleteDrawResults();
    List<DrawResult> getDrawResults();
    DrawResult getLastDrawResult();
}
