/*
package com.web.blog.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.web.blog.dao.mail.MailDao;
import com.web.blog.service.mail.MailServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.web.blog.controller.account.MailController;
import com.web.blog.model.BasicResponse;
import com.web.blog.model.mail.MailSendRequest;
import com.web.blog.service.mail.MailService;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MailControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    MailDao mailDao;
    
    @Autowired
    MailServiceImpl mailService;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }

    @AfterEach
    public void tearDown() throws Exception {
        mailDao.deleteAll();
    }

    @Test

    @DisplayName("Mail 등록 테스트")
    public void Mail_save() throws Exception {
        //given
        String tomail = "wotjd4305@naver.com";
        String subject = "제목 ㅎㅇ";

        MailSendRequest mailSendRequest = new MailSendRequest(tomail, subject);

        String url = "http://localhost:" + port + "/email/auth";

        //when
        ResultActions resultActions = mvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(mailSendRequest)))
                .andExpect(status().isOk());
        //then
        ResponseEntity<BasicResponse> basicResponseResponseEntity = mailService.sendMail(mailSendRequest);

        assertThat(basicResponseResponseEntity.getBody().status).isEqualTo(true);
        assertThat(basicResponseResponseEntity.getBody().data).isEqualTo("이메일 보내기 성공");
    }

    @Test
    @DisplayName("Mail 등록 실패 테스트")
    public void Mail_save실패() throws Exception {
        //given
        String tomail = "";
        String subject = "제목 ㅎㅇ";

        MailSendRequest mailSendRequest = new MailSendRequest(tomail, subject);

        String url = "http://localhost:" + port + "/email/auth";

        //when
        mvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(mailSendRequest)))
                .andExpect(status().isNotFound());

        ResponseEntity<BasicResponse> basicResponseResponseEntity = mailService.sendMail(mailSendRequest);

        //then
        assertThat(basicResponseResponseEntity.getBody().status).isEqualTo(true);
        assertThat(basicResponseResponseEntity.getBody().data).isEqualTo("이메일 보내기 성공");
    }
}
*/
