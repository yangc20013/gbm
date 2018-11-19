package com.yang.gbm.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.yang.gbm.models.Role;
import com.yang.gbm.models.User;
import com.yang.gbm.repositories.UserRepo;

public class GbmUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepo userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepo.findByUsername(username);
		if (user != null) {
			List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
			for(Role r : user.getRoles()) {
				GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(r.getAuthority());
				grantedAuthorities.add(grantedAuthority);
			}
//			return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(), grantedAuthorities);
			return buildAuthorities(user,grantedAuthorities);
		} else {
			throw new UsernameNotFoundException("admin: " + username + " do not exist!");
		}
	}

	private UserDetails buildAuthorities(User user, List<GrantedAuthority> authorities) {
		GbmUser u = new GbmUser(user.getUsername(), user.getPassword(), user.getEnabled(), true,true,true, authorities);
		u.setUser(user);
		return u;
	}

}
