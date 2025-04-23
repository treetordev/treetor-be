package com.example.treetor.service;

import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class Brevo {
    private static final Logger log = LoggerFactory.getLogger(Brevo.class);
    private final RestClient client;
    private final BrevoProperties props;

    public Brevo(RestClient client, BrevoProperties props) {
        this.client = client;
        this.props = props;
    }

    public void send(BrevoTemplate template, String... receivers) {
        if (props.isEnabled()) {
            submit(template, receivers);
        } else {
            log(template, receivers);
        }
    }

    private void submit(BrevoTemplate template, String... receivers) {
        client.post()
                .body(new BrevoRequest(template, java.util.Arrays.asList(receivers)))
                .retrieve()
                .toBodilessEntity();
    }

    private void log(BrevoTemplate template, String... receivers) {
        log.info("Sending template {} to {}", template.template(), String.join(", ", receivers));
    }
}


