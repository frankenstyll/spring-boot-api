package com.dsl.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 *
 * @author Administrator
 */
public class StringUtil {

    public static final String DEFAULT_CURRENCY_FORMAT = "#,##0.00";
    public static final String SIMPLE_DOUBLE = "0.00";
    public static final String INTEGER_FORMAT = "#,##0";
    public static final String BLANK = "";
    public static final String COMMA_SINGLE_QUOTE = ",'";
    public static final String COMMA = ",";
    public static final String SEMICOLON = ";";
    public static final String SINGLE_QUOTE = "'";
    public static final String TWO_SINGLE_QUOTE = "''";
    public static final String SHARP = "#";
    public static final String PIPE = "|";
    
    
	public static char[] THAI_NUMBER = {'๐','๑','๒','๓','๔','๕','๖','๗','๘','๙'};
	
	public static String convertNumberThai(String str) {
		StringBuilder builder = new StringBuilder();
		for(int i =0;i<str.length();i++)
		{
		    if(Character.isDigit(str.charAt(i)))
		    {
		        builder.append(THAI_NUMBER[(int)(str.charAt(i))-48]);
		    }
		    else
		    {
		        builder.append(str.charAt(i));
		    }
		}
		  return builder.toString();
	}
	
	

    public static String getBatchTypeDesc(String type) {
        String desc = "";
        if("T".equals(type)){
            desc = "Tele Pro";
        }else if("C".equals(type)){
            desc = "CRM";
        }
        return desc;
    }

    interface OS {
        String Windows = "windows";
        String Linux = "linux";
        String Mac = "linux";
        String Sun = "sunos";
    }

    public static String nullToString(String str) {
    	return null == str||1 >= str.trim().length()? "":str.trim();
    }
    public static BigDecimal stringToBigDecimal(String str) {
    	return null == str ? new BigDecimal(0.00):new BigDecimal(str.trim());
    }
    public static Integer stringToInteger(String str) {
    	return null == str? 0:Integer.parseInt(str.trim());
    }
    public static boolean isEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public static boolean isBlank(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    public static String formatCurrentcy(String format, BigDecimal currency) {
        NumberFormat df = new DecimalFormat(format);
        String formatStr = df.format(currency);
        return formatStr;
    }

    public static String formatCurrentcy(BigDecimal currency) {
        if(currency == null) return "";
        return formatCurrentcy(DEFAULT_CURRENCY_FORMAT, currency);
    }

    public static int removeNumberCommas(String numberWithComma) {
        if (isEmpty(numberWithComma)) {
            return 0;
        }
        if (numberWithComma != null && numberWithComma.indexOf(',') > 0) {
            return Integer.parseInt(numberWithComma.replace(",", ""));
        }
        return Integer.parseInt(numberWithComma.trim());
    }

    public static String notNull(String testStr) {
        return testStr == null ? BLANK : testStr;
    }
    
    /***
     if null or blank  return null
     else return value.trim
     ***/
    public static String trimEmptyToNull(String testStr) {
        return isBlank(testStr) ? null : testStr.trim();
    }
    public static String trimEmptyToNullUpper(String testStr) {
        return isBlank(testStr) ? null : testStr.trim().toUpperCase();
    }
    
    /***
     if null  return ""
     if have a value return that value.trim
     ***/
    public static String trimNotNull(String testStr) {
        return isEmpty(testStr) ? notNull(testStr).trim() : testStr.trim() ;
    }
    
    public static String emptyToNull(String testStr) {
        return isBlank(testStr) ? null : testStr;
    }

    public static String toString(Object obj) {
        return ToStringBuilder.reflectionToString(obj);
    }

    public static String removeStartWithZero(String text) {

        String result = text.replaceFirst("^0*", "");
        System.out.println("result: " + result);
        return result;
    }
    /**
     * *
     * Replace /xxx/ with replace string
     *
     * @param text ex., text= "select /a,b,c/ from orders ";
     * @param replaceWith ex., "1"
     * @return newSql = "select 1 from orders" if match else just return orginal
     * text sql
     */
    public static final String REGEX = "(/.+?/)";

    public static String replaceSql(String text, String replaceWith) {
        Pattern p = Pattern.compile(REGEX);
        Matcher m = p.matcher(text);
        boolean found = m.find();
        if (found) {
            return m.replaceAll(replaceWith);
        } else {
            return text;
        }
    }
    
    public static void main(String[] args) {

//        double dx = 999999999999.9999;
//        System.out.println("dx:" + dx);
//        Double dox = new Double(dx);
//        BigDecimal bd = new BigDecimal(dx, MathContext.DECIMAL64);
//        int p = NumberUtil.getDecimalPointValue(bd);
//        System.out.println("max:"+ Double.MAX_VALUE);
//        System.out.println("bdxxxxxxx:"+NumberUtil.formatNumber(dox, "#############.####"));
//        String javaName = StringEscapeUtils.escapeHtml("ทดสอบ Thai.pdf");
//        String javaName2 = StringEscapeUtils.escapeJava("ทดสอบ Thai.pdf");
//        String javaName3 = StringEscapeUtils.escapeJavaScript("ทดสอบ Thai.pdf");
//        try {
//            String encoded = URLEncoder.encode("คนทำงานทุกคนใช้อยู่ในที่ทำงานไม่น้อยกว่าวันละ 7 .doc", "UTF-8");
//            
//            System.out.println("encoded : "+ encoded);
//        } catch (UnsupportedEncodingException ex) {
//            Logger.getLogger(StringUtil.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        System.out.println("JavaName: "+javaName);
//        System.out.println("JavaName: "+javaName2);
//        System.out.println("JavaName: "+javaName3);
//        String name = StringEscapeUtils.unescapeHtml(javaName);
//        System.out.println("name: "+name);
//        long maxSize = 1024*1000*10;
//        File f = new File("d:/BCC_register_32832_2556_2.pdf");
//        System.out.println(" max: "+ maxSize);
//        System.out.println(" len: "+f.length());
//        String txt = "D40S5107010113100003004999955625000   03004Z1001700001P1000                       0004725408866.00  ";
//        String sub = subString(txt, 38,16);
//        System.out.println("sub:"+sub);
//        List<String> str = new ArrayList();
//        String msg = StringUtils.join(str, "/");
//        System.out.println("msg: "+msg);
//        System.out.println("size: "+str.size());
//        String mark = "√";
////        char m = '√';
////        char m1 = 'a';
//        System.out.println("m:"+ (int)mark.charAt(0));
//        System.out.println("m:"+ CharUtils.isAscii(m1));
//        System.out.println("m:"+mark.charAt(0));
//        String e = StringEscapeUtils.escapeHtml(mark);
//        String u = StringEscapeUtils.unescapeHtml(e);
//        
//        System.out.println("e: "+e);
//        System.out.println("u: "+u);
//        System.out.println("m1:"+(int)m1);
//        System.out.println("mark:"+ mark);
//        System.out.println("Unicode2ASCII = " +Unicode2ASCII(mark));
//        System.out.println("ASCII2Unicode = " +ASCII2Unicode(mark));
//        String pid = StringUtils.remove("1-2498-00057-99-1", "-");
//        System.out.println("pid: "+ pid);
//        System.out.println("TERM_LOAN: " +CreditReviewEnum.LoanType.values().toString());
//        printColumnNameCase();
//           printGenPrepareStatement();
//          printTableNameList();
        
        
//        BigDecimal bd = new BigDecimal(0.3434);
//        System.out.println("bd.toString: " + bd.doubleValue());
//        System.out.println("bd.toString: " + bd.toPlainString());
//        String num = formatCurrentcy("0.00", bd);
//        System.out.println("number: " + num);
//        StringBuilder sb = new StringBuilder();
//        sb.append("select /a,b,c/  from orders  ");
//        sb.append("select /d, e, f/ from separare ");
//        sb.append("select /a, b, ");
//        sb.append("c/ from tab ");
//        sb.append("select /*/ from table1 ");
//        sb.append("select ${b} from table2 ");
//        String result = replaceSql(sb.toString(), "1");
//        System.out.println("result:" + result);
//		Map map = new HashMap();
//		map.put("a", "a,b,c");
//		map.put("b", "d,e,f");
//		StrSubstitutor sub = new StrSubstitutor(map);
//		String x = sub.replace(sb);
//		System.out.println("x:" +x.toString());
    }

    /****
     *Support only Oracle data type for right now 
     * 
     * */
    private static String convertDBType2JavaType(String dbDataType) {
//        System.out.println("type : " +dbDataType);
        if (isEmpty(dbDataType)) {
            throw new IllegalArgumentException("Invalide dbDataType parameter : " + dbDataType);
        }

        if (dbDataType.startsWith("VARCHAR") || dbDataType.startsWith("VARCHAR2") || dbDataType.startsWith("CHAR")) {
            return "String";
        } else if (dbDataType.startsWith("DATE")) {
            return "Date";
        } else if (dbDataType.startsWith("TIMESTAMP")) {
            return "Timestamp";
        } else if (dbDataType.startsWith("NUMBER")) {
            //that can be ex, NUMBER(12,2) or NUMBER(12,0)
            if (StringUtils.contains(dbDataType, ",")) {
                
                int openIdx = StringUtils.indexOf(dbDataType, "(");
                int closeIdx = StringUtils.indexOf(dbDataType, ")");
                String numVal = StringUtils.substring(dbDataType, openIdx+1, closeIdx);
//                System.out.println("numval: " + numVal);
                //vals[0] = number size
                //vals[1] = precision size
                String[] vals = StringUtils.splitByWholeSeparator(numVal, ",");
//                System.out.println("val[0]: "+vals[0] +", val[1]:" +vals[1]);
                if (Integer.parseInt(vals[1]) > 0) {
                    return "BigDecimal";
                } else if (Integer.parseInt(vals[0]) > 10) {
                    return "Long";
                } else {
                    return "Int";
                }
            // don't have comma , so can be Int or Long, ex., NUMBER(1) , NUMBER(12)   
            } else {
//                System.out.println("dbDataType : "+ dbDataType);
                if(!StringUtils.contains(dbDataType, "(")){
                    return "Int";
                }
                int openIdx = StringUtils.indexOf(dbDataType, "(");
                int closeIdx = StringUtils.indexOf(dbDataType, ")");
                String numVal = StringUtils.substring(dbDataType, openIdx+1, closeIdx);
                if (Integer.parseInt(numVal) > 10) {
                    return "Long";
                } else {
                    return "Int";
                }
            }
            

        }

        return null;
    }
    
    
    private static String convertToPrimitive(String objectType){
        if("BigDecimal".equals(objectType)){
            return "BigDecimal";
        }else if("Long".equals(objectType)) {
            return "Long";
        }else if("Int".equals(objectType)) {
            return "Integer";
        } else {
            return objectType;
        }
    }
    
    public static boolean isValidCitizenId(String inputCitizenId) {

        String stringCitizenId = new String(inputCitizenId);
        System.out.println("stringCitizenId :: " + stringCitizenId);
        if (stringCitizenId == null || stringCitizenId.length() == 0
                || stringCitizenId == "") {
            return false;
        }

        int[] i_mul = new int[]{
            13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2};
        String gid = stringCitizenId;
        int l_strlen = stringCitizenId.length();
        int i_weight, i_dummy, r_chkmod, s_dummy, i_lastdigit;
        int i_sum = 0;

        /*
         * if (isNaN(gid)) { return false;
        }
         */
        System.out.println("pass :step--->1");
        if (l_strlen == 0) {
            return false;
        }
        if (l_strlen != 13) {
            return false;
        }
        System.out.println("pass :step--->2");

        s_dummy = gid.charAt(12) - '0';
        System.out.println("last digit :: " + s_dummy);
        i_lastdigit = s_dummy;
        for (int i = 0; i <= 11; i++) {
            s_dummy = gid.charAt(i) - '0';
            i_dummy = s_dummy;
            i_weight = i_dummy * i_mul[i];
            i_sum = i_sum + i_weight;
        }

        i_sum = i_sum % 11;
        r_chkmod = i_sum;

        int i_chkdigit;
        if (r_chkmod == 0) {
            i_chkdigit = 1;
        } else if (r_chkmod == 1) {
            i_chkdigit = 0;
        } else {
            i_chkdigit = 11 - r_chkmod;
        }
        System.out.println("cal :: " + i_chkdigit);
        System.out.println("pass :step--->3");
        if (i_chkdigit == i_lastdigit) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isValidEmail(String email) {

        //Set the email pattern string
        Pattern p = Pattern.compile(".+@.+\\.[a-z]+");

        //Match the given string with the pattern
        Matcher m = p.matcher(email);

        //check whether match is found
        boolean matchFound = m.matches();

        return matchFound;
    }

    public static String Unicode2ASCII(String unicode) {
        StringBuilder ascii = new StringBuilder(unicode);
        int code;
        for (int i = 0; i < unicode.length(); i++) {
            code = (int) unicode.charAt(i);
            if ((0xE01 <= code) && (code <= 0xE5B)) {
                ascii.setCharAt(i, (char) (code - 0xD60));
            }
        }
        return ascii.toString();
    }

    public static String ASCII2Unicode(String ascii) {
        StringBuilder unicode = new StringBuilder(ascii);
        int code;
        for (int i = 0; i < ascii.length(); i++) {
            code = (int) ascii.charAt(i);
            if ((0xA1 <= code) && (code <= 0xFB)) {
                unicode.setCharAt(i, (char) (code + 0xD60));
            }
        }
        return unicode.toString();
    }

    /**
     * *
     *
     * @param List of Id if listOfId = [a,b,c] convert to => 'a','b','c' if
     * listOfId = null convert to => ''
     * @return
     */
    public static String genSqlInStr(List listOfId) {
        StringBuilder allKey = new StringBuilder();
        for (int i = 0; i < listOfId.size(); i++) {
            if (i == 0) {
                allKey.append(SINGLE_QUOTE).append(listOfId.get(i)).append(SINGLE_QUOTE);
            } else {
                allKey.append(COMMA_SINGLE_QUOTE).append(listOfId.get(i)).append(SINGLE_QUOTE);
            }
        }
        if (isEmpty(allKey.toString())) {
            allKey.append(TWO_SINGLE_QUOTE); //Set empty in sql as ''
        }
        return allKey.toString();
    }

    public static String Left(String text, int length) {
        if (text != null) {
            return text.substring(0, text.length()>=length?length:text.length() );
        } else {
            return null;
        }
    }

    public static String Right(String text, int length) {
        if (text != null) {
            return text.substring(text.length() - (text.length()>=length?length:text.length()), text.length());
        } else {
            return null;
        }

    }

    public static String Mid(String text, int start, int end) {
        if (text != null) {
            return text.substring(start, end);
        } else {
            return null;
        }

    }

    public static String Mid(String text, int start) {
        if (text != null) {
            return text.substring(start, text.length() - start);
        } else {
            return null;
        }

    }
    
    public static String subString(String text, int length){
        return subString(text, 0, length);
    }
    
    /***
     * 
     * @param text string text
     * @param indexStart start with 0
     * @param length length of string will be sub
     * @return 
     */
    public static String subString(String text, int indexStart, int length){
        return text.substring(indexStart, indexStart+length);
    }
    
    public static String getFlagText(int flagId) {
        if(flagId == -1){
            return "";
        }
        return flagId == 1 ? "ผ่าน" : "ไม่ผ่าน";
    }
    
    public static String getFlagValid(int flagId) {
        if(flagId == -1){
            return "";
        }
        return flagId == 1 ? "ถูกต้อง" : "ไม่ถูกต้อง";
    }
    
    public static String getFlagPreemptionText(int flagId) {
        return flagId == 1 ? "ใบจอง" : "ไม่ใช่ใบจอง";
    }
    
    public static String getAmtTypeText(String amtType) {
        if(isEmpty(amtType)){
            return "";
        }
        
        String text = "";
        if("I".equals(amtType)){
            text = "ในประเทศ";
        }else if("O".equals(amtType)){
            text = "ใบขนสินค้า";
        }
        return text;
    }
    
    public static String genExcelFileName(String prefix, String batchRefNo) {
        String batchReplace = batchRefNo.replace("/", "_");
        String excelFileName = prefix + batchReplace + ".xlsx";
        return excelFileName;
    }

    public static String genAuditKtbExcelFile(String batchRefNo) {
        String batchReplace = batchRefNo.replace("/", "_");
        String excelFileName = "ktb_audit_file_" + batchReplace + ".xlsx";
        return excelFileName;
    }



    public static String genReconcileExcelFileName(int recId, String batchRefNo){
        String batchReplace = batchRefNo.replace("/", "_");
        return "reconcile_"+recId+"_"+batchReplace+".xlsx";
    }
    
    
    /***
     * flagStr : flag number as string
     * @return 
     * -1 if flagStr is null
     * else return parseInt of flagStr
     */
    public static int getFlagValue(String flagStr){
        return isEmpty(flagStr) ? -1 : Integer.parseInt(flagStr);
    }
    
//    public static String checkOS(){
//        String os = "";
//        if (System.getProperty("os.name").toLowerCase().indexOf("win") > -1) {
//            os = "windows";
//        } else if (System.getProperty("os.name").toLowerCase().indexOf("nux") > -1) {
//            os = "linux";
//        } else if (System.getProperty("os.name").toLowerCase().indexOf("mac") > -1) {
//            os = "mac";
//        } else if (System.getProperty("os.name").toLowerCase().indexOf("nix") > -1) {
//            os = "unix";
//        } else if (System.getProperty("os.name").toLowerCase().indexOf("sunos") > -1) {
//            os = "sun";
//        }
//        return os;
//    }
    
    public enum OSType {
        Windows, MacOS, Unix, Other
    };
    
    protected static OSType detectedOS;

    public static OSType checkOS() {
        if (detectedOS == null) {
            String OS = System.getProperty("os.name", "generic").toLowerCase();
            if (OS.indexOf("win") >= 0) {
                detectedOS = OSType.Windows;
            } else if (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0) {
                detectedOS = OSType.Unix;
            } else if ((OS.indexOf("mac") >= 0) || (OS.indexOf("darwin") >= 0)) {
                detectedOS = OSType.MacOS;
            }else {
                detectedOS = OSType.Other;
            }
        }
        return detectedOS;
    }
    
	public static String convertToFullName(String title, String fname, String lname) {
		if (title != null && fname != null && lname != null) {
			return title + " " + fname + " " + lname;
		} else {
			return "-";
		}
	}

}
