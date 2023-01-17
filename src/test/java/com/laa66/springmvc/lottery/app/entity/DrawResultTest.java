package com.laa66.springmvc.lottery.app.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

// test drawResult instance

class DrawResultTest {

    @Test
    void shouldDrawSixNumbers() {

        DrawResult drawResult = new DrawResult();

        drawResult.draw();

        Assertions.assertEquals(6, drawResult.getNumbers().size());
    }

    @Test
    void shouldMaxNumberBe100() {
        DrawResult drawResult = new DrawResult();
        drawResult.draw();

        for (Integer integer: drawResult.getNumbers()) Assertions.assertTrue(integer < 100);
    }


}