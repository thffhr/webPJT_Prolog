package com.web.blog.model.experience;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.*;

@Entity
@Data
@NoArgsConstructor
public class ETagCreateResponse {

    @Id
    @Column(name = "extid")
    private int extid;

    @Column(name = "exid")
    private int exid;

    @Column(name = "tid")
    private int tid;

    @Builder
    public ETagCreateResponse(int extid, int exid, int tid) {
        this.extid = extid;
        this.exid = exid;
        this.tid = tid;
    }

}
