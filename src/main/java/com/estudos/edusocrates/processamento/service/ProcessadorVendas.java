package com.estudos.edusocrates.processamento.service;

import com.estudos.edusocrates.processamento.deserializer.VendaDeserializer;
import com.estudos.edusocrates.processamento.model.Venda;
import com.fasterxml.jackson.databind.ser.std.StringSerializer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.ProducerConfig;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;
import java.util.Random;


public class ProcessadorVendas {

    public static void main(String[] args) throws InterruptedException {
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, VendaDeserializer.class.getName());
        properties.put(ConsumerConfig.GROUP_ID_CONFIG,"group1");
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,"earliest");
        properties.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG,"10");

        try(KafkaConsumer<String, Venda> consumer = new KafkaConsumer<>(properties)){
            consumer.subscribe(Arrays.asList("venda-ingressos"));

            while (true){
                ConsumerRecords<String, Venda> vendas = consumer.poll(Duration.ofMillis(200));

                for (ConsumerRecord<String, Venda> record : vendas) {
                    Venda venda = record.value();
                    if (new Random().nextBoolean()){
                        venda.setStatus("APROVADA");
                    }else {
                        venda.setStatus("REPROVADA");
                    }
                    Thread.sleep(500);
                    System.out.println(venda);
                }
            }
        }
    }
}
