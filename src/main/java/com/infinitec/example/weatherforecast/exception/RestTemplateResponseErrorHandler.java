package com.infinitec.example.weatherforecast.exception;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.DefaultResponseErrorHandler;

/**
 * For the purpose of this task, simply tweak Spring Boot default response error handle for better error handling 
 * in case of not found city. 
 */
@Component
public class RestTemplateResponseErrorHandler extends DefaultResponseErrorHandler {
 
    @Override
    public void handleError(ClientHttpResponse httpResponse) throws IOException {
        if ((httpResponse.getStatusCode().series() == HttpStatus.Series.CLIENT_ERROR) &&
            (httpResponse.getStatusCode() == HttpStatus.NOT_FOUND)) {
                throw new CityNotFoundException();
        } else {
        	super.handleError(httpResponse);
        }
    }
}
