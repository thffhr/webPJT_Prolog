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
import com.web.blog.model.portfolio.Portfolio;
import com.web.blog.model.tag.Tag;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "p_tag")
public class PortfolioTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ptid")
    private int id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "pid")
    private Portfolio portfolio;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "tid")
    private Tag tag;

}
