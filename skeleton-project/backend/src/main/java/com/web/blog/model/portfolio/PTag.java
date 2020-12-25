package com.web.blog.model.portfolio;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.*;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "p_tag")
public class PTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ptid")
    private int ptid;

    @Column(name = "pid")
    private int pid;

    @Column(name = "tid")
    private int tid;

    // @OneToMany(mappedBy = "tag")
    // private List<PortfolioTag> portfolioTags = new ArrayList<>();

    @Builder
    public PTag(int ptid, int pid, int tid) {
        this.ptid = ptid;
        this.pid = pid;
        this.tid = tid;
    }

    public PTagCreateResponse toResEntity() {
        return PTagCreateResponse.builder().ptid(ptid).pid(pid).tid(tid).build();
    }

}