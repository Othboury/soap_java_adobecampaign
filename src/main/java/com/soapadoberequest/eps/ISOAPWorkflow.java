package com.soapadoberequest.eps;

import java.util.ArrayList;

public interface ISOAPWorkflow {
    public void postSOAPStartWorkflow(String workflowId, String sessionToken, String securityToken);
    public void postSOAPPostEvent(String workFlowId, String activity, ArrayList<String> vars , ArrayList<String> param,
                                  String sessionToken, String securityToken);
    public void postSOAPKillWorkflow(String workFlowId, String sessionToken, String securityToken);
}
