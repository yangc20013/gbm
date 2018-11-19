package com.yang.gbm.config;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import com.yang.gbm.models.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GbmUser extends org.springframework.security.core.userdetails.User{
	private User user;

	private static final long serialVersionUID = -1524873397309733866L;
	
	public GbmUser(String username, String password, boolean enabled,
			boolean accountNonExpired, boolean credentialsNonExpired,
			boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired,
				credentialsNonExpired, accountNonLocked, authorities);
	}

	public GbmUser(String username, String password, 
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
	}

}
