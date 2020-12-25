package com.web.blog.controller.portfolio;

import javax.validation.Valid;

import com.web.blog.model.BasicResponse;
import com.web.blog.model.portfolio.PTagCreateRequest;
import com.web.blog.model.portfolio.PortfolioRequest;
import com.web.blog.model.portfolio.PortfolioUpdateRequest;
import com.web.blog.service.portfolio.PortfolioService;
import com.web.blog.service.tag.TagService;

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
public class PortfolioController {

    @Autowired
    PortfolioService portfolioService;

    @Autowired
    TagService tagService;

    @GetMapping("/portfolio/all")
    @ApiOperation(value = "유저의 전체 포트폴리오 목록(태그포함, 파일리스트 불포함)")
    public ResponseEntity<BasicResponse> readAll(@RequestParam(required = true) final String uid) {
        return portfolioService.readAll(uid);
    }

    @GetMapping("/portfolio/{pid}")
    @ApiOperation(value = "특정 포트폴리오의 값을 보여줌")
    public ResponseEntity<BasicResponse> readOne(@PathVariable("pid") int pid) {
        return portfolioService.readOne(pid);
    }

    @GetMapping("/portfolio/Tags")
    @ApiOperation(value = "유저의 프로젝트가 가지고 있는 태그 모두 출력")
    public ResponseEntity<BasicResponse> tagAll(@RequestParam(required = true) final String uid) {
        return portfolioService.getTagAll(uid);
    }

    @PostMapping("/portfolio")
    @ApiOperation(value = "새로운 포트폴리오 생성")
    public ResponseEntity<BasicResponse> createPortfolio(@Valid @RequestBody PortfolioRequest request)
            throws Exception {
        return portfolioService.createPortfolio(request);
    }

    @PutMapping("/portfolio")
    @ApiOperation(value = "포트폴리오 수정하기")
    public ResponseEntity<BasicResponse> updatePortfolio(@Valid @RequestBody PortfolioUpdateRequest updateRequest)
            throws Exception {

        return portfolioService.updatePortfolio(updateRequest);
    }

    @DeleteMapping("/portfolio/{pid}")
    @ApiOperation(value = "포트폴리오 삭제")
    public ResponseEntity<BasicResponse> deleteExperience(@PathVariable("pid") final int pid) {
        return portfolioService.deletePortfolio(pid);
    }

    // @PostMapping("/portfolio/tag")
    // @ApiOperation(value = "p_tag 테이블에 새로운 포트폴리오 태그 추가")
    // public ResponseEntity<BasicResponse> createPortfolio(@Valid @RequestBody
    // PTagCreateRequest request)
    // throws Exception {

    // return portfolioService.createPTag(request);
    // }

    @PostMapping("/portfolio/tag")
    @ApiOperation(value = "포트폴리오에 태그 추가")
    public ResponseEntity<BasicResponse> createPortfolioTag(@RequestParam(required = true) int pid,
            @RequestParam(required = true) String tag) {
        return portfolioService.createPTag(pid, tag);
    }

    @DeleteMapping("/tag")
    @ApiOperation(value = "포트폴리오에 태그 연결해제")
    public ResponseEntity<BasicResponse> deletePtag(@RequestBody PTagCreateRequest request) {
        return portfolioService.deletePtag(request);
    }
}
