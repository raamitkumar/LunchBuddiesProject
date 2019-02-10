/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lunch;

import java.awt.PageAttributes;
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

    ResultSet rs;

    @GET
    @Path("registration&{firstName}&{lastName}&{email}&{phonenumber}&{password}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson(@PathParam("firstName") String fn, @PathParam("lastName") String ln, @PathParam("email") String email,
            @PathParam("password") String pass, @PathParam("phonenumber") String pNumber) throws SQLException {
        int userid = 0;
        try {
            stm = conclass.createConnection();
            try {
                ResultSet rs = stm.executeQuery("SELECT USER_ID FROM USERS order by USER_ID DESC");
                rs.next();
                userid = rs.getInt("USER_ID");
                System.out.println("USERID OF THE USER IS " + userid);
                ++userid;
            } catch (SQLException sq) {

                Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, sq);

                userid = 100;
            }
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

            singledata.accumulate("STATUS", "ERROR");
            singledata.accumulate("TIMESTAMP", sq.toInstant().toEpochMilli());
            singledata.accumulate("MESSAGE", "Database connectivity error");
        }
        return singledata.toString();
    }

    @GET
    @Path("login&{email}&{password}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson(@PathParam("email") String email, @PathParam("password") String pass) {

        stm = conclass.createConnection();

        try {
            System.out.println("select * from USERS WHERE EMAIL=" + "'" + email + "'" + " and PASSWORD=" + "'" + pass + "'");
            rs = stm.executeQuery("select * from USERS WHERE EMAIL=" + "'" + email + "'" + " AND PASSWORD=" + "'" + pass + "'");

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
            singledata.accumulate("STATUS", "ERROR");
            singledata.accumulate("TIMESTAMP", sq.toInstant().toEpochMilli());
            singledata.accumulate("MESSAGE", "Database connectivity error");
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
            rs = stm.executeQuery("select * from USERS WHERE USER_ID=" + u_id);

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
                singledata.accumulate("Status", "Wrong");
                singledata.accumulate("TIMESTAMP", sq.toInstant().toEpochMilli());
                singledata.accumulate("MESSAGE", "something wrong in the userID");
            }

            rs.close();
            stm.close();

        } catch (SQLException ex) {
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);

            singledata.accumulate("STATUS", "ERROR");
            singledata.accumulate("TIMESTAMP", sq.toInstant().toEpochMilli());
            singledata.accumulate("MESSAGE", "Database connectivity error");
        }

        return singledata.toString();
    }

    @GET
    @Path("editprofile&{userid}&{firstName}&{lastName}&{email}&{password}&{phonenumber}")
    @Produces(MediaType.APPLICATION_JSON)
    public String editprofilegetJson(@PathParam("userid") int u_id, @PathParam("firstName") String fn, @PathParam("lastName") String ln, @PathParam("email") String email,
            @PathParam("password") String pass, @PathParam("phonenumber") String pNumber) {
        String fName, lName, emailid, contactnumber, userpassword, username;
        stm = conclass.createConnection();
        try {
            rs = stm.executeQuery("select * from USERS WHERE USER_ID=" + u_id + ")");

            rs.next();
            int user_Id = 0;

            System.out.println("rs have values........................");
            fName = rs.getString("FIRSTNAME");
            lName = rs.getString("LASTNAME");
            emailid = rs.getString("EMAIL");
            contactnumber = rs.getString("CONTACTNUMBER");
            userpassword = rs.getString("PASSWORD");
            user_Id = rs.getInt("USER_ID");
            System.out.println("username is " + fName);

            number = stm.executeUpdate("UPDATE USERS SET FIRSTNAME=" + fn + ",LASTNAME=" + ln + ",EMAIL=" + email + ",CONTACTNUMBER=" + pNumber + ",PASSWORD=" + pass + "WHERE USER_ID=" + u_id + ")");

            singledata.accumulate("Status", "OK");
            singledata.accumulate("TIMESTAMP", sq.toInstant().toEpochMilli());
            singledata.accumulate("firstname", fn);
            singledata.accumulate("lastname", ln);
            singledata.accumulate("email", email);
            singledata.accumulate("contactnumber", pNumber);
            singledata.accumulate("Password", pass);
            singledata.accumulate("user_id", u_id);
            if (user_Id == 0) {
                singledata.accumulate("Status", "OK");
                singledata.accumulate("TIMESTAMP", sq.toInstant().toEpochMilli());
                singledata.accumulate("MESSAGE", "something wrong in the userID");
            }

            rs.close();
            stm.close();

        } catch (SQLException ex) {
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);
            singledata.accumulate("STATUS", "ERROR");
            singledata.accumulate("TIMESTAMP", sq.toInstant().toEpochMilli());
            singledata.accumulate("MESSAGE", "Database connectivity error");
        }

        return singledata.toString();
    }

    @GET
    @Path("postinfo&{Place}&{NumberofPerson}&{Budget}&{CuisineType}&{StartTime}&{EndTime}&{User_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson(@PathParam("Place") String place, @PathParam("NumberofPerson") int numberofperson, @PathParam("Budget") double budget, @PathParam("CuisineType") String cuisinetype,
            @PathParam("StartTime") String starttime, @PathParam("EndTime") String endtime, @PathParam("User_id") int userid) throws SQLException {
        int postid = 0;
        try {
            stm = conclass.createConnection();
            try {
                rs = stm.executeQuery("SELECT POST_ID FROM POST_ADD ORDER BY POST_ID DESC");
                rs.next();
                postid = rs.getInt("POST_ID");
                System.out.println("POSTID OF THE POST IS " + postid);
                ++postid;
            } catch (SQLException sq) {
                Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, sq);

                postid = 1000;
            }
            System.out.println("INSERT INTO POST_ADD VALUES(" + postid + "," + place + "," + numberofperson + "," + budget + "," + cuisinetype + "," + starttime + "," + endtime + "," + userid + ")");
            number = stm.executeUpdate("INSERT INTO POST_ADD VALUES(" + postid + "," + place + "," + numberofperson + "," + budget + "," + cuisinetype + "," + starttime + "," + endtime + "," + userid + ")");
            System.out.println("total inserted rows" + number);

            if (number == 1) {
                singledata.accumulate("STATUS", "OK");
                singledata.accumulate("TIMESTAMP", sq.toInstant().toEpochMilli());
                singledata.accumulate("Post_id", postid);
                singledata.accumulate("Message", "Your add is successfully Post");
            } else if (number == 0) {

                singledata.accumulate("STATUS", "WRONG");
                singledata.accumulate("TIMESTAMP", sq.toInstant().toEpochMilli());
                singledata.accumulate("Message", "Your "
                        + "entered information is wrong");
            }

            stm.close();
        } catch (SQLException ex) {
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);
            singledata.accumulate("STATUS", "ERROR");
            singledata.accumulate("TIMESTAMP", sq.toInstant().toEpochMilli());
            singledata.accumulate("MESSAGE", "Database connectivity error");

        }
        return singledata.toString();
    }

    @GET
    @Path("addevent&{EventName}&{PlaceofEvent}&{startTime}&{EndTime}&{user_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson(@PathParam("EventName") String eventname, @PathParam("PlaceofEvent") String placeofevent, @PathParam("startTime") String starttime, @PathParam("EndTime") String endtime,
            @PathParam("user_id") int user_id) throws SQLException {
        int event_id = 0;
        try {
            stm = conclass.createConnection();
            try {
                rs = stm.executeQuery("SELECT EVENTID FROM EVENTS ORDER BY EVENTID DESC ");
                rs.next();
                event_id = rs.getInt("EVENTID");
                ++event_id;

                System.out.println("INSERT INTO EVENTS VALUES(" + event_id + "," + "'" + eventname + "'" + "," + "'" + placeofevent + "'" + "," + "'" + starttime + "'" + "," + "'" + endtime + "'" + "," + user_id + ")");
            } catch (SQLException sq) {
                Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, sq);
                event_id = 1;
            }
            number = stm.executeUpdate("INSERT INTO EVENTS VALUES(" + event_id + "," + "'" + eventname + "'" + "," + "'" + placeofevent + "'" + "," + "'" + starttime + "'" + "," + "'" + endtime + "'" + "," + user_id + ")");
            System.out.println("total inserted rows" + number);

            if (number == 1) {
                singledata.accumulate("Status", "OK");
                singledata.accumulate("Timestamp", sq.toInstant().toEpochMilli());
                singledata.accumulate("Event_id", event_id);
                singledata.accumulate("Message", "Event is succesfully added");
            } else if (number == 0) {
                singledata.accumulate("Status", "OK");
                singledata.accumulate("Timestamp", sq.toInstant().toEpochMilli());
                singledata.accumulate("Message", "Event is succesfully added");
            }

            stm.close();
        } catch (SQLException ex) {
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);

            singledata.accumulate("STATUS", "ERROR");
            singledata.accumulate("TIMESTAMP", sq.toInstant().toEpochMilli());
            singledata.accumulate("MESSAGE", "Database connectivity error");

        }
        return singledata.toString();
    }

    @GET
    @Path("addphotos&{Photobase64code}&{Event_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson(@PathParam("Photobase64code") String code, @PathParam("Event_id") int eventid) throws SQLException {
        int photo_id = 10001;
        System.out.println("INSERT INTO PHOTOS VALUES(" + photo_id + ",'" + code + "'," + eventid + ")");
        try {
            stm = conclass.createConnection();
            try {
                rs = stm.executeQuery("SELECT * FROM PHOTOS ORDER BY PHOTO_ID DESC");
                rs.next();
                photo_id = rs.getInt("PHOTO_ID");
                ++photo_id;
            } catch (SQLException sq) {
                photo_id = 2000;
            }
            number = stm.executeUpdate("INSERT INTO PHOTOS VALUES(" + photo_id + ",'" + code + "'," + eventid + ")");
            System.out.println("total inserted rows" + number);

            if (number == 1) {
                singledata.accumulate("STATUS", "OK");
                singledata.accumulate("TIMESTAMP", sq.toInstant().toEpochMilli());
                singledata.accumulate("Photo_ID", photo_id);
                singledata.accumulate("Message", "successfully uploaded");
            } else if (number == 0) {
                singledata.accumulate("STATUS", "WRONG");
                singledata.accumulate("TIMESTAMP", sq.toInstant().toEpochMilli());
                singledata.accumulate("Message", "INFORMATION IS WRONG");
            }

            stm.close();
        } catch (SQLException ex) {
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);

            singledata.accumulate("STATUS", "ERROR");
            singledata.accumulate("TIMESTAMP", sq.toInstant().toEpochMilli());
            singledata.accumulate("MESSAGE", "Database connectivity error");

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
            rs = stm.executeQuery("SELECT USER_ID FROM USERS WHERE EMAIL=" + email);
            rs.next();
            int user_id = rs.getInt("USER_ID");
            number = stm.executeUpdate("DELETE FROM POST_ADD WHERE USER_ID=" + user_id);
            if (number == 1) {
                number = 0;
                number = stm.executeUpdate("DELETE FROM USERS WHERE email=" + email);
            }
            System.out.println("total Deleted rows" + number);

            if (number == 1) {
                singledata.accumulate("Status", "OK");
                singledata.accumulate("Timestamp", sq.toInstant().toEpochMilli());
                singledata.accumulate("Message", "User is successfulkly removed");
            } else if (number == 0) {
                singledata.accumulate("Status", "WRONG");
                singledata.accumulate("Timestamp", sq.toInstant().toEpochMilli());
                singledata.accumulate("Message", "User is no more registered");
            }

            stm.close();
        } catch (SQLException ex) {
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);

            singledata.accumulate("STATUS", "ERROR");
            singledata.accumulate("TIMESTAMP", sq.toInstant().toEpochMilli());
            singledata.accumulate("MESSAGE", "Database connectivity error");

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

            if (number == 1) {

                singledata.accumulate("STATUS", "OK");
                singledata.accumulate("TIMESTAMP", sq.toInstant().toEpochMilli());
                singledata.accumulate("MESSAGE ", "EVENT IS SUCCESSFULLY REMOVE");
            } else if (number == 0) {
                singledata.accumulate("STATUS", "WRONG");
                singledata.accumulate("TIMESTAMP", sq.toInstant().toEpochMilli());
                singledata.accumulate("MESSAGE ", "EVENT IS NO MORE AVAILABLE");
            }

            stm.close();
        } catch (SQLException ex) {

            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);

            singledata.accumulate("STATUS", "ERROR");
            singledata.accumulate("TIMESTAMP", sq.toInstant().toEpochMilli());
            singledata.accumulate("MESSAGE", "Database connectivity error");

        }
        return singledata.toString();
    }

    @GET
    @Path("sendinvitation&{SharingTime}&{User_id}&{RecieverUserid}&{Post_id}")
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
            } else if (number == 0) {
                singledata.accumulate("Status", "OK");
                singledata.accumulate("TimeStamp", sq.toInstant().toEpochMilli());
                singledata.accumulate("Message", "the user_id is no more available");
            }

            stm.close();
        } catch (SQLException ex) {
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);
            singledata.accumulate("STATUS", "ERROR");
            singledata.accumulate("TIMESTAMP", sq.toInstant().toEpochMilli());
            singledata.accumulate("MESSAGE", "Database connectivity error");

        }
        return singledata.toString();
    }

    @GET
    @Path("recieveinvitation&{InvitationStatus}&{StatusTime}&{User_id}&{Reciever_id}&{post_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson(@PathParam("InvitationStatus") String invitationstatus, @PathParam("StatusTime") String time, @PathParam("User_id") int userid, @PathParam("Reciever_id") int recieverid, @PathParam("post_id") int postId) throws SQLException {

        try {
            stm = conclass.createConnection();
            System.out.println("INSERT INTO INVITATIONSTATUS VALUES(" + "'" + invitationstatus + "'" + ",'" + time + "'," + userid + "," + recieverid + "," + postId + ")");
            number = stm.executeUpdate("INSERT INTO INVITATIONSTATUS VALUES(" + "'" + invitationstatus + "','" + time + "'," + userid + "," + recieverid + "," + postId + ")");
            System.out.println("total Inserted rows" + number);

            if (number == 1) {
                singledata.accumulate("Status", "OK");
                singledata.accumulate("TimeStamp", sq.toInstant().toEpochMilli());
                singledata.accumulate("Message", "Invitation " + invitationstatus);
            } else if (number == 0) {
                singledata.accumulate("Status", "WRONG");
                singledata.accumulate("TimeStamp", sq.toInstant().toEpochMilli());
                singledata.accumulate("Message", "EXPIRED POST");

            }

            stm.close();
        } catch (SQLException ex) {
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);

            singledata.accumulate("STATUS", "ERROR");
            singledata.accumulate("TIMESTAMP", sq.toInstant().toEpochMilli());
            singledata.accumulate("MESSAGE", "Database connectivity error");

        }
        return singledata.toString();
    }

    @GET
    @Path("sendmessage&{Message}&{Sender_id}&{Reciever_id}&{SendDate}")
    @Produces(MediaType.APPLICATION_JSON)
    public String sendMessagegetJson(@PathParam("Message") String message, @PathParam("Sender_id") int senderid, @PathParam("Reciever_id") int receiverid,
            @PathParam("SendDate") String senddatetime) throws SQLException {
        int messageid = 0;

        stm = conclass.createConnection();
        try {
            try {
                rs = stm.executeQuery("SELECT * FROM MESSAGE ORDER BY MESSAGE_ID DESC");
                rs.next();
                messageid = rs.getInt("MESSAGE_ID");
                ++messageid;
            } catch (SQLException sq) {
                Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, sq);
                messageid = 3000;

            }
            System.out.println("INSERT INTO MESSAGE VALUES(" + messageid + ",'" + message + "'" + "," + senderid + "," + receiverid + "," + "'" + senddatetime + "'" + ")");
            number = stm.executeUpdate("INSERT INTO MESSAGE VALUES(" + messageid + ",'" + message + "'" + "," + senderid + "," + receiverid + "," + "'" + senddatetime + "'" + ")");
            System.out.println("total Inserted rows" + number);

            if (number == 1) {
                singledata.accumulate("Status", "OK");
                singledata.accumulate("Timestamp", sq.toInstant().toEpochMilli());
                singledata.accumulate("Message", "Message Sent");
            } else if (number == 0) {

                singledata.accumulate("Status", "WRONG");
                singledata.accumulate("Timestamp", sq.toInstant().toEpochMilli());
                singledata.accumulate("Message", "EXPIRED POST");
            }
            stm.close();
        } catch (SQLException ex) {
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);

            singledata.accumulate("STATUS", "ERROR");
            singledata.accumulate("TIMESTAMP", sq.toInstant().toEpochMilli());
            singledata.accumulate("MESSAGE", "Database connectivity error");

        }
        return singledata.toString();
    }

    @GET
    @Path("recievemessage&{Reciever_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String RecieveMessagegetJson(@PathParam("Reciever_id") int recieverid) throws SQLException {
        String message = null, date = null;
        int user_id = 0, message_id, messageid;
        try {
            stm = conclass.createConnection();
            System.out.println("select * from Message WHERE RECIEVER_ID=" + recieverid);

            rs = stm.executeQuery("select * from Message WHERE RECIEVER_ID=" + recieverid);
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
            if (user_id == 0) {

                singledata.accumulate("Status", "WRONG");
                singledata.accumulate("Timestamp", sq.toInstant().toEpochMilli());
                singledata.accumulate("Message", "no message");
            }
            stm.close();
        } catch (SQLException ex) {
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);

            singledata.accumulate("STATUS", "ERROR");
            singledata.accumulate("TIMESTAMP", sq.toInstant().toEpochMilli());
            singledata.accumulate("MESSAGE", "Database connectivity error");

        }
        return singledata.toString();
    }

    JSONArray multipledata = new JSONArray();
    String place = null, cuisinetype = null, startTime = null, endTime = null;
    int numberOfperson = 0, user_id = 0;
    double budget = 0;
    JSONObject viewpost = new JSONObject();

    @GET
    @Path("viewpost")
    @Produces(MediaType.APPLICATION_JSON)
    public String viewpostgetJson() throws SQLException {
        int post_id = 0;

        try {
            stm = conclass.createConnection();
            System.out.println("select * from POST_ADD");
            rs = stm.executeQuery("select * from POST_ADD");
            singledata.accumulate("Status", "OK");
            singledata.accumulate("Timestamp", sq.toInstant().toEpochMilli());
            while (rs.next()) {
                place = rs.getString("PLACE");
                cuisinetype = rs.getString("CUISINETYPE");
                startTime = rs.getDate("STARTTIME").toString();
                endTime = rs.getDate("ENDTIME").toString();
                budget = rs.getDouble("BUDGET");
                numberOfperson = rs.getInt("NUMBEROFPERSON");
                post_id = rs.getInt("POST_ID");
                user_id = rs.getInt("USER_ID");

                viewpost.accumulate("PLACE", place);
                viewpost.accumulate("NUMBEROFPERSON", numberOfperson);
                viewpost.accumulate("CUSINETYPE", cuisinetype);
                viewpost.accumulate("STARTTIME", startTime);
                viewpost.accumulate("ENDTIME", endTime);
                viewpost.accumulate("USER_ID", user_id);
                viewpost.accumulate("POST_ID", post_id);
                viewpost.accumulate("BUDGET", budget);
                multipledata.add(viewpost);
                viewpost.clear();

            }
            singledata.accumulate("POSTDATA", multipledata);
            if (post_id == 0) {
                singledata.accumulate("Status", "OK");
                singledata.accumulate("Timestamp", sq.toInstant().toEpochMilli());
                singledata.accumulate("MESSAGE", "SOME INFORMATION WENT WRONG");

            }
            stm.close();
        } catch (SQLException ex) {
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);

            singledata.accumulate("STATUS", "ERROR");
            singledata.accumulate("TIMESTAMP", sq.toInstant().toEpochMilli());
            singledata.accumulate("MESSAGE", "Database connectivity error");

        }
        return singledata.toString();
    }

    @GET
    @Path("viewpost&{Post_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String viewsinglepostgetJson(@PathParam("Post_id") int postid) throws SQLException {

        int post_id = 0;
        try {
            stm = conclass.createConnection();
            System.out.println("select * from POST_ADD WHERE POST_ID=" + postid);
            rs = stm.executeQuery("select * from POST_ADD WHERE POST_ID=" + postid);

            while (rs.next()) {
                place = rs.getString("PLACE");
                cuisinetype = rs.getString("CUISINETYPE");
                startTime = rs.getDate("STARTTIME").toString();
                endTime = rs.getDate("ENDTIME").toString();
                budget = rs.getDouble("BUDGET");
                numberOfperson = rs.getInt("NUMBEROFPERSON");
                user_id = rs.getInt("USER_ID");
                post_id = rs.getInt("POST_ID");

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
            if (post_id == 0) {
                singledata.accumulate("Status", "WRONG");
                singledata.accumulate("Timestamp", sq.toInstant().toEpochMilli());
                singledata.accumulate("MESSAGE", "The post is no more available");

            }

            stm.close();
        } catch (SQLException ex) {
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);

            singledata.accumulate("STATUS", "ERROR");
            singledata.accumulate("TIMESTAMP", sq.toInstant().toEpochMilli());
            singledata.accumulate("MESSAGE", "Database connectivity error");

        }
        return singledata.toString();
    }

    @GET
    @Path("viewevent")
    @Produces(MediaType.APPLICATION_JSON)
    public String vieweventgetJson() throws SQLException {
        String eventName = null, eventPlace = null, startTime = null, endTime = null, photopath = null;
        int eventid = 0, userid = 0, photo_id = 0;

        try {
            stm = conclass.createConnection();
            System.out.println("select * from EVENTS  JOIN PHOTOS ON EVENTS.EVENTID=PHOTOS.EVENT_ID");
            rs = stm.executeQuery("select * from EVENTS  JOIN PHOTOS ON EVENTS.EVENTID=PHOTOS.EVENT_ID");
            singledata.accumulate("Status", "OK");
            singledata.accumulate("Timestamp", sq.toInstant().toEpochMilli());
            while (rs.next()) {
                eventPlace = rs.getString("PLACEOFEVENT");
                eventName = rs.getString("EVENTNAME");
                startTime = rs.getDate("STARTTIME").toString();
                endTime = rs.getDate("ENDTIME").toString();
                eventid = rs.getInt("EVENTID");
                userid = rs.getInt("ADMINUSER_ID");
                photo_id = rs.getInt("PHOTO_ID");
                photopath = rs.getBlob("PHOTOPATH").toString();

                viewpost.accumulate("EVENTPLACE", eventPlace);
                viewpost.accumulate("EVENTNAME", eventName);
                viewpost.accumulate("STARTTIME", startTime);
                viewpost.accumulate("ENDTIME", endTime);
                viewpost.accumulate("USER_ID", userid);
                viewpost.accumulate("EVENT_ID", eventid);
                viewpost.accumulate("PHOTOID", photo_id);
                viewpost.accumulate("PHOTOPATH", photopath);

                multipledata.add(viewpost);
                viewpost.clear();
            }
            singledata.accumulate("EVENTDATA", multipledata);
            if (eventid == 0) {
                singledata.accumulate("Status", "OK");
                singledata.accumulate("Timestamp", sq.toInstant().toEpochMilli());
                singledata.accumulate("MESSAGE", "SOME INFORMATION WENT WRONG");

            }

            stm.close();
        } catch (SQLException ex) {
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);

            singledata.accumulate("STATUS", "ERROR");
            singledata.accumulate("TIMESTAMP", sq.toInstant().toEpochMilli());
            singledata.accumulate("MESSAGE", "Database connectivity error");

        }
        return singledata.toString();
    }

    
    @GET
    @Path("viewevent&{event_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String vieweventsinglegetJson(@PathParam("event_id") int eventid ) throws SQLException {
        String eventName = null, eventPlace = null, startTime = null, endTime = null, photopath = null;
        int eventId = 0, userid = 0, photo_id = 0;

        try {
            stm = conclass.createConnection();
            System.out.println("select * from EVENTS  JOIN PHOTOS ON EVENTS.EVENTID=PHOTOS.EVENT_ID");
            rs = stm.executeQuery("select * from EVENTS  JOIN PHOTOS ON EVENTS.EVENTID=PHOTOS.EVENT_ID WHERE EVENT_ID="+eventid);
            singledata.accumulate("Status", "OK");
            singledata.accumulate("Timestamp", sq.toInstant().toEpochMilli());
            while (rs.next()) {
                eventPlace = rs.getString("PLACEOFEVENT");
                eventName = rs.getString("EVENTNAME");
                startTime = rs.getDate("STARTTIME").toString();
                endTime = rs.getDate("ENDTIME").toString();
                eventid = rs.getInt("EVENTID");
                userid = rs.getInt("ADMINUSER_ID");
                photo_id = rs.getInt("PHOTO_ID");
                photopath = rs.getBlob("PHOTOPATH").toString();
                
                viewpost.accumulate("PHOTOID", photo_id);
            viewpost.accumulate("PHOTOPATH", photopath);

                
                multipledata.add(viewpost);
                viewpost.clear();
            }
            singledata.accumulate("EVENTPLACE", eventPlace);
            singledata.accumulate("EVENTNAME", eventName);
            singledata.accumulate("STARTTIME", startTime);
            singledata.accumulate("ENDTIME", endTime);
            singledata.accumulate("USER_ID", userid);
            singledata.accumulate("EVENT_ID", eventid);
            

            singledata.accumulate("EVENTDATA", multipledata);
            if (eventid == 0) {
                singledata.accumulate("Status", "OK");
                singledata.accumulate("Timestamp", sq.toInstant().toEpochMilli());
                singledata.accumulate("MESSAGE", "SOME INFORMATION WENT WRONG");

            }

            stm.close();
        } catch (SQLException ex) {
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);

            singledata.accumulate("STATUS", "ERROR");
            singledata.accumulate("TIMESTAMP", sq.toInstant().toEpochMilli());
            singledata.accumulate("MESSAGE", "Database connectivity error");

        }
        return singledata.toString();
    }
    @GET
    @Path("addeventview&{viewdate}&{eventid}&{userid}")
    @Produces(MediaType.APPLICATION_JSON)
    public String vieweventgetJson(@PathParam("viewdate") String view_date, @PathParam("eventid") int event_id, @PathParam("userid") int user_id) throws SQLException {
        int numberofpostview = 0, photo_id = 0, eventid = 0;
        ResultSet rsphotos;
        String path = null;
        JSONObject photopath = new JSONObject();
        JSONArray photoarray = new JSONArray();
        try {
            stm = conclass.createConnection();
            try {
                rs = stm.executeQuery("SELECT * FROM EVENTVIEW");
                rs.next();
                System.out.println(rs.getInt("NUMBEROFTIMES"));
                numberofpostview = rs.getInt("NUMBEROFTIMES");
                ++numberofpostview;
            } catch (SQLException sq) {

                Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, sq);
                numberofpostview = 1;
            }
            number = stm.executeUpdate("INSERT INTO EVENTVIEW VALUES( '" + view_date + "'," + numberofpostview + "," + event_id + "," + user_id + ")");
            if (number == 1) {
                singledata.accumulate("STATUS", "OK");
                singledata.accumulate("TIMESTAMP", sq.toInstant().toEpochMilli());
                singledata.accumulate("NUMBEROFTIMES", numberofpostview);

                rsphotos = stm.executeQuery("SELECT * FROM PHOTOS WHERE EVENT_ID=" + event_id);
                while (rsphotos.next()) {

                    photo_id = rsphotos.getInt("PHOTO_ID");
                    path = rsphotos.getBlob("PHOTOPATH").toString();
                    photopath.accumulate("PHOTO_ID", photo_id);
                    photopath.accumulate("CODE", path);
                    photoarray.add(photopath);
                    photopath.clear();
                }
                singledata.accumulate("Path", photoarray);

                singledata.accumulate("MESSAGE", numberofpostview + " Times " + user_id + " show this " + event_id + " EVENTID");
            } else if (number == 0) {
                singledata.accumulate("STATUS", "OK");
                singledata.accumulate("TIMESTAMP", sq.toInstant().toEpochMilli());
                singledata.accumulate("MESSAGE", "NO USER HAS SEEN THIS ID");
            }

            stm.close();
        } catch (SQLException ex) {
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);

            singledata.accumulate("STATUS", "ERROR");
            singledata.accumulate("TIMESTAMP", sq.toInstant().toEpochMilli());
            singledata.accumulate("MESSAGE", "Database connectivity error");

        }
        return singledata.toString();
    }

    @GET
    @Path("addpostview&{viewdate}&{userid}&{postid}")
    @Produces(MediaType.APPLICATION_JSON)
    public String viewpoststatusgetJson(@PathParam("viewdate") String view_date, @PathParam("userid") int user_id, @PathParam("postid") int post_id) throws SQLException {
        int numberofpostview = 0;
        try {
            stm = conclass.createConnection();
            try {
                rs = stm.executeQuery("SELECT * FROM VIEWPOST");
                rs.next();
                System.out.println(rs.getInt("NUMBEROFTIMES"));
                numberofpostview = rs.getInt("NUMBEROFTIMES");
                ++numberofpostview;
            } catch (SQLException sq) {

                Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, sq);
                numberofpostview = 1;
            }
            number = stm.executeUpdate("INSERT INTO VIEWPOST VALUES( '" + view_date + "'," + numberofpostview + "," + user_id + "," + post_id + ")");
            if (number == 1) {

                singledata.accumulate("STATUS", "OK");
                singledata.accumulate("TIMESTAMP", sq.toInstant().toEpochMilli());
                singledata.accumulate("NUMBEROFTIMES", numberofpostview);
                singledata.accumulate("USER_ID", user_id);
                singledata.accumulate("POST_ID", post_id);
                singledata.accumulate("MESSAGE", numberofpostview + " Times " + user_id + " show this " + post_id + " postID");
            } else if (number == 0) {
                singledata.accumulate("STATUS", "OK");
                singledata.accumulate("TIMESTAMP", sq.toInstant().toEpochMilli());
                singledata.accumulate("MESSAGE", "NO USER HAS SEEN THIS ID");
            }

            stm.close();
        } catch (SQLException ex) {
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);
            singledata.accumulate("STATUS", "ERROR");
            singledata.accumulate("TIMESTAMP", sq.toInstant().toEpochMilli());
            singledata.accumulate("MESSAGE", "Database connectivity error");

        }
        return singledata.toString();
    }
    JSONObject photoobject = new JSONObject();

    @GET
    @Path("viewphotos&{event_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String viewphotogetJson(@PathParam("event_id") int eventID) throws SQLException {
        int photoid = 0;
        String path = null;

        try {
            stm = conclass.createConnection();
            rs = stm.executeQuery("SELECT *  FROM PHOTOS WHERE EVENT_ID=" + eventID);
            while (rs.next()) {
                photoid = rs.getInt("PHOTO_ID");
                eventID = rs.getInt("EVENT_ID");
                path = rs.getBlob("PHOTOPATH").toString();

                photoobject.accumulate("PATH", path);
                photoobject.accumulate("PHOTO_ID", photoid);

                multipledata.add(photoobject);

                photoobject.clear();

            }
            if (photoid > 0) {
                singledata.accumulate("STATUS", "OK");
                singledata.accumulate("TIMESTAMP", sq.toInstant().toEpochMilli());
                singledata.accumulate("PHOTOPATH", multipledata);
                multipledata.clear();
            } else if (photoid == 0) {

                singledata.accumulate("STATUS", "Wrong");
                singledata.accumulate("TIMESTAMP", sq.toInstant().toEpochMilli());
                singledata.accumulate("MESSAGE", "NO MORE PHOTOS AVAILABLE FOR THIS EVENTS");
            }

            stm.close();
        } catch (SQLException ex) {
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);

            singledata.accumulate("STATUS", "ERROR");
            singledata.accumulate("TIMESTAMP", sq.toInstant().toEpochMilli());
            singledata.accumulate("MESSAGE", "Database connectivity error");

        }
        return singledata.toString();
    }

    
//    @GET
//
//    @Path("viewrecieverinvitation&{user_id}")
//
//    @Produces(MediaType.APPLICATION_JSON)
//
//    public String viewRecieveInvitationgetJson(@PathParam("user_id") int user_id) throws SQLException {
//
//        int recieveruser_id = 0, post_id = 0;
//
//        JSONObject recievemul = new JSONObject();
//
//        JSONObject userdata = new JSONObject();
//
//        JSONObject postdata = new JSONObject();
//
//        ResultSet rs2, rspost;
//
//        String placeName, cuisineType, firstName, lastName;
//
//        try {
//
//            stm = conclass.createConnection();
//
//            rs = stm.executeQuery("SELECT * FROM INVITATIONSTATUS NATURAL JOIN USERS NATURAL JOIN POST_ADD WHERE USER_ID="+user_id);
//               singledata.accumulate("STATUS", "OK");
//
//                singledata.accumulate("TIMESTAMP", sq.toInstant().toEpochMilli());
//
//            while (rs.next()) {
//
//                recieveruser_id = rs.getInt("RECIEVERUSERID");
//
//                post_id = rs.getInt("POST_ID");
//
//
//                place = rs.getString("PLACE");
//
//                cuisinetype = rs.getString("CUISINETYPE");
//
//                startTime = rs.getDate("STARTTIME").toString();
//
//                endTime = rs.getDate("ENDTIME").toString();
//
//                budget = rs.getDouble("BUDGET");
//
//                numberOfperson = rs.getInt("NUMBEROFPERSON");
//
//                user_id = rs.getInt("USER_ID");
//                
//
//                postdata.accumulate("PLACE", place);
//
//                postdata.accumulate("NUMBEROFPERSON", numberOfperson);
//
//                postdata.accumulate("CUSINETYPE", cuisinetype);
//
//                postdata.accumulate("STARTTIME", startTime);
//
//                postdata.accumulate("ENDTIME", endTime);
//
//                postdata.accumulate("USER_ID", user_id);
//
//                postdata.accumulate("POST_ID", post_id);
//
//                postdata.accumulate("BUDGET", budget);
//
//               
//                rs2 = stm.executeQuery("SELECT * FROM USERS WHERE USER_ID=" + recieveruser_id);
//
//                rs2.next();
//                System.out.println(rs2.next());
//                firstName = rs2.getString("FIRSTNAME");
//
//                lastName = rs2.getString("LASTNAME");
//
//                userdata.accumulate("RECIEVERUSER_ID", recieveruser_id);
//
//                userdata.accumulate("FIRSTNAME", firstName);
//
//                userdata.accumulate("LASTNAME", lastName);
//
//                System.out.println(userdata.toString());
//
//                recievemul.accumulate("POSTDATA", postdata);
//
//                recievemul.accumulate("SENDERDATA", userdata);
//
//                postdata.clear();
//
//                userdata.clear();
//
//                multipledata.add(recievemul);
//
//                recievemul.clear();
//
//                singledata.accumulate("Data", multipledata);
//
//            }
//
//            multipledata.clear();
//
//            if (recieveruser_id == 0) {
//
//                singledata.accumulate("STATUS", "Wrong");
//
//                singledata.accumulate("TIMESTAMP", sq.toInstant().toEpochMilli());
//
//                singledata.accumulate("MESSAGE", "You didn't have any Invitation");
//
//            } 
//
//            stm.close();
//
//        } catch (SQLException ex) {
//
//            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);
//            singledata.accumulate("STATUS", "ERROR");
//
//                singledata.accumulate("TIMESTAMP", sq.toInstant().toEpochMilli());
//
//                singledata.accumulate("MESSAGE", "Database connectivity error");
//
//        }
//
//        return singledata.toString();
//    }
//
//    @GET
//    @Path("viewsendedinvitation&{user_id}")
//    @Produces(MediaType.APPLICATION_JSON)
//    public String viewSendedInvitationgetJson(@PathParam("user_id") int user_id) throws SQLException {
//        int recieveruser_id = 0, post_id = 0;
//        JSONObject recievemul = new JSONObject();
//        JSONObject userdata = new JSONObject();
//        JSONObject postdata = new JSONObject();
//        ResultSet rs2, rspost;
//
//        String statustime, invtationStatus, firstName, lastName;
//
//        try {
//            stm = conclass.createConnection();
//            rs = stm.executeQuery("SELECT *  FROM INVITATIONSTATUS NATURALJOIN USERS  NATURAL JOIN POST_ADD WHERE USER_ID=" + user_id);
//            singledata.accumulate("STATUS", "OK");
//            singledata.accumulate("TIMESTAMP", sq.toInstant().toEpochMilli());
//            while (rs.next()) {
//
//                recieveruser_id = rs.getInt("RECIEVERUSERID");
//                user_id = rs.getInt("USER_ID");
//                firstName = rs.getString("FIRSTNAME");
//                lastName = rs.getString("LASTNAME");
//                post_id = rs.getInt("POST_ID");
//                invtationStatus = rs.getString("INVITATIONSTATUS");
//                statustime = rs.getString("STATUSTIME");
//                place = rs.getString("PLACE");
//                cuisinetype = rs.getString("CUISINETYPE");
//                startTime = rs.getString("STARTTIME");
//                endTime = rs.getString("ENDTIME");
//
//                postdata.accumulate("RECIEVERUSER_ID", recieveruser_id);
//                postdata.accumulate("SENDERUSER_ID", user_id);
//
//                postdata.accumulate("POST_ID", post_id);
//                postdata.accumulate("INVITATIONSTATUS", invtationStatus);
//                postdata.accumulate("STATUSTIME", statustime);
//                postdata.accumulate("FIRSTNAME", firstName);
//                postdata.accumulate("LASTNAME", lastName);
//                postdata.accumulate("PLACE", place);
//                postdata.accumulate("CUISINETYPE", cuisinetype);
//                postdata.accumulate("STARTTIME", startTime);
//                postdata.accumulate("ENDTIME", endTime);
//
//                multipledata.add(postdata);
//            }
//            singledata.accumulate("InvitationSTATUS", multipledata);
//            if (recieveruser_id == 0) {
//
//                singledata.accumulate("STATUS", "Wrong");
//                singledata.accumulate("TIMESTAMP", sq.toInstant().toEpochMilli());
//                singledata.accumulate("MESSAGE", "You didn't have any Invitation");
//            }
//
//            stm.close();
//        } catch (SQLException ex) {
//            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);
//
//            singledata.accumulate("STATUS", "ERROR");
//            singledata.accumulate("TIMESTAMP", sq.toInstant().toEpochMilli());
//            singledata.accumulate("MESSAGE", "Database connectivity error");
//
//        }
//        return singledata.toString();
//    }
}
