package com.zzh.gdut.gduthelper.util;

import android.util.Log;

import com.zzh.gdut.gduthelper.bean.ExamInfo;
import com.zzh.gdut.gduthelper.bean.PersonInfo;
import com.zzh.gdut.gduthelper.bean.ScheduleInfo;
import com.zzh.gdut.gduthelper.bean.ScoreInfo;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ZengZeHong on 2016/9/26.
 * Jsoup工具类
 */

public class JsoupUtil {
    private static final String TAG = "JsoupUtil";

    /**
     * 获取网页上ViewState状态
     *
     * @return
     */
    public static String getViewState() {
        try {
            Document document = Jsoup.connect(ApiUtil.URL_HOST_TWO).get();
            Elements elements = document.getElementsByTag("input");
            for (Element element : elements) {
                if (element.attr("name").equals("__VIEWSTATE")) {
                    Log.e(TAG, "getViewState: " + element.attr("value"));
                    return element.attr("value");
                }
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 登陆成功后解析出重定向的地址
     *
     * @param result
     * @return
     */
    public static String getLoginHref(String result) {
      /*  String html = "<html><head><title>Object moved</title></head>"
                + "<body><h2>Object moved to <a href='/xs_main.aspx?xh=3114005890'>here</a>.</h2>"
                + "</body></html>";*/
        Document document = Jsoup.parse(result);
        Elements elements = document.getElementsByTag("a");
        for (Element element : elements) {
            Log.e(TAG, "getLoginHref: " + element.attr("href"));
            return element.attr("href");
        }
        return null;
    }

    /**
     * 登陆失败，解析出指定错误信息
     *
     * @param result
     * @return
     */
    public static String getLoginError(String result) {
        Document document = Jsoup.parse(result);
        Elements elements = document.getElementsByTag("script");
        Log.e(TAG, "getLoginError: elements " + elements.size());
        if(elements.size() != 0)
        if (elements.get(1) != null) {
            Element element = elements.get(1);
            String data = element.data();
            if (data.contains("验证码不正确"))
                return "验证码错误，请重新输入";
            else if (data.contains("密码错误"))
                return "密码错误，请重新输入";
            else if (data.contains("用户名不存在或未按照要求参加教学活动"))
                return "当前学号不存在";
        }
        return null;
    }

    /**
     * 获取当前学号对应同学的名字
     *
     * @param result
     */
    public static String getUserName(String result) {
        Document document = Jsoup.parse(result);
        Elements elements = document.getElementsByTag("span");
        String data = elements.get(1).text();
        if (data.contains("同学")) {
            data = data.substring(0, data.indexOf("同学"));
            Log.e(TAG, "getUserName: " + data);
            return data;
        }
        return null;
    }

    /**
     * 解析个人信息数据
     *
     * @param result
     */
    public static PersonInfo getPersonInfo(String result) {
        PersonInfo personInfo = new PersonInfo();
        Document document = Jsoup.parse(result);
        //所有的表格数据
        Elements elements = document.getElementsByTag("tr");
        //获取学号
        Element elementOne = elements.get(0);
        Elements elementsOne = elementOne.getElementsByTag("span");
        personInfo.setNumberTag(elementsOne.get(0).text());
        personInfo.setNumber(elementsOne.get(1).text());
        //获取手机号
        Element elementTwo = elements.get(1);
        Elements elementsTwo = elementTwo.getElementsByTag("span");
        personInfo.setPhoneNumberTag(elementsTwo.get(4).text());
        personInfo.setPhoneNumber(elementTwo.getElementById("TELNUMBER").attr("value"));
        //专业方向
        Element elementThree = elements.get(2);
        Elements elementsThree = elementThree.getElementsByTag("span");
        personInfo.setMajorTag(elementsThree.get(2).text());
        personInfo.setMajor(elementsThree.get(3).text());
        personInfo.setFamilyCode(elementThree.getElementById("jtyb").attr("value"));

        //入学日期
        Element elementFour = elements.get(3);
        Elements elementsFour = elementFour.getElementsByTag("span");
        personInfo.setDateToSchoolTag(elementsFour.get(2).text());
        personInfo.setDateToSchool(elementsFour.get(3).text());
        personInfo.setFamilyPhoneNumber(elementFour.getElementById("jtdh").attr("value"));

        //出生和毕业
        Element elementFive = elements.get(4);
        Elements elementsFive = elementFive.getElementsByTag("span");
        personInfo.setDateToBirthTag(elementsFive.get(0).text());
        personInfo.setDateToBirth(elementsFive.get(1).text());
        personInfo.setGraduateTag(elementsFive.get(2).text());
        personInfo.setGradurate(elementFive.getElementById("byzx").attr("value"));
        personInfo.setFather(elementFive.getElementById("fqxm").attr("value"));

        //民族和宿舍
        Element elementSix = elements.get(5);
        Elements elementsSix = elementSix.getElementsByTag("span");
        personInfo.setNationTag(elementsSix.get(0).text());
        personInfo.setNation(elementsSix.get(1).text());
        personInfo.setDormitoryTag(elementsSix.get(2).text());
        personInfo.setDormitory(elementSix.getElementById("ssh").attr("value"));
        personInfo.setFatherWork(elementSix.getElementById("fqdw").attr("value"));

        //电子邮箱
        Element elementSeven = elements.get(6);
        Elements elementsSeven = elementSeven.getElementsByTag("span");
        personInfo.setEmailTag(elementsSeven.get(2).text());
        personInfo.setEmail(elementSeven.getElementById("dzyxdz").attr("value"));
        personInfo.setNativePlace(elementSeven.getElementById("txtjg").attr("value"));
        personInfo.setFatherWorkCode(elementSeven.getElementById("fqdwyb").attr("value"));

        //政治面貌
        Element elementEight = elements.get(7);
        Elements elementsEight = elementEight.getElementsByTag("span");
        personInfo.setPolityTag(elementsEight.get(0).text());
        personInfo.setContractPhoneNumber(elementEight.getElementById("lxdh").attr("value"));
        personInfo.setMother(elementEight.getElementById("mqxm").attr("value"));


        Element elementNine = elements.get(8);
        personInfo.setPostCode(elementNine.getElementById("yzbm").attr("value"));
        personInfo.setMotherWork(elementNine.getElementById("mqdw").attr("value"));

        Element elementTen = elements.get(9);
        personInfo.setMotherWorkCode(elementTen.getElementById("mqdwyb").attr("value"));

        Element elementEleven = elements.get(10);
        personInfo.setFatherPhoneNumber(elementEleven.getElementById("fqdwdh").attr("value"));

        Element elementTwelve = elements.get(11);
        personInfo.setHeathy(elementTwelve.getElementById("jkzk").attr("value"));
        personInfo.setMothePhoneNumber(elementTwelve.getElementById("mqdwdh").attr("value"));
        //List
        Elements polityElements = elementEight.getElementsByTag("option");
        personInfo.setPolity(polityElements.get(0).text());
        List<String> polityList = new ArrayList<>();
        for (Element element : polityElements) {
            polityList.add(element.text());
        }
        polityList.remove(0);
        polityList.remove(0);
        personInfo.setPolicyList(polityList);
        //家庭住址
        Element elementThirteen = elements.get(12);
        Elements elementsThirteen = elementThirteen.getElementsByTag("span");
        personInfo.setAddressTag(elementsThirteen.get(4).text());
        personInfo.setAddress(elementThirteen.getElementById("jtdz").attr("value"));

        //家庭所在地
        Element elementFourteen = elements.get(13);
        personInfo.setFamilyLocation(elementFourteen.getElementById("jtszd").attr("value"));

        //家庭所在地
        Element elementNineteen = elements.get(19);
        personInfo.setTimeToPolity(elementNineteen.getElementById("RDSJ").attr("value"));
        //家庭所在地
        Element elementTwenty = elements.get(20);
        personInfo.setStationEnd(elementTwenty.getElementById("ccqj").attr("value"));
        return personInfo;
    }

    /**
     * 解析提交个人信息结果
     *
     * @param result
     * @return
     */
    public static String parseSubmitResult(String result) {
        Document document = Jsoup.parse(result);
        Elements elements = document.getElementsByTag("script");
        String data = elements.get(0).text();
        if (data.contains("成功"))
            return "提交数据成功";
        else
            return "更新个人数据失败";
    }

    /**
     * 查询考试情况
     *
     * @param result
     * @return
     */
    public static List<ExamInfo> searchExamInfo(String result) {
        List<ExamInfo> list = new ArrayList<>();
        Document document = Jsoup.parse(result);
        Elements elements = document.getElementsByTag("tr");
        for (int i = 1; i < elements.size(); i++) {
            Elements elementsChild = elements.get(i).getElementsByTag("td");
            ExamInfo examInfo = new ExamInfo();
            examInfo.setExamName(elementsChild.get(1).text());
            examInfo.setExamTime(elementsChild.get(3).text());
            examInfo.setExamPlace(elementsChild.get(4).text());
            examInfo.setExamSeat(elementsChild.get(6).text());
            list.add(examInfo);
        }
        return list;
    }

    /**
     * 解析成绩
     *
     * @param result
     * @return
     */
    public static List<ScoreInfo> getScoreInfo(String result) {
        List<ScoreInfo> list = new ArrayList<>();
        Document document = Jsoup.parse(result);
        Elements elements = document.getElementsByTag("table");
        Element elementTable = elements.get(1);
        Elements elementsScore = elementTable.getElementsByTag("tr");
        for (int i = 1; i < elementsScore.size(); i++) {
            ScoreInfo scoreInfo = new ScoreInfo();
            Element element = elementsScore.get(i);
            Elements elementsData = element.getElementsByTag("td");
            scoreInfo.setClassName(elementsData.get(1).text());
            scoreInfo.setClassAttr(elementsData.get(2).text());
            scoreInfo.setClassScore(elementsData.get(3).text());
            scoreInfo.setClassCredit(elementsData.get(7).text());
            Log.e(TAG, "getScoreInfo: " + scoreInfo.getClassName() + ">" + scoreInfo.getClassScore());
            list.add(scoreInfo);
        }
        return list;
    }

    /**
     * 解析密码修改结果
     *
     * @param result
     * @return
     */
    public static String parseSubmitPassword(String result) {
        Document document = Jsoup.parse(result);
        Elements elements = document.getElementsByTag("script");
        String data = elements.get(0).data();
        return data.substring(data.indexOf("'") + 1, data.lastIndexOf("'"));
    }

    /**
     * 获取课程信息
     *
     * @param result
     * @return
     */
    public static List<Map<String, List<ScheduleInfo>>> getSchedule(String result) {
        Map<String, List<ScheduleInfo>> mapOne = new HashMap<>();
        Map<String, List<ScheduleInfo>> mapThree = new HashMap<>();
        Map<String, List<ScheduleInfo>> mapSix = new HashMap<>();
        Map<String, List<ScheduleInfo>> mapEight = new HashMap<>();
        Map<String, List<ScheduleInfo>> mapTen = new HashMap<>();
        List<Map<String, List<ScheduleInfo>>> list = new ArrayList<>();

        Document document = Jsoup.parse(result);
        Elements elementsClass = document.getElementsByTag("tr");
        //从4开始第一节
        Element elementOne = elementsClass.get(4);
        Elements elementsOne = elementOne.getElementsByTag("td");
        //第一二节的数据
        for (int i = 2; i <= 8; i++) {
            Element element = elementsOne.get(i);
            mapOne.put((i - 2) + "", getScheduleInfo(i, 2, 0, element));
        }
        //3-4
        Element elementThree = elementsClass.get(6);
        Elements elementsThree = elementThree.getElementsByTag("td");
        for (int i = 1; i <= 7; i++) {
            Element element = elementsThree.get(i);
            mapThree.put((i - 1) + "", getScheduleInfo(i, 1, 2, element));
        }

        //6-7
        Element elementSix = elementsClass.get(9);
        Elements elementsSix = elementSix.getElementsByTag("td");
        for (int i = 1; i <= 7; i++) {
            Element element = elementsSix.get(i);
            mapSix.put((i - 1) + "", getScheduleInfo(i, 1, 5, element));
        }
        //8-9
        Element elementEight = elementsClass.get(11);
        Elements elementsEight = elementEight.getElementsByTag("td");
        for (int i = 1; i <= 7; i++) {
            Element element = elementsEight.get(i);
            mapEight.put((i - 1) + "", getScheduleInfo(i, 1, 7, element));
        }
        //10-12
        Element elementTen = elementsClass.get(13);
        Elements elementsTen = elementTen.getElementsByTag("td");
        for (int i = 2; i <= 8; i++) {
            Element element = elementsTen.get(i);
            mapTen.put((i - 2) + "", getScheduleInfo(i, 2, 9, element));
        }
        list.add(mapOne);
        list.add(mapThree);
        list.add(mapSix);
        list.add(mapEight);
        list.add(mapTen);
        return list;
    }

    private static List<ScheduleInfo> getScheduleInfo(int i, int span, int line, Element element) {
        List<ScheduleInfo> list = new ArrayList<>();
        if (!element.text().equals(" ")) {
            Log.e(TAG, "getScheduleInfo: html " + element.html() );
            String[] schedules = element.html().split("<br><br>");
            for(String schedule : schedules) {
                String[] data = schedule.split("<br>");
                Log.e(TAG, "getScheduleInfo: schedule " +  schedule );
                    try {
                        ScheduleInfo scheduleInfo = new ScheduleInfo();
                        scheduleInfo.setScheduleName(data[0]);
                        scheduleInfo.setScheduleTime(data[1]);
                        Log.e(TAG, "getScheduleInfo: name " + data[0]);
                        Log.e(TAG, "getScheduleInfo: time " + data[1]);
                        scheduleInfo.setScheduleTeacher(data[2]);
                        scheduleInfo.setSchedulePlace(data[3]);
                        scheduleInfo.setLocation(i - span, line);
                        list.add(scheduleInfo);
                    }catch (ArrayIndexOutOfBoundsException e){
                        Log.e(TAG, "ArrayIndexOutOfBoundsException: " );
                        e.printStackTrace();
                }
            }
        }
        return list;
    }

}
