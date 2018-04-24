package project2.gintonics.Utils;

import org.apache.commons.codec.digest.DigestUtils;

public class Utils {
    public static String getKeyFromName(String seq){
        return DigestUtils.md5Hex(seq).toUpperCase();
    }
}
