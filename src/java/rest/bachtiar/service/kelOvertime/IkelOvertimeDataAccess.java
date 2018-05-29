/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest.bachtiar.service.kelOvertime;

import java.util.List;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

/**
 *
 * @author Bachtiar M Permadi
 */
public interface IkelOvertimeDataAccess<T> {

    public Response create(T entity);

    public Response edit(T entity);

    public Response remove(@PathParam("id") Integer id);

    public List<T> findByNik(@PathParam("nik") Integer nik);

    public List<T> findByTag(@PathParam("tag") String tag);

    public List<T> findAll();
}
