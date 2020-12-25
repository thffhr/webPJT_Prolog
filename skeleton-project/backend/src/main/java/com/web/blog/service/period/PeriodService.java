package com.web.blog.service.period;

import com.web.blog.model.BasicResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface PeriodService {

      // 기간 가져오기
      ResponseEntity<BasicResponse> findAll();

}