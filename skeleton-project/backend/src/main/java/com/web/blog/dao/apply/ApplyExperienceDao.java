package com.web.blog.dao.apply;

import java.util.List;

import com.web.blog.model.apply.Apply;
import com.web.blog.model.apply.ApplyExperience;
import com.web.blog.model.experience.Experience;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplyExperienceDao extends JpaRepository<ApplyExperience, Integer> {

    List<ApplyExperience> findApplyExperienceByApply(Apply apply);

    List<ApplyExperience> findApplyExperienceByApplyAndExperience(Apply apply, Experience experience);

}