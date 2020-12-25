package com.web.blog.dao.period;

import com.web.blog.model.apply.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PeriodDao extends JpaRepository<ApplyTerm, Integer> {
}
