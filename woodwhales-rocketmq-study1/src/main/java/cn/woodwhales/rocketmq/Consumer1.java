package cn.woodwhales.rocketmq;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;

/**
 * @author woodwhales on 2025-07-24 22:52
 */
@Slf4j
@Component
@RocketMQMessageListener(
        nameServer = "${rocketmq.name-server}",
        consumerGroup = "topic_a_group",
        topic = "topic_a",
        accessKey = "rocketmq_a",
        secretKey = "rocketmq_a")
public class Consumer1 implements RocketMQListener<MessageExt> {

    @Override
    public void onMessage(MessageExt message) {
        String body = new String(message.getBody());
        log.info("accept mq, topic={}, msg={}, message={}", message.getTopic(), body, message);
    }
}
