package com.meng.define;

/**
 * common define
 *
 * @author : sunyuecheng
 */
public final class CommonDefine {
    /**
     * default encoding
     */
    public static final String DEFAULT_ENCODING = "UTF-8";

    /**
     * collection init size
     */
    public static final int COLLECTION_INIT_SIZE = 16;

    /*
     * regex format check ipv4
     */
    public static final String REGEX_FORMAT_CHECK_IPV4 = "\\A(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)(\\.(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)){3}\\z";

    /*
     * regex format check ipv4 segment
     */
    public static final String REGEX_FORMAT_CHECK_IPV4_SEGMENT = "^(?=(\\b|\\D))(((\\d{1,2})|(1\\d{1,2})|(2[0-4]\\d)|(25[0-5]))\\.){3}((\\d{1,2})|(1\\d{1,2})|(2[0-4]\\d)|(25[0-5]))(?=(\\b|\\D))/([1-2][0-9]|3[0-2]|[1-9])$";
    /*
     * regex format check ipv6
     */
    public static final String REGEX_FORMAT_CHECK_IPV6 = "^\\s*((([0-9A-Fa-f]{1,4}:){7}([0-9A-Fa-f]{1,4}|:))|(([0-9A-Fa-f]{1,4}:){6}(:[0-9A-Fa-f]{1,4}|((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3})|:))|(([0-9A-Fa-f]{1,4}:){5}(((:[0-9A-Fa-f]{1,4}){1,2})|:((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3})|:))|(([0-9A-Fa-f]{1,4}:){4}(((:[0-9A-Fa-f]{1,4}){1,3})|((:[0-9A-Fa-f]{1,4})?:((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3}))|:))|(([0-9A-Fa-f]{1,4}:){3}(((:[0-9A-Fa-f]{1,4}){1,4})|((:[0-9A-Fa-f]{1,4}){0,2}:((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3}))|:))|(([0-9A-Fa-f]{1,4}:){2}(((:[0-9A-Fa-f]{1,4}){1,5})|((:[0-9A-Fa-f]{1,4}){0,3}:((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3}))|:))|(([0-9A-Fa-f]{1,4}:){1}(((:[0-9A-Fa-f]{1,4}){1,6})|((:[0-9A-Fa-f]{1,4}){0,4}:((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3}))|:))|(:(((:[0-9A-Fa-f]{1,4}){1,7})|((:[0-9A-Fa-f]{1,4}){0,5}:((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3}))|:)))(%.+)?\\s*$";

    /*
     * regex format check path
     */
    public static final String REGEX_FORMAT_CHECK_PATH = "[a-zA-Z]:(\\\\([0-9a-zA-Z]+))+|(\\/([0-9a-zA-Z]+))+";

    /*
     * regex format check uuid
     */
    public static final String REGEX_FORMAT_CHECK_UUID = "[0-9a-f]{8}(-[0-9a-f]{4}){3}-[0-9a-f]{12}";

    /**
     * radix 10
     */
    public static final int RADIX_10 = 10;

    /**
     * milli second 1000
     */
    public static final long MILLI_SECOND_1000 = 1000L;

    /**
     * one year second
     */
    public static final long ONE_YEAR_SECOND = 365 * 24 * 3600L;

    /**
     * one week second
     */
    public static final long ONE_WEEK_SECOND = 7 * 24 * 3600L;

    /**
     * one day second
     */
    public static final long ONE_DAY_SECOND = 24 * 3600L;

    /**
     * one hour second
     */
    public static final long ONE_HOUR_SECOND = 3600L;

    /**
     * one minute second
     */
    public static final long ONE_MINUTE_SECOND = 60L;

    /**
     * day hours
     */
    public static final int DAY_HOURS = 24;

    /**
     * day hours
     */
    public static final double PERCENT_100 = 100.00;

    /**
     * bit len
     */
    public static final int BIT_LEN = 8;

    /**
     * star division flag
     */
    public static final String STAR_DIVISION_FLAG = "*";

    /**
     * point division flag
     */
    public static final String POINT_DIVISION_FLAG = ".";

    /**
     * lang zn ch
     */
    public static final String LANG_ZH_CN = "zh-cn";

    /**
     * lang en us
     */
    public static final String LANG_EN_US = "en-us";

    /**
     * read buffer size
     */
    public static final int READ_BUFFER_SIZE = 1024 * 32;

    /**
     * service status busy
     */
    public static final String SERVICE_STATUS_BUSY = "BUSY";

    /**
     * msg deal status key
     */
    public static final String MSG_DEAL_STATUS_KEY = "msgDealStatus";

    /**
     * service thread pool used count key
     */
    public static final String SERVICE_THREAD_POOL_USED_COUNT_KEY = "threadPoolUsedCount";

    /**
     * msg response max time header
     */
    public static final String MSG_RESPONSE_MAX_TIME_HEADER = "msgResponseMaxTime-%s";

    /**
     * msg consume count header
     */
    public static final String MSG_CONSUME_COUNT_HEADER = "msgConsumeCount-%s";

    /**
     * msg consume total time header
     */
    public static final String MSG_CONSUME_TOTAL_TIME_HEADER = "msgConsumeTotalTime-%s";

    /**
     * msg consume max time header
     */
    public static final String MSG_CONSUME_MAX_TIME_HEADER = "msgConsumeMaxTime-%s";

    /**
     * jwt claim role info key
     */
    public static final String JWT_CLAIM_ROLE_INFO_KEY = "ROLE";

    /**
     * linux path regex
     */
    public static final String LINUX_PATH_REGEX = "^([\\\\/][\\w._-]+)*[\\\\/]?$";

    /**
     * windows path regex
     */
    public static final String WINDOWS_PATH_REGEX = "^[A-Za-z]:([\\\\/][^\\\\/:*?\"<>|]+)*[\\\\/]?$";

    /**
     * sax reader faeture disallow
     */
    public static final String SAX_READER_FAETURE_DISALLOW = "http://apache.org/xml/features/disallow-doctype-decl";

    /**
     * system property log trace switch key
     */
    public static final String SYSTEM_PROPERTY_LOG_TRACE_SWITCH_KEY = "LOG_TRACE_SWITCH";

    private CommonDefine() {
    }
}
