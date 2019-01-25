/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lunch;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

    int number = 0;

    @GET
    @Path("registration&{firstName}&{lastName}&{email}&{password}&{phonenumber}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson(@PathParam("firstName") String fn, @PathParam("lastName") String ln, @PathParam("email") String email,
            @PathParam("password") String pass,
            @PathParam("phonenumber") String pNumber) throws SQLException {
        String QUERY="\"("+111+"," +"'"+fn +"'"+ "," +"'"+ ln +"'"+ "," +"'"+ email +"'"+ "," +"'"+ pass +"'"+ ","+ "'"+ pNumber +"'"+")\"";
       System.out.println("INSERT INTO USERS VALUES(111," +"'"+fn +"'"+ "," +"'"+ ln +"'"+ "," +"'"+ email +"'"+ "," +"'"+ pass +"'"+ ","+ "'"+ pNumber +"'"+")");
        System.out.println(QUERY);
        try {
            stm = conclass.createConnection();
            number = stm.executeUpdate("INSERT INTO USERS VALUES(111," +"'"+fn +"'"+ "," +"'"+ ln +"'"+ "," +"'"+ email +"'"+ ","+ "'"+ pNumber +"'"+ "," +"'"+ pass +"'"+")");
            System.out.println("total inserted rows" + number);

            stm.close();
        } catch (SQLException ex) {
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }

    //defining and declaring a JSon object
    JSONObject singledata = new JSONObject();

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

                singledata.accumulate("Status", "OK");

                singledata.accumulate("Active", true);
                singledata.accumulate("user_id", user_Id);

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
                singledata.accumulate("userpassword",userpassword );
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
    @Path("PostInfo&{Place}&{NumberofPerson}&{CuisineType}&&{StartTime}&{EndTime}@{User_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson(@PathParam("Place") String place, @PathParam("NumberofPerson") int numberofperson, @PathParam("CuisineType") String cuisinetype,
            @PathParam("StartTIme") String starttime,@PathParam("EndTime") String endtime,@PathParam("User_id") int userid) throws SQLException {
        String query=("111," + place + "," + numberofperson + "," + cuisinetype + "," + starttime + "," + endtime + ","+userid+")");
        try {
            stm = conclass.createConnection();
            number = stm.executeUpdate("INSERT INTO POST_ADD VALUES"+ query);
            System.out.println("total inserted rows" + number);

            stm.close();
        } catch (SQLException ex) {
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
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
}
