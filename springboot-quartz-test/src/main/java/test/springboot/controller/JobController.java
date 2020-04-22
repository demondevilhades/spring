package test.springboot.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
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
}
