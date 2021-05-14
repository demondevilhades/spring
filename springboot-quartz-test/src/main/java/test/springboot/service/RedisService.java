package test.springboot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.google.common.collect.Lists;

/**
 * 
 * @author awesome
 */
//@Component
public class RedisService {

    @Autowired
    private StringRedisTemplate srt;

    private final String getAndDelScriptStr = "local current = redis.call('get', KEYS[1]);\nif (current) then\n    redis.call('del', KEYS[1]);\nend\nreturn current;";
    private final RedisScript<String> getAndDelRedisScript = new DefaultRedisScript<String>(getAndDelScriptStr,
            String.class);

    /**
     * 
     * @param key
     * @return
     */
    public String getAndDel(String key) {
        return execScript(getAndDelRedisScript, Lists.newArrayList(key), "");
    }

    /**
     * 
     * @param redisScript
     * @param keys
     * @param args
     * @return
     */
    private String execScript(RedisScript<String> redisScript, List<String> keys, Object args) {
        return srt.execute(redisScript, StringRedisSerializer.UTF_8, StringRedisSerializer.UTF_8, keys, args);
    }
}
