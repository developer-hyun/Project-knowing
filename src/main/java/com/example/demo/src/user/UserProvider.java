package com.example.demo.src.user;


import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.main.model.GetStudent;
import com.example.demo.src.user.model.*;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//Provider : Read의 비즈니스 로직 처리
@Service
public class UserProvider {

    private final UserDao userDao;
    private final JwtService jwtService;


    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public UserProvider(UserDao userDao, JwtService jwtService) {
        this.userDao = userDao;
        this.jwtService = jwtService;
    }

    /**
     * 카카오 네이버는 uid를 이메일로 쓰기때문에 그거 중복체크
     * @param email
     * @return
     * @throws BaseException
     */

    public String kakaologin(String email) throws BaseException {
        String status;
        //카카오로 들어왔을때 기존회원인지 아닌지
        if(userDao.checkuid(email) == 1) {
             status = "기존회원";
             return status;
        } else {
            status = "신규회원";
            return status;
        }
    }

    /** 이메일 중복체크 ( 회원가입시 )
     *  없으면 true
     * @return
     */
    public boolean getUsercheckemail(String email) throws BaseException{
        boolean status;
        if (userDao.checkEmail(email) == 1) {
            status = false;
            return status;
        } else {
            status = true;
            return status;
        }
    }

    /**
     * uid 중복체크 로그인지 회원가입인지 로그인인지
     * @return
     */
    public boolean getUsercheckuid(String uid) throws BaseException{
        boolean status;
        if (userDao.checkuid(uid) == 1) {
            status = false;
            return status;
        } else {
            status = true;
            return status;
        }
    }

    /** 아이디찾기 default
     *
     * @return
     */
    public GetUserFindId getUserFindId(String name,String phNum) throws BaseException {
        try {
            GetUserFindId getUserFindId = userDao.getUserFindId(name, phNum);
            return getUserFindId;
        } catch (Exception exception) {
            String email = "none";
            return new GetUserFindId(email);
        }
    }

    /**
     * 유저 정보 제공 부분
     */
    public GetUserModifyReqTransfer getUserInfoRes(String uid) throws BaseException {
//        try {
            GetUserInfoRes getUserInfoRes = userDao.getUserInfoRes(uid);
            List<String> specialStatus = Arrays.asList(getUserInfoRes.getSpecialStatus().split(" "));
            List<String> employmentState = Arrays.asList(getUserInfoRes.getEmploymentState().split(" "));
            List<String> studentCategory = Arrays.asList(getUserInfoRes.getStudentCategory().split(" "));
            List<String> empolyCategory = Arrays.asList(getUserInfoRes.getEmpolyCategory().split(" "));
            List<String> foundationCategory = Arrays.asList(getUserInfoRes.getFoundationCategory().split(" "));
            List<String> residentCategory = Arrays.asList(getUserInfoRes.getResidentCategory().split(" "));
            List<String> lifeCategory = Arrays.asList(getUserInfoRes.getLifeCategory().split(" "));
            List<String> covidCategory = Arrays.asList(getUserInfoRes.getCovidCategory().split(" "));
         //   List<String> bookmark = Arrays.asList(getUserInfoRes.getBookmark().split(" "));
         //   int birth = Integer.parseInt(getUserInfoRes.getBirth());
            List<String> bookmark = new ArrayList<>();
//
//            System.out.println(specialStatus);
//            System.out.println(employmentState);

            GetUserModifyReqTransfer getUserModifyReqTransfer = new GetUserModifyReqTransfer(getUserInfoRes.getEmail(),getUserInfoRes.getName(),getUserInfoRes.getPwd(),getUserInfoRes.getPhNum(),getUserInfoRes.getGender(),getUserInfoRes.getBirth(),getUserInfoRes.getAddress(),getUserInfoRes.getAddressDatil(),
                    specialStatus,getUserInfoRes.getIncomeLevel(),getUserInfoRes.getIncomeAvg(),employmentState,getUserInfoRes.getSchoolRecords(),getUserInfoRes.getSchool(),getUserInfoRes.getMainMajor(),getUserInfoRes.getSubMajor(),getUserInfoRes.getSemester(),getUserInfoRes.getLastSemesterScore(),
                    studentCategory,empolyCategory,foundationCategory,residentCategory,lifeCategory,covidCategory,bookmark,getUserInfoRes.getProvider(),getUserInfoRes.getFCMTOKEN());



         //   GetUserInfoRes getUserInfoRes = userDao.getUserInfoRes(uid);
            return getUserModifyReqTransfer;
//        } catch (Exception exception) {
//            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
//        }
    }

    /**
     * 북마크 현재 상태 알아보기
     */
    public PostBookmarkRes postBookmarkRes(String userUid,String welfareUid) {
        String result = "";
        if ((userDao.checkbookmark(userUid,welfareUid))==1) {        //중복이있다 등록되어있다
            result = "북마크등록";
        }
        if ((userDao.checkbookmark(userUid,welfareUid))==0) {
            result = "북마크삭제";
        }
        PostBookmarkRes postBookmarkRes = new PostBookmarkRes(result);
        return postBookmarkRes;
    }
    /**
     * check provier
     */
    public String checkProvider(String uid) {
        String provider = userDao.checkprovider(uid);
        return provider;
    }





//    }
}
