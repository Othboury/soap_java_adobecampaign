package com.soapadoberequest.eps;

import java.util.List;

/**
 * This class is an interface containing the methods used in the class SOAPWorkflow in order to execute SOAP requests
 * for workflows
 */
public interface ISOAPWorkflow {
    /**
     *This function sends a SOAP request to start a workflow
     *
     * @param workflowId - The id of the workflow
     * @param sessionToken - Token of the session (__sessiontoken)
     * @param securityToken - Security Token of the session (X-Security-Token)
     * @throws Exception -Throws Exception when failure
     *
     */
    public void postSOAPStartWorkflow(String workflowId, String sessionToken, String securityToken)
            throws Exception;

    /**
     * This function sends a SOAP request to send a signal in order to trigger a workflow
     *
     * @param workFlowId - The id of the worklow
     * @param activity - the activity
     * @param vars - A List of the variables to add inside the variables tag
     * @param param - A List of the values to add inside the variables tag (according to the variables list)
     * @param sessionToken - Token of the session (__sessiontoken)
     * @param securityToken - Security Token of the session (X-Security-Token)
     * @throws Exception -Throws Exception when failure
     */
    public void postSOAPPostEvent(String workFlowId, String activity, List<String> vars , List<String> param,
                                  String sessionToken, String securityToken) throws Exception;

    /**
     * This function sends a SOAP request to kill a workflow
     *
     * @param workFlowId - The id of the workflow
     * @param sessionToken - Token of the session (__sessiontoken)
     * @param securityToken - Security Token of the session (X-Security-Token)
     * @throws Exception -Throws Exception when failure
     */
    public void postSOAPKillWorkflow(String workFlowId, String sessionToken, String securityToken)
            throws Exception;

    /**
     * This function sends a SOAP request to pause a workflow
     *
     * @param workFlowId - The Id of the workflow
     * @param sessionToken - Token of the session (__sessiontoken)
     * @param securityToken - Security Token of the session (X-Security-Token)
     * @throws Exception -Throws Exception when failure
     */
    public void postSOAPPauseWorkflow(String workFlowId, String sessionToken, String securityToken)
            throws Exception;

    /**
     * This function sends a SOAP request to wake up a workflow
     *
     * @param workFlowId - The Id of the workflow
     * @param sessionToken - Token of the session (__sessiontoken)
     * @param securityToken - Security Token of the session (X-Security-Token)
     * @throws Exception -Throws Exception when failure
     */
    public void postSOAPWakeUpWorkflow(String workFlowId, String sessionToken, String securityToken)
            throws Exception;

    /**
     * This function sends a SOAP request to start a workflow with parameters
     *
     * @param workFlowId - The id of the workflow
     * @param vars - A List of the variables to add inside the variables tag
     * @param param - A List of the values to add inside the variables tag (according to the variables list)
     * @param sessionToken - Token of the session (__sessiontoken)
     * @param securityToken - Security Token of the session (X-Security-Token)
     * @throws Exception -Throws Exception when failure
     */
    public void postSOAPStartWithParams(String workFlowId, List<String> vars, List<String> param,
                                        String sessionToken, String securityToken) throws Exception;

    /**
     * This function sends a SOAP request the logs of a workflow
     *
     * @param internalName - The internal name of the workflow
     * @param sessionToken - Token of the session (__sessiontoken)
     * @param securityToken - Security Token of the session (X-Security-Token)
     * @throws Exception -Throws Exception when failure
     */
    public void postSOAPWorkflowLogs(String internalName, String sessionToken, String securityToken)
            throws Exception;

    /**
     * This function sends a SOAP request the logs of a workflow
     *
     * @param workflowInternalName - The internal name of the workflow
     * @param sessionToken - Token of the session (__sessiontoken)
     * @param securityToken - Security Token of the session (X-Security-Token)
     * @throws Exception -Throws Exception when failure
     */
    public void postSOAPWorkflowState(String workflowInternalName, String sessionToken, String securityToken)
            throws Exception;

    /**
     * This function sends a SOAP request to return the workflow ID based on its workflow internalName
     *
     * @param internalName - Internal name of workflow
     * @param sessionToken - Token of the session (__sessiontoken)
     * @param securityToken - Security Token of the session (X-Security-Token)
     * @throws Exception - Throws exception when failure
     */
    public String postSOAPSelectWorkflow(String internalName, String sessionToken, String securityToken)
            throws Exception;
}
