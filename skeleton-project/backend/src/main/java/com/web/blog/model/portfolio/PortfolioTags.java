package com.web.blog.model.portfolio;

import java.sql.Date;
import java.util.List;

import com.web.blog.model.tag.Tag;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder(builderMethodName = "hiddenBuilder")
public class PortfolioTags {

    private int pid;

    private String uid;

    private String title;

    private Date start_date;

    private Date end_date;

    private String contents;

    private List<Tag> tag;

    public static PortfolioTagsBuilder builder(int pid, String uid, String title, Date start_date, Date end_date,
            String contents, List<Tag> tag) {
        return hiddenBuilder().pid(pid).uid(uid).title(title).start_date(start_date).end_date(end_date)
                .contents(contents).tag(tag);
    }
}
