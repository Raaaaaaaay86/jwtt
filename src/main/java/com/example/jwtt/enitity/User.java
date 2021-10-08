package com.example.jwtt.enitity;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@Entity
@Table(name = "user_list")
public class User implements UserDetails, Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "username")
	private String username;

	@Column(name = "password")
	private String password;

	@Column(name = "email")
	private String email;

	@Column(name = "enabled")
	private Boolean enabled;

	@ManyToMany(cascade = CascadeType.ALL)
	private List<UserRole> roles;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities () {
		List<GrantedAuthority> authorities = new ArrayList<>();
		List<UserRole> roles = this.getRoles();

		roles.forEach(role -> {
			authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
		});

		return authorities;
	}

	@Override
	public String getUsername () {
		return this.username;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public boolean isEnabled () {
		return this.enabled;
	}

	@Override
	public boolean isAccountNonExpired () {
		return true;
	}

	@Override
	public boolean isAccountNonLocked () {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired () {
		return true;
	}
}
