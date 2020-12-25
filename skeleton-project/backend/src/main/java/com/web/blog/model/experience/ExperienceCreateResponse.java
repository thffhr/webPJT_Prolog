package com.web.blog.model.experience;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ExperienceCreateResponse {
    
    @ApiModelProperty(required = true)
    Experience experience;

    @ApiModelProperty(required = true)
    boolean clicked;

 
    @Builder
    public ExperienceCreateResponse(final Experience experience, final boolean clicked) {
        this.experience = experience;
        this.clicked = clicked;
    }
}