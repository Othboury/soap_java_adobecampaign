package com.soapadoberequest.eps;

public interface ISOAPQuery {
    public void postSOAPInsert(String firstname, String lastname, String email, String sessionToken,
                               String securityToken);
    public void postSOAPSelect(String email, String sessionToken, String securityToken);
    public void postSOAPWrite(Recipient recipient, String sessionToken, String securityToken);
    public String postSOAPSelectCount(String prefix, String tableName, String sessionToken, String securityToken);
    public String postSOAPSelectLast(String prefix, String tableName, String sessionToken, String securityToken);
}
