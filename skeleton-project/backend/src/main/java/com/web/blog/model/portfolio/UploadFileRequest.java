package com.web.blog.model.portfolio;

import org.springframework.web.multipart.MultipartFile;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UploadFileRequest {

    private int pid;

    private MultipartFile[] files;

    @Builder
    public UploadFileRequest(int pid, MultipartFile[] files) {
        this.pid = pid;
        this.files = files;
    }

}
