package com.fumuquan.util;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

	private static final SimpleDateFormat sdf = new SimpleDateFormat(
			"yyyy/MM/dd HH:mm:ss");

	private static final SimpleDateFormat sdf1 = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	private static final SimpleDateFormat sdf2 = new SimpleDateFormat(
			"yyyy.MM.dd HH:mm:ss");

	private static final SimpleDateFormat sdf3 = new SimpleDateFormat("HH:mm");

	private static final SimpleDateFormat sdf4 = new SimpleDateFormat(
			"yyyy-MM-dd");

	private static final SimpleDateFormat sdf5 = new SimpleDateFormat(
			"HH:mm:ss");

	public static Date formatToDate(String time) {

		if (null == time || time.isEmpty()) {
			return null;
		}

		Date timeDate = null;
		try {
			timeDate = sdf.parse(time);
		} catch (ParseException e) {
			try {
				timeDate = sdf1.parse(time);
			} catch (ParseException e1) {
				try {
					timeDate = sdf2.parse(time);
				} catch (ParseException e2) {
					e2.printStackTrace();
				}
			}

		}
		return timeDate;
	}

	public static String fomatToHHMM(Date date) {
		if (null == date) {
			return null;
		}

		return sdf3.format(date);
	}

	public static String fomatToYYYYMMDD(Date date) {
		String dateInfo;
		
		if (null == date) {
			return null;
		}
		
		do{
			dateInfo = sdf4.format(date);
		
			//日期格式检查  0000-00-00
			if(dateInfo.length() == 10){
				break;
			}
		}while(true);
		
		return dateInfo; 
	}

	public static String fomatToHHMMSS(Date date) {

		if (null == date) {
			return null;
		}
		return sdf5.format(date);
	}

	public static String fomatToYYYYMMDDHHMMSS(Date date) {
		String dateInfo;
		
		if (null == date) {
			return null;
		}
		
		do{
			//格式 1. 0000-00-00 00:00:00
			dateInfo = sdf1.format(date);
			if(dateInfo.length() == 19){
				break;
			}
		}while(true);
		
		return dateInfo;
	}
	
	/**
	 * 比较时间time1是否比time2早。
	 * @param time1		字符串格式的时间, ex "12:01:01"
	 * @param time2		同time1
	 * @return 如果time1早于time2返回true
	 */
	public static boolean isBefore(String strTime1, String strTime2){
		boolean res = true;
		
		try{
			Time t1 = Time.valueOf(strTime1);
			Time t2 = Time.valueOf(strTime2);
			
			if(t1.before(t2)){
				res = true;
			}
			else{
				res = false;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return res;
	}
	
	/**
	 * 对日期格式为“0000-00-00”的两个日期进行比较。如果strDate2为null，则与当期日期进行比较。
	 * @param strDate1
	 * @param strDate2
	 * @return   1 晚于。 =0 等于。-1， 早于。  -2 error
	 */
	public static int compareDate(String strDate1, String strDate2){
		if(null == strDate1 || 10 != strDate1.length() || null == strDate2 || 10 != strDate2.length()){
			return -2;
		}
		
		return strDate1.compareTo(strDate2);
	}
	
	/**
	 * 给定格式“0000-00-00”日期，和与该日期相差的天数diffDay,返回所得到的日期
	 * @param dateInfo
	 * @param diffDay
	 * @return
	 */
	public static Date getDate(String dateInfo, int diffDay){
		Date date = null;
		
		try{
			Date startDate = sdf4.parse(dateInfo);
			Calendar c = Calendar.getInstance();
			c.setTime(startDate);
			
			c.add(Calendar.DATE, diffDay);
			
			date = c.getTime(); 
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return date;
	}
	
	/**
	 * 给定格式“0000-00-00 00:00:00”, 返回该字符串对应的时间
	 * @param dateInfo
	 * @return
	 */
	public static Date getDateTime(String dateInfo){
		Date date = null;
		
		if(null == dateInfo || dateInfo.isEmpty()){
			return null;
		}
		
		try{
			date = sdf1.parse(dateInfo);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return date;
	}
}
