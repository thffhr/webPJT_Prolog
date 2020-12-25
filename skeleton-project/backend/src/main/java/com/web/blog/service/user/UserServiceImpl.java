package com.web.blog.service.user;

import java.util.List;
import java.util.Optional;
import java.util.zip.Deflater;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

import com.web.blog.dao.apply.ApplyDao;
import com.web.blog.dao.experience.ExperienceDao;
import com.web.blog.dao.portfolio.FileDAO;
import com.web.blog.dao.portfolio.PortfolioDao;
import com.web.blog.dao.user.ProfileDao;
import com.web.blog.dao.user.UserDao;
import com.web.blog.model.BasicResponse;
import com.web.blog.model.portfolio.Portfolio;
import com.web.blog.model.portfolio.UploadFile;
import com.web.blog.model.user.LoginRequest;
import com.web.blog.model.user.Profile;
import com.web.blog.model.user.ProfileUpdateRequest;
import com.web.blog.model.user.SignupRequest;
import com.web.blog.model.user.User;
import com.web.blog.model.user.UserUpdateRequest;
import com.web.blog.property.FileUploadProperties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    @Autowired
    ProfileDao profileDao;

    @Autowired
    FileDAO fileDAO;

    @Autowired
    ExperienceDao experienceDao;

    @Autowired
    PortfolioDao portfolioDao;

    @Autowired
    ApplyDao applyDao;

    final BasicResponse result = new BasicResponse();
    ResponseEntity<BasicResponse> response = null;

    private Path fileLocation;

    public UserServiceImpl(FileUploadProperties prop) {
        this.fileLocation = Paths.get(prop.getUploadDir()).toAbsolutePath().normalize();
    }

    @Override
    public ResponseEntity<BasicResponse> login(LoginRequest loginRequest) throws Exception {
        ResponseEntity<BasicResponse> response = null;
        String email = loginRequest.toEntity().getEmail();
        String uid = loginRequest.toEntity().getUid();
        String password = loginRequest.getPassword();

        final Optional<User> userOpt = userDao.findUserByEmailAndPassword(email, password);
        final Optional<User> userOpt2 = userDao.findUserByUidAndPassword(uid, password);

        final BasicResponse result = new BasicResponse();

        if (userOpt.isPresent() || userOpt2.isPresent()) {
            result.status = true;
            result.data = "success";

            if (userOpt.isPresent())
                result.object = userOpt;
            if (userOpt2.isPresent())
                result.object = userOpt2;

            response = new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            result.status = false;
            result.data = "fail";
            response = new ResponseEntity<>(null, HttpStatus.OK);
            // response = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        return response;
    }

    @Override
    public ResponseEntity<BasicResponse> signup(final SignupRequest request) throws Exception {

        ResponseEntity<BasicResponse> response = null;

        // 이메일, 닉네임 중복처리 필수
        final Optional<User> userEmailOpt = userDao.findUserByEmail(request.getEmail());
        final Optional<User> userUidOpt = userDao.findUserByUid(request.getUid());

        // 초기화
        final BasicResponse result = new BasicResponse();

        if (userEmailOpt.isPresent()) {
            result.status = false;
            result.data = "이메일 중복";
            response = new ResponseEntity<>(result, HttpStatus.OK);
        } else if (userUidOpt.isPresent()) {
            result.status = false;
            result.data = "아이디 중복";
            response = new ResponseEntity<>(result, HttpStatus.OK);
        } else {

            // profile default img
            // ProfileUpdateRequest updaterequest = new ProfileUpdateRequest(uid,
            // file.getBytes());

            // profileDao.save(updaterequest.toEntity(uid, file.getBytes()));
            // request.setPicByte(picByte);

            userDao.save(request.toEntity());
            result.status = true;
            result.data = "회원가입 성공";
            response = new ResponseEntity<>(result, HttpStatus.OK);
        }

        return response;
    }

    @Transactional
    @Override
    public ResponseEntity<BasicResponse> deleteUser(final String uid) throws Exception {
        ResponseEntity<BasicResponse> response = null;

        final Optional<User> userOpt = userDao.findById(uid);

        // 초기화
        final BasicResponse result = new BasicResponse();

        if (userOpt.isPresent()) {
            // 1. 파일삭제
            // uid로 pid를 검색하고
            List<Portfolio> portfolioList = portfolioDao.findPortfolioByUid(uid);
            // 그 pid로 fileDao에서 파일 이름들 찾아와서
            for (Portfolio portfolio : portfolioList) {
                List<UploadFile> uploadFiles = fileDAO.findUploadFileBypid(portfolio);

                for (UploadFile uploadFile : uploadFiles) {
                    File file = new File(fileLocation + "\\" + uploadFile.getFileName());
                    if (file.exists()) {
                        // 파일스토리지에서 삭제
                        if (file.delete()) {
                            // 2. 파일 디비 삭제
                            // 검색된 리스트 데이터 전체 삭제
                            fileDAO.deleteById(uploadFile.getId());
                        }
                    }
                }
                // 3. 프로젝트 삭제
                // 검색된 pid리스트 데이터 전체 삭제
                portfolioDao.deleteById(portfolio.getPid());
            }
            // 4. 경험삭제
            // uid를 갖고있는 경험 전체 삭제
            // List<Experience> experienceList = experienceDao.findExperienceByUid(uid);
            experienceDao.deleteExperienceByUid(uid);

            // 5. 지원목록 삭제
            // uid를 갖고있는 지원목록 전체 삭제
            applyDao.deleteUploadFileByUid(uid);
            // 6. 유저삭제
            // 유저삭제
            userDao.deleteById(uid);
            result.status = true;
            result.data = "회원탈퇴 성공";
            response = new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            result.status = false;
            result.data = "회원탈퇴 실패";
            response = new ResponseEntity<>(result, HttpStatus.OK);
        }

        return response;
    }

    @Override
    public ResponseEntity<BasicResponse> updateUser(final UserUpdateRequest updateRequest) throws Exception {

        try {
            // 프로필 있는지 확인하고 있으면넣고 없으면 안넣음.
            Optional<User> opt = userDao.findUserByUid(updateRequest.getUid());
            userDao.save(updateRequest.toEntity(opt.get().getPicByte()));
            result.status = true;
            result.data = "회원수정 성공";
            response = new ResponseEntity<>(result, HttpStatus.OK);

        } catch (Exception e) {
            result.status = false;
            result.data = "회원수정 실패";
            response = new ResponseEntity<>(result, HttpStatus.OK);
        }
        return response;
    }

    @Override
    public ResponseEntity<BasicResponse> emailcheck(final String email) throws Exception {
        final Optional<User> userEmailOpt = userDao.findUserByEmail(email);
        final BasicResponse result = new BasicResponse();

        if (userEmailOpt.isPresent()) {
            result.status = false;
            result.data = "이미 존재하는 이메일입니다.";
        } else {
            result.status = true;
            result.data = "사용 가능한 이메일입니다.";
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<BasicResponse> nicknamecheck(final String nickname) throws Exception {
        final Optional<User> userNicknameOpt = userDao.findUserByNickname(nickname);
        final BasicResponse result = new BasicResponse();

        if (userNicknameOpt.isPresent()) {
            result.status = false;
            result.data = "이미 존재하는 닉네임입니다.";
        } else {
            result.status = true;
            result.data = "사용 가능한 닉네임입니다.";
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<BasicResponse> findUserByEmail(final String email) throws Exception {
        final Optional<User> userOpt = userDao.findUserByEmail(email);
        final BasicResponse result = new BasicResponse();

        if (userOpt.isPresent()) {
            result.status = true;
            result.data = "이메일로 유저정보를 가져왔습니다";
            result.object = userOpt;
        } else {
            result.status = false;
            result.data = "존재하는 이메일이 아닙니다.";
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<BasicResponse> updateProfile(String uid, MultipartFile file) throws Exception {

        ProfileUpdateRequest updaterequest = new ProfileUpdateRequest(uid, file.getBytes());

        profileDao.save(updaterequest.toEntity(uid, file.getBytes()));

        final BasicResponse result = new BasicResponse();

        result.status = true;
        result.data = "회원프로필 업로드 성공";

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<BasicResponse> getProfile(String uid) throws Exception {
        final Optional<Profile> retrievedImage = profileDao.findByUid(uid);
        byte[] profile = retrievedImage.get().getPicByte();

        final BasicResponse result = new BasicResponse();

        result.status = true;
        result.data = "회원프로필 가져오기 성공";
        result.object = profile;

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    public static byte[] compressBytes(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.setInput(data);
        deflater.finish();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        while (!deflater.finished()) {
            int count = deflater.deflate(buffer);
            outputStream.write(buffer, 0, count);
        }
        try {
            outputStream.close();
        } catch (IOException e) {

        }

        return outputStream.toByteArray();
    }

    // uncompress the image bytes before returning it to the angular application
    public static byte[] decompressBytes(byte[] data) {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(buffer);
                outputStream.write(buffer, 0, count);
            }
            outputStream.close();
        } catch (IOException ioe) {
        } catch (DataFormatException e) {
        }
        return outputStream.toByteArray();
    }

    public static String byteArrayToBinaryString(byte[] b) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < b.length; ++i) {
            sb.append(byteToBinaryString(b[i]));
        }
        return sb.toString();
    }

    public static String byteToBinaryString(byte n) {
        StringBuilder sb = new StringBuilder("00000000");
        for (int bit = 0; bit < 8; bit++) {
            if (((n >> bit) & 1) > 0) {
                sb.setCharAt(7 - bit, '1');
            }
        }
        return sb.toString();
    }

    @Override
    public ResponseEntity<BasicResponse> idcheck(String uid) throws Exception {
        final Optional<User> userUidOpt = userDao.findUserByUid(uid);
        final BasicResponse result = new BasicResponse();

        if (userUidOpt.isPresent()) {
            result.status = false;
            result.data = "이미 존재하는 아이디입니다.";
        } else {
            result.status = true;
            result.data = "사용 가능한 아이디입니다.";
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<BasicResponse> deleteProfile(String uid) {

        Profile profile = profileDao.findUserByUid(uid);
        profile.setPicByte(null);
        profileDao.save(profile);

        final BasicResponse result = new BasicResponse();

        result.status = true;
        result.data = "회원프로필이미지 삭제";
        result.object = profile;

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}