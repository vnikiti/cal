package edu.ncsu.csc510.dao;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.CalendarList;
import com.google.api.services.calendar.model.CalendarListEntry;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;

/**
 * @author gargi pingale
 */
public class EventDAO {

	private static final String APPLICATION_NAME = "MyWolfPackNavigator";
	private static final String SERVICE_ACCOUNT_EMAIL = "794973057266-v3f47pm6b2jjv5de2publs4ldfnhnu11@developer.gserviceaccount.com";
	private static final String P12_KEY_FILE = "MyWolfPackNavigator-4dc9f8e58780.p12";

	/** Global instance of the HTTP transport. */
	private static HttpTransport httpTransport;

	/** Global instance of the JSON factory. */
	private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

	private static com.google.api.services.calendar.Calendar client;

	/** Authorizes the installed application to access user's protected data. */
	private static Credential authorize() throws Exception {

		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		URL url = loader.getResource(P12_KEY_FILE);
		File p12File = new File(url.getPath());

		HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
		GoogleCredential credential = new GoogleCredential.Builder().setTransport(httpTransport)
		        .setJsonFactory(JSON_FACTORY).setServiceAccountId(SERVICE_ACCOUNT_EMAIL)
		        .setServiceAccountPrivateKeyFromP12File(p12File)
		        .setServiceAccountScopes(Collections.singleton(CalendarScopes.CALENDAR)).build();

		return credential;
	}

	public static Map<String, List<Event>> search(String query) {
		try {
			// stores the result
			Map<String, List<Event>> map = new HashMap<String, List<Event>>();

			// initialize the transport
			httpTransport = GoogleNetHttpTransport.newTrustedTransport();

			// authorization
			Credential credential = authorize();

			// set up global Calendar instance
			client = new Calendar.Builder(httpTransport, JSON_FACTORY, credential).setApplicationName(APPLICATION_NAME)
			        .build();

			List<String> calList = new ArrayList<String>();
			calList.add("ncsu.edu_hpasl5cmtenq7biv0omve1nvq8@group.calendar.google.com"); // CSC
			                                                                              // Calendar
			calList.add("ncsu.edu_iv41gou4edva6l3sejfg9mjo2k@group.calendar.google.com"); // CCEE
			                                                                              // Student
			                                                                              // Organization
			calList.add("ncsu.edu_vd4gv8ter4klr9sa6efm5vmsq0@group.calendar.google.com"); // Physics
			                                                                              // Department
			calList.add("ncsu.edu_507c8794r25bnebhjrrh3i5c4s@group.calendar.google.com"); // Academic
			                                                                              // Calendar

			String sdt = "11/12/2012"; // dd/MM/yyyy
			String edt = null;

			map = searchCalendars(calList, query, sdt, edt);

			return (map);
		} catch (IOException e) {
			System.err.println(e.getMessage());
		} catch (Throwable t) {
			t.printStackTrace();
		}
		return null;
	}

	private static CalendarList getClientCalendars() throws IOException {
		// View.header("Search Calendars");
		CalendarList feed = client.calendarList().list().execute();
		if (feed.getItems() != null) {
			for (CalendarListEntry entry : feed.getItems()) {
				System.out.println("ID: " + entry.getId());
			}
		}
		return feed;
	}

	/**
	 * 
	 * @param calList
	 *            : List of Calendar Ids as String
	 * @param qStr
	 *            : free text query string
	 * @param sdt
	 *            : start date time input field : string in format : dd/MM/yyyy
	 * @param edt
	 *            : end date time input field : string in format : dd/MM/yyyy
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	private static Map<String, List<Event>> searchCalendars(List<String> calList, String qStr, String sdt, String edt)
	        throws IOException, ParseException {

		Map<String, List<Event>> map = new HashMap<String, List<Event>>();

		if (qStr == null && sdt == null && edt == null) {
			System.out.println("There must be atleast one search term");
			return null;
		}
		if (calList != null) {
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			com.google.api.client.util.DateTime timeMax = null;
			com.google.api.client.util.DateTime timeMin = null;
			String q = null;

			for (String cal : calList) {
				System.out.println("Calendar ID: " + cal);
				if (qStr != null && !qStr.isEmpty()) {
					q = qStr;
				}
				if (sdt != null && !sdt.isEmpty()) {
					Date sdate = formatter.parse(sdt);
					String sdateStr = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX").format(sdate);
					timeMin = new com.google.api.client.util.DateTime(sdateStr);
				}
				if (edt != null && !edt.isEmpty()) {
					Date edate = formatter.parse(edt);
					String edateStr = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX").format(edate);
					timeMax = new com.google.api.client.util.DateTime(edateStr);
				}
				Events events = client.events().list(cal).setQ(q).setTimeMax(timeMax).setTimeMin(timeMin).execute();
				map.put(cal, events.getItems());
			}
		}
		return map;
	}

	private static void searchCalendars() throws IOException {
		CalendarList feed = client.calendarList().list().execute();
		if (feed.getItems() != null) {
			for (CalendarListEntry entry : feed.getItems()) {
				System.out.println("ID: " + entry.getId());
				Events events = client.events().list(entry.getId()).setQ("alex").setTimeMax(null).setTimeMin(null)
				        .execute();
			}
		}
	}
}