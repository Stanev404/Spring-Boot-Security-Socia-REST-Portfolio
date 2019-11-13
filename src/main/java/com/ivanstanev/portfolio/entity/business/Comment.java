package com.ivanstanev.portfolio.entity.business;


import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "comments")
@EntityListeners(AuditingEntityListener.class)
public class Comment extends AbstractPersistable<Long> {


    private Long id;

    @NotBlank
    private String publisherName;

    @NotBlank
    private String contentOfComment;

    public Comment(){};

    public Comment(@NotBlank String publisherName, @NotBlank String contentOfComment) {
        this.publisherName = publisherName;
        this.contentOfComment = contentOfComment;
    }

    public String getPublisherName() {
        return publisherName;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }

    public String getContentOfComment() {
        return contentOfComment;
    }

    public void setContentOfComment(String contentOfComment) {
        this.contentOfComment = contentOfComment;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", publisherName='" + publisherName + '\'' +
                ", contentOfComment='" + contentOfComment + '\'' +
                '}';
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}
