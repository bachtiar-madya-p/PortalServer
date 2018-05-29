/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest.bachtiar.service.message;

import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

/**
 *
 * @author root
 */
public interface IPushService {
    
    public Response sendPush(@PathParam("nik") Integer nik, @PathParam("message") String Message);
}
