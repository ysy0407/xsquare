package com.xsquare.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 日期处理
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年12月21日 下午12:53:33
 */
public class DateUtils {
	/** 时间格式(yyyy-MM-dd) */
	public final static String DATE_PATTERN = "yyyy-MM-dd";
	/** 时间格式(yyyy-MM-dd HH:mm:ss) */
	public final static String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
	
	public static String format(Date date) {
        return format(date, DATE_PATTERN);
    }

    public static String format(Date date, String pattern) {
        if(date != null){
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            return df.format(date);
        }
        return null;
    }

    //获取某一天的年数
    public static int getYear(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }

    //根据年和周数获取这周的开始到结束的时间,2018-W10
    public static String[] getAllWeekDate(String weekNo){
        System.out.println("----weekNo:"+weekNo);
        String[] yearAndWeek = weekNo.split("\\-");
        int year = Integer.valueOf(yearAndWeek[0].substring(1,5));
        int weekOfYear = Integer.valueOf(yearAndWeek[1].substring(1,3));
        System.out.println("year:"+year+"\tweekOfYear:"+weekOfYear);
        //将开始时间和结束时间中的每个日期取出,组成数组,便于后面的判断
        String[] dateArray = new String[7];
        for (int i = 0; i < dateArray.length; i++) {
            String date = null;
            //周六之前
            if (i < 6) {
                date = DateUtils.getDayNoOfWeekNo(year, weekOfYear, i+2);
                //周日
            } else if (i == 6) {
                date = DateUtils.getDayNoOfWeekNo(year, weekOfYear, 1);
            }
            String[] dateStrs = date.split("\\-");
            for (int j = 0; j < dateStrs.length; j++) {
                //为一位的话应当在前面加上0
                dateStrs[j] = dateStrs[j].length() == 1 ? "0"+dateStrs[j] : dateStrs[j];
            }
            dateArray[i] = dateStrs[0]+"-"+dateStrs[1]+"-"+dateStrs[2];
        }
        return dateArray;
    }

    //根据开始和结束日期获取期间的所有日期
    public static List<String> getStartDateToEndDateFromDates(String startDate, String endDate) {
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_PATTERN);
        List<String> dateArray = new ArrayList<>();
        if (startDate.equals(endDate)) {
            System.out.println("两个日期相等!");
            return null;
        }

        String tmp = "";
        if (startDate.compareTo(endDate) > 0) {  //确保 date1的日期不晚于date2
            tmp = startDate;
            startDate = endDate;
            endDate = tmp;
        }
        dateArray.add(startDate);
        tmp = formatter.format(getDateFromStr(startDate).getTime() + 3600 * 24 * 1000);


        int num = 1;
        while (tmp.compareTo(endDate) < 0) {
            dateArray.add(tmp);
            num++;
            tmp = formatter.format(getDateFromStr(tmp).getTime() + 3600 * 24 * 1000);
        }dateArray.add(endDate);
        if (num == 0)
            System.out.println("两个日期相邻!");

        return dateArray;

    }

    public static Date getDateFromStr(String date){
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_PATTERN);
        if (date == null) return null;

        try {
            return formatter.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    //获取某一天是一年的第几周
    public static int getWeekNoOfYear(Date date){
        Calendar calendar = Calendar.getInstance();
        //设置一周的第一天为周一
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.setTime(date);
        return calendar.get(Calendar.WEEK_OF_YEAR);
    }

    //获取某一天是周几
    public static int getDayOfWeek(String date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(getDateFromStr(date));
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    //获取某一年的第几周的周几的日期
    public static String getDayNoOfWeekNo(int year,int weekNo, int dayNo){
        Calendar calendar = Calendar.getInstance();
        //设置一周的第一天为周一
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.setWeekDate(year, weekNo, dayNo);
//        cal.set(Calendar.YEAR, year);
//        cal.set(Calendar.WEEK_OF_YEAR, weekNo);
//        cal.set(Calendar.DAY_OF_WEEK, dayNo);
        return calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" +
                calendar.get(Calendar.DAY_OF_MONTH);
    }

    //获取当前日期
    public static String getDate(){
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_PATTERN);
        return  formatter.format(new Date());
    }

    //获取当前时间
    public static String getTime(){
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_TIME_PATTERN);
        return  formatter.format(new Date());
    }

    /**
     * @Author: YuSongYuan
     * @Description: 获取两个时间之间的时间差
     *  参数：传入两个yyyy-MM-dd hh:mm:ss格式的字符串
     * @Date: 2018/1/23 11:01
     **/
    public static String getDatePoor(String startTimeStr, Date date) {
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        // long ns = 1000;
        // 获得两个时间的毫秒时间差异
        long diff = getNowDatePoorMill(startTimeStr, date);
        // 计算差多少天
        long day = diff / nd;
        // 计算差多少小时
        long hour = diff % nd / nh;
        // 计算差多少分钟
        long min = diff % nd % nh / nm;
        // 计算差多少秒//输出结果
        // long sec = diff % nd % nh % nm / ns;
        String result = day + "天" + hour + "小时" + min + "分钟";
        return result.replaceAll("-", " ");
    }

    /**
     * @Author: YuSongYuan
     * @Description: 获取一个时间据当前时间的时间差
     *
     * @Date: 2018/1/23 11:07
     **/
    public static String getNowDatePoor(String timeStr){
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_TIME_PATTERN);
        Date nowTime = new Date();
        System.out.println("simpleDateFormat.format(new Date()):"+formatter.format(nowTime));
        return getDatePoor(timeStr, new Date());
    }

    //获取两个时间的毫秒差
    public static long getNowDatePoorMill(String theStartDateStr,  Date date){
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_TIME_PATTERN);
        Date starttime = null;
        Date endtime = date;
        try {
            starttime = formatter.parse(theStartDateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        // 获得两个时间的毫秒时间差异
        long diff = starttime.getTime() - endtime.getTime();
        return diff;
    }
}
