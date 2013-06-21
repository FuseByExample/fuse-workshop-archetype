package org.fusesource.poc.testing;

import org.fusesource.service.DocumentResponse;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class MyProcessor {

    public void process(Exchange exchange) throws Exception {

        String text = "<html><body>Message received !!</body></html>";
        DocumentResponse response = new DocumentResponse();
        response.setEmail("cmoulliard@gmail.com");
        response.setFamilyName("Moulliard");
        response.setGivenName("Charles");
        response.setIncidentId("123");
        response.setPhone("111 222 333");
        response.setDetails(text);

        exchange.getOut().setBody(response);
    }
}
