/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest.bachtiar.service.user;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import org.springframework.transaction.annotation.Transactional;
import rest.bachtiar.service.EntityBase;
import rest.bachtiar.service.FileLoc;

/**
 *
 * @author Bachtiar M Permadi
 */
@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserService extends EntityBase implements IUserDataAccess<TblUser> {

    public final static String nameFile = FileLoc.BASE_URL + "client/";
    public static final String UPLOAD_FILE_SERVER = FileLoc.BASE_FOLDER + "client/";

    @POST
    @Path("/create")
    @Transactional
    @Override
    public Response create(TblUser entity) {
        try {
            con = connect();
            PreparedStatement stmt = con.prepareStatement("INSERT INTO tbl_user (nik, name, email, phone, plant, dept, pwd, img_user, tag, reg_date) VALUES ( ?, ? ,? ,? ,? ,? ,? ,? ,? ,?)");
            Date todaysDate = new Date();
            DateFormat df = new SimpleDateFormat("dd MMM yyyy");
            String date = df.format(todaysDate);
            String posDate = date;
            String DefImage = FileLoc.BASE_URL + "add_photo.png";
            String tag = "user";

            stmt.setInt(1, entity.getNik());
            stmt.setString(2, entity.getName());
            stmt.setString(3, entity.getEmail());
            stmt.setString(4, entity.getPhone());
            stmt.setString(5, entity.getPlant());
            stmt.setString(6, entity.getDept());
            stmt.setString(7, entity.getPwd());
            stmt.setString(8, DefImage);
            stmt.setString(9, tag);
            stmt.setString(10, posDate);
            //System.out.println(query);
            int rs = stmt.executeUpdate();
            System.out.println(rs + " records inserted");
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

    @DELETE
    @Path("/remove/{nik}")
    @Transactional
    @Override
    public Response remove(@PathParam("nik") Integer nik) {
        try {
            con = connect();
            String query = "DELETE FROM tbl_user  WHERE nik = ?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1, nik);
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

    @GET
    @Path("/find/nik/{nik}")
    @Transactional
    @Override
    public TblUser find(@PathParam("nik") Integer nik) {
        TblUser user = new TblUser();
        try {
            con = connect();
            String query = "SELECT nik,name,email,phone,plant,dept,pwd,img_user,tag,reg_date FROM tbl_user Where nik = ?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1, nik);
            ResultSet rs = stmt.executeQuery();
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

            }
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return user;
    }

    @GET
    @Path("/find/name/{name}")
    @Transactional
    @Override
    public TblUser findBynama(@PathParam("name") String name) {
        TblUser user = new TblUser();
        try {
            con = connect();
            String query = "SELECT * FROM tbl_user Where name LIKE ?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
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
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return user;
    }

    @GET
    @Path("list")
    @Transactional
    @Override
    public List<TblUser> findAll() {
        List<TblUser> listUser = new ArrayList<>();
        con = connect();
        try {

            Statement stmt = con.createStatement();
            String query = "SELECT nik, name, email, phone,plant,dept,reg_date,img_user,pwd,gcm_token,tag FROM tbl_user ORDER BY nik";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                TblUser user = new TblUser();
                user.setNik(rs.getInt(1));
                user.setName(rs.getString(2));
                user.setEmail(rs.getString(3));
                user.setPhone(rs.getString(4));
                user.setPlant(rs.getString(5));
                user.setDept(rs.getString(6));
                user.setRegDate(rs.getString(7));
                user.setImgUser(FileLoc.BASE_URL + rs.getString(8));
                user.setPwd(rs.getString(9));
                user.setGcmToken(rs.getString(10));
                user.setTag(rs.getString(11));
                listUser.add(user);

            }
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listUser;
    }

    @PUT
    @Transactional
    @Override
    public Response edit(TblUser entity) {

        try {
            con = connect();
            String query = "UPDATE tbl_user SET name = ? , email = ?, phone = ?, plant = ?, dept = ?, pwd = ?, tag = ? WHERE nik = ?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, entity.getName());
            stmt.setString(2, entity.getEmail());
            stmt.setString(3, entity.getPhone());
            stmt.setString(5, entity.getPlant());
            stmt.setString(6, entity.getDept());
            stmt.setString(7, entity.getPwd());
            stmt.setString(8, entity.getTag());
            stmt.setInt(8, entity.getNik());
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

    @POST
    @Path("/image")
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

    /**
     *
     * @param inputStream
     * @param fileName
     * @throws IOException
     */
    private Boolean writeToFileServer(InputStream inputStream, String fileName) throws IOException {

        OutputStream outputStream = null;
        String qualifiedUploadFilePath = UPLOAD_FILE_SERVER + fileName;

        String filenameArray[] = fileName.split("\\.");
        Boolean success = updateUserImageName(Integer.valueOf(filenameArray[0]), fileName);
        if (success) {
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
        } else {
            return false;
        }

    }

    private Boolean updateUserImageName(int nik, String imgUser) {
        try {
            con = connect();
            String query = "UPDATE tbl_user SET img_user = ? WHERE nik = ?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, nameFile + imgUser);
            stmt.setInt(2, nik);
            //System.out.println(query);
            int rs = stmt.executeUpdate();
            if (rs != 0) {
                disconect();
                return true;
            } else {
                disconect();
                return false;
            }

        } catch (SQLException ex) {
            disconect();
            return false;
        }
    }

    @PUT
    @Path("{nik}/{gcm_token}")
    @Transactional
    @Override
    public Response edit(@PathParam("nik") Integer nik, @PathParam("gcm_token") String gcm_token) {
        try {
            con = connect();
            String query = "UPDATE tbl_user SET gcm_token = ?  WHERE nik = ?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, gcm_token);
            stmt.setInt(2, nik);
            //System.out.println(query);
            int rs = stmt.executeUpdate();
            if (rs != 0) {
                disconect();
                return Response.created(URI.create("")).entity(rs).build();
            } else {
                disconect();
                return Response.status(Response.Status.EXPECTATION_FAILED).build();
            }

        } catch (SQLException ex) {
            disconect();
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
