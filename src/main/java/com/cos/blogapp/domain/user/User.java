package com.cos.blogapp.domain.user;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class User {
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id; 
	private String uesrname; 
	private String password;
	private String email;
	
}
