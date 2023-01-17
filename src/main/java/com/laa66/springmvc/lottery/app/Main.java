package com.laa66.springmvc.lottery.app;

import com.laa66.springmvc.lottery.app.service.LotteryService;
import com.laa66.springmvc.lottery.app.service.LotteryServiceImpl;
public class Main {
    public static void main(String[] args) {
        LotteryService service = new LotteryServiceImpl();
        service.drawAndSave();
    }
}
