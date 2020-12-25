package com.web.blog.service.portfolio;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import com.web.blog.dao.portfolio.FileDAO;
import com.web.blog.dao.portfolio.PTagDao;
import com.web.blog.dao.portfolio.PortfolioDao;
import com.web.blog.dao.tag.TagDao;
import com.web.blog.model.BasicResponse;
import com.web.blog.model.join.PortfolioTag;
import com.web.blog.model.portfolio.PTag;
import com.web.blog.model.portfolio.PTagCreateRequest;
import com.web.blog.model.portfolio.Portfolio;
import com.web.blog.model.portfolio.PortfolioRequest;
import com.web.blog.model.portfolio.PortfolioUpdateRequest;
import com.web.blog.model.portfolio.UploadFile;
import com.web.blog.model.portfolio.PortfolioTags;
import com.web.blog.model.portfolio.PortfolioTagsFiles;
import com.web.blog.model.tag.Tag;
import com.web.blog.model.tag.TagCreateRequest;
import com.web.blog.model.tag.TagPortfolioTagResponse;
import com.web.blog.property.FileUploadProperties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service("portfolioService")
public class PortfolioServiceImpl implements PortfolioService {

    @Autowired
    PortfolioDao portfolioDao;

    @Autowired
    PTagDao ptagDao;

    @Autowired
    TagDao tagDao;

    // @Autowired
    // private PortfolioTagDao portfolioTagDao;
    @Autowired
    FileDAO fileDAO;

    private Path fileLocation;

    public PortfolioServiceImpl(FileUploadProperties prop) {
        this.fileLocation = Paths.get(prop.getUploadDir()).toAbsolutePath().normalize();
    }

    // 요청과 응답
    final BasicResponse result = new BasicResponse();
    ResponseEntity<BasicResponse> response = null;

    @Override
    public ResponseEntity<BasicResponse> readAll(final String uid) {

        List<Portfolio> list = portfolioDao.findPortfolioByUid(uid);
        List<PortfolioTags> ptsList = new ArrayList<>();

        for (Portfolio p : list) {
            List<PortfolioTag> ptList = p.getPortfolioTags();
            List<Tag> tagList = new ArrayList<>();
            for (PortfolioTag pt : ptList) {
                Tag tag = pt.getTag();
                tagList.add(tag);
            }
            ptsList.add(PortfolioTags.builder(p.getPid(), p.getUid(), p.getTitle(), p.getStartDate(), p.getStartDate(),
                    p.getContents(), tagList).build());
        }

        if (!list.isEmpty()) {
            result.status = true;
            result.data = "포트폴리오 리스트 성공";
            result.object = ptsList;
            response = new ResponseEntity<BasicResponse>(result, HttpStatus.OK);
        } else {
            result.status = false;
            result.data = "포트폴리오 리스트 실패(비어있거나 실패)";
            result.object = null;
            response = new ResponseEntity<BasicResponse>(result, HttpStatus.OK);
            return response;
        }
        return response;
    }

    @Override
    public ResponseEntity<BasicResponse> getTagAll(String uid) {

        List<Portfolio> list = portfolioDao.findPortfolioByUid(uid);
        HashSet<TagPortfolioTagResponse> hs = new HashSet<>();

        // 해시셋에 넣고 중복제거.
        for (Portfolio i : list) {
            List<PortfolioTag> portfolioTags = i.getPortfolioTags();
            for (PortfolioTag j : portfolioTags) {
                hs.add(TagPortfolioTagResponse.builder().tid(j.getTag().getTid()).tagName(j.getTag().getTagName())
                        .state(false).build());
            }
        }

        if (!list.isEmpty()) {
            result.status = true;
            result.data = "태그 리스트 성공";
            result.object = hs;
            response = new ResponseEntity<BasicResponse>(result, HttpStatus.OK);
        } else {
            result.status = false;
            result.data = "태그 리스트 실패";
            result.object = null;
            response = new ResponseEntity<BasicResponse>(result, HttpStatus.OK);
            return response;
        }

        return response;
    }

    @Override
    public ResponseEntity<BasicResponse> createPortfolio(PortfolioRequest request) {
        try {
            final Portfolio opt = portfolioDao.save(request.toEntity());
            result.status = true;
            result.data = "포트폴리오 생성 성공";
            result.object = opt;
        } catch (final Exception e) {
            result.status = false;
            result.data = "포트폴리오 생성 실패";
            result.object = null;
        }
        response = new ResponseEntity<>(result, HttpStatus.OK);
        return response;
    }

    @Override
    public ResponseEntity<BasicResponse> updatePortfolio(final PortfolioUpdateRequest updaterequest) {
        try {
            final Portfolio opt = portfolioDao.save(updaterequest.toEntity());
            result.status = true;
            result.data = "포트폴리오 수정 성공";
            result.object = opt;
        } catch (final Exception e) {
            result.status = false;
            result.data = "포트폴리오 수정 실패";
            result.object = null;
        }
        response = new ResponseEntity<>(result, HttpStatus.OK);
        return response;
    }

    @Override
    public ResponseEntity<BasicResponse> deletePortfolio(int pid) {

        try {
            Portfolio portfolio = portfolioDao.findPortfolioByPid(pid);

            // 프로젝트에 포함되어있는 파일 삭제
            // - pid가 pid인 파일리스트를 불러와서 for문으로 filename으로 파일스토리지에서 삭제하고 db에서 삭제
            List<UploadFile> fileList = portfolio.getFiles();

            for (UploadFile uploadFile : fileList) {
                String fileName = uploadFile.getFileName();
                int fileId = uploadFile.getId();
                File file = new File(fileLocation + "\\" + fileName);
                if (file.exists()) {
                    if (file.delete()) {
                        // 지워졌으면 db에서 파일의 정보를 지워야해
                        fileDAO.deleteById(fileId);
                    }
                }
            }
            // 프로젝트와 연결되어있는 태그연결 삭제 - cascading

            // 삭제할 프로젝트만 연결되어있던(연결된 프로젝트가 없는) 태그 삭제 - 아직 유지
            // 프로젝트 삭제
            portfolioDao.deleteById(pid);
            result.status = true;
            result.data = "포트폴리오 삭제 성공";
            result.object = null;
        } catch (final Exception e) {
            result.status = false;
            result.data = "포트폴리오 삭제 실패";
            result.object = null;
        }
        response = new ResponseEntity<>(result, HttpStatus.OK);
        return response;
    }

    @Override
    public ResponseEntity<BasicResponse> readOne(int pid) {

        Portfolio p = portfolioDao.findPortfolioByPid(pid);

        List<Tag> tagList = new ArrayList<>();
        List<PortfolioTag> ptList = p.getPortfolioTags();
        for (PortfolioTag pt : ptList) {
            Tag tag = pt.getTag();
            tagList.add(tag);
        }

        PortfolioTagsFiles ptf = PortfolioTagsFiles.builder().pid(p.getPid()).uid(p.getUid()).title(p.getTitle())
                .start_date(p.getStartDate()).end_date(p.getEndDate()).contents(p.getContents()).tag(tagList)
                .files(p.getFiles()).build();

        if (ptf != null) {
            result.status = true;
            result.data = "pid가 " + pid + "인 포트폴리오의 정보입니다.";
            result.object = ptf;
            response = new ResponseEntity<BasicResponse>(result, HttpStatus.OK);
        } else {
            result.status = false;
            result.data = "포트폴리오 실패(비어있거나 실패)";
            result.object = null;
            response = new ResponseEntity<BasicResponse>(result, HttpStatus.OK);
            return response;
        }
        return response;
    }

    @Override
    public ResponseEntity<BasicResponse> createPTag(int pid, String tag) {

        // tag table에 존재하지 않으면 tagName을 tag table에 만들고 그 tid를 pid와 연결한다.
        Optional<Tag> tagOpt = tagDao.getTagByTagName(tag);
        if (!tagOpt.isPresent()) {
            TagCreateRequest tagCreateRequest = new TagCreateRequest();
            tagCreateRequest.setTagName(tag);

            Tag tagSave = tagDao.save(tagCreateRequest.toEntity());
            int tid = tagSave.getTid();

            PTag pTagSave = ptagDao.save(PTag.builder().pid(pid).tid(tid).build());

            result.status = true;
            result.data = "포트폴리오 태그 생성 성공";
            result.object = pTagSave.toResEntity();

            response = new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            // 태그 이름을 확인해서 이미 tag table에 존재하면 그 tag의 tid와 pid의 연결상태를 확인한다.
            int tid = tagOpt.get().getTid();
            Optional<PTag> ptagOpt = ptagDao.getTagByPidAndTid(pid, tid);
            // 연결되어있으면 태그연결 실패(이미 연결됨)
            if (ptagOpt.isPresent()) {
                result.status = false;
                result.data = "이미 연결된 태그";
                result.object = "";
                response = new ResponseEntity<>(result, HttpStatus.OK);
            } else {
                // 연결되어있지 않으면 태그 연결
                PTag pTagSave = ptagDao.save(PTag.builder().pid(pid).tid(tid).build());

                result.status = true;
                result.data = "이미 존재하는 태그에 연결";
                result.object = pTagSave.toResEntity();
                response = new ResponseEntity<>(result, HttpStatus.OK);
            }
        }
        return response;
    }

    @Override
    public ResponseEntity<BasicResponse> deletePtag(PTagCreateRequest request) {
        Optional<PTag> pTagOpt = ptagDao.getTagByPidAndTid(request.getPid(), request.getTid());
        if (pTagOpt.isPresent()) {
            ptagDao.deletePTagByPtid(pTagOpt.get().getPtid());
            result.status = true;
            result.data = "태그 연결 해제";
            result.object = pTagOpt;
            response = new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            result.status = false;
            result.data = "태그 연결 실패 - 존재하지 않는 연결";
            result.object = pTagOpt;
            response = new ResponseEntity<>(result, HttpStatus.OK);
        }

        return response;
    }
}
