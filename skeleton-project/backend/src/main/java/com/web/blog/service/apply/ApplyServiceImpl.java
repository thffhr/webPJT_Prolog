package com.web.blog.service.apply;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.web.blog.dao.apply.ApplyDao;
import com.web.blog.dao.apply.ApplyExperienceDao;
import com.web.blog.dao.apply.ApplyPortfolioDao;
import com.web.blog.dao.experience.ExperienceDao;
import com.web.blog.dao.portfolio.PortfolioDao;
import com.web.blog.model.BasicResponse;
import com.web.blog.model.apply.Apply;
import com.web.blog.model.apply.ApplyAddOrDeleteERequest;
import com.web.blog.model.apply.ApplyAddOrDeletePRequest;
import com.web.blog.model.apply.ApplyCreateRequest;
import com.web.blog.model.apply.ApplyExperience;
import com.web.blog.model.apply.ApplyPortfolio;
import com.web.blog.model.apply.ApplyUpdateRequest;
import com.web.blog.model.experience.Experience;
import com.web.blog.model.portfolio.Portfolio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("applyService")
public class ApplyServiceImpl implements ApplyService {

    @Autowired
    ApplyDao applyDao;

    @Autowired
    PortfolioDao portfolioDao;

    @Autowired
    ApplyPortfolioDao applyPortfolioDao;

    @Autowired
    ExperienceDao experienceDao;

    @Autowired
    ApplyExperienceDao applyExperienceDao;

    BasicResponse result = new BasicResponse();
    ResponseEntity<BasicResponse> response = null;

    @Override
    public ResponseEntity<BasicResponse> createApply(ApplyCreateRequest applyCreateRequest) {
        try {
            if (applyCreateRequest.getApCompany().equals(null)) {
                result.status = false;
                result.data = "회사명은 필수 입력값입니다.";
                result.object = null;
            } else {
                Apply apply = applyDao.save(applyCreateRequest.toReqEntity());
                result.status = true;
                result.data = "지원 추가 성공";
                result.object = apply;
            }
        } catch (final Exception e) {
            result.status = false;
            result.data = "지원 추가 실패";
            result.object = null;
        }
        response = new ResponseEntity<>(result, HttpStatus.OK);
        return response;
    }

    @Override
    public ResponseEntity<BasicResponse> readAllApply(String uid) {
        try {
            List<Apply> list = applyDao.findApplyByUid(uid);
            if (list.isEmpty()) {
                result.status = false;
                result.data = "지원 내역 없음";
                result.object = null;
            } else {
                result.status = true;
                result.data = "지원 내역 리스트";
                result.object = list;
            }

        } catch (Exception e) {
            result.status = false;
            result.data = "지원 내역 조회 실패";
            result.object = null;
        }
        response = new ResponseEntity<>(result, HttpStatus.OK);
        return response;
    }

    @Override
    public ResponseEntity<BasicResponse> readOneApply(String uid, int apid) {
        try {
            Optional<Apply> applyOpt = applyDao.findApplyByUidAndApid(uid, apid);
            if (applyOpt.isPresent()) {
                result.status = true;
                result.data = "지원 내역 조회 성공";
                result.object = applyOpt;
            } else {
                result.status = false;
                result.data = "지원 내역 없음";
                result.object = null;
            }
        } catch (Exception e) {
            result.status = false;
            result.data = "지원 내역 조회 실패";
            result.object = null;
        }
        response = new ResponseEntity<>(result, HttpStatus.OK);
        return response;
    }

    @Override
    public ResponseEntity<BasicResponse> updateApply(ApplyUpdateRequest applyUpdateRequest) {
        try {
            if (applyUpdateRequest.getApCompany().equals("")) {
                result.status = false;
                result.data = "회사명은 필수 입력값입니다.";
                result.object = null;
            } else {
                Apply apply = applyDao.save(applyUpdateRequest.toReqEntity());
                result.status = true;
                result.data = "지원 추가 성공";
                result.object = apply;
            }
        } catch (Exception e) {
            result.status = false;
            result.data = "지원 내역 수정 실패";
            result.object = null;
        }
        response = new ResponseEntity<>(result, HttpStatus.OK);
        return response;
    }

    @Override
    public ResponseEntity<BasicResponse> deleteApply(String uid, int apid) {
        try {
            Optional<Apply> applyOpt = applyDao.findApplyByUidAndApid(uid, apid);
            if (applyOpt.isPresent()) {
                applyDao.delete(applyOpt.get());
                result.status = true;
                result.data = "지원 내역 삭제 성공";
                result.object = null;
            } else {
                result.status = false;
                result.data = "지원 내역 없음";
                result.object = null;
            }
        } catch (Exception e) {
            // e.printStackTrace();
            result.status = false;
            result.data = "지원 내역 삭제 실패";
            result.object = null;
        }
        response = new ResponseEntity<>(result, HttpStatus.OK);
        return response;
    }

    @Override
    public ResponseEntity<BasicResponse> portfolioInApply(String uid, int apid) {
        // 연결된 포트폴리오 가져오기!!
        // uid, apid가 있으니까 applyportfolio table에서 apid가 this.apid인 pid를 가져온다.
        // 근데 apid가 int 값이 아니라 apply 객체이므로 apply객체를 전달해주면 된다?!
        Apply apply = applyDao.getOne(apid);
        if (apply == null) {
            result.status = false;
            result.data = "존재하지 않는 지원내역입니다.";
            result.object = null;
        }
        // 전달받은 apply 객체로 applyportfolio table의 apid 가 this.apid인 값들을 가져온다.
        List<ApplyPortfolio> list = applyPortfolioDao.findApplyPortfolioByApply(apply);

        // 찾아온 값이 하나도 없으면 포폴이 없는거
        List<Portfolio> plist = new ArrayList<>();
        if (list.isEmpty()) {
            result.status = false;
            result.data = "연결된 포트폴리오가 없습니다.";
            result.object = null;

            // 찾아온 값이 있으면 list를 하나씩 까보면서 pid의 값으로 포폴을 plist에 담아 리턴해준다.
        } else {
            for (ApplyPortfolio ap : list) {
                plist.add(portfolioDao.findPortfolioByPid(ap.getPortfolio().getPid()));
            }
            result.status = true;
            result.data = "연결된 포트폴리오";
            result.object = plist;
        }
        response = new ResponseEntity<>(result, HttpStatus.OK);
        return response;
    }

    @Override
    public ResponseEntity<BasicResponse> portfolioOutOfApply(String uid, int apid) {

        Optional<Apply> applyOpt = applyDao.findById(apid);
        if (!applyOpt.isPresent()) {
            result.status = false;
            result.data = "존재하지 않는 지원내역입니다.";
            result.object = null;
        } else {
            // apid로 속해있는 포폴을 가져올 수 있지. List<ApplyPortfolio> list
            List<ApplyPortfolio> list = applyPortfolioDao.findApplyPortfolioByApply(applyOpt.get());
            List<Portfolio> plist = new ArrayList<>();
            for (ApplyPortfolio ap : list) {
                plist.add(portfolioDao.findPortfolioByPid(ap.getPortfolio().getPid()));
            }

            // uid로 유저의 전체 포폴의 pid를 서치.List<Portfolio> pAllList
            List<Portfolio> pAllList = portfolioDao.findPortfolioByUid(uid);

            // 전체 포폴 리스트중 apid에 속해있는 pid를 지워줘
            pAllList.removeAll(plist);

            result.status = true;
            result.data = "apid :" + apid + "에 속해있지 않은 포트폴리오";
            result.object = pAllList;
        }
        response = new ResponseEntity<>(result, HttpStatus.OK);
        return response;
    }

    @Override
    public ResponseEntity<BasicResponse> experienceInApply(String uid, int apid) {

        Apply apply = applyDao.getOne(apid);

        List<ApplyExperience> list = applyExperienceDao.findApplyExperienceByApply(apply);

        List<Experience> explist = new ArrayList<>();
        if (list.isEmpty()) {
            result.status = false;
            result.data = "연결된 경험이 없습니다.";
            result.object = null;

        } else {
            for (ApplyExperience aexp : list) {
                explist.add(experienceDao.findExperienceByExid(aexp.getExperience().getExid()));
            }
            result.status = true;
            result.data = "연결된 경험";
            result.object = explist;
        }
        response = new ResponseEntity<>(result, HttpStatus.OK);
        return response;
    }

    @Override
    public ResponseEntity<BasicResponse> experienceOutOfApply(String uid, int apid) {

        Optional<Apply> applyOpt = applyDao.findById(apid);
        if (!applyOpt.isPresent()) {
            result.status = false;
            result.data = "존재하지 않는 지원내역입니다.";
            result.object = null;
        } else {
            List<ApplyExperience> list = applyExperienceDao.findApplyExperienceByApply(applyOpt.get());
            List<Experience> explist = new ArrayList<>();
            for (ApplyExperience aexp : list) {
                explist.add(experienceDao.findExperienceByExid(aexp.getExperience().getExid()));
            }

            List<Experience> expAllList = experienceDao.findExperienceByUid(uid);

            expAllList.removeAll(explist);

            result.status = true;
            result.data = "apid :" + apid + "에 속해있지 않은 경험";
            result.object = expAllList;
        }
        response = new ResponseEntity<>(result, HttpStatus.OK);
        return response;
    }

    @Override
    public ResponseEntity<BasicResponse> findListBySearch(String uid, String searchTxt, String period) {

        String tempTxt = searchTxt == null ? "" : searchTxt;
        String tempPeriod = period == null ? "" : period;

        List<Apply> applyOpt = applyDao.findListBySearch(uid, tempTxt, tempPeriod);
        /*
         * List<ApplySearchResponse> resList = new ArrayList<>();
         * 
         * for(Apply a : applyOpt){ resList.add(new ApplySearchResponse(a.getApid(),
         * a.getUid(), a.getApCompany(), a.getApTerm(), a.getApDesc(),
         * a.getApplyPortfolio(), a.getApplyExperience(), false)); }
         */

        if (applyOpt.size() == 0) {
            result.status = false;
            result.data = "존재하지 않는 지원내역입니다.";
            result.object = null;
        } else {
            result.status = true;
            result.data = "지원내역 출력";
            result.object = applyOpt;
        }
        response = new ResponseEntity<>(result, HttpStatus.OK);
        return response;
    }

    @Override
    public ResponseEntity<BasicResponse> addPortfolio(ApplyAddOrDeletePRequest request) {
        // applyPortfolio model에 apid, pid값 추가
        Apply apply = applyDao.findApplyByApid(request.getApid());
        Portfolio portfolio = portfolioDao.findPortfolioByPid(request.getPid());
        ApplyPortfolio applyPortfolio = ApplyPortfolio.builder().apply(apply).portfolio(portfolio).build();
        if (!applyPortfolioDao.findApplyPortfolioByApplyAndPortfolio(apply, portfolio).isEmpty()) {
            result.status = false;
            result.data = "이미 있음";
            result.object = null;
        } else {
            applyPortfolioDao.save(applyPortfolio);
            result.status = true;
            result.data = "포폴추가";
            result.object = null;
        }
        response = new ResponseEntity<>(result, HttpStatus.OK);
        return response;
    }

    @Override
    public ResponseEntity<BasicResponse> addExp(ApplyAddOrDeleteERequest request) {
        
        
        // applyPortfolio model에 apid, exid값 추가
        Apply apply = applyDao.findApplyByApid(request.getApid());
        Experience experience = experienceDao.findExperienceByExid(request.getExid());
        ApplyExperience applyExperience = ApplyExperience.builder().apply(apply).experience(experience).build();
        if (!applyExperienceDao.findApplyExperienceByApplyAndExperience(apply, experience).isEmpty()) {
            result.status = false;
            result.data = "이미 있음";
            result.object = null;
        } else {
            applyExperienceDao.save(applyExperience);
            result.status = true;
            result.data = "경험추가";
            result.object = null;
        }
        response = new ResponseEntity<>(result, HttpStatus.OK);
        return response;
    }

    @Override
    @Transactional
    public ResponseEntity<BasicResponse> deletePortfolio(ApplyAddOrDeletePRequest request) {
        Apply apply = applyDao.findApplyByApid(request.getApid());
        Portfolio portfolio = portfolioDao.findPortfolioByPid(request.getPid());
        List<ApplyPortfolio> applyPortfolio = applyPortfolioDao.findApplyPortfolioByApplyAndPortfolio(apply, portfolio);

        try {
            for (ApplyPortfolio ap : applyPortfolio) {
                applyPortfolioDao.delete(ap);
            }
            result.status = true;
            result.data = "삭제됨";
            result.object = null;
        } catch (Exception e) {
            result.status = false;
            result.data = "삭제 실패";
            result.object = null;

        }
        response = new ResponseEntity<>(result, HttpStatus.OK);
        return response;
    }

    @Override
    @Transactional
    public ResponseEntity<BasicResponse> deleteExp(ApplyAddOrDeleteERequest request) {
        Apply apply = applyDao.findApplyByApid(request.getApid());
        Experience experience = experienceDao.findExperienceByExid(request.getExid());
        List<ApplyExperience> applyExperience = applyExperienceDao.findApplyExperienceByApplyAndExperience(apply,
                experience);
        try {
            for (ApplyExperience ae : applyExperience) {
                applyExperienceDao.delete(ae);
            }
            result.status = true;
            result.data = "삭제됨";
            result.object = null;
        } catch (Exception e) {
            result.status = false;
            result.data = "삭제 실패";
            result.object = null;
        }
        response = new ResponseEntity<>(result, HttpStatus.OK);
        return response;
    }

}