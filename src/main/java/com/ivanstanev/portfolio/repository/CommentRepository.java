package com.ivanstanev.portfolio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ivanstanev.portfolio.entity.business.Comment;

public interface CommentRepository extends JpaRepository<Comment,Long> {
}
