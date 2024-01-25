package se.callista.camelblog;

import java.util.Random;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class ActiveMQExampleRoute extends RouteBuilder {

    // JacksonDataFormat jsonDataFormat = new
    // JacksonDataFormat(MyMessageClass.class);

    @Override
    public void configure() throws Exception {

        from("activemq:foo")
                .routeId("in.foo")
                .log("msg-in: ${body} ")
                .setBody().simple("BAR: ${body}")
                .to("activemq:bar").id("out.bar");

        // Random text sender.
        // randomMessageSender();

    }

    private void randomMessageSender() {
        from("timer:randomTimer?period=5000") // Timer with a random period
                .routeId("randon-message-generator")
                .process(exchange -> {
                    // Generate a random message type ("text" or "json")
                    String randomMessageType = getRandomMessageType();
                    exchange.getIn().setHeader("MessageType", randomMessageType);

                    // Set the message body based on the message type
                    if ("text".equals(randomMessageType)) {
                        exchange.getIn().setBody("Hello, this is a text message!");
                    } else {
                        exchange.getIn().setBody("{\"message\":\"Hello, this is a json message!\"}");
                    }
                })
                .to("activemq:foo");
    }

    private String getRandomMessageType() {
        // Generate a random number (0 or 1) to represent the message type
        int randomIndex = new Random().nextInt(2);
        return (randomIndex == 0) ? "text" : "json";
    }

}
