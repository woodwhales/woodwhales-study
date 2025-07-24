package cn.woodwhales.rocketmq;

import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.apis.*;
import org.apache.rocketmq.client.apis.message.Message;
import org.apache.rocketmq.client.apis.producer.Producer;
import org.apache.rocketmq.client.apis.producer.SendReceipt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
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

    @Value("${rocketmq.endpoint}")
    private String endpoint;

    @Value("${rocketmq.access-key}")
    private String accessKey;

    @Value("${rocketmq.access-secret}")
    private String accessSecret;

    private static final String topic_a = "topic_a";

    @GetMapping("/send")
    public Object send() {
        Map<String, Object> body = new HashMap<>();
        body.put("uuid", UUID.randomUUID().toString());
        Message message = ClientServiceProvider.loadService().newMessageBuilder()
                .setTopic(topic_a)
                .setBody(JSON.toJSONString(body).getBytes())
                .build();
        try {

            ClientServiceProvider provider = ClientServiceProvider.loadService();
            ClientConfiguration configuration = ClientConfiguration.newBuilder()
                    .setCredentialProvider(new StaticSessionCredentialsProvider(this.accessKey, this.accessSecret))
                    .setEndpoints(endpoint)
                    .build();
            Producer producer = provider.newProducerBuilder()
                    .setTopics(topic_a)
                    .setClientConfiguration(configuration)
                    .build();

            SendReceipt sendReceipt = producer.send(message);
            log.info("send msg success, topic={}, sendResult={}", topic_a, sendReceipt);
            return "ok";
        } catch (Exception e) {
            log.error("send msg error, topic={}, errorMsg={}", topic_a, e.getMessage(), e);
            return "ko";
        }

    }

    //@Autowired
    //private Producer producer;
    //
    //@Bean("producer")
    //public Producer producer() throws ClientException {
    //    ClientServiceProvider provider = ClientServiceProvider.loadService();
    //    ClientConfiguration configuration = ClientConfiguration.newBuilder()
    //            .setCredentialProvider(new StaticSessionCredentialsProvider(this.accessKey, this.accessSecret))
    //            .setEndpoints(endpoint)
    //            .build();
    //    Producer producer = provider.newProducerBuilder()
    //            .setTopics(topic_a)
    //            .setClientConfiguration(configuration)
    //            .build();
    //    return producer;
    //}

}
