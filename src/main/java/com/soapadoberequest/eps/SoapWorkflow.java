package com.soapadoberequest.eps;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class SoapWorkflow implements ISOAPWorkflow{

    //This function sends a SOAP request to start a workflow
    @Override
    public void postSOAPStartWorkflow(String workflowId, String sessionToken, String securityToken) {
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

            HttpClient httpclient = HttpClientBuilder.create().build();
            // You can get below parameters from SoapUI's Raw request if you are using that tool
            StringEntity strEntity = new StringEntity(soapBody, "text/xml", "UTF-8");
            // URL of request
            HttpPost post = new HttpPost("http://localhost:8080/nl/jsp/soaprouter.jsp");
            post.setHeader("SOAPAction", "xtk:workflow#Start");
            post.setHeader("cookie","__sessiontoken="+sessionToken);
            post.setHeader("X-Security-Token", securityToken);
            post.setEntity(strEntity);

            // Execute request
            HttpResponse response = httpclient.execute(post);
            HttpEntity respEntity = response.getEntity();

            if (respEntity != null) {
                resp = EntityUtils.toString(respEntity);

                //prints whole response
                System.out.println(resp);

            } else {
                System.err.println("No Response");
            }

        } catch (Exception e) {
            System.err.println("WebService SOAP exception = " + e);
        }
    }

    //This function sends a SOAP request to send a signal in order to trigger a workflow
    @Override
    public void postSOAPPostEvent(String workFlowId, String activity, ArrayList<String> vars, ArrayList<String> param, String sessionToken, String securityToken) {
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

            HttpClient httpclient = HttpClientBuilder.create().build();
            // You can get below parameters from SoapUI's Raw request if you are using that tool
            StringEntity strEntity = new StringEntity(soapBody, "text/xml", "UTF-8");
            // URL of request
            HttpPost post = new HttpPost("http://localhost:8080/nl/jsp/soaprouter.jsp");
            post.setHeader("SOAPAction", "xtk:workflow#PostEvent");
            post.setHeader("cookie","__sessiontoken="+sessionToken);
            post.setHeader("X-Security-Token", securityToken);
            post.setEntity(strEntity);

            // Execute request
            HttpResponse response = httpclient.execute(post);
            HttpEntity respEntity = response.getEntity();

            if (respEntity != null) {
                resp = EntityUtils.toString(respEntity);

                //prints whole response
                System.out.println(resp);

            } else {
                System.err.println("No Response");
            }

        } catch (Exception e) {
            System.err.println("WebService SOAP exception = " + e);
        }
    }

    //This function sends a SOAP request to kill a workflow
    @Override
    public void postSOAPKillWorkflow(String workFlowId, String sessionToken, String securityToken) {
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

            HttpClient httpclient = HttpClientBuilder.create().build();
            // You can get below parameters from SoapUI's Raw request if you are using that tool
            StringEntity strEntity = new StringEntity(soapBody, "text/xml", "UTF-8");
            // URL of request
            HttpPost post = new HttpPost("http://localhost:8080/nl/jsp/soaprouter.jsp");
            post.setHeader("SOAPAction", "xtk:workflow#Kill");
            post.setHeader("cookie","__sessiontoken="+sessionToken);
            post.setHeader("X-Security-Token", securityToken);
            post.setEntity(strEntity);

            // Execute request
            HttpResponse response = httpclient.execute(post);
            HttpEntity respEntity = response.getEntity();

            if (respEntity != null) {
                resp = EntityUtils.toString(respEntity);

                //prints whole response
                System.out.println(resp);

            } else {
                System.err.println("No Response");
            }

        } catch (Exception e) {
            System.err.println("WebService SOAP exception = " + e);
        }
    }

    //This function sends a SOAP request to pause a workflow
    @Override
    public void postSOAPPauseWorkflow(String workFlowId, String sessionToken, String securityToken) {
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

            HttpClient httpclient = HttpClientBuilder.create().build();
            // You can get below parameters from SoapUI's Raw request if you are using that tool
            StringEntity strEntity = new StringEntity(soapBody, "text/xml", "UTF-8");
            // URL of request
            HttpPost post = new HttpPost("http://localhost:8080/nl/jsp/soaprouter.jsp");
            post.setHeader("SOAPAction", "xtk:workflow#Pause");
            post.setHeader("cookie","__sessiontoken="+sessionToken);
            post.setHeader("X-Security-Token", securityToken);
            post.setEntity(strEntity);

            // Execute request
            HttpResponse response = httpclient.execute(post);
            HttpEntity respEntity = response.getEntity();

            if (respEntity != null) {
                resp = EntityUtils.toString(respEntity);

                //prints whole response
                System.out.println(resp);

            } else {
                System.err.println("No Response");
            }

        } catch (Exception e) {
            System.err.println("WebService SOAP exception = " + e);
        }
    }

    //This function sends a SOAP request to wake up a workflow
    @Override
    public void postSOAPWakeUpWorkflow(String workFlowId, String sessionToken, String securityToken) {
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

            HttpClient httpclient = HttpClientBuilder.create().build();
            // You can get below parameters from SoapUI's Raw request if you are using that tool
            StringEntity strEntity = new StringEntity(soapBody, "text/xml", "UTF-8");
            // URL of request
            HttpPost post = new HttpPost("http://localhost:8080/nl/jsp/soaprouter.jsp");
            post.setHeader("SOAPAction", "xtk:workflow#Wakeup");
            post.setHeader("cookie","__sessiontoken="+sessionToken);
            post.setHeader("X-Security-Token", securityToken);
            post.setEntity(strEntity);

            // Execute request
            HttpResponse response = httpclient.execute(post);
            HttpEntity respEntity = response.getEntity();

            if (respEntity != null) {
                resp = EntityUtils.toString(respEntity);

                //prints whole response
                System.out.println(resp);

            } else {
                System.err.println("No Response");
            }

        } catch (Exception e) {
            System.err.println("WebService SOAP exception = " + e);
        }
    }

    //This function sends a SOAP request to start a workflow with parameters
    @Override
    public void postSOAPStartWithParams(String workFlowId, ArrayList<String> vars,
                                        ArrayList<String> param, String sessionToken, String securityToken) {
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

            HttpClient httpclient = HttpClientBuilder.create().build();
            // You can get below parameters from SoapUI's Raw request if you are using that tool
            StringEntity strEntity = new StringEntity(soapBody, "text/xml", "UTF-8");
            // URL of request
            HttpPost post = new HttpPost("http://localhost:8080/nl/jsp/soaprouter.jsp");
            post.setHeader("SOAPAction", "xtk:workflow#StartWithParameters");
            post.setHeader("cookie","__sessiontoken="+sessionToken);
            post.setHeader("X-Security-Token", securityToken);
            post.setEntity(strEntity);

            // Execute request
            HttpResponse response = httpclient.execute(post);
            HttpEntity respEntity = response.getEntity();

            if (respEntity != null) {
                resp = EntityUtils.toString(respEntity);

                //prints whole response
                System.out.println(resp);

            } else {
                System.err.println("No Response");
            }

        } catch (Exception e) {
            System.err.println("WebService SOAP exception = " + e);
        }
    }

}
