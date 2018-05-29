/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest.bachtiar.service.sketarangan;

import java.io.InputStream;
import java.util.List;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

/**
 *
 * @author root
 */
public interface IskUserDataAcccess<T> {

    public Response create(T entity);

    public Response uploadImageFile(@FormDataParam("file") InputStream fileInputStream,
            @FormDataParam("file") FormDataContentDisposition fileMetaData);

    public Response edit(T entity);

    public Response remove(@PathParam("id") Integer id);

    public List<T> findByNik(@PathParam("nik") Integer nik);

    public List<T> findByStatus(@PathParam("status") String status);

    public List<T> findAll();

    public T getLastId();
}
