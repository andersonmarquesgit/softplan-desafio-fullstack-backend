package com.softplan.restapi.api.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.softplan.restapi.api.enums.ProfileEnum;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tb_user")
@Getter
@Setter
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String id;
	
	@NotBlank(message = "Email required")
	@Email(message = "Email invalid")
	@Column(name = "email", unique = true)
	private String email;
	
	@NotBlank(message = "Password required")
	@Size(min = 6)
	@Column(name = "password", unique = true)
	private String password;
	
	private ProfileEnum profile;

}
