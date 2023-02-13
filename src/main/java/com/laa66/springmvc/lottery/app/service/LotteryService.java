package com.laa66.springmvc.lottery.app.service;

import com.laa66.springmvc.lottery.app.entity.DrawResult;

import java.util.List;

/**
 *  Support for CRUD Database operations and also drawAndSave
 *  function for generating DrawResult object which
 *  should be immediately saved in Database
 *  and drawOneTimeADay function which generates
 *  one drawResult object a day
 *
 */
public interface LotteryService {

    List<DrawResult> getDrawResults();

    DrawResult getLastDrawResult();

    void deleteDrawResult(int id);

    void drawAndSave();

    void drawOneTimeADay();

}
