package com.feng.config.security;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import com.feng.security.model.ApplicationPrivilege;
import com.feng.security.model.ApplicationUser;
import com.feng.security.service.ApplicationUserService;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("local")
public class ApplicationUserServiceImp implements ApplicationUserService {

    private List<ApplicationUser> users = new ArrayList<ApplicationUser>();

    @Override
    public ApplicationUser getUserByName(String username) {
        List<ApplicationUser> r = this.users.stream().filter(a -> a.getUsername().equals(username))
                .collect(Collectors.toList());
        r.forEach(System.out::println);
        if (r.size() > 0)
            return r.get(0);
        else
            return null;
    }

    @Override
    public ApplicationUser getUserById(String userid) {
        return null;
    }

    @Override
    public Iterable<ApplicationUser> getAllUsersExceptMe(String username) {
        return null;
    }

    public ApplicationUserServiceImp() {

        ApplicationPrivilege p = new ApplicationPrivilege();
        p.setPrivilege("READ_PRIVILEGE");
        p.setObject("");

        ApplicationUser user1 = new ApplicationUser();
        user1.setUsername("feng");
        user1.setPassword("$2a$10$trT3.R/Nfey62eczbKEnueTcIbJXW.u1ffAo/XfyLpofwNDbEB86O");
        user1.setLocked(false);
        user1.setPrivileges(new HashSet<ApplicationPrivilege>(Arrays.asList(p)));
        
        ApplicationUser user2 = new ApplicationUser();
        user2.setUsername("user");
        user2.setPassword("$2a$10$trT3.R/Nfey62eczbKEnueTcIbJXW.u1ffAo/XfyLpofwNDbEB86O");
        user2.setPrivileges(new HashSet<ApplicationPrivilege>(Arrays.asList(p)));
        this.users.add(user1);
        this.users.add(user2);
    }

}