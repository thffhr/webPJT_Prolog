package com.web.blog.controller.period;

import com.web.blog.model.BasicResponse;
import com.web.blog.service.period.PeriodService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
public class PeriodController {
    @Autowired
    PeriodService periodService;

    @GetMapping("/period")
    @ApiOperation(value = "기간 가져오기")
    public ResponseEntity<BasicResponse> findAll() {
        return periodService.findAll();
    }


}