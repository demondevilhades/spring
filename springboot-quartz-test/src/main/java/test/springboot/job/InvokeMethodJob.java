package test.springboot.job;

import org.apache.commons.lang3.StringUtils;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

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

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("InvokeMethodJob.execute");
        String str = jobExecutionContext.getJobDetail().getJobDataMap().getString(TaskUtils.JOB_DATA_KEY);
        try {
            InvokeMethodJobData invokeMethodJobData = new Gson().fromJson(str, InvokeMethodJobData.class);
            String className = invokeMethodJobData.getClassName();
            if (StringUtils.isNoneEmpty(className)) {
                Class<?> clazz = Class.forName(invokeMethodJobData.getClassName());
                if (clazz != null) {
                    Object instance = MethodHandleUtils.newInstance(clazz);
                    if (instance != null) {
                        if (StringUtils.isEmpty(invokeMethodJobData.getArg())) {
                            MethodHandleUtils.invockMethod(clazz, invokeMethodJobData.getMethodName(), void.class,
                                    instance);
                        } else {
                            MethodHandleUtils.invockMethod(clazz, invokeMethodJobData.getMethodName(), String.class,
                                    void.class, instance, invokeMethodJobData.getArg());
                        }
                    }
                }
            }
        } catch (JsonSyntaxException e) {
            log.error("", e);
        } catch (ClassNotFoundException e) {
            log.error("", e);
        } catch (Throwable e) {
            log.error("", e);
        }
    }

    @Slf4j
    public static class TestInvokeMethod {
        public void test() {
            log.info("TestInvokeMethod.test");
        }
    }
}
