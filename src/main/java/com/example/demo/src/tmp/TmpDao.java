package com.example.demo.src.tmp;


import com.example.demo.src.tmp.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class TmpDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

//    public List<GetUserRes> getUsers(){
//        String getUsersQuery = "select * from UserInfo";
//        return this.jdbcTemplate.query(getUsersQuery,
//                (rs,rowNum) -> new GetUserRes(
//                        rs.getInt("userIdx"),
//                        rs.getString("userName"),
//                        rs.getString("ID"),
//                        rs.getString("Email"),
//                        rs.getString("password"))
//                );
//    }
//
//    public List<GetUserRes> getUsersByEmail(String email){
//        String getUsersByEmailQuery = "select * from UserInfo where email =?";
//        String getUsersByEmailParams = email;
//        return this.jdbcTemplate.query(getUsersByEmailQuery,
//                (rs, rowNum) -> new GetUserRes(
//                        rs.getInt("userIdx"),
//                        rs.getString("userName"),
//                        rs.getString("ID"),
//                        rs.getString("Email"),
//                        rs.getString("password")),
//                getUsersByEmailParams);
//    }
//
//    public GetUserRes getUser(int userIdx){
//        String getUserQuery = "select * from UserInfo where userIdx = ?";
//        int getUserParams = userIdx;
//        return this.jdbcTemplate.queryForObject(getUserQuery,
//                (rs, rowNum) -> new GetUserRes(
//                        rs.getInt("userIdx"),
//                        rs.getString("userName"),
//                        rs.getString("ID"),
//                        rs.getString("Email"),
//                        rs.getString("password")),
//                getUserParams);
//    }
//
//
//    public int createUser(PostUserReq postUserReq){
//        String createUserQuery = "insert into UserInfo (userName, ID, password, email) VALUES (?,?,?,?)";
//        Object[] createUserParams = new Object[]{postUserReq.getUserName(), postUserReq.getId(), postUserReq.getPassword(), postUserReq.getEmail()};
//        this.jdbcTemplate.update(createUserQuery, createUserParams);
//
//        String lastInserIdQuery = "select last_insert_id()";
//        return this.jdbcTemplate.queryForObject(lastInserIdQuery,int.class);
//    }
//
//    public int checkEmail(String email){
//        String checkEmailQuery = "select exists(select email from UserInfo where email = ?)";
//        String checkEmailParams = email;
//        return this.jdbcTemplate.queryForObject(checkEmailQuery,
//                int.class,
//                checkEmailParams);
//
//    }
//
//    public int modifyUserName(PatchUserReq patchUserReq){
//        String modifyUserNameQuery = "update UserInfo set userName = ? where userIdx = ? ";
//        Object[] modifyUserNameParams = new Object[]{patchUserReq.getUserName(), patchUserReq.getUserIdx()};
//
//        return this.jdbcTemplate.update(modifyUserNameQuery,modifyUserNameParams);
//    }
//
//    public User getPwd(PostLoginReq postLoginReq){
//        String getPwdQuery = "select userIdx, password,email,userName,ID from UserInfo where ID = ?";
//        String getPwdParams = postLoginReq.getId();
//
//        return this.jdbcTemplate.queryForObject(getPwdQuery,
//                (rs,rowNum)-> new User(
//                        rs.getInt("userIdx"),
//                        rs.getString("ID"),
//                        rs.getString("userName"),
//                        rs.getString("password"),
//                        rs.getString("email")
//                ),
//                getPwdParams
//                );
//
//    }

    /** 메인페이지 uid받아서 유저정보 받는 부분
     *
     */
    public GetUserInfoloop getUserInfoloop(String uid) {

        return this.jdbcTemplate.queryForObject("select birth,address,addressDetail,specialStatus,incomeLevel,incomeAvg,employmentState,schoolRecords,studentCategory,empolyCategory,foundationCategory,residentCategory,lifeCategory,covidCategory from User where uid=?",
                (rs,rownum) -> new GetUserInfoloop(
                        rs.getInt("birth"),
                        rs.getString("address"),
                        rs.getString("addressDetail"),
                        rs.getString("specialStatus"),
                        rs.getString("incomeLevel"),
                        rs.getString("incomeAvg"),
                        rs.getString("employmentState"),
                        rs.getString("schoolRecords"),
                        rs.getString("studentCategory"),
                        rs.getString("empolyCategory"),
                        rs.getString("foundationCategory"),
                        rs.getString("residentCategory"),
                        rs.getString("lifeCategory"),
                        rs.getString("covidCategory")
                ),uid);
    }


    /** 메인페이지 실제 출력 부분
     *
     */
    public List<com.example.demo.src.tmp.model.GetStudent> getMainViewRes(String age, String type1, String type2, String type3, String type4, String type5, String type6, String address, String addressDetail, String special1, String special2, String special3, String special4, String  special5, String special6, int incomeLevelInt, int incomeAvgInt, String empoly1, String empoly2, String empoly3, String empoly4, String empoly5, String empoly6, String empoly7, String empoly8, String school1, String school2, String school3, String school4, String school5){
        return this.jdbcTemplate.query("select uid, name, title,servicetype, maxmoney, minmoney, incomelevel, category, content, rundate, applydate, scale, age, address, detailterms, schoolrecords, employmentstate, specialstatus, joinlimit, applymethod, judge, url, document, manageoffice,phNum\n" +
                        "from welfarePolicy\n" +
                        "where (? BETWEEN cast(SUBSTRING_INDEX(IF(age='제한없음','0~200',age),'~',1) as unsigned) AND  cast(SUBSTRING_INDEX(IF(age='제한없음','0~200',age),'~',-1)as unsigned )) #나이!!!; 나이부분 ?받아야함\n" +
                        "AND (category LIKE ? OR category LIKE ? OR category LIKE ? OR category LIKE ? OR category LIKE ? OR category LIKE ?)\n" +
                        "AND (address=? OR address=? OR address LIKE '%고용%' OR address LIKE '%보건%' OR address LIKE '%국토%' OR address LIKE '%중소%' OR address LIKE '%기획%' OR address LIKE '%금융%' OR address LIKE '%한국%' OR address LIKE '%산업%' OR address LIKE '%농림%' OR address LIKE '%교육%' OR address LIKE '%국가%' OR address LIKE '%여성%' OR address LIKE '%가족%' OR address LIKE '%청년%' OR address LIKE '%과학%' OR address LIKE '%문화%' OR address LIKE '%조달청%' OR address LIKE '%병무청%' OR address LIKE '%외교부%'OR address LIKE '%행정중심%' OR address LIKE '%공정%' OR address LIKE '%농촌%' OR address LIKE '%해양수산%' OR address LIKE '%특허%' OR address LIKE '%통일%' OR address LIKE '%국민%')\n" +
                        "AND (specialStatus LIKE '%없음%' OR specialStatus LIKE '%제한없음%' OR specialStatus LIKE '%전체%' OR specialStatus LIKE ? OR specialStatus LIKE ? OR specialStatus LIKE ? OR specialStatus LIKE ? OR specialStatus LIKE ? OR specialStatus LIKE ?)\n" +
                        "AND (incomeLevel='제한없음' OR incomeLevel LIKE '%없음%' OR incomeLevel LIKE '%제한없음%' OR incomeLevel LIKE '%전체%' OR if(CHAR_LENGTH(incomeLevel)=1,incomeLevel>=?,incomeLevel>=?))\n" +
                        "AND (employmentState='제한없음' OR employmentState LIKE '%없음%' OR employmentState LIKE '%제한없음%' OR employmentState LIKE '%전체%' OR employmentState LIKE ? OR employmentState LIKE ? OR employmentState LIKE ? OR employmentState LIKE ? OR employmentState LIKE ? OR employmentState LIKE ? OR employmentState LIKE ? OR employmentState LIKE ?)\n" +
                        "AND (schoolRecords LIKE '%없음%' OR schoolRecords LIKE '%제한없음%' OR schoolRecords LIKE '%전체%' OR schoolRecords LIKE ? OR schoolRecords LIKE ? OR schoolRecords LIKE ? OR schoolRecords LIKE ? OR schoolRecords LIKE ?)",
                (rs,rownum) -> new com.example.demo.src.tmp.model.GetStudent(
                        rs.getString("uid"),
                        rs.getString("name"),
                        rs.getString("title"),
                        rs.getString("servicetype"),
                        rs.getString("maxmoney"),
                        rs.getString("minmoney"),
                        rs.getString("incomelevel"),
                        rs.getString("category"),
                        rs.getString("content"),
                        rs.getString("rundate"),
                        rs.getString("applydate"),
                        rs.getString("scale"),
                        rs.getString("age"),
                        rs.getString("address"),
                        rs.getString("detailterms"),
                        rs.getString("schoolrecords"),
                        rs.getString("employmentstate"),
                        rs.getString("specialstatus"),
                        rs.getString("joinlimit"),
                        rs.getString("applymethod"),
                        rs.getString("judge"),
                        rs.getString("url"),
                        rs.getString("document"),
                        rs.getString("manageoffice"),
                        rs.getString("phNum")

                ),age,type1,type2,type3,type4,type5,type6,address,addressDetail,special1,special2,special3,special4,special5,special6,incomeLevelInt,incomeAvgInt,empoly1,empoly2,empoly3,empoly4,empoly5,empoly6,empoly7,empoly8,school1,school2,school3,school4,school5);

    }

    /**
     * 메인뷰 전체다 나오는 부분
     * @param
     * @param type1
     * @param type2
     * @param type3
     * @param type4
     * @param type5
     * @param type6
     * @return
     */
    public List<com.example.demo.src.tmp.model.GetStudent> getMainViewtotalres(String type1, String type2, String type3, String type4, String type5, String type6){
        return this.jdbcTemplate.query("select uid, name, title,servicetype, maxmoney, minmoney, incomelevel, category, content, rundate, applydate, scale, age, address, detailterms, schoolrecords, employmentstate, specialstatus, joinlimit, applymethod, judge, url, document, manageoffice,phNum\n" +
                        "from welfarePolicy\n" +
                        "where category LIKE ? OR category LIKE ? OR category LIKE ? OR category LIKE ? OR category LIKE ? OR category LIKE ?",
                (rs,rownum) -> new GetStudent(
                        rs.getString("uid"),
                        rs.getString("name"),
                        rs.getString("title"),
                        rs.getString("servicetype"),
                        rs.getString("maxmoney"),
                        rs.getString("minmoney"),
                        rs.getString("incomelevel"),
                        rs.getString("category"),
                        rs.getString("content"),
                        rs.getString("rundate"),
                        rs.getString("applydate"),
                        rs.getString("scale"),
                        rs.getString("age"),
                        rs.getString("address"),
                        rs.getString("detailterms"),
                        rs.getString("schoolrecords"),
                        rs.getString("employmentstate"),
                        rs.getString("specialstatus"),
                        rs.getString("joinlimit"),
                        rs.getString("applymethod"),
                        rs.getString("judge"),
                        rs.getString("url"),
                        rs.getString("document"),
                        rs.getString("manageoffice"),
                        rs.getString("phNum")

                ),type1,type2,type3,type4,type5,type6);

    }












    //테스트
    public List<GetUserRes> logintest() {
          List<GetUserRes> getUserRes = this.jdbcTemplate.query("select email,password from User",
                (rs,rownum) -> new GetUserRes(
                        rs.getString("email"),
                        rs.getString("password")
                ));
          return getUserRes;
    }

    /** 소셜 회원가입 API
     *
     */
    public int createsocial(String uid, String social, PostUserReq postUserReq) {
        this.jdbcTemplate.update("insert into User(uid,social,email,password) VALUES (?,?,?,?)",
                new Object[]{uid,social,postUserReq.getEmail(),postUserReq.getPassword()});
        return this.jdbcTemplate.queryForObject("select last_insert_id()",int.class);
    }

    /** 이메일 회원가입 API
     *
     */
    public int createemailsignup(String uid, PostUserSingUpReq postUserSingUpReq) {
        this.jdbcTemplate.update("insert into User(uid,email,name,pwd,phNum,gender,birth,address,addressDetail,specialStatus,incomeLevel,incomeAvg,employmentState,schoolRecords,school,mainMajor,subMajor,semester,lastSemesterScore,studentCategory,empolyCategory,foundationCategory,residentCategory,lifeCategory,covidCategory,bookmark,provider,FCMTOKEN) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
                new Object[]{uid,postUserSingUpReq.getEmail(),postUserSingUpReq.getName(),postUserSingUpReq.getPwd(),postUserSingUpReq.getPhNum(),postUserSingUpReq.getGender(),postUserSingUpReq.getBirth(),postUserSingUpReq.getAddress(),postUserSingUpReq.getAddressDatil(),postUserSingUpReq.getSpecialStatus(),postUserSingUpReq.getIncomeLevel(),postUserSingUpReq.getIncomeAvg()
        ,postUserSingUpReq.getEmploymentState(),postUserSingUpReq.getSchoolRecords(),postUserSingUpReq.getSchool(),postUserSingUpReq.getMainMajor(),postUserSingUpReq.getSubMajor(),postUserSingUpReq.getSemester(),postUserSingUpReq.getLastSemesterScore(),postUserSingUpReq.getStudentCategory(),postUserSingUpReq.getEmpolyCategory(),postUserSingUpReq.getFoundationCategory(),postUserSingUpReq.getResidentCategory(),postUserSingUpReq.getLifeCategory()
        ,postUserSingUpReq.getMedicalCategory(),postUserSingUpReq.getBookmark(),postUserSingUpReq.getProvider(),postUserSingUpReq.getFCMTOKEN()}
        );
        return this.jdbcTemplate.queryForObject("select last_insert_id()",int.class);
    }
    /** 이메일 중복여부 확인
     *
     */
    public int checkEmail(String email){
        String checkEmailQuery = "select exists(select email from User where email = ?)";
        String checkEmailParams = email;
        return this.jdbcTemplate.queryForObject(checkEmailQuery,
                int.class,
                checkEmailParams);
    }

    public int checkuid(String uid){
        String checkEmailQuery = "select exists(select uid from User where uid = ?)";
        String checkEmailParams = uid;
        return this.jdbcTemplate.queryForObject(checkEmailQuery,
                int.class,
                checkEmailParams);
    }

    /**
     * default 아이디 찾기
     */
    public GetUserFindId getUserFindId(String name, String phNum){
        return this.jdbcTemplate.queryForObject("select email from makeus.User where provider='default'AND name=? AND phNum=?",
                (rs,rownum) -> new GetUserFindId(
                        rs.getString("email")
                ),name,phNum);
    }

    /**
     * default 아이디 찾기 있나 확인부분
     */

    /**
     * 유저 정보 제공 부분
     */
    public GetUserInfoRes getUserInfoRes(String uid) {
        return this.jdbcTemplate.queryForObject("select uid,email,name,pwd,phNum,gender,birth,address,addressDetail,specialStatus,incomeLevel,incomeAvg,employmentState,schoolRecords,school,mainMajor,subMajor,semester,lastSemesterScore,studentCategory,empolyCategory,foundationCategory,residentCategory,lifeCategory,covidCategory,bookmark,provider,FCMTOKEN from User where uid=?",
                (rs,rownum) -> new GetUserInfoRes(
                        rs.getString("email"),
                        rs.getString("name"),
                        rs.getString("pwd"),
                        rs.getString("phNum"),
                        rs.getString("gender"),
                        rs.getInt("birth"),
                        rs.getString("address"),
                        rs.getString("addressDetail"),
                        rs.getString("specialStatus"),
                        rs.getString("incomeLevel"),
                        rs.getString("incomeAvg"),
                        rs.getString("employmentState"),
                        rs.getString("schoolRecords"),
                        rs.getString("school"),
                        rs.getString("mainMajor"),
                        rs.getString("subMajor"),
                        rs.getString("semester"),
                        rs.getString("lastSemesterScore"),
                        rs.getString("studentCategory"),
                        rs.getString("empolyCategory"),
                        rs.getString("foundationCategory"),
                        rs.getString("residentCategory"),
                        rs.getString("lifeCategory"),
                        rs.getString("covidCategory"),
                        rs.getString("bookmark"),
                        rs.getString("provider"),
                        rs.getString("FCMTOKEN")
                ),uid);
    }

    /**
     * 유저정보 수정부분
     */
    public void getUserModify(String uid, GetUserModifyReq getUserModifyReq) {
        this.jdbcTemplate.update("update User SET email=?,name=?,pwd=?,phNum=?,gender=?,birth=?,address=?,addressDetail=?,specialStatus=?,incomeLevel=?,incomeAvg=?,employmentState=?,schoolRecords=?,school=?,mainMajor=?,subMajor=?,semester=?,lastSemesterScore=?,studentCategory=?,empolyCategory=?,foundationCategory=?,residentCategory=?,lifeCategory=?,covidCategory=?,bookmark=?,provider=?,FCMTOKEN=? where uid=?",
                new Object[]{getUserModifyReq.getEmail(),getUserModifyReq.getName(),getUserModifyReq.getPwd(),getUserModifyReq.getPhNum(),getUserModifyReq.getGender(),getUserModifyReq.getBirth(),getUserModifyReq.getAddress(),getUserModifyReq.getAddressDatil(),getUserModifyReq.getSpecialStatus(),getUserModifyReq.getIncomeLevel(),getUserModifyReq.getIncomeAvg(),getUserModifyReq.getEmploymentState(),
                        getUserModifyReq.getSchoolRecords(),getUserModifyReq.getSchool(),getUserModifyReq.getMainMajor(),getUserModifyReq.getSubMajor(),getUserModifyReq.getSemester(),getUserModifyReq.getLastSemesterScore(),getUserModifyReq.getStudentCategory(),getUserModifyReq.getEmpolyCategory(),getUserModifyReq.getFoundationCategory(),getUserModifyReq.getResidentCategory(),getUserModifyReq.getLifeCategory(),getUserModifyReq.getMedicalCategory(),getUserModifyReq.getBookmark(),getUserModifyReq.getProvider(),getUserModifyReq.getFCMTOKEN(),uid}
        );
    }

    /** 유저정보 수정(복지정보)
     *
     */
    public void patchUserWelfare(String uid, PatchUserWelfareStringReq patchUserWelfareStringReq) {
         this.jdbcTemplate.update("update User SET studentCategory=?,empolyCategory=?,foundationCategory=?,residentCategory=?,lifeCategory=?,covidCategory=? where uid=? ",
                new Object[]{patchUserWelfareStringReq.getStudentCategory(),patchUserWelfareStringReq.getEmpolyCategory(),patchUserWelfareStringReq.getFoundationCategory(),patchUserWelfareStringReq.getResidentCategory(),patchUserWelfareStringReq.getLifeCategory(),patchUserWelfareStringReq.getEmpolyCategory(),uid});
    }

    /** 유저정보 수정(개인정보)
     *
     */
    public void patchUserPrivacy(String uid, PatchUserPrivacyReq patchUserPrivacyReq) {
         this.jdbcTemplate.update("update User SET email=?,name=?,pwd=?,phNum=?,gender=?,birth=? where uid=? ",
                new Object[]{patchUserPrivacyReq.getEmail(),patchUserPrivacyReq.getName(),patchUserPrivacyReq.getPwd(),patchUserPrivacyReq.getPhNum(),patchUserPrivacyReq.getGender(),patchUserPrivacyReq.getBirth(),uid});
    }

    /**
     * 유저정보 수정(추가정보)
     */
    public void patchUserPlusInfo(String userUid, PatchUserPlusInfo patchUserPlusInfo){
        this.jdbcTemplate.update("update User SET address=?,addressDetail=?,specialStatus=?,incomeLevel=?,incomeAvg=?,employmentState=?,schoolRecords=?,school=?,mainMajor=?,subMajor=?,semester=?,lastSemesterScore=? where uid=?",
                new Object[]{patchUserPlusInfo.getAddress(),patchUserPlusInfo.getAddressDetail(),patchUserPlusInfo.getSpecialStatus(),patchUserPlusInfo.getIncomeLevel(),patchUserPlusInfo.getIncomeAvg(),patchUserPlusInfo.getEmploymentState(),patchUserPlusInfo.getSchoolRecords(),patchUserPlusInfo.getSchool(),patchUserPlusInfo.getMainMajor(),patchUserPlusInfo.getSubMajor(),patchUserPlusInfo.getSemester(),patchUserPlusInfo.getLastSemesterScore(),userUid});

    }

    /** 유저탈퇴
     *
     */
    public void deleteUserRes(String uid) {
        this.jdbcTemplate.update("delete from bookMark where userUid=?",
                new Object[]{uid});
        this.jdbcTemplate.update("delete from alarm where userUid=?",
                new Object[]{uid});
        this.jdbcTemplate.update("delete from alarmInfo where userUid=?",
                new Object[]{uid});

        this.jdbcTemplate.update("delete from User where uid = ?",
                new Object[]{uid});

    }
    /**
     * 북마크 현재 상태
     */


    /** 북마크 등록 (중복된것이 없을때)
     *
     */
    public void createBookmark(String userUid,String welfareUid) {
         this.jdbcTemplate.update("insert into bookMark (userUid , welfareUid) VALUES (?,?)",
                new Object[]{userUid,welfareUid});
    }
    /**북마크 삭제(중복이 있어서)
     *
     */
    public void deleteBookmark(String userUid,String welfareUid) {
         this.jdbcTemplate.update("delete from bookMark where userUid=? AND welfareUid=?",
                new Object[]{userUid,welfareUid});
    }

    /**
     * 북마크 중복확인
     */
    public int checkbookmark(String userUid,String welfareUid) {
        return this.jdbcTemplate.queryForObject("select exists(select bookMarkIdx from bookMark where userUid=? AND welfareUid=?)",
                int.class,userUid,welfareUid);
    }

    /**
     * fcm(아토)추가
     */
    public void plusfcm(String userUid,String fcmtoken) {
        this.jdbcTemplate.update("update User SET FCMTOKEN=? where uid=?",
                new Object[]{fcmtoken,userUid});
    }

    /**
     * check provider
     */
    public String checkprovider(String uid) {
        return this.jdbcTemplate.queryForObject("select provider from User where uid=?",
                String.class,uid);

    }



}
