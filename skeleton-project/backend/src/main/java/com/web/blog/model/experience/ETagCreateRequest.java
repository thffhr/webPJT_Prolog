package com.web.blog.model.experience;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.Column;

@Getter
@NoArgsConstructor
public class ETagCreateRequest {

    @ApiModelProperty(required = true)
    @Column(name = "exid")
    int exid;

    @ApiModelProperty(required = true)
    @Column(name = "tid")
    int tid;

    public ETag toEntity() {
        return ETag.builder().exid(exid).tid(tid).build();
    }
}
