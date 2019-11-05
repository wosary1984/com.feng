package com.feng.security.service;

import com.feng.security.model.ApplicationUser;

public interface ApplicationUserService {

    ApplicationUser getUserByName(String username);

    ApplicationUser getUserById(String userid);

    Iterable<ApplicationUser> getAllUsersExceptMe(String username);

}