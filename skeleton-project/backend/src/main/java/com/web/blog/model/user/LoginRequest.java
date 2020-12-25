package com.web.blog.model.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Valid
@Getter
@AllArgsConstructor
public class LoginRequest {

    @ApiModelProperty(required = true)
    @NotNull
    String uidOrEmail;

    @ApiModelProperty(required = true)
    @NotNull
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d$@$!%*#?&]{8,}$")
    String password;

    public User toEntity() {
        String regex = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";
        if (uidOrEmail.matches(regex)) {
            return User.builder().email(uidOrEmail).password(password).build();
        } else {
            return User.builder().uid(uidOrEmail).password(password).build();
        }
    }

}
