package com.web.blog.service.experience;

import java.util.*;

import com.web.blog.dao.experience.ETagDao;
import com.web.blog.dao.experience.ExperienceDao;
import com.web.blog.model.BasicResponse;
import com.web.blog.model.experience.ETag;
import com.web.blog.model.experience.ETagCreateRequest;
import com.web.blog.model.experience.Experience;
import com.web.blog.model.experience.ExperienceCreateRequest;
import com.web.blog.model.experience.ExperienceFindTagResponse;
import com.web.blog.model.experience.ExperienceUpdateRequest;
import com.web.blog.model.join.ExperienceTag;
import com.web.blog.model.tag.Tag;
import com.web.blog.model.tag.TagExperienceTagResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service("experieneceService")
public class ExperienceServiceImpl implements ExperienceService {

    @Autowired
    ExperienceDao experienceDao;

    @Autowired
    ETagDao etagDao;

    // @Autowired
    // private ExperienceTagDao experienceTagDao;

    // 요청과 응답
    final BasicResponse result = new BasicResponse();
    ResponseEntity<BasicResponse> response = null;

    @Override
    public ResponseEntity<BasicResponse> createExperience(final ExperienceCreateRequest request) {

        try {
            final Experience opt = experienceDao.save(request.toEntity());
            result.status = true;
            result.data = "경험 생성 성공";
            result.object = opt;
        } catch (final Exception e) {
            result.status = false;
            result.data = "경험 생성 실패";
            result.object = null;
        }
        response = new ResponseEntity<>(result, HttpStatus.OK);
        return response;
    }

    @Override
    public ResponseEntity<BasicResponse> updateExperience(final ExperienceUpdateRequest request) {
        try {
            final Experience opt = experienceDao.save(request.toEntity());
            result.status = true;
            result.data = "경험 수정 성공";
            result.object = opt;
        } catch (final Exception e) {
            result.status = false;
            result.data = "경험 수정 실패";
            result.object = null;
        }
        response = new ResponseEntity<>(result, HttpStatus.OK);
        return response;
    }

    @Override
    public ResponseEntity<BasicResponse> deleteExperience(final int exid) {
        try {
            experienceDao.deleteById(exid);
            result.status = true;
            result.data = "경험 삭제 성공";
            result.object = null;
        } catch (final Exception e) {
            result.status = false;
            result.data = "경험 삭제 실패";
            result.object = null;
        }
        response = new ResponseEntity<>(result, HttpStatus.OK);
        return response;
    }

    @Override
    public ResponseEntity<BasicResponse> readAll(String uid) {

        ArrayList<Experience> list = experienceDao.findExperienceByUid(uid);
        ArrayList<ExperienceFindTagResponse> resList = new ArrayList<>();

        for (Experience e : list) {
            ArrayList<Tag> tags = new ArrayList<>();
            for (ExperienceTag et : e.getExperienceTags()) {
                tags.add(et.getTag());
            }
            resList.add(ExperienceFindTagResponse.builder().uid(e.getUid()).exid(e.getExid()).title(e.getTitle())
                    .startdate(e.getStartdate()).enddate(e.getEnddate()).contents(e.getContents()).tags(tags)
                    .clicked(false).build());
        }

        if (!list.isEmpty()) {
            result.status = true;
            result.data = "경험 리스트 성공";
            result.object = resList;
            response = new ResponseEntity<BasicResponse>(result, HttpStatus.OK);
        } else {
            result.status = false;
            result.data = "경험 리스트 실패(비어있거나 실패)";
            result.object = null;
            response = new ResponseEntity<BasicResponse>(result, HttpStatus.OK);
            return response;
        }
        return response;
    }

    @Override
    public ResponseEntity<BasicResponse> getTagAll(String uid) {

        List<Experience> list = experienceDao.findExperienceByUid(uid);
        HashSet<TagExperienceTagResponse> hs = new HashSet<>();

        // 해시셋에 넣고 중복제거.
        for (Experience i : list) {
            List<ExperienceTag> experienceTags = i.getExperienceTags();
            for (ExperienceTag j : experienceTags) {
                hs.add(TagExperienceTagResponse.builder().tid(j.getTag().getTid()).tagName(j.getTag().getTagName())
                        .state(false).build());

            }
        }

        if (!list.isEmpty()) {
            result.status = true;
            result.data = "success";
            result.object = hs;
        } else {
            result.status = false;
            result.data = "fail";
            result.object = null;
        }
        response = new ResponseEntity<BasicResponse>(result, HttpStatus.OK);
        return response;
    }

    @Override
    public ResponseEntity<BasicResponse> createETag(ETagCreateRequest request) throws Exception {

        // p_tag 테이블에서 pid, uid 가 같으면 중복
        final Optional<ETag> ptagOpt = etagDao.getTagByExidAndTid(request.getExid(), request.getTid());

        if (ptagOpt.isPresent()) {
            result.status = false;
            result.data = "경험 태그 생성 실패(중복, 실패)";
            result.object = ptagOpt.get().toResEntity();// 중복되면 그놈 쓰세요! -> 중복되면 추가할 수 없음
            response = new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            ETag resultOpt = etagDao.save(request.toEntity());

            result.status = true;
            result.data = "경험 태그 생성 성공";
            result.object = resultOpt.toResEntity(); // 중복안되서 새로 만들어진 애

            response = new ResponseEntity<>(result, HttpStatus.OK);
            return response;
        }

        return response;
    }

    @Override
    public ResponseEntity<BasicResponse> deleteTagFromEx(int tid, int exid) {
        try {
            // etagDao.deleteByExidAndTid(request.getExid(), request.getTid());
            Optional<ETag> opt = etagDao.findByExidAndTid(exid, tid);
            etagDao.deleteById(opt.get().getExtid());
            result.status = true;
            result.data = "경험에서 태그 삭제 성공";
            result.object = null;
            response = new ResponseEntity<>(result, HttpStatus.OK);

        } catch (Exception e) {
            result.status = false;
            result.data = "경험 태그 삭제 실패";
            result.object = null;
            response = new ResponseEntity<>(result, HttpStatus.OK);
        }
        return response;
    }

}
