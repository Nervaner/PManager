/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pmanager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
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
    
    public ArrayList execQuery(String query) throws Exception {
        ArrayList data = new ArrayList<>();
        Object[] row;
        String dataClassName;
        try {   
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(query);
            ResultSetMetaData rsmd = rs.getMetaData();
            while (rs.next()) {
                row = new Object[rsmd.getColumnCount()];
                for (int i = 0; i < rsmd.getColumnCount(); ++i) {
                    dataClassName = rsmd.getColumnClassName(i+1);
                    switch (dataClassName) {
                        case "java.lang.Integer": 
                            row[i] = rs.getInt(i + 1);
                            break;
                        case "java.sql.Timestamp":
                            row[i] = rs.getDate(i + 1);
                            break;
                        default:
                            row[i] = rs.getString(i+1);
                    }    
                }
                data.add(row);
            }
            s.close();
        } catch(Exception e) {
            e.printStackTrace();
            System.out.println("Failed to create TDM");
        }
        
        return data;
    }
    
}
