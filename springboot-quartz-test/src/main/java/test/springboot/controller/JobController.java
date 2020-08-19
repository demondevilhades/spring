package test.springboot.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import test.springboot.service.TaskService;

@Api("job")
@RestController
@RequestMapping("/job")
public class JobController {
    @Resource
    private TaskService taskService;

    @GetMapping("/getJobInfoList")
    @ApiOperation("getJobInfoList")
    public String getJobInfoList() {
        return new Gson().toJson(taskService.getJobInfoList());
    }

    /**
     * 
     */
    @PostMapping("/flushQuartz")
    @ApiOperation("flushQuartz")
    public String flushQuartz() {
        try {
            taskService.flushQuartz();
            return "SUCCESS";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    /**
     * 
     */
    @PostMapping("/scheduler/pauseAll")
    @ApiOperation("pauseAll")
    public String pauseAll() {
        try {
            taskService.pauseAll();
            return "SUCCESS";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    /**
     * 
     */
    @PostMapping("/scheduler/resumeAll")
    @ApiOperation("resumeAll")
    public String resumeAll() {
        try {
            taskService.resumeAll();
            return "SUCCESS";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    /**
     * 
     */
    @PostMapping("/scheduler/standby")
    @ApiOperation("standby")
    public String standby() {
        try {
            taskService.standby();
            return "SUCCESS";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    /**
     * 
     */
    @PostMapping("/scheduler/start")
    @ApiOperation("start")
    public String start() {
        try {
            taskService.start();
            return "SUCCESS";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    /**
     * 
     */
    @PostMapping("/scheduler/shutdownAndWaitForJobsToComplete")
    @ApiOperation("shutdownAndWaitForJobsToComplete")
    public String shutdownAndWaitForJobsToComplete() {
        try {
            taskService.shutdown(true);
            return "SUCCESS";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    /**
     * 
     * @param t
     * @return
     */
    @ExceptionHandler
    public String exceptionHandler(Throwable t) {
        return t.getMessage();
    }
}
