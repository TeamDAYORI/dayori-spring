package com.tody.dayori.page.repository;

import com.tody.dayori.page.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PageRepository extends JpaRepository<Page, Long> {
}
