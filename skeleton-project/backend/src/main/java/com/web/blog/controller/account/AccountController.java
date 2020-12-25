package com.web.blog.controller.account;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.web.blog.model.BasicResponse;
import com.web.blog.model.user.LoginRequest;
import com.web.blog.model.user.SignupRequest;
import com.web.blog.model.user.UserUpdateRequest;
import com.web.blog.service.user.UserService;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.ResponseEntity;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiOperation;

@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized", response = BasicResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = BasicResponse.class),
        @ApiResponse(code = 404, message = "Not Found", response = BasicResponse.class),
        @ApiResponse(code = 500, message = "Failure", response = BasicResponse.class) })

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
public class AccountController {

    // Repo
    @Autowired
    UserService userService;

    @PostMapping("/account/login")
    @ApiOperation(value = "유저 로그인(uid 혹은 email로 로그인 가능)")
    public Object login(@Valid @RequestBody LoginRequest loginRequest) throws Exception {
        return userService.login(loginRequest);
    }

    @PostMapping("/account")
    @ApiOperation(value = "유저 가입하기")
    public Object signup(@Valid @RequestBody SignupRequest request) throws Exception {

        return userService.signup(request);
    }

    @DeleteMapping("/account/{uid}")
    @ApiOperation(value = "유저 탈퇴하기")
    public Object deleteUser(@RequestParam(required = true) final String uid) throws Exception {

        return userService.deleteUser(uid);
    }

    /*
     * RequestParam :get, post RequestBody : get, post, put, delete filter추가로 cors
     * 해결
     */
    @PutMapping("/account")
    @ApiOperation(value = "유저 정보 수정하기")
    public Object updateUser(@Valid @RequestBody UserUpdateRequest updateRequest) throws Exception {

        return userService.updateUser(updateRequest);
    }

    @PostMapping("/account/signup/emailcheck")
    @ApiOperation(value = "가입시 이메일 중복 확인")
    public Object emailcheck(@RequestParam(required = true) final String email) throws Exception {

        return userService.emailcheck(email);
    }

    @PostMapping("/account/signup/nicknamecheck")
    @ApiOperation(value = "가입시 닉네임 중복 확인")
    public Object nicknamecheck(@RequestParam(required = true) final String nickname) throws Exception {

        return userService.nicknamecheck(nickname);
    }

    @PostMapping("/account/signup/idcheck")
    @ApiOperation(value = "가입시 아이디 중복 확인")
    public Object idcheck(@RequestParam(required = true) final String uid) throws Exception {

        return userService.idcheck(uid);
    }

    @GetMapping("/account/findUserByEmail")
    @ApiOperation(value = "이메일로 유저정보 찾기(id찾기 pw찾기 가능)")
    public Object findPassByEmail(@RequestParam(required = true) final String email) throws Exception {

        return userService.findUserByEmail(email);
    }

    @ApiOperation(value = "프로필 이미지 업로드")
    @PostMapping("/account/profile/{uid}")
    public ResponseEntity<BasicResponse> uploadImage(@PathVariable("uid") String uid,
            @RequestParam("profile") MultipartFile profile) throws Exception {
        return userService.updateProfile(uid, profile);
    }

    @ApiOperation(value = "프로필 이미지 리턴")
    @GetMapping(path = { "/account/profile/{uid}" })
    public void getImage(HttpServletResponse response, @PathVariable("uid") String uid) throws Exception {

        byte[] blob = (byte[]) userService.getProfile(uid).getBody().object;
        response.setContentType("image/jpeg, jpg, png/");
        // byte[] imagefile = blob;
        InputStream in1 = new ByteArrayInputStream(blob);
        IOUtils.copy(in1, response.getOutputStream());

    }

    @ApiOperation(value = "프로필 이미지 확인")
    @GetMapping(path = { "/account/ckprofile/{uid}" })
    public ResponseEntity<BasicResponse> checkProfile(@PathVariable("uid") String uid) throws Exception {
        return userService.getProfile(uid);
    }

    @ApiOperation(value = "프로필 이미지 삭제")
    @PostMapping("/account/profile/delete/{uid}")
    public ResponseEntity<BasicResponse> deleteImage(@PathVariable("uid") String uid) throws Exception {
        return userService.deleteProfile(uid);
    }

    // public static String byteArrayToBinaryString(byte[] b) {
    // StringBuilder sb = new StringBuilder();
    // for (int i = 0; i < b.length; ++i) {
    // sb.append(byteToBinaryString(b[i]));
    // }
    // return sb.toString();
    // }

    // public static String byteToBinaryString(byte n) {
    // StringBuilder sb = new StringBuilder("00000000");
    // for (int bit = 0; bit < 8; bit++) {
    // if (((n >> bit) & 1) > 0) {
    // sb.setCharAt(7 - bit, '1');
    // }
    // }
    // return sb.toString();
    // }

}