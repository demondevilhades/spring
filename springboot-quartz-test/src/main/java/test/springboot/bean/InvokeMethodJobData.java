package test.springboot.bean;

import lombok.Data;

@Data
public class InvokeMethodJobData {

    private String className;
    private String methodName;
    private String arg;
}
