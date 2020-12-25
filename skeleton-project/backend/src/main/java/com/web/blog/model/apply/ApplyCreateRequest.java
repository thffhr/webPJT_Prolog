package com.web.blog.model.apply;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ApplyCreateRequest {

    @ApiModelProperty(required = true)
    private String uid;

    @ApiModelProperty(required = true)
    private String apTitle;

    @ApiModelProperty(required = true)
    private String apCompany;

    private String apTerm;

    private String apDesc;

    public Apply toReqEntity() {
        return Apply.builder().uid(uid).apTitle(apTitle).apCompany(apCompany).apTerm(apTerm).apDesc(apDesc).build();
    }
}