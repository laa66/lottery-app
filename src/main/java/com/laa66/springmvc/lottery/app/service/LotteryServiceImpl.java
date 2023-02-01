package com.laa66.springmvc.lottery.app.service;

import com.laa66.springmvc.lottery.app.dao.DrawResultDAO;
import com.laa66.springmvc.lottery.app.entity.DrawResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class LotteryServiceImpl implements LotteryService {

    @Autowired
    private DrawResultDAO drawResultDAO;

    @Override
    @Transactional
    public List<DrawResult> getDrawResults() {
        List<DrawResult> list = drawResultDAO.getDrawResults();
        mapDatesToString(list);
        return list;
    }

    @Override
    @Transactional
    public DrawResult getLastDrawResult() {
        return drawResultDAO.getLastDrawResult();
    }

    // support delete operation but it's not used in application
    @Override
    @Transactional
    public void deleteDrawResult(int id) {
        drawResultDAO.deleteDrawResult(id);
    }

    @Override
    public void drawAndSave() {
        DrawResult drawResult = new DrawResult();
        drawResult.draw();
        drawResultDAO.saveDrawResult(drawResult);
    }

    // this method is scheduled to 10 PM every day to draw new lottery results and save them to DB
    @Scheduled(cron = "0 0 22 * * *")
    @Transactional
    @Override
    public void drawOneTimeADay() {
        drawAndSave();
    }

    // helpers
    public void mapDatesToString(List<DrawResult> list) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        for (DrawResult drawResult: list) drawResult.setDateString(formatter.format(drawResult.getDate()));
    }
}
