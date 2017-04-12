import com.rabbitmq.client.*;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Map;

import java.util.concurrent.TimeoutException;

/**
 * Created by D.I. on 07.12.2016.
 */
public class RabbitConsumer {
    public static void StartConsume(Map<String,Object> Con_stings,String rmq_queue ){
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername((String) Con_stings.get("User"));
        factory.setPassword((String)Con_stings.get("Password"));
        factory.setHost((String) Con_stings.get("Hostname"));
        factory.setPort((Integer) Con_stings.get("Port"));
        factory.setVirtualHost((String) Con_stings.get("VirtHost"));
        //String rmq_queue=//String.valueOf(Con_stings.get("Queue"));
        Connection connection = null;
        RWFile a= new RWFile();
        try {
            connection = factory.newConnection("ExportRMQ");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }

        Channel channel = null;
        try {
            channel = connection.createChannel();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            channel.queueDeclare(rmq_queue, true, false, false, null);
        } catch (IOException e) {
            e.printStackTrace();
        }



        boolean autoAck = false;
        QueueingConsumer consumer = new QueueingConsumer(channel);


        try {
            channel.basicQos(Integer.valueOf((String) Con_stings.get("Prefetch")),false); // Per consumer limit
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            channel.basicQos(Integer.valueOf((String) Con_stings.get("Prefetch"))+500, true);  // Per channel limit
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            channel.basicConsume(rmq_queue, autoAck, consumer);
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (true) {

            QueueingConsumer.Delivery delivery = null;
            try {
                delivery = consumer.nextDelivery();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Map<String,Object> headers = delivery.getProperties().getHeaders();
            String msg_body = new String(delivery.getBody());
            msg_body = msg_body.replace("\n", "").replace("\r", "");
            String routingKey = delivery.getEnvelope().getRoutingKey();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
            String msg_date = dateFormat.format(Calendar.getInstance().getTime());
            String msg_headers ="";
            if (headers!=null && !headers.isEmpty()) {
                Iterator entries = headers.entrySet().iterator();

                while (entries.hasNext()) {
                    Map.Entry pair = (Map.Entry) entries.next();
                    msg_headers = msg_headers + pair.getKey() + " = " + pair.getValue() + " ";
                    entries.remove(); // avoids a ConcurrentModificationException
                }
            }

            a.SavetoFile(msg_date+';'+routingKey+";"+msg_headers+";"+msg_body,rmq_queue);

            try {
                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
