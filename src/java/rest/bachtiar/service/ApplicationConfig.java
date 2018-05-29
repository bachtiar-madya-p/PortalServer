/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest.bachtiar.service;

import java.util.Set;
import javax.ws.rs.core.Application;
import org.glassfish.jersey.media.multipart.MultiPartFeature;

/**
 *
 * @author root
 */
@javax.ws.rs.ApplicationPath("api")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        final Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        resources.add(MultiPartFeature.class);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        
        resources.add(rest.bachtiar.service.LoginService.class);
        resources.add(rest.bachtiar.service.RegisterService.class);
        resources.add(rest.bachtiar.service.beasiswa.BeasiswaService.class);
        resources.add(rest.bachtiar.service.bpjs.BpjsService.class);
        resources.add(rest.bachtiar.service.dept.DeptService.class);
        resources.add(rest.bachtiar.service.errorhandling.AppExceptionMapper.class);
        resources.add(rest.bachtiar.service.errorhandling.CustomReasonPhraseExceptionMapper.class);
        resources.add(rest.bachtiar.service.errorhandling.GenericExceptionMapper.class);
        resources.add(rest.bachtiar.service.errorhandling.NotFoundExceptionMapper.class);
        resources.add(rest.bachtiar.service.feedback.FeedbackService.class);
        resources.add(rest.bachtiar.service.feedcmt.FeedcmtService.class);
        resources.add(rest.bachtiar.service.idcard.IdcardService.class);
        resources.add(rest.bachtiar.service.kelAbsensi.kelAbsensiService.class);
        resources.add(rest.bachtiar.service.kelCuti.kelCutiService.class);
        resources.add(rest.bachtiar.service.kelOvertime.kelOvertimeService.class);
        resources.add(rest.bachtiar.service.message.PushService.class);
        resources.add(rest.bachtiar.service.newsfeed.NewsfeedService.class);
        resources.add(rest.bachtiar.service.ping.PingService.class);
        resources.add(rest.bachtiar.service.plant.PlantService.class);
        resources.add(rest.bachtiar.service.sa.SaService.class);
        resources.add(rest.bachtiar.service.sketarangan.skUserService.class);
        resources.add(rest.bachtiar.service.status.StatusService.class);
        resources.add(rest.bachtiar.service.user.UserService.class);
    }
    
    
}
