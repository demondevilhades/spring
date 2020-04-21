package test.springboot.config;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.quartz.Trigger;
import org.quartz.spi.JobFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class SchedulerConfig implements ApplicationContextAware {

    private ApplicationContext ac = null;

    @Value("${task.delayTime}")
    private Integer delayTime; // seconds

    @Resource
    private DataSource dataSource;

    @Resource
    private JobFactory jobFactory;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (ac == null) {
            ac = applicationContext;
        }
    }

    /**
     * 调度器工厂
     */
    @Bean(name = "taskQuartzScheduler")
    public SchedulerFactoryBean schedulerFactory() throws IOException {
        log.info("init taskQuartzScheduler");
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        propertiesFactoryBean.setLocation(new ClassPathResource("quartz.properties"));
        propertiesFactoryBean.afterPropertiesSet();
        Properties properties = propertiesFactoryBean.getObject();
        if (properties == null) {
            throw new RuntimeException("quartz init error");
        }
        SchedulerFactoryBean bean = new SchedulerFactoryBean();
        // 覆盖已存在的任务
        bean.setOverwriteExistingJobs(true);
        // 延时启动定时任务，避免系统未完全启动却开始执行定时任务的情况
        bean.setStartupDelay(delayTime);
        bean.setDataSource(dataSource);
        bean.setQuartzProperties(properties);
        // 注册触发器
        Map<String, Trigger> beanMap = ac.getBeansOfType(Trigger.class);
        if (beanMap.size() > 0) {
            bean.setTriggers(beanMap.values().toArray(new Trigger[0]));
        }
        log.info("init triggers : " + StringUtils.join(beanMap.keySet(), ","));
        bean.setJobFactory(jobFactory);
        return bean;
    }
}
