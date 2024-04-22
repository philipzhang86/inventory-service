package com.jmalltech.client;

import com.alibaba.fastjson2.JSON;
import com.jmalltech.entity.Asn;
import com.jmalltech.service.InventoryDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class AsnServiceListener {
    @Autowired
    private InventoryDomainService service;
    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;
    @KafkaListener(topics = "asn-service-topic")
    private void listen(String message){
        if(!message.isEmpty()){
            Asn asn = JSON.parseObject(message, Asn.class);
            service.updateInventoryUsingAsn(asn.getId()).block();
            asn.setStatus((short) 2);
            kafkaTemplate.send("asn-service-topic2", JSON.toJSONString(asn));
        }
    }
}
