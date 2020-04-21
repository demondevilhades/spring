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
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import test.springboot.bean.TaskConfig;
import test.springboot.dao.TaskConfigDao;
import test.springboot.util.TaskUtils;

@Slf4j
@Component
public class QuartzService {

    @Resource
    public SchedulerFactoryBean schedulerFactoryBean;

    @Resource
    private TaskConfigDao taskConfigDao;

    private Scheduler scheduler;

    @PostConstruct
    public void init() throws SchedulerException, ClassNotFoundException {
        scheduler = schedulerFactoryBean.getScheduler();

        List<TaskConfig> taskConfigList = taskConfigDao.getAllTaskConfig();
        for (TaskConfig taskConfig : taskConfigList) {
            log.info(taskConfig.toString());
            addJob(taskConfig);
        }
    }

    public void addJob(TaskConfig taskConfig) throws SchedulerException, ClassNotFoundException {
        TriggerKey triggerKey = TriggerKey.triggerKey(taskConfig.getTriggerName(), taskConfig.getJobGroup());

        CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);

        // 不存在，创建一个
        if (null == trigger) {
            @SuppressWarnings("unchecked")
            Class<? extends Job> jobClass = (Class<? extends Job>) Class.forName(taskConfig.getBeanClass());
            JobDetail jobDetail = JobBuilder.newJob(jobClass)
                    .withIdentity(taskConfig.getJobName(), taskConfig.getJobGroup())
                    .usingJobData("data", taskConfig.getJobData()).build();

            jobDetail.getJobDataMap().put("scheduleJob", taskConfig);

            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(taskConfig.getCronExpression());

            trigger = TriggerBuilder.newTrigger().withDescription(taskConfig.getDescription())
                    .withIdentity(taskConfig.getTriggerName(), taskConfig.getJobGroup()).withSchedule(scheduleBuilder)
                    .build();

            scheduler.scheduleJob(jobDetail, trigger);
        } else {
            // Trigger已存在，那么更新相应的定时设置
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(taskConfig.getCronExpression());

            // 按新的cronExpression表达式重新构建trigger
            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey)
                    .usingJobData(TaskUtils.JOB_DATA_KEY, taskConfig.getJobData()).withSchedule(scheduleBuilder)
                    .build();

            // 按新的trigger重新设置job执行
            scheduler.rescheduleJob(triggerKey, trigger);
        }
    }

    public List<TaskConfig> getJobInfo() {
        List<TaskConfig> taskConfigList = new ArrayList<>();
        try {
            Set<JobKey> jobKeySet = scheduler.getJobKeys(GroupMatcher.anyJobGroup());
            for (JobKey jobKey : jobKeySet) {
                JobDetail jobDetail = scheduler.getJobDetail(jobKey);

                List<? extends Trigger> triggerList = scheduler.getTriggersOfJob(jobKey);
                for (Trigger trigger : triggerList) {
                    TaskConfig taskConfig = new TaskConfig();
                    taskConfig.setBeanClass(jobDetail.getJobClass().toString());
                    taskConfig.setDescription(jobDetail.getDescription());
                    taskConfig.setJobData(jobDetail.getJobDataMap().getString(TaskUtils.JOB_DATA_KEY));
                    taskConfig.setJobGroup(jobKey.getGroup());
                    taskConfig.setJobName(jobKey.getName());
                    taskConfig.setTriggerName(trigger.getKey().getName());
                    if (trigger instanceof CronTrigger) {
                        CronTrigger cronTrigger = (CronTrigger) trigger;
                        taskConfig.setCronExpression(cronTrigger.getCronExpression());
                    }
                    taskConfigList.add(taskConfig);
                }
            }
        } catch (SchedulerException e) {
            log.error("", e);
        }
        return taskConfigList;
    }
}
