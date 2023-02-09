package com.laa66.springmvc.lottery.app.service;

import com.laa66.springmvc.lottery.app.config.SecurityConfig;
import com.laa66.springmvc.lottery.app.config.TestAppConfig;
import com.laa66.springmvc.lottery.app.dao.DrawResultDAO;
import com.laa66.springmvc.lottery.app.entity.DrawResult;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestAppConfig.class, SecurityConfig.class})
@WebAppConfiguration
class LotteryServiceImplTest {

    @Mock
    DrawResultDAO drawResultDAOMock;

    @InjectMocks
    LotteryServiceImpl lotteryService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetDrawResults() {
        DrawResult drawResult1 = new DrawResult();
        drawResult1.draw();
        DrawResult drawResult2 = new DrawResult();
        drawResult2.draw();
        when(drawResultDAOMock.getDrawResults()).thenReturn(List.of(drawResult1, drawResult2));

        List<DrawResult> list = lotteryService.getDrawResults();
        assertNotNull(list);
        assertEquals(2, list.size());
        verify(drawResultDAOMock, times(1)).getDrawResults();
    }

    @Test
    void testGetLastDrawResult() {
        DrawResult drawResult = new DrawResult();
        drawResult.draw();
        when(drawResultDAOMock.getLastDrawResult()).thenReturn(drawResult);
        DrawResult loadedDrawResult = lotteryService.getLastDrawResult();
        assertNotNull(loadedDrawResult);
        assertEquals(drawResult.getId(), loadedDrawResult.getId());
        assertEquals(drawResult.getDate(), loadedDrawResult.getDate());
        verify(drawResultDAOMock, times(1)).getLastDrawResult();
    }

    @Test
    void testDeleteDrawResult() {
        doNothing().when(drawResultDAOMock).deleteDrawResult(1);
        lotteryService.deleteDrawResult(1);
        verify(drawResultDAOMock, times(1)).deleteDrawResult(1);
    }

    @Autowired
    LotteryService lotteryServiceTest;

    @Test
    void testDrawOneTimeADay() {
        await().atMost(5, TimeUnit.SECONDS)
                .until(() ->  lotteryServiceTest.getDrawResults().size() > 0);
    }






}