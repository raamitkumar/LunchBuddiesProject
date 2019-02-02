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
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
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
    int number = 0;

    
    Calendar cal = Calendar.getInstance();
    Date date=new Date();
    

java.sql.Timestamp sq = new java.sql.Timestamp(date.getTime());  

SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");


    @GET
    @Path("registration&{firstName}&{lastName}&{email}&{password}&{phonenumber}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson(@PathParam("firstName") String fn, @PathParam("lastName") String ln, @PathParam("email") String email,
            @PathParam("password") String pass,
            @PathParam("phonenumber") String pNumber) throws SQLException {
        int user_id=111;
        String QUERY="\"("+user_id+"," +"'"+fn +"'"+ "," +"'"+ ln +"'"+ "," +"'"+ email +"'"+ "," +"'"+ pass +"'"+ ","+ "'"+ pNumber +"'"+")\"";
       System.out.println("INSERT INTO USERS VALUES(111," +"'"+fn +"'"+ "," +"'"+ ln +"'"+ "," +"'"+ email +"'"+ "," +"'"+ pass +"'"+ ","+ "'"+ pNumber +"'"+")");
        System.out.println(QUERY);
        try {
            stm = conclass.createConnection();
            number = stm.executeUpdate("INSERT INTO USERS VALUES(111," +"'"+fn +"'"+ "," +"'"+ ln +"'"+ "," +"'"+ email +"'"+ ","+ "'"+ pNumber +"'"+ "," +"'"+ pass +"'"+")");
            System.out.println("total inserted rows" + number);

            singledata.accumulate("Status","OK");
            singledata.accumulate("Timestamp", sdf);
            singledata.accumulate("Active",true);
            singledata.accumulate("User_id",user_id);
            singledata.accumulate("Message","You are successfully Register");
            
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
            System.out.println("select * from USERS WHERE EMAIL=" +"'"+ email +"'"+ " and PASSWORD=" +"'"+ pass+"'");
            ResultSet rs = stm.executeQuery("select * from USERS WHERE EMAIL=" +"'"+ email +"'"+ " and PASSWORD=" +"'"+ pass+"'");

            String fName, lName, contactnumber, userpassword, dateOfbirth;

            int user_Id;

            while (rs.next()) {
                System.out.println("yaaehhhhhhhhhhhhhhhhhhhhhhn........................................");
                fName = rs.getString("FIRSTNAME");
                lName = rs.getString("LASTNAME");
                email = rs.getString("EMAIL");
                contactnumber = rs.getString("CONTACTNUMBER");
                userpassword = rs.getString("PASSWORD");
                user_Id = rs.getInt("USER_ID");
                System.out.println("username is " + fName);

                 singledata.accumulate("Status","OK");
            singledata.accumulate("Timestamp", sdf);
            singledata.accumulate("Active",true);
            singledata.accumulate("User_id",user_Id);
                

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
            System.out.println("select * from USERS WHERE user_id=" + u_id );
            ResultSet rs = stm.executeQuery("select * from USERS WHERE USER_ID=" + u_id );

            String fName, lName, email, contactnumber, userpassword, username;

            int user_Id;

            while (rs.next()) {
                System.out.println("rs have values........................");
                fName = rs.getString("FIRSTNAME");
                username=rs.getString("USERNAME");
                lName = rs.getString("LASTNAME");
                email = rs.getString("EMAIL");
                contactnumber = rs.getString("PHONE");
                userpassword = rs.getString("PASSWORD");
                user_Id=rs.getInt("USER_ID");
                System.out.println("username is " + fName);

                singledata.accumulate("Status", "OK");
                singledata.accumulate("username",username );
                singledata.accumulate("firstname",fName );
                singledata.accumulate("lastname",lName );
                singledata.accumulate("email",email );
                singledata.accumulate("contactnumber", contactnumber);
                singledata.accumulate("Password",userpassword );
                singledata.accumulate("user_id", user_Id);
                singledata.accumulate("Active", true);
                

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
    public String getJson(@PathParam("Place") String place, @PathParam("NumberofPerson") int numberofperson,@PathParam("Budget") double budget,@PathParam("CuisineType") String cuisinetype
            ,@PathParam("StartTime") String starttime,@PathParam("EndTime") String endtime,@PathParam("User_id") int userid) throws SQLException {
      int  postid=111;
        String query=("+"+postid+"," + place + "," + numberofperson + "," +budget+","+ cuisinetype + "," + starttime + "," + endtime + ","+userid+")");
        try {
            stm = conclass.createConnection();
            
            System.out.println("INSERT INTO POST_ADD VALUES("+ query);
            number = stm.executeUpdate("INSERT INTO POST_ADD VALUES("+ query);
            System.out.println("total inserted rows" + number);

            stm.close();
        } catch (SQLException ex) {
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);
            
            singledata.accumulate("STATUS","OK");
            singledata.accumulate("TIMESTAMP",sdf);
            singledata.accumulate("Post_id",postid);
            singledata.accumulate("Message","Your add is successfully Post");
        }
        return singledata.toString();
    }
     @GET
    @Path("ADDEvent&{EventName}&{PlaceofEvent}&{startTime}&&{EndTime}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson(@PathParam("EventName") String eventname, @PathParam("PlaceofEvent") int placeofevent,@PathParam("StartTIme") String starttime,@PathParam("EndTime") String endtime) throws SQLException {
        String query=("111," + eventname + "," + placeofevent  + "," + starttime + "," + endtime +")");
        try {
            stm = conclass.createConnection();
            number = stm.executeUpdate("INSERT INTO EVENTS VALUES"+ query);
            System.out.println("total inserted rows" + number);

            stm.close();
        } catch (SQLException ex) {
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
    

       @GET
    @Path("ADDPhotos&{Photopath}&{Event_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson(@PathParam("EventName") String path,@PathParam("Event_id") int eventid) throws SQLException {
        String query=("111," + path + "," + eventid +")");
        try {
            stm = conclass.createConnection();
            number = stm.executeUpdate("INSERT INTO PHOTOS VALUES"+ query);
            System.out.println("total inserted rows" + number);

            stm.close();
        } catch (SQLException ex) {
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
    
      @GET
    @Path("RemoveUser&{Email}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson(@PathParam("Email") String email) throws SQLException {
        
        try {
            stm = conclass.createConnection();
            number = stm.executeUpdate("DELETE FROM USERS WHERE email="+ email);
            System.out.println("total Deleted rows" + number);

            stm.close();
        } catch (SQLException ex) {
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }

        @GET
    @Path("RemoveEvent&{EventName}&{EventPlace}&{StartDate}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson(@PathParam("EventName") String name,@PathParam("EventPlace") String place,@PathParam("StartDate") String startdate) throws SQLException {
        
        try {
            stm = conclass.createConnection();
            number = stm.executeUpdate("DELETE FROM EVENTS WHERE EVENTNAME="+ name+"AND PLACEOFEVENT="+place+"AND STARTDATE="+startdate);
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
    public String getJson(@PathParam("SharingTime") String sharetime,@PathParam("User_id") int userid,@PathParam("RecieverUserid") int recieverid,
            @PathParam("Post_id") int postid) throws SQLException {
        
        try {
            stm = conclass.createConnection();
            number = stm.executeUpdate("INSERT INTO SHARINGINVITATION VALUES("+"'"+sharetime+"'"+","+userid+","+recieverid+","+postid+")");
            System.out.println("total Inserted rows" + number);

            stm.close();
        } catch (SQLException ex) {
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
      @GET
    @Path("RecieveInvitation&{InvitationStatus}&{StatusTime}&{User_id}&{Reciever_id}&{post_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson(@PathParam("InvitationStatus") String invitationstatus,@PathParam("StatusTime") int time,@PathParam("User_id") int userid,@PathParam("Reciever_id")
    int recieverid,@PathParam("Post_id") int post_id) throws SQLException {
        
        try {
            stm = conclass.createConnection();
            number = stm.executeUpdate("INSERT INTO INVITATIONSTATUS VALUES("+"'"+invitationstatus+"'"+","+time+","+userid+","+recieverid+","+post_id+")");
            System.out.println("total Inserted rows" + number);

            stm.close();
        } catch (SQLException ex) {
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
    
      @GET
    @Path("SendMessage&{Message}&{Sender_id}&{Reciever_id}&{SendDate}")
    @Produces(MediaType.APPLICATION_JSON)
    public String sendMessagegetJson(@PathParam("Message") String message,@PathParam("Sender_id") int senderid,@PathParam("Reciever_id") int receiverid
   ,@PathParam("SendDate") String senddatetime) throws SQLException {
        
        try {
            stm = conclass.createConnection();
            number = stm.executeUpdate("INSERT INTO MESSAGE VALUES("+"'"+message+"'"+","+senderid+","+receiverid+","+"'"+senddatetime+"'"+")");
            System.out.println("total Inserted rows" + number);

            stm.close();
        } catch (SQLException ex) {
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
   
    @GET
    @Path("RecieveMessage&{Reciever_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String RecieveMessagegetJson(@PathParam("Reciever_id") int recieverid) throws SQLException {
        String message,userid;
        try {
            stm = conclass.createConnection();
             System.out.println("select * from Message WHERE RECIEVER_ID=" + recieverid );
            ResultSet rs = stm.executeQuery("select * from Message WHERE RECIEVER_ID=" + recieverid );

            stm.close();
        } catch (SQLException ex) {
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
}