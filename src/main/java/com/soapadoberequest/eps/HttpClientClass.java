package com.soapadoberequest.eps;

import io.github.cdimascio.dotenv.Dotenv;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;

/**
 * This class contains the HTTPClient methods in order to launch SOAP requests, it implements its methods from the interface
 * IHttpClientClass
 */
public class HttpClientClass implements IHttpClientClass{
    Dotenv dotenv = Dotenv.configure()
            .ignoreIfMalformed()
            .ignoreIfMissing()
            .load();
    private static final String HTTP_URL = "HTTP_URL";

    /**
     * This method is used to launch HTTPClient for different SOAP requests
     * @param soapBody
     * @param soapAction
     * @param sessionToken
     * @param securityToken
     * @return Response Entity
     * @throws IOException
     */
    public HttpEntity httpClientCall(String soapBody, String soapAction, String sessionToken, String securityToken) throws IOException {

        HttpClient httpclient = HttpClientBuilder.create().build();
        // You can get below parameters from SoapUI's Raw request if you are using that tool
        StringEntity strEntity = new StringEntity(soapBody, "text/xml", "UTF-8");
        // URL of request
        HttpPost post = new HttpPost(dotenv.get(HTTP_URL));
        post.setHeader("SOAPAction", soapAction);
        post.setHeader("cookie","__sessiontoken="+sessionToken);
        post.setHeader("X-Security-Token", securityToken);
        post.setEntity(strEntity);

        // Execute request
        HttpResponse response = httpclient.execute(post);
        return response.getEntity();

    }

    /**
     * This method is used to launch HTTPClient to logon in order to get session and security tokens
     *
     * @param soapBody
     * @return response Entity
     * @throws IOException
     */
    public HttpEntity httpClientLogon(String soapBody) throws IOException {

        HttpClient httpclient = HttpClientBuilder.create().build();
        // You can get below parameters from SoapUI's Raw request if you are using that tool
        StringEntity strEntity = new StringEntity(soapBody, "text/xml", "UTF-8");

        // URL of request
        HttpPost post = new HttpPost(dotenv.get(HTTP_URL));
        post.setHeader("SOAPAction", "xtk:session#Logon");
        post.setEntity(strEntity);

        // Execute request
        HttpResponse response = httpclient.execute(post);
        return response.getEntity();

    }
}
