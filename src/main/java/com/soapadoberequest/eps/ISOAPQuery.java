package com.soapadoberequest.eps;

/**
 * This class is an interface containing the methods used in the class SOAPQuery in order to execute SOAP requests
 * for queries like the selection and insertion
 */
public interface ISOAPQuery {
    /**
     * This function sends a SOAP request to insert a new recipient
     *
     * @param firstname
     * @param lastname
     * @param email
     * @param sessionToken
     * @param securityToken
     * @throws Exception
     */
    public void postSOAPInsert(String firstname, String lastname, String email, String sessionToken,
                               String securityToken) throws Exception;

    /**
     * This function sends a SOAP request to select a recipient using the email
     *
     * @param email
     * @param sessionToken
     * @param securityToken
     * @throws Exception
     */
    public void postSOAPSelect(String email, String sessionToken, String securityToken) throws Exception;

    /**
     * This function sends a SOAP request to write(Insert) a collection of entries (recipient - deliveries -
     * workflow - etc) from csv file
     *
     * @param filename
     * @param sessionToken
     * @param securityToken
     * @throws Exception
     */
    public void postSOAPWriteCollection(String filename, String sessionToken, String securityToken) throws Exception;

    /**
     * This function sends a SOAP request to select the count of table
     *
     * @param prefix
     * @param tableName
     * @param sessionToken
     * @param securityToken
     * @return
     * @throws Exception
     */
    public String postSOAPSelectCount(String prefix, String tableName, String sessionToken, String securityToken)
            throws Exception;

    /**
     * This function sends a SOAP request to select last entry's id
     *
     * @param prefix
     * @param tableName
     * @param sessionToken
     * @param securityToken
     * @return The count
     * @throws Exception
     */
    public String postSOAPSelectLast(String prefix, String tableName, String sessionToken, String securityToken)
            throws Exception;

    /**
     * This function sends a SOAP request to write(Insert) a new recipient
     *
     * @param recipient
     * @param sessionToken
     * @param securityToken
     * @throws Exception
     */
    public void postSOAPWrite(Recipient recipient, String sessionToken, String securityToken) throws Exception;
}
