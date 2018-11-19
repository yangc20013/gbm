package com.yang.gbm.models;

import java.util.Calendar;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties({"password","createTime","updateTime","roles"})
public class User {
	private static final long serialVersionUID = 199347493491188752L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@Column(nullable=false)
	private String username;
	@Column(nullable=false)
	private String password;
	private String nickName;
	private String phone;
	private String telephone;
	private Integer age;
	private Boolean isMale;
	private String address;
	private String email;
	private String avatar;
	private Boolean enabled;
	private Integer userType;
	private String remarks;
	
	@CreatedBy
	private String createdBy;
	
	@CreatedDate
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern="yyyy/MM/dd")
	private Calendar createTime;
	@LastModifiedDate
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern="yyyy/MM/dd")
	private Calendar updateTime;
	
	@OneToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "userRole", joinColumns = { @JoinColumn(name = "userId") }, inverseJoinColumns = { @JoinColumn(name = "roleId") })
	@OrderBy("id")
    private List<Role> roles;
	
}
