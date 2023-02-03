package com.sch.shift3.user.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "content_feed")
public class ContentFeed {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Lob
    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "title", nullable = false, length = 30)
    private String title;

    @Column(name = "thumbnail_file_name", nullable = false)
    private String thumbnailFileName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}