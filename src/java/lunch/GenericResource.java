/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lunch;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.JsonArray;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * REST Web Service
 *
 * @author Anubhav
 */
@Path("application")
public class GenericResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of GenericResource
     */
    public GenericResource() {
    }

    /**
     * Retrieves representation of an instance of lunch.GenericResource
     *
     * @return an instance of java.lang.String
     */
    Connectionclass conclass = new Connectionclass();
    Statement stm;

    //defining and declaring a JSon object
    JSONObject singledata = new JSONObject();
    JSONObject singlewrong = new JSONObject();
    int number = 0;

    Date date = new Date();

    java.sql.Timestamp sq = new java.sql.Timestamp(date.getTime());

//    SimpleDateFormat sf = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
    @GET
    @Path("registration&{firstName}&{lastName}&{email}&{password}&{phonenumber}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson(@PathParam("firstName") String fn, @PathParam("lastName") String ln, @PathParam("email") String email,
            @PathParam("password") String pass, @PathParam("phonenumber") String pNumber) throws SQLException {

        System.out.println("INSERT INTO USERS VALUES(111," + "'" + fn + "'" + "," + "'" + ln + "'" + "," + "'" + email + "'" + "," + "'" + pass + "'" + "," + "'" + pNumber + "'" + ")");
        try {
            stm = conclass.createConnection();
            ResultSet rs = stm.executeQuery("SELECT USER_ID FROM USERS order by USER_ID DESC");
            rs.next();
            int userid = rs.getInt("USER_ID");
            System.out.println("USERID OF THE USER IS " + userid);
            userid = ++userid;
            number = stm.executeUpdate("INSERT INTO USERS VALUES(+" + userid + "," + "'" + fn + "'" + "," + "'" + ln + "'" + "," + "'" + email + "'" + "," + "'" + pNumber + "'" + "," + "'" + pass + "'" + ")");
            System.out.println("total inserted rows" + number);

            if (number == 1) {
                singledata.accumulate("Status", "OK");
                singledata.accumulate("Timestamp", sq.toInstant().toEpochMilli());
                singledata.accumulate("Active", true);
                singledata.accumulate("User_id", userid);
                singledata.accumulate("Message", "You are successfully Register");

            } else if (number == 0) {

                singledata.accumulate("Status", "WRONG");
                singledata.accumulate("Timestamp", sq.toInstant().toEpochMilli());
                singledata.accumulate("Message", "You Entered the wrong details");
            }
            stm.close();
        } catch (SQLException ex) {
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return singledata.toString();
    }

    @GET
    @Path("Login&{email}&{password}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson(@PathParam("email") String email, @PathParam("password") String pass) {

        stm = conclass.createConnection();

        try {
            System.out.println("select * from USERS WHERE EMAIL=" + "'" + email + "'" + " and PASSWORD=" + "'" + pass + "'");
            ResultSet rs = stm.executeQuery("select * from USERS WHERE EMAIL=" + "'" + email + "'" + " AND PASSWORD=" + "'" + pass + "'");

            String fName, lName, contactnumber, userpassword, dateOfbirth;

            int user_Id = 0;

            while (rs.next()) {

                System.out.println("yaaehhhhhhhhhhhhhhhhhhhhhhn........................................");
                fName = rs.getString("FIRSTNAME");
                lName = rs.getString("LASTNAME");
                email = rs.getString("EMAIL");
                contactnumber = rs.getString("CONTACTNUMBER");
                userpassword = rs.getString("PASSWORD");
                user_Id = rs.getInt("USER_ID");
                System.out.println("username is " + fName);
                if (user_Id != 0) {

                    singledata.accumulate("Status", "OK");
                    singledata.accumulate("Timestamp", sq.toInstant().toEpochMilli());
                    singledata.accumulate("Active", true);
                    singledata.accumulate("User_id", user_Id);
                }

            }
            if (user_Id == 0) {
                singledata.accumulate("Status", "WRONG");
                singledata.accumulate("Timestamp", sq.toInstant().toEpochMilli());
                singledata.accumulate("MESSAGE", "YOUR ENTERED THE WRONG INFORMATION");
            }
            rs.close();
            stm.close();
        } catch (SQLException ex) {
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);
        }

        return singledata.toString();
    }

    @GET
    @Path("myprofile&{userid}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson(@PathParam("userid") int u_id) {

        stm = conclass.createConnection();
        try {
            System.out.println("select * from USERS WHERE USER_ID=" + u_id);
            ResultSet rs = stm.executeQuery("select * from USERS WHERE USER_ID=" + u_id);

            String fName, lName, email, contactnumber, userpassword, username;

            int user_Id = 0;

            while (rs.next()) {
                System.out.println("rs have values........................");
                fName = rs.getString("FIRSTNAME");
                lName = rs.getString("LASTNAME");
                email = rs.getString("EMAIL");
                contactnumber = rs.getString("CONTACTNUMBER");
                userpassword = rs.getString("PASSWORD");
                user_Id = rs.getInt("USER_ID");
                System.out.println("username is " + fName);

                singledata.accumulate("Status", "OK");
                singledata.accumulate("TIMESTAMP", sq.toInstant().toEpochMilli());
                singledata.accumulate("firstname", fName);
                singledata.accumulate("lastname", lName);
                singledata.accumulate("email", email);
                singledata.accumulate("contactnumber", contactnumber);
                singledata.accumulate("Password", userpassword);
                singledata.accumulate("user_id", user_Id);

            }
            if (user_Id == 0) {
                singledata.accumulate("Status", "OK");
                singledata.accumulate("TIMESTAMP", sq.toInstant().toEpochMilli());
                singledata.accumulate("MESSAGE", "something wrong in the userID");
            }

            rs.close();
            stm.close();

        } catch (SQLException ex) {
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);
        }

        return singledata.toString();
    }

    @GET
    @Path("PostInfo&{Place}&{NumberofPerson}&{Budget}&{CuisineType}&{StartTime}&{EndTime}&{User_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson(@PathParam("Place") String place, @PathParam("NumberofPerson") int numberofperson, @PathParam("Budget") double budget, @PathParam("CuisineType") String cuisinetype,
            @PathParam("StartTime") String starttime, @PathParam("EndTime") String endtime, @PathParam("User_id") int userid) throws SQLException {

        try {
            stm = conclass.createConnection();

            ResultSet rs = stm.executeQuery("SELECT POST_ID FROM POST_ADD ORDER BY POST_ID DESC");
            rs.next();
            int postid = rs.getInt("POST_ID");
            System.out.println("POSTID OF THE POST IS " + postid);
            postid = ++postid;

            System.out.println("INSERT INTO POST_ADD VALUES(" + postid + "," + place + "," + numberofperson + "," + budget + "," + cuisinetype + "," + starttime + "," + endtime + "," + userid + ")");
            number = stm.executeUpdate("INSERT INTO POST_ADD VALUES(" + postid + "," + place + "," + numberofperson + "," + budget + "," + cuisinetype + "," + starttime + "," + endtime + "," + userid + ")");
            System.out.println("total inserted rows" + number);

            if (number == 1) {
                singledata.accumulate("STATUS", "OK");
                singledata.accumulate("TIMESTAMP", sq.toInstant().toEpochMilli());
                singledata.accumulate("Post_id", postid);
                singledata.accumulate("Message", "Your add is successfully Post");
            } else {

                singledata.accumulate("STATUS", "WRONG");
                singledata.accumulate("TIMESTAMP", sq.toInstant().toEpochMilli());
                singledata.accumulate("Message", "Your "
                        + "entered information is wrong");
            }
            stm.close();
        } catch (SQLException ex) {
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);

        }
        return singledata.toString();
    }

    @GET
    @Path("AddEvent&{EventName}&{PlaceofEvent}&{startTime}&{EndTime}&{user_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson(@PathParam("EventName") String eventname, @PathParam("PlaceofEvent") String placeofevent, @PathParam("startTime") String starttime, @PathParam("EndTime") String endtime,
            @PathParam("user_id") int user_id) throws SQLException {

        try {
            stm = conclass.createConnection();
            ResultSet rs = stm.executeQuery("SELECT EVENTID FROM EVENTS ORDER BY EVENTID DESC ");
            rs.next();
            int event_id = rs.getInt("EVENTID");
            event_id = ++event_id;
            String query = (event_id + "," + "'" + eventname + "'" + "," + "'" + placeofevent + "'" + "," + "'" + starttime + "'" + "," + "'" + endtime + "'" + "," + user_id + ")");

            System.out.println("INSERT INTO EVENTS VALUES(" + query);
            number = stm.executeUpdate("INSERT INTO EVENTS VALUES(" + query);
            System.out.println("total inserted rows" + number);

            if (number == 1) {
                singledata.accumulate("Status", "OK");
                singledata.accumulate("Timestamp", sq.toInstant().toEpochMilli());
                singledata.accumulate("Event_id", event_id);
                singledata.accumulate("Message", "Event is succesfully added");
            } else {
                singledata.accumulate("Status", "OK");
                singledata.accumulate("Timestamp", sq.toInstant().toEpochMilli());
                singledata.accumulate("Message", "Event is succesfully added");
            }
            stm.close();
        } catch (SQLException ex) {
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);

        }
        return singledata.toString();
    }

    @GET
    @Path("ADDPhotos&{Photobase64code}&{Event_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson(@PathParam("Photobase64code") String code, @PathParam("Event_id") int eventid) throws SQLException {
        int photo_id = 1001;
        String query = (photo_id + "," + eventid + code + ")");
        try {
            stm = conclass.createConnection();
            number = stm.executeUpdate("INSERT INTO PHOTOS VALUES" + query);
            System.out.println("total inserted rows" + number);

            if (number == 1) {
                singledata.accumulate("STATUS", "OK");
                singledata.accumulate("TIMESTAMP", sq.toInstant().toEpochMilli());
                singledata.accumulate("Photo_ID", photo_id);
                singledata.accumulate("Message", "successfully uploaded");
            } else {
                singledata.accumulate("STATUS", "WRONG");
                singledata.accumulate("TIMESTAMP", sq.toInstant().toEpochMilli());
                singledata.accumulate("Message", "INFORMATION IS WRONG");
            }
            stm.close();
        } catch (SQLException ex) {
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return singledata.toString();
    }

    @GET
    @Path("RemoveUser&{Email}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson(@PathParam("Email") String email) throws SQLException {

        try {
            stm = conclass.createConnection();
            System.out.println("DELETE FROM USERS WHERE email=" + email);
            number = stm.executeUpdate("DELETE FROM USERS WHERE email=" + email);
            System.out.println("total Deleted rows" + number);
            singledata.accumulate("Status", "OK");
            singledata.accumulate("Timestamp", sq.toInstant().toEpochMilli());
            singledata.accumulate("Message", "User is successfulkly removed");
            stm.close();
        } catch (SQLException ex) {
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);

        }
        return singledata.toString();
    }

    @GET
    @Path("RemoveEvent&{EventName}&{EventPlace}&{StartDate}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson(@PathParam("EventName") String name, @PathParam("EventPlace") String place, @PathParam("StartDate") String startdate) throws SQLException {

        try {
            stm = conclass.createConnection();
            System.out.println("DELETE FROM EVENTS WHERE EVENTNAME='" + name + "' AND PLACEOFEVENT='" + place + "' AND STARTTIME='" + startdate + "'");
            number = stm.executeUpdate("DELETE FROM EVENTS WHERE EVENTNAME='" + name + "' AND PLACEOFEVENT='" + place + "' AND STARTTIME='" + startdate + "'");
            System.out.println("total Deleted rows" + number);

            stm.close();
        } catch (SQLException ex) {
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }

    @GET
    @Path("SendInvitation&{SharingTime}&{User_id}&{RecieverUserid}&{Post_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson(@PathParam("SharingTime") String sharetime, @PathParam("User_id") int userid, @PathParam("RecieverUserid") int recieverid, @PathParam("Post_id") int postid) throws SQLException {

        try {
            stm = conclass.createConnection();
            System.out.println("INSERT INTO SHARINGINVITATION VALUES(" + "'" + sharetime + "'" + "," + userid + "," + recieverid + "," + postid + ")");
            number = stm.executeUpdate("INSERT INTO SHARINGINVITATION VALUES(" + "'" + sharetime + "'" + "," + userid + "," + recieverid + "," + postid + ")");
            System.out.println("total Inserted rows" + number);

            if (number == 1) {
                singledata.accumulate("Status", "OK");
                singledata.accumulate("TimeStamp", sq.toInstant().toEpochMilli());
                singledata.accumulate("Message", "Invitation Sent Successfully");
            } else {
                singledata.accumulate("Status", "OK");
                singledata.accumulate("TimeStamp", sq.toInstant().toEpochMilli());
                singledata.accumulate("Message", "the user_id is no more available");
            }
            stm.close();
        } catch (SQLException ex) {
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);

        }
        return singledata.toString();
    }

    @GET
    @Path("RecieveInvitation&{InvitationStatus}&{StatusTime}&{User_id}&{Reciever_id}&{post_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson(@PathParam("InvitationStatus") String invitationstatus, @PathParam("StatusTime") String time, @PathParam("User_id") int userid, @PathParam("Reciever_id") int recieverid, @PathParam("post_id") int postId) throws SQLException {

        try {
            stm = conclass.createConnection();
            System.out.println("INSERT INTO INVITATION VALUES(" + "'" + invitationstatus + "'" + ",'" + time + "'," + userid + "," + recieverid + "," + postId + ")");
            number = stm.executeUpdate("INSERT INTO INVITATION VALUES(" + "'" + invitationstatus + "','" + time + "'," + userid + "," + recieverid + "," + postId + ")");
            System.out.println("total Inserted rows" + number);

            if (number == 1) {
                singledata.accumulate("Status", "OK");
                singledata.accumulate("TimeStamp", sq.toInstant().toEpochMilli());
                singledata.accumulate("Message", "Invitation Rejected");
            } else {
                singledata.accumulate("Status", "WRONG");
                singledata.accumulate("TimeStamp", sq.toInstant().toEpochMilli());
                singledata.accumulate("Message", "EXPIRED POST");

            }
            stm.close();
        } catch (SQLException ex) {
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);

        }
        return singledata.toString();
    }

    @GET
    @Path("SendMessage&{Message}&{Sender_id}&{Reciever_id}&{SendDate}")
    @Produces(MediaType.APPLICATION_JSON)
    public String sendMessagegetJson(@PathParam("Message") String message, @PathParam("Sender_id") int senderid, @PathParam("Reciever_id") int receiverid,
            @PathParam("SendDate") String senddatetime) throws SQLException {
        int messageid = 100001;
        try {
            stm = conclass.createConnection();
            System.out.println("INSERT INTO MESSAGE VALUES(" + messageid + ",'" + message + "'" + "," + senderid + "," + receiverid + "," + "'" + senddatetime + "'" + ")");
            number = stm.executeUpdate("INSERT INTO MESSAGE VALUES(" + messageid + ",'" + message + "'" + "," + senderid + "," + receiverid + "," + "'" + senddatetime + "'" + ")");
            System.out.println("total Inserted rows" + number);

            if (number == 1) {
                singledata.accumulate("Status", "OK");
                singledata.accumulate("Timestamp", sq.toInstant().toEpochMilli());
                singledata.accumulate("Message", "Message Sent");
            } else {

                singledata.accumulate("Status", "OK");
                singledata.accumulate("Timestamp", sq.toInstant().toEpochMilli());
                singledata.accumulate("Message", "EXPIRED POST");
            }
            stm.close();
        } catch (SQLException ex) {
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);

        }
        return singledata.toString();
    }

    @GET
    @Path("RecieveMessage&{Reciever_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String RecieveMessagegetJson(@PathParam("Reciever_id") int recieverid) throws SQLException {
        String message = null, date = null;
        int user_id = 0, message_id, messageid;
        try {
            stm = conclass.createConnection();
            System.out.println("select * from Message WHERE RECIEVER_ID=" + recieverid);
            ResultSet rs = stm.executeQuery("select * from Message WHERE RECIEVER_ID=" + recieverid);
            while (rs.next()) {

                message = rs.getString("MESSAGE");
                user_id = rs.getInt("SENDER_ID");
                date = rs.getString("MessageTime");
                message_id = rs.getInt("MESSAGE_ID");

                singledata.accumulate("Status", "OK");
                singledata.accumulate("Timestamp", sq.toInstant().toEpochMilli());
                singledata.accumulate("Send_id", user_id);
                singledata.accumulate("date", date);
                singledata.accumulate("Message", message);
            }
            if (user_id != 0) {

                singledata.accumulate("Status", "OK");
                singledata.accumulate("Timestamp", sq.toInstant().toEpochMilli());
                singledata.accumulate("Message", "EXPIRED POST");
            }
            stm.close();
        } catch (SQLException ex) {
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return singledata.toString();
    }

    JSONArray multipledata = new JSONArray();

    @GET
    @Path("viewpost")
    @Produces(MediaType.APPLICATION_JSON)
    public String viewpostgetJson() throws SQLException {
        String place = null, cuisinetype = null, startTime = null, endTime = null;
        int post_id = 0, numberOfperson = 0, user_id = 0;
        double budget = 0;

        try {
            stm = conclass.createConnection();
            System.out.println("select * from POST_ADD");
            ResultSet rs = stm.executeQuery("select * from POST_ADD");
            while (rs.next()) {
                place = rs.getString("PLACE");
                cuisinetype = rs.getString("CUISINETYPE");
                startTime = rs.getDate("STARTTIME").toString();
                endTime = rs.getDate("ENDTIME").toString();
                budget = rs.getDouble("BUDGET");
                numberOfperson = rs.getInt("NUMBEROFPERSON");
                post_id = rs.getInt("POST_ID");
                user_id = rs.getInt("USER_ID");

                singledata.accumulate("Status", "OK");
                singledata.accumulate("Timestamp", sq.toInstant().toEpochMilli());
                singledata.accumulate("PLACE", place);
                singledata.accumulate("NUMBEROFPERSON", numberOfperson);
                singledata.accumulate("CUSINETYPE", cuisinetype);
                singledata.accumulate("STARTTIME", startTime);
                singledata.accumulate("ENDTIME", endTime);
                singledata.accumulate("USER_ID", user_id);
                singledata.accumulate("POST_ID", post_id);
                singledata.accumulate("BUDGET", budget);
                multipledata.add(singledata);
                singledata.clear();
            }
            stm.close();
        } catch (SQLException ex) {
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return multipledata.toString();
    }

    @GET
    @Path("viewpost&{Post_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String viewsinglepostgetJson(@PathParam("Post_id") int postid) throws SQLException {
        String place = null, cuisinetype = null, startTime = null, endTime = null;
        int numberOfperson = 0, user_id = 0;
        double budget = 0;

        try {
            stm = conclass.createConnection();
            System.out.println("select * from POST_ADD WHERE POST_ID=" + postid);
            ResultSet rs = stm.executeQuery("select * from POST_ADD");
            while (rs.next()) {
                place = rs.getString("PLACE");
                cuisinetype = rs.getString("CUISINETYPE");
                startTime = rs.getDate("STARTTIME").toString();
                endTime = rs.getDate("ENDTIME").toString();
                budget = rs.getDouble("BUDGET");
                numberOfperson = rs.getInt("NUMBEROFPERSON");
                user_id = rs.getInt("USER_ID");

                singledata.accumulate("Status", "OK");
                singledata.accumulate("Timestamp", sq.toInstant().toEpochMilli());
                singledata.accumulate("PLACE", place);
                singledata.accumulate("NUMBEROFPERSON", numberOfperson);
                singledata.accumulate("CUSINETYPE", cuisinetype);
                singledata.accumulate("STARTTIME", startTime);
                singledata.accumulate("ENDTIME", endTime);
                singledata.accumulate("USER_ID", user_id);
                singledata.accumulate("POST_ID", postid);
                singledata.accumulate("BUDGET", budget);

            }
            stm.close();
        } catch (SQLException ex) {
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return singledata.toString();
    }

    @GET
    @Path("viewevent")
    @Produces(MediaType.APPLICATION_JSON)
    public String vieweventgetJson() throws SQLException {
        String eventName = null, eventPlace = null, startTime = null, endTime = null;
        int eventid = 0, userid = 0;

        try {
            stm = conclass.createConnection();
            System.out.println("select * from EVENTS");
            ResultSet rs = stm.executeQuery("select * from EVENTS");
            while (rs.next()) {
                eventPlace = rs.getString("PLACEOFEVENTS");
                eventName = rs.getString("EVENTNAME");
                startTime = rs.getDate("STARTTIME").toString();
                endTime = rs.getDate("ENDTIME").toString();
                eventid = rs.getInt("EVENT_ID");
                userid = rs.getInt("USERADMIN_ID");

                singledata.accumulate("Status", "OK");
                singledata.accumulate("Timestamp", sq.toInstant().toEpochMilli());
                singledata.accumulate("EVENTPLACE", eventPlace);
                singledata.accumulate("EVENTNAME", eventName);
                singledata.accumulate("STARTTIME", startTime);
                singledata.accumulate("ENDTIME", endTime);
                singledata.accumulate("USER_ID", userid);
                singledata.accumulate("EVENT_ID", eventid);

                multipledata.add(singledata);
                singledata.clear();
            }
            stm.close();
        } catch (SQLException ex) {
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return multipledata.toString();
    }
}
