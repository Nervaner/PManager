/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pmanager;

import com.sun.rowset.internal.Row;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.List;

/**
 *
 * @author Gedeon
 */
public class TableDataModelFactory {
    private Connection con;
    private List<Row> data;
    public TableDataModelFactory(Connection c) {
        con = c;
    }
    
     public void feelDataModel(TableDataModel tdm) {
        tdm.clear();
        Object o;
        try {
            
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(tdm.query);
            ResultSetMetaData rsmd = rs.getMetaData();
            while (rs.next()) {
                for (int i = 0; i < rsmd.getColumnCount(); ++i) {
                    //o[i] = rs.getString(i+1);
                    switch (tdm.cellsModel[i].dataType) {
                        case "int": 
                            o = rs.getInt(i + 1);
                            break;
                        case "date":
                            o = rs.getDate(i + 1);
                            break;
                        case "string":
                        default:
                            o = rs.getString(i+1);
                    }
                    tdm.add(tdm.cellsModel[i].columnName, o);
                }
            }
            s.close();
        } catch(Exception e) {
            e.printStackTrace();
            System.out.println("Failed to create TDM");
        }
    }
    
}
