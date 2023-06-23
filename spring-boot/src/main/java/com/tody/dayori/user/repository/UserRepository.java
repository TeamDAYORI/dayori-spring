package com.tody.dayori.user.repository;

import com.tody.dayori.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
