package com.web.blog.dao.join;

import java.util.List;

import com.web.blog.model.join.ExperienceTag;
import com.web.blog.model.tag.Tag;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ExperienceTagDao extends JpaRepository<ExperienceTag, Integer> {

    @Query(value = "select temp from Tag as temp where temp.id = (select tp.tag from ExperienceTag as tp where tp.id = :extid)", nativeQuery = false)
    List<Tag> getTagByExtid(@Param("extid") int extid);
}