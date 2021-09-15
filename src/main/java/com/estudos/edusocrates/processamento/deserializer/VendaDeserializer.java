package com.estudos.edusocrates.processamento.deserializer;

import com.estudos.edusocrates.processamento.model.Venda;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.IOException;

public class VendaDeserializer implements Deserializer<Venda> {
    @Override
    public Venda deserialize(String topic, byte[] venda) {
        try {
            new ObjectMapper().readValue(venda,Venda.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }
}
