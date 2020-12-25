package com.web.blog.dao.join;

import java.util.Optional;

import com.web.blog.model.join.PortfolioTag;
import com.web.blog.model.portfolio.Portfolio;
import com.web.blog.model.tag.Tag;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PortfolioTagDao extends JpaRepository<PortfolioTag, Integer> {

    Optional<PortfolioTag> getPortfolioTagByPortfolioAndTag(Portfolio portfolio, Tag tag);
}