package com.spring.deal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.deal.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String>{
	
	public boolean existsByUserId(String Userid);
	
	public User findByUserId(String userId);
}
