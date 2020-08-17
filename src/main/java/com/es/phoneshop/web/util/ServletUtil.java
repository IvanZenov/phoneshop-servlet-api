package com.es.phoneshop.web.util;

import com.es.phoneshop.model.exceptions.OutOfStockException;

import java.text.ParseException;
import java.util.Map;

public class ServletUtil {
    public static final String PREFIX = "/WEB-INF/pages/";
    public static final String SUFFIX = ".jsp";

    private ServletUtil(){}

    public static String createViewPath(String viewName){
        return String.format("%s%s%s",PREFIX,viewName,SUFFIX);
    }

    public static void handleError(Map<Long, String> errors, Long productId, Exception ex) {
        if (ex.getClass().equals(ParseException.class)||ex.getClass().equals(NumberFormatException.class)) {
            errors.put(productId,"Not a number");
        }
        if (ex.getClass().equals(OutOfStockException.class)) {
            errors.put(productId,"Out of stock, max available " + ((OutOfStockException) ex).getStockAvailable());
        }
    }
}
