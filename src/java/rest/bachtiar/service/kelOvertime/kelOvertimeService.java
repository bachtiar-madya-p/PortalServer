/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest.bachtiar.service.kelOvertime;

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
import rest.bachtiar.service.kelCuti.TblKelcuti;
import rest.bachtiar.service.kelCuti.kelCutiService;
import rest.bachtiar.service.user.TblUser;

/**
 *
 * @author Bachtiar M Permadi
 */
@Path("/overtime")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class kelOvertimeService extends EntityBase implements IkelOvertimeDataAccess<TblKelovertime> {

    @POST
    @Path("/create")
    @Transactional
    @Override
    public Response create(TblKelovertime entity) {
        try {
            con = connect();
            PreparedStatement stmt = con.prepareStatement("INSERT INTO tbl_kelovertime (content, date, status, nik) VALUES (?, ?, ?, ?);");

            Date todaysDate = new Date();
            DateFormat df = new SimpleDateFormat("dd MMM yyyy");
            String date = df.format(todaysDate);
            String posDate = date;
            String tag = "waiting";

            stmt.setString(1, entity.getContent());
            stmt.setString(2, posDate);
            stmt.setString(3, tag);
            stmt.setInt(4, entity.getNik().getNik());
            int rs = stmt.executeUpdate();
            System.out.println(rs + " records inserted");
            if (rs != 0) {
                con.close();
                return Response.status(Response.Status.CREATED).build();
            } else {
                con.close();
                return Response.status(Response.Status.EXPECTATION_FAILED).build();
            }
        } catch (SQLException ex) {
            Logger.getLogger(kelOvertimeService.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @Override
    public Response edit(TblKelovertime entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @DELETE
    @Path("/remove/{id}")
    @Transactional
    @Override
    public Response remove(@PathParam("id") Integer id) {
        try {
            con = connect();
            String query = "DELETE FROM tbl_kelovertime  WHERE id = ?";
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
            Logger.getLogger(kelOvertimeService.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("find/nik/{nik}")
    @Transactional
    @Override
    public List<TblKelovertime> findByNik(@PathParam("nik") Integer nik) {
        List<TblKelovertime> listOvertime = new ArrayList<>();
        try {
            con = connect();
            String query = "select id,content,date,status,nik from tbl_kelovertime where nik = ? order by id DESC";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1, nik);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                TblKelovertime data = new TblKelovertime();
                data.setId(rs.getInt(1));
                data.setContent(rs.getString(2));
                data.setDate(rs.getString(3));
                data.setStatus(rs.getString(4));
                TblUser user = new TblUser();
                user.setNik(rs.getInt(5));
                user = getUserByNik(user.getNik());
                data.setNik(user);
                listOvertime.add(data);
            }
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(kelOvertimeService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listOvertime;
    }

    @GET
    @Path("find/status/{status}")
    @Transactional
    @Override
    public List<TblKelovertime> findByTag(@PathParam("status") String status) {
        List<TblKelovertime> listOvertime = new ArrayList<>();
        try {
            con = connect();
            String query = "select id,content,date,status,nik from tbl_kelovertime where status = ? order by id DESC";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, status);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                TblKelovertime data = new TblKelovertime();
                data.setId(rs.getInt(1));
                data.setContent(rs.getString(2));
                data.setDate(rs.getString(3));
                data.setStatus(rs.getString(4));
                TblUser user = new TblUser();
                user.setNik(rs.getInt(5));
                user = getUserByNik(user.getNik());
                data.setNik(user);
                listOvertime.add(data);
            }
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(kelOvertimeService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listOvertime;
    }

    @GET
    @Path("list")
    @Transactional
    @Override
    public List<TblKelovertime> findAll() {
        List<TblKelovertime> listOvertime = new ArrayList<>();
        try {
            con = connect();
            Statement stmt = con.createStatement();
            String query = "select id,content,date,status,nik from tbl_kelovertime order by id DESC";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                TblKelovertime data = new TblKelovertime();
                data.setId(rs.getInt(1));
                data.setContent(rs.getString(2));
                data.setDate(rs.getString(3));
                data.setStatus(rs.getString(4));
                TblUser user = new TblUser();
                user.setNik(rs.getInt(5));
                user = getUserByNik(user.getNik());
                data.setNik(user);
                listOvertime.add(data);
            }
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(kelOvertimeService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listOvertime;
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
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(kelOvertimeService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return user;
    }

}
