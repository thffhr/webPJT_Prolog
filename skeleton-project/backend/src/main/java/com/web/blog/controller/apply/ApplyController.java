package com.web.blog.controller.apply;

import javax.validation.Valid;

import com.web.blog.model.BasicResponse;
import com.web.blog.model.apply.ApplyAddOrDeleteERequest;
import com.web.blog.model.apply.ApplyAddOrDeletePRequest;
import com.web.blog.model.apply.ApplyCreateRequest;
import com.web.blog.model.apply.ApplyUpdateRequest;
import com.web.blog.service.apply.ApplyService;

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
public class ApplyController {

    @Autowired
    ApplyService applyService;

    @PostMapping("/apply")
    @ApiOperation(value = "새로운 지원목록을 생성")
    public ResponseEntity<BasicResponse> create(@Valid @RequestBody ApplyCreateRequest applyCreateRequest) {
        return applyService.createApply(applyCreateRequest);
    }

    @GetMapping("/apply")
    @ApiOperation(value = "지원목록 리스트를 리턴")
    public ResponseEntity<BasicResponse> readAll(@RequestParam String uid) {
        return applyService.readAllApply(uid);
    }

    @GetMapping("/apply/{apid}")
    @ApiOperation(value = "지원목록 하나를 리턴")
    public ResponseEntity<BasicResponse> readOne(@RequestParam String uid, @PathVariable int apid) {
        return applyService.readOneApply(uid, apid);
    }

    @PutMapping("/apply")
    @ApiOperation(value = "지원목록 수정")
    public ResponseEntity<BasicResponse> update(@RequestBody ApplyUpdateRequest applyUpdateRequest) {
        return applyService.updateApply(applyUpdateRequest);
    }

    @DeleteMapping("/apply")
    @ApiOperation(value = "지원목록 삭제")
    public ResponseEntity<BasicResponse> delete(@RequestParam String uid, @RequestParam int apid) {
        return applyService.deleteApply(uid, apid);
    }

    @GetMapping("/apply/inportfolio")
    @ApiOperation(value = "지원목록에 속해있는 프로젝트 목록")
    public ResponseEntity<BasicResponse> portfolioInApply(@RequestParam(required = true) String uid,
            @RequestParam(required = true) int apid) {
        return applyService.portfolioInApply(uid, apid);
    }

    @GetMapping("/apply/outportfolio")
    @ApiOperation(value = "지원목록에 속해있지 않은 프로젝트 목록")
    public ResponseEntity<BasicResponse> portfolioOutOfApply(@RequestParam(required = true) String uid,
            @RequestParam(required = true) int apid) {
        return applyService.portfolioOutOfApply(uid, apid);
    }

    @GetMapping("/apply/inexp")
    @ApiOperation(value = "지원목록에 속해있는 경험 목록")
    public ResponseEntity<BasicResponse> experienceInApply(@RequestParam(required = true) String uid,
            @RequestParam(required = true) int apid) {
        return applyService.experienceInApply(uid, apid);
    }

    @GetMapping("/apply/outexp")
    @ApiOperation(value = "지원목록에 속해있지 않은 경험 목록")
    public ResponseEntity<BasicResponse> experienceOutOfApply(@RequestParam(required = true) String uid,
            @RequestParam(required = true) int apid) {
        return applyService.experienceOutOfApply(uid, apid);
    }

    @GetMapping("/apply/search")
    @ApiOperation(value = "검색에 대한 지원목록 가져오기")
    public ResponseEntity<BasicResponse> findListBySearch(@RequestParam(required = true) String uid,
            @RequestParam(required = false) String searchTxt, @RequestParam(required = false) String period) {
        return applyService.findListBySearch(uid, searchTxt, period);
    }

    @PostMapping("/apply/addPortfolio")
    @ApiOperation(value = "지원목록에 포폴추가")
    public ResponseEntity<BasicResponse> addPortfolio(@RequestBody ApplyAddOrDeletePRequest request) {
        return applyService.addPortfolio(request);
    }

    @PostMapping("/apply/addExp")
    @ApiOperation(value = "지원목록에 경험추가")
    public ResponseEntity<BasicResponse> addExp(@RequestBody ApplyAddOrDeleteERequest request) {
        return applyService.addExp(request);
    }

    @DeleteMapping("/apply/deletePortfolio")
    @ApiOperation(value = "지원목록에서 포폴제거")
    public ResponseEntity<BasicResponse> deletePortfolio(@RequestBody ApplyAddOrDeletePRequest request) {
        return applyService.deletePortfolio(request);
    }

    @DeleteMapping("/apply/deleteExp")
    @ApiOperation(value = "지원목록에서 경험제거")
    public ResponseEntity<BasicResponse> deleteExp(@RequestBody ApplyAddOrDeleteERequest request) {
        return applyService.deleteExp(request);
    }

    // @GetMapping("/apply/{keyword}")
    // @ApiOperation("회사, 기간을 keyword로 검색")
    // public ResponseEntity<BasicResponse> searchApply(@PathVariable String
    // keyword) {
    // return applyService.searchApply(keyword);
    // }
}