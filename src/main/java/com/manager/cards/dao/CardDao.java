package com.manager.cards.dao;

import com.manager.cards.model.Card;

import java.util.List;

/**
 * Interface for Card Data Access Object
 */
public interface CardDao {
   void save(Card card);

   void update(Card card);

   Card find(Long id);

   void delete(Long id);

   List<Long> getLoggedInUserCardIds(String loggedInUser);

   List<Card> getLoggedInUserCards(String loggedInUser);

   List<Card> list();
}
