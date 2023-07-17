package com.tody.dayori.page.service;

import com.tody.dayori.comment.domain.Comment;
import com.tody.dayori.comment.dto.CommentInfoResponse;
import com.tody.dayori.comment.repository.CommentRepository;
import com.tody.dayori.diary.domain.Diary;
import com.tody.dayori.page.domain.Page;
import com.tody.dayori.page.dto.*;
import com.tody.dayori.page.exception.PageNotFoundException;
import com.tody.dayori.page.repository.PageRepository;
import com.tody.dayori.auth.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PageService {

    private final PageRepository pageRepository;
    private final CommentRepository commentRepository;

    /**
     * 페이지 생성
     * @param createPageRequest diaryId, title, content
     * @param user 유저 정보
     * @param diary 다이어리 정보
     * @return pageId
     */
    @Transactional
    public Long createPage (CreatePageRequest createPageRequest, User user, Diary diary) {
        Page page = Page.builder()
                    .title(createPageRequest.getTitle())
                    .content(createPageRequest.getContent())
                    .date(LocalDateTime.now())
                    .userInfo(user)
                    .diary(diary)
                    .build();
        pageRepository.save(page);
        return page.getId();
    }

    /**
     * 페이지 상세 조회
     * @param searchPageRequest pageId
     * @return Page: title, content, date, nickname, comments
     */
    public SearchPageResponse getPage (SearchPageRequest searchPageRequest) {
        Page page = pageRepository.findById(searchPageRequest.getPageId())
                .orElseThrow(PageNotFoundException::new);
        List<Comment> commentList = commentRepository.findCommentsByPageId(page.getId());
        List<CommentInfoResponse> commentsResponseList = new ArrayList<>();

        for (Comment comment : commentList) {
            commentsResponseList.add(
                    CommentInfoResponse
                            .builder()
                            .username(comment.getUserInfo().getUserName())
                            .userImg(comment.getUserInfo().getUserImgUrl())
                            .date(comment.getDate())
                            .content(comment.getContent())
                            .build());
        }

        return SearchPageResponse.builder()
                .title(page.getTitle())
                .content(page.getContent())
                .date(page.getDate())
                .nickname(page.getUserInfo().getUserName())
                .comments(commentsResponseList)
                .build();
    }

    public Page findPageById (Long pageId) {
        return pageRepository.findById(pageId)
                .orElseThrow(IllegalStateException::new);
    }

    /**
     * 페이지 수정
     * @param updatePageRequest title, content
     */
    @Transactional
    public void updatePage (UpdatePageRequest updatePageRequest) {
        Page page = pageRepository.findById(updatePageRequest.getPageId())
                .orElseThrow(PageNotFoundException::new);
        // 변경 권한 확인
        page.update(updatePageRequest.getTitle(), updatePageRequest.getContent());
    }

    /**
     * 페이지 삭제 (+ 페이지에 달린 댓글 삭제)
     * @param deletePageRequest pageId
     */
    @Transactional
    public void deletePage (DeletePageRequest deletePageRequest) {
        Page page = pageRepository.findById(deletePageRequest.getPageId())
                .orElseThrow(PageNotFoundException::new);
        // 삭제 권한 확인 (parameter: email)
        pageRepository.deleteById(page.getId());
        commentRepository.deleteCommentsByPageId(page.getId());
    }

}
