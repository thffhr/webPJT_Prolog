package com.web.blog.service.tag;

import com.web.blog.model.BasicResponse;
import com.web.blog.model.tag.TagCreateRequest;
import com.web.blog.model.tag.TagGetExperienceRequest;
import com.web.blog.model.tag.TagGetPortfolioRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface TagService {
     ResponseEntity<BasicResponse> createTag(final TagCreateRequest request) throws Exception;

     ResponseEntity<BasicResponse> getPortfolioByUidAndTagList(TagGetPortfolioRequest request) throws Exception;

     ResponseEntity<BasicResponse> getExperienceByUidAndTagList(TagGetExperienceRequest request) throws Exception;

}
