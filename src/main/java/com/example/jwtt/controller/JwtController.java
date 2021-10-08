package com.example.jwtt.controller;

import com.example.jwtt.dao.UserRepository;
import com.example.jwtt.dao.UserRoleRepository;
import com.example.jwtt.enitity.User;
import com.example.jwtt.enitity.UserRole;
import com.example.jwtt.enitity.request.LoginRequest;
import com.example.jwtt.utils.jwt.JwtUtil;
import com.example.jwtt.utils.result.Response;
import com.example.jwtt.utils.result.ResponseStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("jwt")
public class JwtController {

	@Autowired
	UserRepository userRepository;

	@Autowired
	UserRoleRepository userRoleRepository;

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired JwtUtil jwtUtil;

	@PostMapping("/register")
	public Response register(@RequestBody HashMap<String, User> requestBody) {
		User user = requestBody.get("data");
		Optional<User> repoUser = userRepository.findByUsername(user.getUsername());

		if (repoUser.isPresent()) return new Response(new ResponseStatus(200, "用戶已存在"));

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		UserRole userRole = userRoleRepository.findByRoleId("ROLE_USER");
		List<UserRole> roles = new ArrayList<>();
		roles.add(userRole);

		user.setPassword(encoder.encode(user.getPassword()));
		user.setEnabled(true);
		user.setRoles(roles);

		var data = new HashMap<String, Object>();
		data.put("userInfo", user);

		userRepository.save(user);

		return new Response(data, new ResponseStatus());
	}

	@PostMapping("/login")
	public Response login(@RequestBody HashMap<String, LoginRequest> requestBody) {
		var loginInformation = requestBody.get("data");
		var username = loginInformation.getUsername();
		var password = loginInformation.getPassword();

		Authentication authAfterSuccessLogin = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

		SecurityContextHolder.getContext().setAuthentication(authAfterSuccessLogin);

		List<String> userRoles =
				authAfterSuccessLogin
						.getAuthorities().stream()
						.map(GrantedAuthority::getAuthority)
						.collect(Collectors.toList());

		var responseResult = new HashMap<String, Object>();
		responseResult.put("username", username);
		responseResult.put("password", password);
		responseResult.put("userRoles", userRoles);
		responseResult.put("token", jwtUtil.createToken(username, userRoles));

		return new Response(responseResult);
	}

	@GetMapping("/secure")
	public Response secure() {
		var response = new HashMap<String, Object>();
		response.put("foo", "bar");
		return new Response(response);
	}
}
