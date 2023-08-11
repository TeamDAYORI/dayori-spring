package com.tody.dayori.comment.controller;

import com.tody.dayori.auth.repository.UserRepository;
import com.tody.dayori.auth.service.UserServiceImpl;
import com.tody.dayori.comment.dto.CreateCommentRequest;
import com.tody.dayori.comment.dto.DeleteCommentRequest;
import com.tody.dayori.comment.dto.UpdateCommentRequest;
import com.tody.dayori.comment.service.CommentService;
import com.tody.dayori.common.dto.BaseResponse;
import com.tody.dayori.common.exception.NotFoundException;
import com.tody.dayori.page.domain.Page;
import com.tody.dayori.page.service.PageServiceImpl;
import com.tody.dayori.auth.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import static com.tody.dayori.comment.constant.CommentConstant.*;
import static com.tody.dayori.common.util.TokenUtil.*;

@Controller
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {

    private final PageServiceImpl pageService;
    private final CommentService commentService;
    private final UserRepository userRepository;

    @PostMapping
    public ResponseEntity<BaseResponse> createComment (@RequestBody CreateCommentRequest createCommentRequest) {
        User user = userRepository.findById(getCurrentUserSeq())
                .orElseThrow(NotFoundException::new);
        Page page = pageService.findPageById(createCommentRequest.getPageId());

        return new ResponseEntity<>(BaseResponse.from(
                true,
                CREATE_COMMENT_SUCCESS_MESSAGE,
                commentService.createComment(createCommentRequest, user, page)),
                HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<BaseResponse> updateComment (@RequestBody UpdateCommentRequest updateCommentRequest) {
        User user = userRepository.findById(getCurrentUserSeq())
                .orElseThrow(NotFoundException::new);
        commentService.updateComment(updateCommentRequest, user);
        return new ResponseEntity<>(BaseResponse.from(
                true,
                UPDATE_COMMENT_SUCCESS_MESSAGE),
                HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<BaseResponse> deleteComment (@RequestBody DeleteCommentRequest deleteCommentRequest) {
        User user = userRepository.findById(getCurrentUserSeq())
                .orElseThrow(NotFoundException::new);
        commentService.deleteComment(deleteCommentRequest, user);
        return new ResponseEntity<>(BaseResponse.from(
                true,
                DELETE_COMMENT_SUCCESS_MESSAGE),
                HttpStatus.OK);
    }
}