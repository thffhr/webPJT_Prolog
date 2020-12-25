package com.web.blog.model.apply;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Getter
@NoArgsConstructor
public class ApplyUpdateRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int apid;

    private String uid;

    private String apTitle;
    @NonNull
    private String apCompany;

    private String apTerm;

    private String apDesc;

    public Apply toReqEntity() {
        return Apply.builder().apid(apid).uid(uid).apTitle(apTitle).apCompany(apCompany).apTerm(apTerm).apDesc(apDesc)
                .build();
    }
}