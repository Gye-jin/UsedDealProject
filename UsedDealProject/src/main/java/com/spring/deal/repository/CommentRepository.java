package com.spring.deal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.deal.entity.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>{

}
