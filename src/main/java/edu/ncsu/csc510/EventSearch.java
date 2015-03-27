package edu.ncsu.csc510;

import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.DataStoreFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
//import com.google.api.services.calendar.Calendar.Events.List;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.CalendarList;
import com.google.api.services.calendar.model.CalendarListEntry;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;

/**
 * @author gargi pingale
 */
public class EventSearch {

  private static final String APPLICATION_NAME = "MyWolfPack Event Navigator";

  /** Directory to store user credentials. */
  private static final java.io.File DATA_STORE_DIR =
      new java.io.File(System.getProperty("user.home"), ".store/calendar_sample");

  /**
   * Global instance of the {@link DataStoreFactory}. The best practice is to make it a single
   * globally shared instance across your application.
   */
  private static FileDataStoreFactory dataStoreFactory;
  
  /** Global instance of the HTTP transport. */
  private static HttpTransport httpTransport;

  /** Global instance of the JSON factory. */
  private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

  private static com.google.api.services.calendar.Calendar client;

  /** Authorizes the installed application to access user's protected data. */
  private static Credential authorize() throws Exception {
    // load client secrets
    GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY,
        new InputStreamReader(EventSearch.class.getResourceAsStream("/client_secrets.json")));
    if (clientSecrets.getDetails().getClientId().startsWith("Enter")
        || clientSecrets.getDetails().getClientSecret().startsWith("Enter ")) {
      System.out.println(
          "Enter Client ID and Secret from https://code.google.com/apis/console/?api=calendar "
          + "into calendar-cmdline-sample/src/main/resources/client_secrets.json");
      System.exit(1);
    }
    // set up authorization code flow
    GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
        httpTransport, JSON_FACTORY, clientSecrets,
        Collections.singleton(CalendarScopes.CALENDAR)).setDataStoreFactory(dataStoreFactory)
        .build();
    // authorize
    return new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
  }

  public static Map<String,List<Event>> search() {
    try {
      //stores the result	
    	Map<String,List<Event>> map = new HashMap<String,List<Event>>();
        
      // initialize the transport
      httpTransport = GoogleNetHttpTransport.newTrustedTransport();

      // initialize the data store factory
      dataStoreFactory = new FileDataStoreFactory(DATA_STORE_DIR);

      // authorization
      Credential credential = authorize();

      // set up global Calendar instance
      client = new com.google.api.services.calendar.Calendar.Builder(
          httpTransport, JSON_FACTORY, credential).setApplicationName(APPLICATION_NAME).build();
      
      List<String> calList = new ArrayList<String>();
      calList.add("en.usa#holiday@group.v.calendar.google.com");
      calList.add("ncsu.edu_hpasl5cmtenq7biv0omve1nvq8@group.calendar.google.com");
      
      String qStr = "alex";
      String sdt = "11/12/2012";  //   dd/MM/yyyy
      String edt = null;
      
      map = searchCalendars(calList,qStr,sdt,edt);
      
      System.out.println();
      System.out.println("Progam Ends");

      return (map);
    } catch (IOException e) {
      System.err.println(e.getMessage());
    } catch (Throwable t) {
      t.printStackTrace();
    }
	return null;
  }
  
  private static CalendarList getClientCalendars() throws IOException{
    //View.header("Search Calendars");
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
   * @param calList : List of Calendar Ids as String
   * @param qStr : free text query string
   * @param sdt : start date time input field : string in format : dd/MM/yyyy
   * @param edt : end date time input field : string in format : dd/MM/yyyy
   * @return
   * @throws IOException
   * @throws ParseException 
   */
  private static Map<String,List<Event>> searchCalendars(List<String> calList,String qStr,
             String sdt,String edt) throws IOException, ParseException{
    
    Map<String,List<Event>> map = new HashMap<String,List<Event>>();
   
    if(qStr == null && sdt == null && edt == null){
      System.out.println("There must be atleast one search term");
      return null;
    }
    if(calList != null){
			      SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			      com.google.api.client.util.DateTime timeMax = null;
			      com.google.api.client.util.DateTime timeMin = null;
			      String q = null;
			      
			      for(String cal : calList){
			       System.out.println("Calendar ID: " + cal);
			       if(qStr != null && !qStr.isEmpty()){
			         q = qStr;
			       }
			       if(sdt != null && !sdt.isEmpty()){
			    	 Date sdate = formatter.parse(sdt);
					 String sdateStr = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX").format(sdate);
			         timeMin = new com.google.api.client.util.DateTime(sdateStr);
			       }
			       if(edt != null && !edt.isEmpty()){
			    	 Date edate = formatter.parse(edt);
					 String edateStr = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX").format(edate);
			         timeMax = new com.google.api.client.util.DateTime(edateStr);
			       }
			       Events events = client.events().list(cal).setQ(q).setTimeMax(timeMax).setTimeMin(timeMin).execute();
			       map.put(cal,events.getItems());
     }
   }
	return map;
  }
  
  private static void searchCalendars() throws IOException {
    CalendarList feed = client.calendarList().list().execute();
    if (feed.getItems() != null) {
      for (CalendarListEntry entry : feed.getItems()) {
        System.out.println("ID: " + entry.getId());
        Events events = client.events().list(entry.getId()).setQ("alex").setTimeMax(null).setTimeMin(null).execute();
      }
    }
  }
}
