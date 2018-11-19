package com.yang.gbm.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.security.core.GrantedAuthority;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Role extends BaseModel implements GrantedAuthority{
	private static final long serialVersionUID = 4643374568693445507L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String description;
	
	@Override
	public String getAuthority() {
		return "ROLE_" + name.toUpperCase();
	}
}
