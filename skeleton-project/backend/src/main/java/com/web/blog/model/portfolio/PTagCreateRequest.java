package com.web.blog.model.portfolio;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.Column;

@Getter
@Setter
@NoArgsConstructor
public class PTagCreateRequest {

    @ApiModelProperty(required = true)
    @Column(name = "pid")
    int pid;

    @ApiModelProperty(required = true)
    @Column(name = "tid")
    int tid;

    public PTag toEntity() {
        return PTag.builder().pid(pid).tid(tid).build();
    }
}
