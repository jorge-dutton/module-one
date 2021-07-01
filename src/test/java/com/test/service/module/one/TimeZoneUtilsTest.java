package com.test.service.module.one;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.TimeZone;

import org.junit.Before;
import org.junit.Test;
//import  org.junit.Assert.assertTrue;

public class TimeZoneUtilsTest {

	// TimeZones
	private static final String EUROPE_LISBON_CALENDAR = "Europe/Lisbon";
	private static final String EUROPE_PORTUGAL_CALENDAR = "Portugal";
	private static final String EUROPE_MADRID_CALENDAR = "Europe/Madrid";
	private static final String AMERICA_SAOPAULO_CALENDAR = "America/Sao_Paulo";
	private static final String INVALID_TIMEZONE = "invalidValueOfTimezone";
	private static final String TIMEZONE_ETC_GMT_1 = "Etc/GMT-1";

	// GMT references
	private static final String GMTplus01 = "GMT+1";
	private static final String GMTplus02 = "GMT+2";
	private static final String GMTstandart = "GMT+0";
	private static final String GMTminus03 = "GMT-3";
	private static final String GMTminus02 = "GMT-2";

	// Test valid Id
	@Test
	public void testValidCalendar() {
		assertTrue(TimeZoneUtils.isValidTimeZoneId(TimeZoneUtilsTest.EUROPE_MADRID_CALENDAR));
	}

	@Test
	public void testInValidCalendar() {
		assertFalse(TimeZoneUtils.isValidTimeZoneId(TimeZoneUtilsTest.INVALID_TIMEZONE));
	}

	@Test
	public void compareTimezoneOffsetDifferent() {
		Calendar date = getSummerDate(TimeZone.getTimeZone(EUROPE_PORTUGAL_CALENDAR));
		assertFalse(TimeZoneUtils.isEqualTimezoneOffset(date, TimeZone.getTimeZone(EUROPE_MADRID_CALENDAR)));
	}

	@Test
	public void compareTimezoneOffsetNoDayLight() {
		Calendar date = getSummerDate(TimeZone.getTimeZone(EUROPE_MADRID_CALENDAR));
		assertFalse(TimeZoneUtils.isEqualTimezoneOffset(date, TimeZone.getTimeZone(TIMEZONE_ETC_GMT_1)));
	}

	@Test
	public void compareTimezoneOffsetDifferentDatesNoDayLight() {
		Calendar date = getWinterDate(TimeZone.getTimeZone(EUROPE_MADRID_CALENDAR));
		assertTrue(TimeZoneUtils.isEqualTimezoneOffset(date, TimeZone.getTimeZone(TIMEZONE_ETC_GMT_1)));
	}

	@Test
	public void compareTimezoneOffsetDifferentWinter() {
		Calendar date = getWinterDate(TimeZone.getTimeZone(EUROPE_PORTUGAL_CALENDAR));
		assertFalse(TimeZoneUtils.isEqualTimezoneOffset(date, TimeZone.getTimeZone(TIMEZONE_ETC_GMT_1)));
	}

	@Test
	public void compareTimezoneOffsetEquals() {
		Calendar date = getSummerDate(TimeZone.getTimeZone(EUROPE_PORTUGAL_CALENDAR));
		assertTrue(TimeZoneUtils.isEqualTimezoneOffset(date, TimeZone.getTimeZone(EUROPE_LISBON_CALENDAR)));
	}

	@Test
	public void compareTimezoneOffsetEqualsWinter() {
		Calendar date = getWinterDate(TimeZone.getTimeZone(EUROPE_PORTUGAL_CALENDAR));
		assertTrue(TimeZoneUtils.isEqualTimezoneOffset(date, TimeZone.getTimeZone(EUROPE_LISBON_CALENDAR)));
	}

	@Test
	public void testGMTSpanishSummerTimeFromPortugalTimezone() {
		Calendar dateSummer = getSummerDate(TimeZone.getTimeZone(AMERICA_SAOPAULO_CALENDAR));
		String gmtTxt = TimeZoneUtils.getGMTOffsetFromAnotherTimezone(dateSummer, EUROPE_PORTUGAL_CALENDAR);
		assertEquals(
				"Spanish time in summer should has [" + TimeZoneUtilsTest.GMTplus01 + "] instead of [" + gmtTxt + "]",
				TimeZoneUtilsTest.GMTplus01, gmtTxt);
	}

	@Test
	public void testGMTSpanishWinterTime2() {
		Calendar datewinter = getSummerDate(TimeZone.getTimeZone(AMERICA_SAOPAULO_CALENDAR));
		String gmtTxt = TimeZoneUtils.getGMTOffsetFromAnotherTimezone(datewinter, EUROPE_MADRID_CALENDAR);
		assertEquals(
				"Spanish time in winter  should has [" + TimeZoneUtilsTest.GMTplus02 + "] instead of [" + gmtTxt + "]",
				TimeZoneUtilsTest.GMTplus02, gmtTxt);
	}

	@Test
	public void testGMTSpanishSummerTime() {
		Calendar dateSummer = getSummerDate(TimeZone.getTimeZone(EUROPE_MADRID_CALENDAR));
		String gmtTxt = TimeZoneUtils.getGMTOffset(dateSummer);
		assertEquals(
				"Spanish time in summer should has [" + TimeZoneUtilsTest.GMTplus02 + "] instead of [" + gmtTxt + "]",
				TimeZoneUtilsTest.GMTplus02, gmtTxt);
	}

	@Test
	public void testGMTSpanishWinterTime() {
		Calendar datewinter = getWinterDate(TimeZone.getTimeZone(EUROPE_MADRID_CALENDAR));
		String gmtTxt = TimeZoneUtils.getGMTOffset(datewinter);
		assertEquals(
				"Spanish time in winter  should has [" + TimeZoneUtilsTest.GMTplus01 + "] instead of [" + gmtTxt + "]",
				TimeZoneUtilsTest.GMTplus01, gmtTxt);
	}

	@Test
	public void testGMTSpanishSummerTimeDefault() {
		Calendar dateSummer = getSummerDate();
		String gmtTxt = TimeZoneUtils.getGMTOffset(dateSummer);
		assertEquals(
				"Spanish time in summer should has [" + TimeZoneUtilsTest.GMTplus02 + "] instead of [" + gmtTxt + "]",
				TimeZoneUtilsTest.GMTplus02, gmtTxt);
	}

	@Test
	public void testGMTSpanishWinterTimeDefault() {
		Calendar datewinter = getWinterDate();
		String gmtTxt = TimeZoneUtils.getGMTOffset(datewinter);
		assertEquals(
				"Spanish time in winter  should has [" + TimeZoneUtilsTest.GMTplus01 + "] instead of [" + gmtTxt + "]",
				TimeZoneUtilsTest.GMTplus01, gmtTxt);
	}

	@Test
	public void testGMTPortugalSummerTime() {
		Calendar dateSummer = getSummerDate(TimeZone.getTimeZone(EUROPE_LISBON_CALENDAR));
		String gmtTxt = TimeZoneUtils.getGMTOffset(dateSummer);
		assertEquals(
				"Spanish time in summer should has [" + TimeZoneUtilsTest.GMTplus01 + "] instead of [" + gmtTxt + "]",
				TimeZoneUtilsTest.GMTplus01, gmtTxt);
	}

	@Test
	public void testGMTPortugalWinterTime() {
		Calendar datewinter = getWinterDate(TimeZone.getTimeZone(EUROPE_LISBON_CALENDAR));
		String gmtTxt = TimeZoneUtils.getGMTOffset(datewinter);
		assertEquals("Spanish time in winter  should has [" + TimeZoneUtilsTest.GMTstandart + "] instead of [" + gmtTxt
				+ "]", TimeZoneUtilsTest.GMTstandart, gmtTxt);
	}

	@Test
	public void testGMTSaoPauloSummerTime() {
		Calendar dateSummer = getSummerDate(TimeZone.getTimeZone(AMERICA_SAOPAULO_CALENDAR));
		String gmtTxt = TimeZoneUtils.getGMTOffset(dateSummer);
		assertEquals(
				"Spanish time in summer should has [" + TimeZoneUtilsTest.GMTminus03 + "] instead of [" + gmtTxt + "]",
				TimeZoneUtilsTest.GMTminus03, gmtTxt);
	}

	@Test
	public void testGMTSaoPauloWinterTime() {
		Calendar datewinter = getWinterDate(TimeZone.getTimeZone(AMERICA_SAOPAULO_CALENDAR));
		String gmtTxt = TimeZoneUtils.getGMTOffset(datewinter);
		assertEquals(
				"Spanish time in winter  should has [" + TimeZoneUtilsTest.GMTminus02 + "] instead of [" + gmtTxt + "]",
				TimeZoneUtilsTest.GMTminus03, gmtTxt);
	}

	@Test
	public void testTransformSpanishTimeIntoPortugueseTimeSummer() {
		Calendar summerDateSpain = getSummerDate(TimeZone.getTimeZone(EUROPE_MADRID_CALENDAR));
		Calendar endDatePortugal = TimeZoneUtils.transformToTimeZone(summerDateSpain, EUROPE_LISBON_CALENDAR);
		assertEquals(summerDateSpain.get(Calendar.HOUR_OF_DAY), endDatePortugal.get(Calendar.HOUR_OF_DAY) + 1);
	}

	@Test
	public void testTransformPortugueseTimeIntoSpanishTimeSummer() {
		Calendar summerDatePortugal = getSummerDate(TimeZone.getTimeZone(EUROPE_MADRID_CALENDAR));
		Calendar endDateSpain = TimeZoneUtils.transformToTimeZone(summerDatePortugal, EUROPE_LISBON_CALENDAR);
		assertEquals(summerDatePortugal.get(Calendar.HOUR_OF_DAY), endDateSpain.get(Calendar.HOUR_OF_DAY) + 1);
	}

	@Test
	public void testTransformSpanishTimeIntoPortugueseTimeWinter() {
		Calendar summerDateSpain = getWinterDate(TimeZone.getTimeZone(EUROPE_MADRID_CALENDAR));
		Calendar endDatePortugal = TimeZoneUtils.transformToTimeZone(summerDateSpain, EUROPE_LISBON_CALENDAR);
		assertEquals(summerDateSpain.get(Calendar.HOUR_OF_DAY), endDatePortugal.get(Calendar.HOUR_OF_DAY) + 1);
	}

	@Test
	public void testTransformPortugueseTimeIntoSpanishTimeWinter() {
		Calendar summerDatePortugal = getWinterDate(TimeZone.getTimeZone(EUROPE_MADRID_CALENDAR));
		Calendar endDateSpain = TimeZoneUtils.transformToTimeZone(summerDatePortugal, EUROPE_LISBON_CALENDAR);
		assertEquals(summerDatePortugal.get(Calendar.HOUR_OF_DAY), endDateSpain.get(Calendar.HOUR_OF_DAY) + 1);
	}

	private static Calendar getSummerDate() {
		return getSummerDate(TimeZone.getDefault());
	}

	private static Calendar getSummerDate(TimeZone actualTimezone) {
		Calendar dateSummer = Calendar.getInstance(actualTimezone);
		dateSummer.set(Calendar.HOUR_OF_DAY, 10);
		dateSummer.set(Calendar.DAY_OF_MONTH, 15);
		dateSummer.set(Calendar.MONTH, 6);
		return dateSummer;
	}

	private static Calendar getWinterDate() {
		return getWinterDate(TimeZone.getDefault());
	}

	private static Calendar getWinterDate(TimeZone actualTimezone) {
		Calendar datewinter = Calendar.getInstance(actualTimezone);
		datewinter.set(Calendar.HOUR_OF_DAY, 10); // 0..23
		datewinter.set(Calendar.DAY_OF_MONTH, 15);
		datewinter.set(Calendar.MONTH, 11);
		return datewinter;
	}

}