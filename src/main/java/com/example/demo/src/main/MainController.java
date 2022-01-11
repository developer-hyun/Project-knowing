package com.example.demo.src.main;

import com.example.demo.src.main.model.*;
import com.example.demo.src.user.UserProvider;
import com.example.demo.src.user.UserService;
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
@RequestMapping("/app/mains")
public class MainController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final MainProvider mainProvider;
    @Autowired
    private final MainService mainService;
    @Autowired
    private final JwtService jwtService;


    public MainController(MainProvider mainProvider, MainService mainService, JwtService jwtService) {
        this.mainProvider = mainProvider;
        this.mainService = mainService;
        this.jwtService = jwtService;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(
                String[].class,
                new StringArrayPropertyEditor(null)
        );
    }
//
//    @ResponseBody
//    @GetMapping("mainview")
//    public BaseResponse<GetMainViewRes> getMainViewRes(){
//        GetMainViewRes getMainViewRes = mainProvider.getMainViewRes();
//        return new BaseResponse<>(getMainViewRes);
//    }
    //justtest


    /**
     * 메인 뷰 테스트
     *
     * @return
     */
    @ResponseBody
    @GetMapping("mainview")
    public BaseResponse<GetMainViewRes> getMainViewRes(@RequestHeader(value = "uid") String uid) {
        GetMainViewRes getMainViewRes = mainProvider.getMainViewRes(uid);
        return new BaseResponse<>(getMainViewRes);
    }


    /**
     * 메인뷰
     */
    @ResponseBody
    @GetMapping("/mainpage")
    public BaseResponse<GetMainViewRes> getmain(@RequestHeader(value = "uid") String uid) throws Exception {
        GetMainViewRes getMainViewRes = mainProvider.getuserinfo(uid);
        return new BaseResponse<>(getMainViewRes);
    }


    /**
     * 북마크 GET
     */
    @ResponseBody
    @GetMapping("bookmark")
    public BaseResponse<List<GetStudent>> getBookmark(@RequestHeader(value = "uid") String uid) throws Exception {
        List<GetStudent> getStudents = mainProvider.getBookmark(uid);
        return new BaseResponse<>(getStudents);
    }

    /**
     * 삭제하면 북마크
     */
    @ResponseBody
    @Transactional
    @DeleteMapping("bookmark")
    public BaseResponse<List<GetStudent>> deletebookmark(@RequestHeader(value = "userUid") String userUid,
                                                         @RequestHeader(value = "welfareuid") String welfareUid) throws Exception {
        mainProvider.deleteBookmark(welfareUid);
        List<GetStudent> getStudents = mainProvider.getBookmark(userUid);
        return new BaseResponse<>(getStudents);
    }

    /**
     * 정책번호 받아서 정책내용 주는 것
     */
    @ResponseBody
    @GetMapping("/welfareInfo")
    public BaseResponse<GetStudent> getwelfareinfo(@RequestHeader(value = "uid") String uid) {
        GetStudent getStudent = mainProvider.getwelfareinfo(uid);
        return new BaseResponse<>(getStudent);
    }

    //
//    /**
//     * 파이어베이스 accesstoken  발급받기
//     */
    @ResponseBody
    @GetMapping("/testversion")
    public BaseResponse<String> testverson() throws Exception {
        String test = "success";
        String token = "eflR1ohyRPutLNFosIm1MS:APA91bEeFLyWdrmgUIKjJ8nXyqyN_qhqLrf1ZV1NfvMCwfnXkJzm6pie_92umSFIMyH5QNik2F9wvLaWHIEbwZ9Neanvx2Gn4RdJY9TfTk6VTVxIWcQjZxyZvC0G2XhA__T0flAcylfa";


        mainService.tett(token);

        return new BaseResponse<>(test);

    }

    /**
     * 알림조회 (상세페이지)
     */
    @ResponseBody
    @GetMapping("/alarm/detailPage")
    public BaseResponse<GetAlarmRes> getAlarmRes(@RequestHeader(value = "userUid") String userUid,
                                                 @RequestHeader(value = "welfareUid") String welfareUid) throws BaseException {
        try {
            GetAlarmRes getAlarmRes = mainProvider.getAlarmRes(userUid, welfareUid);
            return new BaseResponse<>(getAlarmRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }


    }

    /**
     * 알림 생성(상세페이지)
     */
    @ResponseBody
    @PostMapping("/alarm/detailPage")
    public BaseResponse<GetAlarmRes> postAlarmRes(@RequestHeader(value = "userUid") String userUid,
                                                  @RequestHeader(value = "welfareUid") String welfareUid) throws BaseException{
        try {
            GetAlarmRes getAlarmRes = mainProvider.postalarmres(userUid, welfareUid);
            return new BaseResponse<>(getAlarmRes);
        } catch (BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 알림 클릭했을때 뜨는 정보들
     */
    @ResponseBody
    @GetMapping("/alarm/alarmPage")
    public BaseResponse<GetAlarmPageRes> getAlarmPageRes(@RequestHeader(value = "userUid") String userUid) throws BaseException {
        try {


            GetAlarmPageRes getAlarmPageRes = mainProvider.getAlarmPageRes(userUid);
            return new BaseResponse<>(getAlarmPageRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 알림 하나 삭제 부분
     */
    @ResponseBody
    @DeleteMapping("/alarm/alarmPage")
    public BaseResponse<GetAlarmPageRes> deleteOneAlarm(@RequestHeader(value = "userUid") String userUid,
                                                      @RequestHeader(value = "alarmUid") String alarmUid) throws BaseException {
        try{
            GetAlarmPageRes getAlarmPageRes = mainProvider.deleteOneAlarmRes(userUid,alarmUid);
            return new BaseResponse<>(getAlarmPageRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 알림 전체 삭제 부분
     */
    @ResponseBody
    @DeleteMapping("/alarm/alarmPage/total")
    public BaseResponse<GetAlarmPageRes> deleteAllAlarm(@RequestHeader(value="userUid") String userUid) throws BaseException {
        try {
            GetAlarmPageRes getAlarmPageRes = mainProvider.deleteAllAlarm(userUid);
            return new BaseResponse<>(getAlarmPageRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 알림 누르면 read false로 변경하는 부분
     */
    @ResponseBody
    @PostMapping("/alarm/alarmPage/read")
    public BaseResponse<GetAlarmPageRes> patchOneAlarm(@RequestHeader(value = "userUid") String userUid,
                                                       @RequestHeader(value = "alarmUid") String alarmUid) throws BaseException {
        try{
            GetAlarmPageRes getAlarmPageRes = mainProvider.patchOneAlarm(userUid, alarmUid);
            return new BaseResponse<>(getAlarmPageRes);
        }catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 공지사항
     */
    @ResponseBody
    @GetMapping("/notice")
    public BaseResponse<List<notice>> getnotice() throws BaseException {

        List<notice> notice = mainProvider.getnotice();
        return new BaseResponse<>(notice);
    }
}

