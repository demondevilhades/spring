package hades.webtask.controller;

import hades.webtask.service.WebTask;
import hades.webtask.util.HttpUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/admin", method = RequestMethod.GET)
public class AdminController {

    @Value("${management.port}")
    private String managementPort;

    @Resource
    private WebTask webTask;

    @RequestMapping("/startupTask")
    public String startupTask() {
        return webTask.startupTask();
    }

    /**
     * http://localhost:8083/admin/shutdownTask
     * 
     * @return
     */
    @RequestMapping("/shutdownTask")
    public String shutdownTask() {
        return webTask.shutdownTask();
    }

    @RequestMapping("/taskStatus")
    public String taskStatus() {
        return webTask.taskStatus();
    }

    /**
     * http://127.0.0.1:8083/admin/shutdownApp
     * 
     * @param httpServletRequest
     * @return
     * @throws Exception
     */
    @RequestMapping("/shutdownApp")
    public String shutdownApp(HttpServletRequest httpServletRequest) throws Exception {
        String shutdownUrl = "http://" + httpServletRequest.getRemoteHost() + ":" + managementPort + "/shutdown";
        return HttpUtils.shutdownApp(shutdownUrl);
    }
}
