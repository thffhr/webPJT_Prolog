package com.web.blog.model.tag;

import javax.persistence.Column;
import javax.persistence.Id;

import lombok.*;

@Data
@NoArgsConstructor
public class TagCreateResponse {

    @Id
    @Column(name = "tid")
    private int tid;

    @Column(name = "tag_name", nullable = false)
    private String tagName;

    private boolean state;

    @Builder
    public TagCreateResponse(int tid, String tagName, boolean state) {
        this.tid = tid;
        this.tagName = tagName;
        this.state = state;
    }

}
