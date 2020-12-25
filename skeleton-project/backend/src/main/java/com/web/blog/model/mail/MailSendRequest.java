package com.web.blog.model.mail;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Valid
@ToString
@Getter
@Setter
@AllArgsConstructor
public class MailSendRequest {

    @ApiModelProperty(required = true)
    @NotNull
    // @Pattern(regexp = "^[a-zA-Z1-9]+@([\\w-[+\\.)+[\\w-]{4,}$")
    String toEmail;

    @ApiModelProperty(required = true)
    @NotNull
    String subject;

    /*
     * @ApiModelProperty(required = true)
     * 
     * @NotNull String text;
     */

    public Mail toEntity(String AuthCode) {
        return Mail.builder().email(toEmail).code(AuthCode).build();
    }

    public Mail toEntity(String AuthCode, Long id) {
        return Mail.builder().id(id).email(toEmail).code(AuthCode).build();
    }

}
