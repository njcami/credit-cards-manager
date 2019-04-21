package com.manager.cards.service;

import com.manager.cards.dao.CardDao;
import com.manager.cards.dao.CardUserDao;
import com.manager.cards.model.Card;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * Service class for Cards
 */
@Service
public class CardServiceImpl implements CardService {

    @Autowired
    private CardDao cardDao;

    @Autowired
    private CardUserDao cardUserDao;

    @Transactional
    public void save(Card card, String loggedInUser) {
        if (card != null) {
            card.setEnabled(true);
            card.setUserId(cardUserDao.getLoggedInUserId(loggedInUser));
            card.setCreated(new Timestamp(new Date().getTime()));
            cardDao.save(card);
        }
    }

    @Transactional
    public void update(Card card, String loggedInUser) {
        if (card != null) {
            card.setEnabled(true);
            Long cardId = card.getId();
            if (cardId != null && cardId > 0) {
                card.setLastUpdated(new Timestamp(new Date().getTime()));
                if (cardUserDao.isLoggedInUserAdmin(loggedInUser)) {
                    if (cardDao.find(cardId) != null) {
                        cardDao.update(card);
                    }
                } else {
                    List<Long> userCardIds = cardDao.getLoggedInUserCardIds(loggedInUser);
                    if (!CollectionUtils.isEmpty(userCardIds) && userCardIds.contains(cardId)) {
                        cardDao.update(card);
                    }
                }
            }
        }
    }

    @Transactional(readOnly = true)
    public Card find(Long cardId, String loggedInUser) {
        List<Card> cards = listCardsForLoggedInUser(loggedInUser);
        if (!CollectionUtils.isEmpty(cards)) {
            return cards.stream()
                    .filter(c -> c.getId().equals(cardId))
                    .findFirst().orElse(null);
        }
        return null;
    }

    @Transactional
    public void delete(Long cardId, String loggedInUser) {
        if (cardId != null && !StringUtils.isEmpty(loggedInUser)) {
            List<Long> userCardIds = cardDao.getLoggedInUserCardIds(loggedInUser);
            if ((!CollectionUtils.isEmpty(userCardIds) && userCardIds.contains(cardId)) ||
                    cardUserDao.isLoggedInUserAdmin(loggedInUser)) {
                cardDao.delete(cardId);
            }
        }
    }

    @Transactional(readOnly = true)
    public List<Card> listCardsForLoggedInUser(String loggedInUser) {
        if (cardUserDao.isLoggedInUserAdmin(loggedInUser)) {
            return cardDao.list();
        }
        return cardDao.getLoggedInUserCards(loggedInUser);
    }

    @Transactional(readOnly = true)
    public List<Card> list() {
        return cardDao.list();
    }
}
