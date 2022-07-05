package com.soapadoberequest.eps;

import org.w3c.dom.Node;

import java.util.List;

/**
 * This class is an interface containing the methods used in the class SOAPAuth in order to execute SOAP requests
 * for authentication
 */
public interface ISOAPAuth {
    /**
     * This function sends a SOAP request to authenticate and returns the sessionToken and the securityToken
     *
     * @param login User's adobe campaign login
     * @param password User's adobe campaign password
     * @param port Adobe Campaign's server port
     * @return SessionToken and securityToken
     * @throws Exception Throws exception when failure
     */
    public List<Node> postSOAPAuth(String login, String password, String port) throws Exception;

    /**
     * This function sends a SOAP request to subscribe a recipient to a service
     *
     * @param recipient The recipient's object
     * @param serviceName The name of service
     * @param sessionToken Token of the session (__sessiontoken)
     * @param securityToken Security token of the session (X-Security-Token)
     * @throws Exception Throws exception when failure
     */
    public void postSOAPSubscribe(Recipient recipient, String serviceName, String sessionToken,
                                  String securityToken) throws Exception;
}