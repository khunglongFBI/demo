package com.vn.vsii.Service.Impl;

import com.vn.vsii.model.Account;
import com.vn.vsii.model.MyUserPrincipal;
import com.vn.vsii.reponsitory.AccountReponsitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserDetailsService {
    @Autowired
    AccountReponsitory accountReponsitory;
    @Override
    public UserDetails loadUserByUsername(String username) {
        Account user = accountReponsitory.findByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new MyUserPrincipal(user);
    }
}
