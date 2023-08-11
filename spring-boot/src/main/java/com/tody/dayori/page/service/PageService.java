package com.tody.dayori.page.service;

import com.tody.dayori.auth.entity.User;
import com.tody.dayori.diary.domain.Diary;
import com.tody.dayori.page.domain.Page;
import com.tody.dayori.page.dto.*;

public interface PageService {
    Long createPage(CreatePageRequest createPageRequest, User user, Diary diary);
    SearchPageResponse getPage (SearchPageRequest searchPageRequest);
    Page findPageById (Long pageId);
    void updatePage (UpdatePageRequest updatePageRequest, Long userSeq);
    void deletePage (DeletePageRequest deletePageRequest, Long userSeq);
}
