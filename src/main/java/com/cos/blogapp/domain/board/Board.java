package com.cos.blogapp.domain.board;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.cos.blogapp.domain.user.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "board")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Board {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id; //PK (자동증가 번호)
	private String title; // 아이디
	@Lob
	private String content;
	
	@JoinColumn(name = "userId") // 외래키(포린키)의 이름을 바꿀 수 있다.
	@ManyToOne(fetch = FetchType.EAGER) //LAZY : user=null 쿼리 한번 실행 (경우(페이지)에 따라 필요/불필요한 데이터를 선택할 수 있다.)-> 지연로딩 || EAGER : 미리 땡겨옴(디폴트값:어차피 땡켜올 게시글이 한건이라서)   
	private User user; // user_id 만들어준다. 포린키 만들어준다. 
	
}
