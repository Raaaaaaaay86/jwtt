package com.example.jwtt.service.security;

import com.example.jwtt.dao.UserRepository;
import com.example.jwtt.enitity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailService implements UserDetailsService {
	@Autowired
	UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername (String username) throws UsernameNotFoundException {
		Optional<User> repoUser = userRepository.findByUsername(username);

		if (repoUser.isEmpty()) {
			throw new UsernameNotFoundException("用戶不存在");
		}

		return repoUser.get();
	}
}
