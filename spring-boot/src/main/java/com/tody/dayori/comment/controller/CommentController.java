package com.tody.dayori.comment.controller;

import com.tody.dayori.comment.dto.CreateCommentRequest;
import com.tody.dayori.comment.dto.DeleteCommentRequest;
import com.tody.dayori.comment.dto.UpdateCommentRequest;
import com.tody.dayori.comment.service.CommentService;
import com.tody.dayori.common.dto.BaseResponse;
import com.tody.dayori.page.domain.Page;
import com.tody.dayori.page.service.PageService;
import com.tody.dayori.auth.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import static com.tody.dayori.comment.constant.CommentConstant.*;

@Controller
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {

    private final PageService pageService;
    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<BaseResponse> createComment (@RequestBody CreateCommentRequest createCommentRequest) {
        User user = new User();
        user.setUserSeq(2L);
        Page page = pageService.findPageById(createCommentRequest.getPageId());

        return new ResponseEntity<>(BaseResponse.from(
                true,
                CREATE_COMMENT_SUCCESS_MESSAGE,
                commentService.createComment(createCommentRequest, user, page)),
                HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<BaseResponse> updateComment (@RequestBody UpdateCommentRequest updateCommentRequest) {
        // 수정 권한 확인
        commentService.updateComment(updateCommentRequest);
        return new ResponseEntity<>(BaseResponse.from(
                true,
                UPDATE_COMMENT_SUCCESS_MESSAGE),
                HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<BaseResponse> deleteComment (@RequestBody DeleteCommentRequest deleteCommentRequest) {
        User user = new User();
        user.setUserSeq(2L);
        // 삭제 권한 확인
        commentService.deleteComment(deleteCommentRequest, user);
        return new ResponseEntity<>(BaseResponse.from(
                true,
                DELETE_COMMENT_SUCCESS_MESSAGE),
                HttpStatus.OK);
    }
}