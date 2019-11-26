package com.jibug.cetty.core.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	public static SimpleDateFormat FORMAT_YMDHMS_EASY = new SimpleDateFormat(
			"yyyyMMddHHmmss");

	public static SimpleDateFormat FORMAT_YMDHMS = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	public static final DateFormat DEFAULT_DATEFORMAT = new SimpleDateFormat(
			"yyyy-MM-dd");

	/**
	 * 时间转换为字符串yyyyMMddHHmmss
	 *
	 * @return
	 */
	public static String ymdhmsFormatEasy() {
		return FORMAT_YMDHMS_EASY.format(new Date());
	}

	/**
	 * 时间转换为字符串yyyyMMddHHmmss
	 *
	 * @return
	 */
	public static String ymdhmsFormatEasy(Date date) {
		return FORMAT_YMDHMS_EASY.format(date);
	}

	/**
	 * 时间转换为字符串yyyy-MM-dd HH:mm:ss
	 * 
	 * @param date
	 * @return
	 */
	public static String ymdhmsFormat(Date date) {
		if(date == null){
			return null;
		}
		return FORMAT_YMDHMS.format(date);
	}

	/**
	 * 时间转换为字符串yyyy-MM-dd
	 * 
	 * @param date
	 * @return
	 */
	public static String ymdFormat(Date date) {
		if(date == null){
			return null;
		}
		return DEFAULT_DATEFORMAT.format(date);
	}

	public static Timestamp currentTime() {
		return new Timestamp(System.currentTimeMillis());
	}

	/**
	 * @disc 根据日期获取相差的月份
	 * @param date
	 *            :给定的日期
	 * @param months
	 *            :相差的月份数
	 * @return 返回Date对象
	 */
	public static Date getDayAdd(Date date, int days) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, days);
		return new Date(cal.getTime().getTime());
	}

	/**
	 * @disc 根据日期获取相差的月份
	 * @param date
	 *            :给定的日期
	 * @param months
	 *            :相差的月份数
	 * @return 返回Date对象
	 */
	public static String getHourAdd(int hour) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.HOUR_OF_DAY, hour);
		return ymdhmsFormatEasy(new Date(cal.getTime().getTime()));
	}

	public static Date getDateAfterDay(Date somedate, int day) {
		if (somedate == null)
			return null;
		Calendar cal = Calendar.getInstance();
		cal.setTime(somedate);
		cal.add(Calendar.DAY_OF_MONTH, day);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return new Date(cal.getTime().getTime());
	}

	public static Timestamp getDateAfterDay(int day) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DAY_OF_MONTH, day);
		return new Timestamp(cal.getTime().getTime());
	}
}
