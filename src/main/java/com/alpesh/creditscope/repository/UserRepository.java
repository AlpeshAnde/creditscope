package com.alpesh.creditscope.repository;

import com.alpesh.creditscope.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
