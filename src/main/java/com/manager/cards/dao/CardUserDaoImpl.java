package com.manager.cards.dao;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import com.manager.cards.config.SecurityConfig;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import com.manager.cards.model.CardUser;
import org.springframework.util.CollectionUtils;

/**
 * Repository class for the Card Users
 */
@Transactional
@Repository
public class CardUserDaoImpl implements CardUserDao {

   @Autowired
   private SessionFactory sessionFactory;

   @Override
   public void save(CardUser cardUser) {
      if (cardUser != null && cardUser.getUsername() != null) {
         cardUser.setCreated(new Timestamp(new Date().getTime()));
         if (cardUser.getUsername().equals(SecurityConfig.ADMIN_USERNAME)) {
            cardUser.setIsAdmin(true);
         } else {
            cardUser.setIsAdmin(false);
         }
         sessionFactory.getCurrentSession().save(cardUser);
      }
   }

   @Override
   public List<CardUser> list() {
      TypedQuery<CardUser> query = sessionFactory.getCurrentSession().createQuery("FROM CardUser");
      return query.getResultList();
   }

   @Override
   public Long getLoggedInUserId(String loggedInUser) {
      TypedQuery<Long> query = sessionFactory.getCurrentSession()
              .createQuery("SELECT u.id FROM CardUser u WHERE u.username = ?1");
      query.setParameter(1, loggedInUser);
      return query.getSingleResult();
   }

   @Override
   public boolean isLoggedInUserAdmin(String loggedInUser) {
      TypedQuery<Boolean> query = sessionFactory.getCurrentSession()
              .createQuery("SELECT u.isAdmin FROM CardUser u WHERE u.username = ?1");
      query.setParameter(1, loggedInUser);
      return query.getSingleResult();
   }

   @Override
   public User findByUsername(String username) {
      checkForAdminUser(username);
      TypedQuery<CardUser> query =
              sessionFactory.getCurrentSession().createQuery("SELECT u FROM CardUser u WHERE u.username = ?1");
      query.setParameter(1, username);
      CardUser user = query.getSingleResult();
      if (user != null) {
         return new User(user.getUsername(), user.getPassword(), true, true, true, true,
                 user.getIsAdmin() ?
                         Collections.singleton(() -> SecurityConfig.ADMIN_ROLE) :
                         Collections.singleton(() -> SecurityConfig.USER_ROLE));
      }
      return null;
   }

   private void checkForAdminUser(String username) {
      if (username.equals(SecurityConfig.ADMIN_USERNAME)) {
         TypedQuery<CardUser> query =
                 sessionFactory.getCurrentSession().createQuery("SELECT u FROM CardUser u WHERE u.username = ?1");
         query.setParameter(1, username);
         if (CollectionUtils.isEmpty(query.getResultList())) {
            save(new CardUser(0L, SecurityConfig.ADMIN_USERNAME,
                    new BCryptPasswordEncoder(11).encode("admin123!"),
                    new Timestamp(new Date().getTime()), true));
         }
      }
   }
}
