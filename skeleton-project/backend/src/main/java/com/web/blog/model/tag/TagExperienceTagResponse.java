package com.web.blog.model.tag;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;


@NoArgsConstructor
@Getter
@Setter
public class TagExperienceTagResponse {

    @ApiModelProperty(required = true)
    @NotNull
    int tid;

    @ApiModelProperty(required = true)
    @NotNull
    String tagName;

    @ApiModelProperty(required = true)
    @NotNull
    boolean state;
    
    @Builder
    public TagExperienceTagResponse(int tid,String tagName, boolean state){
        this.state = state;
        this.tid = tid;
        this.tagName = tagName;
    }

    @Override
    public boolean equals(Object o) {
        TagExperienceTagResponse temp = (TagExperienceTagResponse)o;
        if (this.tid == temp.tid) return true;
        else return false;
    }

    @Override
    public int hashCode() {
        return (tid+"").hashCode();
    }
}
