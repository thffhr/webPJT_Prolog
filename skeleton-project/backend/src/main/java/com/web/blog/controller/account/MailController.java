package com.web.blog.controller.account;

import javax.validation.Valid;

import com.web.blog.model.mail.MailInfoSendRequest;
import com.web.blog.model.mail.MailSendRequest;
import com.web.blog.service.mail.MailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin(origins = { "*" }, maxAge = 6000)
public class MailController {

    @Autowired
    MailService mailService;

    @PutMapping("/email/auth")
    @ApiOperation(value = "이메일 인증 보내기, 인증코드 만들기, 인증 테이블 생성")
    public Object sendMail(@Valid @RequestBody MailSendRequest request) throws Exception {

        return mailService.sendMail(request);
    }

    @GetMapping("/email/compare-code")
    @ApiOperation(value = "이메일 인증코드 비교")
    public Object compareEmailCode(@RequestParam(required = true) final String code,
            @RequestParam(required = true) final String email) {

        return mailService.compareEmailCode(code, email);
    }

    @DeleteMapping("/email/{email}")
    @ApiOperation(value = "이메일 인증코드 테이블 삭제")
    public Object deleteEmailCode(@PathVariable final String email) {

        return mailService.deleteEmailCode(email);
    }

    @PutMapping("/email/sendInfo")
    @ApiOperation(value = "이메일로 찾고자 하는 정보 보내기(id,pw..)")
    public Object sendInfoMail(@Valid @RequestBody MailInfoSendRequest request) throws Exception {

        return mailService.sendInfoMail(request);
    }

}