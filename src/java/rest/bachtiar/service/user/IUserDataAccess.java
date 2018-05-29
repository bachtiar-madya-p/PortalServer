/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest.bachtiar.service.user;

import java.io.InputStream;
import java.util.List;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

/**
 *
 * @author Bachtiar M Permadi
 */
public interface IUserDataAccess<T> {

    public Response create(T entity);

    public Response edit(T entity);

    public Response remove(@PathParam("nik") Integer nik);

    public T find(@PathParam("nik") Integer nik);

    public T findBynama(@PathParam("name") String name);

    public List<T> findAll();

    public Response edit(@PathParam("nik") Integer nik, @PathParam("gcm_token") String gcm_token);

    public Response uploadImageFile(@FormDataParam("file") InputStream fileInputStream,
            @FormDataParam("file") FormDataContentDisposition fileMetaData);

}
