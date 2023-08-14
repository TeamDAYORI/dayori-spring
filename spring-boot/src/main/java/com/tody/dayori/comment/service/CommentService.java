package com.tody.dayori.comment.service;

import com.tody.dayori.comment.domain.Comment;
import com.tody.dayori.comment.dto.CreateCommentRequest;
import com.tody.dayori.comment.dto.DeleteCommentRequest;
import com.tody.dayori.comment.dto.UpdateCommentRequest;
import com.tody.dayori.comment.exception.CommentNotFoundException;
import com.tody.dayori.comment.repository.CommentRepository;
import com.tody.dayori.page.domain.Page;
import com.tody.dayori.auth.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    @Transactional
    public Long createComment (CreateCommentRequest createCommentRequest, User user, Page page) {
        Comment comment = Comment.builder()
                .content(createCommentRequest.getContent())
                .date(LocalDateTime.now())
                .userInfo(user)
                .page(page)
                .build();
        commentRepository.save(comment);
        return comment.getId();
    }

    @Transactional
    public void updateComment (UpdateCommentRequest updateCommentRequest, User user) {
        Comment comment = commentRepository.findById(updateCommentRequest.getCommentId())
                .orElseThrow(CommentNotFoundException::new);
        if (comment.getUserInfo().getUserSeq() == user.getUserSeq()) {
            comment.update(updateCommentRequest.getContent());
        } else throw new IllegalStateException("댓글 수정 권한이 없음");
    }

    @Transactional
    public void deleteComment (DeleteCommentRequest deleteCommentRequest, User user) {
        Comment comment = commentRepository.findById(deleteCommentRequest.getCommentId())
                .orElseThrow(CommentNotFoundException::new);
        if (comment.getUserInfo().getUserSeq() == user.getUserSeq())
            commentRepository.deleteById(comment.getId());
        else throw new IllegalStateException("댓글 삭제 권한이 없음");
    }
}
