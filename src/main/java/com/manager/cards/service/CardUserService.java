package com.manager.cards.service;

import java.util.List;

import com.manager.cards.model.CardUser;

/**
 * Interface for the Card User Service
 */
public interface CardUserService {
   void save(CardUser cardUser);

   List<CardUser> list();
}
