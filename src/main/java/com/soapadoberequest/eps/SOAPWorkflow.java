package com.soapadoberequest.eps;

import jakarta.xml.soap.MessageFactory;
import jakarta.xml.soap.SOAPMessage;
import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;

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
     *
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

                //prints whole response
                String loggerInfo = Formatter.prettyPrintByDom4j(resp,4, true);
                logger.log(Level.INFO,"Start Workflow SOAP request XML response:");
                logger.log(Level.INFO,loggerInfo);

            } else {
                logger.log(Level.WARNING,"No Response");
            }

        } catch (Exception e) {
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

                //prints whole response
                String loggerInfo = Formatter.prettyPrintByDom4j(resp,4, true);
                logger.log(Level.INFO,"Post event workflow SOAP request XML response:");
                logger.log(Level.INFO,loggerInfo);

            } else {
                logger.log(Level.WARNING,"No Response");
            }

        } catch (Exception e) {
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

                //prints whole response
                String loggerInfo = Formatter.prettyPrintByDom4j(resp,4, true);
                logger.log(Level.INFO,"Kill workflow SOAP request XML response:");
                logger.log(Level.INFO,loggerInfo);

            } else {
                logger.log(Level.WARNING,"No Response");
            }

        } catch (Exception e) {
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

                //prints whole response
                String loggerInfo = Formatter.prettyPrintByDom4j(resp,4, true);
                logger.log(Level.INFO,"Pause workflow SOAP request XML response:");
                logger.log(Level.INFO,loggerInfo);

            } else {
                logger.log(Level.WARNING,"No Response");
            }
        } catch (Exception e) {
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

                //prints whole response
                String loggerInfo = Formatter.prettyPrintByDom4j(resp,4, true);
                logger.log(Level.INFO,"Wake up workflow SOAP request XML response:");
                logger.log(Level.INFO,loggerInfo);

            } else {
                logger.log(Level.WARNING,"No Response");
            }

        } catch (Exception e) {
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

                //prints whole response
                String loggerInfo = Formatter.prettyPrintByDom4j(resp,4, true);
                logger.log(Level.INFO,"Start workflow with params SOAP request XML response:");
                logger.log(Level.INFO,loggerInfo);

            } else {
                logger.log(Level.WARNING,"No Response");
            }

        } catch (Exception e) {
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

                //Write log response in xml file
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

            } else {
                logger.log(Level.WARNING,"No Response");
            }

        } catch (Exception e) {

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

                //prints whole response
                String loggerInfo = Formatter.prettyPrintByDom4j(resp,4, true);
                logger.log(Level.INFO,"Select workflow state SOAP request XML response");
                logger.log(Level.INFO,loggerInfo);

            } else {
                logger.log(Level.WARNING,"No Response");
            }

        } catch (Exception e) {
            throw new Exception("WebService SOAP exception = " + e);
        }
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
            throw new Exception("WebService SOAP exception = " + e);
        }
    }

}
