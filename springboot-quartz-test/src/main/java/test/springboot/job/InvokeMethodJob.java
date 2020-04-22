package test.springboot.job;

import org.apache.commons.lang3.StringUtils;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

import lombok.extern.slf4j.Slf4j;
import test.springboot.bean.InvokeMethodJobData;
import test.springboot.util.MethodHandleUtils;
import test.springboot.util.TaskUtils;

/**
 * InvokeMethodJob
 */
@Slf4j
@Component
@DisallowConcurrentExecution
@PersistJobDataAfterExecution
public class InvokeMethodJob implements Job {

    private boolean inited = false;
    private Class<?> clazz = null;
    private Object instance = null;
    private String methodName = null;
    private String arg = null;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("InvokeMethodJob.execute");
        if (!inited) {
            try {
                String str = jobExecutionContext.getJobDetail().getJobDataMap().getString(TaskUtils.JOB_DATA_KEY);
                InvokeMethodJobData invokeMethodJobData = new Gson().fromJson(str, InvokeMethodJobData.class);
                String className = invokeMethodJobData.getClassName();
                methodName = invokeMethodJobData.getMethodName();
                arg = invokeMethodJobData.getArg();
                if (StringUtils.isNoneEmpty(className)) {
                    clazz = Class.forName(invokeMethodJobData.getClassName());
                    if (clazz != null) {
                        instance = MethodHandleUtils.newInstance(clazz);
                    } else {
                        log.error("class is null");
                    }
                } else {
                    log.error("className is empty");
                }
            } catch (Throwable e) {
                log.error("", e);
            }
            inited = true;
        }
        if (instance != null) {
            try {
                if (StringUtils.isEmpty(arg)) {
                    MethodHandleUtils.invockMethod(clazz, methodName, void.class, instance);
                } else {
                    MethodHandleUtils.invockMethod(clazz, methodName, String.class, void.class, instance, arg);
                }
            } catch (Throwable e) {
                
                log.error("", e);
            }
        } else {
            log.error("instance is null");
        }
    }
}
