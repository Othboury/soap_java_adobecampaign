package com.soapadoberequest.eps;

import java.util.ArrayList;

public interface ISOAPDelivery {
    public void postSOAPCreateWithTemplate(String ScenarioName, ArrayList<String> vars, ArrayList<String> param,
                                           String sessionToken, String securityToken);
    public void postSOAPStopDelivery(String deliveryId, String sessionToken, String securityToken);
    public void postSOAPPrepareAndStart( ArrayList<String> vars, ArrayList<String> param, String sessionToken,
                                         String securityToken);
    public void postSOAPPrepareTarget( ArrayList<String> vars, ArrayList<String> param, String sessionToken,
                                       String securityToken);
    public void postSOAPPrepareMessage( ArrayList<String> vars, ArrayList<String> param, String sessionToken,
                                        String securityToken);
    public void postSOAPSubmitDelivery( String ScenarioName, ArrayList<String> vars, ArrayList<String> param,
                                        String sessionToken, String securityToken);
}
