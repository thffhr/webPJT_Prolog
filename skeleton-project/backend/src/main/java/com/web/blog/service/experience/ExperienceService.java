package com.web.blog.service.experience;

import com.web.blog.model.BasicResponse;
import com.web.blog.model.experience.ETagCreateRequest;
import com.web.blog.model.experience.ExperienceCreateRequest;
import com.web.blog.model.experience.ExperienceUpdateRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface ExperienceService {

    ResponseEntity<BasicResponse> createExperience(ExperienceCreateRequest request);

    ResponseEntity<BasicResponse> updateExperience(ExperienceUpdateRequest request);

    ResponseEntity<BasicResponse> deleteExperience(int exid);

    ResponseEntity<BasicResponse> readAll(String uid);

    ResponseEntity<BasicResponse> getTagAll(String uid);

    ResponseEntity<BasicResponse> createETag(ETagCreateRequest request) throws Exception;

    ResponseEntity<BasicResponse> deleteTagFromEx(int tid, int exid);

}