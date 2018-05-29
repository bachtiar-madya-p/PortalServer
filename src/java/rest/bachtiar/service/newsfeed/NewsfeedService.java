/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest.bachtiar.service.newsfeed;

import java.net.URI;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.springframework.transaction.annotation.Transactional;
import rest.bachtiar.service.EntityBase;
import rest.bachtiar.service.FileLoc;
import rest.bachtiar.service.feedcmt.FeedcmtService;
import rest.bachtiar.service.user.TblUser;

import rest.bachtiar.service.user.UserService;

/**
 *
 * @author Bachtiar M Permadi
 */
@Path("/newsfeed")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class NewsfeedService extends EntityBase implements INewsfeedDataAccess<TblNewsfeed> {

    String UriImg = FileLoc.BASE_URL + "feed/noImg.png";

    @POST
    @Path("/create")
    @Transactional
    @Override
    public Response create(TblNewsfeed entity) {
        try {
            con = connect();
            PreparedStatement stmt = con.prepareStatement("INSERT INTO tbl_newsfeed (content, img_content, posdate, nik) VALUES ( ?, ? ,? ,? )");

            Date todaysDate = new Date();
            DateFormat df = new SimpleDateFormat("dd MMM yyyy");
            String date = df.format(todaysDate);
            String reg_date = date;

            stmt.setString(1, entity.getContent());
            if (entity.getImgContent() == null) {
                String imgContent = UriImg;
                stmt.setString(2, imgContent);
            } else {
                stmt.setString(2, entity.getImgContent());
            }
            stmt.setString(3, reg_date);
            stmt.setInt(4, entity.getNik().getNik());
            int rs = stmt.executeUpdate();
            if (rs != 0) {
                con.close();
                return Response.status(Response.Status.CREATED).build();
            } else {
                con.close();
                return Response.status(Response.Status.EXPECTATION_FAILED).build();
            }
        } catch (SQLException ex) {
            Logger.getLogger(NewsfeedService.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(Response.Status.NOT_FOUND).build();
        }

    }

    @PUT
    @Transactional
    @Override
    public Response edit(TblNewsfeed entity) {
        try {
            con = connect();
            String query = "UPDATE tbl_newsfeed SET content = ? , img_content = ? WHERE room_id = ?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, entity.getContent());
            stmt.setString(2, entity.getImgContent());

            int rs = stmt.executeUpdate();
            if (rs != 0) {
                con.close();
                return Response.created(URI.create(entity.getNik().toString())).build();
            } else {
                con.close();
                return Response.status(Response.Status.EXPECTATION_FAILED).build();
            }

        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @DELETE
    @Path("{room_id}")
    @Transactional
    @Override
    public Response remove(@PathParam("room_id") Integer room_id) {
        try {
            con = connect();
            String query = "DELETE FROM tbl_newsfeed  WHERE room_id = ?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1, room_id);
            //System.out.println(query);
            int rs = stmt.executeUpdate();
            if (rs != 0) {
                con.close();
                return Response.status(Response.Status.ACCEPTED).build();
            } else {
                con.close();
                return Response.status(Response.Status.EXPECTATION_FAILED).build();
            }

        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @Override
    public TblNewsfeed find(Integer room_id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    String Hasil;

    @GET
    @Path("list")
    @Transactional
    @Override
    public List<TblNewsfeed> findAll() {
        List<TblNewsfeed> listFeed = new ArrayList<>();
        try {
            con = connect();
            Statement stmt = con.createStatement();
            String query = "select room_id, content,posdate,img_content,nik from tbl_newsfeed order by room_id DESC";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                TblNewsfeed feed = new TblNewsfeed();
                feed.setRoomId(rs.getInt(1));
                feed.setContent(rs.getString(2));
                feed.setImgContent(FileLoc.BASE_URL + rs.getString(4));
                feed.setPosdate(rs.getString(3));
                TblUser user = new TblUser();
                user.setNik(rs.getInt(5));
                user = getUserByNik(user.getNik());
                feed.setNik(user);
                count(feed.getRoomId());
                feed.setCount(Hasil);
                
                listFeed.add(feed);
                

            }
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listFeed;
    }

    private TblUser getUserByNik(Integer nik) {
        TblUser user = new TblUser();
        try {

            con = connect();
            Statement stmt = con.createStatement();
            String query = "SELECT nik, name, email, phone,plant,dept,pwd,img_user,tag,reg_date, gcm_token FROM tbl_user where nik = '" + nik + "'";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                user.setNik(rs.getInt(1));
                user.setName(rs.getString(2));
                user.setEmail(rs.getString(3));
                user.setPhone(rs.getString(4));
                user.setPlant(rs.getString(5));
                user.setDept(rs.getString(6));
                user.setPwd(rs.getString(7));
                user.setImgUser(FileLoc.BASE_URL + rs.getString(8));
                user.setTag(rs.getString(9));
                user.setRegDate(rs.getString(10));
                user.setGcmToken(rs.getString(11));

            }
            con.close();

        } catch (SQLException ex) {
            Logger.getLogger(NewsfeedService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return user;
    }

    public String count(Integer roomId) {
        Hasil = "";
        try {
            con = connect();
            String query = "SELECT COUNT(*) FROM tbl_feedcmt WHERE room_id = ?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1, roomId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Hasil = String.valueOf(rs.getInt(1));
            }
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(FeedcmtService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Hasil;
    }

}
