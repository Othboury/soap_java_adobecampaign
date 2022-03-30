package com.soapadoberequest.eps;

import io.github.cdimascio.dotenv.Dotenv;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * This class contains the SOAP requests for different Delivery functions, it implements its methods from the interface
 * ISOAPWorkflow
 */
public class SoapWorkflow implements ISOAPWorkflow{

    Logger logger = Logger.getLogger("logger");

    /**
     *This function sends a SOAP request to start a workflow
     *
     * @param workflowId
     * @param sessionToken
     * @param securityToken
     * @throws Exception
     *
     */
    @Override
    public void postSOAPStartWorkflow(String workflowId, String sessionToken, String securityToken) throws Exception {
        String resp = null;
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
                    sessionToken, securityToken );

            if (respEntity != null) {
                resp = EntityUtils.toString(respEntity);

                //prints whole response
                logger.log(Level.INFO,resp);

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
     * @param workFlowId
     * @param activity
     * @param vars
     * @param param
     * @param sessionToken
     * @param securityToken
     * @throws Exception
     */
    @Override
    public void postSOAPPostEvent(String workFlowId, String activity, ArrayList<String> vars, ArrayList<String> param,
                                  String sessionToken, String securityToken) throws Exception {
        String resp = null;
        ArrayList<String> varBuilder = new ArrayList<>();
        for (int i = 0; i <param.size(); i++){
            varBuilder.add(vars.get(i)+'='+'"'+param.get(i)+'"');
        }
        String varSentence = varBuilder.stream().collect(Collectors.joining(" "));
        try {

            String soapBody = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:urn=\"urn:xtk:workflow\">\n" +
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
                    sessionToken, securityToken );

            if (respEntity != null) {
                resp = EntityUtils.toString(respEntity);

                //prints whole response
                logger.log(Level.INFO,resp);

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
     * @param workFlowId
     * @param sessionToken
     * @param securityToken
     * @throws Exception
     */
    @Override
    public void postSOAPKillWorkflow(String workFlowId, String sessionToken, String securityToken) throws Exception {
        String resp = null;
        try {

            String soapBody = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:urn=\"urn:xtk:workflow\">\n" +
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
                    sessionToken, securityToken );

            if (respEntity != null) {
                resp = EntityUtils.toString(respEntity);

                //prints whole response
                logger.log(Level.INFO,resp);

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
     * @param workFlowId
     * @param sessionToken
     * @param securityToken
     * @throws Exception
     */
    @Override
    public void postSOAPPauseWorkflow(String workFlowId, String sessionToken, String securityToken) throws Exception {
        String resp = null;
        try {

            String soapBody = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:urn=\"urn:xtk:workflow\">\n" +
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
                    sessionToken, securityToken );

            if (respEntity != null) {
                resp = EntityUtils.toString(respEntity);

                //prints whole response
                logger.log(Level.ALL,resp);

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
     * @param workFlowId
     * @param sessionToken
     * @param securityToken
     * @throws Exception
     */
    @Override
    public void postSOAPWakeUpWorkflow(String workFlowId, String sessionToken, String securityToken) throws Exception {
        String resp = null;
        try {

            String soapBody = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:urn=\"urn:xtk:workflow\">\n" +
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
                    sessionToken, securityToken );
            if (respEntity != null) {
                resp = EntityUtils.toString(respEntity);

                //prints whole response
                logger.log(Level.INFO,resp);

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
     * @param workFlowId
     * @param vars
     * @param param
     * @param sessionToken
     * @param securityToken
     * @throws Exception
     */
    @Override
    public void postSOAPStartWithParams(String workFlowId, ArrayList<String> vars,
                                        ArrayList<String> param, String sessionToken, String securityToken) throws Exception{
        String resp = null;
        ArrayList<String> varBuilder = new ArrayList<>();
        for (int i = 0; i <param.size(); i++){
            varBuilder.add(vars.get(i)+'='+'"'+param.get(i)+'"');
        }
        String varSentence = varBuilder.stream().collect(Collectors.joining(" "));
        try {

            String soapBody = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:urn=\"urn:xtk:workflow\">\n" +
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
                    sessionToken, securityToken );

            if (respEntity != null) {
                resp = EntityUtils.toString(respEntity);

                //prints whole response
                logger.log(Level.INFO,resp);

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
     * @param workflowId
     * @param sessionToken
     * @param securityToken
     * @throws Exception
     */
    @Override
    public void postSOAPWorkflowLogs(String workflowId, String sessionToken, String securityToken) throws Exception {
        String resp = null;
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
                    sessionToken, securityToken );

            if (respEntity != null) {
                resp = EntityUtils.toString(respEntity);

                //prints whole response
                logger.log(Level.INFO,resp);

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
     * @param workflowInternalName
     * @param sessionToken
     * @param securityToken
     * @throws Exception
     */
    @Override
    public void postSOAPWorkflowState(String workflowInternalName, String sessionToken, String securityToken) throws Exception{
        String resp = null;
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
                    "              <condition expr=\"@internalName = "+workflowInternalName+"\"/>\n" +
                    "            </where>\n" +
                    "          </queryDef>\n" +
                    "         </urn:entity>\n" +
                    "      </urn:ExecuteQuery>\n" +
                    "   </soapenv:Body>\n" +
                    "</soapenv:Envelope>";

            HttpClientClass httpClientClass = new HttpClientClass();
            HttpEntity respEntity =  httpClientClass.httpClientCall(soapBody, "xtk:queryDef#ExecuteQuery",
                    sessionToken, securityToken );

            if (respEntity != null) {
                resp = EntityUtils.toString(respEntity);

                //prints whole response
                logger.log(Level.INFO,resp);

            } else {
                logger.log(Level.WARNING,"No Response");
            }

        } catch (Exception e) {
            throw new Exception("WebService SOAP exception = " + e);
        }
    }

}
