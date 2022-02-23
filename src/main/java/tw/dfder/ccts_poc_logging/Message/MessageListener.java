package tw.dfder.ccts_poc_logging.Message;



import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;
import tw.dfder.ccts_poc_logging.configuration.RabbitmqConfig;

import java.io.IOException;

@EnableRabbit
@Service("MessageListener")
public class MessageListener {
    private final Gson gson;
    private final CCTSMessageSender sender;

    @Autowired
    public MessageListener(Gson gson, CCTSMessageSender sender) {
        this.gson = gson;
        this.sender = sender;
    }


    @RabbitListener(queues = {
            RabbitmqConfig.QUEUE_PAYMENT_RESPONSE
    })
    public void receivedMessageFromPayment(String msg, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag, Channel ch) throws IOException {



    }
}
