package com.example.demo.src.main;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.main.model.*;
import com.example.demo.src.user.UserDao;
import com.example.demo.src.user.model.*;
import com.example.demo.utils.JwtService;
import com.google.api.services.storage.Storage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

//Provider : Read의 비즈니스 로직 처리
@Service
public class MainProvider {

    private final MainDao mainDao;
    private final JwtService jwtService;


    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public MainProvider(MainDao mainDao, JwtService jwtService) {
        this.mainDao = mainDao;
        this.jwtService = jwtService;
    }
//
//    public GetMainViewRes getMainViewRes(){
//        String name = "최성락";
//        String maxMoney = "17000000";
//        List<GetStudent> getStudents = mainDao.getStudents();
//
//        GetMainViewRes getMainViewRes = new GetMainViewRes(name,maxMoney,getStudents,getStudents,getStudents,getStudents,getStudents,getStudents);
//        return getMainViewRes;
//
//    }

    /** 테스트버전 메인화면
     *
     * @return
     */
    public GetMainViewRes getMainViewRes(String uid) {

        GetTotalCategory getTotalCategory = new GetTotalCategory(mainDao.getStudents(),mainDao.gettest(),mainDao.gettest2(),mainDao.gettest2(),mainDao.gettest(),mainDao.gettest2());

        GetMainViewRes getMainViewRes = new GetMainViewRes(mainDao.getStudents(),mainDao.gettest(),mainDao.gettest2(),mainDao.gettest2(),mainDao.gettest(),mainDao.gettest2(),getTotalCategory);
        return getMainViewRes;
    }

    /** 메인화면을 위해 유저정보 가져오기
     *
     */
    public GetMainViewRes getuserinfo(String uid) throws Exception{
      //  String uid = "MHQ72TN4d8dFFL2b74Ldy4s3EHa2";
        GetUserInfoloop getUserInfoloop = mainDao.getUserInfoloop(uid);
       // System.out.println(getUserInfoloop.getBirth());
        int birth = getUserInfoloop.getBirth();
        String birth1 = Integer.toString(birth);
        Date date = new SimpleDateFormat("yyyyMMdd").parse(birth1);
        String birth2 = new SimpleDateFormat("yyyy").format(date);  //년도만
        int birth3 = Integer.parseInt(birth2);

        int year = Calendar.getInstance().get(Calendar.YEAR); //현재년도
        String age = Integer.toString(year-birth3+1);
       // System.out.println(age);  //완 나이


        //시도 - 거주지 부분 user에서 받아서 서울특별시 -> 서울 필터후
        //첫번째 ? 는 address      두번째 ? 는 address+addressDeatil
        String address = getUserInfoloop.getAddress();
        if (address.equals("서울특별시")) {
            address = "서울";
        }
        if (address.equals("인천광역시")) {
            address = "인천";
        }
        if (address.equals("세종특별자치시")) {
            address = "세종";
        }
        String empty=" ";
        String addressDetail = getUserInfoloop.getAddressDatil();
        addressDetail = address+empty+addressDetail;

      //  System.out.println(address);
      //  System.out.println(addressDetail);

        //거주지 끝

        //특화분야  6개  없으면 none     none은 없다고 변환 할 필요는 없음 왜냐ㅕ면 제한이 없으면 다되는거니까
        String special1 = "%홅%";
        String special2 = "%홅%";
        String special3 = "%홅%";
        String special4 = "%홅%";
        String special5 = "%홅%";
        String special6 = "%홅%";


        if (getUserInfoloop.getSpecialStatus().contains("중소기업")) {
            special1 = "%중소기업%";
        }
        if (getUserInfoloop.getSpecialStatus().contains("저소득층")) {
            special2 = "%저소득층%";
        }
        if (getUserInfoloop.getSpecialStatus().contains("장애인")) {
            special3 = "%장애인%";
        }
        if (getUserInfoloop.getSpecialStatus().contains("농업인")) {
            special4 = "%농업인%";
        }
        if (getUserInfoloop.getSpecialStatus().contains("군인")) {
            special5 = "%군인%";
        }
        if(getUserInfoloop.getSpecialStatus().contains("지역인재")) {
            special6 = "%지역인재%";
        }
        //특화분야 끝


        //소득, 중위분위
        //그냥 소득 혹은 중위소득 주면됨
        String incomeLevel = getUserInfoloop.getIncomeLevel();
        String incomeAvg = getUserInfoloop.getIncomeAvg();
        int incomeLevelInt = Integer.parseInt(incomeLevel);
        int incomeAvgInt = Integer.parseInt(incomeAvg);
//        System.out.println(incomeLevelInt);
//        System.out.println(incomeAvgInt);

        //취업상태 부분
        String empoly1 = "%홅%";
        String empoly2 = "%홅%";
        String empoly3 = "%홅%";
        String empoly4 = "%홅%";
        String empoly5 = "%홅%";
        String empoly6 = "%홅%";
        String empoly7 = "%홅%";
        String empoly8 = "%홅%";

        if(getUserInfoloop.getEmploymentState().equals("전체")){
            empoly1 = "%미취업자%";
            empoly2 = "%재직자%";
            empoly3 = "%프리랜서%";
            empoly4 = "%일용근로자%";
            empoly5 = "%단기근로자%";
            empoly6 = "%예비창업자%";
            empoly7 = "%자영업자%";
            empoly8 = "%영농종사자%";
        }

        if (getUserInfoloop.getEmploymentState().contains("미취업자")) {
            empoly1 = "%미취업자%";
        }
        if (getUserInfoloop.getEmploymentState().contains("재직자")) {
            empoly2 = "%재직자%";
        }
        if (getUserInfoloop.getEmploymentState().contains("프리랜서")) {
            empoly3 = "%프리랜서%";
        }
        if (getUserInfoloop.getEmploymentState().contains("일용근로자")) {
            empoly4 = "%일용근로자%";
        }
        if (getUserInfoloop.getEmploymentState().contains("단기근로자")) {
            empoly5 = "%단기근로자%";
        }
        if (getUserInfoloop.getEmploymentState().contains("예비창업자")) {
            empoly6 = "%예비창업자%";
        }
        if (getUserInfoloop.getEmploymentState().contains("자영업자")) {
            empoly7 = "%자영업자%";
        }
        if (getUserInfoloop.getEmploymentState().contains("영농종사자")) {
            empoly8 = "%영농종사자%";
        }



        //학력
        String school1 = "%홅%";
        String school2 = "%홅%";
        String school3 = "%홅%";
        String school4 = "%홅%";
        String school5 = "%홅%";
        if (getUserInfoloop.getSchoolRecords().contains("전체")) {
            school1 = "%고졸미만%";
            school2 = "%고교졸업%";
            school3 = "%대학재학%";
            school4 = "%대학졸업%";
            school5 = "%석박사%";
        }

        if (getUserInfoloop.getSchoolRecords().contains("고졸미만")) {
            school1 = "%고졸미만%";
        }
        if(getUserInfoloop.getSchoolRecords().contains("고교졸업")) {
            school2 = "%고교졸업%";
        }
        if(getUserInfoloop.getSchoolRecords().contains("대학재학")) {
            school3 = "%대학재학%";
        }
        if(getUserInfoloop.getSchoolRecords().contains("대학졸업")) {
            school4 = "%대학졸업%";
        }
        if (getUserInfoloop.getSchoolRecords().contains("석박사")) {
            school5 = "%석박사%";
        }



        //지원형태 부분 맨 마지막
        String type1 = "%홅%";
        String type2 = "%홅%";
        String type3 = "%홅%";
        String type4 = "%홅%";
        String type5 = "%홅%";
        String type6 = "%홅%";
        String type7 = "%홅%";
        String type8 = "%홅%";
        String type9 = "%홅%";
        String type10 = "%홅%";
        String type11 = "%홅%";
        String type12 = "%홅%";
        String type13 = "%홅%";
        String type14 = "%홅%";
        String type15 = "%홅%";
        String type16 = "%홅%";
        String type17 = "%홅%";
        String type18 = "%홅%";
        String type19 = "%홅%";
        String type20 = "%홅%";

        String studentCategory[] = getUserInfoloop.getStudentCategory().split(" ");
      //  System.out.println(category);
       // System.out.println(type1);
        if(getUserInfoloop.getStudentCategory().contains("교내장학금")){
            type1 = "%교내장학금%";
        }
        if(getUserInfoloop.getStudentCategory().contains("교외장학금")){
            type2 = "%교외장학금%";
        }
        //학생지원 받는부분
        List<GetStudent> getStudent = mainDao.getMainViewRes(age,type1,type2,type3,type4,type5,type6,address,addressDetail,special1,special2,special3,special4,special5,special6,incomeLevelInt,incomeAvgInt,empoly1,empoly2,empoly3,empoly4,empoly5,empoly6,empoly7,empoly8,school1,school2,school3,school4,school5);

        type1 = "%홅%";
        type2 = "%홅%";
        type3 = "%홅%";
        type4 = "%홅%";
        type5 = "%홅%";
        type6 = "%홅%";

        // 취업지원
        if(getUserInfoloop.getEmpolyCategory().contains("구직활동지원인턴")){
            type1 = "%구직활동지원인턴%";
        }
        if(getUserInfoloop.getEmpolyCategory().contains("중소중견기업취업지원")) {
            type2 = "%중소중견기업취업지원%";
        }
        if(getUserInfoloop.getEmpolyCategory().contains("특수분야취업지원")) {
            type3 = "%특수분야취업지원%";
        }
        if(getUserInfoloop.getEmpolyCategory().contains("해외취업및진출지원")) {
            type4 = "%해외취업및진출지원%";
        }

        //취업지원 받는 부분
        List<GetStudent> getEmpoly = mainDao.getMainViewRes(age,type1,type2,type3,type4,type5,type6,address,addressDetail,special1,special2,special3,special4,special5,special6,incomeLevelInt,incomeAvgInt,empoly1,empoly2,empoly3,empoly4,empoly5,empoly6,empoly7,empoly8,school1,school2,school3,school4,school5);

        type1 = "%홅%";
        type2 = "%홅%";
        type3 = "%홅%";
        type4 = "%홅%";
        type5 = "%홅%";
        type6 = "%홅%";

        //창업지원
        if(getUserInfoloop.getFoundationCategory().contains("창업운영지원")) {
            type1 = "%창업운영지원%";

        }
        if(getUserInfoloop.getFoundationCategory().contains("경영지원")) {
            type2 = "%경영지원%";
        }
        if (getUserInfoloop.getFoundationCategory().contains("자본금지원")) {
            type3 = "%자본금지원%";
        }
        //창업지원 받는 부분
        List<GetStudent> getFoundation = mainDao.getMainViewRes(age,type1,type2,type3,type4,type5,type6,address,addressDetail,special1,special2,special3,special4,special5,special6,incomeLevelInt,incomeAvgInt,empoly1,empoly2,empoly3,empoly4,empoly5,empoly6,empoly7,empoly8,school1,school2,school3,school4,school5);

        type1 = "%홅%";
        type2 = "%홅%";
        type3 = "%홅%";
        type4 = "%홅%";
        type5 = "%홅%";
        type6 = "%홅%";

        //주거 금융지원
        if(getUserInfoloop.getResidentCategory().contains("생활비지원금융혜택")) {
            type1 = "%생활비지원금융혜택";
        }
        if(getUserInfoloop.getResidentCategory().contains("주거지원")) {
            type2="%주거지원%";
        }
        if(getUserInfoloop.getResidentCategory().contains("학자금지원")) {
            type3="%학자금지원%";
        }
        //주거금융지원받는부분
        List<GetStudent>  getResident = mainDao.getMainViewRes(age,type1,type2,type3,type4,type5,type6,address,addressDetail,special1,special2,special3,special4,special5,special6,incomeLevelInt,incomeAvgInt,empoly1,empoly2,empoly3,empoly4,empoly5,empoly6,empoly7,empoly8,school1,school2,school3,school4,school5);

        type1 = "%홅%";
        type2 = "%홅%";
        type3 = "%홅%";
        type4 = "%홅%";
        type5 = "%홅%";
        type6 = "%홅%";

        //생활복지지원
        if(getUserInfoloop.getLifeCategory().contains("건강")) {
            type1 = "%건강%";
        }
        if(getUserInfoloop.getLifeCategory().contains("문화")) {
            type2 = "%문화%";
        }
        //생활복지지원받는부분
        List<GetStudent>  getLife = mainDao.getMainViewRes(age,type1,type2,type3,type4,type5,type6,address,addressDetail,special1,special2,special3,special4,special5,special6,incomeLevelInt,incomeAvgInt,empoly1,empoly2,empoly3,empoly4,empoly5,empoly6,empoly7,empoly8,school1,school2,school3,school4,school5);

        type1 = "%홅%";
        type2 = "%홅%";
        type3 = "%홅%";
        type4 = "%홅%";
        type5 = "%홅%";
        type6 = "%홅%";

        //코로나19지원
        if(getUserInfoloop.getCovidCategory().contains("기본소득지원")) {
            type1="%기본소득지원%";
        }
        if(getUserInfoloop.getCovidCategory().contains("저소득층지원")) {
            type2="%저소득층지원%";
        }
        if(getUserInfoloop.getCovidCategory().contains("재난피해지원")) {
            type3="%재난피해지원%";
        }
        if(getUserInfoloop.getCovidCategory().contains("소득일자리보전")) {
            type4="%소득일자리보전%";
        }
        if(getUserInfoloop.getCovidCategory().contains("소득일자리 보전")) {
            type4="%소득일자리보전%";
        }
        if(getUserInfoloop.getCovidCategory().contains("기타인센티브")) {
            type5="%기타인센티브%";
        }
        if(getUserInfoloop.getCovidCategory().contains("심리지원")) {
            type6="%심리지원%";
        }
        System.out.println(type4);
        //코로나19받는부분
        List<GetStudent> getCovid = mainDao.getMainViewRes(age,type1,type2,type3,type4,type5,type6,address,addressDetail,special1,special2,special3,special4,special5,special6,incomeLevelInt,incomeAvgInt,empoly1,empoly2,empoly3,empoly4,empoly5,empoly6,empoly7,empoly8,school1,school2,school3,school4,school5);




        //전체 복지정보에서 학생,취업,창업 등등 나누는 부분
        type1 = "%교내장학금%";
        type2 = "%교외장학금%";
        type3 = "%홅%";
        type4 = "%홅%";
        type5 = "%홅%";
        type6 = "%홅%";

        if(getUserInfoloop.getStudentCategory().contains("교내장학금")){
            type1 = "%교내장학금%";
        }
        if(getUserInfoloop.getStudentCategory().contains("교외장학금")){
            type2 = "%교외장학금%";
        }
        //학생지원 받는부분
        List<GetStudent> gettotalStudent = mainDao.getMainViewtotalres(type1,type2,type3,type4,type5,type6);

        type1 = "%구직활동지원인턴%";
        type2 = "%중소중견기업취업지원%";
        type3 = "%특수분야취업지원%";
        type4 = "%해외취업및진출지원%";
        type5 = "%홅%";
        type6 = "%홅%";

        // 취업지원
        if(getUserInfoloop.getEmpolyCategory().contains("구직활동지원인턴")){
            type1 = "%구직활동지원인턴%";
        }
        if(getUserInfoloop.getEmpolyCategory().contains("중소중견기업취업지원")) {
            type2 = "%중소중견기업취업지원%";
        }
        if(getUserInfoloop.getEmpolyCategory().contains("특수분야취업지원")) {
            type3 = "%특수분야취업지원%";
        }
        if(getUserInfoloop.getEmpolyCategory().contains("해외취업및진출지원")) {
            type4 = "%해외취업및진출지원%";
        }

        //취업지원 받는 부분
        List<GetStudent> gettotalempoly = mainDao.getMainViewtotalres(type1,type2,type3,type4,type5,type6);


        type1 = "%창업운영지원%";
        type2 = "%경영지원%";
        type3 = "%자본금지원%";
        type4 = "%홅%";
        type5 = "%홅%";
        type6 = "%홅%";
        //창업지원
        if(getUserInfoloop.getFoundationCategory().contains("창업운영지원")) {
            type1 = "%창업운영지원%";

        }
        if(getUserInfoloop.getFoundationCategory().contains("경영지원")) {
            type2 = "%경영지원%";
        }
        if (getUserInfoloop.getFoundationCategory().contains("자본금지원")) {
            type3 = "%자본금지원%";
        }
        //창업지원 받는 부분
        List<GetStudent> gettotalfoundation = mainDao.getMainViewtotalres(type1,type2,type3,type4,type5,type6);

        type1 = "%생활비지원금융혜택";
        type2 = "%주거지원%";
        type3 = "%학자금지원%";
        type4 = "%홅%";
        type5 = "%홅%";
        type6 = "%홅%";
        //주거 금융지원
        if(getUserInfoloop.getResidentCategory().contains("생활비지원금융혜택")) {
            type1 = "%생활비지원금융혜택";
        }
        if(getUserInfoloop.getResidentCategory().contains("주거지원")) {
            type2="%주거지원%";
        }
        if(getUserInfoloop.getResidentCategory().contains("학자금지원")) {
            type3="%학자금지원%";
        }
        //주거금융지원받는부분
        List<GetStudent>  gettotalResident = mainDao.getMainViewtotalres(type1,type2,type3,type4,type5,type6);


        type1 = "%건강%";
        type2 = "%문화%";
        type3 = "%홅%";
        type4 = "%홅%";
        type5 = "%홅%";
        type6 = "%홅%";
        //생활복지지원
        if(getUserInfoloop.getLifeCategory().contains("건강")) {
            type1 = "%건강%";
        }
        if(getUserInfoloop.getLifeCategory().contains("문화")) {
            type2 = "%문화%";
        }
        //생활복지지원받는부분
        List<GetStudent>  gettotalLife = mainDao.getMainViewtotalres(type1,type2,type3,type4,type5,type6);


        type1 = "%기본소득지원%";
        type2 = "%저소득층지원%";
        type3 = "%재난피해지원%";
        type4 = "%소득일자리보전%";
        type5 = "%기타인센티브%";
        type6 = "%심리지원%";
        //코로나19지원
        if(getUserInfoloop.getCovidCategory().contains("기본소득지원")) {
            type1="%기본소득지원%";
        }
        if(getUserInfoloop.getCovidCategory().contains("저소득층지원")) {
            type2="%저소득층지원%";
        }
        if(getUserInfoloop.getCovidCategory().contains("재난피해지원")) {
            type3="%재난피해지원%";
        }
        if(getUserInfoloop.getCovidCategory().contains("소득일자리보전")) {
            type4="%소득일자리보전%";
        }
        if(getUserInfoloop.getCovidCategory().contains("기타인센티브")) {
            type5="%기타인센티브%";
        }
        if(getUserInfoloop.getCovidCategory().contains("심리지원")) {
            type6="%심리지원%";
        }
        //코로나19받는부분
        List<GetStudent> gettotalCovid = mainDao.getMainViewtotalres(type1,type2,type3,type4,type5,type6);

        //전체 복지 리스트 만들기


        GetTotalCategory getTotalCategory = new GetTotalCategory(gettotalStudent,gettotalempoly,gettotalfoundation,gettotalResident,gettotalLife,gettotalCovid);



        GetMainViewRes getMainViewRes = new GetMainViewRes(getStudent,getEmpoly,getFoundation,getResident,getLife,getCovid,getTotalCategory);
        return getMainViewRes;


    }

    /**
     * 복지번호 받으면 상세페이지  주는 부분
     */
    public GetStudent getwelfareinfo(String uid) {
        GetStudent getStudent = mainDao.getwelfareinfo(uid);
        return getStudent;
    }


    /**
     * 북마크 상세 페이지 정보
     */
    public List<GetStudent> getBookmark(String uid) {
        List<GetStudent> getStudents = mainDao.GetBookmarkRes(uid);
        return getStudents;
    }

    /**북마크 삭제하고 정보제공
     *
     */
    public void deleteBookmark(String userUid) {
        mainDao.deleteBookmark(userUid);
    }


    /**
     * 알람 조회(상세페이지)
     * @param userUid
     * @param welfareUid
     * @return
     */
    public GetAlarmRes getAlarmRes(String userUid,String welfareUid) throws BaseException{
        String result = "";
        if ((mainDao.checkUserUid(userUid))==0){
            throw new BaseException(BaseResponseStatus.NO_USERUID);
        }
        if ((mainDao.checkwelfareUid(welfareUid))==0) {
            throw new BaseException(BaseResponseStatus.NO_WELFAREUID);
        }

        if ((mainDao.checkAlarm(userUid,welfareUid))==1) {        //중복이있다 등록되어있다
            result = "알림등록";
        }
        if ((mainDao.checkAlarm(userUid,welfareUid))==0) {           //알림이없다
            result = "알림삭제";
        }
        GetAlarmRes getAlarmRes = new GetAlarmRes(result);
        return getAlarmRes;
    }

    /**
     * 알림 post 등록 삭제(상세페이지)
     */
    public GetAlarmRes postalarmres(String userUid,String welfareUid) throws BaseException{
        String result="";
        if ((mainDao.checkUserUid(userUid))==0){
            throw new BaseException(BaseResponseStatus.NO_USERUID);
        }
        if ((mainDao.checkwelfareUid(welfareUid))==0) {
            throw new BaseException(BaseResponseStatus.NO_WELFAREUID);
        }

        if ((mainDao.checkAlarm(userUid,welfareUid))==1) {      //중복이있어요 그러면 알람을 킨거니까 다시 눌르면 삭제해야한다.
            mainDao.deleteAlarm(userUid, welfareUid);
            result = "알림삭제";
        } else {
            mainDao.createAlarm(userUid, welfareUid);     //중복이 없으면 생성해야한다.
            result= "알림등록";
        }
        GetAlarmRes getAlarmRes = new GetAlarmRes(result);
        return getAlarmRes;

    }

    /**
     * 알림화면 클릭했을때 뜨는 정보들
     */
    public GetAlarmPageRes getAlarmPageRes(String userUid) throws BaseException {
        if ((mainDao.checkUserUid(userUid))==0){
            throw new BaseException(BaseResponseStatus.NO_USERUID);
        }
//        if ((mainDao.checkwelfareUid(welfareUid))==0) {
//            throw new BaseException(BaseResponseStatus.NO_WELFAREUID);
//        }

       List<GetAlarmList> getAlarmLists = mainDao.getAlarmPageRes(userUid);
       GetAlarmPageRes getAlarmPageRes = new GetAlarmPageRes(getAlarmLists);
        return getAlarmPageRes;
        //List<GetAlarmPageRes> getAlarmPageRes = mainDao.getAlarmPageRes(userUid);
        //return getAlarmPageRes;
    }

    /**
     * 알림 하나 삭제 부분
     */
    public GetAlarmPageRes deleteOneAlarmRes(String userUid,String alarmUid) throws BaseException {
        if ((mainDao.checkUserUid(userUid))==0){
            throw new BaseException(BaseResponseStatus.NO_USERUID);
        }
        if ((mainDao.checkAlarmPage(alarmUid))==0){
            throw new BaseException(BaseResponseStatus.NO_ALARM);
        }
      //  String result="";
        mainDao.deleteOneAlarm(userUid, alarmUid);
        List<GetAlarmList> getAlarmList = mainDao.getAlarmPageRes(userUid);
        GetAlarmPageRes getAlarmPageRes = new GetAlarmPageRes(getAlarmList);
        return getAlarmPageRes;
      //  result="삭제완료";
      //  GetAlarmRes getAlarmRes = new GetAlarmRes(result);

    }
    /**
     * 알림 전체 삭제 부분
     */
    public GetAlarmPageRes deleteAllAlarm(String userUid) throws BaseException{
        if ((mainDao.checkUserUid(userUid))==0){
            throw new BaseException(BaseResponseStatus.NO_USERUID);
        }
        mainDao.deleteAllAlarm(userUid);
        List<GetAlarmList> getAlarmList = mainDao.getAlarmPageRes(userUid);
        GetAlarmPageRes getAlarmPageRes = new GetAlarmPageRes(getAlarmList);
        return getAlarmPageRes;
    }

    /**
     * 알림 누르면  Read false로 변경 부분
     */
    public GetAlarmPageRes patchOneAlarm(String userUid,String alarmUid) throws BaseException{
        if ((mainDao.checkUserUid(userUid))==0){
            throw new BaseException(BaseResponseStatus.NO_USERUID);
        }
        if ((mainDao.checkAlarmPage(alarmUid))==0){
            throw new BaseException(BaseResponseStatus.NO_ALARM);
        }
        mainDao.patchOneAlarm(userUid,alarmUid);
        List<GetAlarmList> getAlarmList = mainDao.getAlarmPageRes(userUid);
        GetAlarmPageRes getAlarmPageRes = new GetAlarmPageRes(getAlarmList);
        return getAlarmPageRes;
    }

}
