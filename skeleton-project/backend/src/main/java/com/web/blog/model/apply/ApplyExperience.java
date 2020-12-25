package com.web.blog.model.apply;

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

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "apply_experience")
public class ApplyExperience {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ap_exid")
    private int id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "apid")
    private Apply apply;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "exid")
    private Experience experience;

    @Builder
    public ApplyExperience(Apply apply, Experience experience) {
        this.apply = apply;
        this.experience = experience;
    }
}
