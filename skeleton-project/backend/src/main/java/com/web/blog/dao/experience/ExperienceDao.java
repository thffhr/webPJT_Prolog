package com.web.blog.dao.experience;

import java.util.ArrayList;
import java.util.List;

import com.web.blog.model.experience.Experience;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ExperienceDao extends JpaRepository<Experience, Integer> {

    ArrayList<Experience> findExperienceByUid(String uid);

    @Query(value = "select e from Experience as e, ExperienceTag as et WHERE e.uid = :uid And et.tag.tid = :tid And et.experience.exid = e.exid", nativeQuery = false)
    List<Experience> getExperienceByTag(@Param("uid") String uid, @Param("tid") int tid);

    Experience findExperienceByExid(int exid);

    void deleteExperienceByUid(String uid);

}