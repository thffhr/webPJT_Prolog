package com.web.blog.service.tag;

import com.web.blog.dao.experience.ExperienceDao;
import com.web.blog.dao.portfolio.PortfolioDao;
import com.web.blog.dao.tag.TagDao;
import com.web.blog.model.BasicResponse;
import com.web.blog.model.experience.Experience;
import com.web.blog.model.portfolio.Portfolio;
import com.web.blog.model.tag.Tag;
import com.web.blog.model.tag.TagCreateRequest;
import com.web.blog.model.tag.TagGetExperienceRequest;
import com.web.blog.model.tag.TagGetPortfolioRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service("tagService")
public class TagServiceImpl implements TagService {

    @Autowired
    TagDao tagDao;

    @Autowired
    PortfolioDao portfolioDao;

    @Autowired
    ExperienceDao experienceDao;

    // 요청과 응답
    final BasicResponse result = new BasicResponse();
    ResponseEntity<BasicResponse> response = null;

    @Override
    public ResponseEntity<BasicResponse> createTag(TagCreateRequest request) throws Exception {
        final Optional<Tag> tagOpt = tagDao.getTagByTagName(request.getTagName());

        if (tagOpt.isPresent()) {
            result.status = false;
            result.data = "fail";
            result.object = tagOpt.get().toResEntity();// 중복되면 그놈 쓰세요!
            response = new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            Tag resultOpt = tagDao.save(request.toEntity());

            result.status = true;
            result.data = "success";
            result.object = resultOpt.toResEntity(); // 중복안되서 새로 만들어진 애

            response = new ResponseEntity<>(result, HttpStatus.OK);
            // response = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            return response;
        }

        return response;
    }

    @Override
    public ResponseEntity<BasicResponse> getPortfolioByUidAndTagList(TagGetPortfolioRequest request) throws Exception {

        HashSet<Portfolio> hs = new HashSet<>();

        for (int tagId : request.getTagList()) {
            List<Portfolio> pList = portfolioDao.getPortfolioByTag(request.getUid(), tagId);
            for (Portfolio p : pList) {
                hs.add(p);
            }
        }

        if (!hs.isEmpty()) {
            result.status = true;
            result.data = "success";
            result.object = hs;
            response = new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            result.status = false;
            result.data = "fail";
            result.object = null;
            response = new ResponseEntity<>(result, HttpStatus.OK);
            return response;
        }

        return response;
    }

    @Override
    public ResponseEntity<BasicResponse> getExperienceByUidAndTagList(TagGetExperienceRequest request)
            throws Exception {
        HashSet<Experience> hs = new HashSet<>();

        for (int tagId : request.getTagList()) {
            List<Experience> pList = experienceDao.getExperienceByTag(request.getUid(), tagId);
            for (Experience p : pList) {
                hs.add(p);
            }
        }

        if (!hs.isEmpty()) {
            result.status = true;
            result.data = "success";
            result.object = hs;
            response = new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            result.status = false;
            result.data = "fail";
            result.object = null;
            response = new ResponseEntity<>(result, HttpStatus.OK);
            return response;
        }

        return response;
    }

}