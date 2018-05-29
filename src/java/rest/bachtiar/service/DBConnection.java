/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest.bachtiar.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;
import rest.bachtiar.service.user.TblUser;

/**
 *
 * @author root
 */
public class DBConnection {

    private static final String DBCLASS = "com.mysql.jdbc.Driver";
    private static final String DBURL = "jdbc:mysql://localhost:3306/PortalV1?zeroDateTimeBehavior=convertToNull";
    private static final String DBUSER = "root";
    private static final String DBPWD = "rootadmin";

    @SuppressWarnings("finally")
    public static Connection createConnection() throws Exception {
        Connection con = null;
        try {
            Class.forName(DBCLASS);
            con = DriverManager.getConnection(DBURL, DBUSER, DBPWD);
        } catch (ClassNotFoundException | SQLException e) {
            throw e;
        } finally {
            return con;
        }
    }

    public static boolean checkLogin(int nik, String pwd) throws Exception {
        boolean isUserAvailable = false;
        Connection dbConn = null;
        try {
            try {
                dbConn = DBConnection.createConnection();
            } catch (Exception e) {
            }
            Statement stmt = dbConn.createStatement();
            String query = "SELECT  nik, name, email, phone,plant,dept,reg_date,img_user,pwd,tag FROM tbl_user WHERE nik = " + nik
                    + " AND pwd = '" + pwd + "'";
            //System.out.println(query);
            ResultSet rs = stmt.executeQuery(query);
           String tag = "waiting";
            while (rs.next()) {
                TblUser user = new TblUser();
                user.setNik(rs.getInt(1));
                user.setName(rs.getString(2));
                user.setEmail(rs.getString(3));
                user.setPhone(rs.getString(4));
                user.setPlant(rs.getString(5));
                user.setDept(rs.getString(6));
                user.setRegDate(rs.getString(7));
                user.setImgUser(FileLoc.BASE_URL + rs.getString(8));
                user.setPwd(rs.getString(9));
                user.setTag(rs.getString(10));                
                if(user.getTag().equals(tag) )
                {
                    isUserAvailable = false;
                    
                }else{
                    isUserAvailable = true;
                }
                
            }
        } catch (SQLException sqle) {
            throw sqle;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            if (dbConn != null) {
                dbConn.close();
            }
            throw e;
        } finally {
            if (dbConn != null) {
                dbConn.close();
            }
        }
        return isUserAvailable;
    }
     public static int chkLogin(Integer nik, String pwd) throws Exception {
        int isUserAvailable = 3;
        Connection dbConn = null;
        try {
            try {
                dbConn = DBConnection.createConnection();
            } catch (Exception e) {
            }
            Statement stmt = dbConn.createStatement();
            String query = "SELECT  nik, name, email, phone,plant,dept,reg_date,img_user, gcm_token,pwd,tag FROM tbl_user WHERE nik = " + nik
                    + " AND pwd = '" + pwd + "'";
            //System.out.println(query);
            ResultSet rs = stmt.executeQuery(query);
           String tag = "waiting";
            while (rs.next()) {
                TblUser user = new TblUser();
                user.setNik(rs.getInt(1));                
                user.setName(rs.getString(2));
                user.setEmail(rs.getString(3));
                user.setPhone(rs.getString(4));
                user.setPlant(rs.getString(5));
                user.setDept(rs.getString(6));
                user.setRegDate(rs.getString(7));
                user.setImgUser(FileLoc.BASE_URL + rs.getString(8));
                user.setGcmToken(rs.getString(9));
                user.setPwd(rs.getString(10));
                user.setTag(rs.getString(11));                
                if(user.getTag().equals(tag) )
                {
                    isUserAvailable = 2;
                    
                }else{
                    isUserAvailable = 1;
                }                
            }
            
        } catch (SQLException sqle) {
            throw sqle;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            if (dbConn != null) {
                dbConn.close();
            }
            throw e;
        } finally {
            if (dbConn != null) {
                dbConn.close();
            }
        }
        return isUserAvailable;
    }
    public static boolean insertUser(int nik, String name, String email, String phone, String plant, String dept, String pwd, String tag, String reg_date, String imgUser)
            throws SQLException, Exception {
        boolean insertStatus = false;
        Connection dbConn = null;
        try {
            try {
                dbConn = DBConnection.createConnection();
            } catch (Exception e) {
            }
            Statement stmt = dbConn.createStatement();
            String query = "INSERT into tbl_user(nik, name, email, phone, plant, dept, pwd, tag, reg_date, img_user) values('" + nik + "'," + "'"
                    + name + "','" + email + "','" + phone + "','" + plant + "','" + dept + "','" + pwd + "','" + tag + "','" + reg_date + "','" + imgUser + "')";
            //System.out.println(query);
            int records = stmt.executeUpdate(query);
            //System.out.println(records);
            //When record is successfully inserted
            if (records > 0) {
                insertStatus = true;
            }
        } catch (SQLException sqle) {
            //sqle.printStackTrace();
            throw sqle;
        } catch (Exception e) {
            //e.printStackTrace();
            // TODO Auto-generated catch block
            if (dbConn != null) {
                dbConn.close();
            }
            throw e;
        } finally {
            if (dbConn != null) {
                dbConn.close();
            }
        }
        return insertStatus;
    }
}
