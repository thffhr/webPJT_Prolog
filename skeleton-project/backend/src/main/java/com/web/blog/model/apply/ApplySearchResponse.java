package com.web.blog.model.apply;

import java.util.List;

import lombok.Getter;

@Getter
public class ApplySearchResponse extends Apply {

    private boolean clicked;

    public ApplySearchResponse(final int apid, final String uid, final String apTitle, final String apCompany,
            final String apTerm, final String apDesc, final List<ApplyPortfolio> applyPortfolio,
            final List<ApplyExperience> applyExperience, final boolean clicked) {
        super();
        this.clicked = clicked;
    }

}
