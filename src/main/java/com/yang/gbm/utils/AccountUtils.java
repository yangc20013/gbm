package com.yang.gbm.utils;

import org.springframework.security.core.context.SecurityContextHolder;

import com.yang.gbm.config.GbmUser;
import com.yang.gbm.models.User;

public class AccountUtils {
	public static User getCurrentUser() {
		GbmUser epbUser = (GbmUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return epbUser.getUser();
	}
}
