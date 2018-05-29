/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest.bachtiar.service.bpjs;

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
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.transaction.annotation.Transactional;
import rest.bachtiar.service.EntityBase;
import rest.bachtiar.service.FileLoc;
import rest.bachtiar.service.beasiswa.BeasiswaService;
import rest.bachtiar.service.beasiswa.TblBeasiswa;
import rest.bachtiar.service.user.TblUser;

/**
 *
 * @author root
 */
@Path("/bpjs")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BpjsService extends EntityBase implements IBpjsDataAccess<TblBpjs> {

    String ImageUri = FileLoc.BASE_URL + "service/bpjs/";
    String ImageFolder = FileLoc.BASE_FOLDER + "service/bpjs/";

    @POST
    @Path("/create")
    @Transactional
    @Override
    public Response create(TblBpjs entity) {
        try {
            con = connect();
            String query = "INSERT INTO tbl_bpjs(date, nokk, noktp, doc, status, nik) VALUES (?, ?, ?, ?,?,?)";
            PreparedStatement stmt = con.prepareStatement(query);

            Date todaysDate = new Date();
            DateFormat df = new SimpleDateFormat("dd MMM yyyy");
            String date = df.format(todaysDate);
            String posDate = date;
            String status = "waiting";
            stmt.setString(1, posDate);
            stmt.setString(2, entity.getNokk());
            stmt.setString(3, entity.getNoktp());
            stmt.setString(4, entity.getDoc());
            stmt.setString(5, status);
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
            Logger.getLogger(BpjsService.class.getName()).log(Level.SEVERE, null, ex);
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

    @Override
    public Response edit(TblBpjs entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @DELETE
    @Path("{id}")
    @Transactional
    @Override
    public Response remove(@PathParam("id") Integer id) {
        try {
            con = connect();
            String query = "DELETE FROM tbl_bpjs  WHERE id = ?";
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
            Logger.getLogger(BpjsService.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("find/nik/{nik}")
    @Transactional
    @Override
    public List<TblBpjs> findByNik(@PathParam("nik") Integer nik) {
        List<TblBpjs> listData = new ArrayList<>();
        try {
            con = connect();
            String query = "SELECT id, date,nokk, noktp, doc, nik, status FROM tbl_bpjs WHERE nik = ? ORDER BY id DESC";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1, nik);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                TblBpjs data = new TblBpjs();
                data.setId(rs.getInt(1));
                data.setDate(rs.getString(2));
                data.setNokk(rs.getString(3));
                data.setNoktp(rs.getString(4));
                data.setDoc(rs.getString(5));
                TblUser user = new TblUser();
                user.setNik(rs.getInt(6));
                user = getUserByNik(user.getNik());
                data.setNik(user);
                data.setStatus(rs.getString(7));

                listData.add(data);

            }
        } catch (SQLException ex) {
            Logger.getLogger(BpjsService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listData;
    }

    @GET
    @Path("find/status/{status}")
    @Transactional
    @Override
    public List<TblBpjs> findByStatus(@PathParam("status") String status) {
        List<TblBpjs> listData = new ArrayList<>();
        try {
            con = connect();
            String query = "SELECT id, date,nokk, noktp, doc, nik, status FROM tbl_bpjs WHERE status = ? ORDER BY id DESC";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, status);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                TblBpjs data = new TblBpjs();
                data.setId(rs.getInt(1));
                data.setDate(rs.getString(2));
                data.setNokk(rs.getString(3));
                data.setNoktp(rs.getString(4));
                data.setDoc(rs.getString(5));
                TblUser user = new TblUser();
                user.setNik(rs.getInt(6));
                user = getUserByNik(user.getNik());
                data.setNik(user);
                data.setStatus(rs.getString(7));

                listData.add(data);

            }
        } catch (SQLException ex) {
            Logger.getLogger(BpjsService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listData;
    }

    @GET
    @Path("list")
    @Transactional
    @Override
    public List<TblBpjs> findAll() {
        List<TblBpjs> listData = new ArrayList<>();
        try {
            con = connect();
            Statement stmt = con.createStatement();
            String query = "SELECT id, date,nokk, noktp, doc, nik, status FROM tbl_bpjs ORDER BY id DESC ";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                TblBpjs data = new TblBpjs();
                data.setId(rs.getInt(1));
                data.setDate(rs.getString(2));
                data.setNokk(rs.getString(3));
                data.setNoktp(rs.getString(4));
                data.setDoc(rs.getString(5));
                TblUser user = new TblUser();
                user.setNik(rs.getInt(6));
                user = getUserByNik(user.getNik());
                data.setNik(user);
                data.setStatus(rs.getString(7));

                listData.add(data);

            }
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(BpjsService.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(BpjsService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return user;
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

    @GET
    @Path("lastid")
    @Transactional
    @Override
    public TblBpjs getLastId() {
        TblBpjs data = new TblBpjs();
        int hasil = 0;
        try {
            con = connect();
            Statement stmt = con.createStatement();
            String query = "SELECT MAX(id) AS id FROM tbl_bpjs";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                data.setId(rs.getInt(1) + 1);
            }
            con.close();
        } catch (Exception ex) {
            Logger.getLogger(BpjsService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return data;
    }

}
