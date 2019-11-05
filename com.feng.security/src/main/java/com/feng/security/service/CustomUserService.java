package com.feng.security.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.feng.exception.def.BadRequestException;
import com.feng.security.model.ApplicationPrivilege;
import com.feng.security.model.ApplicationUser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserService implements UserDetailsService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	ApplicationUserService applicationUserService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		ApplicationUser loginUser = applicationUserService.getUserByName(username);

		if (loginUser != null) {
			Collection<GrantedAuthority> grantedAuthorities = getAuthorities(loginUser);

			return new User(loginUser.getUsername(), loginUser.getPassword(), grantedAuthorities);
		} else {
			logger.error("user:{} is not existed", username);
			throw new UsernameNotFoundException("user: " + username + " is not exised!");
		}
	}

	/* 获取用户的角色权限,为了降低实验的难度，这里去掉了根据用户名获取角色的步骤 */
	private Collection<GrantedAuthority> getAuthorities(ApplicationUser user) {
		List<GrantedAuthority> authList = new ArrayList<GrantedAuthority>();
		for (ApplicationPrivilege privilege : user.getPrivileges()) {
			authList.add(new SimpleGrantedAuthority(privilege.getPrivilege()));
		}
		// authList.add(new SimpleGrantedAuthority("ROLE_USER"));
		// authList.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		return authList;
	}

	public ApplicationUser getLoginUser() {
		ApplicationUser loginUser = null;
		SecurityContext context = SecurityContextHolder.getContext();
		Authentication authentication = context.getAuthentication();
		if (authentication.getName() != null) {
			loginUser = applicationUserService.getUserByName(authentication.getName());
		}
		return loginUser;
	}

	public Iterable<ApplicationUser> getAllUsersExceptMe() {
		ApplicationUser me = this.getLoginUser();
		if (me == null) {
			throw new BadRequestException("login user not found!");
		}
		return applicationUserService.getAllUsersExceptMe(me.getUsername());
	}
}
