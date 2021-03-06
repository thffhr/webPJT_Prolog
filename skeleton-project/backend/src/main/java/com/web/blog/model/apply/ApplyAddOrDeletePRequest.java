package com.web.blog.model.apply;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ApplyAddOrDeletePRequest {
    
    @ApiModelProperty(required = true)
    private int apid;

    @ApiModelProperty(required = true)
    private int pid;
}