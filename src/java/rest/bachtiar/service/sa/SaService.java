/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest.bachtiar.service.sa;

import java.net.URI;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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

/**
 *
 * @author Bachtiar M Permadi
 */
@Path("/superadmin")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SaService extends EntityBase implements ISaDataAccess<TblSa> {

    @POST
    @Path("/create")
    @Transactional
    @Override
    public Response create(TblSa entity) {
        try {
            con = connect();
            PreparedStatement stmt = con.prepareStatement("INSERT INTO tbl_sa (uname, pwd) VALUES ( ?, ?)");

            stmt.setString(1, entity.getUname());
            stmt.setString(2, entity.getPwd());
            int rs = stmt.executeUpdate();
            if (rs != 0) {
                return Response.status(Response.Status.ACCEPTED).build();
            } else {
                return Response.status(Response.Status.EXPECTATION_FAILED).build();
            }

        } catch (SQLException ex) {
            Logger.getLogger(SaService.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @PUT
    @Transactional
    @Path("/update")
    @Override
    public Response edit(TblSa entity) {
        try {
            con = connect();
            String query = "UPDATE tbl_sa SET uname = ? , pwd = ? WHERE id = ?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, entity.getUname());
            stmt.setString(2, entity.getPwd());
            stmt.setInt(3, entity.getId());
            int rs = stmt.executeUpdate();
            if (rs != 0) {
                return Response.status(Response.Status.ACCEPTED).build();
            } else {
                return Response.status(Response.Status.EXPECTATION_FAILED).build();
            }
        } catch (SQLException ex) {
            Logger.getLogger(SaService.class.getName()).log(Level.SEVERE, null, ex);
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
            String query = "DELETE FROM tbl_sa  WHERE id = ?";
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
            Logger.getLogger(SaService.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("find/id/{id}")
    @Transactional
    @Override
    public TblSa find(@PathParam("id") Integer id) {
       TblSa data = new TblSa();
      try {
            con = connect();
            String query = "SELECT * FROM tbl_sa Where id = ?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                data.setId(rs.getInt(1));
                data.setPwd(rs.getString(2));
                data.setUname(rs.getString(3));
            }
        } catch (SQLException ex) {
            Logger.getLogger(SaService.class.getName()).log(Level.SEVERE, null, ex);
        }
      return data;
    }

    @GET
    @Path("find/uname/{uname}")
    @Transactional
    @Override
    public TblSa findBynama(@PathParam("uname") String uname) {
         TblSa data = new TblSa();
      try {
            con = connect();
            String query = "SELECT * FROM tbl_sa Where uname = ?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, uname);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                data.setId(rs.getInt(1));
                data.setPwd(rs.getString(2));
                data.setUname(rs.getString(3));
            }
        } catch (SQLException ex) {
            Logger.getLogger(SaService.class.getName()).log(Level.SEVERE, null, ex);
        }
      return data;
    }

    @GET
    @Path("list")
    @Transactional
    @Override
    public List<TblSa> findAll() {
        List<TblSa> listSa = new ArrayList<>();
        try {
            con = connect();
            Statement stmt = con.createStatement();
            String query = "select * from tbl_sa";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                TblSa sa = new TblSa();
                sa.setId(rs.getInt(1));
                sa.setPwd(rs.getString(2));
                sa.setUname(rs.getString(3));

                listSa.add(sa);

            }
        } catch (SQLException ex) {
            Logger.getLogger(SaService.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return listSa;
    }

}
