package edu.ncsu.csc510.dao;

import edu.ncsu.csc510.model.UserCalendar;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jessica on 3/31/2015.
 */
public class UserCalendarDAO {

    // comment out below line for local environment
    //private static final String jdbcURL = "jdbc:mysql://127.12.104.130:3306/cal";

    // uncomment below line for local environment
    private static final String jdbcURL = "jdbc:mysql://127.0.0.1:3306/cal";



    private static final String user = "adminCU6MEuD";
    private static final String password = "xhcLl9QNsZwA";


    public static List<UserCalendar> getUserCalendars(String userId)
    {
        List<UserCalendar> calendars = new ArrayList<UserCalendar>();
        Connection connection = null;
        Statement statement = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcURL, user, password);
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * from user_calendar where userId = '"+userId+"'");
            while (rs.next()) {
                String userid = rs.getString("userId");
                String calId = rs.getString("calendarId");
                System.out.println(userid+" "+calId);
                calendars.add(new UserCalendar(userid,calId));
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }


        return calendars;
    }

    public static void addUserCalendars(List<UserCalendar> userCalendarList)
    {
        Connection connection = null;
        Statement statement = null;


        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcURL, user, password);
            statement = connection.createStatement();

            if (userCalendarList.size() >= 1) {
                String delete = "DELETE FROM user_calendar WHERE  userId = '" + userCalendarList.get(1).getUserId() + "'";
                System.out.println(delete);
                statement.executeUpdate(delete);

                for (UserCalendar uc : userCalendarList) {
                    String insert = "INSERT INTO `user_calendar`(`userId`, `calendarId`) VALUES ('" + uc.getUserId() + "','" + uc.getCalendarId() + "')";
                    System.out.println(insert);
                    statement.executeUpdate(insert);
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    }



}
