package com.web.blog.controller.experience;

import javax.validation.Valid;

import com.web.blog.model.BasicResponse;
import com.web.blog.model.experience.ETagCreateRequest;
import com.web.blog.model.experience.ExperienceCreateRequest;
import com.web.blog.model.experience.ExperienceUpdateRequest;
import com.web.blog.service.experience.ExperienceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
public class ExperienceController {

    @Autowired
    ExperienceService experienceService;

    @PostMapping("/experience")
    @ApiOperation(value = "유저의 경험 생성")
    public ResponseEntity<BasicResponse> createExperience(@Valid @RequestBody ExperienceCreateRequest request) {
        return experienceService.createExperience(request);
    }

    @PutMapping("/experience")
    @ApiOperation(value = "유저의 경험 수정")
    public ResponseEntity<BasicResponse> updateExperience(@Valid @RequestBody ExperienceUpdateRequest request) {
        return experienceService.updateExperience(request);
    }

    @DeleteMapping("/experience/{exid}")
    @ApiOperation(value = "유저의 경험 삭제")
    public ResponseEntity<BasicResponse> deleteExperience(@PathVariable("exid") final int exid) {
        return experienceService.deleteExperience(exid);
    }

    @GetMapping("/experience/all")
    @ApiOperation(value = "유저의 전체 경험 목록")
    public ResponseEntity<BasicResponse> readAll(@RequestParam(required = true) final String uid) {
        return experienceService.readAll(uid);
    }

    @GetMapping("/experience/Tags")
    @ApiOperation(value = "유저의 경험이 가지고 있는 태그 모두 출력")
    public ResponseEntity<BasicResponse> tagAll(@RequestParam(required = true) final String uid) {
        return experienceService.getTagAll(uid);
    }

    @PostMapping("/experience/tag")
    @ApiOperation(value = "ex_tag 테이블에 새로운 경험 태그 추가")
    public ResponseEntity<BasicResponse> createPortfolio(@Valid @RequestBody ETagCreateRequest request)
            throws Exception {

        return experienceService.createETag(request);
    }

    @DeleteMapping("/experience/{tid}/{exid}")
    @ApiOperation(value = "유저의 경험 삭제")
    public ResponseEntity<BasicResponse> deleteTagFromEx(@PathVariable("tid") final int tid,
            @PathVariable("exid") final int exid) {
        return experienceService.deleteTagFromEx(tid, exid);
    }

}
