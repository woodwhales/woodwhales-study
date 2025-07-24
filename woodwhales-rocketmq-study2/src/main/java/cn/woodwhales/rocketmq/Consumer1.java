package cn.woodwhales.rocketmq;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.apis.ClientConfiguration;
import org.apache.rocketmq.client.apis.ClientException;
import org.apache.rocketmq.client.apis.ClientServiceProvider;
import org.apache.rocketmq.client.apis.StaticSessionCredentialsProvider;
import org.apache.rocketmq.client.apis.consumer.ConsumeResult;
import org.apache.rocketmq.client.apis.consumer.FilterExpression;
import org.apache.rocketmq.client.apis.consumer.FilterExpressionType;
import org.apache.rocketmq.client.apis.consumer.PushConsumer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.util.Collections;

/**
 * @author woodwhales on 2025-07-24 22:52
 */
@Slf4j
@Component
public class Consumer1  {
    @Value("${rocketmq.endpoint}")
    private String endpoint;

    private String accessKey = "rocketmq_a";

    private String accessSecret = "rocketmq_a";

    //@Bean("pushConsumer")
    //public PushConsumer pushConsumer() throws ClientException {
    //    final ClientServiceProvider provider = ClientServiceProvider.loadService();
    //
    //    ClientConfiguration configuration = ClientConfiguration.newBuilder()
    //            .setCredentialProvider(new StaticSessionCredentialsProvider(this.accessKey, this.accessSecret))
    //            .setEndpoints(endpoint)
    //            .build();
    //
    //    PushConsumer pushConsumer = provider.newPushConsumerBuilder()
    //            .setClientConfiguration(configuration)
    //            // 设置消费者分组。
    //            .setConsumerGroup("topic_a_group")
    //            // 设置预绑定的订阅关系。
    //            .setSubscriptionExpressions(Collections.singletonMap("topic_a", new FilterExpression()))
    //            // 设置消费监听器。
    //            .setMessageListener(messageView -> {
    //                // 处理消息并返回消费结果。
    //                log.info("Consume message successfully, messageId={}", messageView.getMessageId());
    //                return ConsumeResult.SUCCESS;
    //            })
    //            .build();
    //    return pushConsumer;
    //}
}
