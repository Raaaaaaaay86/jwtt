package com.example.jwtt.service.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserDetailsImpl implements UserDetails {
	private String                       userName;
	private String                       password;
	private List<SimpleGrantedAuthority> authorities = new ArrayList<>();

	public UserDetailsImpl(String userName, String password, SimpleGrantedAuthority authority) {
		this.userName = userName;
		this.password = password;
		authorities.add(authority);
	}

	public UserDetailsImpl(String userName, String password, List<SimpleGrantedAuthority> authorities) {
		this.userName = userName;
		this.password = password;
		this.authorities = authorities;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities () {
		return null;
	}

	@Override
	public String getPassword () {
		return this.password;
	}

	@Override
	public String getUsername () {
		return this.userName;
	}

	@Override
	public boolean isAccountNonExpired () {
		return false;
	}

	@Override
	public boolean isAccountNonLocked () {
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired () {
		return false;
	}

	@Override
	public boolean isEnabled () {
		return false;
	}
}
