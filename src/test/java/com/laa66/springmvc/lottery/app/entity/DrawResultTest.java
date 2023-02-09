package com.laa66.springmvc.lottery.app.entity;

import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class DrawResultTest {

    @Test
    void testDraw() {
        DrawResult drawResult = new DrawResult();
        drawResult.draw();
        assertNotNull(drawResult.getNumbers());
        assertNotNull(drawResult.getDate());
        assertEquals(6, drawResult.getNumbers().size());
    }
}