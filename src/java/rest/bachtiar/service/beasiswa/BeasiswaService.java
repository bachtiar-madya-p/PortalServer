/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest.bachtiar.service.beasiswa;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.transaction.annotation.Transactional;
import rest.bachtiar.service.EntityBase;
import rest.bachtiar.service.FileLoc;
import rest.bachtiar.service.user.TblUser;

/**
 *
 * @author root
 */
@Path("/beasiswa")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BeasiswaService extends EntityBase implements IBeasiswaDataAccess<TblBeasiswa> {

    String ImageUri = FileLoc.BASE_URL + "service/beasiswa/";
    String ImageFolder = FileLoc.BASE_FOLDER + "service/beasiswa/";

    @POST
    @Path("/create")
    @Transactional
    @Override
    public Response create(TblBeasiswa entity) {
        try {
            con = connect();
            String query = "INSERT INTO tbl_beasiswa ( date, doc, nm_anak, status, ttl, nik) VALUES (?, ?, ?, ?, ?,?)";
            PreparedStatement stmt = con.prepareStatement(query);

            Date todaysDate = new Date();
            DateFormat df = new SimpleDateFormat("dd MMM yyyy");
            String date = df.format(todaysDate);
            String posDate = date;
            String status = "Menunggu";
            stmt.setString(1, posDate);
            stmt.setString(2, entity.getDoc());
            stmt.setString(3, entity.getNmAnak());
            stmt.setString(4, status);
            stmt.setString(5, entity.getTtl());
            stmt.setInt(6, entity.getNik().getNik());
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
            Logger.getLogger(BeasiswaService.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    @Path("/file")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Override
    public Response uploadImageFile(@FormDataParam("file") InputStream fileInputStream,
            @FormDataParam("file") FormDataContentDisposition fileMetaData) {
        try {
            writeToFileServer(fileInputStream, fileMetaData.getFileName());

        } catch (IOException e) {
            throw new WebApplicationException("Error while uploading file. Please try again !!");
        }
        return Response.status(Response.Status.CREATED).build();
    }
     private Boolean writeToFileServer(InputStream inputStream, String fileName) throws IOException {

        OutputStream outputStream = null;
        String qualifiedUploadFilePath = ImageFolder + fileName;

        File file = new File(qualifiedUploadFilePath);
        if (file.exists()) {
            file.delete();
        }
        file.createNewFile();
        outputStream = new FileOutputStream(file);
        int read = 0;
        byte[] bytes = new byte[1024];
        while ((read = inputStream.read(bytes)) != -1) {
            outputStream.write(bytes, 0, read);
        }
        outputStream.flush();
        return true;

    }

    @Override
    public Response edit(TblBeasiswa entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @DELETE
    @Path("{id}")
    @Transactional
    @Override
    public Response remove(@PathParam("id") Integer id) {
        try {
            con = connect();
            String query = "DELETE FROM tbl_beasiswa  WHERE id = ?";
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
            Logger.getLogger(BeasiswaService.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("find/nik/{nik}")
    @Transactional
    @Override
    public List<TblBeasiswa> findByNik(@PathParam("nik") Integer nik) {
        List<TblBeasiswa> listData = new ArrayList<>();
        try {
            con = connect();
            String query = "SELECT id,date,doc,nm_anak,status,ttl,nik FROM tbl_beasiswa WHERE nik = ? ORDER BY id DESC";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1, nik);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                TblBeasiswa data = new TblBeasiswa();
                data.setId(rs.getInt(1));
                data.setDate(rs.getString(2));
                data.setDoc(rs.getString(3));
                data.setNmAnak(rs.getString(4));
                data.setStatus(rs.getString(5));
                data.setTtl(rs.getString(6));
                TblUser user = new TblUser();
                user.setNik(rs.getInt(7));
                user = getUserByNik(user.getNik());
                data.setNik(user);
                listData.add(data);

            }
        } catch (SQLException ex) {
            Logger.getLogger(BeasiswaService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listData;
    }

    @GET
    @Path("find/status/{status}")
    @Transactional
    @Override
    public List<TblBeasiswa> findByStatus(@PathParam("status") String status) {
        List<TblBeasiswa> listData = new ArrayList<>();
        try {
            con = connect();
            String query = "SELECT id,date,doc,nm_anak,status,ttl,nik FROM tbl_beasiswa WHERE status = ? ORDER BY id DESC";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, status);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                TblBeasiswa data = new TblBeasiswa();
                data.setId(rs.getInt(1));
                data.setDate(rs.getString(2));
                data.setDoc(rs.getString(3));
                data.setNmAnak(rs.getString(4));
                data.setStatus(rs.getString(5));
                data.setTtl(rs.getString(6));
                TblUser user = new TblUser();
                user.setNik(rs.getInt(7));
                user = getUserByNik(user.getNik());
                data.setNik(user);
                listData.add(data);

            }
        } catch (SQLException ex) {
            Logger.getLogger(BeasiswaService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listData;
    }

    @GET
    @Path("list")
    @Transactional
    @Override
    public List<TblBeasiswa> findAll() {
        List<TblBeasiswa> listData = new ArrayList<>();
        try {
            con = connect();
            Statement stmt = con.createStatement();
            String query = "SELECT id,date,doc,nm_anak,status,ttl,nik FROM tbl_beasiswa ORDER BY id DESC ";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                TblBeasiswa data = new TblBeasiswa();
                data.setId(rs.getInt(1));
                data.setDate(rs.getString(2));
                data.setDoc(rs.getString(3));
                data.setNmAnak(rs.getString(4));
                data.setStatus(rs.getString(5));
                data.setTtl(rs.getString(6));
                TblUser user = new TblUser();
                user.setNik(rs.getInt(7));
                user = getUserByNik(user.getNik());
                data.setNik(user);
                getLastId();
                listData.add(data);

            }
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(BeasiswaService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listData;
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
            Logger.getLogger(BeasiswaService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return user;
    }
    @GET
    @Path("lastid")
    @Transactional
    @Override
    public TblBeasiswa getLastId(){
        TblBeasiswa data = new TblBeasiswa();
        int hasil = 0;
        try{
            con = connect();
            Statement stmt = con.createStatement();
            String query = "SELECT MAX(id) AS id FROM `tbl_beasiswa`";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next())
            {                
                data.setId(rs.getInt(1)+1);
            }   
            con.close();
        }
        catch(Exception ex)
        {
            Logger.getLogger(BeasiswaService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return data;
    }
    
}