package com.test.service.module.one;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class TimeZoneUtils {
	private static final int HOURS_IN_MILISECONDS = 3600000;
	private static final String WORLD_DEFAULT ="Etc/UCT";
	private static final String BUSSINES_DEFAULT ="Europe/Madrid";
	
	/**
	 * Updates the actual time to the selected timezone
	 * */
	public static boolean isEqualTimezoneOffset(final Calendar actualTime,final  TimeZone timeZone){
		return actualTime.getTimeZone().getOffset(actualTime.getTimeInMillis()) == timeZone.getOffset(actualTime.getTimeInMillis());
	}
	
	/**
	 * Updates the actual time to the selected timezone
	 * */
	public static Calendar transformToTimeZone(final Calendar actualTime,final  String timeZone){
		return transformToTimeZone(actualTime , TimeZone.getTimeZone(timeZone));
	}
	
	/**
	 * Updates the actual time to the selected timezone
	 * */
	public static Calendar transformToTimeZone(final Calendar actualTime, final TimeZone timeZone){
		Calendar destinationCalendar = new GregorianCalendar(timeZone );
		destinationCalendar.setTimeInMillis(actualTime.getTimeInMillis());
		return destinationCalendar;
	}
	
	/**
	 * Updates the actual time to the selected timezone
	 * */
	public static Calendar transformToTimeZone(final Calendar actualTime){
		Calendar destinationCalendar = new GregorianCalendar(TimeZone.getDefault());
		destinationCalendar.setTimeInMillis(actualTime.getTimeInMillis());
		return destinationCalendar;
	}
	
	
	/**
	 * Changes common calendar to UTC 
	 * */
	public static Calendar transformToUTC(final Calendar actualTime){
		return transformToTimeZone(actualTime,WORLD_DEFAULT);
	}

	/**
	 * Changes common calendar to UTC 
	 * */
	public static Calendar transformToSpanish(final Calendar actualTime){
		return transformToTimeZone(actualTime,BUSSINES_DEFAULT);
	}
	
	/**
	 * Checks if the actual timezone is included on the list
	 * @param id
	 * @return
	 */
	public static boolean isValidTimeZoneId(final String id){
		TimeZone dataToPrint  =  TimeZone.getTimeZone(id);
		final boolean rsl = id.equalsIgnoreCase(dataToPrint.getID());
		return rsl;
	}
	
	/**
	 * Calculate the actual offset in order to determine the GMT difference
	 * @param actualTime
	 * @param actualTimeZone
	 * @return
	 */
	public static  String getGMTOffset (final Calendar actualTime){
		String rsl = null;
		//Retrieve the actual offset
		int offset = actualTime.getTimeZone().getOffset(actualTime.getTimeInMillis())/HOURS_IN_MILISECONDS;
		//Prepare final string
		if(offset<0){
			rsl = "GMT"+offset;
		}else{
			rsl = "GMT+"+offset;
		}
		return rsl; 
	}
	
	/**
	 * Calculate the actual offset in order to determine the GMT difference
	 * @param actualTime
	 * @param actualTimeZone
	 * @return
	 */
	public static  String getGMTOffsetFromAnotherTimezone (final Calendar actualTime, final String destinationTimezone){
		Calendar destinationCalendar = new GregorianCalendar(TimeZone.getTimeZone(destinationTimezone));
		destinationCalendar.setTimeInMillis(actualTime.getTimeInMillis());
		return getGMTOffset (destinationCalendar);
	}
	
}
