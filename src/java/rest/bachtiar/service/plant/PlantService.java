/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest.bachtiar.service.plant;

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
@Path("/plant")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PlantService extends EntityBase implements IPlantDataAccess<TblPlant>{

     @POST
    @Path("/create")
    @Transactional
    @Override
    public Response create(TblPlant entity) {
         try {
            con = connect();
            PreparedStatement stmt = con.prepareStatement("INSERT INTO tbl_plant (plant) VALUES ( ?)");

            stmt.setString(1, entity.getPlant());
            int rs = stmt.executeUpdate();
            if (rs != 0) {
                con.close();
                return Response.status(Response.Status.ACCEPTED).build();
            } else {
                con.close();
                return Response.status(Response.Status.EXPECTATION_FAILED).build();
            }

        } catch (SQLException ex) {
            Logger.getLogger(PlantService.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @PUT
    @Transactional
    @Path("/update")
    @Override
    public Response edit(TblPlant entity) {
         try {
            con = connect();
            String query = "UPDATE tbl_plant SET plant = ? WHERE id_plant = ?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, entity.getPlant());
            stmt.setInt(2, entity.getIdPlant());
            int rs = stmt.executeUpdate();
            if (rs != 0) {
                con.close();
                return Response.status(Response.Status.ACCEPTED).build();
            } else {
                con.close();
                return Response.status(Response.Status.EXPECTATION_FAILED).build();
            }
        } catch (SQLException ex) {
            Logger.getLogger(PlantService.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @DELETE
    @Transactional
    @Path("/remove/{id_plant}")
    @Override
    public Response remove(@PathParam("id_plant")Integer idPlant) {
        try {
            con = connect();
            String query = "DELETE FROM tbl_plant  WHERE id_plant = ?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1, idPlant);
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
            Logger.getLogger(PlantService.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("list")
    @Transactional
    @Override
    public List<TblPlant> findAll() {
        List<TblPlant> listPlant = new ArrayList<>();
        try {
            con = connect();
            Statement stmt = con.createStatement();
            String query = "select * from tbl_plant";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                TblPlant plant = new TblPlant();
                plant.setPlant(rs.getString(1));
                plant.setIdPlant(rs.getInt(2));
                listPlant.add(plant);
            }
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(PlantService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listPlant;
    }
    
}
