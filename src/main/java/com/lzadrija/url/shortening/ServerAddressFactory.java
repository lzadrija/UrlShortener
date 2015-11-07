package com.lzadrija.url.shortening;

import java.net.MalformedURLException;
import java.net.URL;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ServerAddressFactory {

    private static final Logger logger = LoggerFactory.getLogger(ServerAddressFactory.class);
    private static final String SHORT_URL_REFERENCE_TEMPLATE = "http://%s:%s/";

    public String create(HttpServletRequest request) {

        String prefix = "";
        try {
            URL url = new URL(request.getRequestURL().toString());
            prefix = String.format(SHORT_URL_REFERENCE_TEMPLATE, url.getHost(), url.getPort());
        } catch (MalformedURLException ex) {
            logger.error("Unable to obtain host and port from URL", ex);
        }
        return prefix;
    }

}
