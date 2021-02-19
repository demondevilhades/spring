package test.spring.tests.dao;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 
 * @author awesome
 */
@Repository
public interface TestDao extends BaseMapper<String> {

    @Select("SELECT VERSION()")
    public String version();
}
