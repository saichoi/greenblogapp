package com.cos.blogapp.web.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.cos.blogapp.domain.board.Board;
import com.cos.blogapp.domain.user.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardSaveReqDto {
	
	@Size(min = 1, max = 50)
	@NotBlank //null, 공백 둘 다 잡아준다.
	private String title;
	private String content;
	
	public Board toEntity(User principal) {
		Board board = new Board();
		board.setTitle(title);
		board.setContent(content);
		board.setUser(principal);
		return board;
	}
	
}
