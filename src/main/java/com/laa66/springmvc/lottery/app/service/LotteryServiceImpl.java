package com.laa66.springmvc.lottery.app.service;

import com.laa66.springmvc.lottery.app.dao.DrawResultDAO;
import com.laa66.springmvc.lottery.app.entity.DrawResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LotteryServiceImpl implements LotteryService {

    @Autowired
    private DrawResultDAO drawResultDAO;

    @Override
    @Transactional
    public List<DrawResult> getDrawResults() {
        return drawResultDAO.getDrawResults();
    }

    @Override
    @Transactional
    public DrawResult getLastDrawResult() {
        return drawResultDAO.getLastDrawResult();
    }

    @Override
    @Transactional
    public void deleteDrawResult(int id) {
        drawResultDAO.deleteDrawResult(id);
    }

    @Override
    @Transactional
    public void drawAndSave() {
        DrawResult drawResult = new DrawResult();
        drawResult.draw();
        drawResultDAO.saveDrawResult(drawResult);
    }
}
