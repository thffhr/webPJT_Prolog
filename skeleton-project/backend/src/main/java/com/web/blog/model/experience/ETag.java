package com.web.blog.model.experience;

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
@Table(name = "ex_tag")
public class ETag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "extid")
    private int extid;
    
    @Column(name ="exid")
    private int exid;

    @Column(name ="tid")
    private int tid;


    @Builder
    public ETag(int extid, int exid, int tid){
        this.extid = extid;
        this.exid = exid;
        this.tid = tid;
    }

    public ETagCreateResponse toResEntity(){
        return ETagCreateResponse.builder()
                .extid(extid)
                .exid(exid)
                .tid(tid)
                    .build();
    }

}