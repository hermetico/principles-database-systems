package project2.gintonics.Utils;

import org.apache.commons.codec.digest.DigestUtils;

public class Utils {
    public static String getKeyFromName(String seq){
        return DigestUtils.md5Hex(seq).toUpperCase();
    }

    public static String getCombinationName(String gin, String tonic){
        return gin + " with " + tonic;
    }

    public static String getCombinationName(String gin, String tonic, String garnish){
        return gin + " with " + tonic + " and " + garnish;
    }
}
