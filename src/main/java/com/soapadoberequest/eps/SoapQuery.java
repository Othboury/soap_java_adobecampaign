package com.soapadoberequest.eps;


import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import jakarta.xml.soap.MessageFactory;
import jakarta.xml.soap.SOAPMessage;
import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class contains the SOAP requests for different Delivery functions, it implements its methods from the interface
 * ISOAPQuery
 */
public class SoapQuery implements ISOAPQuery{

    Logger logger = Logger.getLogger("logger");

    /**
     * This function sends a SOAP request to insert a new recipient
     *
     * @param firstname
     * @param lastname
     * @param email
     * @param sessionToken
     * @param securityToken
     * @throws Exception
     */
    @Override
    public void postSOAPInsert(String firstname, String lastname, String email, String sessionToken,
                               String securityToken) throws Exception{
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

            HttpClientClass httpClientClass = new HttpClientClass();
            HttpEntity respEntity =  httpClientClass.httpClientCall(soapBody, "nms:recipient#insert",
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
     * This function sends a SOAP request to select the count of table
     *
     * @param prefix
     * @param tableName
     * @param sessionToken
     * @param securityToken
     * @return
     * @throws Exception
     */
    @Override
    public String postSOAPSelectCount(String prefix, String tableName, String sessionToken, String securityToken)
            throws Exception{
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
                return soapResp.getSOAPBody().getElementsByTagName(tableName).item(0)
                        .getAttributes().getNamedItem("count").getNodeValue();

            } else {
                logger.log(Level.WARNING,"No Response");
            }

        } catch (Exception e) {
            throw new Exception("WebService SOAP exception = " + e);
        }
        return null;
    }

    /**
     * This function sends a SOAP request to select last entry's id
     *
     * @param prefix
     * @param tableName
     * @param sessionToken
     * @param securityToken
     * @return The count
     * @throws Exception
     */
    @Override
    public String postSOAPSelectLast(String prefix, String tableName, String sessionToken,
                                     String securityToken) throws Exception{
        String resp = null;
        try {
            String soapBody = """ 
                       <soapenv:Envelope xmlns:soapenv= http://schemas.xmlsoap.org/soap/envelope/ xmlns:urn=urn:xtk:queryDef>
                       <soapenv:Header/>
                       <soapenv:Body>
                          <urn:ExecuteQuery>
                             <urn:sessiontoken/>
                             <urn:entity>
                                <queryDef operation=select schema=nms:recipient lineCount=1>
                                   <select>
                                       <node expr=@id/>
                                   </select>
                                   <orderBy>
                                       <node expr=@id sortDesc=true/>
                                   </orderBy>
                                </queryDef>
                             </urn:entity>
                          </urn:ExecuteQuery>
                       </soapenv:Body>
                    </soapenv:Envelope>""";

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
                return soapResp.getSOAPBody().getElementsByTagName(tableName).item(0)
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
     * This function sends a SOAP request to select a recipient using the email
     *
     * @param email
     * @param sessionToken
     * @param securityToken
     * @throws Exception
     */
    @Override
    public void postSOAPSelect(String email, String sessionToken, String securityToken) throws Exception {
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
     * This function sends a SOAP request to write(Insert) a collection of entries (recipient - deliveries -
     * workflow - etc) from csv file
     *
     * @param filename
     * @param sessionToken
     * @param securityToken
     * @throws Exception
     */
    @Override
    public void postSOAPWriteCollection(String filename, String sessionToken, String securityToken) throws Exception {
        String resp = null;
        String[] varNames;
        String[] values;
        String valuesString ="";
        String schema = "";
        String template ="";
        String key="";

        try (CSVReader reader = new CSVReader(new FileReader(filename))) {
            varNames = reader.readNext();
            while ((values = reader.readNext()) != null){
                schema = values[0]+ ":" + values[1];
                key = values[2];
                valuesString ="";
                for(int i=3; i<values.length; i++){
                    valuesString = valuesString + varNames[i] + "='" + values[i] + "'\n";
                }
                template = template + "<"+schema.split(":")[1]+" _key='"+key+"' "+
                        valuesString + " />";
            }

            try{
            int currentCount = Integer.parseInt(postSOAPSelectCount(schema.split(":")[0], schema.split(":")[1],
                    sessionToken, securityToken));
            int lastId = Integer.parseInt(postSOAPSelectLast(schema.split(":")[0], schema.split(":")[1],
                    sessionToken, securityToken));
            String soapBody = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:urn=\"urn:xtk:session\">\n" +
                    "   <soapenv:Header/>\n" +
                    "   <soapenv:Body>\n" +
                    "      <urn:WriteCollection>\n" +
                    "         <urn:sessiontoken/>\n" +
                    "         <urn:domDoc>\n" +
                    "              <"+schema.split(":")[1]+"-collection xtkschema='"+schema+"'>"+
                                        template +
                    "               </"+schema.split(":")[1]+"-collection>"+
                    "         </urn:domDoc>\n" +
                    "      </urn:WriteCollection>\n" +
                    "   </soapenv:Body>\n" +
                    "</soapenv:Envelope>";

            HttpClientClass httpClientClass = new HttpClientClass();
            HttpEntity respEntity =  httpClientClass.httpClientCall(soapBody, "xtk:persist#WriteCollection",
                        sessionToken, securityToken );

            if (respEntity != null) {
                resp = EntityUtils.toString(respEntity);

                //prints whole response
                logger.log(Level.INFO,resp);

                int secondCount = Integer.parseInt(postSOAPSelectCount(schema.split(":")[0], schema.split(":")[1]
                        ,sessionToken, securityToken));

                if (currentCount == secondCount){
                    logger.log(Level.INFO,"No entry has been saved in the datatable");
                }else if(secondCount > currentCount){
                    int entriesNumber = secondCount - currentCount;
                    logger.log(Level.INFO,"Last entry ID before insertion: {0}", lastId);
                    logger.log(Level.INFO,"{0} entries were registered in database", String.valueOf(entriesNumber));
                    String lastIdInserted = postSOAPSelectLast(schema.split(":")[0]
                            , schema.split(":")[1],sessionToken, securityToken);
                    logger.log(Level.INFO,"Last entry ID after insertion: {0}", lastIdInserted);
                }

            } else {
                logger.log(Level.WARNING,"No Response");
            }

        } catch (Exception e) {
                throw new Exception("WebService SOAP exception = " + e);
        }
    } catch (CsvValidationException e) {
            throw new Exception("CsvValidationException = "+ e);
        } catch (IOException e) {
            throw new Exception("IOException = " + e);
        }
    }

    /**
     * This function sends a SOAP request to write(Insert) a new recipient
     *
     * @param recipient
     * @param sessionToken
     * @param securityToken
     * @throws Exception
     */
    @Override
    public void postSOAPWrite(Recipient recipient, String sessionToken, String securityToken) throws Exception {
        String resp = null;
        String prefix ="nms";
        String schema ="recipient";
        try {
            int currentCount = Integer.parseInt(postSOAPSelectCount(prefix, schema,sessionToken, securityToken));
            int lastId = Integer.parseInt(postSOAPSelectLast(prefix, schema,sessionToken, securityToken));
            String soapBody = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:urn=\"urn:xtk:session\">\n" +
                    "   <soapenv:Header/>\n" +
                    "   <soapenv:Body>\n" +
                    "      <urn:Write>\n" +
                    "         <urn:sessiontoken/>\n" +
                    "         <urn:domDoc>\n" +
                    "            <recipient _operation=\"insert\" \n" +
                    "            \t\t\tlastName=\""+recipient.getLastName()+"\" \n" +
                    "            \t\t\tfirstName=\""+recipient.getFirstName()+"\" \n" +
                    "            \t\t\temail=\""+recipient.getEmail()+"\"\n" +
                    "            \t\t\txtkschema=\""+prefix+":"+schema +
                    "         </urn:domDoc>\n" +
                    "      </urn:Write>\n" +
                    "   </soapenv:Body>\n" +
                    "</soapenv:Envelope>";

            HttpClientClass httpClientClass = new HttpClientClass();
            HttpEntity respEntity =  httpClientClass.httpClientCall(soapBody, "xtk:persist#Write",
                    sessionToken, securityToken );

            if (respEntity != null) {
                resp = EntityUtils.toString(respEntity);

                //prints whole response
                logger.log(Level.INFO,resp);

                int secondCount = Integer.parseInt(postSOAPSelectCount(prefix, schema,sessionToken, securityToken));

                if (currentCount == secondCount){
                    logger.log(Level.WARNING,"No entry has been saved in the datatable");
                }else if(secondCount > currentCount){
                    int entriesNumber = secondCount - currentCount;
                    logger.log(Level.INFO,"Last entry ID before insertion: {0}", lastId);
                    logger.log(Level.INFO,"{0} entries were registered in database", String.valueOf(entriesNumber));
                    String lastIdInserted = postSOAPSelectLast("nms", "recipient",
                            sessionToken, securityToken);
                    logger.log(Level.INFO,"Last entry ID after insertion: {0}", lastIdInserted);
                }

            } else {
                logger.log(Level.WARNING,"No Response");
            }

        } catch (Exception e) {
            throw new Exception("WebService SOAP exception = " + e);
        }
    }
}
