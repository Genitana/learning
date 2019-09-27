package learning.kafkaDemo.kafka.produce;

import com.alibaba.fastjson.JSON;
import learning.kafkaDemo.kafka.model.UserEvent;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;
import java.util.UUID;

/**
 * @author bo.yang
 */
public class ProducerUserEvent {
    private static Logger logger = LoggerFactory.getLogger(ProducerUserEvent.class);
    public static final String brokerList = "10.118.80.53:9092";
//    public static final String topic = "topic-demo";
    public static final String topic = "test-topic";

    public static void main(String[] args){
        Properties properties = new Properties();
        properties.put("key.serializer", StringSerializer.class.getName());
        properties.put("value.serializer", StringSerializer.class.getName());
        properties.put("bootstrap.servers", brokerList);

        // 配置生产者客户端参数并创建kafkaProducer实例
        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);

        // 构建所需要发送的消息
        UserEvent userEvent = new UserEvent()
                .setName(UUID.randomUUID().toString())
                .setAge(22)
                .setCountry("Australia");

        String json = JSON.toJSONString(userEvent);
        String json2 = JSON.toJSONString(userEvent.setName(UUID.randomUUID().toString()));
        System.out.println("send:"+json);
        System.out.println("send2:"+json2);

        ProducerRecord<String, String> record = new ProducerRecord<>(topic, json);
        ProducerRecord<String, String> record2 = new ProducerRecord<>(topic, json2);

        // 发送消息
        try {
            logger.info("start ***********");
            producer.send(record);
//            producer.send(record2);
        }catch (Exception e) {
            e.printStackTrace();
        }

        // 关闭生产者客户端示例
        producer.close();


    }
}
