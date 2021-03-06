package com.soapadoberequest.eps;

import jakarta.xml.soap.MessageFactory;
import jakarta.xml.soap.SOAPMessage;
import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * This class contains the SOAP requests for different workflow functions, it implements its methods from the interface
 * ISOAPWorkflow
 */
public class SOAPWorkflow implements ISOAPWorkflow {
    //Logger to initiate the logs
    Logger logger = Logger.getLogger("logger");

    /**
     * This function sends a SOAP request to start a workflow
     *
     * @param workflowId The ID of the workflow
     * @param sessionToken Token of the session (__sessiontoken)
     * @param securityToken Security token of the session (X-Security-Token)
     * @throws Exception Throws exception when failure
     */
    @Override
    public void postSOAPStartWorkflow(String workflowId, String sessionToken, String securityToken) throws Exception {
        String resp;
        try {
            String soapBody = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" " +
                    "xmlns:urn=\"urn:xtk:workflow\">\n" +
                    "   <soapenv:Header/>\n" +
                    "   <soapenv:Body>\n" +
                    "      <urn:Start>\n" +
                    "         <urn:sessiontoken/>\n" +
                    "         <urn:strWorkflowId>"+workflowId+"</urn:strWorkflowId>\n" +
                    "      </urn:Start>\n" +
                    "   </soapenv:Body>\n" +
                    "</soapenv:Envelope>";

            HttpClientClass httpClientClass = new HttpClientClass();
            HttpEntity respEntity =  httpClientClass.httpClientCall(soapBody, "xtk:workflow#Start",
                    sessionToken, securityToken);

            if (respEntity != null) {
                resp = EntityUtils.toString(respEntity);

                //Convert response to SOAP Message
                InputStream is = new ByteArrayInputStream(resp.getBytes());
                SOAPMessage soapResp = MessageFactory.newInstance().createMessage(null, is);

                //Check the firstChild of the SOAPResponse to determine whether the response has a fault envelope or not
                String firstChild = soapResp.getSOAPBody().getFirstChild().getNodeName();
                if(!firstChild.equals("SOAP-ENV:Fault")){
                    System.out.println("The workflow '"+workflowId+"' has successfully started. \n");
                    //Print logs
                    String loggerInfo = Formatter.prettyPrintByDom4j(resp,4, true);
                    logger.log(Level.INFO,"Start Workflow SOAP request XML response:");
                    logger.log(Level.INFO,loggerInfo);
                } else{
                    System.out.println("The workflow '"+workflowId+"' hasn't started, please check the logs for further " +
                            "details.\n");
                    //Print logs
                    String loggerInfo = Formatter.prettyPrintByDom4j(resp,4, true);
                    logger.log(Level.INFO,"Start Workflow SOAP request XML response (with errors):");
                    logger.log(Level.INFO,loggerInfo);
                }

            } else {
                logger.log(Level.WARNING,"No Response");
            }

        } catch (Exception e) {
            logger.log(Level.WARNING,"WebService SOAP exception = {0}", e);
            throw new Exception("WebService SOAP exception = " + e);
        }
    }

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
    @Override
    public void postSOAPPostEvent(String workFlowId, String activity, List<String> vars, List<String> param,
                                  String sessionToken, String securityToken) throws Exception {
        String resp;
        ArrayList<String> varBuilder = new ArrayList<>();
        for (int i = 0; i <param.size(); i++){
            varBuilder.add(vars.get(i)+'='+'"'+param.get(i)+'"');
        }
        String varSentence = varBuilder.stream().collect(Collectors.joining(" "));
        try {

            String soapBody = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" " +
                    "xmlns:urn=\"urn:xtk:workflow\">\n" +
                    "   <soapenv:Header/>\n" +
                    "   <soapenv:Body>\n" +
                    "      <urn:PostEvent>\n" +
                    "         <urn:sessiontoken/>\n" +
                    "         <urn:strWorkflowId>"+workFlowId+"</urn:strWorkflowId>\n" +
                    "         <urn:strActivity>"+activity+"</urn:strActivity>\n" +
                    "         <urn:strTransition></urn:strTransition>\n" +
                    "         <urn:elemParameters>\n" +
                    "            <variables "+ varSentence +" />\n" +
                    "         </urn:elemParameters>\n" +
                    "         <urn:bComplete>false</urn:bComplete>\n" +
                    "      </urn:PostEvent>\n" +
                    "   </soapenv:Body>\n" +
                    "</soapenv:Envelope>";

            HttpClientClass httpClientClass = new HttpClientClass();
            HttpEntity respEntity =  httpClientClass.httpClientCall(soapBody, "xtk:workflow#PostEvent",
                    sessionToken, securityToken);

            if (respEntity != null) {
                resp = EntityUtils.toString(respEntity);

                //Convert response to SOAP Message
                InputStream is = new ByteArrayInputStream(resp.getBytes());
                SOAPMessage soapResp = MessageFactory.newInstance().createMessage(null, is);

                //Check the firstChild of the SOAPResponse to determine whether the response has a fault envelope or not
                String firstChild = soapResp.getSOAPBody().getFirstChild().getNodeName();
                if(!firstChild.equals("SOAP-ENV:Fault")){
                    System.out.println("The postEvent of the workflow '"+workFlowId+"' was successful. \n");
                    //Print logs
                    String loggerInfo = Formatter.prettyPrintByDom4j(resp,4, true);
                    logger.log(Level.INFO,"Post event workflow SOAP request XML response:");
                    logger.log(Level.INFO,loggerInfo);
                } else{
                    System.out.println("The postEvent of the workflow '"+workFlowId+"' has failed, " +
                            "please check the logs for further " +
                            "details.\n");
                    //Print logs
                    String loggerInfo = Formatter.prettyPrintByDom4j(resp,4, true);
                    logger.log(Level.INFO,"Post event workflow SOAP request XML response (with errors):");
                    logger.log(Level.INFO,loggerInfo);
                }

            } else {
                logger.log(Level.WARNING,"No Response");
            }

        } catch (Exception e) {
            logger.log(Level.WARNING,"WebService SOAP exception = {0}", e);
            throw new Exception("WebService SOAP exception = " + e);
        }
    }

    /**
     * This function sends a SOAP request to kill a workflow
     *
     * @param workFlowId The ID of the workflow
     * @param sessionToken Token of the session (__sessiontoken)
     * @param securityToken Security token of the session (X-Security-Token)
     * @throws Exception Throws exception when failure
     */
    @Override
    public void postSOAPKillWorkflow(String workFlowId, String sessionToken, String securityToken) throws Exception {
        String resp;
        try {

            String soapBody = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" " +
                    "xmlns:urn=\"urn:xtk:workflow\">\n" +
                    "   <soapenv:Header/>\n" +
                    "   <soapenv:Body>\n" +
                    "      <urn:Kill>\n" +
                    "         <urn:sessiontoken/>\n" +
                    "         <urn:strWorkflowId>"+workFlowId+"</urn:strWorkflowId>\n" +
                    "      </urn:Kill>\n" +
                    "   </soapenv:Body>\n" +
                    "</soapenv:Envelope>";

            HttpClientClass httpClientClass = new HttpClientClass();
            HttpEntity respEntity =  httpClientClass.httpClientCall(soapBody, "xtk:workflow#Kill",
                    sessionToken, securityToken);

            if (respEntity != null) {
                resp = EntityUtils.toString(respEntity);

                //Convert response to SOAP Message
                InputStream is = new ByteArrayInputStream(resp.getBytes());
                SOAPMessage soapResp = MessageFactory.newInstance().createMessage(null, is);

                //Check the firstChild of the SOAPResponse to determine whether the response has a fault envelope or not
                String firstChild = soapResp.getSOAPBody().getFirstChild().getNodeName();
                if(!firstChild.equals("SOAP-ENV:Fault")){
                    System.out.println("The workflow '"+workFlowId+"' was been successful killed. \n");
                    //Print logs
                    String loggerInfo = Formatter.prettyPrintByDom4j(resp,4, true);
                    logger.log(Level.INFO,"Kill workflow SOAP request XML response:");
                    logger.log(Level.INFO,loggerInfo);
                } else{
                    System.out.println("The workflow '"+workFlowId+"' kill has failed, " +
                            "please check the logs for further " +
                            "details.\n");
                    //Print logs
                    String loggerInfo = Formatter.prettyPrintByDom4j(resp,4, true);
                    logger.log(Level.INFO,"Kill workflow SOAP request XML response (with errors):");
                    logger.log(Level.INFO,loggerInfo);
                }

            } else {
                logger.log(Level.WARNING,"No Response");
            }

        } catch (Exception e) {
            logger.log(Level.WARNING,"WebService SOAP exception = {0}", e);
            throw new Exception("WebService SOAP exception = " + e);
        }
    }

    /**
     * This function sends a SOAP request to pause a workflow
     *
     * @param workFlowId The ID of the workflow
     * @param sessionToken Token of the session (__sessiontoken)
     * @param securityToken Security token of the session (X-Security-Token)
     * @throws Exception Throws exception when failure
     */
    @Override
    public void postSOAPPauseWorkflow(String workFlowId, String sessionToken, String securityToken) throws Exception {
        String resp;
        try {
            String soapBody = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" " +
                    "xmlns:urn=\"urn:xtk:workflow\">\n" +
                    "   <soapenv:Header/>\n" +
                    "   <soapenv:Body>\n" +
                    "      <urn:Pause>\n" +
                    "         <urn:sessiontoken/>\n" +
                    "         <urn:strWorkflowId>"+workFlowId+"</urn:strWorkflowId>\n" +
                    "      </urn:Pause>\n" +
                    "   </soapenv:Body>\n" +
                    "</soapenv:Envelope>";

            HttpClientClass httpClientClass = new HttpClientClass();
            HttpEntity respEntity =  httpClientClass.httpClientCall(soapBody, "xtk:workflow#Pause",
                    sessionToken, securityToken);

            if (respEntity != null) {
                resp = EntityUtils.toString(respEntity);

                //Convert response to SOAP Message
                InputStream is = new ByteArrayInputStream(resp.getBytes());
                SOAPMessage soapResp = MessageFactory.newInstance().createMessage(null, is);

                //Check the firstChild of the SOAPResponse to determine whether the response has a fault envelope or not
                String firstChild = soapResp.getSOAPBody().getFirstChild().getNodeName();
                if(!firstChild.equals("SOAP-ENV:Fault")){
                    System.out.println("The workflow '"+workFlowId+"' has been successfully paused. \n");
                    //Print logs
                    String loggerInfo = Formatter.prettyPrintByDom4j(resp,4, true);
                    logger.log(Level.INFO,"Pause workflow SOAP request XML response:");
                    logger.log(Level.INFO,loggerInfo);
                } else{
                    System.out.println("The workflow '"+workFlowId+"' has not successfully been paused, " +
                            "please check the logs for further " +
                            "details.\n");
                    //Print logs
                    String loggerInfo = Formatter.prettyPrintByDom4j(resp,4, true);
                    logger.log(Level.INFO,"Pause workflow SOAP request XML response (with errors):");
                    logger.log(Level.INFO,loggerInfo);
                }

            } else {
                logger.log(Level.WARNING,"No Response");
            }
        } catch (Exception e) {
            logger.log(Level.WARNING,"WebService SOAP exception = {0}", e);
            throw new Exception("WebService SOAP exception = " + e);
        }
    }

    /**
     * This function sends a SOAP request to wake up a workflow
     *
     * @param workFlowId The ID of the workflow
     * @param sessionToken Token of the session (__sessiontoken)
     * @param securityToken Security token of the session (X-Security-Token)
     * @throws Exception Throws exception when failure
     */
    @Override
    public void postSOAPWakeUpWorkflow(String workFlowId, String sessionToken, String securityToken) throws Exception {
        String resp;
        try {

            String soapBody = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" " +
                    "xmlns:urn=\"urn:xtk:workflow\">\n" +
                    "   <soapenv:Header/>\n" +
                    "   <soapenv:Body>\n" +
                    "      <urn:Wakeup>\n" +
                    "         <urn:sessiontoken/>\n" +
                    "         <urn:strWorkflowId>"+workFlowId+"</urn:strWorkflowId>\n" +
                    "      </urn:Wakeup>\n" +
                    "   </soapenv:Body>\n" +
                    "</soapenv:Envelope>";

            HttpClientClass httpClientClass = new HttpClientClass();
            HttpEntity respEntity =  httpClientClass.httpClientCall(soapBody, "xtk:workflow#WakeUp",
                    sessionToken, securityToken);
            if (respEntity != null) {
                resp = EntityUtils.toString(respEntity);

                //Convert response to SOAP Message
                InputStream is = new ByteArrayInputStream(resp.getBytes());
                SOAPMessage soapResp = MessageFactory.newInstance().createMessage(null, is);

                //Check the firstChild of the SOAPResponse to determine whether the response has a fault envelope or not
                String firstChild = soapResp.getSOAPBody().getFirstChild().getNodeName();
                if(!firstChild.equals("SOAP-ENV:Fault")){
                    System.out.println("The workflow '"+workFlowId+"' has been successfully woken up. \n");
                    //Print logs
                    String loggerInfo = Formatter.prettyPrintByDom4j(resp,4, true);
                    logger.log(Level.INFO,"Wake Up workflow SOAP request XML response:");
                    logger.log(Level.INFO,loggerInfo);
                } else{
                    System.out.println("The workflow '"+workFlowId+"' has not successfully been woken up, " +
                            "please check the logs for further " +
                            "details.\n");
                    //Print logs
                    String loggerInfo = Formatter.prettyPrintByDom4j(resp,4, true);
                    logger.log(Level.INFO,"Wake Up workflow SOAP request XML response (with errors):");
                    logger.log(Level.INFO,loggerInfo);
                }

            } else {
                logger.log(Level.WARNING,"No Response");
            }

        } catch (Exception e) {
            logger.log(Level.WARNING,"WebService SOAP exception = {0}", e);
            throw new Exception("WebService SOAP exception = " + e);
        }
    }

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
    @Override
    public void postSOAPStartWithParams(String workFlowId, List<String> vars,
                                        List<String> param, String sessionToken, String securityToken)
            throws Exception{
        String resp;
        ArrayList<String> varBuilder = new ArrayList<>();
        for (int i = 0; i <param.size(); i++){
            varBuilder.add(vars.get(i)+'='+'"'+param.get(i)+'"');
        }
        String varSentence = varBuilder.stream().collect(Collectors.joining(" "));
        try {

            String soapBody = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" " +
                    "xmlns:urn=\"urn:xtk:workflow\">\n" +
                    "   <soapenv:Header/>\n" +
                    "   <soapenv:Body>\n" +
                    "      <urn:StartWithParameters>\n" +
                    "         <urn:sessiontoken/>\n" +
                    "         <urn:strWorkflowId>"+workFlowId+"</urn:strWorkflowId>\n" +
                    "         <urn:elemParameters>\n" +
                    "             <variables "+ varSentence +" />\n" +
                    "         </urn:elemParameters>\n" +
                    "      </urn:StartWithParameters>\n" +
                    "   </soapenv:Body>\n" +
                    "</soapenv:Envelope>";

            HttpClientClass httpClientClass = new HttpClientClass();
            HttpEntity respEntity =  httpClientClass.httpClientCall(soapBody, "xtk:workflow#StartWithParameters",
                    sessionToken, securityToken);

            if (respEntity != null) {
                resp = EntityUtils.toString(respEntity);

                //Convert response to SOAP Message
                InputStream is = new ByteArrayInputStream(resp.getBytes());
                SOAPMessage soapResp = MessageFactory.newInstance().createMessage(null, is);

                //Check the firstChild of the SOAPResponse to determine whether the response has a fault envelope or not
                String firstChild = soapResp.getSOAPBody().getFirstChild().getNodeName();
                if(!firstChild.equals("SOAP-ENV:Fault")){
                    System.out.println("The workflow '"+workFlowId+"' has successfully started with Parameters. \n");
                    //Print logs
                    String loggerInfo = Formatter.prettyPrintByDom4j(resp,4, true);
                    logger.log(Level.INFO,"Start workflow with parameters SOAP request XML response:");
                    logger.log(Level.INFO,loggerInfo);
                } else{
                    System.out.println("The workflow '"+workFlowId+"' has not successfully started with parameters, " +
                            "please check the logs for further " +
                            "details.\n");
                    //Print logs
                    String loggerInfo = Formatter.prettyPrintByDom4j(resp,4, true);
                    logger.log(Level.INFO,"Start workflow with parameters SOAP request XML response (with errors):");
                    logger.log(Level.INFO,loggerInfo);
                }

            } else {
                logger.log(Level.WARNING,"No Response");
            }

        } catch (Exception e) {
            logger.log(Level.WARNING,"WebService SOAP exception = {0}", e);
            throw new Exception("WebService SOAP exception = " + e);
        }
    }

    /**
     * This function sends a SOAP request the logs of a workflow
     *
     * @param internalName The internal name of the workflow
     * @param sessionToken Token of the session (__sessiontoken)
     * @param securityToken Security token of the session (X-Security-Token)
     * @throws Exception Throws exception when failure
     */
    @Override
    public void postSOAPWorkflowLogs(String internalName, String sessionToken, String securityToken)
            throws Exception {
        String resp;

        String workflowId = postSOAPSelectWorkflow(internalName, sessionToken, securityToken  );
        try {
            String soapBody = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" " +
                    "xmlns:urn=\"urn:xtk:queryDef\">\n" +
                    "   <soapenv:Header/>\n" +
                    "   <soapenv:Body>\n" +
                    "      <urn:ExecuteQuery>\n" +
                    "         <urn:sessiontoken/>\n" +
                    "         <urn:entity>\n" +
                    "            <queryDef operation=\"select\" schema=\"xtk:workflowLog\">\n" +
                    "            <select>\n" +
                    "              <node expr=\"@activity\"/>\n" +
                    "              <node expr=\"@logDate\"/>\n" +
                    "              <node expr=\"@error\"/>\n" +
                    "              <node expr=\"@logType\"/>\n" +
                    "              <node expr=\"@message\"/>\n" +
                    "            </select>\n" +
                    "            <where>\n" +
                    "              <condition expr=\"[@workflow-id] = "+workflowId+"\"/>\n" +
                    "            </where>\n" +
                    "          </queryDef>\n" +
                    "         </urn:entity>\n" +
                    "      </urn:ExecuteQuery>\n" +
                    "   </soapenv:Body>\n" +
                    "</soapenv:Envelope>";

            HttpClientClass httpClientClass = new HttpClientClass();
            HttpEntity respEntity =  httpClientClass.httpClientCall(soapBody, "xtk:queryDef#ExecuteQuery",
                    sessionToken, securityToken);

            if (respEntity != null) {
                resp = EntityUtils.toString(respEntity);

                //Convert response to SOAP Message
                InputStream is = new ByteArrayInputStream(resp.getBytes());
                SOAPMessage soapResp = MessageFactory.newInstance().createMessage(null, is);

                //Check the firstChild of the SOAPResponse to determine whether the response has a fault envelope or not
                String firstChild = soapResp.getSOAPBody().getFirstChild().getNodeName();
                if(!firstChild.equals("SOAP-ENV:Fault")){
                    System.out.println("The logs of the workflow '"+workflowId+"' have successfully been retrieved. \n");
                    //Print logs into xml file
                    try {
                        String userHomeFolder = System.getProperty("user.home");
                        String filename = userHomeFolder+"\\Desktop\\Logs-"+internalName+".xml";
                        FileWriter myWriter = new FileWriter(filename);
                        myWriter.write(Formatter.prettyPrintByDom4j(resp,4, true));
                        myWriter.close();
                        logger.log(Level.INFO, "Successfully wrote to the file.");
                        logger.log(Level.INFO, "The file path: {0}", Path.of(filename).toUri());
                    } catch (IOException e) {
                        throw new Exception("IO exception = " + e);
                    }
                } else{
                    System.out.println("The logs of the workflow '"+workflowId+"' have not successfully been retrieved, " +
                            "please check the logs for further " +
                            "details.\n");
                    //Print logs
                    String loggerInfo = Formatter.prettyPrintByDom4j(resp,4, true);
                    logger.log(Level.INFO, "No writings have been inserted into the file.");
                    logger.log(Level.INFO,loggerInfo);
                }

            } else {
                logger.log(Level.WARNING,"No Response");
            }

        } catch (Exception e) {
            logger.log(Level.WARNING,"WebService SOAP exception = {0}", e);
            throw new Exception("WebService SOAP exception = " + e);
        }
    }

    /**
     * This function sends a SOAP request the logs of a workflow
     *
     * @param workflowInternalName The internal name of the workflow
     * @param sessionToken Token of the session (__sessiontoken)
     * @param securityToken Security token of the session (X-Security-Token)
     * @throws Exception Throws e when failure
     */
    @Override
    public void postSOAPWorkflowState(String workflowInternalName, String sessionToken, String securityToken)
            throws Exception{
        String resp;
        try {

            String soapBody = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" " +
                    "xmlns:urn=\"urn:xtk:queryDef\">\n" +
                    "   <soapenv:Header/>\n" +
                    "   <soapenv:Body>\n" +
                    "      <urn:ExecuteQuery>\n" +
                    "         <urn:sessiontoken/>\n" +
                    "         <urn:entity>\n" +
                    "            <queryDef operation=\"select\" schema=\"xtk:workflow\">\n" +
                    "            <select>\n" +
                    "              <node expr=\"@state\"/>\n" +
                    "            </select>\n" +
                    "            <where>\n" +
                    "              <condition expr=\"@internalName = '"+workflowInternalName+"'\"/>\n" +
                    "            </where>\n" +
                    "          </queryDef>\n" +
                    "         </urn:entity>\n" +
                    "      </urn:ExecuteQuery>\n" +
                    "   </soapenv:Body>\n" +
                    "</soapenv:Envelope>";

            HttpClientClass httpClientClass = new HttpClientClass();
            HttpEntity respEntity =  httpClientClass.httpClientCall(soapBody, "xtk:queryDef#ExecuteQuery",
                    sessionToken, securityToken);

            if (respEntity != null) {
                resp = EntityUtils.toString(respEntity);

                //Convert response to SOAP Message
                InputStream is = new ByteArrayInputStream(resp.getBytes());
                SOAPMessage soapResp = MessageFactory.newInstance().createMessage(null, is);

                //Check the firstChild of the SOAPResponse to determine whether the response has a fault envelope or not
                String firstChild = soapResp.getSOAPBody().getFirstChild().getNodeName();
                if(!firstChild.equals("SOAP-ENV:Fault")){
                    System.out.println("The state of the workflow '"+workflowInternalName+"' has successfully been retrieved. \n");
                    //Print logs
                    String loggerInfo = Formatter.prettyPrintByDom4j(resp,4, true);
                    logger.log(Level.INFO,"Select workflow state SOAP request XML response:");
                    logger.log(Level.INFO,loggerInfo);
                } else{
                    System.out.println("The state of the workflow '"+workflowInternalName+"' has not successfully been retrieved., " +
                            "please check the logs for further " +
                            "details.\n");
                    //Print logs
                    String loggerInfo = Formatter.prettyPrintByDom4j(resp,4, true);
                    logger.log(Level.INFO,"Select workflow state SOAP request XML response (with errors):");
                    logger.log(Level.INFO,loggerInfo);
                }

            } else {
                logger.log(Level.WARNING,"No Response");
            }

        } catch (Exception e) {
            logger.log(Level.WARNING,"WebService SOAP exception = {0}", e);
            throw new Exception("WebService SOAP exception = " + e);
        }
    }

    /**
     * This function sends a SOAP request the failed status of the workflow
     *
     * @param workflowInternalName The internal name of the workflow
     * @param sessionToken Token of the session (__sessiontoken)
     * @param securityToken Security token of the session (X-Security-Token)
     * @throws Exception Throws e when failure
     */
    @Override
    public List<String> postSOAPWorkflowFailed(String workflowInternalName, String sessionToken, String securityToken)
            throws Exception{
        String resp;
        try {

            String soapBody = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" " +
                    "xmlns:urn=\"urn:xtk:queryDef\">\n" +
                    "   <soapenv:Header/>\n" +
                    "   <soapenv:Body>\n" +
                    "      <urn:ExecuteQuery>\n" +
                    "         <urn:sessiontoken/>\n" +
                    "         <urn:entity>\n" +
                    "            <queryDef operation=\"select\" schema=\"xtk:workflow\">\n" +
                    "            <select>\n" +
                    "              <node expr=\"@internalName\"/>\n" +
                    "              <node expr=\"@failed\"/>\n" +
                    "              <node expr=\"@status\"/>\n" +
                    "              <node expr=\"@errorActivity\"/>\n"+
                    "            </select>\n" +
                    "            <where>\n" +
                    "              <condition expr=\"@internalName = '"+workflowInternalName+"'\"/>\n" +
                    "            </where>\n" +
                    "          </queryDef>\n" +
                    "         </urn:entity>\n" +
                    "      </urn:ExecuteQuery>\n" +
                    "   </soapenv:Body>\n" +
                    "</soapenv:Envelope>";

            HttpClientClass httpClientClass = new HttpClientClass();
            HttpEntity respEntity =  httpClientClass.httpClientCall(soapBody, "xtk:queryDef#ExecuteQuery",
                    sessionToken, securityToken);

            if (respEntity != null) {
                resp = EntityUtils.toString(respEntity);

                //prints whole response
                String loggerInfo = Formatter.prettyPrintByDom4j(resp,4, true);
                logger.log(Level.INFO,"Select workflow's failure state SOAP request XML response");
                logger.log(Level.INFO,loggerInfo);

                //Convert response to SOAP Message
                InputStream is = new ByteArrayInputStream(resp.getBytes());
                SOAPMessage soapResp = MessageFactory.newInstance().createMessage(null, is);

                //Retrieve the deliveryId based on their attribute's name and return it
                String failed = soapResp.getSOAPBody().getElementsByTagName("workflow").item(0)
                        .getAttributes().getNamedItem("failed").getNodeValue();

                ArrayList<String> rs = new ArrayList<>();
                String realStatus;
                String errorActivity;
                rs.add(failed);
                if(failed.equals("1")) {
                    realStatus = soapResp.getSOAPBody().getElementsByTagName("workflow").item(0)
                            .getAttributes().getNamedItem("status").getNodeValue();
                    errorActivity = soapResp.getSOAPBody().getElementsByTagName("workflow").item(0)
                            .getAttributes().getNamedItem("errorActivity").getNodeValue();
                }else{
                    realStatus = "0";
                    errorActivity="N/A";
                }
                rs.add(errorActivity);
                rs.add(realStatus);
                System.out.println("failed= "+ rs.get(0)+"\n"
                + "status= "+ rs.get(1)+"\n"
                + "errorActivity= "+rs.get(2));

                return rs;
            } else {
                logger.log(Level.WARNING,"No Response");
            }

        } catch (Exception e) {
            logger.log(Level.WARNING,"WebService SOAP exception = {0}", e);
            throw new Exception("WebService SOAP exception = " + e);
        }
        return null;
    }

    /**
     * This function sends a SOAP request to return the workflow ID based on its workflow internalName
     *
     * @param internalName Internal name of workflow
     * @param sessionToken Token of the session (__sessiontoken)
     * @param securityToken Security token of the session (X-Security-Token)
     * @throws Exception Throws exception when failure
     * @return Returns the workflow's ID
     */
    @Override
    public String postSOAPSelectWorkflow(String internalName, String sessionToken, String securityToken)
            throws Exception {
        String resp;
        try {

            String soapBody = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" " +
                    "xmlns:urn=\"urn:xtk:queryDef\">\n" +
                    "   <soapenv:Header/>\n" +
                    "   <soapenv:Body>\n" +
                    "      <urn:ExecuteQuery>\n" +
                    "         <urn:sessiontoken/>\n" +
                    "         <urn:entity>\n" +
                    "            <queryDef operation=\"select\" schema=\"xtk:workflow\">\n" +
                    "            <select>\n" +
                    "              <node expr=\"@id\"/>\n" +
                    "            </select>\n" +
                    "            <where>\n" +
                    "              <condition expr=\"@internalName = '"+internalName+"'\"/>\n" +
                    "            </where>\n" +
                    "          </queryDef>\n" +
                    "         </urn:entity>\n" +
                    "      </urn:ExecuteQuery>\n" +
                    "   </soapenv:Body>\n" +
                    "</soapenv:Envelope>";

            HttpClientClass httpClientClass = new HttpClientClass();
            HttpEntity respEntity =  httpClientClass.httpClientCall(soapBody, "xtk:queryDef#ExecuteQuery",
                    sessionToken, securityToken);

            if (respEntity != null) {
                resp = EntityUtils.toString(respEntity);

                //prints whole response
                String loggerInfo = Formatter.prettyPrintByDom4j(resp,4, true);
                logger.log(Level.INFO,"Select workflow SOAP request XML response");
                logger.log(Level.INFO,loggerInfo);

                //Convert response to SOAP Message
                InputStream is = new ByteArrayInputStream(resp.getBytes());
                SOAPMessage soapResp = MessageFactory.newInstance().createMessage(null, is);

                //Retrieve the deliveryId based on their attribute's name and return it
                return soapResp.getSOAPBody().getElementsByTagName("workflow").item(0)
                        .getAttributes().getNamedItem("id").getNodeValue();

            } else {
                logger.log(Level.WARNING,"No Response");
                return null;
            }

        } catch (Exception e) {
            logger.log(Level.WARNING,"WebService SOAP exception = {0}", e);
            throw new Exception("WebService SOAP exception = " + e);
        }
    }

    /**
     * This function sends multiple SOAP requests to check workflows' status and define which ones failed, it also
     * returns the activity in which the workflow failed
     *
     * @param internalName Internal name of workflow
     * @param sessionToken Token of the session (__sessiontoken)
     * @param securityToken Security token of the session (X-Security-Token)
     * @throws Exception Throws exception when failure
     */
    @Override
    public void checkWorkflowStatus(String internalName, String sessionToken, String securityToken) throws Exception {
       List<String> rs = postSOAPWorkflowFailed(internalName, sessionToken, securityToken);
       if(rs.get(0).equals("1")){
           if(rs.get(2).equals("3")){
               logger.log(Level.INFO, "The workflow has failed while executing the activity {0}", rs.get(1));
               postSOAPWorkflowLogs(internalName, sessionToken, securityToken);
           }else if(rs.get(2).equals("5")){
               postSOAPWorkflowLogs(internalName, sessionToken, securityToken);
           }
       }
       rs.clear();
    }

    /**
     * This function sends a SOAP request the internalNames of all the paused (state= 13) and stopped (state=20)
     * workflows
     *
     * @param sessionToken Token of the session (__sessiontoken)
     * @param securityToken Security token of the session (X-Security-Token)
     * @throws Exception Throws e when failure
     */
    @Override
    public List<String> postSOAPPausedAndStoppedWKF(String sessionToken, String securityToken)
            throws Exception{
        String resp;
        try {

            String soapBody = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" " +
                    "xmlns:urn=\"urn:xtk:queryDef\">\n" +
                    "   <soapenv:Header/>\n" +
                    "   <soapenv:Body>\n" +
                    "      <urn:ExecuteQuery>\n" +
                    "         <urn:sessiontoken/>\n" +
                    "         <urn:entity>\n" +
                    "            <queryDef operation=\"select\" schema=\"xtk:workflow\">\n" +
                    "            <select>\n" +
                    "              <node expr=\"@internalName\"/>\n"+
                    "            </select>\n" +
                    "            <where>\n" +
                    "               <condition expr=\"@state= '20' or @state= '13'\"/>\n"+
                    "            </where>\n" +
                    "          </queryDef>\n" +
                    "         </urn:entity>\n" +
                    "      </urn:ExecuteQuery>\n" +
                    "   </soapenv:Body>\n" +
                    "</soapenv:Envelope>";

            HttpClientClass httpClientClass = new HttpClientClass();
            HttpEntity respEntity =  httpClientClass.httpClientCall(soapBody, "xtk:queryDef#ExecuteQuery",
                    sessionToken, securityToken);

            if (respEntity != null) {
                resp = EntityUtils.toString(respEntity);

                //prints whole response
                String loggerInfo = Formatter.prettyPrintByDom4j(resp,4, true);
                logger.log(Level.INFO,"Select paused and stopped workflows' internalNames");
                logger.log(Level.INFO,loggerInfo);

                //Convert response to SOAP Message
                InputStream is = new ByteArrayInputStream(resp.getBytes());
                SOAPMessage soapResp = MessageFactory.newInstance().createMessage(null, is);

                //Retrieve the internalNames of the workflow which state are 20 (Stopped) or 13 (Paused) and return it
                DocumentBuilder documentBuilder =
                        DocumentBuilderFactory.newInstance().newDocumentBuilder();
                Reader in = new StringReader(resp);
                Document document = documentBuilder.parse(new InputSource(in));
                in.close();
                NodeList nodes = document.getElementsByTagName("workflow");
                ArrayList<String> internalNames = new ArrayList<>();
                for(int i = 0; i < nodes.getLength(); i++) {
                    Element elem = (Element) nodes.item(i);
                    internalNames.add(elem.getAttributes().getNamedItem("internalName").getNodeValue());
                }

                return internalNames;
            } else {
                logger.log(Level.WARNING,"No Response");
            }

        } catch (Exception e) {
            logger.log(Level.WARNING,"WebService SOAP exception = {0}", e);
            throw new Exception("WebService SOAP exception = " + e);
        }
        return null;
    }

}