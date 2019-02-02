/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lunch;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Anubhav
 */
public class Connectionclass {

    Connection con;
    Statement stm;
    
    
    public Connectionclass()  {
       
    
    }
    public Statement createConnection(){
    
     try {
            Class.forName("oracle.jdbc.OracleDriver");
            con=DriverManager.getConnection("jdbc:oracle:thin:@144.217.163.57:1521:XE","mad304p3","mad303p3pw");
            stm=con.createStatement();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Connectionclass.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Connectionclass.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    
    return stm;}
    
}
