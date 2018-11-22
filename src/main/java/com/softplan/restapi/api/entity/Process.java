package com.softplan.restapi.api.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.softplan.restapi.api.enums.StatusEnum;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tb_process")
@Getter
@Setter
public class Process {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String id;
	
	@OneToMany(cascade = CascadeType.ALL)
    private Set<User> usersAssigned = new HashSet<>();
	
	@Column(name = "number")
	private Integer number;
	
	@Column(name = "occurrence")
	private String occurrence;
	
	@Column(name = "legal_opinion")
	private String legalOpinion;
	
	@Column(name = "create_at")
	private Date createAt;

	@Column(name = "update_at")
	private Date updateAt;
	
	private StatusEnum status;
}
