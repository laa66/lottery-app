package com.laa66.springmvc.lottery.app.task;

import com.laa66.springmvc.lottery.app.service.LotteryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class DrawTask {

    @Autowired
    private LotteryService lotteryService;

    @Scheduled(cron = "${app.schedule.cron}")
    public void launchTask() {
        lotteryService.drawAndSave();
    }

}
