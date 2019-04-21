package com.manager.cards.service;

import com.manager.cards.model.Card;

import java.util.List;

/**
 * Interface for the Card Service
 */
public interface CardService {
   void save(Card card, String loggedInUser);

   void update(Card card, String loggedInUser);

   Card find(Long cardId, String loggedInUser);

   void delete(Long cardId, String loggedInUser);

   List<Card> listCardsForLoggedInUser(String loggedInUser);

   List<Card> list();
}
