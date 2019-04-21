package com.manager.cards.dao;

import com.manager.cards.model.Card;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Repository Class for Cards
 */
@Transactional
@Repository
public class CardDaoImpl implements CardDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void save(Card card) {
        if (card != null) {
            sessionFactory.getCurrentSession().save(card);
        }
    }

    @Override
    public void update(Card card) {
        if (card != null) {
            TypedQuery<Card> query = sessionFactory.getCurrentSession()
                    .createQuery("UPDATE Card c SET c.number = ?1, c.expiryMonth = ?2, c.expiryYear = ?3," +
                            "c.lastUpdated = ?4 WHERE c.id = ?5");
            query.setParameter(1, card.getNumber());
            query.setParameter(2, card.getExpiryMonth());
            query.setParameter(3, card.getExpiryYear());
            query.setParameter(4, new Timestamp(new Date().getTime()));
            query.setParameter(5, card.getId());
            query.executeUpdate();
        }
    }

    @Override
    public Card find(Long id) {
        TypedQuery<Card> query = sessionFactory.getCurrentSession().createQuery("SELECT c FROM Card c WHERE c.id = ?1");
        query.setParameter(1, id);
        return query.getSingleResult();
    }

    @Override
    public void delete(Long id) {
        TypedQuery<Card> query = sessionFactory.getCurrentSession().createQuery("SELECT c FROM Card c WHERE c.id = ?1");
        query.setParameter(1, id);
        Card card = query.getSingleResult();
        card.setEnabled(false);
        card.setLastUpdated(new Timestamp(new Date().getTime()));
        sessionFactory.getCurrentSession().update(card);
    }

    @Override
    public List<Long> getLoggedInUserCardIds(String loggedInUser) {
        return getLoggedInUserCards(loggedInUser)
                .stream()
                .map(Card::getId)
                .collect(Collectors.toList());
    }

    @Override
    public List<Card> getLoggedInUserCards(String loggedInUser) {
        TypedQuery<Card> query = sessionFactory.getCurrentSession()
                .createQuery("SELECT c FROM Card c JOIN CardUser u ON u.id = c.userId AND u.username = ?1");
        query.setParameter(1, loggedInUser);
        return query.getResultList();
    }

    @Override
    public List<Card> list() {
        TypedQuery<Card> query = sessionFactory.getCurrentSession().createQuery("FROM Card");
        return query.getResultList();
    }
}
