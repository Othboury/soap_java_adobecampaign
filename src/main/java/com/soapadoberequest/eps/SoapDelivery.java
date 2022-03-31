package com.soapadoberequest.eps;

import jakarta.xml.soap.MessageFactory;
import jakarta.xml.soap.SOAPMessage;
import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * This class contains the SOAP requests for different Delivery functions, it implements its methods from the interface
 * ISOAPDelivery
 */

public class SoapDelivery implements ISOAPDelivery{
    Logger logger = Logger.getLogger("logger");

    /**
     * This function sends a SOAP request to create delivery from model
     *
     * @param scenarioName
     * @param vars
     * @param param
     * @param source
     * @param sessionToken
     * @param securityToken
     * @throws Exception
     */
    @Override
    public void postSOAPCreateWithTemplate(String scenarioName, List<String> vars, List<String> param,
                                           String source, String sessionToken, String securityToken) throws Exception{
        String resp = null;
        ArrayList<String> varBuilder = new ArrayList<>();
        for (int i = 0; i <param.size(); i++){
            varBuilder.add(vars.get(i)+'='+'"'+param.get(i)+'"');
        }
        String varSentence = varBuilder.stream().collect(Collectors.joining(" "));
        try {

            String soapBody = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" " +
                    "xmlns:urn=\"urn:nms:delivery\">\n" +
                    "   <soapenv:Header/>\n" +
                    "   <soapenv:Body>\n" +
                    "      <urn:CreateFromModel>\n" +
                    "         <urn:sessiontoken/>\n" +
                    "         <urn:strScenarioName>"+scenarioName+"</urn:strScenarioName>\n" +
                    "         <urn:elemContent>\n" +
                    "            <delivery>\n" +
                    "               <targets>\n" +
                    "                   <deliveryTarget>\n" +
                    "                       <targetPart exclusion='false' ignoreDeleteStatus='false'>\n" +
                    "                           <where>\n" +
                    "                               <condition expr='@"+varSentence+"'/>\n" +
                    "                           </where>\n" +
                    "                       </targetPart>\n" +
                    "                    </deliveryTarget>\n" +
                    "                </targets>\n" +
                    "                <content>\n" +
                    "                   <html>\n" +
                    "                       <source>"+source+"</source>\n" +
                    "                    </html>\n" +
                    "                 </content>\n" +
                    "             </delivery>\n" +
                    "         </urn:elemContent>\n" +
                    "      </urn:CreateFromModel>\n" +
                    "   </soapenv:Body>\n" +
                    "</soapenv:Envelope>";

            HttpClientClass httpClientClass = new HttpClientClass();
            HttpEntity respEntity =  httpClientClass.httpClientCall(soapBody, "nms:delivery#CreateFromModel",
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
     * This function sends a SOAP request to select a delivery using the internal name
     *
     * @param internalName
     * @param sessionToken
     * @param securityToken
     * @return the delivery's Id
     * @throws Exception
     */
    @Override
    public String postSOAPSelectDelivery(String internalName, String sessionToken, String securityToken)
            throws Exception {
        String resp = null;
        try {

            String soapBody = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" " +
                    "xmlns:urn=\"urn:xtk:queryDef\">\n" +
                    "   <soapenv:Header/>\n" +
                    "   <soapenv:Body>\n" +
                    "      <urn:ExecuteQuery>\n" +
                    "         <urn:sessiontoken/>\n" +
                    "         <urn:entity>\n" +
                    "            <queryDef operation=\"select\" schema=\"nms:delivery\">\n" +
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
                    sessionToken, securityToken );

            if (respEntity != null) {
                resp = EntityUtils.toString(respEntity);

                //prints whole response
                logger.log(Level.INFO,resp);
                //Convert response to SOAP Message
                InputStream is = new ByteArrayInputStream(resp.getBytes());
                SOAPMessage soapResp = MessageFactory.newInstance().createMessage(null, is);

                //Retrieve the deliveryId based on their attribute's name and return it
                return soapResp.getSOAPBody().getElementsByTagName("delivery").item(0)
                        .getAttributes().getNamedItem("id").getNodeValue();
            } else {
                logger.log(Level.WARNING,"No Response");
            }

        } catch (Exception e) {
            throw new Exception("WebService SOAP exception = " + e);
        }
        return null;
    }

    /**
     * This function sends a SOAP request to prerape a delivery and start it
     *
     * @param internalName
     * @param sessionToken
     * @param securityToken
     * @throws Exception
     */
    @Override
    public void postSOAPPrepareAndStart( String internalName, String sessionToken, String securityToken)
            throws Exception{
        String resp = null;
        try {
            String soapBody = """
                    <soapenv:Envelope xmlns:soapenv=http://schemas.xmlsoap.org/soap/envelope/"
                    xmlns:urn=urn:nms:delivery>
                       <soapenv:Header/>
                       <soapenv:Body>
                          <urn:PrepareAndStart>
                             <urn:sessiontoken/>
                             <urn:entity>
                                <-- ADD DELIVERY -->
                             </urn:entity>
                          </urn:PrepareAndStart>
                       </soapenv:Body>
                    </soapenv:Envelope>""";

            HttpClientClass httpClientClass = new HttpClientClass();
            HttpEntity respEntity =  httpClientClass.httpClientCall(soapBody, "nms:delivery#PrepareAndStart",
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
     * This function sends a SOAP request to prepare delivery's target
     *
     * @param internalName
     * @param sessionToken
     * @param securityToken
     * @throws Exception
     */
    @Override
    public void postSOAPPrepareTarget( String internalName, String sessionToken, String securityToken)
            throws Exception {
        String resp = null;

        try {
            String soapBody = """
                    <soapenv:Envelope xmlns:soapenv= http://schemas.xmlsoap.org/soap/envelope/ 
                    xmlns:urn=urn:nms:delivery>
                       <soapenv:Header/>
                       <soapenv:Body>
                          <urn:PrepareTarget>
                             <urn:sessiontoken/>
                             <urn:entity>
                                <-- ADD DELIVERY -->
                             </urn:entity>
                          </urn:PrepareTarget>
                       </soapenv:Body>
                    </soapenv:Envelope>""";

            HttpClientClass httpClientClass = new HttpClientClass();
            HttpEntity respEntity =  httpClientClass.httpClientCall(soapBody, "nms:delivery#PrepareAndTarget",
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
     * This function sends a SOAP request to prepare delivery's message
     *
     * @param internalName
     * @param sessionToken
     * @param securityToken
     * @throws Exception
     */
    @Override
    public void postSOAPPrepareMessage( String internalName, String sessionToken, String securityToken)
            throws Exception {
        String resp = null;

        try {
            String soapBody = """
                    <soapenv:Envelope xmlns:soapenv=http://schemas.xmlsoap.org/soap/envelope/
                    xmlns:urn=urn:nms:delivery>
                       <soapenv:Header/>
                       <soapenv:Body>
                          <urn:PrepareMessage>
                             <urn:sessiontoken/>
                             <urn:entity>
                                <-- ADD DELIVERY -->
                             </urn:entity>
                          </urn:PrepareMessage>
                       </soapenv:Body>
                    </soapenv:Envelope>""";

            HttpClientClass httpClientClass = new HttpClientClass();
            HttpEntity respEntity =  httpClientClass.httpClientCall(soapBody, "nms:delivery#PrepareMessage",
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
     * This function sends a SOAP request to submit a delivery
     *
     * @param scenarioName
     * @param sessionToken
     * @param securityToken
     * @return the delivery's Id
     * @throws Exception
     */
    @Override
    public String postSOAPSubmitDelivery( String scenarioName, String sessionToken, String securityToken)
            throws Exception{
        String resp = null;
        try {

            String soapBody = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" " +
                    "xmlns:urn=\"urn:nms:delivery\">\n" +
                    "   <soapenv:Header/>\n" +
                    "   <soapenv:Body>\n" +
                    "      <urn:SubmitDelivery>\n" +
                    "         <urn:sessiontoken/>\n" +
                    "         <urn:strScenarioName>"+scenarioName+"</urn:strScenarioName>\n" +
                    "         <urn:elemContent>\n" +
                    "         </urn:elemContent>\n" +
                    "      </urn:SubmitDelivery>\n" +
                    "   </soapenv:Body>\n" +
                    "</soapenv:Envelope>";

            HttpClientClass httpClientClass = new HttpClientClass();
            HttpEntity respEntity =  httpClientClass.httpClientCall(soapBody, "nms:delivery#SubmitDelivery",
                    sessionToken, securityToken );

            if (respEntity != null) {
                resp = EntityUtils.toString(respEntity);

                //prints whole response
                logger.log(Level.INFO,resp);
                //Convert response to SOAP Message
                InputStream is = new ByteArrayInputStream(resp.getBytes());
                SOAPMessage soapResp = MessageFactory.newInstance().createMessage(null, is);

                //Retrieve the deliveryId based on their attribute's name and return it
                return soapResp.getSOAPBody().getElementsByTagName("plDeliveryId").item(0).getNodeValue();

            } else {
                logger.log(Level.WARNING,"No Response");
            }

        } catch (Exception e) {
            throw new Exception("WebService SOAP exception = " + e);
        }
        return null;
    }

    /**
     * This function sends a SOAP request to stop a delivery
     *
     * @param deliveryId
     * @param sessionToken
     * @param securityToken
     * @throws Exception
     */
    @Override
    public void postSOAPStopDelivery(String deliveryId, String sessionToken, String securityToken) throws Exception{
        String resp = null;
        try {

            String soapBody = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" " +
                    "xmlns:urn=\"urn:nms:delivery\">\n" +
                    "   <soapenv:Header/>\n" +
                    "   <soapenv:Body>\n" +
                    "      <urn:Stop>\n" +
                    "         <urn:sessiontoken/>\n" +
                    "         <urn:strDeliveryId>"+deliveryId+"</urn:strDeliveryId>\n" +
                    "      </urn:Stop>\n" +
                    "   </soapenv:Body>\n" +
                    "</soapenv:Envelope>";

            HttpClientClass httpClientClass = new HttpClientClass();
            HttpEntity respEntity =  httpClientClass.httpClientCall(soapBody, "nms:delivery#Stop",
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
