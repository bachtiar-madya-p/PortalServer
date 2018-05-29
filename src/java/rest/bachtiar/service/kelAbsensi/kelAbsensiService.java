/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest.bachtiar.service.kelAbsensi;

import java.sql.Connection;
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
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.springframework.transaction.annotation.Transactional;
import rest.bachtiar.service.EntityBase;
import rest.bachtiar.service.newsfeed.NewsfeedService;
import rest.bachtiar.service.user.TblUser;

/**
 *
 * @author Bachtiar M Permadi
 */
@Path("/absensi")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class kelAbsensiService extends EntityBase implements IkelAbsensiDataAccess<TblKelabsensi> {

    @POST
    @Path("/create")
    @Transactional
    @Override
    public Response create(TblKelabsensi entity) {
        try {
            con = connect();
            String query = "INSERT INTO tbl_kelabsensi (content, date, status, nik) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = con.prepareStatement(query);

            Date todaysDate = new Date();
            DateFormat df = new SimpleDateFormat("dd MMM yyyy");
            String date = df.format(todaysDate);
            String posDate = date;
            String status = "waiting";

            stmt.setString(1, entity.getContent());
            stmt.setString(2, posDate);
            stmt.setString(3, status);
            stmt.setInt(4, entity.getNik().getNik());
            int rs = stmt.executeUpdate();
            System.out.println(rs + " records inserted");
            if (rs != 0) {
                return Response.status(Response.Status.CREATED).build();

            } else {
                disconect();
                return Response.status(Response.Status.EXPECTATION_FAILED).build();
            }

        } catch (SQLException ex) {
            disconect();
            Logger.getLogger(kelAbsensiService.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
    @Override
    public Response edit(TblKelabsensi entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @DELETE
    @Path("/remove/{id}")
    @Transactional
    @Override
    public Response remove(@PathParam("id") Integer id) {
        try {
            con = connect();
            String query = "DELETE FROM tbl_kelabsensi  WHERE id = ?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1, id);
            //System.out.println(query);
            int rs = stmt.executeUpdate();
            if (rs != 0) {
                return Response.status(Response.Status.ACCEPTED).build();
            } else {
                return Response.status(Response.Status.EXPECTATION_FAILED).build();
            }
        } catch (SQLException ex) {
            Logger.getLogger(kelAbsensiService.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("find/status/{status}")
    @Transactional
    @Override
    public List<TblKelabsensi> findByTag(@PathParam("status") String status) {
        List<TblKelabsensi> listAbsensi = new ArrayList<>();
        try {
            con = connect();
            String query = "select id,content,date,status,nik from tbl_kelabsensi where status = ? order by id DESC";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, status);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                TblKelabsensi data = new TblKelabsensi();
                data.setId(rs.getInt(1));
                data.setContent(rs.getString(2));
                data.setDate(rs.getString(3));
                data.setStatus(rs.getString(4));
                TblUser user = new TblUser();
                user.setNik(rs.getInt(5));
                user = getUserByNik(user.getNik());
                data.setNik(user);
                listAbsensi.add(data);

            }
        } catch (SQLException ex) {
            Logger.getLogger(kelAbsensiService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listAbsensi;
    }

    @GET
    @Path("find/nik/{nik}")
    @Transactional
    @Override
    public List<TblKelabsensi> findByNik(@PathParam("nik") Integer nik) {
        List<TblKelabsensi> listAbsensi = new ArrayList<>();
        try {
            con = connect();
            String query = "select id,content,date,status,nik from tbl_kelabsensi where nik = ? order by id DESC";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1, nik);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                TblKelabsensi data = new TblKelabsensi();
                data.setId(rs.getInt(1));
                data.setContent(rs.getString(2));
                data.setDate(rs.getString(3));
                data.setStatus(rs.getString(4));
                TblUser user = new TblUser();
                user.setNik(rs.getInt(5));
                user = getUserByNik(user.getNik());
                data.setNik(user);
                listAbsensi.add(data);

            }
        } catch (SQLException ex) {
            Logger.getLogger(kelAbsensiService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listAbsensi;
    }

    @GET
    @Path("list")
    @Transactional
    @Override
    public List<TblKelabsensi> findAll() {
        List<TblKelabsensi> listAbsensi = new ArrayList<>();
        try {
            con = connect();
            Statement stmt = con.createStatement();
            String query = "select id,content,date,status,nik from tbl_kelabsensi order by id DESC";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                TblKelabsensi data = new TblKelabsensi();
                data.setId(rs.getInt(1));
                data.setContent(rs.getString(2));
                data.setDate(rs.getString(3));
                data.setStatus(rs.getString(4));
                TblUser user = new TblUser();
                user.setNik(rs.getInt(5));
                user = getUserByNik(user.getNik());
                data.setNik(user);
                listAbsensi.add(data);

            }
        } catch (SQLException ex) {
            Logger.getLogger(kelAbsensiService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listAbsensi;
    }    
    private TblUser getUserByNik(Integer nik) {
        TblUser user = new TblUser();
        try {

            con = connect();
            Statement stmt = con.createStatement();
            String query = "SELECT nik, name, email, phone,plant,dept,pwd,img_user,tag,reg_date FROM tbl_user where nik = '" + nik + "'";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                user.setNik(rs.getInt(1));
                user.setName(rs.getString(2));
                user.setEmail(rs.getString(3));
                user.setPhone(rs.getString(4));
                user.setPlant(rs.getString(5));
                user.setDept(rs.getString(6));
                user.setPwd(rs.getString(7));
                user.setImgUser(rs.getString(8));
                user.setTag(rs.getString(9));
                user.setRegDate(rs.getString(10));
            }

        } catch (SQLException ex) {
            Logger.getLogger(NewsfeedService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return user;
    }

}
