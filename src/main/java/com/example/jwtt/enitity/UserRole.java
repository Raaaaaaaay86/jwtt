package com.example.jwtt.enitity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@Table(name = "user_role")
public class UserRole implements Serializable {
	@Id
	@Column(name = "role_id", unique = true)
	private String roleId;

	@Column(name = "role_name")
	private String roleName;
}
