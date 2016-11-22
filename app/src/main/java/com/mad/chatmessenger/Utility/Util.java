package com.mad.chatmessenger.Utility;

/**
 * Created by atulb on 11/22/2016.
 */

public class Util {
    public static String getMergedId(String firstUserId, String secondUserId) {
        if (firstUserId.compareTo(secondUserId) < 0) {
            return firstUserId + secondUserId;
        } else {
            return secondUserId + firstUserId;
        }
    }
}
