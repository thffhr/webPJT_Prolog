package com.web.blog.model.experience;

import java.sql.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.web.blog.model.apply.ApplyExperience;
import com.web.blog.model.join.ExperienceTag;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@NoArgsConstructor
@Getter
@ToString
public class Experience {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int exid;

    private String uid;

    private String title;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "start_date", insertable = false)
    private Date startdate;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "end_date", insertable = false)
    private Date enddate;

    private String contents;

    @OneToMany(mappedBy = "experience")
    private List<ExperienceTag> experienceTags;

    @OneToMany(mappedBy = "experience")
    private List<ApplyExperience> applyExperiences;

    @Builder
    public Experience(int exid, String uid, String title, Date startdate, Date enddate, String contents) {
        this.exid = exid;
        this.uid = uid;
        this.title = title;
        this.startdate = startdate;
        this.enddate = enddate;
        this.contents = contents;
    }

}