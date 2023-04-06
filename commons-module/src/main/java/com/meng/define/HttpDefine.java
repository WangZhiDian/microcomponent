package com.meng.define;

/**
 * http define
 *
 * @author : sunyuecheng
 */
public final class HttpDefine {

    /**
     * http url header
     */
    public static final String HTTP_URL_HEADER = "http://";


    /**
     * https url header
     */
    public static final String HTTPS_URL_HEADER = "https://";

    /**
     * http method get
     */
    public static final String HTTP_METHOD_GET = "GET";

    /**
     * http method post
     */
    public static final String HTTP_METHOD_POST = "POST";

    /**
     * http method delete
     */
    public static final String HTTP_METHOD_DELETE = "DELETE";


    /**
     * header content type
     */
    public static final String HEADER_CONTENT_TYPE = "Content-Type";

    /**
     * header accept
     */
    public static final String HEADER_ACCEPT = "Accept";

    /**
     * header host
     */
    public static final String HEADER_HOST = "Host";

    /**
     * header language
     */
    public static final String HEADER_LANGUAGE = "X-Language";

    /**
     * header algorithm
     */
    public static final String HEADER_ALGORITHM = "Algorithm";

    /**
     * header time stamp
     */
    public static final String HEADER_TIME_STAMP = "TimeStamp";

    /**
     * header region
     */
    public static final String HEADER_REGION = "region";

    /**
     * header content disposition
     */
    public static final String HEADER_CONTENT_DISPOSITION = "Content-Disposition";

    /**
     * accept application json
     */
    public static final String ACCEPT_APPLICATION_JSON = "application/json; charset=utf-8";

    /**
     * content type application json
     */
    public static final String CONTENT_TYPE_APPLICATION_JSON = "application/json";

    /**
     * content type application json utf-8
     */
    public static final String CONTENT_TYPE_APPLICATION_JSON_UTF_8 = "application/json; charset=utf-8";

    /**
     * content type application file
     */
    public static final String CONTENT_TYPE_APPLICATION_FILE = "application/octet-stream";

    /**
     * content disposition attachment file name
     */
    public static final String CONTENT_DISPOSITION_ATTACHMENT_FILE_NAME ="attachment;filename=";

    /**
     * jwt token prefix
     */
    public static final String JWT_TOKEN_PREFIX = "token ";

    /**
     * header jwt token
     */
    public static final String HEADER_JWT_TOKEN = "Authorization";

    /**
     * header x forwarded for
     */
    public static final String HEADER_X_FORWARDED_FOR = "x-forwarded-for";

    /**
     * header x request source type
     */
    public static final String HEADER_X_REQUEST_SOURCE_TYPE = "X-Request-Source-Type";

    /**
     * header x real ip
     */
    public static final String HEADER_X_REAL_IP = "X-Real-IP";


    private HttpDefine() {
    }
}
