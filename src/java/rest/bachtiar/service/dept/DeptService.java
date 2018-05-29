/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest.bachtiar.service.dept;

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
import rest.bachtiar.service.sa.SaService;

/**
 *
 * @author Bachtiar M Permadi
 */
@Path("/dept")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DeptService extends EntityBase implements IDeptDataAccess<TblDept> {

    @POST
    @Path("/create")
    @Transactional
    @Override
    public Response create(TblDept entity) {
        try {
            con = connect();
            PreparedStatement stmt = con.prepareStatement("INSERT INTO tbl_dept (dept) VALUES (?)");

            stmt.setString(1, entity.getDept());
            int rs = stmt.executeUpdate();
            if (rs != 0) {
                con.close();
                return Response.status(Response.Status.CREATED).build();
            } else {
                con.close();
                return Response.status(Response.Status.EXPECTATION_FAILED).build();
            }

        } catch (SQLException ex) {
            Logger.getLogger(DeptService.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @PUT
    @Transactional
    @Path("/update")
    @Override
    public Response edit(TblDept entity) {
        try {
            con = connect();
            String query = "UPDATE tbl_dept SET dept = ? WHERE id_dept = ?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, entity.getDept());
            stmt.setInt(2, entity.getIdDept());
            int rs = stmt.executeUpdate();
            if (rs != 0) {
                con.close();
                return Response.status(Response.Status.ACCEPTED).build();
            } else {
                con.close();
                return Response.status(Response.Status.EXPECTATION_FAILED).build();
            }
        } catch (SQLException ex) {
            Logger.getLogger(DeptService.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(Response.Status.NOT_FOUND).build();
        }

    }

    @DELETE
    @Transactional
    @Path("/remove/{id_dept}")
    @Override
    public Response remove(@PathParam("id_dept") Integer idDept) {
        try {
            con = connect();
            String query = "DELETE FROM tbl_dept  WHERE id_dept = ?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1, idDept);
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
            Logger.getLogger(SaService.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("list")
    @Transactional
    @Override
    public List<TblDept> findAll() {
        List<TblDept> listDept = new ArrayList<>();
        try {
            con = connect();
            Statement stmt = con.createStatement();
            String query = "SELECT id_dept,dept FROM tbl_dept";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                TblDept dept = new TblDept();
                dept.setIdDept(rs.getInt(1));
                dept.setDept(rs.getString(2));
                listDept.add(dept);
            }
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(SaService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listDept;
    }

}
