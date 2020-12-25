package com.web.blog.dao.portfolio;

import java.util.List;

import com.web.blog.model.portfolio.Portfolio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PortfolioDao extends JpaRepository<Portfolio, Integer> {
    List<Portfolio> findPortfolioByUid(String uid);

    @Query(value = "select p from Portfolio as p, PortfolioTag as pt WHERE p.uid = :uid And pt.tag.tid = :tid And pt.portfolio.pid = p.pid", nativeQuery = false)
    List<Portfolio> getPortfolioByTag(@Param("uid") String uid, @Param("tid") int tid);

    Portfolio findPortfolioByPid(int pid);

}