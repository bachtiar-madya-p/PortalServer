/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest.bachtiar.service;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import rest.bachtiar.service.user.TblUser;

/**
 *
 * @author Bachtiar M Permadi
 */
@Path("/register")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RegisterService {

    public RegisterService() {

    }

    @POST
    @Path("/doregister")
    @Produces(MediaType.APPLICATION_JSON)
    public String register(TblUser entity) {
        String response = "";
        int nik = entity.getNik();
        String name = entity.getName();
        String email = entity.getEmail();
        String phone = entity.getPhone();
        String plant = entity.getPlant();
        String dept = entity.getDept();
        String pwd = entity.getPwd();
        Date todaysDate = new Date();
        DateFormat df = new SimpleDateFormat("dd MMM yyyy");
        String date = df.format(todaysDate);
        String reg_date = date;
        String DefImage = "client/add_photo.png";
        String imgUser = DefImage;
        String tag = "waiting";
        int retCode = registerUser(nik, name, email, phone, plant, dept, pwd, tag, reg_date, imgUser);
        if (retCode == 0) {
            response = Utility.constructJSON("register", true);
        } else if (retCode == 1) {
            response = Utility.constructJSON("register", false, "NIK sudah terdaftar");
        } else if (retCode == 2) {
            response = Utility.constructJSON("register", false, "Spesial karakter tidak di ijinkan pada NIK");
        } else if (retCode == 3) {
            response = Utility.constructJSON("register", false, "Error occured");
        }
        return response;
    }

    private int registerUser(Integer nik, String name, String email, String phone, String plant, String dept, String pwd, String tag, String reg_date, String imgUser) {
        System.out.println("Inside checkCredentials");
        int result = 3;
        if (Utility.isNotNull(nik.toString()) && Utility.isNotNull(pwd)) {
            try {
                if (DBConnection.insertUser(nik, name, email, phone, plant, dept, pwd, tag, reg_date, imgUser)) {
                    System.out.println("RegisterUSer if");
                    result = 0;
                }
            } catch (SQLException sqle) {
                System.out.println("RegisterUSer catch sqle");
                //When Primary key violation occurs that means user is already registered
                if (sqle.getErrorCode() == 1062) {
                    result = 1;
                } //When special characters are used in name,username or password
                else if (sqle.getErrorCode() == 1064) {
                    System.out.println(sqle.getErrorCode());
                    result = 2;
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                System.out.println("Inside checkCredentials catch e ");
                result = 3;
            }
        } else {
            System.out.println("Inside checkCredentials else");
            result = 3;
        }

        return result;
    }

}
