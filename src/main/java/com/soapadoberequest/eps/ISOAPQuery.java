package com.soapadoberequest.eps;

/**
 * This class is an interface containing the methods used in the class SOAPQuery in order to execute SOAP requests
 * for queries like the selection and insertion
 */
public interface ISOAPQuery {
    /**
     * This function sends a SOAP request to select a recipient using the email
     *
     * @param email Email of recipient
     * @param sessionToken Token of the session (__sessiontoken)
     * @param securityToken Security token of the session (X-Security-Token)
     * @throws Exception Throws exception when failure
     */
    public void postSOAPSelect(String email, String sessionToken, String securityToken) throws Exception;

    /**
     * This function sends a SOAP request to write(Insert) a collection of entries (recipient - deliveries -
     * workflow) from csv file
     *
     * @param filename The name of the file containing the entries
     * @param sessionToken Token of the session (__sessiontoken)
     * @param securityToken Security token of the session (X-Security-Token)
     * @throws Exception Throws exception when failure
     */
    public void postSOAPWriteCollection(String filename, String sessionToken, String securityToken) throws Exception;

    /**
     * This function sends a SOAP request to select the count of table
     *
     * @param prefix Prefix of the schema (ex: nms)
     * @param tableName The name of the schema (ex: recipient)
     * @param sessionToken Token of the session (__sessiontoken)
     * @param securityToken Security token of the session (X-Security-Token)
     * @return Returns the count
     * @throws Exception Throws exception when failure
     */
    public String postSOAPSelectCount(String prefix, String tableName, String sessionToken, String securityToken)
            throws Exception;

    /**
     * This function sends a SOAP request to select last entry's id
     *
     * @param prefix Prefix of the schema (ex: nms)
     * @param tableName The name of the schema (ex: recipient)
     * @param sessionToken Token of the session (__sessiontoken)
     * @param securityToken Security token of the session (X-Security-Token)
     * @return The last entry
     * @throws Exception Throws exception when failure
     */
    public String postSOAPSelectLast(String prefix, String tableName, String sessionToken, String securityToken)
            throws Exception;

    /**
     * This function sends a SOAP request to write(Insert, update and delete) a collection of entries
     *
     * @param recipient The recipient object
     * @param sessionToken Token of the session (__sessiontoken)
     * @param securityToken Security token of the session (X-Security-Token)
     * @throws Exception Throws exception when failure
     */
    public void postSOAPWrite(Recipient recipient, String sessionToken, String securityToken) throws Exception;
}