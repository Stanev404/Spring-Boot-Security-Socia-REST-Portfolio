package com.ivanstanev.portfolio.controller;

import com.ivanstanev.portfolio.dao.CommentDAO;
import com.ivanstanev.portfolio.entity.business.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CommentController {

    @Autowired
    CommentDAO commentDAO;

    /* to save an comment */
    @PostMapping("/comment")
    public Comment createComment(@RequestBody Comment comment) {

        return constructNewComment(comment);
    }

    private Comment constructNewComment(@RequestBody Comment comment) {
        Comment currentComment = new Comment();
        currentComment.setPublisherName(comment.getPublisherName());
        currentComment.setContentOfComment(comment.getContentOfComment());
        return this.commentDAO.save(currentComment);
    }

    /* get all comments */
    @GetMapping("/comments")
    public List<Comment> getAllComments() {
        return this.commentDAO.findAll();
    }


}
