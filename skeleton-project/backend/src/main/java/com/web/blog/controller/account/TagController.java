package com.web.blog.controller.account;

import com.web.blog.model.BasicResponse;
import com.web.blog.model.tag.TagCreateRequest;
import com.web.blog.model.tag.TagGetExperienceRequest;
import com.web.blog.model.tag.TagGetPortfolioRequest;

import com.web.blog.service.tag.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
public class TagController {

    @Autowired
    TagService tagService;

    @PostMapping("/tag")
    @ApiOperation(value = "(없어도 됨..)작성한 태그가 있는지 확인해서 있으면 fail, 없으면 success(새로생성)")
    public ResponseEntity<BasicResponse> readAll(@Valid @RequestBody TagCreateRequest request) throws Exception {
        return tagService.createTag(request);
    }

    @PostMapping("/tag/portfolios")
    @ApiOperation(value = "유저 id와 태그 리스트로 프로젝트 출력")
    public ResponseEntity<BasicResponse> getPortfolioByUidAndTagList(@Valid @RequestBody TagGetPortfolioRequest request)
            throws Exception {

        return tagService.getPortfolioByUidAndTagList(request);
    }

    @PostMapping("/tag/experiences")
    @ApiOperation(value = "유저 id와 태그 리스트로 경험 출력")
    public ResponseEntity<BasicResponse> getExperienceByUidAndTagList(
            @Valid @RequestBody TagGetExperienceRequest request) throws Exception {

        return tagService.getExperienceByUidAndTagList(request);
    }

}
