package com.soapadoberequest.eps;

import org.apache.http.HttpEntity;

import java.io.IOException;

/**
 * This class is an interface containing the methods used in the class HttpClientClass
 */
public interface IHttpClientClass {

    /**
     * This method is used to launch HTTPClient for different SOAP requests
     * @param soapBody
     * @param SOAPAction
     * @param sessionToken
     * @param securityToken
     * @return Response Entity
     * @throws IOException
     */
    public HttpEntity httpClientCall(String soapBody, String SOAPAction, String sessionToken, String securityToken)
            throws IOException;

    /**
     * This method is used to launch HTTPClient to logon in order to get session and security tokens
     *
     * @param soapBody
     * @return response Entity
     * @throws IOException
     */
    public HttpEntity httpClientLogon(String soapBody) throws IOException;
}
