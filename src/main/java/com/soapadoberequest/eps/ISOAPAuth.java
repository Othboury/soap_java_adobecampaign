package com.soapadoberequest.eps;

import org.w3c.dom.Node;

import java.util.ArrayList;

public interface ISOAPAuth {
    public ArrayList<Node> postSOAPAUTH(String login, String password);
    public void postSOAPInsert(String firstname, String lastname, String email, String sessionToken,
                               String securityToken);
    public void postSOAPSelect(String email, String sessionToken, String securityToken);
    public void postSOAPStartWorkflow(String workflowId, String sessionToken, String securityToken);
    public void postSOAPSubscribe(Recipient recipient, String serviceName, String sessionToken, String securityToken);
    public void postSOAPPostEvent(String workFlowId, String activity, ArrayList<String> vars ,ArrayList<String> param,
                                  String sessionToken, String securityToken);
    public void postSOAPWrite(Recipient recipient, String sessionToken, String securityToken);
}
