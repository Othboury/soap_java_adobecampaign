package com.soapadoberequest.eps;

import java.util.List;

/**
 * This class is an interface containing the methods used in the class SOAPDelivery in order to execute SOAP requests
 * for deliveries
 */
public interface ISOAPDelivery {
    /**
     * This function sends a SOAP request to create delivery from model
     *
     * @param scenarioName The name of the scenario
     * @param vars A List of the variables to add inside the variables tag
     * @param param A List of the values to add inside the variables tag (according to the variables list)
     * @param source The name of the source
     * @param sessionToken Token of the session (__sessiontoken)
     * @param securityToken Security token of the session (X-Security-Token)
     * @throws Exception Throws exception when failure
     */
    public void postSOAPCreateWithTemplate(String scenarioName, List<String> vars, List<String> param,
                                           String source, String sessionToken, String securityToken) throws Exception;

    /**
     * This function sends a SOAP request to stop a delivery
     *
     * @param deliveryId The delivery's ID
     * @param sessionToken Token of the session (__sessiontoken)
     * @param securityToken Security token of the session (X-Security-Token)
     * @throws Exception Throws exception when failure
     */
    public void postSOAPStopDelivery(String deliveryId, String sessionToken, String securityToken) throws Exception;

    /**
     * This function sends a SOAP request to select a delivery using the internal name
     *
     * @param internalName The internal name of the delivery
     * @param sessionToken Token of the session (__sessiontoken)
     * @param securityToken Security token of the session (X-Security-Token)
     * @return The delivery's ID
     * @throws Exception Throws exception when failure
     */
    public String postSOAPSelectDelivery(String internalName, String sessionToken, String securityToken)
            throws Exception;

    /**
     * This function sends a SOAP request to prerape a delivery and start it
     *
     * @param internalName The internal name of the delivery
     * @param sessionToken Token of the session (__sessiontoken)
     * @param securityToken Security token of the session (X-Security-Token)
     * @throws Exception Throws exception when failure
     */
    public void postSOAPPrepareAndStart(String internalName, String sessionToken, String securityToken)
            throws Exception;

    /**
     * This function sends a SOAP request to prepare delivery's target
     *
     * @param internalName The internal name of the delivery
     * @param sessionToken Token of the session (__sessiontoken)
     * @param securityToken Security token of the session (X-Security-Token)
     * @throws Exception Throws exception when failure
     */
    public void postSOAPPrepareTarget(String internalName, String sessionToken, String securityToken)
            throws Exception;

    /**
     * This function sends a SOAP request to prepare delivery's message
     *
     * @param internalName The internal name of the delivery
     * @param sessionToken Token of the session (__sessiontoken)
     * @param securityToken Security token of the session (X-Security-Token)
     * @throws Exception Throws exception when failure
     */
    public void postSOAPPrepareMessage(String internalName, String sessionToken, String securityToken)
            throws Exception;

    /**
     * This function sends a SOAP request to submit a delivery
     *
     * @param scenarioName The name of the scenario
     * @param sessionToken Token of the session (__sessiontoken)
     * @param securityToken Security token of the session (X-Security-Token)
     * @return The delivery's ID
     * @throws Exception Throws exception when failure
     */
    public String postSOAPSubmitDelivery(String scenarioName, String sessionToken, String securityToken)
            throws Exception;
}
