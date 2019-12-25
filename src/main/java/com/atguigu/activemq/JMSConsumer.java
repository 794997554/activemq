package com.atguigu.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class JMSConsumer {
    public static final String MQ_URL = "tcp://192.168.100.101:61616";
    public static final String Queue_Name = "queue";
    public static void main(String[] args) throws JMSException {
        //1 获得ActiveMQConnectionFactory
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(MQ_URL);
        //2 由ActiveMQConnectionFactory获得Connection
        Connection connection = activeMQConnectionFactory.createConnection();
        //3 启动连接准备建立会话
        connection.start();
        //4 获得Session，两个参数先用默认
        //4.1 是否开启事务
        //4.2 签收模式
        Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
        //5 获得目的地，此例是队列
        Queue queue = session.createQueue(Queue_Name);
        //6 获得消息消费者,消费什么内容？从哪里消费？
        MessageConsumer messageConsumer = session.createConsumer(queue);

        messageConsumer.setMessageListener(message -> {
            if(message != null && message instanceof TextMessage) {
                TextMessage textMessage = (TextMessage)message;
                try {
                    System.out.println("messageConsumer:" + textMessage.getText());
                    textMessage.acknowledge();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });

        /*while (true) {
            TextMessage textMessage = (TextMessage) messageConsumer.receive();

            if(null != textMessage) {
                System.out.println("messageConsumer:" + textMessage.getText());
            }else {
                break;
            }
        }
        //8 释放各种连接和资源
        messageConsumer.close();
        session.close();
        connection.close();*/

        System.out.println("******msg Consumer ok,O(∩_∩)O");
    }
}
