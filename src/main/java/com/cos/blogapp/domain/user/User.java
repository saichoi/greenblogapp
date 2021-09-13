package com.cos.blogapp.domain.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "user")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class User {
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id; 
	@Column(nullable = false, length = 20, unique = true) //무조건 username 받도록 만들기, 20byte(한글6자)로 제외, 중복 제한  
	private String username;
	@Column(nullable = false, length = 70)
	private String password;
	@Column(nullable = false, length = 50)
	private String email;
	
}
