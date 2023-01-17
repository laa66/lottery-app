package com.laa66.springmvc.lottery.app.service;

import com.laa66.springmvc.lottery.app.entity.DrawResult;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
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

        // TODO: 17.01.2023 Add database saving support
    }
}
