package com.laa66.springmvc.lottery.app.dao;

import com.laa66.springmvc.lottery.app.config.SecurityConfig;
import com.laa66.springmvc.lottery.app.config.TestAppConfig;
import com.laa66.springmvc.lottery.app.entity.DrawResult;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestAppConfig.class, SecurityConfig.class})
@WebAppConfiguration
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class DrawResultDAOImplTest {

    @Autowired
    SessionFactory sessionFactory;

    @Autowired
    DrawResultDAO drawResultDAO;

    @BeforeEach
    void beforeTest() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        DrawResult drawResult1 = new DrawResult();
        drawResult1.draw();
        session.save(drawResult1);

        DrawResult drawResult2 = new DrawResult();
        drawResult2.draw();
        session.save(drawResult2);

        session.getTransaction().commit();
        session.close();
    }

    @Test
    @Transactional
    void testGetDrawResultsWhenDbIsNotEmpty() {
        List<DrawResult> list = drawResultDAO.getDrawResults();
        assertNotNull(list);
        assertEquals(2, list.size());
    }

    @Test
    @Transactional
    void testGetDrawResultsWhenDbIsEmpty() {
        List<DrawResult> list = drawResultDAO.getDrawResults();
        assertNotNull(list);
        for (DrawResult drawResult : list) drawResultDAO.deleteDrawResult(drawResult.getId());
        List<DrawResult> emptyList = drawResultDAO.getDrawResults();
        assertTrue(emptyList.isEmpty());
    }

    @Test
    @Transactional
    void testGetLastDrawResultWhenDbIsNotEmpty() {
        DrawResult drawResult = drawResultDAO.getLastDrawResult();
        assertNotNull(drawResult);
        assertNotEquals(0, drawResult.getId());
        assertNotNull(drawResult.getDate());
        assertNotNull(drawResult.getNumbers());
    }

    @Test
    @Transactional
    void testGetLastDrawResultWhenDbIsEmpty() {
        List<DrawResult> list = drawResultDAO.getDrawResults();
        assertNotNull(list);
        for (DrawResult drawResult: list) drawResultDAO.deleteDrawResult(drawResult.getId());
        assertNull(drawResultDAO.getLastDrawResult());
    }

    @Test
    @Transactional
    void testSaveDrawResult() {
        int sizeBeforeSave = drawResultDAO.getDrawResults().size();
        DrawResult drawResult = new DrawResult();
        drawResult.draw();
        drawResultDAO.saveDrawResult(drawResult);
        assertTrue(sizeBeforeSave < drawResultDAO.getDrawResults().size());
    }

    @Test
    @Transactional
    void testDeleteDrawResultIfIdExists() {
        List<DrawResult> list = drawResultDAO.getDrawResults();
        assertNotNull(list);
        int sizeBeforeDelete = list.size();
        DrawResult drawResult = list.get(0);
        drawResultDAO.deleteDrawResult(drawResult.getId());
        assertTrue(sizeBeforeDelete > drawResultDAO.getDrawResults().size());
    }

    @Test
    @Transactional
    void testDeleteDrawResultIfIdNotExists() {
        List<DrawResult> list = drawResultDAO.getDrawResults();
        assertNotNull(list);
        int sizeBeforeDelete = list.size();
        drawResultDAO.deleteDrawResult(5);
        assertEquals(sizeBeforeDelete, drawResultDAO.getDrawResults().size());
    }



}