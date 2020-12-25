package com.web.blog.model.tag;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.web.blog.model.join.PortfolioTag;

import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tag")
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tid")
    private int tid;

    @Column(name = "tag_name", nullable = false)
    private String tagName;

    @OneToMany(mappedBy = "tag")
    private List<PortfolioTag> portfolioTags = new ArrayList<>();

    @Builder
    public Tag(int tid, String tagName) {
        this.tid = tid;
        this.tagName = tagName;
    }

    public TagCreateResponse toResEntity() {
        return TagCreateResponse.builder().tid(tid).tagName(tagName).state(false).build();
    }

}