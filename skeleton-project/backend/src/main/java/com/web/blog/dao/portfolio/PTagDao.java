package com.web.blog.dao.portfolio;

import com.web.blog.model.portfolio.PTag;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface PTagDao extends JpaRepository<PTag, Integer> {

    // p_tag 테이블에서 중복된 태그 있는지 확인
    Optional<PTag> getTagByPidAndTid(int pid, int tid);

    @Transactional // 이걸...해결했다고 볼수 있을까.....
    void deletePTagByPtid(int ptid);
}