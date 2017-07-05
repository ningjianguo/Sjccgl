package com.wonders.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * 日期转换类
 *
 */
public class DateUtils extends org.apache.commons.lang.time.DateUtils {

	public static final String ISO_DATE_FORMAT = "yyyy-MM-dd";
	public static final String ISO_DATETIME_NO_T_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final String ISO_DATE_NO_HYPHEN_FORMAT = "yyyyMMdd";
	public static final String ISO_DATETIME_NO_HYPHEN_AND_T_FORMAT = "yyyyMMdd HH:mm:ss";

	/**
	 * 将timestamp转换为Date类型
	 */
	public static Date convertTimestampToDate(Timestamp timestamp) {
		return new Date(timestamp.getTime());
	}

	/**
	 * 将日期按照指定模式转换为字符串
	 * @param date 日期
	 * @param pattern 指定模式
	 * @return 转换后的字符串
	 */
	public static String convertToString(final Date date, final String pattern) {
		if (date != null) {
			return new SimpleDateFormat(pattern).format(date);
		} else {
			return null;
		}
	}

	/**
	 * 将字符串按照指定格式转换为日期
	 * @param source 字符串
	 * @param pattern 指定格式
	 * @return 日期
	 * @throws ParseException
	 */
	public static Date converToDate(final String source, final String pattern) throws ParseException {
		return new SimpleDateFormat(pattern).parse(source);
	}

	/**
	 * 取得当前年份
	 * @return 当前年份
	 */
	public static int getCurrentYear() {
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.YEAR);
	}

	/**
	 * 取得当前月份
	 * @return 当前月份
	 */
	public static int getCurrentMonth() {
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.MONTH) + 1;
	}

	/**
	 * 取得当前月份的第一天对应的日期字符串
	 */
	public static String getBeginDateOfMonth() {
		Calendar calendar = Calendar.getInstance();
		String year = String.valueOf(calendar.get(Calendar.YEAR));
		String month = calendar.get(Calendar.MONTH) < 9 ? "0" + String.valueOf(calendar.get(Calendar.MONTH) + 1)
				: String.valueOf(calendar.get(Calendar.MONTH) + 1);
		return year + "-" + month + "-01";
	}

	/**
	 * 取得今天对应的日期字符串
	 */
	public static String getCurrentDate() {
		return convertToString(new Date(), ISO_DATE_FORMAT);
	}

	/**
	 * 判断一个日期字符串是否合法
	 * @param date 日期字符串
	 * @param pattern 日期模式
	 * @return 合法则返回true
	 */
	public static boolean isValidDate(final String date, final String pattern) {
		if (date != null) {
			try {
				converToDate(date, pattern);
				return true;
			} catch (ParseException e) {
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * 将日期转换为当日开始的日期
	 */
	public static Date getBeginDateOfDay(final Date date) {
		try {
			return converToDate(convertToString(date, ISO_DATE_FORMAT) + " 00:00:00", ISO_DATETIME_NO_T_FORMAT);
		} catch (ParseException e) {
			throw new RuntimeException(e.getMessage(), e); // Will Never happen
		}
	}

	/**
	 * 将输入的日期字符串转换为当日开始日期
	 * @throws ParseException 
	 */
	public static Date getBeginDateOfDay(final String date, final String pattern) throws ParseException {
		return converToDate(date + " 00:00:00", pattern);
	}

	/**
	 * 将输入日期转换为当日结束日期
	 */
	public static Date getEndDateOfDay(final Date date) {
		try {
			return converToDate(convertToString(date, ISO_DATE_FORMAT) + " 23:59:59", ISO_DATETIME_NO_T_FORMAT);
		} catch (ParseException e) {
			throw new RuntimeException(e.getMessage(), e); // Will Never happen
		}
	}

	/**
	 * 将输入的日期字符串转换为当日结束日期
	 * @throws ParseException 
	 */
	public static Date getEndDateOfDay(final String date, final String pattern) throws ParseException {
		return converToDate(date + " 23:59:59", pattern);
	}
	
	/**
	  * 求某年月的第一天 
	  * java里0到11分别是1到12月
	  * @param year
	  * @param month
	  * @return Date
	  */
	 public static Date getMonthFirst(int year, int month) {
		 Calendar calendar = Calendar.getInstance();
         calendar.clear();
         calendar.set(Calendar.YEAR, year);
         calendar.set(Calendar.MONTH, month);
         Date currYearFirst = calendar.getTime();
         return currYearFirst;
	 }

	 /**
	  * 求某年月的最后一天 
	  * java里0到11分别是1到12月
	  * @param year
	  * @param month
	  * @return Date
	  */
	 public static Date getMonthLast(int year, int month) {
		 Calendar calendar = Calendar.getInstance();
         calendar.clear();
         calendar.set(Calendar.YEAR, year);
         calendar.set(Calendar.MONTH, month);
         calendar.roll(Calendar.DAY_OF_MONTH, -1);//向指定日历字段添加指定（有符号的）时间量，不更改更大的字段。负的时间量意味着向下滚动。 
         Date currMonthLast = calendar.getTime();

	  return currMonthLast;
	 }
	 
	 /**
	  * 某年某月第几周第几天 是几月几号
	  * @param year          年份
	  * @param month         月份
	  * @param weekOfMonth   这个月的第几周
	  * @param dayOfWeek     星期几
	  * @return
	  */
	 public static String weekDateToData(int year,int month,int weekOfMonth,int dayOfWeek){
		  Calendar c = Calendar.getInstance();
		  //计算出 x年 y月 1号 是星期几
		  c.set(year, month-1, 1);
		  
		  //如果i_week_day =1 的话 实际上是周日  
		  int i_week_day = c.get(Calendar.DAY_OF_WEEK);
		  
		  int sumDay = 0;
		  //dayOfWeek+1 就是星期几（星期日 为 1）
		  if(i_week_day == 1){
		   sumDay = (weekOfMonth-1)*7 + dayOfWeek+1;
		  }else{
		   sumDay = 7-i_week_day+1 +  (weekOfMonth-1)*7 + dayOfWeek +1;
		  }
		  //在1号的基础上加上相应的天数
		  c.set(Calendar.DATE,  sumDay);
		  SimpleDateFormat sf2 = new SimpleDateFormat("yyyy-MM-dd");
		  return sf2.format(c.getTime());
	 }
	 
    /** 
     * 获得当天之后的N天日期 
     * @param num 当天后的第N天 
     * @return 返回的日期 
     */ 
    public static List<String> getNextDays(int num) { 
        List<String> list = new ArrayList<String>(); 
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD"); 
        for (int i = 1; i < num; i++) { 
            list.add(sdf.format(getAfterDate(i))); 
        } 
        return list; 
    } 
    
    /** 
     * 获取当前日期n天后的日期 
     * @param n:返回当天后的第N天 
     * @return 返回的日期 
     */ 
    public static Date getAfterDate(int n) { 
        Calendar c = Calendar.getInstance(); 
        c.add(Calendar.DAY_OF_MONTH, n); 
        return c.getTime(); 
    }
    
    /** 
     * 获取当前日期n天后的日期 
     * @param n:返回当天后的第N天 
     * @return 返回的日期 
     */ 
    public static Date getAfterDate(Date date,int n) { 
    	Calendar calendar = new GregorianCalendar(); 
    	calendar.setTime(date); 
    	calendar.add(Calendar.DAY_OF_MONTH, n); 
        return calendar.getTime(); 
    }
    
    /**
     * 将util.date转换成XMLGregorianCalendar
     * */
    @SuppressWarnings("static-access")
	public static XMLGregorianCalendar getXMLGregorianCalendar(Date date) throws Exception {
	    Calendar calendar = Calendar.getInstance();
	    calendar.setTime(date);
	    DatatypeFactory dtf = DatatypeFactory.newInstance();
	    return dtf.newXMLGregorianCalendar(
	    calendar.get(calendar.YEAR),
	    calendar.get(calendar.MONTH)+1,
	    calendar.get(calendar.DAY_OF_MONTH),
	    calendar.get(calendar.HOUR),
	    calendar.get(calendar.MINUTE),
	    calendar.get(calendar.SECOND),
	    calendar.get(calendar.MILLISECOND),
	    calendar.get(calendar.ZONE_OFFSET)/(1000*60));
    }
}
