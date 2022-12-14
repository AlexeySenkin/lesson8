package ru.senkin.spring.producer;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class SenderApp {
    private final static String QUEUE_NAME = "IT";
    private final static String EXCHANGER_NAME = "IT_exchanger";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {

            channel.exchangeDeclare(EXCHANGER_NAME, BuiltinExchangeType.DIRECT);
            channel.queueDeclare(QUEUE_NAME,false,false,false, null);
            channel.queueBind(QUEUE_NAME, EXCHANGER_NAME, "PHP");


            String message = "php some message)";
            channel.basicPublish(EXCHANGER_NAME,"PHP", null, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");

        }
    }
}
