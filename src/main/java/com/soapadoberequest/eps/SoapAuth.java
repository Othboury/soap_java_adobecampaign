package com.soapadoberequest.eps;

import jakarta.xml.soap.MessageFactory;
import jakarta.xml.soap.SOAPMessage;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.w3c.dom.Node;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SoapAuth implements ISOAPAuth{
    //This function sends a SOAP request to authenticate and returns the sessionToken and the securityToken
    public ArrayList<Node> postSOAPAUTH(String login, String password) {
        String resp = null;
        Node securityToken = null;
        Node sessionToken = null;
        try {
            String soapBody = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" " +
                    "xmlns:urn=\"urn:xtk:session\">\n" +
                    "   <soapenv:Header/>\n" +
                    "   <soapenv:Body>\n" +
                    "      <urn:Logon>\n" +
                    "         <urn:sessiontoken></urn:sessiontoken>\n" +
                    "         <urn:strLogin>"+login+"</urn:strLogin>\n" +
                    "         <urn:strPassword>"+password+"</urn:strPassword>\n" +
                    "         <urn:elemParameters>\n" +
                    "            <!--You may enter ANY elements at this point-->\n" +
                    "         </urn:elemParameters>\n" +
                    "      </urn:Logon>\n" +
                    "   </soapenv:Body>\n" +
                    "</soapenv:Envelope>";

            //HttpClient httpclient = new DefaultHttpClient();
            HttpClient httpclient = HttpClientBuilder.create().build();
            // You can get below parameters from SoapUI's Raw request if you are using that tool
            StringEntity strEntity = new StringEntity(soapBody, "text/xml", "UTF-8");
            // URL of request
            HttpPost post = new HttpPost("http://localhost:8080/nl/jsp/soaprouter.jsp");
            post.setHeader("SOAPAction", "xtk:session#Logon");
            post.setEntity(strEntity);

            // Execute request
            HttpResponse response = httpclient.execute(post);
            HttpEntity respEntity = response.getEntity();

            if (respEntity != null) {
                resp = EntityUtils.toString(respEntity);
                //prints whole response
                System.out.println(resp);
                InputStream is = new ByteArrayInputStream(resp.getBytes());
                SOAPMessage soapResp = MessageFactory.newInstance().createMessage(null, is);
                //Retrieve the sessionToken and securityToken based on their elements' tagNames
                sessionToken =  soapResp.getSOAPBody().getElementsByTagName("pstrSessionToken").item(0);
                securityToken =  soapResp.getSOAPBody().getElementsByTagName("pstrSecurityToken").item(0);

                //Regular expression to parse texts of <balance> elements in the response assuming they have
                // no child elements
                Matcher m = Pattern.compile("<balance>([^<]*)").matcher(resp); //groups all characters except <
                // (tag closing character)
                while (m.find()) {
                    //prints texts of all balances in a loop
                    System.out.println(m.group(1));
                }
            } else {
                System.err.println("No Response");
            }
            //Pouplate the tokens Arraylist
            ArrayList<Node> tokens = new ArrayList<>();
            tokens.add(sessionToken);
            tokens.add(securityToken);
            //return the tokens
            return tokens;
        } catch (Exception e) {
            System.err.println("WebService SOAP exception = " + e.toString());
            //return null if no token are retrieved
            return  null;
        }
    }

    //This function sends a SOAP request to insert a new recipient
    public void postSOAPInsert(String firstname, String lastname, String email, String sessionToken,
                               String securityToken) {
        String resp = null;
        try {

            String soapBody = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" " +
                    "xmlns:urn=\"urn:nms:recipient\">\n" +
                    "   <soapenv:Header/>\n" +
                    "   <soapenv:Body>\n" +
                    "      <urn:insert>\n" +
                    "         <urn:sessiontoken/>\n" +
                    "         <urn:entity>\n" +
                    "            <!--You may enter ANY elements at this point-->\n" +
                    "         </urn:entity>\n" +
                    "         <urn:firstName>"+firstname+"</urn:firstName>\n" +
                    "         <urn:lastName>"+lastname+"</urn:lastName>\n" +
                    "         <urn:email>"+email+"</urn:email>\n" +
                    "      </urn:insert>\n" +
                    "   </soapenv:Body>\n" +
                    "</soapenv:Envelope>";

            //HttpClient httpclient = new DefaultHttpClient();
            HttpClient httpclient = HttpClientBuilder.create().build();
            // You can get below parameters from SoapUI's Raw request if you are using that tool
            StringEntity strEntity = new StringEntity(soapBody, "text/xml", "UTF-8");
            // URL of request
            HttpPost post = new HttpPost("http://localhost:8080/nl/jsp/soaprouter.jsp");
            post.setHeader("SOAPAction", "nms:recipient#insert");
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

                //Regular expression to parse texts of <balance> elements in the response assuming they have
                // no child elements
                Matcher m = Pattern.compile("<balance>([^<]*)").matcher(resp); //groups all characters except
                // < (tag closing character)
                while (m.find()) {
                    //prints texts of all balances in a loop
                    System.out.println(m.group(1));
                }
            } else {
                System.err.println("No Response");
            }

        } catch (Exception e) {
            System.err.println("WebService SOAP exception = " + e.toString());
        }
    }

    //This function sends a SOAP request to select a recipient using the email
    public void postSOAPSelect(String email, String sessionToken, String securityToken) {
        String resp = null;
        try {

            String soapBody = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" " +
                    "xmlns:urn=\"urn:xtk:queryDef\">\n" +
                    "   <soapenv:Header/>\n" +
                    "   <soapenv:Body>\n" +
                    "      <urn:ExecuteQuery>\n" +
                    "         <urn:sessiontoken/>\n" +
                    "         <urn:entity>\n" +
                    "            <queryDef operation=\"select\" schema=\"nms:recipient\">\n" +
                    "            <select>\n" +
                    "              <node expr=\"@email\"/>\n" +
                    "              <node expr=\"@lastName\"/>\n" +
                    "              <node expr=\"@firstName\"/>\n" +
                    "            </select>\n" +
                    "            <where>\n" +
                    "              <condition expr=\"@email = '"+email+"'\"/>\n" +
                    "            </where>\n" +
                    "          </queryDef>\n" +
                    "         </urn:entity>\n" +
                    "      </urn:ExecuteQuery>\n" +
                    "   </soapenv:Body>\n" +
                    "</soapenv:Envelope>";

            //HttpClient httpclient = new DefaultHttpClient();
            HttpClient httpclient = HttpClientBuilder.create().build();
            // You can get below parameters from SoapUI's Raw request if you are using that tool
            StringEntity strEntity = new StringEntity(soapBody, "text/xml", "UTF-8");
            // URL of request
            HttpPost post = new HttpPost("http://localhost:8080/nl/jsp/soaprouter.jsp");
            post.setHeader("SOAPAction", "xtk:queryDef#ExecuteQuery");
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

                //Regular expression to parse texts of <balance> elements in the response assuming they have
                // no child elements
                Matcher m = Pattern.compile("<balance>([^<]*)").matcher(resp);//groups all characters except
                // < (tag closing character)
                while (m.find()) {
                    //prints texts of all balances in a loop
                    System.out.println(m.group(1));
                }
            } else {
                System.err.println("No Response");
            }

        } catch (Exception e) {
            System.err.println("WebService SOAP exception = " + e.toString());
        }
    }

    //This function sends a SOAP request to start a workflow
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

            //HttpClient httpclient = new DefaultHttpClient();
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

                //Regular expression to parse texts of <balance> elements in the response assuming they have
                // no child elements
                Matcher m = Pattern.compile("<balance>([^<]*)").matcher(resp); //groups all characters except
                // < (tag closing character)
                while (m.find()) {
                    //prints texts of all balances in a loop
                    System.out.println(m.group(1));
                }
            } else {
                System.err.println("No Response");
            }

        } catch (Exception e) {
            System.err.println("WebService SOAP exception = " + e.toString());
        }
    }

    //This function sends a SOAP request to start a workflow
    public void postSOAPSubscribe(Recipient recipient, String serviceName, String sessionToken, String securityToken) {
        String resp = null;

        try {
            String soapBody = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" " +
                    "xmlns:urn=\"urn:nms:subscription\">\n" +
                    "   <soapenv:Header/>\n" +
                    "   <soapenv:Body>\n" +
                    "      <urn:Subscribe>\n" +
                    "         <urn:sessiontoken/>\n" +
                    "         <urn:strServiceName>"+serviceName+"</urn:strServiceName>\n" +
                    "         <urn:elemRecipient>\n" +
                    "            <recipient email=\""+recipient.email+"\" lastName=\""+recipient.lastName+"\" " +
                    "firstName=\""+recipient.firstName+"\" _key=\"@email\"/>\n" +
                    "         </urn:elemRecipient>\n" +
                    "         <urn:bCreate>true</urn:bCreate>\n" +
                    "      </urn:Subscribe>\n" +
                    "   </soapenv:Body>\n" +
                    "</soapenv:Envelope>";

            //HttpClient httpclient = new DefaultHttpClient();
            HttpClient httpclient = HttpClientBuilder.create().build();
            // You can get below parameters from SoapUI's Raw request if you are using that tool
            StringEntity strEntity = new StringEntity(soapBody, "text/xml", "UTF-8");
            // URL of request
            HttpPost post = new HttpPost("http://localhost:8080/nl/jsp/soaprouter.jsp");
            post.setHeader("SOAPAction", "nms:subscription#Subscribe");
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

                //Regular expression to parse texts of <balance> elements in the response assuming they have no child elements
                Matcher m = Pattern.compile("<balance>([^<]*)").matcher(resp); //groups all characters except < (tag closing character)
                while (m.find()) {
                    //prints texts of all balances in a loop
                    System.out.println(m.group(1));
                }
            } else {
                System.err.println("No Response");
            }

        } catch (Exception e) {
            System.err.println("WebService SOAP exception = " + e.toString());
        }
    }

    public static void main(String[] args) {
        System.out.println("START SOAP REQUESTS...");
        SoapAuth soapWebServiceClientObject = new SoapAuth();
        System.out.println("********AUTHENTICATE TO A SESSION********");
        ArrayList<Node> tokens =  soapWebServiceClientObject.postSOAPAUTH("admin","neo");
        Recipient recipient = new Recipient("Othmane","Boury","othboury@gmail.com");
        System.out.println("********INSERT NEW RECIPIENT INTO DB********");
        soapWebServiceClientObject.postSOAPInsert("Messi", "loko", "treiue@xyz.com",
                tokens.get(0).getTextContent(), tokens.get(1).getTextContent());
        System.out.println("********SELECT RECIPIENT FROM DB********");
        soapWebServiceClientObject.postSOAPSelect("othboury@gmail.com",tokens.get(0).getTextContent(),
                tokens.get(1).getTextContent());
        System.out.println("********START AN EXISTING WORKFLOW********");
        soapWebServiceClientObject.postSOAPStartWorkflow("WKF31",tokens.get(0).getTextContent(),
                tokens.get(1).getTextContent());
        System.out.println("********SUBSCRIBE EXISTING RECIPIENT TO AN EXISTING SERVICE********");
        soapWebServiceClientObject.postSOAPSubscribe(recipient, "SVC1",tokens.get(0).getTextContent(),
                tokens.get(1).getTextContent());
        System.out.println("END SOAP REQUESTS...");
    }
}
