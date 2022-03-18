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

public class SoapDelivery implements ISOAPDelivery{

    //This function sends a SOAP request to create delivery from model
    @Override
    public void postSOAPCreateWithTemplate(String ScenarioName, ArrayList<String> vars,
                                        ArrayList<String> param, String sessionToken, String securityToken) {
        String resp = null;
        ArrayList<String> varBuilder = new ArrayList<>();
        for (int i = 0; i <param.size(); i++){
            varBuilder.add(vars.get(i)+'='+'"'+param.get(i)+'"');
        }
        String varSentence = varBuilder.stream().collect(Collectors.joining(" "));
        try {

            String soapBody = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:urn=\"urn:nms:delivery\">\n" +
                    "   <soapenv:Header/>\n" +
                    "   <soapenv:Body>\n" +
                    "      <urn:CreateFromModel>\n" +
                    "         <urn:sessiontoken/>\n" +
                    "         <urn:strScenarioName>"+ScenarioName+"</urn:strScenarioName>\n" +
                    "         <urn:elemContent>\n" +
                    "            <!--You may enter ANY elements at this point-->\n" +
                    "         </urn:elemContent>\n" +
                    "      </urn:CreateFromModel>\n" +
                    "   </soapenv:Body>\n" +
                    "</soapenv:Envelope>";

            HttpClient httpclient = HttpClientBuilder.create().build();
            // You can get below parameters from SoapUI's Raw request if you are using that tool
            StringEntity strEntity = new StringEntity(soapBody, "text/xml", "UTF-8");
            // URL of request
            HttpPost post = new HttpPost("http://localhost:8080/nl/jsp/soaprouter.jsp");
            post.setHeader("SOAPAction", "nms:delivery#CreateFromModel");
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

    //This function sends a SOAP request to create delivery from model
    @Override
    public void postSOAPPrepareAndStart( ArrayList<String> vars, ArrayList<String> param, String sessionToken,
                                         String securityToken) {
        String resp = null;
        ArrayList<String> varBuilder = new ArrayList<>();
        for (int i = 0; i <param.size(); i++){
            varBuilder.add(vars.get(i)+'='+'"'+param.get(i)+'"');
        }
        String varSentence = varBuilder.stream().collect(Collectors.joining(" "));
        try {

            String soapBody = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:urn=\"urn:nms:delivery\">\n" +
                    "   <soapenv:Header/>\n" +
                    "   <soapenv:Body>\n" +
                    "      <urn:PrepareAndStart>\n" +
                    "         <urn:sessiontoken/>\n" +
                    "         <urn:entity>\n" +
                    "            <!--You may enter ANY elements at this point-->\n" +
                    "         </urn:entity>\n" +
                    "      </urn:PrepareAndStart>\n" +
                    "   </soapenv:Body>\n" +
                    "</soapenv:Envelope>";

            HttpClient httpclient = HttpClientBuilder.create().build();
            // You can get below parameters from SoapUI's Raw request if you are using that tool
            StringEntity strEntity = new StringEntity(soapBody, "text/xml", "UTF-8");
            // URL of request
            HttpPost post = new HttpPost("http://localhost:8080/nl/jsp/soaprouter.jsp");
            post.setHeader("SOAPAction", "nms:delivery#PrepareAndStart");
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

    //This function sends a SOAP request to prepare delivery's target
    @Override
    public void postSOAPPrepareTarget( ArrayList<String> vars, ArrayList<String> param, String sessionToken,
                                         String securityToken) {
        String resp = null;
        ArrayList<String> varBuilder = new ArrayList<>();
        for (int i = 0; i <param.size(); i++){
            varBuilder.add(vars.get(i)+'='+'"'+param.get(i)+'"');
        }
        String varSentence = varBuilder.stream().collect(Collectors.joining(" "));
        try {

            String soapBody = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:urn=\"urn:nms:delivery\">\n" +
                    "   <soapenv:Header/>\n" +
                    "   <soapenv:Body>\n" +
                    "      <urn:PrepareTarget>\n" +
                    "         <urn:sessiontoken/>\n" +
                    "         <urn:entity>\n" +
                    "            <!--You may enter ANY elements at this point-->\n" +
                    "         </urn:entity>\n" +
                    "      </urn:PrepareTarget>\n" +
                    "   </soapenv:Body>\n" +
                    "</soapenv:Envelope>";

            HttpClient httpclient = HttpClientBuilder.create().build();
            // You can get below parameters from SoapUI's Raw request if you are using that tool
            StringEntity strEntity = new StringEntity(soapBody, "text/xml", "UTF-8");
            // URL of request
            HttpPost post = new HttpPost("http://localhost:8080/nl/jsp/soaprouter.jsp");
            post.setHeader("SOAPAction", "nms:delivery#PrepareTarget");
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

    //This function sends a SOAP request to prepare delivery's message
    @Override
    public void postSOAPPrepareMessage( ArrayList<String> vars, ArrayList<String> param, String sessionToken,
                                       String securityToken) {
        String resp = null;
        ArrayList<String> varBuilder = new ArrayList<>();
        for (int i = 0; i <param.size(); i++){
            varBuilder.add(vars.get(i)+'='+'"'+param.get(i)+'"');
        }
        String varSentence = varBuilder.stream().collect(Collectors.joining(" "));
        try {

            String soapBody = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:urn=\"urn:nms:delivery\">\n" +
                    "   <soapenv:Header/>\n" +
                    "   <soapenv:Body>\n" +
                    "      <urn:PrepareMessage>\n" +
                    "         <urn:sessiontoken/>\n" +
                    "         <urn:entity>\n" +
                    "            <!--You may enter ANY elements at this point-->\n" +
                    "         </urn:entity>\n" +
                    "      </urn:PrepareMessage>\n" +
                    "   </soapenv:Body>\n" +
                    "</soapenv:Envelope>";

            HttpClient httpclient = HttpClientBuilder.create().build();
            // You can get below parameters from SoapUI's Raw request if you are using that tool
            StringEntity strEntity = new StringEntity(soapBody, "text/xml", "UTF-8");
            // URL of request
            HttpPost post = new HttpPost("http://localhost:8080/nl/jsp/soaprouter.jsp");
            post.setHeader("SOAPAction", "nms:delivery#PrepareMessage");
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

    //This function sends a SOAP request to submit a delivery
    @Override
    public void postSOAPSubmitDelivery( String ScenarioName, ArrayList<String> vars, ArrayList<String> param, String sessionToken,
                                        String securityToken) {
        String resp = null;
        ArrayList<String> varBuilder = new ArrayList<>();
        for (int i = 0; i <param.size(); i++){
            varBuilder.add(vars.get(i)+'='+'"'+param.get(i)+'"');
        }
        String varSentence = varBuilder.stream().collect(Collectors.joining(" "));
        try {

            String soapBody = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:urn=\"urn:nms:delivery\">\n" +
                    "   <soapenv:Header/>\n" +
                    "   <soapenv:Body>\n" +
                    "      <urn:SubmitDelivery>\n" +
                    "         <urn:sessiontoken/>\n" +
                    "         <urn:strScenarioName>"+ScenarioName+"</urn:strScenarioName>\n" +
                    "         <urn:elemContent>\n" +
                    "            <!--You may enter ANY elements at this point-->\n" +
                    "         </urn:elemContent>\n" +
                    "      </urn:SubmitDelivery>\n" +
                    "   </soapenv:Body>\n" +
                    "</soapenv:Envelope>";

            HttpClient httpclient = HttpClientBuilder.create().build();
            // You can get below parameters from SoapUI's Raw request if you are using that tool
            StringEntity strEntity = new StringEntity(soapBody, "text/xml", "UTF-8");
            // URL of request
            HttpPost post = new HttpPost("http://localhost:8080/nl/jsp/soaprouter.jsp");
            post.setHeader("SOAPAction", "nms:delivery#SubmitDelivery");
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

    //This function sends a SOAP request to stop a delivery
    @Override
    public void postSOAPStopDelivery(String deliveryId, String sessionToken, String securityToken) {
        String resp = null;
        try {

            String soapBody = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:urn=\"urn:nms:delivery\">\n" +
                    "   <soapenv:Header/>\n" +
                    "   <soapenv:Body>\n" +
                    "      <urn:Stop>\n" +
                    "         <urn:sessiontoken/>\n" +
                    "         <urn:strDeliveryId>"+deliveryId+"</urn:strDeliveryId>\n" +
                    "      </urn:Stop>\n" +
                    "   </soapenv:Body>\n" +
                    "</soapenv:Envelope>";

            HttpClient httpclient = HttpClientBuilder.create().build();
            // You can get below parameters from SoapUI's Raw request if you are using that tool
            StringEntity strEntity = new StringEntity(soapBody, "text/xml", "UTF-8");
            // URL of request
            HttpPost post = new HttpPost("http://localhost:8080/nl/jsp/soaprouter.jsp");
            post.setHeader("SOAPAction", "nms:delivery#Stop");
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
