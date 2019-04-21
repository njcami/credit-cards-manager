package com.manager.cards.service;

import com.manager.cards.common.UserPrincipal;
import com.manager.cards.dao.CardUserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Service class for finding the UserPrincipal from the username
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private CardUserDao cardUserDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = cardUserDao.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new UserPrincipal(user);
    }
}
