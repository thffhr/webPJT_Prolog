package com.web.blog.service.user;

import com.web.blog.model.BasicResponse;
import com.web.blog.model.user.LoginRequest;
import com.web.blog.model.user.SignupRequest;
import com.web.blog.model.user.UserUpdateRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface UserService {

   // 로그인
   ResponseEntity<BasicResponse> login(LoginRequest loginRequest) throws Exception;

   // 회원 가입
   ResponseEntity<BasicResponse> signup(SignupRequest request) throws Exception;

   // 회원 탈퇴
   ResponseEntity<BasicResponse> deleteUser(String uid) throws Exception;

   // 회원 정보 수정
   ResponseEntity<BasicResponse> updateUser(UserUpdateRequest updateRequest) throws Exception;

   // 이메일 중복 체크
   ResponseEntity<BasicResponse> emailcheck(String email) throws Exception;

   // 닉네임 중복 체크
   ResponseEntity<BasicResponse> nicknamecheck(String nickname) throws Exception;

   // 아이디 중복 체크
   ResponseEntity<BasicResponse> idcheck(String uid) throws Exception;

   // 이메일로 유저정보찾기)
   ResponseEntity<BasicResponse> findUserByEmail(String email) throws Exception;

   // uid에 해당하는 유저의 프로필 사진 업로드
   ResponseEntity<BasicResponse> updateProfile(String uid, MultipartFile file) throws Exception;

   // uid에 해당하는 유저의 프로필 사진 가져오기
   ResponseEntity<BasicResponse> getProfile(String uid) throws Exception;

   // uid에 해당하는 유저의 프로필 사진 삭제
   ResponseEntity<BasicResponse> deleteProfile(String uid);

}