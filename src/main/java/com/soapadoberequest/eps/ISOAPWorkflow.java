package com.soapadoberequest.eps;

import java.util.List;

/**
 * This class is an interface containing the methods used in the class SOAPWorkflow in order to execute SOAP requests
 * for workflows
 */
public interface ISOAPWorkflow {
    /**
     * This function sends a SOAP request to start a workflow
     *
     * @param workflowId The ID of the workflow
     * @param sessionToken Token of the session (__sessiontoken)
     * @param securityToken Security token of the session (X-Security-Token)
     * @throws Exception Throws exception when failure
     */
    public void postSOAPStartWorkflow(String workflowId, String sessionToken, String securityToken)
            throws Exception;

    /**
     * This function sends a SOAP request to send a signal in order to trigger a workflow
     *
     * @param workFlowId The ID of the workflow
     * @param activity The activity
     * @param vars A List of the variables to add inside the variables tag
     * @param param A List of the values to add inside the variables tag (according to the variables list)
     * @param sessionToken Token of the session (__sessiontoken)
     * @param securityToken Security token of the session (X-Security-Token)
     * @throws Exception Throws exception when failure
     */
    public void postSOAPPostEvent(String workFlowId, String activity, List<String> vars , List<String> param,
                                  String sessionToken, String securityToken) throws Exception;

    /**
     * This function sends a SOAP request to kill a workflow
     *
     * @param workFlowId The ID of the workflow
     * @param sessionToken Token of the session (__sessiontoken)
     * @param securityToken Security token of the session (X-Security-Token)
     * @throws Exception Throws exception when failure
     */
    public void postSOAPKillWorkflow(String workFlowId, String sessionToken, String securityToken)
            throws Exception;

    /**
     * This function sends a SOAP request to pause a workflow
     *
     * @param workFlowId The ID of the workflow
     * @param sessionToken Token of the session (__sessiontoken)
     * @param securityToken Security token of the session (X-Security-Token)
     * @throws Exception Throws exception when failure
     */
    public void postSOAPPauseWorkflow(String workFlowId, String sessionToken, String securityToken)
            throws Exception;

    /**
     * This function sends a SOAP request to wake up a workflow
     *
     * @param workFlowId The ID of the workflow
     * @param sessionToken Token of the session (__sessiontoken)
     * @param securityToken Security token of the session (X-Security-Token)
     * @throws Exception Throws exception when failure
     */
    public void postSOAPWakeUpWorkflow(String workFlowId, String sessionToken, String securityToken)
            throws Exception;

    /**
     * This function sends a SOAP request to start a workflow with parameters
     *
     * @param workFlowId The ID of the workflow
     * @param vars A List of the variables to add inside the variables tag
     * @param param A List of the values to add inside the variables tag (according to the variables list)
     * @param sessionToken Token of the session (__sessiontoken)
     * @param securityToken Security token of the session (X-Security-Token)
     * @throws Exception Throws exception when failure
     */
    public void postSOAPStartWithParams(String workFlowId, List<String> vars, List<String> param,
                                        String sessionToken, String securityToken) throws Exception;

    /**
     * This function sends a SOAP request the logs of a workflow
     *
     * @param internalName The internal name of the workflow
     * @param sessionToken Token of the session (__sessiontoken)
     * @param securityToken Security token of the session (X-Security-Token)
     * @throws Exception Throws Exception when failure
     */
    public void postSOAPWorkflowLogs(String internalName, String sessionToken, String securityToken)
            throws Exception;

    /**
     * This function sends a SOAP request the state of a workflow
     *
     * @param workflowInternalName The internal name of the workflow
     * @param sessionToken Token of the session (__sessiontoken)
     * @param securityToken Security token of the session (X-Security-Token)
     * @throws Exception Throws exception when failure
     */
    public void postSOAPWorkflowState(String workflowInternalName, String sessionToken, String securityToken)
            throws Exception;

    /**
     * This function sends a SOAP request the state of failure of a workflow (0 if no errors, 1 if errors found)
     *
     * @param workflowInternalName The internal name of the workflow
     * @param sessionToken Token of the session (__sessiontoken)
     * @param securityToken Security token of the session (X-Security-Token)
     * @throws Exception Throws exception when failure
     */
    public List<String> postSOAPWorkflowFailed(String workflowInternalName, String sessionToken, String securityToken)
            throws Exception;

    /**
     * This function sends a SOAP request to return the workflow ID based on its workflow internalName
     *
     * @param internalName Internal name of workflow
     * @param sessionToken Token of the session (__sessiontoken)
     * @param securityToken Security token of the session (X-Security-Token)
     * @throws Exception Throws exception when failure
     * @return Returns the workflow's ID
     */
    public String postSOAPSelectWorkflow(String internalName, String sessionToken, String securityToken)
            throws Exception;

    /**
     * This function sends multiple SOAP requests to check workflows statuts and define which ones failed, it also
     * returns the activity in which the workflow failed
     *
     * @param internalName Internal name of workflow
     * @param sessionToken Token of the session (__sessiontoken)
     * @param securityToken Security token of the session (X-Security-Token)
     * @throws Exception Throws exception when failure
     */
    public void checkWorkflowStatus(String internalName, String sessionToken, String securityToken) throws Exception;

    /**
     * This function sends a SOAP request the internalNames of all the paused (state= 13) and stopped (state=20)
     * workflows
     *
     * @param sessionToken Token of the session (__sessiontoken)
     * @param securityToken Security token of the session (X-Security-Token)
     * @throws Exception Throws e when failure
     */
    public List<String> postSOAPPausedAndStoppedWKF(String sessionToken, String securityToken) throws Exception;
}