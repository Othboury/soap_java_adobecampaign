package com.soapadoberequest.eps;

import org.apache.http.HttpEntity;

import java.io.IOException;

public interface IHttpClientClass {
    public HttpEntity httpClientCall(String soapBody, String SOAPAction, String sessionToken, String securityToken) throws IOException;
    public HttpEntity httpClientLogon(String soapBody) throws IOException;
}
