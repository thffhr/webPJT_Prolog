package com.web.blog.model.experience;

import java.sql.Date;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ExperienceCreateRequest {
    
    @ApiModelProperty(required = true)
    @NotNull
    String uid;

    @ApiModelProperty(required = true)
    @NotNull
    String title;
    
    @ApiModelProperty(required = true)
    Date startdate;

    @ApiModelProperty(required = true)
    Date enddate;

    @ApiModelProperty(required = true)
    String contents;

    public Experience toEntity(){
        return Experience.builder()
                .uid(uid)
                .title(title)
                .contents(contents)
                .build();
    }

    @Builder
    public ExperienceCreateRequest(String uid, String title,String contents){
        this.uid = uid;
        this.title = title;
        this.contents = contents;
    }
}