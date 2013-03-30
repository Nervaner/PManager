/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pmanager;

import java.sql.Connection;
import java.sql.DriverManager;
import org.firebirdsql.jdbc.FBSQLException;

/**
 *
 * @author Kel
 */
public class DatabaseConnection {
    private Connection con;
    
    public DatabaseConnection(String databasePath)  throws Exception {
        try { 
            Class.forName("org.firebirdsql.jdbc.FBDriver");
            con = DriverManager.getConnection("jdbc:firebirdsql:localhost/3050:" + databasePath, "SYSDBA", "masterkey");
        } catch(ClassNotFoundException e) {
            throw(new Exception("Firebird driver not found"));
        } catch(FBSQLException e) {
            throw(new Exception("Failed to connect database"));
        } 
        
    }
    
}
