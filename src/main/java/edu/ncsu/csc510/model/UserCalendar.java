package edu.ncsu.csc510.model;

/**
 * Created by Jessica on 3/31/2015.
 */
public class UserCalendar {
    private String userId;
    private String calendarId;

    public UserCalendar(String userId, String calendarId) {
        this.calendarId = calendarId;
        this.userId = userId;
    }

    public String getCalendarId() {
        return calendarId;
    }

    public void setCalendarId(String calendarId) {
        this.calendarId = calendarId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}
