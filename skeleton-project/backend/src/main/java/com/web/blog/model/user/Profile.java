package com.web.blog.model.user;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Data;

@Entity
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "user")

public class Profile {

    public Profile() {
        super();
    }

    public Profile(byte[] picByte) {
        this.picByte = picByte;
    }

    @Id
    @Column(name = "uid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String uid;

    @Column(name = "picByte", length = 1000)
    private byte[] picByte;

    @Builder
    public Profile(String uid, byte[] picByte) {
        this.uid = uid;
        this.picByte = picByte;
    }

    // @Builder
    // public ProfileModel(String uid ,MultipartFile profile){
    // this.uid = uid;
    // this.profile = profile;
    // }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public byte[] getPicByte() {
        return picByte;
    }

    public void setPicByte(byte[] picByte) {
        this.picByte = picByte;
    }
}