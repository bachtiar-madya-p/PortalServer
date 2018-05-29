/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest.bachtiar.service.feedcmt;

import java.util.List;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

/**
 *
 * @author Bachtiar M Permadi
 */
public interface IFeedcmtDataAccess<T> {

    public Response create(T entity);

    public Response edit(T entity);

    public Response remove(@PathParam("id") Integer id);

    public List<T> findByRoom(@PathParam("room_id") Integer room_id);

    public List<T> findAll();

}
