package com.yang.gbm.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@JsonIgnoreProperties({"roles"})
public class Menu extends BaseModel{
	private static final long serialVersionUID = 6647089210444644967L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private Long parentId;
	private Integer sort;
	private String name;
	private String href;
	private String icon;
	private Integer type;
	private Boolean usable;
	private String description;
	
	@OneToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "menuRole", joinColumns = { @JoinColumn(name = "menuId") }, inverseJoinColumns = { @JoinColumn(name = "roleId") })
	@OrderBy("id")
    private List<Role> roles;
}
