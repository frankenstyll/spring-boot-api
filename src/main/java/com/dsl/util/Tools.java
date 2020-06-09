package com.dsl.util;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Vector;

//import javax.mail.Message;
//import javax.mail.Multipart;
//import javax.mail.Session;
//import javax.mail.Transport;
//import javax.mail.internet.InternetAddress;
//import javax.mail.internet.MimeBodyPart;
//import javax.mail.internet.MimeMessage;
//import javax.mail.internet.MimeMultipart;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

//import java.util.Base64;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

/**
 * Store tools for develop application
 * @version 1.00 25 Mar 2002
 * @author Prawith Thowphant
 */
@SuppressWarnings("restriction")
public class Tools {
//    private static String crystalServer = PccProperties.getResourceString(PccProperties.environment + "_CrystalServer");
//    private static String crystalCluster = PccProperties.getResourceString("CrystalCluster" + crystalServer);
//    private static String crystalUserid = PccProperties.getResourceString("CrystalUserid" + crystalServer);
//    private static String crystalPassword = PccProperties.getResourceString("CrystalPassword" + crystalServer);
//    private static String crystalDB = PccProperties.getResourceString("CrystalDB" + crystalServer);
//    private static String crystalServerName = PccProperties.getResourceString("CrystalServerName" + crystalServer);
//    private static String crystalDBUserid = PccProperties.getResourceString("CrystalDBUserid" + crystalServer);
//    private static String crystalDBPassword = PccProperties.getResourceString("CrystalDBPassword" + crystalServer);
//    private static String crystalDBServerType = PccProperties.getResourceString("CrystalDBServerType" + crystalServer);

    public Tools() {
    }
    
    /**
     * เปลี่ยนค่า double ให้เป็นคำอ่านภาษาไทย
     * @param dVal ค่าที่ต้องการเปลี่ยน สามารถน้อยกว่าศูนย์ได้
     * @return คำอ่านภาษาไทย
     */
    public static String thaiDecimal(double dVal) {
        NumberFormat form;
        String strVal, strBath, strStang;
        StringBuffer thaiValue = new StringBuffer("");
        int strLen;
        
        if (dVal == 0.0)
            return "ศูนย์บาท";
        
        // เปลี่ยนค่าจาก input ให้เป็น String ในรูปแบบมีทศนิยมสองตำแหน่ง
        form = NumberFormat.getInstance();
        ((DecimalFormat)form).applyPattern("0.00");
        strVal = form.format(dVal);
        
        // แบ่งค่าจำนวนเต็มออกจากทศนิยม แล้วทำการ reverse ค่าทั้งสองเตรียมแปลงเป็นตัวอักษร
        strLen = strVal.length();
        strBath =
                new String(new StringBuffer(strVal.substring(0, strLen - 3)).reverse());
        strStang =
                new String(new StringBuffer(strVal.substring(strLen - 2)).reverse());
        
        // ตรวจสอบค่าสตางค์
        if (strStang.compareTo("00") == 0)
            thaiValue.insert(0, "ถ้วน");
        else {
            thaiValue.insert(0, "สตางค์");
            thaiValue.insert(0, digitAlpha(strStang));
        }
        
        // ตรวจสอบค่าบาท
        if (strBath.compareTo("0") != 0) {
            thaiValue.insert(0, "บาท");
            thaiValue.insert(0, digitAlpha(strBath));
        }
        
        return thaiValue.toString();
    }
    
    /**
     * เปลี่ยนค่า double ให้เป็นคำอ่านภาษาไทย
     * @param dVal ค่าที่ต้องการเปลี่ยน สามารถน้อยกว่าศูนย์ได้
     * @return คำอ่านภาษาไทย
     */
    public static String thaiDecimalNoBath(double dVal) {
        NumberFormat form;
        String strVal, strBath, strStang;
        StringBuffer thaiValue = new StringBuffer("");
        int strLen, pointPos;
        
        if (dVal == 0.0)
            return "ศูนย์";
        
        // เปลี่ยนค่าจาก input ให้เป็น String ในรูปแบบมีทศนิยมสองตำแหน่ง
        form = NumberFormat.getInstance();
        ((DecimalFormat)form).applyPattern("0.0########");
        strVal = form.format(dVal);
        
        // แบ่งค่าจำนวนเต็มออกจากทศนิยม แล้วทำการ reverse ค่าทั้งสองเตรียมแปลงเป็นตัวอักษร
        strLen = strVal.length();
        pointPos = strVal.indexOf(".");
        //System.out.println("pointPos = " + pointPos + "|strVal="+strVal);
        strBath =
                new String(new StringBuffer(strVal.substring(0, pointPos)).reverse());
        strStang =
                new String(new StringBuffer(strVal.substring(pointPos+1)).reverse());
        
        // ตรวจสอบค่าสตางค์
        if (strStang.compareTo("0") != 0) {
            thaiValue.insert(0, digitAlphaNoUnit(strStang));
            thaiValue.insert(0, "จุด");
        }
        
        // ตรวจสอบค่าบาท
        if (strBath.compareTo("0") != 0) {
            thaiValue.insert(0, digitAlpha(strBath));
        }
        
        return thaiValue.toString();
    }
    
    /**
     * เปลี่ยนค่าจากตัวเลขให้เป็นคำอ่านภาษาไทย
     * @return คำอ่านภาษาไทย
     */
    public static String digitAlpha(String strVal) {
        String thaiDigit[] =
        { "ศูนย์", "หนึ่ง", "สอง", "สาม", "สี่", "ห้า", "หก", "เจ็ด", "แปด",
          "เก้า", "เอ็ด", "ยี่", "ลบ" };
        String thaiUnit[] = { "ล้าน", "สิบ", "ร้อย", "พัน", "หมื่น", "แสน" };
        StringBuffer digitValue = new StringBuffer("");
        int strLen, iUnit;
        char charVal;
        
        // ถ้าค่าที่ได้รับเป็นเลขศูนย์ตัวเดียว จะส่งค่ากลับเป็นคำว่า "ศูนย์"
        strLen = strVal.length();
        if (strLen == 0 && strVal.charAt(0) == '0') {
            digitValue.insert(0, thaiDigit[0]);
            return digitValue.toString();
        }
        
        // วนแปลงตัวเลขเป็นตัวอักษรจนหมดทุกตัว
        for (int i = 0; i < strLen; i++) {
            charVal = strVal.charAt(i);
            
            // หาหลักที่ของตัวเลข
            iUnit = i % 6;
            
            // ถ้าค่าตัวเลขเป็นศูนย์ ไม่ต้องแสดงตัวอักษร
            if (charVal == '0') {
                if (iUnit == 0 && i > 0)
                    digitValue.insert(0, thaiUnit[iUnit]);
                continue;
            }
            
            // ถ้าเป็นเครื่องหมายลบ จะแสดงคำว่า "ลบ" แล้วหยุด
            if (charVal == '-') {
                digitValue.insert(0, thaiDigit[12]);
                break;
            }
            
            if (i > 0)
                // ถ้าไม่ใช่หลักหน่วยจะแสดงชื่อหลัก
                digitValue.insert(0, thaiUnit[iUnit]);
            
            // ถ้าหลักสิบเป็นเลขหนึ่ง ไม่ต้องแสดงตัวอักษร
            if (iUnit == 1 && charVal == '1')
                continue;
            
            // ตรวจสอบว่าถ้าหลักสิบ หรือหลักร้อยมีค่าให้แสดงเลขหนึ่งที่หลักหน่วยว่า "เอ็ด"
            if (iUnit == 0 && charVal == '1') {
                boolean testOne = false;
                for (int j = i + 1; j < strLen; j++) {
                    if (j - i == 3)
                        break;
                    if (strVal.charAt(j) > '0') {
                        testOne = true;
                        break;
                    }
                }
                if (testOne)
                    digitValue.insert(0, thaiDigit[10]);
                else
                    digitValue.insert(0, thaiDigit[1]);
            } else if (iUnit == 1 && charVal == '2') {
                digitValue.insert(0, thaiDigit[11]);
                // แสดงคำว่า "ยี่" ถ้าหลักสิบเป็นเลขสอง
            } else {
                digitValue.insert(0, thaiDigit[charVal - '0']);
                // แสดงตัวอักษรของเลข
            }
        }
        
        return digitValue.toString();
    }
    
    /**
     * เปลี่ยนค่าจากตัวเลขให้เป็นคำอ่านภาษาไทย
     * @return คำอ่านภาษาไทย
     */
    public static String digitAlphaNoUnit(String strVal) {
        String thaiDigit[] =
        { "ศูนย์", "หนึ่ง", "สอง", "สาม", "สี่", "ห้า", "หก", "เจ็ด", "แปด",
          "เก้า", "เอ็ด", "ยี่", "ลบ" };
        StringBuffer digitValue = new StringBuffer("");
        int strLen;
        char charVal;
        
        // ถ้าค่าที่ได้รับเป็นเลขศูนย์ตัวเดียว จะส่งค่ากลับเป็นคำว่า "ศูนย์"
        strLen = strVal.length();
        if (strLen == 0 && strVal.charAt(0) == '0') {
            digitValue.insert(0, thaiDigit[0]);
            return digitValue.toString();
        }
        
        // วนแปลงตัวเลขเป็นตัวอักษรจนหมดทุกตัว
        for (int i = 0; i < strLen; i++) {
            charVal = strVal.charAt(i);
            
            digitValue.insert(0, thaiDigit[charVal - '0']);
            // แสดงตัวอักษรของเลข
        }
        
        return digitValue.toString();
    }
    
    /**
     * เปลี่ยน Encoding Type จาก Unicode เป็น MS874
     * @param s Unicode String
     * @return MS874 String
     */
    public static String UnicodeToMS874(String s) {
        if (s == null)
            return "";
        
        StringBuffer stringbuffer = new StringBuffer(s);
        for (int i = 0; i < s.length(); i++) {
            char c = stringbuffer.charAt(i);
            if ('\u0E01' <= c && c <= '\u0E5B')
                stringbuffer.setCharAt(i, (char)(c - 3424));
        }
        
        return stringbuffer.toString();
    }
    
    /**
     * เปลี่ยน Encoding Type จาก MS874 เป็น Unicode
     * @param s MS874 String
     * @return Unicode String
     */
    public static String MS874ToUnicode(String s) {
        if (s == null)
            return "";
        
        StringBuffer stringbuffer = new StringBuffer(s);
        for (int i = 0; i < s.length(); i++) {
            char c = stringbuffer.charAt(i);
            if ('\241' <= c && c <= '\373')
                stringbuffer.setCharAt(i, (char)(c + 3424));
        }
        
        return stringbuffer.toString();
    }
    
    /**
     * เปลี่ยนค่า Double ให้เป็น String ตามรูปแบบที่ต้องการ
     * @param x the double to print
     * @param fmt the format string
     * @return String ที่แปลงรูปแบบแล้ว
     */
    public static String dtoa(double x, String fmt) {
        NumberFormat form;
        
        form = NumberFormat.getInstance();
        ((DecimalFormat)form).applyPattern(fmt);
        return form.format(x);
    }
    
    /**
     * เปลี่ยนค่า String ให้เป็น String ตามรูปแบบที่ต้องการ
     * @param x the double to print
     * @param fmt the format string
     * @return String ที่แปลงรูปแบบแล้ว
     */
    public static String dtoa(String x, String fmt) {
        if (x == null || x.compareTo("") == 0 ||
                x.compareToIgnoreCase("null") == 0)
            return "";
        NumberFormat form;
        
        form = NumberFormat.getInstance();
        ((DecimalFormat)form).applyPattern(fmt);
        return form.format(new Double(x).doubleValue());
    }
    
    /**
     * เพื่อแก้ปัญหาค่า double ลบกันแล้วค่าที่ได้ไม่ถูกต้อง
     * @param d_val1 ตัวตั้ง
     * @param d_val2 ตัวลบ
     * @return ผลลัพธ์ของการลบ
     */
    public static double minusDouble(double d_val1, double d_val2) {
        NumberFormat form;
        final double d_100 = 100.0D;
        double d_return = 0;
        String s_val1, s_val2, s_val3;
        long l_val1, l_val2, l_val3;
        int strLen;
        
        if (d_val1 == -0.00)
            d_val1 = 0.00;
        if (d_val2 == -0.00)
            d_val2 = 0.00;
        form = NumberFormat.getInstance();
        ((DecimalFormat)form).applyPattern("0.00");
        
        s_val1 = form.format(d_val1);
        strLen = s_val1.length();
        s_val2 = s_val1.substring(0, strLen - 3);
        s_val3 = s_val1.substring(strLen - 2);
        s_val1 = s_val2.concat(s_val3);
        l_val1 = Long.parseLong(s_val1);
        
        s_val1 = form.format(d_val2);
        strLen = s_val1.length();
        s_val2 = s_val1.substring(0, strLen - 3);
        s_val3 = s_val1.substring(strLen - 2);
        s_val1 = s_val2.concat(s_val3);
        l_val2 = Long.parseLong(s_val1);
        
        l_val3 = l_val1 - l_val2;
        d_return = (double)l_val3 / d_100;
        
        return d_return;
    }
    
    /**
     * ตรวจสอบวันที่ว่าถูกต้องหรือไม่
     * @param sDate วันที่ที่ต้องการตรวจสอบ รูปแบบ dd/mm/yyyy (ปี พ.ศ.)
     * @return ture or false
     */
    public static boolean validDate(String sDate) {
        String dd = sDate.substring(0, 2);
        String mm = sDate.substring(3, 5);
        String yyyy = sDate.substring(6);
        int day[][] =
        { { 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 },
          { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 } };
        
        int year = Integer.valueOf(yyyy).intValue() - 543;
        int i =
                ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) ? 0 : 1;
        
        if (day[i][(Integer.valueOf(mm).intValue()) - 1] <
                Integer.valueOf(dd).intValue())
            return false;
        else
            return true;
    }
    
    /**
     * Gets the office name in short formf
     * @param officeName office name
     * @return a String
     */
    public static String shortOfficeName(String officeName) {
        if (officeName.indexOf("สำนักงานสรรพสามิต") != -1)
            return officeName.substring(17);
        else
            return officeName;
    }
    
    /**
     * เปลี่ยนค่า double ที่เป็น String ในรูปแบบต่าง ๆ ให้เป็น double เช่น '1,500.50' เป็น 1500.50
     * @param str String double
     * @return a double
     */
    public static double decimalFormatToDouble(String str) {
        double doubleVal = 0;
        if (str == null || str.trim().compareTo("") == 0 ||
                str.trim().compareToIgnoreCase("null") == 0)
            return doubleVal;
        
        try {
            doubleVal = NumberFormat.getInstance().parse(str).doubleValue();
        } catch (Exception e) {
            System.out
                    .println("Exception in method decimalFormatToDouble [" + str + "]= " + e);
        }
        return doubleVal;
    }
    
    /**
     * เปลี่ยนค่า int ที่เป็น String ในรูปแบบต่าง ๆ ให้เป็น double เช่น '1,500.50' เป็น 1500
     * @param str String int
     * @return a int
     */
    public static int intFormatToInt(String str) {
        int intVal = 0;
        if (str == null || str.trim().compareTo("") == 0 ||
                str.trim().compareToIgnoreCase("null") == 0)
            return intVal;
        
        try {
            intVal = NumberFormat.getInstance().parse(str).intValue();
        } catch (Exception e) {
            System.out
                    .println("Exception in method intFormatToInt [" + str + "]= " + e);
        }
        return intVal;
    }
    
    /**
     * เปลี่ยนรูปแบบของ Tin ให้อยู่ในรูปแบบ X-XXXX-XXXX-X
     * @param tin String
     * @return a String
     */
    public static String tinFormat(String tin) {
        if (tin.length() < 10)
            return tin;
        StringBuffer strbuff = new StringBuffer("");
        
        strbuff
                .append(tin.substring(0, 1) + "-" + tin.substring(1, 5) + "-" + tin
                .substring(5, 9) + "-" + tin.substring(9, 10));
        
        return strbuff.toString();
    }
    
    /**
     * เปลี่ยนรูปแบบของ Tin ในรูปแบบ X-XXXX-XXXX-X ให้เป็นรูปแบบ XXXXXXXXXX
     * @param tin String
     * @return a String
     */
    public static String tinFormatToFormal(String tin) {
        if (tin.length() < 13)
            return tin;
        StringBuffer strbuff = new StringBuffer("");
        tin = tin.substring(0,1) + tin.substring(2,6) + tin.substring(7,11) + tin.substring(12);
        strbuff.append(tin);
        return strbuff.toString();
    }
    
    /**
     * เปลี่ยนรูปแบบของ Pin ให้อยู่ในรูปแบบ X-XXXX-XXXXX-XX-X
     * @param pin String
     * @return a String
     */
    public static String pinFormat(String pin) {
        if (pin.length() < 13)
            return pin;
        StringBuffer strbuff = new StringBuffer("");
        
        strbuff
                .append(pin.substring(0, 1) + "-" + pin.substring(1, 5) + "-" + pin
                .substring(5, 10) + "-" + pin.substring(10, 12) + "-" +
                pin.substring(12, 13));
        
        return strbuff.toString();
    }
    
    /**
     * เปลี่ยนรูปแบบของ Pin ในรูปแบบ X-XXXX-XXXXX-XX-X ให้เป็นรูปแบบ  XXXXXXXXXXXXX
     * @param pin String
     * @return a String
     */
    public static String pinFormatToFormal(String pin) {
        if (pin.length() < 17)
            return pin;
        StringBuffer strbuff = new StringBuffer("");
        pin = pin.substring(0,1) + pin.substring(2,6) + pin.substring(7,12) + pin.substring(13, 15) + pin.substring(16);
        strbuff.append(pin);
        return strbuff.toString();
    }
    
    /**
     * ตรวจสอบค่าของ String ที่ส่งเข้าหากเป็น Null จะเปลี่ยนให้เป็นค่าว่าง
     * @param value String
     * @return a String
     */
    public static String chkNull(String value) {
        return (value == null ? "" : value.trim());
    }
    
    /**
     * ตรวจสอบค่าของ String ที่ส่งเข้าหากเป็น Null จะส่งค่ากลับค่าของ double ที่ส่งมา<br>
     * หากค่าของ String ที่ส่งเข้ามาไม่เป็น Null จะทำการเปลี่ยนค่าของ String ให้เป็น double
     * @param strValue String
     * @param doubleZero double
     * @return a double
     */
    public static double chkNull(String strValue, double doubleZero) {
        return ((strValue == null || strValue.trim().compareTo("") == 0 ||
                strValue.trim().compareToIgnoreCase("null") == 0) ?
                    doubleZero : decimalFormatToDouble(strValue));
    }
    
    public static String chkNullInt(String value) {
        return ((value == null || value.trim().compareTo("") == 0 ||
                value.trim().compareToIgnoreCase("null") == 0) ? "null" :
                    (Tools.intFormatToInt(value.trim()) + ""));
    }
    
    public static String chkNullDouble(String value) {
        return ((value == null || value.trim().compareTo("") == 0 ||
                value.trim().compareToIgnoreCase("null") == 0) ? "null" :
                    (Tools.decimalFormatToDouble(value.trim()) + ""));
    }
    
    /**
     * Method getServerDate
     * @return String : yyyy/MM/dd
     * การใช้งาน :  ดึงข้อมูล วัน เดือน ปี พ.ศ. โดยรูปแบบที่ return นั้นคือ yyyy/mm/dd
     */
    public static String getServerDate() {
        String retDate = null;
        try {
            Calendar calendar = Calendar.getInstance();
            Date utilDate = calendar.getTime();
            Date date = new Date( utilDate.getTime() );
            SimpleDateFormat template = new SimpleDateFormat("yyyy/MM/dd");
            retDate = template.format( date );
        } catch( Exception ex ) {
            System.out.println("Error Exception : method getCurrentDate in Class Tools : " + ex.getMessage());
            ex.printStackTrace();
        }
        return retDate;
    }
    
    public static String chkNullToZero(String value) {
        return ((value == null || value.trim().compareTo("") == 0 || value.trim().compareToIgnoreCase("null") == 0) ? "0" :(value.trim()));
    }
    
    /**
     * Method trimSpace
     * param1 str เช่น "  ณ   อยุธยา  เจริญ ธานี   "
     * return  "ณอยุธยาเจริญธานี"
     * By Jjay
     */
    public static String trimSpace(String str){
        String noSpace="";
        if(str.equals("")) return noSpace;
        StringTokenizer token = new StringTokenizer(str," ");
        int loop = 0;
        while(token.hasMoreTokens()){
            loop++;
            String token1 = token.nextToken();
            noSpace += token1;
        }
        return noSpace;
    }
    /**
     * @author naja
     * Method xChgDateEngToThai
     * @param data String
     * @return value String
     * 
     * @edit: kampon.non 26/12/2019, add check null and range condition
     */
    public static String xChgDateEngToThai(String data){
        String value = "";
        if( null == data || "".equals(data) || data.trim().length() == 0) {
        	return "";
        }else if (data.length() >= 10){
            value = data.substring(8,10) + "/" + data.substring(5,7) + "/" + (Integer.parseInt(data.substring(0,4))+543);
        }else if (data.length() == 8){
            value = data.substring(6,8) + "/" + data.substring(4,6) + "/" + (Integer.parseInt(data.substring(0,4))+543);
        }
        return value;
    }
    
    /**@author kampon
     * @param dd/mm/yyyy eng
     * @return yyyy thai
     * @date 26/12/2019
     */
    public static String getThaiYear(String data){
        String value = "";
        if( null == data || "".equals(data) || data.trim().length() == 0) {
        	return "";
        }else if (data.length() >= 10){
            value = ""+(Integer.parseInt(data.substring(0,4))+543);
        }else if (data.length() == 8){
            value = ""+(Integer.parseInt(data.substring(0,4))+543);
        }else if(data.length() == 4) {
        	value = ""+(Integer.parseInt(data)+543);
        }
        return value;
    }
    /**
     * @author naja
     * Method xChgDateEngToThaiTimeStamp
     * @param data String
     * @return value String
     */
    public static String xChgDateEngToThaiTimeStamp(String data){
        String value = "";
        if (data.length() > 10){
            value = data.substring(8,10) + "/" + data.substring(5,7) + "/" + (Integer.parseInt(data.substring(0,4))+543) + " " + data.substring(11);
        }
        return value;
    }
    public static String addZero(String data, int iSize){
        String dataTmp = "";
        for (int i=data.length(); i< iSize; i++){
            dataTmp += "0";
        }
        return dataTmp + data;
    }
    
    
    /*
     * sManrecord = จำนวน size ข้อมูลที่โชว์
     * scountpage = จำนวนpage ที่คำนวณแล้ว คือเศษปัดเป็น 1 page
     * pageNo = Page Running
     * sgrouppage = Group Page เช่น page(1,2,3,4,5 group page = 1) page(6,7,8,9,10,group = 2) ...
     * sprevpage = Running Control page (next or prev) ต้องกำหนด hidden field เมื่อเวลา Click page
     * startrecord = เริ่ม page (ไม่ใช้ข้อมูล แต่เป็น page)
     * endrecord = สิ้นสุด page (ไม่ใช้ข้อมูล แต่เป็น page)
     * record_Group = จำนวนrecoed / gruop
     * ข้อมูลจะ return เป็น StringBuffer
     */
    public static String printPageNo(int perWidth,int sManrecord,int scountpage,String pageNo,String sgrouppage,String sprevpage,int startrecord,int endrecord,int record_Group) {
        StringBuffer sbPage = new StringBuffer("");
        sbPage.append("<table width='"+perWidth+"%' align='center' border='0' id='tableb3' cellspacing='0' cellpadding='0'>\n");
        sbPage.append("<tr class='' height=26>\n");
        sbPage.append("<td width='25%' class=green align ='left'>&nbsp;&nbsp;จำนวนข้อมูลทั้งหมด&nbsp;<font class='green'><b>"+sManrecord+"</b></font>&nbsp;รายการ/จำนวนหน้า&nbsp;<font class='green'><b>"+scountpage+"</b></font>&nbsp;หน้า</td>\n");
        int ii_pagegroup = 0;
        int ii_countpagegroup=1;
        int ii_chkcount=0;
        int iii_prev = 0;
        int iii_startprev = 0;
        int iii_pageprev = 0;
        int iii_next = 0;
        int iii_startnext = 0;
        int iii_pagenext = 0;
        int iii_rec_prev = 0;
        boolean falgprev = false;
        if ( sManrecord > 0) {//aTin_size != null &&
            int detailpage = 0;
            if(pageNo.equals("1")){
                detailpage = 1;
                iii_prev = 1;
                iii_next = 1;
            }else{
                detailpage = Integer.parseInt(pageNo);
                iii_prev = Integer.parseInt(sgrouppage);
                iii_next = Integer.parseInt(sgrouppage);
            }
            if(sgrouppage.equals("")){
                sgrouppage = "1";
            }
            iii_startnext = (iii_next * record_Group);// iii_next*record_Group เป็นค่าtotal เพื่อ pageถัดไป (50 record and 5 page)
            iii_startprev = ((iii_next - 2)* record_Group);// ((iii_next-2)*record_Group) เป็นค่าtotal เพื่อ pageย้อนกลับ (50 record and 5 page)
            if(!sprevpage.equals("")){
                iii_pageprev = Integer.parseInt(sprevpage) - 5;//เพื่อ pageย้อนกลับ
                if(iii_pageprev<=0){
                    iii_pageprev=1;
                }
            }else{
                iii_pageprev=1;
            }
            if(iii_startprev<0){
                sbPage.append("<td width=\"3%\" align=\"left\" ><font color=\"#808080\">ย้อนกลับ</font></td>\n");
            }else{
                sbPage.append("<td width=\"3%\" class=\"green\" align=\"left\" onclick = \"CurrentShowData('"+iii_startprev+"','"+iii_pageprev+"','"+(iii_prev - 1)+"','"+iii_pageprev+"')\" style=\"cursor:hand\"><b>ย้อนกลับ<b></td>\n");
            }
            for(int pagec=1;pagec<=scountpage;pagec++){
                ii_chkcount++;
                if(ii_chkcount>5){// จำนวน page ที่โชว์
                    ii_countpagegroup++;// countgroup จำนวน page
                    ii_chkcount=1;
                }else{
                    if(pagec==scountpage){
                        if(ii_chkcount>5){// จำนวน page ที่โชว์
                            ii_countpagegroup++;
                        }
                    }
                }
                if(!sgrouppage.equals("")){
                    if(Integer.parseInt(sgrouppage) == ii_countpagegroup){//ถ้าเท่ากันโชว์ page นั้น
                        iii_pagenext = pagec ;// iii_next = pagec เพื่อ pageถัดไป
                        if(!falgprev){
                            iii_rec_prev=pagec;
                            falgprev = true;
                        }
                        if(detailpage == pagec){
                            sbPage.append("<td width=\"1%\" align=\"center\" ><B><font color=\"#808080\"><U>"+pagec+"</U></font></B></td>\n");
                        }else{
                            sbPage.append("<td width=\"1%\" class=\"green\" align=\"center\" onclick = \"CurrentShowData('"+startrecord+"','"+pagec+"','"+ii_countpagegroup+"','"+iii_rec_prev+"')\" style=\"cursor:hand\"><b>"+pagec+"</b></td>\n");
                        }
                    }
                }
            }
            if(iii_startnext >= sManrecord){
                sbPage.append("<td width=\"3%\" align=\"right\"><font color=\"#808080\">ถัดไป</font></td>\n");
            }else{
                sbPage.append("<td width=\"3%\" class=\"green\" align=\"right\" onclick =\"CurrentShowData('"+(iii_startnext)+"','"+(iii_pagenext+1)+"','"+(iii_next + 1)+"','"+(iii_rec_prev + 5)+"')\" style=\"cursor:hand\"><b>ถัดไป</b></td>\n");
            }
        }
        sbPage.append("</tr>\n");
        sbPage.append("</table>\n");
        return sbPage.toString();
    }
    
    public static String printPageNoMaster(int perWidth,int sManrecord,int scountpage,String pageNo,String sgrouppage,String sprevpage,int startrecord,int endrecord,int record_Group) {
        StringBuffer sbPage = new StringBuffer("");
//        System.out.println("startrecord>>"+startrecord);
        sbPage.append("<table width='"+perWidth+"%' align='center' border='0' id='tableb3' cellspacing='0' cellpadding='0'>\n");
        sbPage.append("<tr class='thFooter'>\n");
        sbPage.append("<td width='40%' class=green align ='left'>&nbsp;&nbsp;จำนวนข้อมูลทั้งหมด&nbsp;<font class='blue'><b>"+sManrecord+"</b></font>&nbsp;รายการ/จำนวนหน้า&nbsp;<font class='blue'><b>"+scountpage+"</b></font>&nbsp;หน้า</td>\n");
        int ii_countpagegroup=1;
        int ii_chkcount=0;
        int iii_prev = 0;
        int iii_startprev = 0;
        int iii_pageprev = 0;
        int iii_next = 0;
        int iii_startnext = 0;
        int iii_pagenext = 0;
        int iii_rec_prev = 0;
        boolean falgprev = false;
        if ( sManrecord > 0) {//aTin_size != null &&
            int detailpage = 0;
            if(pageNo.equals("1")){
                detailpage = 1;
                iii_prev = 1;
                iii_next = 1;
            }else{
                detailpage = Integer.parseInt(pageNo);
                iii_prev = Integer.parseInt(sgrouppage);
                iii_next = Integer.parseInt(sgrouppage);
            }
            if(sgrouppage.equals("")){
                sgrouppage = "1";
            }
            iii_startnext = (iii_next * record_Group);// iii_next*record_Group เป็นค่าtotal เพื่อ pageถัดไป (50 record and 5 page)
            iii_startprev = ((iii_next - 2)* record_Group);// ((iii_next-2)*record_Group) เป็นค่าtotal เพื่อ pageย้อนกลับ (50 record and 5 page)
            if(!sprevpage.equals("")){
                iii_pageprev = Integer.parseInt(sprevpage) - 5;//เพื่อ pageย้อนกลับ
                if(iii_pageprev<=0){
                    iii_pageprev=1;
                }
            }else{
                iii_pageprev=1;
            }
            
            sbPage.append("<td width=\"30%\" class='green' align=\"right\" >เลือกดูหน้าที่&nbsp;</td>");
            sbPage.append("<td width=\"10%\" align=\"left\" ><INPUT type=\"text\" id=\"txtPage\" class=\"txtCommon\" maxlength=\"4\" size='4' value=\""+detailpage+"\"   onKeyPress=\"chkNumber1();\">&nbsp; <input type=\"button\" name=\"btOk\" value=\"ไป\" class=\"btnCommon\" style='background: url(\"../../images/btnBlue25.gif\"); width: 25px; height: 25px;'  onclick=\"Gopage("+scountpage+");\">&nbsp; </td>\n");
            
            if(iii_startprev<0){
                sbPage.append("<td width=\"6%\" align=\"left\" ><font color=\"#808080\">ย้อนกลับ</font></td>\n");
            }else{
                sbPage.append("<td width=\"6%\" class=\"green\" align=\"left\" onclick = \"CurrentShowData('"+iii_startprev+"','"+iii_pageprev+"','"+(iii_prev - 1)+"','"+iii_pageprev+"')\" style=\"cursor:hand\"><b>ย้อนกลับ<b></td>\n");
            }
            for(int pagec=1;pagec<=scountpage;pagec++){
                ii_chkcount++;
                if(ii_chkcount>5){// จำนวน page ที่โชว์
                    ii_countpagegroup++;// countgroup จำนวน page
                    ii_chkcount=1;
                }else{
                    if(pagec==scountpage){
                        if(ii_chkcount>5){// จำนวน page ที่โชว์
                            ii_countpagegroup++;
                        }
                    }
                }
                if(!sgrouppage.equals("")){
                    if(Integer.parseInt(sgrouppage) == ii_countpagegroup){//ถ้าเท่ากันโชว์ page นั้น
                        iii_pagenext = pagec ;// iii_next = pagec เพื่อ pageถัดไป
                        if(!falgprev){
                            iii_rec_prev=pagec;
                            falgprev = true;
                        }
                        if(detailpage == pagec){
                            sbPage.append("<td width=\"4%\" align=\"center\" ><B><font class=\"blue\"><U>"+pagec+"</U></font></B></td>\n");
                        }else{
                            sbPage.append("<td width=\"4%\" class=\"blue\" align=\"center\" onclick = \"CurrentShowData('"+startrecord+"','"+pagec+"','"+ii_countpagegroup+"','"+iii_rec_prev+"')\" style=\"cursor:hand\">"+pagec+"</td>\n");
                        }
                    }
                }
            }
            if(iii_startnext >= sManrecord){
                sbPage.append("<td width=\"6%\" align=\"right\"><font color=\"#808080\">ถัดไป</font></td>\n");
            }else{
                sbPage.append("<td width=\"6%\" class=\"green\" align=\"right\" onclick =\"CurrentShowData('"+(iii_startnext)+"','"+(iii_pagenext+1)+"','"+(iii_next + 1)+"','"+(iii_rec_prev + 5)+"')\" style=\"cursor:hand\"><b>ถัดไป</b></td>\n");
            }
        }
        sbPage.append("</tr>\n");
        sbPage.append("</table>\n");
        sbPage.append("<SCRIPT>\n");
        sbPage.append("function chGopage(pagen,mpage){\n");
        sbPage.append("var p=pagen;\n");
        sbPage.append("if(pagen>mpage || pagen==0){\n");
        sbPage.append("alert(\"กรุณาเลือกเลขหน้าใหม่\");\n");
        sbPage.append(" document.getElementById(\"txtPage\").select(); \n");
        sbPage.append("}else{alert("+startrecord+"); \n");
        sbPage.append(" CurrentShowData('"+startrecord+"',pagen,'','') \n");
        sbPage.append(" }\n");
        sbPage.append("}\n");
        
        sbPage.append("function chkNumber1(){\n");
        sbPage.append("e_k=event.keyCode\n");
        sbPage.append("if  (!((e_k >= 48) &&(e_k <= 57))) {\n");
        sbPage.append("event.returnValue = false;\n");
        sbPage.append("}\n");
        sbPage.append("}\n");
        sbPage.append("function Gopage(mp){\n");
        sbPage.append("var page=document.getElementById(\"txtPage\").value; \n");
        sbPage.append(" chGopage(page,mp);\n");
        sbPage.append("}\n");
        sbPage.append("</SCRIPT>\n");
        return sbPage.toString();
    }
        /*
        public static String getErrorDesc(String propertiesName,String proprotiesElement){
            String errorDesc = "";
            Connection conn = null;
            Statement stm = null;
            ResultSet rs = null;
            String sql = "";
            try{
            sql = "select description " +
                    "from "+PccProperties.getResourceString("SCHEMA_LCS")+".properties " +
                    "where properties_name = '"+propertiesName+"' " +
                    "and properties_element = '"+proprotiesElement+"'";
         
            conn = DBUtil.getConnection(false);
            stm = conn.createStatement();
            rs = stm.executeQuery(sql);
            if(rs.next()){
                errorDesc = rs.getString("description");
            }
            }catch(Exception ex){
                System.out.println("Exception in getErrorDesc in Tools.java ::: "+ex.toString());
            }
            return errorDesc;
        }
         */
    public static ArrayList mySplit(String msg,String split){
        int chk=0;
        int start = 0;
        ArrayList result = new ArrayList();
        while(chk!=-1){
            chk=msg.indexOf(split,start);
            if(chk>-1){
                result.add(msg.substring(start,chk));
                start = chk+1;
            }else{ // the last data
                result.add(msg.substring(start));
            }
        }
        
        return result;
    }
    
    /**
     * Method numToStr
     * param1 str เช่น "100000.00" หรือ 100000
     * return  "100,000.00"
     * By pom
     */
    public static String strToNumFormatHavePoint(String str){
        String result = "";
        String point = "";
        if(!str.equals("")){
            int pos = str.indexOf(".");
            if(pos > -1){
                point = str.substring(pos);
                str = str.substring(0,pos);
            }else{
                point = ".00";
            }
            for(int i = str.length()-1;i >= 0 ;i--){
                result = str.charAt(i)+result;
                if(i%3==0 && i > 0)
                    result = ","+result;
            }
            result += point;
        }else{
            result = "0.00";
        }
        return result;
    }
    
    /**
     * This method is to convert the price into Indian price
     * separator format
     * @param inputPrice
     * @return
     */
    public static String priceFormatter(String inputPrice){
        try {
        	
            // to check if the number is a decimal number
            String newPrice = "",afterDecimal = "";
            if(inputPrice.indexOf('.') != -1){
                newPrice = inputPrice.substring(0,inputPrice.lastIndexOf('.'));
                afterDecimal = inputPrice.substring(inputPrice.lastIndexOf('.'));
            }else{
                newPrice = inputPrice;
            }
            int length = newPrice.length();
            if (length < 4) {
                return inputPrice;
            }
            // to check whether the length of the number is even or odd
            boolean isEven = false;
            if (length % 2 == 0) {
                isEven = true;
            }
            // to calculate the comma index
            char ch[] = new char[length + (length / 2 - 1)];
            if (isEven) {
                for (int i = 0, j = 0; i < length; i++) {
                    ch[j++] = inputPrice.charAt(i);
                    if (i % 2 == 0 && i < (length - 3)) {
                        ch[j++] = ',';
                    }
                }
            } else {
                for (int i = 0, j = 0; i < length; i++) {
                    ch[j++] = inputPrice.charAt(i);
                    if (i % 2 == 1 && i < (length - 3)) {
                        ch[j++] = ',';
                    }
                }
            }
            // conditional return based on decimal number check
            return afterDecimal.length() > 0 ? String.valueOf(ch) + afterDecimal : String.valueOf(ch);

        } catch(NumberFormatException ex){
            ex.printStackTrace();
            return inputPrice;
        }
          catch (Exception e) {
            e.printStackTrace();
            return inputPrice;
        }
    }
    
    /**
     * Method numToStr
     * param1 str เช่น "100000" หรือ "100000.00"
     * return  "100,000"
     * By pom
     */
    public static String strToNumFormatNotHavePoint(String str){
        String result = "";
        if(!str.equals("")){
            int point = str.indexOf(".");
            if(point > -1)
                str = str.substring(0,point);
            for(int i = str.length()-1;i >= 0 ;i--){
                result = str.charAt(i)+result;
                if(i%3==0 && i > 0)
                    result = ","+result;
            }
        }else{
            result = "0";
        }
        return result;
    }
    
    /**
     * Method numToStr
     * param1 str เช่น "100,000.00" หรือ 100,000
     * return  "100000"
     * By pom
     */
    public static String numToStr(String str){
        if(!str.equals("")){
            str = str.replaceAll(",","");
        }
        return str;
    }
    
    public static Vector allIndexOf(String str, String chr) {
        Vector output = new Vector();
        String temp = "";
        for (int i = 0; i < str.length(); i++) {
            temp = "" + str.charAt(i);
            if (temp.compareTo(chr) == 0) {
                output.add(Integer.toString(i));
            }
        }
        return output;
    }
    
    public static String[] parseIntoArray(String[] output, String str, String chr) {
        Vector v = allIndexOf(str, chr);
        output = new String[v.size() + 1];
        if (v.size() == str.length()) {
            for (int i = 0; i < output.length; i++) {
                output[i] = "";
            }
        }else {
            int index = 0;
            String temp1 = "";
            String temp2 = "";
            
            for (int i = 0; i < v.size(); i++) {
                temp1 = (String)v.elementAt(i);
                if (i == 0) {
                    if (Integer.parseInt(temp1) == 0) {
                        output[index] = "";
                    }else {
                        output[index] = str.substring(0, Integer.parseInt(temp1));
                    }
                    
                    index++;
                }
                
                if (i + 1 < v.size()) {
                    temp2 = (String)v.elementAt(i + 1);
                    output[index] = str.substring(Integer.parseInt(temp1) + 1, Integer.parseInt(temp2));
                }else {
                    output[index] = str.substring(Integer.parseInt(temp1) + 1);
                }
                
                index++;
            }
            
            if (v.size() == 0 && str.length() > 0) {
                output[0] = str;
            }
        }
        
        return output;
    }
    
    public static String cutComma(String sValueIn) {
        String sValueOut = "";
        int i = sValueIn.indexOf(',');
        while (i > 0) {
            sValueOut += sValueIn.substring(0,i);
            sValueIn = sValueIn.substring(++i);
            i = sValueIn.indexOf(',');
        }
        return sValueOut + sValueIn;
    }
    
    public static String ctlnoFormat(String ctlno){
        
        String sZero = "";
        if(ctlno==null || ctlno.equals("")) return("Empty");
        int dValue = Integer.parseInt(ctlno);
        if(dValue <10)
            sZero = "0000";
        else if(dValue < 100)
            sZero = "000";
        else if(dValue < 1000)
            sZero = "00";
        else if(dValue < 10000)
            sZero = "0";
        return (sZero+ctlno);
    }
    
    /**
     * Send e-mail
     * @param to Recipient
     * @param subject Subject
     * @param body Content
     */
//    public static void sendMail(String to, String subject, String body) {
//        String smtpServer = null;
//        String from = null;
//        String cc = null;
//        String toSuffix = null;
//        String footer = null;
//        
//        try {
//            /*
//             * Get the SMTP Server and Sender Address
//             */
//            smtpServer = PccProperties.getResourceString("SMTPServer");
//            from = PccProperties.getResourceString("SendAddress");
//            cc = PccProperties.getResourceString("CcAddress");
//            toSuffix = PccProperties.getResourceString("ToSuffix");
//            if (toSuffix == null)
//                toSuffix = "@ed.go.th";
//            footer = "";
//            
//            // Get system properties
//            Properties props = System.getProperties();
//            
//            // -- Attaching to default Session, or we could start a new one --
//            // -- Could use Session.getTransport() and Transport.connect()
//            // , but assume we're using SMTP --
//            
//            // -- Setup mail server --
//            props.put("mail.smtp.host", smtpServer);
//            Session session = Session.getDefaultInstance(props, null);
//            
//            // -- Create a new message --
//            MimeMessage msg = new MimeMessage(session);
//            
//            // -- Set the FROM and TO fields --
//            msg.setFrom(new InternetAddress(from));
//            msg
//                    .setRecipients(Message.RecipientType.TO, InternetAddress.parse(to + toSuffix, false));
//            
//            // -- We could include CC recipients too --
//            if (cc != null && cc.compareTo("") != 0)
//                msg.setRecipients(Message.RecipientType.CC, InternetAddress.parse(cc, false));
//            
//            // -- Set the subject and body text --
//            msg.setSubject(subject);
//            
//            // -- Fill message --
//            msg.setText(body + footer);
//            
//            // -- Set some other header information --
//            msg.setSentDate(new Date());
//            
//            // -- Send the message --
//            Transport.send(msg);
//        } catch (Exception ex) {
//            System.out.println("*****************************************");
//            System.out.println(ex);
//            ex.printStackTrace();
//            System.out.println("*****************************************");
//        }
//    }
    
    /**
     * @param to
     * @param subject
     * @param body
     * @param fileAttachment
     */
//    public static void sendMail(String to, String subject, String body,
//            String fileAttachment) {
//        String smtpServer = null;
//        String from = null;
//        String cc = null;
//        String toSuffix = null;
//        String footer = null;
//        
//        try {
//            /*
//             * Get the SMTP Server and Sender Address
//             */
//            smtpServer = PccProperties.getResourceString("SMTPServer");
//            from = PccProperties.getResourceString("SendAddress");
//            cc = PccProperties.getResourceString("CcAddress");
//            toSuffix = PccProperties.getResourceString("ToSuffix");
//            if (toSuffix == null)
//                toSuffix = "@ed.go.th";
//            footer = "";
//            
//            // Get system properties
//            Properties props = System.getProperties();
//            
//            // -- Attaching to default Session, or we could start a new one --
//            // -- Could use Session.getTransport() and Transport.connect()
//            // , but assume we're using SMTP --
//            
//            // -- Setup mail server --
//            props.put("mail.smtp.host", smtpServer);
//            Session session = Session.getDefaultInstance(props, null);
//            
//            // -- Create a new message --
//            MimeMessage msg = new MimeMessage(session);
//            
//            // -- Set the FROM and TO fields --
//            msg.setFrom(new InternetAddress(from));
//            msg
//                    .setRecipients(Message.RecipientType.TO, InternetAddress.parse(to + toSuffix, false));
//            
//            // -- We could include CC recipients too --
//            if (cc != null && cc.compareTo("") != 0)
//                msg.setRecipients(Message.RecipientType.CC, InternetAddress.parse(cc, false));
//            
//            // -- Set the subject and body text --
//            msg.setSubject(subject);
//            
//            // -- Create the message part --
//            MimeBodyPart messageBodyPart = new MimeBodyPart();
//            
//            // -- Fill message --
//            messageBodyPart.setText(body + footer);
//            
//            Multipart multipart = new MimeMultipart();
//            multipart.addBodyPart(messageBodyPart);
//            
//            // -- Part two is attachment --
//            messageBodyPart = new MimeBodyPart();
//            DataSource source = new FileDataSource(fileAttachment);
//            messageBodyPart.setDataHandler(new DataHandler(source));
//            messageBodyPart.setFileName(fileAttachment);
//            multipart.addBodyPart(messageBodyPart);
//            
//            // -- Put parts in message --
//            msg.setContent(multipart);
//            
//            // -- Set some other header information --
//            msg.setSentDate(new Date());
//            
//            // -- Send the message --
//            Transport.send(msg);
//        } catch (Exception ex) {
//            System.out.println("*****************************************");
//            System.out.println(ex);
//            ex.printStackTrace();
//            System.out.println("*****************************************");
//        }
//    }
    
    public static String barcodeNormal(String contextPath, String value) {
        StringBuffer sbBarcode = new StringBuffer("");
        try {
            if (contextPath.length() > 0 && value.length() > 0) {
                sbBarcode.append("<div align='center'>\n");
                sbBarcode.append("  <img src='"+contextPath+"/servlet/LinearServlet?FORMAT=GIF&BARCODE="+value+"&BAR_HEIGHT=1'/>\n");
                //sbBarcode.append("  <img src='"+contextPath+"/servlet/com.idautomation.linear.IDAutomationServlet?FORMAT=GIF&BARCODE="+value+"&BAR_HEIGHT=1'/>\n");
                sbBarcode.append("</div>\n");
            }else if(value.length() > 0){
                sbBarcode.append("<div align='center'>\n");
                sbBarcode.append("  <img src='/servlet/LinearServlet?FORMAT=GIF&BARCODE="+value+"&BAR_HEIGHT=1'/>\n");
                sbBarcode.append("</div>\n");
            }
        } catch (Exception e) {
            System.out.println("Exception Error : " + e);
        }
        return sbBarcode.toString();
    }
    public static String barcodeNormalccS(String contextPath, String value) {
        StringBuffer sbBarcode = new StringBuffer("");
        try {
            if (contextPath.length() > 0 && value.length() > 0) {
                sbBarcode.append("<div align='left'>\n");
            sbBarcode.append("  <img src='"+contextPath+"/servlet/LinearServlet?FORMAT=GIF&BARCODE="+value+"&BAR_HEIGHT=1' align='left'/>\n");
                //sbBarcode.append("  <img src='"+contextPath+"/servlet/com.idautomation.linear.IDAutomationServlet?FORMAT=GIF&BARCODE="+value+"&BAR_HEIGHT=1'/>\n");
                sbBarcode.append("</div>\n");
            }else if(value.length() > 0){
                sbBarcode.append("<div align='left'>\n");
                sbBarcode.append("  <img src='/servlet/LinearServlet?FORMAT=GIF&BARCODE="+value+"&BAR_HEIGHT=1' align='left'/>\n");
                sbBarcode.append("</div>\n");
            }
        } catch (Exception e) {
            System.out.println("Exception Error : " + e);
        }
        return sbBarcode.toString();
    }
        /* ใช้นับตัวอักษรภาษาไทย
         *
         *
         */
    public static int getCount(String str) {
        char[] key = new char[]{'\u0E31', '\u0E34', '\u0E35', '\u0E36', '\u0E37', '\u0E38', '\u0E39', '\u0E3A', '\u0E3B', '\u0E3C', '\u0E3D', '\u0E3E',
        '\u0E47', '\u0E48', '\u0E49', '\u0E4A', '\u0E4B', '\u0E4C', '\u0E4D', '\u0E4E',};
        int charCount = 0;
        for(int i = 0; i < str.length(); i++){
            for(int j = 0; j < key.length; j++){
                if(str.charAt(i) == key[j]){
                    charCount++;
                }
            }
        }
        
        return str.length() - charCount;
    }
    
    
    public static String printPageNo(String i_RowCount,String i_Show,String i_Position,String iMaxPage) {
        //แสดงเลขหน้า
        int iPrevious=0;
        int iNext =0;
        boolean falgprev = false;
        StringBuffer sbPage = new StringBuffer("");
        if (Integer.parseInt("0"+i_RowCount) > Integer.parseInt("0"+i_Show)){
            sbPage.append("<table border=\"0\" width=\"100%\" align='center'>");
            sbPage.append("<tr><td><b> หน้า");
            
            int endPage = 0;
            if(Integer.parseInt("0"+i_RowCount)%Integer.parseInt("0"+i_Show) == 0)
                endPage = (Integer.parseInt("0"+i_RowCount) / Integer.parseInt("0"+i_Show));
            else
                endPage = (Integer.parseInt("0"+i_RowCount) / Integer.parseInt("0"+i_Show))+1;
            
            int sPage = 0, iTmp=Integer.parseInt("0"+i_Position)/Integer.parseInt("0"+iMaxPage);
            if (Integer.parseInt("0"+i_Position)%Integer.parseInt("0"+iMaxPage) == 0)
                iTmp--;
            iTmp=(iTmp*Integer.parseInt("0"+iMaxPage)) + 1;
            if (Integer.parseInt("0"+i_Position) > Integer.parseInt("0"+iMaxPage)) {
                sbPage.append("<a href=\"javascript:submitForm("+(iTmp-Integer.parseInt("0"+iMaxPage))+")\">&lt;&lt;</a>&nbsp;");
            }
            for(; iTmp<= endPage && sPage < Integer.parseInt("0"+iMaxPage); iTmp++){
                if(iTmp==Integer.parseInt("0"+i_Position)){
                    sbPage.append("<b>"+ iTmp + "&nbsp;</b>");
                }else{
                    iNext = iTmp;
                    if (iNext <= Integer.parseInt("0"+i_RowCount)){
                        sbPage.append("<a href=\"javascript:submitForm("+iTmp+")\">"+iTmp+"</a>&nbsp;");
                    }
                }
                sPage++;
            }
            if (iTmp <= endPage){
                sbPage.append("<a href=\"javascript:submitForm("+iTmp+")\">&gt;&gt;</a>&nbsp;");
            }
            sbPage.append("</td></tr>");
            sbPage.append("</table>");
        } else {
            sbPage.append("<br>");
        }
        return sbPage.toString();
    }
    
    // use for : find begin date of month and find last date of month by parameter
    // @param strDate : format date month year ("dd/mm/yyyy" is พ.ศ.) or ("yyyy-mm-dd" is ค.ศ.)
    // @return : format send back "dd/mm/yyyy" โดย yyyy เป็นปี พ.ศ.
    // @create_date : 25/04/2551
    public static String [] getBegin_EndDateOfMonth(String strDate) throws Exception {
        if (strDate == null || strDate.equals("") || strDate.length() != 10)
            throw new Exception("<Tools : getBeginDateOfMonth> The Parameter has been passing into method getBeginDateOfMonth invalid value can't be working, Please check parameter again");
        int month = 0, year = 0;
        String strDay = "";
        String [] rtnDate = new String[2];
        try {
            // ----------------------------------------------------------------------------------------------------------------------
            if (strDate.indexOf("/") != 0) // if strDate come is format dd/mm/yyyy
            {
                month = Integer.parseInt(strDate.substring(3, 5));
                year = Integer.parseInt(strDate.substring(6));
                year = (year - 543);
            }
            // ----------------------------------------------------------------------------------------------------------------------
            else if (strDate.indexOf("-") != 0) // if strDate come is format yyyy-mm-dd
            {
                month = Integer.parseInt(strDate.substring(5, 7));
                year = Integer.parseInt(strDate.substring(0, 4));
            }
            // ----------------------------------------------------------------------------------------------------------------------
            java.util.Calendar calendar = new GregorianCalendar(year, (month - 1), 1);
            int firstDayOfMonth = calendar.getActualMinimum(calendar.DATE);
            int endDayOfMonth = calendar.getActualMaximum(calendar.DATE);
            
            year = (year + 543); // เพราะตอนส่งค่ากลับ รูปแบบจะเป็นปี พ.ศ. "dd/mm/yyyy"
            strDay = (firstDayOfMonth < 10)?("0" + firstDayOfMonth):(firstDayOfMonth + "");
            rtnDate[0] = (strDay + "/" + ((month >= 10)?(month + ""):("0" + month)) + "/" + year); // ต้นเดือน
            
            strDay = (endDayOfMonth < 10)?("0" + endDayOfMonth):(endDayOfMonth + "");
            rtnDate[1] = (strDay + "/" + ((month >= 10)?(month + ""):("0" + month)) + "/" + year); // สิ้นเดือน
            // ----------------------------------------------------------------------------------------------------------------------
        } catch (Exception ex) {
            throw ex;
        }
        return rtnDate;
    }
    
    public static String [] getBegin_EndDateOfMonthAD(String strDate) throws Exception {
        if (strDate == null || strDate.equals("") || strDate.length() != 10)
            throw new Exception("<Tools : getBeginDateOfMonth> The Parameter has been passing into method getBeginDateOfMonth invalid value can't be working, Please check parameter again");
        int month = 0, year = 0;
        String strDay = "";
        String [] rtnDate = new String[2];
        try {
            // ----------------------------------------------------------------------------------------------------------------------
            if (strDate.indexOf("/") != 0) // if strDate come is format dd/mm/yyyy
            {
                month = Integer.parseInt(strDate.substring(3, 5));
                year = Integer.parseInt(strDate.substring(6));
            }
            // ----------------------------------------------------------------------------------------------------------------------
            else if (strDate.indexOf("-") != 0) // if strDate come is format yyyy-mm-dd
            {
                month = Integer.parseInt(strDate.substring(5, 7));
                year = Integer.parseInt(strDate.substring(0, 4));
            }
            // ----------------------------------------------------------------------------------------------------------------------
            java.util.Calendar calendar = new GregorianCalendar(year, (month - 1), 1);
            int firstDayOfMonth = calendar.getActualMinimum(calendar.DATE);
            int endDayOfMonth = calendar.getActualMaximum(calendar.DATE);
            
            strDay = (firstDayOfMonth < 10)?("0" + firstDayOfMonth):(firstDayOfMonth + "");
            rtnDate[0] = (strDay + "/" + ((month >= 10)?(month + ""):("0" + month)) + "/" + year); // ต้นเดือน
            
            strDay = (endDayOfMonth < 10)?("0" + endDayOfMonth):(endDayOfMonth + "");
            rtnDate[1] = (strDay + "/" + ((month >= 10)?(month + ""):("0" + month)) + "/" + year); // สิ้นเดือน
            // ----------------------------------------------------------------------------------------------------------------------
        } catch (Exception ex) {
            throw ex;
        }
        return rtnDate;
    }
    
    public static String getMonthName(String monthCode) {
        String monthName = "";
        if ((monthCode.equals("1"))||(monthCode.equals("01"))) monthName = "มกราคม";
        else if ((monthCode.equals("2"))||(monthCode.equals("02"))) monthName = "กุมภาพันธ์";
        else if ((monthCode.equals("3"))||(monthCode.equals("03"))) monthName = "มีนาคม";
        else if ((monthCode.equals("4"))||(monthCode.equals("04"))) monthName = "เมษายน";
        else if ((monthCode.equals("5"))||(monthCode.equals("05"))) monthName = "พฤษภาคม";
        else if ((monthCode.equals("6"))||(monthCode.equals("06"))) monthName = "มิถุนายน";
        else if ((monthCode.equals("7"))||(monthCode.equals("07"))) monthName = "กรกฏาคม";
        else if ((monthCode.equals("8"))||(monthCode.equals("08"))) monthName = "สิงหาคม";
        else if ((monthCode.equals("9"))||(monthCode.equals("09"))) monthName = "กันยายน";
        else if (monthCode.equals("10")) monthName = "ตุลาคม";
        else if (monthCode.equals("11")) monthName = "พฤศจิกายน";
        else if (monthCode.equals("12")) monthName = "ธันวาคม";
        return monthName;
    }
    
    public static String getActiveName(String activeFlag) {
        String rtn = "";
        if(Tools.chkNull(activeFlag).compareTo("Y")==0)
            rtn = "ใช้งาน";
        else
            rtn = "ไม่ใช้งาน";
        
        return rtn;
    }
    
    public static String getFlagName(String activeFlag) {
        String rtn = "";
        if(Tools.chkNull(activeFlag).compareTo("Y")==0)
            rtn = "ใช่";
        else
            rtn = "ไม่ใช่";
        
        return rtn;
    }
    
    public static String getTranFlagName(String t) {
        String rtn = "";
        
        if(t!=null){
        	if(t.compareTo("C")==0) rtn= "C (Credit)";
        	else if (t.compareTo("N")==0) rtn= "N (...)";
        	else if (t.compareTo("D")==0) rtn= "D (Debit)";
        }
        
        return rtn;
    }
    
    public static String getNBFFlagName(String t) {
        String rtn = "";

        if(t!=null){
        	if(t.compareTo("R")==0) rtn= "R (Revert)";
        	else if (t.compareTo("E")==0) rtn= "E (EC)";
        	else if (t.compareTo("C")==0) rtn= "C (Revert - EC)";
        	else rtn= "ค่าว่าง (Normal)";
        }
        
        return rtn;
    }
    
    public static String getTranTypeName(String t) {
        String rtn = "";

        if(t!=null){
        	if(t.compareTo("1")==0) rtn= "รับชำระ";
        	else if (t.compareTo("2")==0) rtn= "ปรับปรุง";
        }
        
        return rtn;
    }
    
    public static String getTranTypePrintCodeName(String t) {
        String rtn = "";

        if(t!=null){
        	if(t.compareTo("1")==0) rtn= "รับชำระ";
        	else if (t.compareTo("2")==0) rtn= "ปรับปรุงบัญชี";
        }
        
        return rtn;
    }
    
    public static String getTranTypeDesc(String t) {
        String rtn = "";

        if(t!=null){
        	if(t.compareTo("1")==0) rtn= "โอนเงิน";
        	else if (t.compareTo("2")==0) rtn= "รับชำระ";
        	else if (t.compareTo("3")==0) rtn= "คืนเงิน";
        	else if (t.compareTo("4")==0) rtn= "ปรับปรุงบัญชี";
        }
        
        return rtn;
    }
    
    public static String getTranAmountFlagName(String t) {
        String rtn = "";

        if(t!=null){
        	if(t.compareTo("Dr")==0) rtn= "เพิ่มหนี้";
        	else if (t.compareTo("Cr")==0) rtn= "ลดหนี้";
        }
        
        return rtn;
    }
    
    public static String getTranAmountTypeName(String t) {
        String rtn = "";

        if(t!=null){
        	if(t.compareTo("P")==0) rtn= "เงินต้น";
        	else if (t.compareTo("I")==0) rtn= "ดอกเบี้ย";
        	else if (t.compareTo("L")==0) rtn= "เบี้ยปรับ";
        }
        
        return rtn;
    }
    
    /**
     * Create By : Paungporn Jongjitson
     * Create Date : 16/09/2551
     * หา Description ของ StatusPayName
     * @return String รหัสสถานะ
     */
    public static String getStatusPayName(String value){
       String status_pay = "";
        if( value.compareTo("00")==0)
            status_pay = "รอโอน";
        else if(value.compareTo("0")==0)
            status_pay = "โอนแล้ว";
        else if(value.compareTo("1")==0 || value.compareTo("2")==0 || value.compareTo("3")==0 || value.compareTo("7")==0)
            status_pay = "โอนไม่สำเร็จ";
        else if (value.compareTo("9")==0)
            status_pay = "สร้างไฟล์แล้ว";
        else
            status_pay = "ไม่สามารถระบุสถานะการโอนได้";
        
        return status_pay;
    }
    
    /**
     * Create By : Weerapat Phaphromma
     * Create Date : 08/12/2559
     * หา Description ของ StatusPayName ตั้งแต่ปี พ.ศ. 2559 เป็นต้นไป
     * @return String รหัสสถานะ
     */
    public static String getStatusPayNamefor59Up(String value){
        String status_pay = "";
        if( value.compareTo("00")==0)
            status_pay = "รอโอน";
        else if(value.compareTo("0")==0)
            status_pay = "โอนสำเร็จ";
        else if(value.compareTo("1")==0 || value.compareTo("2")==0 || value.compareTo("3")==0 || value.compareTo("7")==0)
            status_pay = "โอนไม่สำเร็จ";
        else if (value.compareTo("9")==0)
            status_pay = "สร้างไฟล์แล้ว";
        else
            status_pay = "ไม่สามารถระบุสถานะการโอนได้";
        
        return status_pay;
    }

    /**
    * Create By : Weerapat Phaphromma
    * Create Date : 21/11/2559
    * หา Description ของ Status_pay เพื่อใช้ใสำหรับในหน้าประวัติเท่านั้น
    * @return String รหัสสถานะ
    */
   public static String getStatusPayNameforHis(String value){
       String status_pay = "";
       if( value.compareTo("00")==0)
           status_pay = "รอโอน";
       else if(value.compareTo("0")==0)
           status_pay = "โอนสำเร็จ";
       else if(value.compareTo("1")==0)
           status_pay = "โอนไม่สำเร็จ (เงินไม่พอจ่าย)";
       else if (value.compareTo("2")==0)
           status_pay = "โอนไม่สำเร็จ (บัญชีปิด)";
        else if (value.compareTo("3")==0)
           status_pay = "โอนไม่สำเร็จ (บัญชีถูกระงับ)";
       else if (value.compareTo("7")==0)
           status_pay = "โอนไม่สำเร็จ (Partially Complete)";
       else if (value.compareTo("9")==0)
           status_pay = "สร้างไฟล์แล้ว";
       else
           status_pay = "ไม่สามารถระบุสถานะการโอนได้";
       
       return status_pay;
    }
   
    /**
     * Create By : Paungporn Jongjitson
     * Create Date : 16/09/2551
     * หา Description ของ Status Hold
     * @return String รหัสสถานะ
     */
    public static String getStatusHold(String value){
        String data = "";
        if(value.compareTo("00")==0){
            data = "ปกติ";
        }else if(value.compareTo("10")==0){
            data = "ระงับระดับสถานศึกษา";
        }else if (value.compareTo("20")==0){
            data = "ระงับระดับรายตัว";
        }else if (value.compareTo("30")==0){
            data = "ระงับระดับรายการ";
        }else if (value.compareTo("31")==0){
            data = "ระงับรายการที่มาจากการยกเลิกแบบยืนยันฯ";
        }else if (value.compareTo("32")==0){
            data = "ระงับรายการที่มาจากการยกเลิกสัญญา";
        }else{
            data = "";
        }
        return data;
    }
    
    /**
     * Create By : Paungporn Jongjitson
     * Create Date : 16/09/2551
     * หา Description ของ StatusRemark
     * @return String รหัสสถานะ
     */
    public static String getStatusRemark(String value){
        String statusRemark = "";
        if(value.compareTo("1")==0)
            statusRemark = "Insufficient Fund";
        else if(value.compareTo("2")==0)
            statusRemark = "Account Closed";
        else if(value.compareTo("3")==0)
            statusRemark = "Account Blocked";
        else if(value.compareTo("7")==0)
            statusRemark = "Partially Complete";
        else if( value.compareTo("0")==0)
            statusRemark = "จ่ายเงินแล้ว";
        else
            statusRemark = "";
        
        return statusRemark;
    }
    
    /**
     * Create By : Weerapat Phaphromma
     * Create Date : 8/12/2559
     * หา Description ของ StatusRemark ต้งแต่ปี พ.ศ. 2559 เป็นต้นไป
     * @return String รหัสสถานะ
     */
    public static String getStatusRemarkfor59Up(String value){
        String statusRemark = "";
        if(value.compareTo("1")==0)
            statusRemark = "เงินไม่พอจ่าย";
        else if(value.compareTo("2")==0)
            statusRemark = "บัญชีปิด";
        else if(value.compareTo("3")==0)
            statusRemark = "บัญชีถูกระงับ";
        else if(value.compareTo("7")==0)
            statusRemark = "Partially Complete";
        else if( value.compareTo("0")==0)
            statusRemark = "จ่ายเงินแล้ว";
        else
            statusRemark = "";
        
        return statusRemark;
    }
    
    /**
     * Create By : Paungporn Jongjitson
     * Create Date : 16/09/2551
     * หา Description ของ RegisterStatus
     * @return String รหัสสถานะ
     */
    public static String getRegisterStatus(String value){
        String registerStatus = "";
        if(value.equals("0")){
            registerStatus = "INACTIVE";
        }else if(value.equals("1")){
            registerStatus = "ACTIVE";
        }else if(value.equals("P")){
            registerStatus = "รออนุมัติยกเลิก";
        }else{
            registerStatus = "";
        }
        return registerStatus;
    }
    /**
     * Create By : Anuwan Lapchaiyong
     * Create Date : 23/12/2551
     * หา Sex ของ Person
     * @return String เพศ
     */
    public static String getSex(String value){
        String sex = "";
        if(value.equals("F")){
            sex = "หญิง";
        }else if(value.equals("M")){
            sex = "ชาย";
        }else{
            sex = "";
        }
        return sex;
    }
    
    /**
     * Create By : Supinya Bovornseripatai
     * Create Date : 08/01/2552
     * หา ApproveFlagName ของ ApproveFlag
     * @return String รหัสสถานะ
     */
    public static String getApproveFlagName(String approveFlag){
        String approveFlagName = "";
        if(("I").equals(approveFlag)){
            approveFlagName = "รอส่งอนุมัติ";
        }else if(("W").equals(approveFlag)){
            approveFlagName = "รออนุมัติ";
        }else if(("Y").equals(approveFlag)){
            approveFlagName = "อนุมัติ";
        }else if(("N").equals(approveFlag)){
            approveFlagName = "ไม่อนุมัติ";
        }else if(("C").equals(approveFlag)){
            approveFlagName = "ไม่ส่งอนุมัติ";
        }else{
            approveFlagName = "";
        }
        return approveFlagName;
    }
    
    /**
     * Create By : Anuwan Lapchaiyong
     * Create Date : 9/01/2552
     * หา BankTypeName ของ BankType
     * @return String -
     */
    public static String getBankTypeName(String value){
        String bankTypeName = "";
        if(value.equals("1")){
            bankTypeName = "กระแสรายวัน";
        }else if(value.equals("2")){
            bankTypeName = "ออมทรัพย์";
        }else{
            bankTypeName = "";
        }
        return bankTypeName;
    }
    
    /**
     * Create By : Supinya Bovornseripatai
     * Create Date : 09/01/2552
     * หา loanAccStatusName ของ loanAccStatus
     * @return String รหัสสถานะ
     */
    public static String getLoanAccStatusName(String loanAccStatus) {
        String loanAccStatusName = "";
        if(loanAccStatus.equals("00")) {
            loanAccStatusName = "ปกติ";
        } 
        else if(loanAccStatus.equals("01")) {
            loanAccStatusName = "บัญชีใหม่";
        }
        else if(loanAccStatus.equals("02")) {
            loanAccStatusName = "บัญชีใหม่ (ไม่ส่งอนุมัติ)";
        }
        else if(loanAccStatus.equals("90")) {
            loanAccStatusName = "ปิดบัญชี (Pay Off)";
        }
        else if(loanAccStatus.equals("91")) {
            loanAccStatusName = "ปิดบัญชี (รวมบัญชี)";
        }
        else if(loanAccStatus.equals("92")) {
            loanAccStatusName = "ปิดบัญชี (ไม่มียอดหนี้ค้างชำระ)";
        }
        else if(loanAccStatus.equals("93")) {
            loanAccStatusName = "ปิดบัญชี (มียอดหนี้ค้างชำระ)";
        }
        else if(loanAccStatus.equals("94")) {
//            loanAccStatusName = "ปิดบัญชี (โอนหนี้)";
            loanAccStatusName = "ปิดบัญชีโอนหนี้ (รวมหนี้)";
        }
        else if(loanAccStatus.equals("95")) {
            loanAccStatusName = "ปิดบัญชี (กรณีเสียชีวิต)";
        }
        
        return loanAccStatusName;
    }
    
    /**
     * Create By : Anuwan Lapchaiyong
     * Create Date : 12/01/2552
     * หา EduTypeName ของ EduType
     * @return String -
     */
    public static String getEduType(String value){
        String eduTypeName = "";
        if(value.equals("S")){
            eduTypeName = "โอนเงินเป็นรายตัว";
        }else if(value.equals("U")){
            eduTypeName = "โอนเงินรายสถานศึกษา";
        }else{
            eduTypeName = "";
        }
        return eduTypeName;
    }
    
    /**
     * Create By : Anuwan Lapchaiyong
     * Create Date : 12/01/2552
     * หา LoanStatusName ของ LoanStatus
     * @return String -
     */
    public static String getLoanStatus(String value){
        String loanStatusName = "";
        if(value.equals("Y")){
            loanStatusName = "ตั้งหนี้แล้ว";
        }else if(value.equals("N")){
            loanStatusName = "ยังไม่ได้ตั้งหนี้";
        }else{
            loanStatusName = "ยังไม่ได้ดำเนินการ";
        }
        return loanStatusName;
    }
    
     /**
     * Create By : Paungporn
     * Create Date : 31/03/2552
     * หา TRAN_FLAG 
     * @return String -
     */
    public static String getTranFlag(String value){
        String tranFlagName = "";
        if(value.equals("N")){
            tranFlagName = "Normal Trans";
        }else if(value.equals("E")){
            tranFlagName = "EC Trans";
        }else if(value.equals("R")){
            tranFlagName = "Reverse Trans";
        }else if(value.equals("C")){
            tranFlagName = "EC Reverse Trans";
        }
        return tranFlagName;
    }
    
    /**
     * Create By : Paungporn
     * Create Date : 31/03/2552
     * หา TRAN_FLAG 
     * @return String -
     */
    public static String getTranType(String value){
        String tranTypeName = "";
        if(value.equals("1")){
            tranTypeName = "Disburse";
        }else if(value.equals("2")){
            tranTypeName = "Repay";
        }else if(value.equals("3")){
            tranTypeName = "Return";
        }else if(value.equals("4")){
            tranTypeName = "Adjust";
        }
        return tranTypeName;
    }
    
    
    /**
     * Create By : Prawith Thowphant
     * Create Date : 18/05/2552
     * ใช้สำหรับปัดเศษทศนิยมของตัวแปร double เพื่อแก้ไขปัญหา bug ของ double
     * @param value ค่าที่ต้องการปัดเศษ สามารถน้อยกว่าศูนย์ได้
     * @param decimalPlace จำนวนหลักของทศนิยม
     * @return double ค่าที่มีการปัดเศษแล้ว
     */    
    public static double roundUpDouble(double value, int decimalPlace) {
        double returnValue = 0;
        String strValue = String.valueOf(value);//---Edited by Cz 30/10/2556 แก้ไขกรณีตัวเลขหลังทศนิยมหลักที่ 3 มีค่ามากกว่าหรือเท่ากับ 5 ให้ปัดเศษขึ้น
        BigDecimal bd = new BigDecimal(strValue);//---Edited by Cz 30/10/2556 แก้ไขกรณีตัวเลขหลังทศนิยมหลักที่ 3 มีค่ามากกว่าหรือเท่ากับ 5 ให้ปัดเศษขึ้น
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        returnValue = bd.doubleValue();
        return returnValue;
    }
    
    /**
     * Create By : Paungporn
     * Create Date : 28/01/2553
     * หา AccountStatusName ของ AccountStatus
     * @return String -
     */
    public static String getAccountStatus(String accountStatus){
        String accountStatusName = "";
        if(accountStatus.compareTo("0")==0){
            accountStatusName = "ACTIVE";
        }else if(accountStatus.compareTo("1")==0){
            accountStatusName = "UNCLAIMED";
        }else if(accountStatus.compareTo("2")==0){
            accountStatusName = "MISCELLANEOUS";
        }else if(accountStatus.compareTo("3")==0){
            accountStatusName = "NO-CR-INT";
        }else if(accountStatus.compareTo("4")==0){
            accountStatusName = "CLOSED";
        }else if(accountStatus.compareTo("5")==0){
            accountStatusName = "SUSPENSE";
        }else if(accountStatus.compareTo("6")==0){
            accountStatusName = "NON-POST";
        }else if(accountStatus.compareTo("7")==0){
            accountStatusName = "DEFAULT 1";
        }else if(accountStatus.compareTo("8")==0){
            accountStatusName = "DEFAULT 2";
        }else if(accountStatus.compareTo("9")==0){
            accountStatusName = "DEFAULT 3";
        }else if(accountStatus.compareTo("10")==0){
            accountStatusName = "MATURE";
        }else if(accountStatus.compareTo("11")==0){
            accountStatusName = "WRITE-OFF";
        }else if(accountStatus.compareTo("")==0){
            accountStatusName = "-";
        }    
        return accountStatusName;
    }
    
    /**
     * Create By : Prawith Thowphant
     * Create Date : 08/01/2553
     * ใช้สำหรับปัดเศษทศนิยมของตัวแปร double เพื่อแก้ไขปัญหา bug ของ double
     * @param value ค่าที่ต้องการปัดเศษ สามารถน้อยกว่าศูนย์ได้
     * @param decimalPlace จำนวนหลักของทศนิยม
     * @return double ค่าที่มีการปัดเศษแล้ว
     */    
    public static double roundDownDouble(double value, int decimalPlace) {
        double returnValue = 0;

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_DOWN);
        returnValue = bd.doubleValue();
        return returnValue;
    }
    
    /**
     * Create By : Bunchar Noorod
     * Create Date : 24/01/2557
     * หา ApproveStatusName ของ ApproveStatusName
     * @return String รหัสสถานะ
     */
    public static String getApproveStatusName(String approveStatus) {
       
        if(approveStatus.equals("")) {
            approveStatus = "ยังไม่ดำเนินการ";
        } 
        else if(approveStatus.equals("I")) {
            approveStatus = "รอส่งอนุมัติ";
        }
        else if(approveStatus.equals("W")) {
            approveStatus = "รออนุมัติ";
        }
        else if(approveStatus.equals("Y")) {
            approveStatus = "อนุมัติ";
        }
        else if(approveStatus.equals("N")) {
            approveStatus = "ไม่อนุมัติ";
        }
        else if(approveStatus.equals("C")) {
            approveStatus = "ไม่ส่งอนุมัติ";
        }
       
        return approveStatus;
    }
    /**
     * 
     * @param status
     * @return Name of status
     */
    public static String getSendStatusName(String status) {
        
        if(status.equals("")) {
        	status = "รอดำเนินการ";
        } 
        else if(status.equals("Y")) {
        	status = "ส่งอนุมัติ";
        }
       
        return status;
    }
    
    public static String getIntValueFromString(String str){
    	if(str!=null&&!str.trim().equals("")){
    		return str;
    	}else{
    		return "0";
    	}
    }
    
//    public static String getCrystalServer(){
//       return crystalServer;
//    }
//    
//    public static String getCrystalCluster(){
//       return crystalCluster;
//    }
//    
//    public static String getCrystalUserid(){
//       return crystalUserid;
//    }
//    
//    public static String getCrystalPassword(){
//       return crystalPassword;
//    }
//    
//    public static String getCrystalDB(){
//       return crystalDB;
//    }
//        
//    public static String getCrystalDBUserid(){
//       return crystalDBUserid;
//    }
//    
//    public static String getCrystalDBPassword(){
//       return crystalDBPassword;
//    }
//    
//    public static String getCrystalServerName(){
//       return crystalServerName;
//    }
//    
//    public static String getCrystalServerType(){
//       return crystalDBServerType;
//    }
    
    /**
     * numberic only and decimal two digit
     * @param str
     * @return
     */
    public static boolean isNumeric(String str){
    	return str.matches("[-+]?\\d*\\.?\\d+");
    }
    
    /**
     * numeric only 0-9
     * @param str
     * @return
     */
    public static boolean isNumericPositive(String s) {
	    return s.matches("\\d*");
	}
    
    /**
     * alpha(English) or numeric or "-" or " " only
     * "a" through "z" or "A" through "Z" or "-" or " "
     * @param str
     * @return
     */
    public static boolean isAlphaNumericOrMinusBlankOnly(String str){
    	return str.matches("^[\\p{Alnum}_\\-\\p{Space}]*");
    }
    
    /**
     * Create by Cz 18 Dec 2013
     * กรอง input ที่รับมาจากหน้าจอเพื่อป้องกันการโจมตีด้วย SQL injection 
     * @param inputForm String
     * @return a String
     */
    public static String detectSQLInjection(String inputForm){
        try{
            String tmpValue = "";
            int foundEqual = 0,afterEqual = 0,end = 0,endOftmpValue = 0;
            
            //วนลูป ตัด comment /* */ และข้อความที่อยู่ใน comment
            int start = inputForm.indexOf("/*");
            while(start != -1){
                end = inputForm.indexOf("*/",start+2);
                tmpValue = inputForm.substring(start,end+2);
                if((foundEqual = tmpValue.indexOf("=")) != -1){
                    afterEqual = (start+foundEqual)+1;
                    tmpValue = inputForm.substring(afterEqual,end+2);
                    inputForm = inputForm.replace(tmpValue,"");
                    end = (start+foundEqual)+1;
                    tmpValue = inputForm.substring(start,end);
                }else{
                    end = inputForm.indexOf("*/",start+2);
                    tmpValue = inputForm.substring(start,end+2);
                }
                inputForm = inputForm.replace(tmpValue,"");
                start = inputForm.indexOf("/*");
            }

            //ตัด single quote
            inputForm = inputForm.replaceAll("\'","");
            //ตัดรูปแบบคำสั่ง or x=x|or 1=1|or 2>1|or 1<2|or simple like sim%|or temp in ('a')|or temp between 'x' and 'y'
            inputForm = inputForm.replaceAll("(or|OR)(\\s+)?.+(\\s+)?[=><(like)(LIKE)(in)(IN)(between)(BETWEEN)]?(\\s+)?.+","");
            //ตัดรูปแบบคำสั่ง CHR(0-9+)
            inputForm = inputForm.replaceAll("(chr|CHR)\\(\\d+\\)","");
            //ตัด reserved words บางคำ ที่ใช้ใน sql
            inputForm = inputForm.replaceAll("\\b(?:AND|BETWEEN|CREATE|DELETE|DROP|GRANT|ALL|" +
                "CHR|INSERT|INTO|LIKE|NOT|NULL|OR|SELECT|TABLE|UNION|UPDATE|WHERE|" +
                "and|between|create|delete|drop|grant|all|chr|insert|into|like|not|" +
                "null|or|select|table|union|update|where)\\b","");
            //ตัด punctuation characters [!"#$%&'()*+,/:;<=>?@\^_`{|}~] ยกเว้น (-) hyphen, (.) dot, (/) slash, อักษรที่เป็นภาษาไทย, (s+) ช่องว่าง, () วงเล็บเปิด-ปิด, (*) ดอกจัน
            inputForm = inputForm.replaceAll("[^\\w\\-\\.\\/\\p{InThai}\\s+\\(\\)\\*]","");// \\p{Punct}+
            //ตัดช่องว่างทั้งหมด
//            inputForm = inputForm.replaceAll("\\s+","");
            
        }catch(Exception e){
            System.out.println("Error in detectSQLInjection() : inputForm = "+inputForm);
            System.err.println("Error in detectSQLInjection() : " + e.getLocalizedMessage());
            inputForm = "";
        }
        return inputForm;
    }
    
    /**
     * get payment type 
     * @param paymentType paymentType
     * 1 : Cash, 2 : Cheque, 3 : Other Cheque, 4 : Transfer, 9 : Transfer
     * 
     * @return paymentTypeName
     */
    public static String getPaymantTypeNameByPaymentTypeCode(String paymentType){
    	Map<String, String> payTypeMap = new HashMap<String, String>();
    	payTypeMap.put("1", "Cash");
    	payTypeMap.put("2", "Cheque");
    	payTypeMap.put("3", "Other Cheque");
    	payTypeMap.put("4", "Transfer");
    	payTypeMap.put("9", "Other");
    	
    	String payType = payTypeMap.get(paymentType);
    	return (payType == null)? "": payType;
    }
    
    /**
     * @modify_date : 20161123
     * change double in String format to double value
     * @param doubleStr : 1234 or 1234.00 or 1,234.00
     * @return 1234.00 in type double
     */
    public static double stringToDouble(String doubleStr){
		double result = 0d;
		if(doubleStr != null && !"".equals(doubleStr)){
			String value = doubleStr.replace(",", "");
			if(Tools.isNumeric(value)){
				BigDecimal bd = new BigDecimal(value);
				result = bd.doubleValue();
			}
		}
		return result;
	}
    
    /**
     * @modify_date : 20161129
     * change double in String format to double value
     * @param doubleStr : 1234 or 1234.00 or 1,234.00
     * @return type BigDecimal
     */
    public static BigDecimal stringToBigDecimal(String doubleStr){
    	BigDecimal bd = BigDecimal.ZERO;
		if(doubleStr != null && !"".equals(doubleStr)){
			String value = doubleStr.replace(",", "");
			if(Tools.isNumeric(value)){
				bd = new BigDecimal(value);
			}
		}
		return bd;
	}
    
    /**
     * @modify_date : 20161129
     * change BigDecimal to currency format and maximum scale is 2 digit
     * @param data is BigDecimal to change.
     * @param isScale : true is currency with 2 scale / false is currency no scale
     * @return currency format : #,### or #,###.##
     */
    public static String currencyFormat2Scale(BigDecimal data, boolean isScale){
		String result = "0";
		DecimalFormat df = new DecimalFormat("#,###");
		if(isScale){
			result = "0.00";
			df = new DecimalFormat("#,###.00");
		}
		
		if(data != null && data.doubleValue() > 0){
			result = df.format(data);
		}
		return result;
	}
	
	public static String getFormatDateSeqCifName(String seq , String cif ){
    	String result = "";    	
    	SimpleDateFormat fm = new SimpleDateFormat("ddMMyyyyy");
    	String date = fm.format(new Date());
    	result = cif+"_"+seq+"_"+date;
    	return result;
    }
	
	public static String encode(String data) {
		String encode = "";
		if(data != null && data.length() > 0) {
			String p1 = data.substring(0, 3);
			String p2 = data.substring(3, data.length()-2);
			String p3 = data.substring(data.length()-2, data.length());
			String dataEncode = p2 + p1 + p3;
//			byte[] encodeByte = Base64.getEncoder().encode(dataEncode.getBytes());
			encode = Base64.encode(dataEncode.getBytes());
//			encode = new String(encodeByte);
/*			
			byte[] encodeByte = Base64.getEncoder().encode(data.getBytes());
			String encodeP = new String(encodeByte);
			String p1 = encodeP.substring(0, 3);
			String p2 = encodeP.substring(3, encodeP.length()-2);
			String p3 = encodeP.substring(encodeP.length()-2, encodeP.length());
			encode = p2 + p1 + p3;
*/
			
		}
		return encode;
	}
	
	public static String decode(String data) {
		String decode = "";
		if(data != null && data.length() > 0) {
//			byte[] decodeByte = Base64.getDecoder().decode(data.getBytes());
			byte[] decodeByte = Base64.decode(data);
			String decodeTmp = new String(decodeByte);
			String d1 = decodeTmp.substring(0, (decodeTmp.length() - 5));
			String d2 = decodeTmp.substring((decodeTmp.length() - 5), decodeTmp.length() - 2);
			String d3 = decodeTmp.substring(decodeTmp.length() - 2, decodeTmp.length());
			decode = d2 + d1 + d3;
			
			/*
			String d1 = data.substring(0, data.length() - 5);
			String d2 = data.substring(data.length() - 5, data.length() - 2);
			String d3 = data.substring(data.length() - 2, data.length());
			String newDecodeP = d2 + d1 + d3;
			
			byte[] decodeByte = Base64.getDecoder().decode(newDecodeP.getBytes());
			decode = new String(decodeByte);
			*/
		}
		return decode;
	}
}
