package test.springboot.util;

import org.apache.commons.codec.digest.DigestUtils;

public class Md5Utils {

    public static String md5(String str) {
        return DigestUtils.md5Hex(str);
    }
}
