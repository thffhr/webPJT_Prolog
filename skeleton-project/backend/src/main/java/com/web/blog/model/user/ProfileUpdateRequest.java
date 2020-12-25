package com.web.blog.model.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Valid
@ToString
@Getter
@Setter
// @AllArgsConstructor
@NoArgsConstructor
public class ProfileUpdateRequest {

    @ApiModelProperty(required = true)
    @NotNull
    byte[] picByte;

    String uid;

    public Profile toEntity(String uid, byte[] picByte) {
        return Profile.builder().uid(uid).picByte(picByte).build();
    }

    public ProfileUpdateRequest(String uid, byte[] picByte) {
        this.uid = uid;
        this.picByte = picByte;
    }

}
