package com.sch.shift3.user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "image")
@DiscriminatorColumn(name="image_type")
@Inheritance(strategy= InheritanceType.SINGLE_TABLE)
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
//
//    @Column(name = "feed_id")
//    private Integer feedId;

    @Column(name = "image_name", nullable = false, length = 100)
    private String imageName;


}