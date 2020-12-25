package com.web.blog.model.join;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.web.blog.model.experience.Experience;
import com.web.blog.model.tag.Tag;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name = "ex_tag")
public class ExperienceTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="extid")
    private int id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "exid")
    private Experience experience;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "tid")
    private Tag tag;



}
