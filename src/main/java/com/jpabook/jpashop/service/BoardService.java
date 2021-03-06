package com.jpabook.jpashop.service;

import com.jpabook.jpashop.domain.board.*;
import com.jpabook.jpashop.domain.form.BoardForm;
import com.jpabook.jpashop.domain.member.Member;
import com.jpabook.jpashop.domain.session.MemberInfo;
import com.jpabook.jpashop.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {

    private final BoardRepository boardRepository;

    public List<Board> findAll(BoardSearch boardSearch) {
        return boardRepository.findAll(boardSearch);
    }

    public List<Board> findAll() {
        return boardRepository.findAll();
    }

    public Board findBoard(Long boardId) {
        return boardRepository.findOne(boardId);
    }

    @Transactional
    public Long saveBoard(Board board) {
        boardRepository.save(board);
        return board.getId();
    }


    @Transactional
    public void updateBoard(Long id, String subject, String content, BoardCategory boardCategory) {
        Board findItem = boardRepository.findOne(id);

        // 영속성 진입
        findItem.change(subject, content, boardCategory);
    }

    @Transactional
    public void deleteBoard(Long id) {
        Board findItem = boardRepository.findOne(id);

        // 영속성 진입
        findItem.delete();
    }

    @Transactional
    public void createComment(Long boardId, String content, Member member) {
        Board board = boardRepository.findOne(boardId);
        Comment comment = Comment.createComment(board, member, content);

        board.getComments().add(comment);
    }
}
