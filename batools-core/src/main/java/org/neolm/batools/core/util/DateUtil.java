package org.neolm.batools.core.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	
	public static final String DATE_FORMAT_YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
	public static final String DATE_FORMAT_YYYYMMDDHHMMSS_ORACLE = "yyyymmddHH24miss";
	public static final String DATE_FORMAT_YYYYMMDDHHMM = "yyyyMMddHHmm";
	public static final String DATE_FORMAT_YYYYMMDDHH = "yyyyMMddHH";
	public static final String DATE_FORMAT_YYYYMMDD = "yyyyMMdd";
	public static final String DATE_FORMAT_YYYYMM = "yyyyMM";
	public static final String DATE_FORMAT_YYYY = "yyyy";
	public static final String DATE_FORMAT_EN_A_YYYYMMDDHHMMSS = "yyyy/MM/dd HH:mm:ss";
	public static final String DATE_FORMAT_EN_A_YYYYMMDDHHMM = "yyyy/MM/dd HH:mm";
	public static final String DATE_FORMAT_EN_A_YYYYMMDDHH = "yyyy/MM/dd HH";
	public static final String DATE_FORMAT_EN_A_YYYYMMDD = "yyyy/MM/dd";
	public static final String DATE_FORMAT_EN_A_YYYYMM = "yyyy/MM";
	public static final String DATE_FORMAT_EN_A_YYYY = "yyyy";
	public static final String DATE_FORMAT_EN_B_YYYYMMDDHHMMSS = "yyyy-MM-dd HH:mm:ss";
	public static final String DATE_FORMAT_EN_B_YYYYMMDDHHMM = "yyyy-MM-dd HH:mm";
	public static final String DATE_FORMAT_EN_B_YYYYMMDDHH = "yyyy-MM-dd HH";
	public static final String DATE_FORMAT_EN_B_YYYYMMDD = "yyyy-MM-dd";
	public static final String DATE_FORMAT_EN_B_YYYYMM = "yyyy-MM";
	public static final String DATE_FORMAT_EN_B_YYYY = "yyyy";
	public static final String DATE_FORMAT_CN_YYYYMMDDHHMMSS = "yyyy'骞�MM'鏈�dd'鏃� HH'鏃�mm'鍒�ss'绉�";
	public static final String DATE_FORMAT_CN_YYYYMMDDHHMM = "yyyy'骞�MM'鏈�dd'鏃� HH'鏃�mm'鍒�";
	public static final String DATE_FORMAT_CN_YYYYMMDDHH = "yyyy'骞�MM'鏈�dd'鏃� HH'鏃�";
	public static final String DATE_FORMAT_CN_YYYYMMDD = "yyyy'骞�MM'鏈�dd'鏃�";
	public static final String DATE_FORMAT_CN_YYYYMM = "yyyy'骞�MM'鏈�";
	public static final String DATE_FORMAT_CN_YYYY = "yyyy'骞�";

	/**
     * 鑾峰彇褰撳墠鏃堕棿鐨勬柟娉曪紝缁熶竴浣跨敤璇ユ帴鍙ｆ柟渚夸互鍚庤浆鎹㈠疄鐜版柟寮�
     * @return
     */
    public static Date now(){
    	return new Date();
    }
    
    
    /**
	 * 鑾峰彇褰撳墠鏃堕棿瀛楃涓�
	 * 
	 * @param pattern 鏍煎紡鍖栨牱寮�
	 * @return 缁忚繃鏍煎紡鍖栫殑褰撳墠鏃堕棿瀛楃涓�
	 */
	public static String now(String pattern) {
		if (pattern == null || "".equals(pattern.trim())) {
			pattern = DATE_FORMAT_YYYYMMDDHHMMSS;
		}
		return formatDate(now(), pattern);
	}
	


    /**
     * 鏍煎紡鍖栨棩蹇椾负鎸囧畾鏍峰紡鐨勫瓧绗︿覆
     * 
     * @param date 缁欏畾鐨勬棩鏈熷璞�
     * @param pattern 鏍煎紡鍖栭噰鐢ㄧ殑鏍峰紡
     * @return 鏍煎紡鍖栧悗鐨勫瓧绗︿覆
     */
    public static String formatDate(Date date, String pattern) {
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        return formatter.format(date);
    }
    
    /**
     * 瀵硅緭鍏ョ殑鏃ユ湡瀛楃涓茶繘琛屾牸寮忓寲.
     *
     * @param strDate     闇�杩涜鏍煎紡鍖栫殑鏃ユ湡锛屾牸寮忎负鍓嶉潰瀹氫箟鐨�DATE_FORMAT_YYYYMMDDHHMMSS
     * @return 缁忚繃鏍煎紡鍖栧悗鐨勫瓧绗︿覆
     */
    public static Date getFormattedDate(String strDate) {
        String formatStr = "yyyyMMdd";
        if (strDate == null || strDate.trim().equals("")) {
            return null;
        }
        switch (strDate.trim().length()) {
            case 6:
            	//淇鈥�10501鈥濇椂闂存牸寮忚浆鎹㈤敊璇棶棰�
                if (strDate.substring(0, 1).equals("0")
                		||strDate.substring(0, 1).equals("1")) {
                    formatStr = "yyMMdd";
                } else {
                    formatStr = "yyyyMM";
                }
                break;
            case 8:
                formatStr = "yyyyMMdd";
                break;
            case 10:
                if (strDate.indexOf("-") == -1) {
                    formatStr = "yyyy/MM/dd";
                } else {
                    formatStr = "yyyy-MM-dd";
                }
                break;
            case 11:
                if (strDate.getBytes().length == 14) {
                    formatStr = "yyyy年MM月dd日";
                } else {
                    return null;
                }
            case 14:
                formatStr = "yyyyMMddHHmmss";
                break;
            case 19:
                if (strDate.indexOf("-") == -1) {
                    formatStr = "yyyy/MM/dd HH:mm:ss";
                } else {
                    formatStr = "yyyy-MM-dd HH:mm:ss";
                }
                break;
            default:
                return null;
        }
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(formatStr);
            return formatter.parse(strDate);
        } catch (Exception e) {
        	
            return null;
        }
    }

    
    /**
     * 瀵硅緭鍏ョ殑鏃ユ湡瀛楃涓茶繘琛屾牸寮忓寲.
     *
     * @param strDate     闇�杩涜鏍煎紡鍖栫殑鏃ユ湡锛屾牸寮忎负鍓嶉潰瀹氫箟鐨�DATE_FORMAT_YYYYMMDDHHMMSS
     * @param strFormatTo 鎸囧畾閲囩敤浣曠鏍煎紡杩涜鏍煎紡鍖栨搷浣�
     * @return 缁忚繃鏍煎紡鍖栧悗鐨勫瓧绗︿覆
     */
    public static String getFormattedDate(String strDate, String strFormatTo) {
        String formatStr = "yyyyMMdd";
        if (strDate == null || strDate.trim().equals("")) {
            return "";
        }
        switch (strDate.trim().length()) {
            case 6:
            	
                if (strDate.substring(0, 1).equals("0")
                		||strDate.substring(0, 1).equals("1")) {
                    formatStr = "yyMMdd";
                } else {
                    formatStr = "yyyyMM";
                }
                break;
            case 8:
                formatStr = "yyyyMMdd";
                break;
            case 10:
                if (strDate.indexOf("-") == -1) {
                    formatStr = "yyyy/MM/dd";
                } else {
                    formatStr = "yyyy-MM-dd";
                }
                break;
            case 11:
                if (strDate.getBytes().length == 14) {
                    formatStr = "yyyy年MM月dd日";
                } else {
                    return "";
                }
            case 14:
                formatStr = "yyyyMMddHHmmss";
                break;
            case 19:
                if (strDate.indexOf("-") == -1) {
                    formatStr = "yyyy/MM/dd HH:mm:ss";
                } else {
                    formatStr = "yyyy-MM-dd HH:mm:ss";
                }
                break;
            default:
                return strDate.trim();
        }
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(formatStr);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(formatter.parse(strDate));
            formatter = new SimpleDateFormat(strFormatTo);
            return formatter.format(calendar.getTime());
        } catch (Exception e) {
        	
            return "";
        }
    }



    /**
     * 寰楀埌鎸囧畾鏃ユ湡 yyyyMMddHHmmss 鍓嶅悗 鎸囧畾澶╃殑鏁版嵁
     *
     * @param strNowDay
     * @param nDelaySeconds
     * @return
     */
    public static String getAddDayStr(String strNowDay, int nDelayDays) {
        if (strNowDay.length() != 14) {
        	strNowDay = getFormattedDate(strNowDay,DATE_FORMAT_YYYYMMDDHHMMSS);
        }
        
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT_YYYYMMDDHHMMSS);
            formatter.setLenient(false);
            Date date = formatter.parse(strNowDay);
            Date datetmp = new Date(date.getTime() + nDelayDays*24*60*60*1000);
            //return formatter.format(datetmp);
            return formatDate(datetmp, DATE_FORMAT_YYYYMMDD);
        } catch (Exception e) {
        	
            return "";
        }
    }

}
