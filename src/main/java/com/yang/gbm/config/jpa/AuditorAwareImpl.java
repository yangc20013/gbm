package com.yang.gbm.config.jpa;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.yang.gbm.config.GbmUser;
import com.yang.gbm.models.User;

public class AuditorAwareImpl implements AuditorAware<String> {

	@Override
	public Optional<String> getCurrentAuditor() {
		SecurityContext context = SecurityContextHolder.getContext();
		if (context != null && context.getAuthentication() != null) {
			GbmUser user = (GbmUser) context.getAuthentication().getPrincipal();
			return Optional.of(user.getUser().getUsername().toString());
		}
		return Optional.empty();
	}
}
