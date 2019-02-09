package com.vn.vsii.Service.Impl;

import com.vn.vsii.Service.AccountService;
import com.vn.vsii.model.Account;
import com.vn.vsii.model.Role;
import com.vn.vsii.reponsitory.AccountReponsitory;
import com.vn.vsii.reponsitory.RoleReponsitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    AccountReponsitory accountReponsitory;
    @Autowired
    RoleReponsitory roleReponsitory;
    @Override
    public Page<Account> findAll(Pageable pageable) {
        return accountReponsitory.findAll(pageable);
    }

    @Override
    public Account findById(Long id) {
        return accountReponsitory.findAllById(id).orElse(null);
    }

    @Override
    public void save(Account user) {
    accountReponsitory.save(user);
    }

    @Override
    public void remove(Long id) {
    accountReponsitory.findAllById(id);
    }

    @Override
    public void setupRole(String role, Account account) {
        Set<Role> roles=new HashSet<>();
        roles.add(roleReponsitory.findByName(role));

    }

    @Override
    public Page<Account> findAllByStudentName(String nickname, Pageable pageable) {
        return null;
    }

    @Override
    public Account findByUserName(String username) {
        return accountReponsitory.findByUserName(username);
    }


}
