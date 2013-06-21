package org.fusesource.poc.pojo;

import org.fusesource.service.DocumentId;
import org.fusesource.service.DocumentResponse;
import org.apache.camel.Body;

public class Feedback {

    public DocumentId createRequest(@Body String id) {
         DocumentId request = new DocumentId();
        request.setId(id);
        return request;
    }

    public DocumentResponse clientReply(@Body DocumentId doc) {

        DocumentResponse response = new DocumentResponse();
        response.setIncidentId(doc.getId());
        response.setDetails("This is a big incident");
        response.setGivenName("Fuse");
        response.setFamilyName("Source");
        response.setEmail("info@fusesource.com");

        return response;

    }
}
