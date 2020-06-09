package com.springboot.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Administrator
 */
public class CollectionUtil {

    private static final Logger logger = LogManager.getLogger(CollectionUtil.class);
    public static final String DEFAULT_DELIMITER = ",";

    public static boolean isEmpty(Collection collection) {
        return (collection == null || collection.isEmpty());
    }

    public static boolean isNotEmpty(Collection collection) {
        return !isEmpty(collection);
    }

    /**
     * *
     * Get size of Collection and NullPointerException safe
     *
     * @param collection
     * @return -1 if collction is null then else return collection's size
     */
    public static int getSize(Collection collection) {
        if (collection == null) {
            return -1;
        }
        return collection.size();
    }

    /**
     * * convert String "a,b,c" to List["a","b","c"] **
     */
    public static List extractStringToList(String arrString, String delimiter) {
        if (arrString == null || "".equals(arrString)) {
            return null;
        }
        StringTokenizer token = new StringTokenizer(arrString, delimiter);
        List list = new ArrayList();
        while (token.hasMoreTokens()) {
            list.add(token.nextToken());
        }
        return list;
    }

    /**
     * * Convert List["a","b","c"] to String "a,b,c" **
     */
    public static String listToString(List list, String delimiter) {
        if (list == null || list.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            Object val = list.get(i);
            if (i < list.size() - 1) {
                sb.append(val).append(delimiter);
            } else {
                sb.append(val);
            }
        }
        if (logger.isDebugEnabled()) {
            logger.debug("listToString converted =" + sb.toString());
        }
        return sb.toString();
    }

    public static boolean uniqueAll(List<String> list) {
        Set<String> set = new HashSet<String>(list);
        return set.size() == list.size();
    }

    public static List getMapKey(Map map) {
        Object[] arr = map.keySet().toArray();
        return Arrays.asList(arr);
    }

    static List genIableList = new ArrayList();

    static {
        genIableList.add("01");
        genIableList.add("0101");
        genIableList.add("0102");
        genIableList.add("0103");
        genIableList.add("0104");
        genIableList.add("0201");
        genIableList.add("0202");
        genIableList.add("0203");
        genIableList.add("0204");
    }

    public static void main(String[] args) {
        boolean u = uniqueAll(genIableList);
        System.out.println("u : " + u);
        boolean result = genIableList.contains("01");
        System.out.println("contain: " + result);
//        List list = extractStringToList(str, ",");
//        List x = getMapKey(null);
//        System.out.println("list:"+x);
//        System.out.println(" list.to" + list.toString());
    }
}
