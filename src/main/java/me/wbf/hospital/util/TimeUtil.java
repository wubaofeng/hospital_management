package me.wbf.hospital.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;

/**
 * 时间工具类
 * @author Baofeng.Wu
 */
public class TimeUtil {
	private TimeUtil() {
	}

	public static Long dateOfHtmlToLong(String date) {
		return Instant
				.ofEpochSecond(LocalDate.parse(date).toEpochSecond(LocalTime.parse("00:00:00"), ZoneOffset.of("Z")))
				.toEpochMilli();
	}

	public static Long datetimeLocalOfHtmlToLong(String dateTime) {
		return LocalDateTime.parse(dateTime).toInstant(ZoneOffset.UTC).toEpochMilli();
	}

}
