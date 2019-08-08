package com.san.logicuniversity_ad.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateUtil {

    public static LocalDate parseMsTimestampToDate(String msJsonDateTime) {
        LocalDate result = null;
        if (msJsonDateTime != null) {
            Pattern datePatt = Pattern.compile("^/Date\\((\\d+)([+-]\\d+)?\\)/$");
            Matcher m = datePatt.matcher(msJsonDateTime);
            if (m.matches()) {
                Long l = Long.parseLong(m.group(1));
                result = Instant.ofEpochMilli(l).atZone(ZoneId.systemDefault()).toLocalDate();
                // Time zone is not needed to calculate date
            } else {
                throw new IllegalArgumentException("Wrong date format");
            }
        }
        return result;
    }
}
