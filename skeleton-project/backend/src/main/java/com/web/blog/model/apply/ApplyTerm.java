package com.web.blog.model.apply;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Builder;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "applyterm")
public class ApplyTerm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int aptid;

    @Column(name = "ap_term")
    private String apTerm;

    @Builder
    public ApplyTerm(int aptid, String apTerm) {
        this.aptid = aptid;
        this.apTerm = apTerm;
    }

}