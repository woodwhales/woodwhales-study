package cn.woodwhales.rocketmq;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.acl.common.AclClientRPCHook;
import org.apache.rocketmq.acl.common.SessionCredentials;
import org.apache.rocketmq.client.consumer.DefaultLitePullConsumer;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.MQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.consumer.rebalance.AllocateMessageQueueAveragely;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.RPCHook;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author woodwhales on 2025-07-24 22:59
 */
@Slf4j
@Component
public class Consumer2 {

    @Value("${rocketmq.name-server}")
    private String namesrvAddr;

    @Autowired
    private Consumer2MessageListener consumer2MessageListener;

    private RPCHook rpcHook = new AclClientRPCHook(new SessionCredentials("rocketmq_b", "rocketmq_b"));

    @Bean(value = "Consumer2", initMethod = "start", destroyMethod = "shutdown")
    public MQPushConsumer eventProducer() throws Exception {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(rpcHook);
        consumer.setConsumerGroup("topic_b_group");
        consumer.setNamesrvAddr(namesrvAddr);
        consumer.subscribe("topic_b", "*");
        consumer.registerMessageListener(this.consumer2MessageListener);
        return consumer;
    }

    @Slf4j
    @Component
    public static class Consumer2MessageListener implements MessageListenerConcurrently {

        @Override
        public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
            for (MessageExt message : msgs) {
                String body = new String(message.getBody());
                log.info("accept mq, topic={}, msg={}, message={}", message.getTopic(), body, message);
            }
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        }
    }

}
