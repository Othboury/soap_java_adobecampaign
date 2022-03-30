package com.soapadoberequest.eps;

import org.w3c.dom.Node;

import java.util.List;

/**
 * This class is an interface containing the methods used in the class SOAPAuth in order to execute SOAP requests
 * for authentification
 */
public interface ISOAPAuth {
    /**
     * This function sends a SOAP request to authenticate and returns the sessionToken and the securityToken
     *
     * @return sessionToken and securityToken
     * @throws Exception
     */
    public List<Node> postSOAPAUTH() throws Exception;

    /**
     * This function sends a SOAP request to subscribe a recipient to a service
     *
     * @param recipient
     * @param serviceName
     * @param sessionToken
     * @param securityToken
     * @throws Exception
     */
    public void postSOAPSubscribe(Recipient recipient, String serviceName, String sessionToken,
                                  String securityToken) throws Exception;

}
