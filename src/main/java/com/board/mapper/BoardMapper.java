package com.board.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.board.domain.BoardDTO;

@Mapper
public interface BoardMapper {
    //return type int : 보통 void. SQL 결과값을 확실하게 전달받기 위해서
    public int insertBoard(BoardDTO params);     //게시글 생성
    public BoardDTO selectBoardDetail(Long idx); //게시글 하나 조회
    public int updateBoard(BoardDTO params);     //수정
    public int deleteBoard(Long idx);            //삭제
    public List<BoardDTO> selectBoardList(BoardDTO params); //게시글 목록 조회
    public int selectBoardTotalCount(BoardDTO params); //delete_yn 'N' 게시글 개수 조회
    public boolean cntPlus(Long idx); //조회수 카운트
}