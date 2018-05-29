/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest.bachtiar.service.sa;

import java.util.List;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

/**
 *
 * @author Bachtiar M Permadi
 */
public interface ISaDataAccess <T>{
    public Response create(T entity);

    public Response edit(T entity);

    public Response remove(@PathParam("id") Integer id);

    public T find(@PathParam("id") Integer id);

    public T findBynama(@PathParam("uname") String uname);
    
    public List <T> findAll();
    
}
