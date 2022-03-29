package com.soapadoberequest.eps;

import org.w3c.dom.Node;

import java.util.ArrayList;

public interface ISOAPAuth {
    public ArrayList<Node> postSOAPAUTH();
    public void postSOAPSubscribe(Recipient recipient, String serviceName, String sessionToken, String securityToken);

}
