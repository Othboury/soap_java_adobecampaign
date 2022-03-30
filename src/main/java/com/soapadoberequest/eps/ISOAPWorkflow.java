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
     * @param workflowId
     * @param sessionToken
     * @param securityToken
     * @throws Exception
     *
     */
    public void postSOAPStartWorkflow(String workflowId, String sessionToken, String securityToken) throws Exception;

    /**
     * This function sends a SOAP request to send a signal in order to trigger a workflow
     *
     * @param workFlowId
     * @param activity
     * @param vars
     * @param param
     * @param sessionToken
     * @param securityToken
     * @throws Exception
     */
    public void postSOAPPostEvent(String workFlowId, String activity, List<String> vars , List<String> param,
                                  String sessionToken, String securityToken) throws Exception;

    /**
     * This function sends a SOAP request to kill a workflow
     *
     * @param workFlowId
     * @param sessionToken
     * @param securityToken
     * @throws Exception
     */
    public void postSOAPKillWorkflow(String workFlowId, String sessionToken, String securityToken) throws Exception;

    /**
     * This function sends a SOAP request to pause a workflow
     *
     * @param workFlowId
     * @param sessionToken
     * @param securityToken
     * @throws Exception
     */
    public void postSOAPPauseWorkflow(String workFlowId, String sessionToken, String securityToken) throws Exception;

    /**
     * This function sends a SOAP request to wake up a workflow
     *
     * @param workFlowId
     * @param sessionToken
     * @param securityToken
     * @throws Exception
     */
    public void postSOAPWakeUpWorkflow(String workFlowId, String sessionToken, String securityToken) throws Exception;

    /**
     * This function sends a SOAP request to start a workflow with parameters
     *
     * @param workFlowId
     * @param vars
     * @param param
     * @param sessionToken
     * @param securityToken
     * @throws Exception
     */
    public void postSOAPStartWithParams(String workFlowId, List<String> vars, List<String> param,
                                        String sessionToken, String securityToken) throws Exception;

    /**
     * This function sends a SOAP request the logs of a workflow
     *
     * @param workflowId
     * @param sessionToken
     * @param securityToken
     * @throws Exception
     */
    public void postSOAPWorkflowLogs(String workflowId, String sessionToken, String securityToken) throws Exception;

    /**
     * This function sends a SOAP request the logs of a workflow
     *
     * @param workflowInternalName
     * @param sessionToken
     * @param securityToken
     * @throws Exception
     */
    public void postSOAPWorkflowState(String workflowInternalName, String sessionToken, String securityToken)
            throws Exception;
}
