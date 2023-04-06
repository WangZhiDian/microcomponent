package com.meng.util;

import org.apache.commons.lang3.StringUtils;

import static com.meng.define.CommonDefine.*;

/**
 * ip utils
 *
 * @author : sunyuecheng
 */
public final class IpUtils {
    /**
     * is ipv4 address
     *
     * @param address :
     * @return boolean :
     */
    public static boolean isIpv4Address(String address) {
        if (StringUtils.isEmpty(address) || address.length() < 7 || address.length() > 15) {
            return false;
        }

        return address.matches(REGEX_FORMAT_CHECK_IPV4);
    }

    /**
     * is ipv4 segment
     *
     * @param address :
     * @return boolean :
     */
    public static boolean isIpv4Segment(String address) {
        if (StringUtils.isEmpty(address) || address.length() < 9 || address.length() > 18) {
            return false;
        }

        return address.matches(REGEX_FORMAT_CHECK_IPV4_SEGMENT);
    }

    /**
     * is ipv6 address
     *
     * @param address :
     * @return boolean :
     */
    public static boolean isIpv6Address(String address) {
        if (StringUtils.isEmpty(address) || address.length() < 7 || address.length() > 128) {
            return false;
        }

        return address.matches(REGEX_FORMAT_CHECK_IPV6);
    }

    /**
     * ip to long
     *
     * @param strIp :
     * @return Long :
     */
    public static Long ipv4ToLong(String strIp) {
        if (!isIpv4Address(strIp)) {
            return null;
        }

        long[] ip = new long[4];
        int position1 = strIp.indexOf(".");
        int position2 = strIp.indexOf(".", position1 + 1);
        int position3 = strIp.indexOf(".", position2 + 1);

        ip[0] = Long.parseLong(strIp.substring(0, position1));
        ip[1] = Long.parseLong(strIp.substring(position1 + 1, position2));
        ip[2] = Long.parseLong(strIp.substring(position2 + 1, position3));
        ip[3] = Long.parseLong(strIp.substring(position3 + 1));
        return (ip[0] << 24) + (ip[1] << 16) + (ip[2] << 8) + ip[3];
    }

    /**
     * get ipv4 mask
     *
     * @param bitMask :
     * @return String :
     */
    public static String getIpv4Mask(int bitMask) {
        if (bitMask > 32 || bitMask < 0) {
            return null;
        }

        int[] tmpMask = {0, 0, 0, 0};
        int times = bitMask / 8;
        int i = 0;
        for (; i < times; i++) {
            tmpMask[i] = 255;
        }

        if (i < 4) {
            for (int j = 1; j <= 8; j++) {
                if (j <= bitMask - times * 8) {
                    tmpMask[i] = 2 * tmpMask[i] + 1;
                } else {
                    tmpMask[i] = 2 * tmpMask[i];
                }
            }
        }

        return tmpMask[0] + "." + tmpMask[1]
                + "." + tmpMask[2] + "." + tmpMask[3];
    }

    public static Long getIpv4SegmentBeginIp(String ipSegment) {
        if (!isIpv4Segment(ipSegment)) {
            return null;
        }

        String strMask = ipSegment.substring(ipSegment.indexOf("/") + 1);
        String strIp = ipSegment.substring(0, ipSegment.indexOf("/"));
        if (StringUtils.isEmpty(strMask) || StringUtils.isEmpty(strIp)) {
            return null;
        }

        Integer bitMask = null;
        try {
            bitMask = Integer.valueOf(strMask);
        } catch (Exception e) {
            return null;
        }

        String strMaskIp = getIpv4Mask(bitMask);
        if (StringUtils.isEmpty(strMaskIp)) {
            return null;
        }

        Long ip = ipv4ToLong(strIp);
        Long maskIp = ipv4ToLong(strMaskIp);

        if (ip == null || maskIp == null) {
            return null;
        }
        return ip & maskIp;
    }

    public static Long getIpv4SegmentEndIp(String ipSegment) {
        if (!isIpv4Segment(ipSegment)) {
            return null;
        }

        String strMask = ipSegment.substring(ipSegment.indexOf("/") + 1);
        String strIp = ipSegment.substring(0, ipSegment.indexOf("/"));
        if (StringUtils.isEmpty(strMask) || StringUtils.isEmpty(strIp)) {
            return null;
        }

        Integer bitMask = null;
        try {
            bitMask = Integer.valueOf(strMask);
        } catch (Exception e) {
            return null;
        }

        String strMaskIp = getIpv4Mask(bitMask);
        if (StringUtils.isEmpty(strMaskIp)) {
            return null;
        }

        Long beginIp = getIpv4SegmentBeginIp(ipSegment);
        Integer maskIp = ipv4ToLong(strMaskIp).intValue();

        if (beginIp == null || maskIp == null) {
            return null;
        }
        return beginIp + ~maskIp;
    }

    private IpUtils() {
    }
}
