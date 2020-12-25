package com.web.blog.dao.portfolio;

import java.util.List;
import java.util.Optional;

import com.web.blog.model.portfolio.Portfolio;
import com.web.blog.model.portfolio.UploadFile;

import org.springframework.data.repository.CrudRepository;

public interface FileDAO extends CrudRepository<UploadFile, Integer> {

    void deleteById(int id);

    UploadFile findUploadFileById(int id);

    Optional<UploadFile> findUploadFileByFileName(String fileName);

    List<UploadFile> findUploadFileBypid(Portfolio portfolio);

}