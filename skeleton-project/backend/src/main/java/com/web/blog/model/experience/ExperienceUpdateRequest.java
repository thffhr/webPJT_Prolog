package com.web.blog.model.experience;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ExperienceUpdateRequest {
    
    @Id
    @ApiModelProperty(required = true)
    @NotNull
    @Column(name = "exid")
    int id;

    @ApiModelProperty(required = true)
    @NotNull
    @Column(name = "uid")
    String uid;

    @ApiModelProperty(required = true)
    @NotNull
    @Column(name = "title")
    String title;
    
    @ApiModelProperty(required = true)
    @Column(name = "start_date")
    Date startdate;

    @ApiModelProperty(required = true)
    @Column(name = "end_date")
    Date enddate;

    @ApiModelProperty(required = true)
    @Column(name = "contents")
    String contents;

    public Experience toEntity(){
        return Experience.builder()
                .exid(id)
                .uid(uid)
                .title(title)
                .startdate(startdate)
                .enddate(enddate)
                .contents(contents)
                .build();
    }
}