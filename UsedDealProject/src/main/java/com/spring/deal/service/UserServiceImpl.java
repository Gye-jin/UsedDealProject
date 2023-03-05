package com.spring.deal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.spring.deal.dto.UserDTO;
import com.spring.deal.entity.User;
import com.spring.deal.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
	
	@Autowired
	UserRepository userRepository;
	
	BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	
	@Override
	public ResponseEntity<?> joinUser(UserDTO userDTO){
		User user = User.DTOtoEntity(userDTO);
		System.out.println(user);
		user.passwordEncoding(encoder.encode(user.getPassword()));
		userRepository.save(user);
		
		return new ResponseEntity<String>("ok", HttpStatus.OK);
	}
}