package com.web.blog.service.period;

import java.util.List;

import com.web.blog.dao.period.PeriodDao;
import com.web.blog.model.BasicResponse;
import com.web.blog.model.apply.ApplyTerm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service("periodService")
public class PeriodServiceImpl implements PeriodService {

    @Autowired
    PeriodDao periodDao;

    // 요청과 응답
    BasicResponse result = new BasicResponse();
    ResponseEntity<BasicResponse> response = null;

    @Override
    public ResponseEntity<BasicResponse> findAll() {
        
        List<ApplyTerm> obj = periodDao.findAll();

        if (obj != null ) {
            result.status = true;
            result.data = "기간 가져오기 성공";
            result.object = obj;
        }  else {
            result.status = false;
            result.data = "기간 가져오기 실패";
            result.object = null;
        }
        
        response = new ResponseEntity<BasicResponse>(result, HttpStatus.OK);
        return response;
    }

 

}
