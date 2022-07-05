package com.doordash.dataservice.service.common;

public class DataServiceCommon {
    public static final String HOME_PREFIX = "(Home) ";
    public static final String CELL_PREFIX = "(Cell) ";
    public static final Character PHONE_DELIMITER = '-';
    public static final Character DIGIT = 'd';
    public static final String PHONE_NUMBER_PATTERN = "ddd-ddd-dddd";
    public static final String HOME_PHONE_INPUT_PATTERN = HOME_PREFIX + PHONE_NUMBER_PATTERN;
    public static final String CELL_PHONE_INPUT_PATTERN = CELL_PREFIX + PHONE_NUMBER_PATTERN;
    public static final String COMPLETE_PHONE_INPUT_PATTERN = HOME_PHONE_INPUT_PATTERN + " " +CELL_PHONE_INPUT_PATTERN;

}
