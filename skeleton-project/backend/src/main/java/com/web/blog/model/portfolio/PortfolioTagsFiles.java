package com.web.blog.model.portfolio;

import java.sql.Date;
import java.util.List;

import com.web.blog.model.tag.Tag;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PortfolioTagsFiles extends PortfolioTags {

    @Builder
    PortfolioTagsFiles(int pid, String uid, String title, Date start_date, Date end_date, String contents,
            List<Tag> tag, List<UploadFile> files) {
        super(pid, uid, title, start_date, end_date, contents, tag);
        this.files = files;
    }

    private List<UploadFile> files;

}
