/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest.bachtiar.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author root
 */
public abstract class EntityBase {

    private static final String DBCLASS = "com.mysql.jdbc.Driver";
    private static final String DBURL = "jdbc:mysql://localhost:3306/PortalV1?zeroDateTimeBehavior=convertToNull";
    private static final String DBUSER = "root";
    private static final String DBPWD = "rootadmin";
    public Connection con;

    @SuppressWarnings("finally")
    public final Connection connect() {

        try {
            Class.forName(DBCLASS);
            con = DriverManager.getConnection(DBURL, DBUSER, DBPWD);
        } catch (ClassNotFoundException | SQLException e) {
            throw e;
        } finally {
            return con;
        }
    }

    public final void disconect() {
        try {
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(EntityBase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
