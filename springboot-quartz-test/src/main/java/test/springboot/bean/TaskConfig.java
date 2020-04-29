package test.springboot.bean;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.ToString;

@SuppressWarnings("serial")
@ToString
@Data
public class TaskConfig implements Serializable {
    public static class Status {
        public static final int NORMAL = 1;
        public static final int DELETED = 0;
    }

    private String jobName;

    private String triggerName;

    private String jobGroup;

    private String triggerGroup;

    private String jobData;

    private String cronExpression;

    private String description;

    private Date startTime;

    private Date endTime;

    private Integer startNow;
    
    private Integer priority;

    private String beanClass;

    private Integer status;

    private Date createtime;

    private Date updatetime;
}
