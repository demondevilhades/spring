package test.springboot.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import test.springboot.bean.TaskConfig;

@Repository
public interface TaskConfigDao {

    @Select("<script> SELECT * FROM task_config WHERE status = 1 </script>")
    public List<TaskConfig> getAllTaskConfig();
}
