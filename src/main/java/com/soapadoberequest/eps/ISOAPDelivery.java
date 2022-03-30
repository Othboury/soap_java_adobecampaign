package com.soapadoberequest.eps;

import java.util.ArrayList;

/**
 * This class is an interface containing the methods used in the class SOAPDelivery in order to execute SOAP requests
 * for deliveries
 */
public interface ISOAPDelivery {
    /**
     * This function sends a SOAP request to create delivery from model
     *
     * @param scenarioName
     * @param vars
     * @param param
     * @param source
     * @param sessionToken
     * @param securityToken
     * @throws Exception
     */
    public void postSOAPCreateWithTemplate(String scenarioName, ArrayList<String> vars, ArrayList<String> param,
                                           String source, String sessionToken, String securityToken) throws Exception;

    /**
     * This function sends a SOAP request to stop a delivery
     *
     * @param deliveryId
     * @param sessionToken
     * @param securityToken
     * @throws Exception
     */
    public void postSOAPStopDelivery(String deliveryId, String sessionToken, String securityToken) throws Exception;

    /**
     * This function sends a SOAP request to select a delivery using the internal name
     *
     * @param internalName
     * @param sessionToken
     * @param securityToken
     * @return the delivery's Id
     * @throws Exception
     */
    public String postSOAPSelectDelivery(String internalName, String sessionToken, String securityToken)
            throws Exception;

    /**
     * This function sends a SOAP request to prerape a delivery and start it
     *
     * @param internalName
     * @param sessionToken
     * @param securityToken
     * @throws Exception
     */
    public void postSOAPPrepareAndStart( String internalName, String sessionToken, String securityToken)
            throws Exception;

    /**
     * This function sends a SOAP request to prepare delivery's target
     *
     * @param internalName
     * @param sessionToken
     * @param securityToken
     * @throws Exception
     */
    public void postSOAPPrepareTarget( String internalName, String sessionToken, String securityToken)
            throws Exception;

    /**
     * This function sends a SOAP request to prepare delivery's message
     *
     * @param internalName
     * @param sessionToken
     * @param securityToken
     * @throws Exception
     */
    public void postSOAPPrepareMessage( String internalName, String sessionToken, String securityToken)
            throws Exception;

    /**
     * This function sends a SOAP request to submit a delivery
     *
     * @param scenarioName
     * @param sessionToken
     * @param securityToken
     * @return the delivery's Id
     * @throws Exception
     */
    public String postSOAPSubmitDelivery( String scenarioName, String sessionToken, String securityToken)
            throws Exception;
}
