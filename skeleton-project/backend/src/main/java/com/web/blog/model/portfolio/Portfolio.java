package com.web.blog.model.portfolio;

import java.sql.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.web.blog.model.apply.ApplyPortfolio;
// import com.web.blog.model.apply.ApplyPortfolio;
import com.web.blog.model.join.PortfolioTag;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@NoArgsConstructor
@Getter
@ToString
public class Portfolio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int pid;

    private String uid;

    private String title;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "start_date", insertable = false)
    private Date startDate;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "end_date", insertable = false)
    private Date endDate;

    @Column(name = "contents")
    private String contents;

    @OneToMany(mappedBy = "portfolio")
    private List<PortfolioTag> portfolioTags;

    @OneToMany(mappedBy = "pid")
    private List<UploadFile> files;

    @OneToMany(mappedBy = "portfolio")
    private List<ApplyPortfolio> applyPortfolios;

    @Builder
    public Portfolio(int pid, String uid, Date startDate, Date endDate, String title, String contents) {
        this.pid = pid;
        this.uid = uid;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.contents = contents;
    }

}