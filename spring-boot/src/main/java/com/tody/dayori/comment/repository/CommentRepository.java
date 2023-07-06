package com.tody.dayori.comment.repository;

import com.tody.dayori.comment.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("select c from Comment c where c.page.id = :id")
    List<Comment> findCommentsByPageId(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query("delete from Comment c where c.page.id = :id")
    void deleteCommentsByPageId(@Param("id") Long id);
}
