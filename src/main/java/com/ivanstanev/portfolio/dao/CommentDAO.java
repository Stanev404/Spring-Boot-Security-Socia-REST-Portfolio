package com.ivanstanev.portfolio.dao;

import com.ivanstanev.portfolio.entity.business.Comment;
import com.ivanstanev.portfolio.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// data access object
// DATA ACCESS OBJECT
@Service
public class CommentDAO {

    @Autowired
    private CommentRepository commentRepository;

    /* to save an comment in DB */

    public Comment save(Comment comment) {
        return this.commentRepository.save(comment);
    }

    /* search all comments */

    public List<Comment> findAll() {
        return this.commentRepository.findAll();
    }

    /* get an comment */

    public Optional<Comment> findOne(Long commentId) {
        return this.commentRepository.findById(commentId);
    }

    /* delete an comment */

    public void delete(Comment comment) {
        this.commentRepository.delete(comment);
    }
}
