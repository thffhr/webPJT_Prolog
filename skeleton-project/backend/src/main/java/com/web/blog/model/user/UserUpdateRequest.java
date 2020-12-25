package com.web.blog.model.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Valid
@ToString
@Getter
@NoArgsConstructor
public class UserUpdateRequest {

    @ApiModelProperty(required = true)
    @NotNull
    String uid;

    @ApiModelProperty(required = true)
    @NotNull
    String email;

    @ApiModelProperty(required = true)
    @NotNull
    String nickname;

    @ApiModelProperty(required = true)
    @NotNull
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d$@$!%*#?&]{8,}$")
    String password;

    @JsonIgnore
    byte[] picByte;

    public User toEntity(byte[] picByte) {
        return User.builder().uid(uid).nickname(nickname).email(email).password(password).picByte(picByte).build();
    }

}
