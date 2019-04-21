package com.manager.cards.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.manager.cards.dao.CardUserDao;
import com.manager.cards.model.CardUser;

/**
 * Service class for Card users
 */
@Service
public class CardUserServiceImpl implements CardUserService {

   @Autowired
   private CardUserDao cardUserDao;

   @Autowired
   private PasswordEncoder passwordEncoder;

   @Transactional
   public void save(CardUser cardUser) {
      cardUser.setPassword(passwordEncoder.encode(cardUser.getPassword()));
      cardUserDao.save(cardUser);
   }

   @Transactional(readOnly = true)
   public List<CardUser> list() {
      return cardUserDao.list();
   }
}
