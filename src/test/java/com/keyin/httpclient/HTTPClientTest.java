package com.keyin.httpclient;

import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.IOException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@ExtendWith(MockitoExtension.class)
public class HTTPClientTest {

    @Test
    public void HTTPClientTest() {
        HTTPClient client = mock(HTTPClient.class);
        Assertions.assertNotNull(client);
    }
    @Test
    public void HttpResponseTest() throws IOException {
        HTTPClient client = mock(HTTPClient.class);
        HttpRequest request = mock(HttpRequest.class);
        HttpResponse response = mock(HttpResponse.class);
        when(response.statusCode()).thenReturn(200);
        Assertions.assertEquals(200, response.statusCode());
    }
}

