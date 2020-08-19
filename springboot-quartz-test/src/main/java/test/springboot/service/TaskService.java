package test.springboot.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import test.springboot.bean.TaskConfig;
import test.springboot.dao.TaskConfigDao;
import test.springboot.util.TaskUtils;

@Slf4j
@Service
public class TaskService {

    @Resource
    public SchedulerFactoryBean schedulerFactoryBean;

    @Resource
    private TaskConfigDao taskConfigDao;

    private Scheduler scheduler;

    @PostConstruct
    public void init() throws SchedulerException, ClassNotFoundException {
        if (scheduler == null) {
            scheduler = schedulerFactoryBean.getScheduler();
        }

        List<TaskConfig> taskConfigList = taskConfigDao.getAllTaskConfig();
        for (TaskConfig taskConfig : taskConfigList) {
            log.info(taskConfig.toString());
            addJob(taskConfig);
        }
    }

    public void addJob(TaskConfig taskConfig) throws SchedulerException, ClassNotFoundException {
        TriggerKey triggerKey = TriggerKey.triggerKey(taskConfig.getTriggerName(), taskConfig.getTriggerGroup());
        CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
        if (trigger == null) {
            @SuppressWarnings("unchecked")
            Class<? extends Job> jobClass = (Class<? extends Job>) Class.forName(taskConfig.getBeanClass());
            JobDetail jobDetail = JobBuilder.newJob(jobClass)
                    .withIdentity(taskConfig.getJobName(), taskConfig.getJobGroup())
                    .usingJobData(TaskUtils.JOB_DATA_KEY, taskConfig.getJobData()).build();

            scheduler.scheduleJob(jobDetail, buildTrigger(trigger, taskConfig, triggerKey));
        } else {
            scheduler.rescheduleJob(triggerKey, buildTrigger(trigger, taskConfig, triggerKey));
        }
    }

    private CronTrigger buildTrigger(CronTrigger trigger, TaskConfig taskConfig, TriggerKey triggerKey) {
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(taskConfig.getCronExpression());
        switch (taskConfig.getMisfireHandlingInstruction()) {
        case CronTrigger.MISFIRE_INSTRUCTION_DO_NOTHING:
            scheduleBuilder.withMisfireHandlingInstructionDoNothing();
            break;
        case CronTrigger.MISFIRE_INSTRUCTION_FIRE_ONCE_NOW:
            scheduleBuilder.withMisfireHandlingInstructionFireAndProceed();
            break;
        case CronTrigger.MISFIRE_INSTRUCTION_IGNORE_MISFIRE_POLICY:
            scheduleBuilder.withMisfireHandlingInstructionIgnoreMisfires();
            break;
        case CronTrigger.MISFIRE_INSTRUCTION_SMART_POLICY:
            break;
        default:
        }
        TriggerBuilder<CronTrigger> triggerBuilder = trigger == null
                ? TriggerBuilder.newTrigger().withSchedule(scheduleBuilder)
                : trigger.getTriggerBuilder().withSchedule(scheduleBuilder);
        triggerBuilder.withIdentity(triggerKey).withDescription(taskConfig.getDescription())
                .usingJobData(TaskUtils.JOB_DATA_KEY, taskConfig.getJobData());
        if (taskConfig.getStartTime() != null) {
            triggerBuilder.startAt(taskConfig.getStartTime());
        }
        if (taskConfig.getEndTime() != null) {
            triggerBuilder.endAt(taskConfig.getEndTime());
        }
        if (taskConfig.getStartNow() != null && taskConfig.getStartNow() != 0) {
            triggerBuilder.startNow();
        }
        if (taskConfig.getPriority() != null && taskConfig.getPriority() != 0) {
            triggerBuilder.withPriority(taskConfig.getPriority());
        }
        return triggerBuilder.build();
    }

    public List<TaskConfig> getJobInfoList() {
        List<TaskConfig> taskConfigList = new ArrayList<>();
        try {
            Set<JobKey> jobKeySet = scheduler.getJobKeys(GroupMatcher.anyJobGroup());
            for (JobKey jobKey : jobKeySet) {
                JobDetail jobDetail = scheduler.getJobDetail(jobKey);

                List<? extends Trigger> triggerList = scheduler.getTriggersOfJob(jobKey);
                for (Trigger trigger : triggerList) {
                    TriggerKey triggerKey = trigger.getKey();
                    TaskConfig taskConfig = new TaskConfig();
                    taskConfig.setBeanClass(jobDetail.getJobClass().toString());
                    taskConfig.setDescription(jobDetail.getDescription());
                    taskConfig.setJobData(jobDetail.getJobDataMap().getString(TaskUtils.JOB_DATA_KEY));
                    taskConfig.setJobGroup(jobKey.getGroup());
                    taskConfig.setJobName(jobKey.getName());
                    taskConfig.setTriggerName(triggerKey.getName());
                    taskConfig.setTriggerGroup(triggerKey.getGroup());
                    if (trigger instanceof CronTrigger) {
                        CronTrigger cronTrigger = (CronTrigger) trigger;
                        taskConfig.setCronExpression(cronTrigger.getCronExpression());
                        taskConfig.setStartTime(cronTrigger.getStartTime());
                        taskConfig.setEndTime(cronTrigger.getEndTime());
                        taskConfig.setPriority(cronTrigger.getPriority());

//                        cronTrigger.getFinalFireTime();
//                        cronTrigger.getPreviousFireTime();
//                        cronTrigger.getNextFireTime();
                    }
                    taskConfig.setStatus(TaskConfig.Status.NORMAL);
                    taskConfigList.add(taskConfig);
                }
            }
        } catch (SchedulerException e) {
            log.error("", e);
        }
        return taskConfigList;
    }

    /**
     * @throws SchedulerException
     * @throws ClassNotFoundException
     * 
     */
    @Transactional(rollbackFor = Exception.class)
    public void flushQuartz() throws ClassNotFoundException, SchedulerException {
        scheduler.standby();
        scheduler.clear();
        init();
        scheduler.start();
    }

    /**
     * 
     * @throws SchedulerException
     */
    public void pauseAll() throws SchedulerException {
        scheduler.pauseAll();
    }

    /**
     * 
     * @throws SchedulerException
     */
    public void resumeAll() throws SchedulerException {
        scheduler.resumeAll();
    }

    /**
     * 
     * @throws SchedulerException
     */
    public void standby() throws SchedulerException {
        scheduler.standby();
    }

    /**
     * 
     * @throws SchedulerException
     */
    public void start() throws SchedulerException {
        scheduler.start();
    }

    /**
     * 
     * @param waitForJobsToComplete
     * @throws SchedulerException
     */
    public void shutdown(boolean waitForJobsToComplete) throws SchedulerException {
        scheduler.shutdown(waitForJobsToComplete);
    }
}
