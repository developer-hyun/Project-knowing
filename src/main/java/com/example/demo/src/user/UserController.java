package com.example.demo.src.user;

import com.example.demo.utils.JwtService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.user.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringArrayPropertyEditor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;


import static com.example.demo.config.BaseResponseStatus.*;
import static com.example.demo.utils.ValidationRegex.*;

import java.util.*;

@RestController
@RequestMapping("/app/users")
public class UserController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final UserProvider userProvider;
    @Autowired
    private final UserService userService;
    @Autowired
    private final JwtService jwtService;


    public UserController(UserProvider userProvider, UserService userService, JwtService jwtService) {
        this.userProvider = userProvider;
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(
                String[].class,
                new StringArrayPropertyEditor(null)
        );
    }


    //테스트
//    @ResponseBody
//    @GetMapping("/test")
//    public BaseResponse<List<GetUserRes>> testlogin() {
//        String jwt = jwtService.createJwtsocial();
//        System.out.println(jwt);
//        List<GetUserRes> getUserRes = userProvider.logintest();
//        return new BaseResponse<>(getUserRes);
//
//
//    }

    @ResponseBody
    @GetMapping("/test/tt")
    public void testlogint() throws Exception {
//        String jwt = jwtService.createJwtsocial();
//        System.out.println(jwt);
        String uid = "godgod153@naver.com";
        Map<String, Object> additionalClaims = new HashMap<String, Object>();
        additionalClaims.put("id", true);

        String customToken = FirebaseAuth.getInstance().createCustomToken(uid, additionalClaims);
        System.out.println(customToken);
//        List<GetUserRes> getUserRes = userProvider.logintest();
//        return new BaseResponse<>(getUserRes);

    }

    @ResponseBody
    @GetMapping("/test/ttt")
    public void testlss() throws Exception {
        String uid = "godgod153@naver.com";
        UserRecord.UpdateRequest request = new UserRecord.UpdateRequest(uid)
                .setEmail("testversion@naver.com");
        UserRecord userRecord = FirebaseAuth.getInstance().updateUser(request);

    }

    /**
     * 소셜 회원가입
     *
     * @param postUserReq
     * @param uid
     * @param social
     * @return
     * @throws Exception
     */
    @ResponseBody
    @PostMapping("/social-login/{uid}")
    public BaseResponse<PostUserRes> sociallogin(@PathVariable("uid") String uid,
                                                 @RequestParam(value = "social", required = true) String social,
                                                 @RequestBody PostUserReq postUserReq) throws Exception {
        //바디부분 null금지
        if (postUserReq.getEmail() == null) {
            return new BaseResponse<>(POST_USERS_EMPTY_EMAIL);
        }
        if (postUserReq.getPassword() == null) {
            return new BaseResponse<>(POST_USERS_EMPTY_PASSWORD);
        }
        //파라미터 social금지
        if (social == null) {
            return new BaseResponse<>(POST_USERS_EMPTY_SOCIAL);
        }
        //소셜 이메일 정규표현식
        if (!isRegexEmail(postUserReq.getEmail())) {
            return new BaseResponse<>(POST_USERS_INVALID_EMAIL);
        }

        //통신부분
        try {
            PostUserRes postUserRes = userService.createsocial(uid, social, postUserReq);
            return new BaseResponse<>(postUserRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }


    /**
     * 카카오 네이버 연동 및 jwt 만들기
     */
//    @GetMapping("/kakao-login")
//    public RedirectView index() {
//
//    }
    @ResponseBody
    @GetMapping("/kakao-login")
    public BaseResponse<PostKakaoLoginRes> kakaologin(@RequestHeader(value = "access-token") String accesstoken) throws Exception {

        // try {
//            RestTemplate rt = new RestTemplate();
//
//            // HttpHeader 오브젝트 생성
//
//
//
//
//
//
//            }
//
        //try {
        try {
            RestTemplate rt2 = new RestTemplate();

            // HttpHeader 오브젝트 생성
            HttpHeaders headers2 = new HttpHeaders();
            headers2.add("Authorization", "Bearer " + accesstoken);
            headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
            HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest2 =
                    new HttpEntity<>(headers2);
            ResponseEntity<String> response2 = rt2.exchange(
                    "https://kapi.kakao.com/v2/user/me",
                    HttpMethod.POST,
                    kakaoProfileRequest2,
                    String.class
            );
            ObjectMapper objectMapper2 = new ObjectMapper();
            KakaoProfile kakaoProfile = null;

            //    System.out.println(response2.getBody());
//
            try {

                kakaoProfile = objectMapper2.readValue(response2.getBody(), KakaoProfile.class);

            } catch (JsonMappingException e) {
                e.printStackTrace();
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            String email = kakaoProfile.getKakao_account().getEmail();
            // System.out.println(email);
            //   int randomnum = kakaoProfile.getId();
            //  String kakaonum = Integer.toString(randomnum);
            String status = userProvider.kakaologin(email);
            Map<String, Object> additionalClaims = new HashMap<String, Object>();
            additionalClaims.put("id", true);
            String customToken = FirebaseAuth.getInstance().createCustomToken(email, additionalClaims);
            PostKakaoLoginRes postKakaoLoginRes = new PostKakaoLoginRes(status, customToken);
            return new BaseResponse<>(postKakaoLoginRes);

//


        } catch (Exception exception) {
            throw new BaseException(GET_USERS_KAKAO_ERROR);
        }
    }
//(@RequestParam(value = "access-token") String accesstoken)

    @ResponseBody
    @GetMapping("/naver-login")
    public BaseResponse<PostKakaoLoginRes> naverlogin(@RequestHeader(value = "access-token") String accesstoken) throws Exception {
        try {

            RestTemplate rt = new RestTemplate();

            //  HttpHeader 오브젝트 생성
            HttpHeaders profileRequestHeader = new HttpHeaders();

            profileRequestHeader.set("Authorization", "Bearer " + accesstoken);
            HttpEntity profileHttpEntity = new HttpEntity(profileRequestHeader);
            //요청
            ResponseEntity<String> profileResponse = rt.exchange(
                    "https://openapi.naver.com/v1/nid/me",
                    HttpMethod.GET,
                    profileHttpEntity,
                    String.class
            );



            ObjectMapper objectMapper = new ObjectMapper();
            NaverOuathParams naverOuathParams = null;
            try {
                naverOuathParams = objectMapper.readValue(profileResponse.getBody(), NaverOuathParams.class);
            } catch (JsonMappingException e) {
                e.printStackTrace();
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }



            String email = naverOuathParams.getResponse().getEmail();

            String status = userProvider.kakaologin(email);
            Map<String, Object> additionalClaims = new HashMap<String, Object>();
            additionalClaims.put("id", true);
            String customToken = FirebaseAuth.getInstance().createCustomToken(email, additionalClaims);
            PostKakaoLoginRes postKakaoLoginRes = new PostKakaoLoginRes(status, customToken);
            return new BaseResponse<>(postKakaoLoginRes);

        } catch (Exception exception) {
            throw new BaseException(GET_USERS_NAVER_ERROR);
        }
    }


    /**
     * 자체 이메일 회원가입 API
     */
    @ResponseBody
    @PostMapping("/sign-up")
    public BaseResponse<PostUserSignUpRes> postUserSignUpRes(@RequestBody PostUserSignUpReqTransfer postUserSignUpReqTransfer,
                                                             @RequestHeader(value = "uid") String uid,
                                                             @RequestHeader(value = "Content-Type") String contenttype) throws Exception {

      //  System.out.println(postUserSignUpReqTransfer.getAlarm().get(0).getTitle());

        //아이디 비밀번호 이름 폰번호 null 금지
        if (postUserSignUpReqTransfer.getName() == null) {
            return new BaseResponse<>(POST_USERS_EMPTY_NAME);
        }
//        if (postUserSignUpReqTransfer.getPwd() == null) {
//            return new BaseResponse<>(POST_USERS_EMPTY_PASSWORD);
//        }
//        if (postUserSignUpReqTransfer.getEmail() == null) {
//            return new BaseResponse<>(POST_USERS_EMPTY_EMAIL);
//        }
//        if (postUserSingUpReq.getBirth() == null) {
//            return new BaseResponse<>(POST_USERS_EMPTY_BIRTH);
//        }
//        if (postUserSingUpReq == null) {
//            return new BaseResponse<>(POST_USERS_EMPTY_GENDER);
//        }
//        if (postUserSignUpReqTransfer.getPhNum() == null) {
//            return new BaseResponse<>(POST_USERS_EMPTY_PHONENUMBER);
//        }

        //이메일 정규표현식
//        if (!isRegexEmail(postUserSignUpReqTransfer.getEmail())) {
//            return new BaseResponse<>(POST_USERS_INVALID_EMAIL);
//        }
        //비밀번호 정규표현식
        //if

        //휴대폰 정규식
//        if (!isRegexPhone(postUserSignUpReqTransfer.getPhNum())) {
//            return new BaseResponse<>(POST_USERS_INVALID_PHONE);
        // }
        //  String address = String.join("",postUserSignUpReqTransfer.getAddress());
        //  System.out.println(address);
        //  String addressDetail = String.join("",postUserSignUpReqTransfer.getAddressDetail());
        //  System.out.println(postUserSignUpReqTransfer);
//
//        System.out.println(postUserSignUpReqTransfer);
//        System.out.println(postUserSignUpReqTransfer.getEmail());
//        System.out.println(postUserSignUpReqTransfer.getName());
//        System.out.println(postUserSignUpReqTransfer.getPwd());
//        System.out.println(postUserSignUpReqTransfer.getPhNum());
//        System.out.println(postUserSignUpReqTransfer.getGender());
//        System.out.println(postUserSignUpReqTransfer.getBirth());
//        System.out.println(postUserSignUpReqTransfer.getAddress());
//        System.out.println(postUserSignUpReqTransfer.getAddressDetail());
//        System.out.println(postUserSignUpReqTransfer.getSpecialStatus());
//        System.out.println(postUserSignUpReqTransfer.getIncomeLevel());
//        System.out.println(postUserSignUpReqTransfer.getIncomeAvg());
//        System.out.println(postUserSignUpReqTransfer.getEmploymentState());
//        System.out.println(postUserSignUpReqTransfer.getSchoolRecords());
//        System.out.println(postUserSignUpReqTransfer.getSchool());
//        System.out.println(postUserSignUpReqTransfer.getMainMajor());
//        System.out.println(postUserSignUpReqTransfer.getSubMajor());
//        System.out.println(postUserSignUpReqTransfer.getSemester());
//        System.out.println(postUserSignUpReqTransfer.getLastSemesterScore());
//        System.out.println(postUserSignUpReqTransfer.getStudentCategory());
//        System.out.println(postUserSignUpReqTransfer.getEmploymentState());
//        System.out.println(postUserSignUpReqTransfer.getFoundationCategory());
//        System.out.println(postUserSignUpReqTransfer.getResidentCategory());
//        System.out.println(postUserSignUpReqTransfer.getLifeCategory());
//        System.out.println(postUserSignUpReqTransfer.getCovidCategory());
//        System.out.println(postUserSignUpReqTransfer.getBookmark());
//        System.out.println(postUserSignUpReqTransfer.getProvider());
//        System.out.println(postUserSignUpReqTransfer.getFCMTOKEN());
      //      String email = postUserSignUpReqTransfer.getEmail();
//
//        if (email.contains("jr")){
//            email = email.replace("jr","");
//        }
//        if (uid.contains("@jr.naver.com")) {
//            email = uid.replace("jr.","");
//        } else {
//            email = uid;
//        }


        String specialStatus = String.join(" ", postUserSignUpReqTransfer.getSpecialStatus());
        String employmentState = String.join(" ", postUserSignUpReqTransfer.getEmploymentState());
        String studentCategory = String.join(" ", postUserSignUpReqTransfer.getStudentCategory());
        String empolyCategory = String.join(" ", postUserSignUpReqTransfer.getEmpolyCategory());
        String foundationCategory = String.join(" ", postUserSignUpReqTransfer.getFoundationCategory());
        String residentCategory = String.join(" ", postUserSignUpReqTransfer.getResidentCategory());
        String lifeCategory = String.join(" ", postUserSignUpReqTransfer.getLifeCategory());
        String covidCategory = String.join(" ", postUserSignUpReqTransfer.getCovidCategory());
        String bookmark = String.join(" ", postUserSignUpReqTransfer.getBookmark());

        PostUserSingUpReq postUserSingUpReq = new PostUserSingUpReq(postUserSignUpReqTransfer.getEmail(), postUserSignUpReqTransfer.getName(), postUserSignUpReqTransfer.getPwd()
                , postUserSignUpReqTransfer.getPhNum(), postUserSignUpReqTransfer.getGender(), postUserSignUpReqTransfer.getBirth(), postUserSignUpReqTransfer.getAddress(), postUserSignUpReqTransfer.getAddressDetail(), specialStatus, postUserSignUpReqTransfer.getIncomeLevel(),
                postUserSignUpReqTransfer.getIncomeAvg(), employmentState, postUserSignUpReqTransfer.getSchoolRecords(), postUserSignUpReqTransfer.getSchool(), postUserSignUpReqTransfer.getMainMajor(), postUserSignUpReqTransfer.getSubMajor(), postUserSignUpReqTransfer.getSemester(),
                postUserSignUpReqTransfer.getLastSemesterScore(), studentCategory, empolyCategory, foundationCategory, residentCategory, lifeCategory, covidCategory, bookmark, postUserSignUpReqTransfer.getProvider(), postUserSignUpReqTransfer.getFCMTOKEN());
//
        // System.out.println(postUserSingUpReq);
//

        try {
            PostUserSignUpRes postUserSingUpRes = userService.createmailsignup(uid, postUserSingUpReq);
            return new BaseResponse<>(postUserSingUpRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }


//        //아이디 비밀번호 이름 폰번호 null 금지 처리
//        if (postUserReq.getId() == null) {
//            return new BaseResponse<>(POST_USERS_EMPTY_ID);
//        }
//        if (postUserReq.getPassword() == null) {
//            return new BaseResponse<>(POST_USERS_EMPTY_PASSWORD);
//        }
//        if (postUserReq.getName() == null) {
//            return new BaseResponse<>(POST_USERS_EMPTY_NAME);
//        }
//        if (postUserReq.getPhoneNumber() == null) {
//            return new BaseResponse<>(POST_USERS_EMPTY_PHONENUMBER);
//        }
//
//        //아이디 정규표현  아이디@네이버.com
//        if (!isRegexEmail(postUserReq.getId())) {
//            return new BaseResponse<>(POST_USERS_INVALID_EMAIL);
//        }
//        // 비밀번호 정규표현
////        if (!(isRegexPassowrd1(postUserReq.getPassword())||isRegexPassowrd2(postUserReq.getPassword())||isRegexPassowrd3(postUserReq.getPassword())||isRegexPassword4(postUserReq.getPassword()))) {
////            return new BaseResponse<>(POST_USERS_INVALID_COMBINE_PASSWORD);
////        }
//        // System.out.println(postUserReq.getPassword());
//        if (!(isRegexPassowrd1(postUserReq.getPassword()) || isRegexPassowrd2(postUserReq.getPassword()) || isRegexPassowrd3(postUserReq.getPassword()) || isRegexPassword4(postUserReq.getPassword()))) {
//            return new BaseResponse<>(POST_USERS_INVALID_COMBINE_PASSWORD);
//        }
//        //비밀번호 3개문자 정규표햔
////        if (!isRegexPassword5(postUserReq.getPassword())){
////            return new BaseResponse<>(POST_USERS_INVALID_CONNECT_PASSWORD);
////        }
//
//        //비밀번호 아이디와 중복된것 반환
//        // if
//        //휴대번호 정규 표현
//        if (!isRegexPhone(postUserReq.getPhoneNumber())) {
//            return new BaseResponse<>(POST_USERS_INVALID_PHONE);
//        }
//
//        try {
//            PostUserRes postUserRes = userService.createUser(postUserReq);
//            return new BaseResponse<>(postUserRes);
//        } catch (BaseException exception) {
//            return new BaseResponse<>((exception.getStatus()));
//        }

    /**
     * 회원가입 시 이메일 중복확인 API
     */
    @ResponseBody
    @GetMapping("/checkemail")
    public BaseResponse<GetUserCheckEmail> getUserCheckEmail(@RequestParam("email") String email) throws BaseException {
        try {
            GetUserCheckEmail getUserCheckEmail = new GetUserCheckEmail(userProvider.getUsercheckemail(email));
            return new BaseResponse<>(getUserCheckEmail);
        } catch (Exception e) {
            return new BaseResponse<>(DATABASE_ERROR);
        }
    }

    /**
     * 구글 페북 애플 default uid가 db에 있는지 확인하는 부분
     */
    @ResponseBody
    @GetMapping("/checkuid")
    public BaseResponse<GetUserCheckEmail> getUsercheckuid(@RequestHeader("uid") String uid) throws BaseException {
        try {
            GetUserCheckEmail getUserCheckEmail = new GetUserCheckEmail(userProvider.getUsercheckuid(uid));
            return new BaseResponse<>(getUserCheckEmail);
        } catch (Exception e) {
            return new BaseResponse<>(DATABASE_ERROR);
        }
    }

    /**
     * default 일때 아이디 찾는 부분
     */
    @ResponseBody
    @GetMapping("findId")
    public BaseResponse<GetUserFindId> getUserfindId(@RequestParam("name") String name,
                                                     @RequestParam("phNum") String phNum) throws BaseException {

        GetUserFindId getUserFindId = userProvider.getUserFindId(name, phNum);
        return new BaseResponse<>(getUserFindId);
    }

    /**
     * 유저 수정시 유저 정보 제공
     */
    @ResponseBody
    @GetMapping("userInfo")
    public BaseResponse<GetUserModifyReqTransfer> getUserInfoRes(@RequestHeader("uid") String uid) throws BaseException {
        GetUserModifyReqTransfer getUserModifyReqTransfer = userProvider.getUserInfoRes(uid);
        return new BaseResponse<>(getUserModifyReqTransfer);
    }

    /**
     * 유저 정보 수정
     */
    @ResponseBody
    @PatchMapping("usermodify")
    public BaseResponse<GetUserModifyRes> getUserModify(@RequestHeader("uid") String uid,
                                                        @RequestBody GetUserModifyReqTransfer getUserModifyReqTransfer) throws Exception {


        String specialStatus = String.join(" ", getUserModifyReqTransfer.getSpecialStatus());
        String employmentState = String.join(" ", getUserModifyReqTransfer.getEmploymentState());
        String studentCategory = String.join(" ", getUserModifyReqTransfer.getStudentCategory());
        String empolyCategory = String.join(" ", getUserModifyReqTransfer.getEmpolyCategory());
        String foundationCategory = String.join(" ", getUserModifyReqTransfer.getFoundationCategory());
        String residentCategory = String.join(" ", getUserModifyReqTransfer.getResidentCategory());
        String lifeCategory = String.join(" ", getUserModifyReqTransfer.getLifeCategory());
        String covidCategory = String.join(" ", getUserModifyReqTransfer.getCovidCategory());
        String bookmark = String.join(" ", getUserModifyReqTransfer.getBookmark());

        GetUserModifyReq getUserModifyReq = new GetUserModifyReq(getUserModifyReqTransfer.getEmail(), getUserModifyReqTransfer.getName(), getUserModifyReqTransfer.getPwd()
                , getUserModifyReqTransfer.getPhNum(), getUserModifyReqTransfer.getGender(), getUserModifyReqTransfer.getBirth(), getUserModifyReqTransfer.getAddress(), getUserModifyReqTransfer.getAddressDetail(), specialStatus, getUserModifyReqTransfer.getIncomeLevel(),
                getUserModifyReqTransfer.getIncomeAvg(), employmentState, getUserModifyReqTransfer.getSchoolRecords(), getUserModifyReqTransfer.getSchool(), getUserModifyReqTransfer.getMainMajor(), getUserModifyReqTransfer.getSubMajor(), getUserModifyReqTransfer.getSemester(),
                getUserModifyReqTransfer.getLastSemesterScore(), studentCategory, empolyCategory, foundationCategory, residentCategory, lifeCategory, covidCategory, bookmark, getUserModifyReqTransfer.getProvider(),getUserModifyReqTransfer.getFCMTOKEN());


        try {
            GetUserModifyRes getUserModifyRes = userService.getUserModifyRes(uid, getUserModifyReq);
            return new BaseResponse<>(getUserModifyRes);
        } catch (BaseException baseException) {
            return new BaseResponse<>((baseException.getStatus()));
        }

    }

    /** 유저정보수정(복지정보 수정)
     *
     */
    @ResponseBody
    @PostMapping("usermodify/welfare")
    public BaseResponse<GetUserModifyRes> patchUserWelfare(@RequestHeader("uid") String uid,
                                                           @RequestHeader("Content-Type") String ContentType,
                                                        @RequestBody PatchUserWelfareReq patchUserWelfareReq) throws Exception {


      //  String specialStatus = String.join(" ", patchUserWelfareReq.getSpecialStatus());
    //    String employmentState = String.join(" ", patchUserWelfareReq.getEmploymentState());
        String studentCategory = String.join(" ", patchUserWelfareReq.getStudentCategory());
        String empolyCategory = String.join(" ", patchUserWelfareReq.getEmpolyCategory());
        String foundationCategory = String.join(" ", patchUserWelfareReq.getFoundationCategory());
        String residentCategory = String.join(" ", patchUserWelfareReq.getResidentCategory());
        String lifeCategory = String.join(" ", patchUserWelfareReq.getLifeCategory());
        String covidCategory = String.join(" ", patchUserWelfareReq.getCovidCategory());
    //    String bookmark = String.join(" ", patchUserWelfareReq.getBookmark());

        PatchUserWelfareStringReq patchUserWelfareStringReq = new PatchUserWelfareStringReq(studentCategory,empolyCategory,foundationCategory,residentCategory,lifeCategory,covidCategory);


        try {
            GetUserModifyRes getUserModifyRes = userService.patchUserWelfareRes(uid, patchUserWelfareStringReq);
            return new BaseResponse<>(getUserModifyRes);
        } catch (BaseException baseException) {
            return new BaseResponse<>((baseException.getStatus()));
        }

    }

    /**
     * 유저정보수정(추가정보
     */
    @ResponseBody
    @PostMapping("usermodify/plusinfo")
    public BaseResponse<GetUserModifyRes> patchUserPlusInfo(@RequestHeader("userUid") String userUid,
                                                            @RequestBody PatchUserPlusInfoList patchUserPlusInfoList) throws BaseException{
        try{
            String specialStatus = String.join(" ",patchUserPlusInfoList.getSpecialStatus());
            String employmentState = String.join(" ",patchUserPlusInfoList.getEmploymentState());
            PatchUserPlusInfo patchUserPlusInfo = new PatchUserPlusInfo(patchUserPlusInfoList.getAddress(),patchUserPlusInfoList.getAddressDetail(),specialStatus,patchUserPlusInfoList.getIncomeLevel(),patchUserPlusInfoList.getIncomeAvg(),employmentState,patchUserPlusInfoList.getSchoolRecords(),patchUserPlusInfoList.getSchool(),patchUserPlusInfoList.getMainMajor(),patchUserPlusInfoList.getSubMajor(),patchUserPlusInfoList.getSemester(),patchUserPlusInfoList.getLastSemesterScore());
            GetUserModifyRes getUserModifyRes = userService.patchUserPlusInfo(userUid,patchUserPlusInfo);
            return new BaseResponse<>(getUserModifyRes);
        } catch (BaseException baseException) {
            return new BaseResponse<>((baseException.getStatus()));
        }
    }



    /** 유저정보수정 (개인정보)
     *
     */
    @ResponseBody
    @PostMapping("usermodify/privacy")
    public BaseResponse<GetUserModifyRes> patchUserWelfare(@RequestHeader("uid") String uid,
                                                           @RequestBody PatchUserPrivacyReq patchUserPrivacyReq) throws Exception {

        try {
//            UserRecord userRecord = FirebaseAuth.getInstance().getUser(uid);
//            System.out.println(userRecord);
          //  System.out.println(userProvider.checkProvider(uid));
            if (userProvider.checkProvider(uid).equals("default")) {        //default만 파베랑 연동
                UserRecord.UpdateRequest request = new UserRecord.UpdateRequest(uid)
                .setEmail(patchUserPrivacyReq.getEmail());
                UserRecord userRecord = FirebaseAuth.getInstance().updateUser(request);
                System.out.println("Successfully updated user: " + userRecord.getUid()); }

            GetUserModifyRes getUserModifyRes = userService.patchUserPrivacy(uid, patchUserPrivacyReq);
            return new BaseResponse<>(getUserModifyRes);
        } catch (BaseException baseException) {
            return new BaseResponse<>((baseException.getStatus()));
        }

    }


//rlMaJwc62uTUDA25gxlxrcB82XU2
        /**
             * 유저 삭제 (탈퇴)
             */
    @ResponseBody
    @Transactional(rollbackFor = {Exception.class,BaseException.class})
    @DeleteMapping("/userdelete")
    public BaseResponse<DeleteUserRes> deleteUserRes(@RequestHeader("uid") String uid) throws Exception,BaseException {
        try {
            try {
                FirebaseAuth.getInstance().deleteUser(uid);
            } catch (Exception exception) {
                return new BaseResponse<>(DELETE_USERS_FIREBASE);
            }
            DeleteUserRes deleteUserRes = userService.deleteUserRes(uid);
            return new BaseResponse<>(deleteUserRes);
        } catch (BaseException baseException) {
            return new BaseResponse<>((baseException.getStatus()));
        }
    }

    /**
     * 북마크 현재상태 알아보기
     */
    @ResponseBody
    @GetMapping("/bookmark")
    public BaseResponse<PostBookmarkRes> getBookmarkRes(@RequestHeader("userUid") String userUid,
                                                        @RequestHeader("welfareUid") String welfareUid) {
        PostBookmarkRes postBookmarkRes = userProvider.postBookmarkRes(userUid,welfareUid);
        return new BaseResponse<>(postBookmarkRes);
    }


    /** 북마크 등록
     *
     */
    @ResponseBody
    @PostMapping("/bookmark")
    public BaseResponse<PostBookmarkRes> postBookmarkRes(@RequestHeader("userUid") String userUid,
                                                        @RequestHeader("welfareUid") String welfareUid) {

        PostBookmarkRes postBookmarkRes = userService.postBookmarkRes(userUid,welfareUid);
        return new BaseResponse<>(postBookmarkRes);
    }

    /**
     * fcm등록(아토)
     */
    @ResponseBody
    @PostMapping("/fcmplus")
    public BaseResponse<Postfcmplus> fcmplus(@RequestHeader("userUid") String userUid,
                                             @RequestHeader("fcmtoken") String fcmtoken) throws BaseException{

        Postfcmplus postfcmplus = userService.fcmplus(userUid, fcmtoken);
        return new BaseResponse<>(postfcmplus);
    }

}


