package com.web.blog.dao.apply;

import java.util.List;

import com.web.blog.model.apply.Apply;
import com.web.blog.model.apply.ApplyPortfolio;
import com.web.blog.model.portfolio.Portfolio;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplyPortfolioDao extends JpaRepository<ApplyPortfolio, Integer> {

    List<ApplyPortfolio> findApplyPortfolioByApply(Apply apply);

    List<ApplyPortfolio> findApplyPortfolioByApplyAndPortfolio(Apply apply, Portfolio portfolio);
}