package tw.dfder.ccts_poc_logging.producer;


import au.com.dius.pact.core.model.Interaction;
import au.com.dius.pact.core.model.Pact;
import au.com.dius.pact.provider.MessageAndMetadata;
import au.com.dius.pact.provider.PactVerifyProvider;

import au.com.dius.pact.provider.junit5.MessageTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import au.com.dius.pact.provider.junitsupport.Consumer;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.loader.PactBroker;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import tw.dfder.ccts_poc_logging.Entity.LogMessageEnvelope;

import java.util.HashMap;
import java.util.UUID;

@Provider("loggingService")
@Consumer("orchestrator")
@PactBroker(url = "http://23.dfder.tw:10141")
public class ToOrchestratorTest {


    @TestTemplate
    @ExtendWith(PactVerificationInvocationContextProvider.class)
    void testTemplate(Pact pact, Interaction interaction, PactVerificationContext context) {
        context.verifyInteraction();
    }


    @BeforeEach
    void before(PactVerificationContext context) {
        context.setTarget(new MessageTestTarget());
        System.setProperty("pact.verifier.publishResults", "true");
        System.setProperty("pact.provider.version", "v0.2");
    }


    @PactVerifyProvider("t-logging-orc-01")
    public MessageAndMetadata verifyMessageOfPayment() {

        Gson gson = new Gson();
        LogMessageEnvelope logMessageEnvelope = new LogMessageEnvelope();
        logMessageEnvelope.setPaymentId(UUID.randomUUID().toString());
        logMessageEnvelope.setBuyerId(UUID.randomUUID().toString());
        logMessageEnvelope.setPoints((int) (Math.random()*1000));
        logMessageEnvelope.setTime(String.valueOf(System.currentTimeMillis()));

        HashMap<String, String> props = new HashMap<>();
        props.put("provider", "loggingService");
        props.put("consumer","orchestrator");
        return new MessageAndMetadata(gson.toJson(logMessageEnvelope).getBytes(), props);
    }
}
