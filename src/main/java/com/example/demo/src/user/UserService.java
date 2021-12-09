package com.example.demo.src.user;



import com.example.demo.config.BaseException;
import com.example.demo.src.user.model.*;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.demo.config.BaseResponseStatus.*;

// Service Create, Update, Delete 의 로직 처리
@Service
public class UserService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final UserDao userDao;
    private final UserProvider userProvider;
    private final JwtService jwtService;


    @Autowired
    public UserService(UserDao userDao, UserProvider userProvider, JwtService jwtService) {
        this.userDao = userDao;
        this.userProvider = userProvider;
        this.jwtService = jwtService;

    }

    /**
     * 소셜회원가입 API
     */
    public PostUserRes createsocial(String uid, String social, PostUserReq postUserReq) throws BaseException {

        int userIdx = userDao.createsocial(uid, social, postUserReq);
        //jwt
        String jwt = jwtService.createJwt(userIdx);
        return new PostUserRes(jwt, userIdx);
    }

    /**
     * 이메일 회원가입 API
     */
    public PostUserSignUpRes createmailsignup(String uid, PostUserSingUpReq postUserSingUpReq) throws BaseException {
        //이메일 중복
//        if(userDao.checkEmail(postUserSingUpReq.getEmail()) == 1) {
//            throw new BaseException(POST_USERS_EXISTS_EMAIL);
//        }
//        String pwd;
//        try{
//            //암호화
//            pwd = new AES128(Secret.USER_INFO_PASSWORD_KEY).encrypt(postUserReq.getPassword());
//            postUserReq.setPassword(pwd);
//        } catch (Exception ignored) {
//            throw new BaseException(PASSWORD_ENCRYPTION_ERROR);
//        }
        //   try{
        int userIdx = userDao.createemailsignup(uid, postUserSingUpReq);
//           // System.out.println(userIdx);
//            //jwt 발급.
//            String jwt = jwtService.createJwt(userIdx);
        //   System.out.println(jwt);
        return new PostUserSignUpRes(uid, userIdx);
//        } catch (Exception exception) {
//            throw new BaseException(DATABASE_ERROR);
//        }
//    }

    }

    /**
     * 유저정보수정
     */
    public GetUserModifyRes getUserModifyRes(String uid, GetUserModifyReq getUserModifyReq) throws BaseException {
        String status = "유저정보수정완료";
        try {
            userDao.getUserModify(uid, getUserModifyReq);
            GetUserModifyRes getUserModifyRes = new GetUserModifyRes(status);
            return getUserModifyRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    /** 유저정보수정(복지정보수정)
     *
     */
    public GetUserModifyRes patchUserWelfareRes(String uid, PatchUserWelfareStringReq patchUserWelfareStringReq) throws BaseException {
        String status = "유저정보수정완료";
       // System.out.println(userDao.patchUserWelfare(uid,patchUserWelfareStringReq));
        try {
            userDao.patchUserWelfare(uid, patchUserWelfareStringReq);
            GetUserModifyRes getUserModifyRes = new GetUserModifyRes(status);
            return getUserModifyRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    /** 유저정보수정(개인정보수정)
     *
     */
    public GetUserModifyRes patchUserPrivacy(String uid,PatchUserPrivacyReq patchUserPrivacyReq) throws BaseException {
        String status = "유저정보수정완료";
       // System.out.println(userDao.patchUserPrivacy(uid, patchUserPrivacyReq));
        try {
            userDao.patchUserPrivacy(uid, patchUserPrivacyReq);
            GetUserModifyRes getUserModifyRes = new GetUserModifyRes(status);
            return getUserModifyRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }





    /** 유저 탈퇴
     *
     */
    public DeleteUserRes deleteUserRes(String uid) throws BaseException,Exception {
        String result = "유저탈퇴완료";
//        try {
//            if(userDao.checkuid(uid) == 0) {
//                throw new BaseException(DATABASE_ERROR);
//            }
            userDao.deleteUserRes(uid);
            DeleteUserRes deleteUserRes = new DeleteUserRes(result);
            return deleteUserRes;
//        } catch (Exception exception){
//            throw new BaseException(DATABASE_ERROR);
        }

    /**
     * 북마크 등록
     */
    public PostBookmarkRes postBookmarkRes(String userUid,String welfareUid) {
        String result = "";
        if((userDao.checkbookmark(userUid,welfareUid)) == 1){   //중복이있다 그러면 삭제해야한다!
            userDao.deleteBookmark(userUid,welfareUid);
            result="북마크삭제";
        }
        else{
            userDao.createBookmark(userUid,welfareUid);
            result="북마크등록";
        }
       // result = "북마크등록이 완료되었습니다";

       // System.out.println(status);
        PostBookmarkRes postBookmarkRes = new PostBookmarkRes(result);
        return postBookmarkRes;
    }


//    //POST
//    public PostUserRes createUser(PostUserReq postUserReq) throws BaseException {
//        //중복
//        if(userProvider.checkEmail(postUserReq.getEmail()) ==1){
//            throw new BaseException(POST_USERS_EXISTS_EMAIL);
//        }
//
//        String pwd;
//        try{
//            //암호화
//            pwd = new AES128(Secret.USER_INFO_PASSWORD_KEY).encrypt(postUserReq.getPassword());
//            postUserReq.setPassword(pwd);
//        } catch (Exception ignored) {
//            throw new BaseException(PASSWORD_ENCRYPTION_ERROR);
//        }
//        try{
//            int userIdx = userDao.createUser(postUserReq);
//            //jwt 발급.
//            String jwt = jwtService.createJwt(userIdx);
//            return new PostUserRes(jwt,userIdx);
//        } catch (Exception exception) {
//            throw new BaseException(DATABASE_ERROR);
//        }
//    }

//
//    public void modifyUserName(PatchUserReq patchUserReq) throws BaseException {
//        try{
//            int result = userDao.modifyUserName(patchUserReq);
//            if(result == 0){
//                throw new BaseException(MODIFY_FAIL_USERNAME);
//            }
//        } catch(Exception exception){
//            throw new BaseException(DATABASE_ERROR);
//        }
//    }
}
