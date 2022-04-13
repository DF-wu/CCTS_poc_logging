package tw.dfder.ccts_poc_logging.Message;



import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;
import tw.dfder.ccts_poc_logging.Entity.LogMessageEnvelope;
import tw.dfder.ccts_poc_logging.configuration.RabbitmqConfig;
import tw.dfder.ccts_poc_logging.configuration.ServiceConfig;
import tw.dfder.ccts_poc_logging.repository.LogRepository;

import java.io.IOException;
import java.time.LocalDateTime;

@EnableRabbit
@Service("MessageListener")
public class MessageListener {
    private final Gson gson;
    private final CCTSMessageSender sender;
    private final LogRepository repo;
    private final ServiceConfig serviceConfig;
    @Autowired
    public MessageListener(Gson gson, CCTSMessageSender sender, LogRepository repo, ServiceConfig serviceConfig) {
        this.gson = gson;
        this.sender = sender;
        this.repo = repo;
        this.serviceConfig = serviceConfig;
    }


    @RabbitListener(queues = {
            RabbitmqConfig.QUEUE_LOGGGING_REQUEST
    })
    public void receiveMessage(String msg, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag, Channel ch) throws IOException {


        LogMessageEnvelope logMessageEnvelope = gson.fromJson(msg, LogMessageEnvelope.class);
        ch.basicAck(deliveryTag, false);
        logMessageEnvelope.setTime(LocalDateTime.now().toString());

        repo.save(logMessageEnvelope);

        sender.sendMessage(
                gson.toJson(logMessageEnvelope),
                "orchestrator",
                RabbitmqConfig.ROUTING_LOGGING_RESPONSE,
                "t-logging-orc-01"
        );


    }
}
