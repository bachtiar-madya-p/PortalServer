/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest.bachtiar.service.feedcmt;

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
import rest.bachtiar.service.newsfeed.NewsfeedService;
import rest.bachtiar.service.newsfeed.TblNewsfeed;
import rest.bachtiar.service.user.TblUser;

/**
 *
 * @author Bachtiar M Permadi
 */
@Path("/feedcomment")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FeedcmtService extends EntityBase implements IFeedcmtDataAccess<TblFeedcmt> {
    
    String hasil;

    @POST
    @Path("/create")
    @Transactional
    @Override
    public Response create(TblFeedcmt entity) {
        try {
            con = connect();
            PreparedStatement stmt = con.prepareStatement("INSERT INTO tbl_feedcmt (cmtcontent, cmtposdate, nik,room_id) VALUES ( ?, ? ,? ,? )");
            Date todaysDate = new Date();
            DateFormat df = new SimpleDateFormat("dd MMM yyyy");
            String date = df.format(todaysDate);

            stmt.setString(1, entity.getCmtcontent());
            stmt.setString(2, date);
            stmt.setInt(3, entity.getNik().getNik());
            stmt.setInt(4, entity.getRoomId());
            int rs = stmt.executeUpdate();
            if (rs != 0) {
                con.close();
                return Response.status(Response.Status.CREATED).build();
            } else {
                con.close();
                return Response.status(Response.Status.EXPECTATION_FAILED).build();
            }
        } catch (SQLException ex) {
            Logger.getLogger(FeedcmtService.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(Response.Status.NOT_FOUND).build();
        }

    }

    @PUT
    @Transactional
    @Path("/update")
    @Override
    public Response edit(TblFeedcmt entity) {
        try {
            con = connect();
            String query = "UPDATE tbl_feedcmt SET cmtcontent = ?, cmtposdate = ?, nik = ?,room_id = ? WHERE id = ?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, entity.getCmtcontent());
            stmt.setString(2, entity.getCmtposdate());
            stmt.setInt(3, entity.getNik().getNik());
            stmt.setInt(4, entity.getRoomId());
            stmt.setInt(5, entity.getId());
            int rs = stmt.executeUpdate();
            if (rs != 0) {
                con.close();
                return Response.status(Response.Status.ACCEPTED).build();
            } else {
                con.close();
                return Response.status(Response.Status.EXPECTATION_FAILED).build();
            }
        } catch (SQLException ex) {
            Logger.getLogger(FeedcmtService.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @DELETE
    @Transactional
    @Path("/remove/{id}")
    @Override
    public Response remove(@PathParam("id") Integer id) {
        try {
            con = connect();
            String query = "DELETE FROM tbl_feedcmt  WHERE id = ?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1, id);
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
            Logger.getLogger(FeedcmtService.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("listbyroom/{room_id}")
    @Transactional
    @Override
    public List<TblFeedcmt> findByRoom(@PathParam("room_id") Integer room_id) {
        List<TblFeedcmt> listFeed = new ArrayList<>();
        try {

            con = connect();
            String query = "SELECT id, cmtcontent, cmtposdate,nik, room_id FROM tbl_feedcmt WHERE room_id = ? ORDER BY id ASC";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1, room_id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                TblFeedcmt feed = new TblFeedcmt();
                feed.setId(rs.getInt(1));
                feed.setCmtcontent(rs.getString(2));
                feed.setCmtposdate(rs.getString(3));
                TblUser user = new TblUser();
                user.setNik(rs.getInt(4));
                user = getUserByNik(user.getNik());
                feed.setNik(user);                
                feed.setRoomId(rs.getInt(5));               
                listFeed.add(feed);
            }
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(FeedcmtService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listFeed;
    }

    @GET
    @Path("list")
    @Transactional
    @Override
    public List<TblFeedcmt> findAll() {
        List<TblFeedcmt> listFeed = new ArrayList<>();
        try {
            con = connect();
            Statement stmt = con.createStatement();
            String query = "SELECT id, room_id, cmtcontent, cmtposdate,nik FROM tbl_feedcmt";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                TblFeedcmt feed = new TblFeedcmt();
                feed.setId(rs.getInt(1));
                feed.setRoomId(rs.getInt(2));
                feed.setCmtcontent(rs.getString(3));
                feed.setCmtposdate(rs.getString(4));
                TblUser user = new TblUser();
                user.setNik(rs.getInt(5));
                user = getUserByNik(user.getNik());
                feed.setNik(user);
                 

                listFeed.add(feed);
            }
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(FeedcmtService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listFeed;
    }

    private TblUser getUserByNik(Integer nik) {
        TblUser user = new TblUser();
        try {

            con = connect();
            Statement stmt = con.createStatement();
            String query = "SELECT nik, name, email, phone,plant,dept,pwd,img_user,tag,reg_date , gcm_token FROM tbl_user where nik = '" + nik + "'";
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

    private TblNewsfeed getFeedByroom(Integer roomId) {
        TblNewsfeed feed = new TblNewsfeed();
        try {
            con = connect();
            Statement stmt = con.createStatement();
            String query = "SELECT room_id, content,img_content, posdate, nik FROM tbl_newsfeed where room_id = '" + roomId + "'";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {

                feed.setRoomId(rs.getInt(1));
                feed.setContent(rs.getString(2));
                feed.setImgContent(rs.getString(3));
                feed.setPosdate(rs.getString(4));
                TblUser user = new TblUser();
                user.setNik(rs.getInt(5));
                user = getUserByNik(user.getNik());
                feed.setNik(user);
            }
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(NewsfeedService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return feed;
    }
    public String count(Integer roomId) {
        hasil = "";
        try {
            con = connect();
            String query = "SELECT COUNT(*) FROM tbl_feedcmt WHERE room_id = ?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1, roomId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                hasil = String.valueOf(rs.getInt(1));
            }
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(FeedcmtService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return hasil;
    }

}
