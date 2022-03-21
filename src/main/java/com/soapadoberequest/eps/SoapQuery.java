package com.soapadoberequest.eps;

import jakarta.xml.soap.MessageFactory;
import jakarta.xml.soap.SOAPMessage;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class SoapQuery implements ISOAPQuery{

    //This function sends a SOAP request to insert a new recipient
    @Override
    public void postSOAPInsert(String firstname, String lastname, String email, String sessionToken, String securityToken) {
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

            } else {
                System.err.println("No Response");
            }

        } catch (Exception e) {
            System.err.println("WebService SOAP exception = " + e);
        }
    }

    //This function sends a SOAP request to select rge count of table
    @Override
    public String postSOAPSelectCount(String prefix, String tableName, String sessionToken, String securityToken) {
        String resp = null;
        try {

            String soapBody = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" " +
                    "xmlns:urn=\"urn:xtk:queryDef\">\n" +
                    "   <soapenv:Header/>\n" +
                    "   <soapenv:Body>\n" +
                    "      <urn:ExecuteQuery>\n" +
                    "         <urn:sessiontoken/>\n" +
                    "         <urn:entity>\n" +
                    "            <queryDef operation=\"count\" schema=\""+prefix+":"+tableName+"\">\n" +
                    "            <select>\n" +
                    "              <node expr=\"@email\"/>\n" +
                    "              <node expr=\"@lastName\"/>\n" +
                    "              <node expr=\"@firstName\"/>\n" +
                    "            </select>\n" +
                    "          </queryDef>\n" +
                    "         </urn:entity>\n" +
                    "      </urn:ExecuteQuery>\n" +
                    "   </soapenv:Body>\n" +
                    "</soapenv:Envelope>";

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

                //Convert response to SOAP Message
                InputStream is = new ByteArrayInputStream(resp.getBytes());
                SOAPMessage soapResp = MessageFactory.newInstance().createMessage(null, is);

                //Retrieve the deliveryId based on their attribute's name
                String count =  soapResp.getSOAPBody().getElementsByTagName(tableName).item(0)
                        .getAttributes().getNamedItem("count").getNodeValue();

                return count;

            } else {
                System.err.println("No Response");
            }

        } catch (Exception e) {
            System.err.println("WebService SOAP exception = " + e);
        }
        return null;
    }

    //This function sends a SOAP request to select last entry's id
    @Override
    public String postSOAPSelectLast(String prefix, String tableName, String sessionToken, String securityToken) {
        String resp = null;
        try {
            String soapBody = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:urn=\"urn:xtk:queryDef\">\n" +
                    "   <soapenv:Header/>\n" +
                    "   <soapenv:Body>\n" +
                    "      <urn:ExecuteQuery>\n" +
                    "         <urn:sessiontoken/>\n" +
                    "         <urn:entity>\n" +
                    "            <queryDef operation=\"select\" schema=\"nms:recipient\" lineCount=\"1\">\n" +
                    "               <select>\n" +
                    "                   <node expr=\"@id\"/>\n" +
                    "               </select>\n" +
                    "               <orderBy>\n" +
                    "                   <node expr=\"@id\" sortDesc=\"true\"/>\n" +
                    "               </orderBy>\n" +
                    "            </queryDef>\n" +
                    "         </urn:entity>\n" +
                    "      </urn:ExecuteQuery>\n" +
                    "   </soapenv:Body>\n" +
                    "</soapenv:Envelope>";

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

                //Convert response to SOAP Message
                InputStream is = new ByteArrayInputStream(resp.getBytes());
                SOAPMessage soapResp = MessageFactory.newInstance().createMessage(null, is);

                //Retrieve the deliveryId based on their attribute's name
                String count =  soapResp.getSOAPBody().getElementsByTagName(tableName).item(0)
                        .getAttributes().getNamedItem("id").getNodeValue();

                return count;

            } else {
                System.err.println("No Response");
            }

        } catch (Exception e) {
            System.err.println("WebService SOAP exception = " + e);
        }
        return null;
    }

    //This function sends a SOAP request to select a recipient using the email
    @Override
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

            } else {
                System.err.println("No Response");
            }

        } catch (Exception e) {
            System.err.println("WebService SOAP exception = " + e);
        }
    }

    //This function sends a SOAP request to write(Insert) a new recipient
    @Override
    public void postSOAPWrite(Recipient recipient, String sessionToken, String securityToken) {
        String resp = null;
        try {
            int currentCount = Integer.parseInt(postSOAPSelectCount("nms", "recipient",sessionToken, securityToken));
            int lastId = Integer.parseInt(postSOAPSelectLast("nms", "recipient",sessionToken, securityToken));
            String soapBody = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:urn=\"urn:xtk:session\">\n" +
                    "   <soapenv:Header/>\n" +
                    "   <soapenv:Body>\n" +
                    "      <urn:Write>\n" +
                    "         <urn:sessiontoken/>\n" +
                    "         <urn:domDoc>\n" +
                    "            <recipient _operation=\"insert\" \n" +
                    "            \t\t\tlastName=\""+recipient.lastName+"\" \n" +
                    "            \t\t\tfirstName=\""+recipient.firstName+"\" \n" +
                    "            \t\t\temail=\""+recipient.email+"\"\n" +
                    "            \t\t\txtkschema=\"nms:recipient\"/>\n" +
                    "         </urn:domDoc>\n" +
                    "      </urn:Write>\n" +
                    "   </soapenv:Body>\n" +
                    "</soapenv:Envelope>";

            HttpClient httpclient = HttpClientBuilder.create().build();
            // You can get below parameters from SoapUI's Raw request if you are using that tool
            StringEntity strEntity = new StringEntity(soapBody, "text/xml", "UTF-8");
            // URL of request
            HttpPost post = new HttpPost("http://localhost:8080/nl/jsp/soaprouter.jsp");
            post.setHeader("SOAPAction", "xtk:persist#Write");
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

                int secondCount = Integer.parseInt(postSOAPSelectCount("nms", "recipient",sessionToken, securityToken));

                if (currentCount == secondCount){
                    System.out.println("No entry has been saved in the datatable");
                }else if(secondCount > currentCount){
                    int entriesNumber = secondCount - currentCount;
                    System.out.println("Last entry's ID before insertion: "+ lastId);
                    System.out.println(String.valueOf(entriesNumber)+" entries were registered in database");
                    System.out.println("Last entry's ID after insertion: "+ postSOAPSelectLast("nms", "recipient",sessionToken, securityToken));
                }

            } else {
                System.err.println("No Response");
            }

        } catch (Exception e) {
            System.err.println("WebService SOAP exception = " + e);
        }
    }
}
