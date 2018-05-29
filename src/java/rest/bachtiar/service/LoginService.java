/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest.bachtiar.service;

import java.sql.SQLException;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.transaction.annotation.Transactional;
import rest.bachtiar.service.user.TblUser;

/**
 *
 * @author root
 */
@Path("/login")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LoginService {

    public LoginService() {

    }

    @POST
    @Path("/dologin")
    @Transactional
    public String login(TblUser entity) throws JSONException {
        String response = "";
        Integer nik = entity.getNik();
        String pwd = entity.getPwd();

        int retCode = checkUser(nik, pwd);
        if (retCode == 0) {
            response = Utility.constructJSON("login", true);
        } else if (retCode == 1) {
            response = Utility.constructJSON("login", false, "NIK dalam validasi");
        } else if (retCode == 2) {
            response = Utility.constructJSON("login", false, "NIK atau password salah");
        } else if (retCode == 3) {
            response = Utility.constructJSON("login", false, "Error occured");
        } 
        return response;
    }

    private int checkUser(Integer nik, String pwd) {
        System.out.println("Inside checkCredentials");
        int result = 3;
        if (Utility.isNotNull(nik.toString()) && Utility.isNotNull(pwd)) {
            try {
                if (DBConnection.chkLogin(nik, pwd) == 1) {
                    System.out.println("RegisterUSer if");
                    result = 0;
                } else if (DBConnection.chkLogin(nik, pwd) == 2) {
                    result = 1;
                } else if (DBConnection.chkLogin(nik, pwd) == 3) {
                    result = 2;
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
