package com.soapadoberequest.eps;

import jakarta.xml.soap.MessageFactory;
import jakarta.xml.soap.SOAPMessage;
import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Node;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import io.github.cdimascio.dotenv.Dotenv;

/**
 * This class contains the methods used in to execute SOAP requests for authentication it implements its methods
 * from the interface ISOAPAuth
 */
public class SOAPAuth implements ISOAPAuth{
    //Dotenv to get the information stored in the .env file
    Dotenv dotenv=Dotenv.configure().ignoreIfMissing().ignoreIfMalformed().load();

    //Logger to initiate the logs
    Logger logger = Logger.getLogger("logger");

    /**
     * This function sends a SOAP request to authenticate and returns the sessionToken and the securityToken
     *
     * @param login User's adobe campaign login
     * @param password User's adobe campaign password
     * @param port Adobe Campaign's server port
     * @return SessionToken and securityToken
     * @throws Exception Throws exception when failure
     */
    public ArrayList<Node> postSOAPAuth(String login, String password, String port) throws Exception {
        String resp;
        Node securityToken = null;
        Node sessionToken = null;
        try {
            String soapBody = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" " +
                    "xmlns:urn=\"urn:xtk:session\">\n" +
                    "   <soapenv:Header/>\n" +
                    "   <soapenv:Body>\n" +
                    "      <urn:Logon>\n" +
                    "         <urn:sessiontoken/>\n" +
                    "         <urn:strLogin>"+login+"</urn:strLogin>\n" +
                    "         <urn:strPassword>"+password+"</urn:strPassword>\n" +
                    "         <urn:elemParameters>\n" +
                    "         </urn:elemParameters>\n" +
                    "      </urn:Logon>\n" +
                    "   </soapenv:Body>\n" +
                    "</soapenv:Envelope>";

            HttpClientClass httpClientClass = new HttpClientClass();
            HttpEntity respEntity =  httpClientClass.httpClientLogon(soapBody);

            if (respEntity != null) {
                resp = EntityUtils.toString(respEntity);

                //Convert response to SOAP Message
                InputStream is = new ByteArrayInputStream(resp.getBytes());
                SOAPMessage soapResp = MessageFactory.newInstance().createMessage(null, is);

                //Check the firstChild of the SOAPResponse to determine whether the response has a fault envelope or not
                String firstChild = soapResp.getSOAPBody().getFirstChild().getNodeName();
                if(!firstChild.equals("SOAP-ENV:Fault")){
                    System.out.println("Connected and ready to run...\n");
                    //Print logs
                    String loggerInfo = Formatter.prettyPrintByDom4j(resp,4, true);
                    logger.log(Level.INFO,"Authentication SOAP successful:");
                } else{
                    System.out.println("Connection not made, please check the logs for further details.\n");
                    //Print logs
                    String loggerInfo = Formatter.prettyPrintByDom4j(resp,4, true);
                    logger.log(Level.INFO,"Authentication SOAP request XML response (with errors):");
                    logger.log(Level.INFO,loggerInfo);
                }

                //Retrieve the sessionToken and securityToken based on their elements' tagNames
                sessionToken =  soapResp.getSOAPBody().getElementsByTagName("pstrSessionToken").item(0);
                securityToken =  soapResp.getSOAPBody().getElementsByTagName("pstrSecurityToken").item(0);

            } else {
                logger.log(Level.WARNING,"No Response, connection has not being made.");
            }

            //Populate the tokens Arraylist
            ArrayList<Node> tokens = new ArrayList<>();
            tokens.add(sessionToken);
            tokens.add(securityToken);

            //return the tokens
            return tokens;

        } catch (Exception e) {
            logger.log(Level.WARNING,"WebService SOAP exception = {0}", e);
            throw new Exception("WebService SOAP exception = " + e);

        }
    }

    /**
     * This function sends a SOAP request to subscribe a recipient to a service
     *
     * @param recipient The recipient's object
     * @param serviceName The name of service
     * @param sessionToken Token of the session (__sessiontoken)
     * @param securityToken Security token of the session (X-Security-Token)
     * @throws Exception Throws exception when failure
     */
    public void postSOAPSubscribe(Recipient recipient, String serviceName, String sessionToken,
                                  String securityToken) throws Exception{
        String resp;

        try {
            String soapBody = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" " +
                    "xmlns:urn=\"urn:nms:subscription\">\n" +
                    "   <soapenv:Header/>\n" +
                    "   <soapenv:Body>\n" +
                    "      <urn:Subscribe>\n" +
                    "         <urn:sessiontoken/>\n" +
                    "         <urn:strServiceName>"+serviceName+"</urn:strServiceName>\n" +
                    "         <urn:elemRecipient>\n" +
                    "            <recipient email=\""+recipient.getEmail()+"\" lastName=\""+recipient.getLastName()+"\" " +
                    "               firstName=\""+recipient.getFirstName()+"\" _key=\"@email\"/>\n" +
                    "         </urn:elemRecipient>\n" +
                    "         <urn:bCreate>true</urn:bCreate>\n" +
                    "      </urn:Subscribe>\n" +
                    "   </soapenv:Body>\n" +
                    "</soapenv:Envelope>";

            HttpClientClass httpClientClass = new HttpClientClass();
            HttpEntity respEntity =  httpClientClass.httpClientCall(soapBody, "nms:subscription#Subscribe",
                    sessionToken, securityToken);

            if (respEntity != null) {
                resp = EntityUtils.toString(respEntity);

                //prints whole response
                String loggerInfo = Formatter.prettyPrintByDom4j(resp,4, true);
                logger.log(Level.INFO,"Subscribe recipient to service SOAP request XML response:");
                logger.log(Level.INFO,loggerInfo);

            } else {
                logger.log(Level.WARNING,"No Response");
            }

        } catch (Exception e) {
            logger.log(Level.WARNING,"WebService SOAP exception = {0}", e);
            throw new Exception("WebService SOAP exception = " + e);
        }
    }

}