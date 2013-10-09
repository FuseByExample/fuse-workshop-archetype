package org.fusesource.poc.routes;

import org.apache.camel.Endpoint;
import org.apache.camel.EndpointInject;
import org.apache.camel.builder.RouteBuilder;

public class WebServiceToPojoUsingQueue extends RouteBuilder {

    @EndpointInject(ref = "cxfUri")
    Endpoint cxfEndpoint;

    @EndpointInject(ref = "activeMqWSQueueUri")
    Endpoint activeMqWSQueueEndpoint;

    @Override
    public void configure() throws Exception {

        // From WebService to PoJo
        from(cxfEndpoint)
        .id("fromWebServiceToQueue")
        .convertBodyTo(org.fusesource.service.DocumentId.class)
        .inOut(activeMqWSQueueEndpoint)
        .log(">>> WebService called and incident created : ${body}");

        // Consume message from WS queue for Web Service
        from(activeMqWSQueueEndpoint)
        .id("fromQueueToPoJo")
        .log(">>> Web Service Message : ${body}")
        .transform()
          .method("feedback","clientReply");

    }


}