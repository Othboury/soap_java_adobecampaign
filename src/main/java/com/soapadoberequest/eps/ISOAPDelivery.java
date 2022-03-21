package com.soapadoberequest.eps;

import java.util.ArrayList;

public interface ISOAPDelivery {
    public void postSOAPCreateWithTemplate(String ScenarioName, ArrayList<String> vars, ArrayList<String> param,
                                           String source, String sessionToken, String securityToken);
    public void postSOAPStopDelivery(String deliveryId, String sessionToken, String securityToken);
    public String postSOAPSelectDelivery(String internalName, String sessionToken, String securityToken);
    public void postSOAPPrepareAndStart( String deliveryId, String sessionToken, String securityToken);
    public void postSOAPPrepareTarget( String internalName, String sessionToken, String securityToken);
    public void postSOAPPrepareMessage( String internalName, String sessionToken, String securityToken);
    public String postSOAPSubmitDelivery( String ScenarioName, String sessionToken, String securityToken);
}
