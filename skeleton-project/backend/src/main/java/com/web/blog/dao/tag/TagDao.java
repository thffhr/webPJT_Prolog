package com.web.blog.dao.tag;

import com.web.blog.model.tag.Tag;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TagDao extends JpaRepository<Tag, Integer> {
    Optional<Tag> getTagByTagName(String tagName);

}