package com.web.blog.model.tag;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.Column;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Valid
@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TagCreateRequest {

    @ApiModelProperty(required = true)
    @NotNull
    @Column(name = "tag_name")
    String tagName;

    public Tag toEntity() {
        return Tag.builder().tagName(tagName).build();
    }
}
