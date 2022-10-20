package pers.me.ad.sender.kafka;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Value;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import pers.me.ad.mysql.dto.MySqlRowData;
import pers.me.ad.sender.ISender;

import java.util.Optional;

/**
 * @author Chris Wang
 * @version 1.0
 * @date 2022-10-19
 */
@Component("kafkaSender")
public class KafkaSender implements ISender {

    private final KafkaTemplate<String,String> kafkaTemplate;

    @Value("${adconf.kafka.topic}")
    private String topic;

    public KafkaSender(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }


    @Override
    public void sender(MySqlRowData rowData) {
        kafkaTemplate.send(topic, JSON.toJSONString(rowData));
    }
    @KafkaListener(topics = {"ad-search-mysql-data"},groupId = "ad-search")
    public void processMySqlRowData(ConsumerRecord<?,?> record){
        Optional<?> kafkaMessage = Optional.ofNullable(record.value());
        Object message = kafkaMessage.get();
        MySqlRowData rowData = JSON.parseObject(message.toString(),MySqlRowData.class);
        System.out.println("kafka processMySqlRowData: "+JSON.toJSONString(rowData));
    }

}
