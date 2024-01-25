package se.callista.camelblog;

import org.apache.camel.CamelContext;
import org.apache.camel.EndpointInject;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.junit5.CamelSpringBootTest;
import org.apache.camel.test.spring.junit5.MockEndpoints;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(classes = CamelBlogExamplesApplication.class)
@CamelSpringBootTest
@AutoConfiguration
@MockEndpoints
public class ActiveMQExampleRouteMockTest {

    @Autowired
    private CamelContext camelContext;

    @EndpointInject("mock:activemq:bar")
    private MockEndpoint mockFooEndpoint;

    @EndpointInject("mock:activemq:bar")
    private MockEndpoint mockBarEndpoint;

    @Test
    @DirtiesContext // Ensures that Camel context is re-created after this test
    public void testActiveMQExampleRoute() throws Exception {
        // Arrange
        String messageBody = "{\"message\":\"Hello, this is a json message!\"}";

        // Set expectations on the mock endpoint
        mockFooEndpoint.expectedMessageCount(1);
        mockFooEndpoint.expectedBodiesReceived(messageBody);

        mockBarEndpoint.expectedMessageCount(1);
        mockBarEndpoint.expectedBodiesReceived("BAR: " + messageBody);

        // Act
        ProducerTemplate producerTemplate = camelContext.createProducerTemplate();
        producerTemplate.sendBody("activemq:foo", messageBody);

        // Assert
        mockBarEndpoint.assertIsSatisfied();
        mockFooEndpoint.assertIsSatisfied();
    }

}
