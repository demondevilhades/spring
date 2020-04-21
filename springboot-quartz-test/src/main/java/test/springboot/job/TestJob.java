package test.springboot.job;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import test.springboot.util.TaskUtils;

/**
 * TestTask
 */
@Slf4j
@Component
@DisallowConcurrentExecution
@PersistJobDataAfterExecution
public class TestJob implements Job {

    private final String ymdhms = "yyyy-MM-dd HH:mm:ss";

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("TestJob.execute : PreviousFireTime={}, ScheduledFireTime={}, FireTime={}, NextFireTime={}",
                (jobExecutionContext.getPreviousFireTime() == null ? null
                        : DateFormatUtils.format(jobExecutionContext.getPreviousFireTime(), ymdhms)),
                DateFormatUtils.format(jobExecutionContext.getScheduledFireTime(), ymdhms),
                DateFormatUtils.format(jobExecutionContext.getFireTime(), ymdhms),
                DateFormatUtils.format(jobExecutionContext.getNextFireTime(), ymdhms));
        log.info(jobExecutionContext.getJobDetail().getJobDataMap().getString(TaskUtils.JOB_DATA_KEY));
    }
}
