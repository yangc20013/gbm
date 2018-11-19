package com.yang.gbm.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.yang.gbm.models.User;

public interface UserRepo extends PagingAndSortingRepository<User, Long>{
	
	User findByUsername(String username);

}
