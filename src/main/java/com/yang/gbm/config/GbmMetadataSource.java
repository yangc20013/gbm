package com.yang.gbm.config;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import com.yang.gbm.models.Menu;
import com.yang.gbm.models.Role;
import com.yang.gbm.repositories.MenuRepo;

@Component
public class GbmMetadataSource implements FilterInvocationSecurityMetadataSource{
	
	private final AntPathMatcher antPathMatcher = new AntPathMatcher();
	private static Map<String, Collection<ConfigAttribute>> resourceMap = null;
	
	@Autowired
    private MenuRepo menuRepo;
	
	private void loadResourceDefine() {
		resourceMap = new HashMap<String, Collection<ConfigAttribute>>();
		List<Menu> menus = menuRepo.findByUsable(true);
		for(Menu menu : menus) {
			List<Role> roles = menu.getRoles();
    		int size = roles.size();
            String[] values = new String[size];
            for (int i = 0; i < size; i++) {
                values[i] = roles.get(i).getAuthority();
            }
            resourceMap.put(menu.getHref(), SecurityConfig.createList(values));
		}
	}

	@Override
	public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
		FilterInvocation fi = (FilterInvocation) object;
        String url = fi.getRequestUrl();
        if(resourceMap == null) loadResourceDefine();
        Iterator<String> ite = resourceMap.keySet().iterator();
		while (ite.hasNext()) {
			String resURL = ite.next();
			if (antPathMatcher.match(url, resURL)) {
				return resourceMap.get(resURL);
			}
		}
//        //没有匹配到,默认是要登录才能访问
        return SecurityConfig.createList("ROLE_USER");
//        return null;
	}

	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		return null;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return FilterInvocation.class.isAssignableFrom(clazz);
	}

}
