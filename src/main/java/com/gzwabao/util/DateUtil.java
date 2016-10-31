package com.gzwabao.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Alex
 */
public class DateUtil {
	public static final long NW = 7 * 1000 * 24 * 60 * 60;// 一周的毫秒数
	public static final long ND = 1000 * 24 * 60 * 60;// 一天的毫秒数
	public static final long NH = 1000 * 60 * 60;// 一小时的毫秒数
	public static final long NM = 1000 * 60;// 一分钟的毫秒数
	public static final long NS = 1000;// 一秒钟的毫秒数

	/**
	 * 得到系统当前时间戳
	 * 
	 * @return
	 */
	public static long getNow() {
		return System.currentTimeMillis();
	}

	/**
	 * 格式 yyyy-MM-dd HH:mm:ss
	 * 
	 * @param date
	 * @return
	 * @author PCY
	 * @date 2014年8月22日
	 */
	public static String format(Date date) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return dateFormat.format(date);
	}

	public static String format(Date date, String format) {
		DateFormat dateFormat = new SimpleDateFormat(format);
		return dateFormat.format(date);
	}

	public static Date parse(String date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return sdf.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Date parse(String date, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			return sdf.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取两个时间的小时差
	 * 
	 * @param minDate
	 * @param maxDate
	 * @return
	 * @author PCY
	 * @date 2014年8月22日
	 */
	public static Long diffHour(Date minDate, Date maxDate) {
		long diff = maxDate.getTime() - minDate.getTime();
		long day = diff / ND;// 计算差多少天
		long hour = diff % ND / NH + day * 24;// 计算差多少小时
		return hour;
	}

	/**
	 * 获得两个日期间的差值(秒)
	 * 
	 * @param minDate
	 * @param maxDate
	 * @return
	 * @author PCY
	 * @date 2014年8月25日
	 */
	public static long diffSecond(Date minDate, Date maxDate) {
		long min = minDate.getTime();
		long max = maxDate.getTime();
		return (max - min) / NS;
	}

	/**
	 * 获得两个日期间的差值(秒)
	 * 
	 * @param minTime
	 *            小的时间
	 * @param maxTime
	 *            大的时间
	 * @return
	 * @since 2015年3月11日 下午8:05:26
	 * @author jia li
	 */
	public static int diffSecond(long minTime, long maxTime) {
		return (int) ((maxTime - minTime) / NS);
	}

	/**
	 * 判断指定日期是不是前些天
	 * 
	 * @param d
	 * @return
	 * @author PCY
	 * @date 2013-9-13
	 */
	public static boolean isPreDay(Date d) {
		if (d == null) {
			return false;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		int year = cal.get(Calendar.YEAR);
		int date = cal.get(Calendar.DAY_OF_YEAR);
		cal.setTime(d);
		int pyear = cal.get(Calendar.YEAR);
		int pdate = cal.get(Calendar.DAY_OF_YEAR);
		if (pyear < year || (pyear == year && pdate < date))
			return true;
		return false;
	}

	/**
	 * 两个日期是否为同一天
	 * 
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static boolean isSameDay(Date d1, Date d2) {
		if (d1 == null || d2 == null) {
			return false;
		}
		Calendar c1 = Calendar.getInstance();
		c1.setTime(d1);
		Calendar c2 = Calendar.getInstance();
		c2.setTime(d2);
		boolean syear = c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR);
		boolean sday = c1.get(Calendar.DAY_OF_YEAR) == c2
				.get(Calendar.DAY_OF_YEAR);
		return syear && sday;
	}

	/**
	 * 根据日期获得秒数
	 * 
	 * @param date
	 * @return
	 * @author PCY
	 * @date 2014年8月26日
	 */
	public static long getDateSecond(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.getTimeInMillis() / NS;
	}

	/**
	 * 比较开始时间与结束时间的时间间隔是否在给定间隔天数内
	 * 
	 * @param startTime
	 * @param endTime
	 * @param dayLimit
	 *            间隔（天）
	 * @author jia li
	 */
	public static boolean isInTheTime(long startTime, long endTime, int dayLimit) {
		if ((startTime + dayLimit * 24 * 60 * 60 * 1000l) > endTime) {
			return true;
		}
		return false;
	}

	/**
	 * 获取两个Date对象之间的天数
	 * 
	 * @param startDate
	 *            开始日期
	 * @param endDate
	 *            截止日期
	 * @return
	 */
	public static int getDays(Date startDate, Date endDate) {
		if (startDate == null) {
			startDate = new Date();
		}
		long start = startDate.getTime();
		long end = endDate.getTime();
		int diffDays = (int) ((end - start) / ND);
		return diffDays;
	}

	/**
	 * 计算两个日期之间相隔的天数
	 * 
	 * @author guobangli
	 * @data 2015年1月8日 下午2:23:57
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static int diffDays(Date startDate, Date endDate) {
		if (startDate == null || endDate == null) {
			return 0;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(startDate);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		long start = calendar.getTime().getTime();

		calendar.setTime(endDate);
		long end = calendar.getTime().getTime();
		int diffDays = (int) ((end - start) / ND);

		return diffDays;
	}

	public static int diffDays(long start, long end) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(start);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		int diffDays = (int) ((end - calendar.getTimeInMillis()) / ND);
		return diffDays;
	}

	/**
	 * 获取指定日期经过多少天后的日期
	 * 
	 * @param date
	 * @param days
	 * @return
	 */
	public static Date getAssignDate(Date date, int days) {
		if (date == null) {
			date = new Date();
		}
		Calendar now = Calendar.getInstance();
		now.setTime(date);
		now.set(Calendar.DATE, now.get(Calendar.DATE) + days);
		return now.getTime();
	}

	/**
	 * 获取时间的小时数
	 * 
	 * @param date
	 * @return
	 * @author PCY
	 * @date 2014年9月17日
	 */
	public static int getHour(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * 获取时间的分钟数
	 * 
	 * @param date
	 * @return
	 * @author PCY
	 * @date 2014年9月17日
	 */
	public static int getMimute(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("mm");
		String format = sdf.format(date);
		return Integer.parseInt(format);
	}

	/**
	 * 获取时间的秒数
	 * 
	 * @param date
	 * @return
	 * @author PCY
	 * @date 2014年9月17日
	 */
	public static int getSecond(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("ss");
		String format = sdf.format(date);
		return Integer.parseInt(format);
	}

	/**
	 * 增加天单位时间
	 * 
	 * @param date
	 * @param second
	 * @return
	 * @author PCY
	 * @date 2014年12月18日
	 */
	public static Date addDay(Date date, int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, day);
		return calendar.getTime();
	}

	/**
	 * 增加小时单位时间
	 * 
	 * @param date
	 * @param hour
	 * @return
	 * @author PCY
	 * @date 2014年9月17日
	 */
	public static Date addHour(Date date, int hour) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.HOUR_OF_DAY, hour);
		return calendar.getTime();
	}

	/**
	 * 增加秒单位时间
	 * 
	 * @param date
	 * @param second
	 * @return
	 * @author PCY
	 * @date 2014年12月18日
	 */
	public static Date addSecond(Date date, int second) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.SECOND, second);
		return calendar.getTime();
	}

	/**
	 * 获取今天星期几 一二三四五六日对应：1234567
	 * 
	 * @return
	 */
	public static int getTodayWeekOfDay() {
		Calendar calendar = Calendar.getInstance();
		int day = calendar.get(Calendar.DAY_OF_WEEK);
		return day == 1 ? 7 : (day - 1);
	}

	/**
	 * 得到每天指定整点毫秒
	 * 
	 * @author guobangli
	 * @data 2014年11月13日 上午11:03:38
	 * @return
	 */
	public static long getHourTime(int hour) {
		Calendar ca = Calendar.getInstance();
		ca.setTime(new Date());
		ca.set(Calendar.HOUR_OF_DAY, hour);
		ca.set(Calendar.MINUTE, 0);
		ca.set(Calendar.SECOND, 0);
		ca.set(Calendar.MILLISECOND, 0);
		return ca.getTimeInMillis();
	}

	/**
	 * 获取当前时间的接下来的整点
	 * 
	 * @author guobangli
	 * @data 2014年11月26日 上午11:36:15
	 * @param hour
	 * @return
	 */
	public static Date getDateHour(int hour) {
		Calendar ca = Calendar.getInstance();
		ca.setTime(new Date());
		ca.add(Calendar.HOUR_OF_DAY, hour);
		ca.set(Calendar.MINUTE, 0);
		ca.set(Calendar.SECOND, 0);
		ca.set(Calendar.MILLISECOND, 0);
		return ca.getTime();
	}

	/**
	 * 判断是否在时间内
	 * 
	 * @param minDate
	 * @param maxDate
	 * @param judge
	 * @return
	 * @author PCY
	 * @date 2014年12月18日
	 */
	public static boolean isBetween(Date minDate, Date maxDate, Date judge) {
		if (minDate == null || maxDate == null || judge == null) {
			return false;
		}
		if (minDate.after(maxDate))
			return false;
		if (judge.equals(minDate))
			return true;
		return judge.after(minDate) && judge.before(maxDate);
	}

	/**
	 * 得到今天对应时间
	 * 
	 * @author guobangli
	 * @data 2015年1月12日 上午10:06:48
	 * @param hour
	 * @param mimute
	 * @param second
	 * @return
	 */
	public static Date getTodayTime(int hour, int mimute, int second) {
		if (hour > 23)
			hour = 0;
		if (mimute > 59)
			mimute = 0;
		if (second > 59)
			second = 0;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.set(Calendar.HOUR_OF_DAY, hour);
		calendar.set(Calendar.MINUTE, mimute);
		calendar.set(Calendar.SECOND, second);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	/**
	 * 判断是不是同一个月
	 * 
	 * @param millTime
	 *            毫秒值
	 * @return
	 * @since 2015年4月1日 下午5:29:22
	 * @author LSQ
	 */
	public static boolean isDiffMonth(Date date) {
		if (date == null) {
			return false;
		}
		SimpleDateFormat sdfs = new SimpleDateFormat("MM");
		String hisMonth = sdfs.format(date);
		String currMonth = sdfs.format(new Date());

		if (Integer.parseInt(hisMonth) != Integer.parseInt(currMonth)) {
			return true;
		}
		return false;
	}

	/**
	 * 设置今天的时间
	 * 
	 * @param hour
	 * @param mimute
	 * @return
	 * @since 2015年10月28日 下午2:34:53
	 * @author PCY
	 */
	public static Date setTodayTime(int hour, int mimute) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.set(Calendar.HOUR_OF_DAY, hour);
		calendar.set(Calendar.MINUTE, mimute);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	/**
	 * 获取月份
	 * 
	 * @return
	 * @since 2015年10月26日 下午3:54:20
	 * @author LSQ
	 */
	public static int getMonth() {
		SimpleDateFormat sdfs = new SimpleDateFormat("MM");
		String month = sdfs.format(new Date());
		return Integer.parseInt(month);
	}

	/**
	 * 获取今天是这个月的几号
	 * 
	 * @return
	 * @since 2015年10月26日 下午6:06:33
	 * @author LSQ
	 */
	public static int getTodayMonthOfDay() {
		Calendar calendar = Calendar.getInstance();
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		return day;
	}

	/**
	 * 获取一个月的最后一天
	 * 
	 * @param addMonth
	 * @param formatStr
	 * @return
	 * @since 2015年10月26日 下午6:06:49
	 * @author LSQ
	 */
	public static String getLastMonthOfDay(int addMonth, String formatStr) {
		Calendar calendar = Calendar.getInstance();
		int MM = Integer.parseInt(DateUtil.format(new Date(), "MM"));
		calendar.set(Calendar.MONTH, MM - 1 + addMonth);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
		DateFormat format = new SimpleDateFormat(formatStr);
		return format.format(calendar.getTime());
	}

	/**
	 * 获取最大的日期
	 * 
	 * @param d1
	 * @param d2
	 * @return
	 * @since 2015年11月4日 下午4:40:50
	 * @author PCY
	 */
	public static Date getMax(Date d1, Date d2) {
		if (d1 == null && d2 == null)
			return null;
		if (d1 == null)
			return d2;
		if (d2 == null)
			return d1;
		return d1.getTime() > d2.getTime() ? d1 : d2;
	}

	/**
	 * 获取今周的日期
	 * 
	 * @param d
	 *            周几
	 * @param h
	 *            小时
	 * @param m
	 *            分钟
	 * @param s
	 *            秒
	 * @return
	 * @since 2015年11月28日 下午6:00:05
	 * @author PCY
	 */
	public static Date getDayOfWeek(int d, int h, int m, int s) {
		Calendar calendar = Calendar.getInstance();
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		if (dayOfWeek == 0)
			dayOfWeek = 7;
		calendar.add(Calendar.DATE, -dayOfWeek + d);
		calendar.set(Calendar.HOUR_OF_DAY, h);
		calendar.set(Calendar.MINUTE, m);
		calendar.set(Calendar.SECOND, s);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	/**
	 * 得到指定天的指定时间
	 * 
	 * @param date
	 * @param hour
	 * @param minute
	 * @param second
	 * @return
	 * @since 2015年12月8日 下午5:45:45
	 */
	public static Date getDayAssignTime(Date date, int hour, int minute,
			int second) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, hour);
		calendar.set(Calendar.MINUTE, minute);
		calendar.set(Calendar.SECOND, second);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}
}
