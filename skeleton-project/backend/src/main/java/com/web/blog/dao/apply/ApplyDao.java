package com.web.blog.dao.apply;

import java.util.List;
import java.util.Optional;

import com.web.blog.model.apply.Apply;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ApplyDao extends JpaRepository<Apply, Integer> {

    List<Apply> findApplyByUid(String uid);

    Optional<Apply> findApplyByUidAndApid(String uid, int apid);

    // SELECT * FROM ssafy_sk.apply where ap_term like '%%' and ap_company like
    // '%%';
    // as temp where temp.uid = :uid and temp.ap_term = :period and temp.ap_company
    // = :searchtxt
    @Query(value = "select temp from Apply as temp where temp.uid= :uid and temp.apTerm like CONCAT('%',:period,'%') and (temp.apCompany like CONCAT('%',:searchTxt,'%') or temp.apTitle like CONCAT('%',:searchTxt,'%'))", nativeQuery = false)
    List<Apply> findListBySearch(@Param("uid") String uid, @Param("searchTxt") String searchTxt,
            @Param("period") String period);

    Apply findApplyByApid(int apid);

    void deleteUploadFileByUid(String uid);

}