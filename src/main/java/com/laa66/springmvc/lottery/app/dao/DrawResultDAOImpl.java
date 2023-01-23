package com.laa66.springmvc.lottery.app.dao;

import com.laa66.springmvc.lottery.app.entity.DrawResult;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

// TODO: 20.01.2023 TEST REPO
@Repository
public class DrawResultDAOImpl implements DrawResultDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void saveDrawResult(DrawResult drawResult) {
        Session session = sessionFactory.getCurrentSession();
        session.save(drawResult);
    }

    @Override
    public void deleteDrawResult(int id) {
        Session session = sessionFactory.getCurrentSession();
        DrawResult drawResult = session.get(DrawResult.class, id);
        try {
            session.remove(drawResult);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<DrawResult> getDrawResults() {
        Session session = sessionFactory.getCurrentSession();
        Query<DrawResult> query = session.createQuery("FROM DrawResult ORDER BY date DESC", DrawResult.class);
        List<DrawResult> list;
        try {
            list = query.getResultList();
        } catch (Exception e) {
            list = null;
        }
        return list;
    }

    @Override
    public DrawResult getLastDrawResult() {
        Session session = sessionFactory.getCurrentSession();
        Query<DrawResult> query = session.createQuery("FROM DrawResult ORDER BY date DESC", DrawResult.class);
        query.setMaxResults(1);
        DrawResult drawResult;
        try {
            drawResult = query.getSingleResult();
        } catch (Exception e) {
            drawResult = null;
        }
        return drawResult;
    }
}
