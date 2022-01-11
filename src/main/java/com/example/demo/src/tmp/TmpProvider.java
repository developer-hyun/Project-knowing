package com.example.demo.src.tmp;


import com.example.demo.config.BaseException;
import com.example.demo.src.tmp.model.*;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

//Provider : Read의 비즈니스 로직 처리
@Service
public class TmpProvider {

    private final TmpDao tmpDao;
    private final JwtService jwtService;


    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public TmpProvider(TmpDao tmpDao, JwtService jwtService) {
        this.tmpDao = tmpDao;
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
        if(tmpDao.checkuid(email) == 1) {
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
        if (tmpDao.checkEmail(email) == 1) {
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
        if (tmpDao.checkuid(uid) == 1) {
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
    public GetUserFindId getUserFindId(String name, String phNum) throws BaseException {
        try {
            GetUserFindId getUserFindId = tmpDao.getUserFindId(name, phNum);
            return getUserFindId;
        } catch (Exception exception) {
            String email = "none";
            return new GetUserFindId(email);
        }
    }


    /** 메인화면을 위해 유저정보 가져오기
     *
     */

    public GetMainViewRes getuserinfo(String uid) throws Exception{

        GetUserInfoloop getUserInfoloop = tmpDao.getUserInfoloop(uid);
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
        List<com.example.demo.src.tmp.model.GetStudent> getStudent = tmpDao.getMainViewRes(age,type1,type2,type3,type4,type5,type6,address,addressDetail,special1,special2,special3,special4,special5,special6,incomeLevelInt,incomeAvgInt,empoly1,empoly2,empoly3,empoly4,empoly5,empoly6,empoly7,empoly8,school1,school2,school3,school4,school5);

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
            type2 = "%중소중견기업취업%";
        }
        if(getUserInfoloop.getEmpolyCategory().contains("특수분야취업")) {
            type3 = "%특수분야취업%";
        }
        if(getUserInfoloop.getEmpolyCategory().contains("해외취업및진출지원")) {
            type4 = "%해외취업및진출%";
        }

        //취업지원 받는 부분
        List<com.example.demo.src.tmp.model.GetStudent> getEmpoly = tmpDao.getMainViewRes(age,type1,type2,type3,type4,type5,type6,address,addressDetail,special1,special2,special3,special4,special5,special6,incomeLevelInt,incomeAvgInt,empoly1,empoly2,empoly3,empoly4,empoly5,empoly6,empoly7,empoly8,school1,school2,school3,school4,school5);

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
        List<com.example.demo.src.tmp.model.GetStudent> getFoundation = tmpDao.getMainViewRes(age,type1,type2,type3,type4,type5,type6,address,addressDetail,special1,special2,special3,special4,special5,special6,incomeLevelInt,incomeAvgInt,empoly1,empoly2,empoly3,empoly4,empoly5,empoly6,empoly7,empoly8,school1,school2,school3,school4,school5);

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
        List<com.example.demo.src.tmp.model.GetStudent>  getResident = tmpDao.getMainViewRes(age,type1,type2,type3,type4,type5,type6,address,addressDetail,special1,special2,special3,special4,special5,special6,incomeLevelInt,incomeAvgInt,empoly1,empoly2,empoly3,empoly4,empoly5,empoly6,empoly7,empoly8,school1,school2,school3,school4,school5);

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
        List<com.example.demo.src.tmp.model.GetStudent>  getLife = tmpDao.getMainViewRes(age,type1,type2,type3,type4,type5,type6,address,addressDetail,special1,special2,special3,special4,special5,special6,incomeLevelInt,incomeAvgInt,empoly1,empoly2,empoly3,empoly4,empoly5,empoly6,empoly7,empoly8,school1,school2,school3,school4,school5);

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
            type2="%저소득층%";
        }
        if(getUserInfoloop.getCovidCategory().contains("재난피해지원")) {
            type3="%재난피해%";
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
      //  System.out.println(type4);
        //코로나19받는부분
        List<com.example.demo.src.tmp.model.GetStudent> getCovid = tmpDao.getMainViewRes(age,type1,type2,type3,type4,type5,type6,address,addressDetail,special1,special2,special3,special4,special5,special6,incomeLevelInt,incomeAvgInt,empoly1,empoly2,empoly3,empoly4,empoly5,empoly6,empoly7,empoly8,school1,school2,school3,school4,school5);




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
        List<com.example.demo.src.tmp.model.GetStudent> gettotalStudent = tmpDao.getMainViewtotalres(type1,type2,type3,type4,type5,type6);

        type1 = "%구직활동지원인턴%";
        type2 = "%중소중견기업취업%";
        type3 = "%특수분야취업%";
        type4 = "%해외취업및진출%";
        type5 = "%홅%";
        type6 = "%홅%";

        // 취업지원
        if(getUserInfoloop.getEmpolyCategory().contains("구직활동지원인턴")){
            type1 = "%구직활동지원인턴%";
        }
        if(getUserInfoloop.getEmpolyCategory().contains("중소중견기업취업지원")) {
            type2 = "%중소중견기업취업%";
        }
        if(getUserInfoloop.getEmpolyCategory().contains("특수분야취업지원")) {
            type3 = "%특수분야취업%";
        }
        if(getUserInfoloop.getEmpolyCategory().contains("해외취업및진출지원")) {
            type4 = "%해외취업및진출%";
        }

        //취업지원 받는 부분
        List<com.example.demo.src.tmp.model.GetStudent> gettotalempoly = tmpDao.getMainViewtotalres(type1,type2,type3,type4,type5,type6);


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
        List<com.example.demo.src.tmp.model.GetStudent> gettotalfoundation = tmpDao.getMainViewtotalres(type1,type2,type3,type4,type5,type6);

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
        List<com.example.demo.src.tmp.model.GetStudent>  gettotalResident = tmpDao.getMainViewtotalres(type1,type2,type3,type4,type5,type6);


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
        List<com.example.demo.src.tmp.model.GetStudent>  gettotalLife = tmpDao.getMainViewtotalres(type1,type2,type3,type4,type5,type6);


        type1 = "%기본소득지원%";
        type2 = "%저소득층%";
        type3 = "%재난피해%";
        type4 = "%소득일자리보전%";
        type5 = "%기타인센티브%";
        type6 = "%심리지원%";
        //코로나19지원
        if(getUserInfoloop.getCovidCategory().contains("기본소득지원")) {
            type1="%기본소득지원%";
        }
        if(getUserInfoloop.getCovidCategory().contains("저소득층지원")) {
            type2="%저소득층%";
        }
        if(getUserInfoloop.getCovidCategory().contains("재난피해지원")) {
            type3="%재난피해%";
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
        List<GetStudent> gettotalCovid = tmpDao.getMainViewtotalres(type1,type2,type3,type4,type5,type6);

        //전체 복지 리스트 만들기


        GetTotalCategory getTotalCategory = new GetTotalCategory(gettotalStudent,gettotalempoly,gettotalfoundation,gettotalResident,gettotalLife,gettotalCovid);



        com.example.demo.src.tmp.model.GetMainViewRes getMainViewRes = new GetMainViewRes(getStudent,getEmpoly,getFoundation,getResident,getLife,getCovid,getTotalCategory);

        return getMainViewRes;


    }








    /**
     * 유저 정보 제공 부분
     */
    public GetUserModifyReqTransfer getUserInfoRes(String uid) throws BaseException {
//        try {
            GetUserInfoRes getUserInfoRes = tmpDao.getUserInfoRes(uid);
            List<String> specialStatus = Arrays.asList(getUserInfoRes.getSpecialStatus().split(" "));
            List<String> employmentState = Arrays.asList(getUserInfoRes.getEmploymentState().split(" "));
            List<String> studentCategory = Arrays.asList(getUserInfoRes.getStudentCategory().split(" "));
            List<String> empolyCategory = Arrays.asList(getUserInfoRes.getEmpolyCategory().split(" "));
            List<String> foundationCategory = Arrays.asList(getUserInfoRes.getFoundationCategory().split(" "));
            List<String> residentCategory = Arrays.asList(getUserInfoRes.getResidentCategory().split(" "));
            List<String> lifeCategory = Arrays.asList(getUserInfoRes.getLifeCategory().split(" "));
            List<String> covidCategory = Arrays.asList(getUserInfoRes.getMedicalCategory().split(" "));
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
    public PostBookmarkRes postBookmarkRes(String userUid, String welfareUid) {
        String result = "";
        if ((tmpDao.checkbookmark(userUid,welfareUid))==1) {        //중복이있다 등록되어있다
            result = "북마크등록";
        }
        if ((tmpDao.checkbookmark(userUid,welfareUid))==0) {
            result = "북마크삭제";
        }
        PostBookmarkRes postBookmarkRes = new PostBookmarkRes(result);
        return postBookmarkRes;
    }
    /**
     * check provier
     */
    public String checkProvider(String uid) {
        String provider = tmpDao.checkprovider(uid);
        return provider;
    }






//    public List<GetUserRes> getUsers() throws BaseException{
//        try{
//            List<GetUserRes> getUserRes = userDao.getUsers();
//            return getUserRes;
//        }
//        catch (Exception exception) {
//            throw new BaseException(DATABASE_ERROR);
//        }
//    }
//
//    public List<GetUserRes> getUsersByEmail(String email) throws BaseException{
//        try{
//            List<GetUserRes> getUsersRes = userDao.getUsersByEmail(email);
//            return getUsersRes;
//        }
//        catch (Exception exception) {
//            throw new BaseException(DATABASE_ERROR);
//        }
//                    }
//
//
//    public GetUserRes getUser(int userIdx) throws BaseException {
//        try {
//            GetUserRes getUserRes = userDao.getUser(userIdx);
//            return getUserRes;
//        } catch (Exception exception) {
//            throw new BaseException(DATABASE_ERROR);
//        }
//    }
//
//    public int checkEmail(String email) throws BaseException{
//        try{
//            return userDao.checkEmail(email);
//        } catch (Exception exception){
//            throw new BaseException(DATABASE_ERROR);
//        }
//    }
//
//    public PostLoginRes logIn(PostLoginReq postLoginReq) throws BaseException{
//        User user = userDao.getPwd(postLoginReq);
//        String password;
//        try {
//            password = new AES128(Secret.USER_INFO_PASSWORD_KEY).decrypt(user.getPassword());
//        } catch (Exception ignored) {
//            throw new BaseException(PASSWORD_DECRYPTION_ERROR);
//        }
//
//        if(postLoginReq.getPassword().equals(password)){
//            int userIdx = userDao.getPwd(postLoginReq).getUserIdx();
//            String jwt = jwtService.createJwt(userIdx);
//            return new PostLoginRes(userIdx,jwt);
//        }
//        else{
//            throw new BaseException(FAILED_TO_LOGIN);
//        }
//
//    }

//
//    public List<GetUserRes> logintest() {
//        List<GetUserRes> getUserRes = userDao.logintest();
//        return getUserRes;
//    }
}
