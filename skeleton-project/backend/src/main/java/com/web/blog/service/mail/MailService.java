package com.web.blog.service.mail;

import com.web.blog.model.BasicResponse;
import com.web.blog.model.mail.MailInfoSendRequest;
import com.web.blog.model.mail.MailSendRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface MailService {

    // 이메일 랜덤 코드 만들기.
    public String MailMakeCode();

    // 메일 보내기
    ResponseEntity<BasicResponse> sendMail(MailSendRequest request) throws Exception;

    // 이메일 인증비교
    ResponseEntity<BasicResponse> compareEmailCode(final String code, final String email);

    // 이메일 인증코드 삭제
    ResponseEntity<BasicResponse> deleteEmailCode(final String email);

    // 이메일로 정보(id,pw)찾기 보내기
    ResponseEntity<BasicResponse> sendInfoMail(MailInfoSendRequest request);

}