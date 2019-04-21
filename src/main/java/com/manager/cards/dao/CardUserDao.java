package com.manager.cards.dao;

import java.util.List;

import com.manager.cards.model.CardUser;
import org.springframework.security.core.userdetails.User;

/**
 * Interface for Card Users Data Access Object
 */
public interface CardUserDao {

   void save(CardUser cardUser);

   List<CardUser> list();

   Long getLoggedInUserId(String loggedInUser);

   boolean isLoggedInUserAdmin(String loggedInUser);

   User findByUsername(String username);
}
