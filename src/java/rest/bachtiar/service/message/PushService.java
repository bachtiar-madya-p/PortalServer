/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest.bachtiar.service.message;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.springframework.transaction.annotation.Transactional;
import rest.bachtiar.service.EntityBase;
import rest.bachtiar.service.user.TblUser;
import rest.bachtiar.service.user.UserService;

/**
 *
 * @author root
 */
@Path("/push/{userId}/{message}")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PushService extends EntityBase implements IPushService {

    public TblUser findPatientRegistrationId(Integer nik) {
        TblUser user = new TblUser();
        try {

            con = connect();
            String query = "SELECT `nik`,`name`,`phone`,`email`,`plant`,`dept`,`img_user`,`pwd`,`reg_date`,`gcm_token`,`tag` FROM `tbl_user` WHERE nik = ?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1, nik);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {

                user.setNik(rs.getInt(1));
                user.setName(rs.getString(2));
                user.setPhone(rs.getString(3));
                user.setEmail(rs.getString(4));
                user.setPlant(rs.getString(5));
                user.setDept(rs.getString(6));
                user.setImgUser(rs.getString(7));
                user.setPwd(rs.getString(8));
                user.setRegDate(rs.getString(9));
                user.setGcmToken(rs.getString(10));
                user.setTag(rs.getString(11));
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        disconect();
        return user;
    }

    @GET
    @Transactional
    @Override
    public Response sendPush(@PathParam("nik") Integer nik, @PathParam("message") String Message) {

        TblUser user = findPatientRegistrationId(nik);
        if (user.getGcmToken()== null) {
            return Response.status(200).entity("Failed").build();
        }
        Sender sender = new Sender("AIzaSyBPdID2HmtDVUD_0v4yqR73zldDp6xBF1I");
        //Sender sender = new Sender(apiKey);
        Message message = new Message.Builder().addData("message", Message).addData("title", "Say Dokter").build();
        try {
            Result result = sender.send(message, user.getGcmToken(), 1);
            //System.out.println(str.substring(4));
            return Response.status(200).entity("Done").build();
        } catch (IOException e) {
            return Response.status(200).entity("Failed").build();
        }
    }
}
