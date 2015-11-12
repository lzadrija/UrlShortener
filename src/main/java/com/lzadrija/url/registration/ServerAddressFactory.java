package com.lzadrija.url.registration;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

@Service
public class ServerAddressFactory {

    private static final String SHORT_URL_REFERENCE_TEMPLATE = "http://%s:%s/";

    public String create(HttpServletRequest request) {

        String serverAddress = String.format(SHORT_URL_REFERENCE_TEMPLATE, request.getServerName(), request.getServerPort());
        return serverAddress;
    }

}
