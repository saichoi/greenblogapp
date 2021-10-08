package com.cos.blogapp.domain.board;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import com.cos.blogapp.domain.comment.Comment;
import com.cos.blogapp.domain.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "board")
@AllArgsConstructor
@NoArgsConstructor
@Data // getter, setter, toString
@Entity
public class Board {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id; //PK (자동증가 번호)
	
	@Column(nullable = false, length = 70)
	private String title; // 아이디
	
	@Lob
	private String content;
	
	@JoinColumn(name = "userId") // 외래키(포린키)의 이름을 바꿀 수 있다.
	@ManyToOne(fetch = FetchType.EAGER) //LAZY : user=null 쿼리 한번 실행 (경우(페이지)에 따라 필요/불필요한 데이터를 선택할 수 있다.)-> 지연로딩 || EAGER : 미리 땡겨옴(디폴트값:어차피 땡켜올 게시글이 한건이라서)   
	private User user; // user_id 만들어준다. 포린키 만들어준다.
	
	// 양방향 매핑 
	// mappedBy 에는 FK의 주인의 변수의 이름(board)을 추가한다.

	@JsonIgnoreProperties({"board"}) // comments 객체 내부의 필드를 제외시키는 법
	//@JsonIgnore // comments 객체를 자체 제외시키는 것
	@OneToMany(mappedBy =  "board", fetch = FetchType.LAZY)
	@OrderBy("id desc")
	private List<Comment> comments;

}
