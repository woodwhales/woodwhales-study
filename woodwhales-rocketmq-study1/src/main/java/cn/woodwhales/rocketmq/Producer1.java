package cn.woodwhales.rocketmq;

import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author woodwhales on 2025-07-24 22:48
 */
@Slf4j
@RestController
public class Producer1 {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    private static final String topic_a = "topic_a";

    @GetMapping("/send1")
    public Object send() {
        Map<String, Object> body = new HashMap<>();
        body.put("uuid", UUID.randomUUID().toString());
        try {
            SendResult sendResult = rocketMQTemplate.syncSend(topic_a, JSON.toJSONString(body));
            log.info("send msg success, topic={}, sendResult={}", topic_a, sendResult);
            return "ok";
        } catch (Exception e) {
            log.error("send msg error, topic={}, errorMsg={}", topic_a, e.getMessage(), e);
            return "ko";
        }
    }

}
