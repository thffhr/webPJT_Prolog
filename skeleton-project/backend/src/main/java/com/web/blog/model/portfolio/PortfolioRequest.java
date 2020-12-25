package com.web.blog.model.portfolio;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Date;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class PortfolioRequest {

    @ApiModelProperty(required = true)
    @NotNull
    @Column(name = "uid")
    String uid;

    @ApiModelProperty(required = true)
    @NotNull
    @Column(name = "title")
    String title;

    @ApiModelProperty(required = true)
    @Column(name = "start_date")
    Date startDate;

    @ApiModelProperty(required = true)
    @Column(name = "end_date")
    Date endDate;

    @ApiModelProperty(required = true)
    @Column(name = "contents")
    String contents;

    public Portfolio toEntity() {
        return Portfolio.builder().uid(uid).title(title).startDate(startDate).endDate(endDate).contents(contents)
                .build();
    }

}
