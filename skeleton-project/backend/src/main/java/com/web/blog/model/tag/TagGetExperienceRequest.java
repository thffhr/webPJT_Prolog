package com.web.blog.model.tag;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TagGetExperienceRequest {

    @ApiModelProperty(required = true)
    @NotNull
    String uid;

    @ApiModelProperty(required = true)
    @NotNull
    List<Integer> tagList;

}