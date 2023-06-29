package com.keyon.sfilehub.service;

import com.keyon.sfilehub.dao.UserDao;
import com.keyon.sfilehub.entity.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author Keyon
 * @data 2023/6/16 13:13
 * @desc UserService
 */
@Service
public class UserService implements UserDetailsService {

    private UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        if (StringUtils.isBlank(username)) throw new IllegalArgumentException("用户名不能为空");
        User user = userDao.findByUsername(username);
        if (user == null) throw new UsernameNotFoundException("用户名不存在");
        return user;
    }

    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }
}
