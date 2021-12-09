package com.example.demo.src.main;

import com.example.demo.src.main.model.*;
import com.example.demo.src.user.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class MainDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


//
//    public List<GetStudent> getStudents() {
//        return this.jdbcTemplate.query("select address,policyName,\n" +
//                "        if(supportType='서비스','서비스지원',maxMoney) as supportPartOne,\n" +
//                "        if(supportType='서비스','',minMoney) as supportPartTwo,\n" +
//                "        (SUBSTRING_INDEX((SUBSTRING_INDEX(applicationPer,' ',-1)),'~',-1)) as endDate,\n" +
//                "        (if(applicationPer Like '%연중상시%','연중상시',TO_DAYS(SUBSTRING_INDEX((SUBSTRING_INDEX(applicationPer,' ',-1)),'~',-1))-TO_DAYS(now()))) as dDay\n" +
//                "       from welfarePolicy\n" +
//                "group by welfarePolicyIdx",
//                (rs,rownum) -> new GetStudent(
//                        rs.getString("address"),
//                        rs.getString("policyName"),
//                        rs.getString("supportPartOne"),
//                        rs.getString("supportPartTwo"),
//                        rs.getString("endDate"),
//                        rs.getString("dDay")
//                ));
//    }

    /** 테스트버전
     *
     * @return
     */
    public List<GetStudent> getStudents() {
        return this.jdbcTemplate.query("select uid, name, title,servicetype, maxmoney, minmoney, incomelevel, category, content, rundate, applydate, scale, age, address, detailterms, schoolrecords, employmentstate, specialstatus, joinlimit, applymethod, judge, url, document, manageoffice,phNum from welfarePolicy",
                (rs,rownum)-> new GetStudent(
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
                ));
    }


    /**테스트버전 2
     *
     */
    public List<GetStudent> gettest() {
        return this.jdbcTemplate.query("select uid, name, title,servicetype, maxmoney, minmoney, incomelevel, category, content, rundate, applydate, scale, age, address, detailterms, schoolrecords, employmentstate, specialstatus, joinlimit, applymethod, judge, url, document, manageoffice,phNum from welfarePolicy where name Like '%청년%'",
                (rs,rownum)-> new GetStudent(
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
                ));
    }
    /**
     * 테스트 버전 3
     */
    public List<GetStudent> gettest2() {
        return this.jdbcTemplate.query("select uid, name, title,servicetype, maxmoney, minmoney, incomelevel, category, content, rundate, applydate, scale, age, address, detailterms, schoolrecords, employmentstate, specialstatus, joinlimit, applymethod, judge, url, document, manageoffice,phNum from welfarePolicy where name Like '%국민%'",
                (rs,rownum)-> new GetStudent(
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
                ));
    }

    /**
     * 정책번호 받으면 정책내용 알려주는 부분
     * @param uid
     * @return
     */

    public GetStudent getwelfareinfo(String uid) {
        return this.jdbcTemplate.queryForObject("select uid, name, title,servicetype, maxmoney, minmoney, incomelevel, category, content, rundate, applydate, scale, age, address, detailterms, schoolrecords, employmentstate, specialstatus, joinlimit, applymethod, judge, url, document, manageoffice,phNum from welfarePolicy where uid=?",
                (rs,rownum)-> new GetStudent(
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
                ),uid);
    }




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
    public List<GetStudent> getMainViewRes(String age,String type1,String type2,String type3,String type4,String type5,String type6,String address,String addressDetail,String special1,String special2,String special3,String special4,String  special5,String special6,int incomeLevelInt,int incomeAvgInt,String empoly1,String empoly2,String empoly3,String empoly4,String empoly5,String empoly6,String empoly7,String empoly8,String school1,String school2,String school3,String school4,String school5){
        return this.jdbcTemplate.query("select uid, name, title,servicetype, maxmoney, minmoney, incomelevel, category, content, rundate, applydate, scale, age, address, detailterms, schoolrecords, employmentstate, specialstatus, joinlimit, applymethod, judge, url, document, manageoffice,phNum\n" +
                "from welfarePolicy\n" +
                "where (? BETWEEN cast(SUBSTRING_INDEX(IF(age='제한없음','0~200',age),'~',1) as unsigned) AND  cast(SUBSTRING_INDEX(IF(age='제한없음','0~200',age),'~',-1)as unsigned )) #나이!!!; 나이부분 ?받아야함\n" +
                "AND (category LIKE ? OR category LIKE ? OR category LIKE ? OR category LIKE ? OR category LIKE ? OR category LIKE ?)\n" +
                "AND (address=? OR address=? OR address LIKE '%고용%' OR address LIKE '%보건%' OR address LIKE '%국토%' OR address LIKE '%중소%' OR address LIKE '%기획%' OR address LIKE '%금융%' OR address LIKE '%한국%' OR address LIKE '%산업%' OR address LIKE '%농림%' OR address LIKE '%교육%' OR address LIKE '%국가%' OR address LIKE '%여성%' OR address LIKE '%가족%' OR address LIKE '%청년%' OR address LIKE '%과학%' OR address LIKE '%문화%' OR address LIKE '%조달청%' OR address LIKE '%병무청%' OR address LIKE '%외교부%'OR address LIKE '%행정중심%' OR address LIKE '%공정%' OR address LIKE '%농촌%' OR address LIKE '%해양수산%' OR address LIKE '%특허%' OR address LIKE '%통일%' OR address LIKE '%국민%')\n" +
                "AND (specialStatus LIKE '%없음%' OR specialStatus LIKE '%제한없음%' OR specialStatus LIKE '%전체%' OR specialStatus LIKE ? OR specialStatus LIKE ? OR specialStatus LIKE ? OR specialStatus LIKE ? OR specialStatus LIKE ? OR specialStatus LIKE ?)\n" +
                "AND (incomeLevel='제한없음' OR incomeLevel LIKE '%없음%' OR incomeLevel LIKE '%제한없음%' OR incomeLevel LIKE '%전체%' OR if(CHAR_LENGTH(incomeLevel)=1,incomeLevel>=?,incomeLevel>=?))\n" +
                "AND (employmentState='제한없음' OR employmentState LIKE '%없음%' OR employmentState LIKE '%제한없음%' OR employmentState LIKE '%전체%' OR employmentState LIKE ? OR employmentState LIKE ? OR employmentState LIKE ? OR employmentState LIKE ? OR employmentState LIKE ? OR employmentState LIKE ? OR employmentState LIKE ? OR employmentState LIKE ?)\n" +
                "AND (schoolRecords LIKE '%없음%' OR schoolRecords LIKE '%제한없음%' OR schoolRecords LIKE '%전체%' OR schoolRecords LIKE ? OR schoolRecords LIKE ? OR schoolRecords LIKE ? OR schoolRecords LIKE ? OR schoolRecords LIKE ?)",
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
    public List<GetStudent> getMainViewtotalres(String type1,String type2,String type3,String type4,String type5,String type6){
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

    /**
     * 북마크 정보제공
     * @param uid
     * @return
     */
    public List<GetStudent> GetBookmarkRes(String uid) {
        return this.jdbcTemplate.query("select wP.uid,wP.name,wP.title,wP.serviceType,wP.maxMoney,wP.minMoney,wP.incomeLevel,wP.category,wP.content,wP.runDate,wP.applyDate,wP.scale,wP.age,wP.address,wP.detailTerms,wP.schoolRecords,wP.employmentState,wP.specialStatus,wP.joinLimit,wP.applyMethod,wP.judge,wP.url,wP.document,wP.manageOffice,wP.phNum from welfarePolicy as wP inner join bookMark bM on wP.uid = bM.welfareUid\n" +
                "inner join User U on bM.userUid = U.uid\n" +
                "where bM.userUid=?",
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
                ),uid);
    }


    /**북마크 삭제하면 삭제하고 정보제공
     *
     */
    public void deleteBookmark(String uid) {
        this.jdbcTemplate.update("delete from bookMark where welfareUid=?",
                new Object[]{uid});
    }

    /**
     * 알림 되어있는지 조회
     */
    public int checkAlarm(String userUid,String welfareUid) {
        return this.jdbcTemplate.queryForObject("select exists(select alarmIdx from alarm where userUid=? AND welfareUid=?)",
                int.class,userUid,welfareUid);
    }

    /**
     * 알림 등록 (없을때)
     */
    public void createAlarm(String userUid,String welfareUid) {
         this.jdbcTemplate.update("insert into alarm (userUid,welfareUid) VALUES (?,?)",
                new Object[]{userUid,welfareUid});
    }
    /**
     * 알림 등록 (있을떄) - 삭제해야함
     */
    public void deleteAlarm(String userUid, String welfareUid) {
         this.jdbcTemplate.update("delete from alarm where userUid=? AND welfareUid=?",
                new Object[]{userUid,welfareUid});
    }


    /**
     * 유저 uid존재여부
     */
    public int checkUserUid(String userUid) {
        return this.jdbcTemplate.queryForObject("select exists(select userIdx from User where uid=?) ",
                int.class,userUid);
    }
    /**
     * 복지번호 존재여부
     */
    public int checkwelfareUid(String welfareUid) {
        return this.jdbcTemplate.queryForObject("select exists(select welfarePolicyIdx from welfarePolicy where uid=?)",
                int.class,welfareUid);
    }

    /**
     * 알림화면 클릭했을때 뜨는 부분
     */
    public List<GetAlarmList> getAlarmPageRes(String userUid) {
        return this.jdbcTemplate.query("select alarmUid,title,subTitle,date,postUid, alarmRead from alarmInfo where userUid=?",
                (rs,rownum) -> new GetAlarmList(
                        rs.getString("alarmUid"),
                        rs.getString("title"),
                        rs.getString("subTitle"),
                        rs.getString("date"),
                        rs.getString("postUid"),
                        rs.getBoolean("alarmRead")
                ),userUid);

    }
    /**
     * 알림화면에서 알림이 존재하는지 여부
     */
    public int checkAlarmPage(String alarmUid) {
        return this.jdbcTemplate.queryForObject("select exists(select alarmInfoIdx from alarmInfo where alarmUid=?)",
                int.class,alarmUid);
    }

    /**
     * 알림 하나 삭제 부분
     */
    public void deleteOneAlarm(String userUid,String alarmUid) {
        this.jdbcTemplate.update("delete from alarmInfo where userUid=? AND alarmUid=?",
                new Object[]{userUid,alarmUid});
    }

    /**
     * 알림 전체 삭제 부분
     */
    public void deleteAllAlarm(String userUid) {
        this.jdbcTemplate.update("delete from alarmInfo where userUid=?",
                new Object[]{userUid});
    }

    /**
     * 클릭시 alarmRead 바꿔주는 부분
     */
    public void patchOneAlarm(String userUid,String alarmUid) {
        this.jdbcTemplate.update("update alarmInfo SET alarmRead=FALSE where userUid=? AND alarmUid=?",
                new Object[]{userUid,alarmUid});
    }




}
