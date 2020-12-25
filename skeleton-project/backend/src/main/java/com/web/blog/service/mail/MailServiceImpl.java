package com.web.blog.service.mail;

import java.util.Optional;
import java.util.Random;

import com.web.blog.dao.mail.MailDao;
import com.web.blog.model.BasicResponse;
import com.web.blog.model.mail.Mail;
import com.web.blog.model.mail.MailInfoSendRequest;
import com.web.blog.model.mail.MailSendRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service("mailService")
public class MailServiceImpl implements MailService {

    @Autowired
    MailDao mailDao;

    @Autowired
    private JavaMailSender mailSender;
    private final String from = "wotjd4315@gmail.com";

    //
    BasicResponse result = new BasicResponse();
    ResponseEntity<BasicResponse> response = null;

    public String MailMakeCode() {

        final int codeLength = 8;
        final StringBuffer resultCode = new StringBuffer();
        final Random random = new Random(System.currentTimeMillis());

        final char[] characterTable = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P',
                'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0' };
        for (int i = 0; i < codeLength; i++) {
            resultCode.append(characterTable[random.nextInt(characterTable.length)]);
        }

        return resultCode.toString();
    }

    @Override
    public ResponseEntity<BasicResponse> sendMail(final MailSendRequest request) throws Exception {
        try {

            final String AuthCode = MailMakeCode();// 랜덤 인증코드 생성 필요
            // final String AuthLink = "http://localhost:3000/#/EmailCompare/" + AuthCode +
            // "/" + request.getToEmail();
            // ec2 설정시
            final String AuthLink = "http://i3a605.p.ssafy.io/#/EmailCompare/" + AuthCode + "/" + request.getToEmail();
            final String AuthSubject = "서비스 인증 메일";
            final SimpleMailMessage message = new SimpleMailMessage();

            // 이메일 보내기
            message.setFrom(from);
            message.setTo(request.getToEmail());
            message.setSubject(AuthSubject + " : " + request.getSubject());
            message.setText("고객님의 이메일 인증링크 [ " + AuthLink + " ] 입니다.");
            mailSender.send(message);

            // 인증테이블에 추가
            final Optional<Mail> OptTemp = mailDao.findMailByEmail(request.getToEmail());
            if (OptTemp.isPresent()) { // 이메일 있으면 업데이트
                final Mail Opt = mailDao.getOne(OptTemp.get().getId());
                mailDao.save(request.toEntity(AuthCode, Opt.getId()));
            } else {// 없으면 그냥 생성
                mailDao.save(request.toEntity(AuthCode));
            }

            result.status = true;
            result.data = "이메일 보내기 성공";
            response = new ResponseEntity<>(result, HttpStatus.OK);

        } catch (final Exception e) {
            e.printStackTrace();
            result.status = false;
            result.data = "이메일 보내기 실패";
            response = new ResponseEntity<>(result, HttpStatus.NOT_ACCEPTABLE);
            return response;
        }

        return response;
    }

    @Override
    public ResponseEntity<BasicResponse> compareEmailCode(final String code, final String email) {

        final Optional<Mail> Opt = mailDao.findMailByEmailAndCode(email, code);

        if (Opt.isPresent()) { // 인증코드가 일치

            // 인증테이블에 추가
            final Mail OptTemp = mailDao.getOne(Opt.get().getId());
            OptTemp.setAuthorized(1);
            mailDao.save(OptTemp);

            result.status = true;
            result.data = "이메일 인증 성공";
            response = new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            result.status = false;
            result.data = "이메일 인증 실패";
            response = new ResponseEntity<>(result, HttpStatus.OK);
            return response;
        }
        return response;
    }

    @Override
    public ResponseEntity<BasicResponse> deleteEmailCode(final String email) {
        final Optional<Mail> Opt = mailDao.findMailByEmail(email);

        // 1은 인증된 데이터
        if (Opt.isPresent() && Opt.get().getAuthorized() == 1) {

            // 인증테이블에 추가
            mailDao.deleteById(Opt.get().getId());
            result.status = true;
            result.data = "이메일 인증코드 테이블 삭제 성공";
            response = new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            result.status = false;
            result.data = "이메일 인증코드 테이블 삭제 실패";
            response = new ResponseEntity<>(result, HttpStatus.OK);
            return response;
        }

        return response;
    }

    @Override
    public ResponseEntity<BasicResponse> sendInfoMail(final MailInfoSendRequest request) {
        try {

            // final String AuthCode = MailMakeCode();// 랜덤 인증코드 생성 필요
            final String AuthSubject = "유저 정보 찾기 서비스";
            final SimpleMailMessage message = new SimpleMailMessage();

            // 이메일 보내기
            message.setFrom(from);
            message.setTo(request.getToEmail());
            message.setSubject(AuthSubject + " : " + request.getSubject());
            message.setText("고객님이 찾고자 하는 정보는  [ " + request.getText() + " ] 입니다.");
            mailSender.send(message);

            result.status = true;
            result.data = "이메일 보내기 성공";
            response = new ResponseEntity<>(result, HttpStatus.OK);

        } catch (final Exception e) {
            e.printStackTrace();
            result.status = false;
            result.data = "이메일 보내기 실패";
            response = new ResponseEntity<>(result, HttpStatus.NOT_ACCEPTABLE);
            return response;
        }

        return response;
    }

}