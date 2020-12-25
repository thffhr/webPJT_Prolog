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
import com.web.blog.model.portfolio.Portfolio;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Table(name = "apply_portfolio")
public class ApplyPortfolio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ap_pid")
    private int id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "apid")
    private Apply apply;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "pid")
    private Portfolio portfolio;

    @Builder
    public ApplyPortfolio(Apply apply, Portfolio portfolio) {
        this.apply = apply;
        this.portfolio = portfolio;
    }
}