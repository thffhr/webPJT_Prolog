package com.web.blog.service.apply;

import com.web.blog.model.BasicResponse;
import com.web.blog.model.apply.ApplyAddOrDeleteERequest;
import com.web.blog.model.apply.ApplyAddOrDeletePRequest;
import com.web.blog.model.apply.ApplyCreateRequest;
import com.web.blog.model.apply.ApplyUpdateRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface ApplyService {
    ResponseEntity<BasicResponse> createApply(ApplyCreateRequest ApplyCreateRequest);

    ResponseEntity<BasicResponse> readAllApply(String uid);

    ResponseEntity<BasicResponse> readOneApply(String uid, int apid);

    ResponseEntity<BasicResponse> updateApply(ApplyUpdateRequest applyUpdateRequest);

    ResponseEntity<BasicResponse> deleteApply(String uid, int apid);

    ResponseEntity<BasicResponse> portfolioInApply(String uid, int apid);

    ResponseEntity<BasicResponse> portfolioOutOfApply(String uid, int apid);

    ResponseEntity<BasicResponse> experienceInApply(String uid, int apid);

    ResponseEntity<BasicResponse> experienceOutOfApply(String uid, int apid);

    ResponseEntity<BasicResponse> findListBySearch(String uid, String searchTxt, String period);

    ResponseEntity<BasicResponse> addPortfolio(ApplyAddOrDeletePRequest request);

    ResponseEntity<BasicResponse> addExp(ApplyAddOrDeleteERequest request);

    ResponseEntity<BasicResponse> deletePortfolio(ApplyAddOrDeletePRequest request);

    ResponseEntity<BasicResponse> deleteExp(ApplyAddOrDeleteERequest request);

}