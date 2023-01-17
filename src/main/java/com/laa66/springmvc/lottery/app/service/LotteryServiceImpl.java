package com.laa66.springmvc.lottery.app.service;

import com.laa66.springmvc.lottery.app.entity.DrawResult;

import java.util.List;

public class LotteryServiceImpl implements LotteryService {

    public List<DrawResult> getDrawResults() {
        return null;
    }

    public DrawResult getLastDrawResult() {
        return null;
    }

    public void deleteDrawResult() {

    }

    public void drawAndSave() {
        DrawResult drawResult = new DrawResult();
        drawResult.draw();
    }
}
