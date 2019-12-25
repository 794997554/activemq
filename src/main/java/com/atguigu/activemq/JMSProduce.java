package com.atguigu.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class JMSProduce {
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
        Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
        //5 获得目的地，此例是队列
        Queue queue = session.createQueue(Queue_Name);
        //6 获得消息生产者,生产什么内容？生产出来放在哪里？
        MessageProducer messageProducer = session.createProducer(queue);
        //7 生产message内容
        for (int i = 0; i < 3; i++) {
            TextMessage textMessage = session.createTextMessage("msg----------" + i);
            messageProducer.send(textMessage);
        }
        //8 释放各种连接和资源
        messageProducer.close();
        session.commit();
        session.close();
        connection.close();

        System.out.println("******msg send ok,O(∩_∩)O");
    }
}
