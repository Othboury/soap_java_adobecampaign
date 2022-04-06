package com.soapadoberequest.eps;

import org.apache.http.HttpEntity;

import java.io.IOException;

/**
 * This class is an interface containing the methods used in the class HttpClientClass
 */
public interface IHttpClientClass {

    /**
     * This method is used to launch HTTPClient for different SOAP requests
     * @param soapBody The soap's envelope's body
     * @param soapAction The soap action to perform
     * @param sessionToken Token of the session (__sessiontoken)
     * @param securityToken Security Token of the session (X-Security-Token)
     * @return HttpEntity
     * @throws IOException Throws exception when failure
     */
    public HttpEntity httpClientCall(String soapBody, String soapAction, String sessionToken, String securityToken)
            throws IOException;

    /**
     * This method is used to launch HTTPClient to logon in order to get session and security tokens
     *
     * @param soapBody The soap's envelope's body
     * @return HttpEntity
     * @throws IOException Throws exception when failure
     */
    public HttpEntity httpClientLogon(String soapBody) throws IOException;
}
