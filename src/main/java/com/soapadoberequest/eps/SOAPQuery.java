package com.soapadoberequest.eps;


import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import jakarta.xml.soap.MessageFactory;
import jakarta.xml.soap.SOAPMessage;
import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;
import java.io.*;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class contains the SOAP requests for different Delivery functions, it implements its methods from the interface
 * ISOAPQuery
 */
public class SOAPQuery implements ISOAPQuery{
    Logger logger = Logger.getLogger("logger");

    /**
     * This function sends a SOAP request to insert a new recipient
     *
     * @param firstname FirstName of recipient
     * @param lastname LastName of recipient
     * @param email Email of recipient
     * @param sessionToken Token of the session (__sessiontoken)
     * @param securityToken Security Token of the session (X-Security-Token)
     * @throws Exception Throws Exception when failure
     */
    @Override
    public void postSOAPInsert(String firstname, String lastname, String email, String sessionToken,
                               String securityToken) throws Exception{
        String resp;
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
                    sessionToken, securityToken);

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
     * @param prefix Prefix of the Schema (ex: nms)
     * @param tableName The name of the schema (ex: recipient)
     * @param sessionToken Token of the session (__sessiontoken)
     * @param securityToken Security Token of the session (X-Security-Token)
     * @return Returns the count
     * @throws Exception Throws Esception when failure
     */
    @Override
    public String postSOAPSelectCount(String prefix, String tableName, String sessionToken, String securityToken)
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
                    sessionToken, securityToken);

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
     * @param prefix Prefix of the Schema (ex: nms)
     * @param tableName The name of the schema (ex: recipient)
     * @param sessionToken Token of the session (__sessiontoken)
     * @param securityToken Security Token of the session (X-Security-Token)
     * @return The last entry
     * @throws Exception Throws exception when failure
     */
    @Override
    public String postSOAPSelectLast(String prefix, String tableName, String sessionToken,
                                     String securityToken) throws Exception{
        String resp;
        try {
            String soapBody = """
                       <soapenv:Envelope xmlns:soapenv='http://schemas.xmlsoap.org/soap/envelope/' xmlns:urn='urn:xtk:queryDef'>
                       <soapenv:Header/>
                       <soapenv:Body>
                          <urn:ExecuteQuery>
                             <urn:sessiontoken/>
                             <urn:entity>
                                <queryDef operation='select' schema='nms:recipient' lineCount='1'>
                                   <select>
                                       <node expr='@id'/>
                                   </select>
                                   <orderBy>
                                       <node expr='@id' sortDesc='true'/>
                                   </orderBy>
                                </queryDef>
                             </urn:entity>
                          </urn:ExecuteQuery>
                       </soapenv:Body>
                    </soapenv:Envelope>""";

            HttpClientClass httpClientClass = new HttpClientClass();
            HttpEntity respEntity =  httpClientClass.httpClientCall(soapBody, "xtk:queryDef#ExecuteQuery",
                    sessionToken, securityToken);

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
     * @param email Email of recipient
     * @param sessionToken Token of the session (__sessiontoken)
     * @param securityToken Security Token of the session (X-Security-Token)
     * @throws Exception Throws exception when failure
     */
    @Override
    public void postSOAPSelect(String email, String sessionToken, String securityToken) throws Exception {
        String resp;
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
                    sessionToken, securityToken);

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
     * @param filename The name of the file containing the entries
     * @param sessionToken Token of the session (__sessiontoken)
     * @param securityToken Security Token of the session (X-Security-Token)
     * @throws Exception Throws exception when failure
     */
    @Override
    public void postSOAPWriteCollection(String filename, String sessionToken, String securityToken) throws Exception {
        String[] varNames, values;
        String schema ="";
        String template = "";
        String valuesString;
        String resp, operation, key, key1,key2,key3,keysSentence = null;
        int countInsert = 0;
        int countDelete =0;
        int countUpdate =0;

        try (CSVReader reader = new CSVReader(new FileReader(filename))) {
            varNames = reader.readNext();
            while ((values = reader.readNext()) != null){
                schema = values[0]+ ":" + values[1];
                operation = values[2];
                if(Objects.equals(operation, "insert")){
                    countInsert++;
                }else if(Objects.equals(operation, "delete")){
                    countDelete++;
                }else if(Objects.equals(operation, "update")){
                    countUpdate++;
                }
                key= values[3];
                /*key2="@"+values[4];
                key3 = "@"+values[5];*/
                valuesString ="";
                for(int i=6; i<values.length; i++){
                    valuesString = valuesString + varNames[i] + "='" + values[i] + "'\n";
                }
                /*if(!"@".equals(key1) && !"@".equals(key2) && !"@".equals(key3)){
                    if(key1.indexOf("-") == 0){ key1 = "["+key1+"]";}
                    if(key2.indexOf("-") == 0){ key2 = "["+key2+"]";}
                    if(key3.indexOf("-") == 0){ key3 = "["+key3+"]";}
                    keysSentence = key1+","+key2+","+key3;
                }else if("@".equals(key1) && !"@".equals(key2) && !"@".equals(key3) ){
                    if(key2.indexOf("-") == 0){ key2 = "["+key2+"]";}
                    if(key3.indexOf("-") == 0){ key3 = "["+key3+"]";}
                    keysSentence = key2+","+key3;
                }else if(!"@".equals(key1) && "@".equals(key2) && !"@".equals(key3)){
                    if(key1.indexOf("-") == 0){ key1 = "["+key1+"]";}
                    if(key3.indexOf("-") == 0){ key3 = "["+key3+"]";}
                    keysSentence = key1+","+key3;
                }else if(!"@".equals(key1) && !"@".equals(key2)){
                    if(key1.indexOf("-") == 0){ key1 = "["+key1+"]";}
                    if(key2.indexOf("-") == 0){ key2 = "["+key2+"]";}
                    keysSentence = key1+","+key2;
                }else if(!"@".equals(key1)){
                    if(key1.indexOf("-") == 0){ key1 = "["+key1+"]";}
                    keysSentence = key1;
                }else if(!"@".equals(key2)){
                    if(key2.indexOf("-") == 0){ key2 = "["+key2+"]";}
                    keysSentence = key2;
                }else if(!"@".equals(key3)){
                    if(key3.indexOf("-") == 0){ key3 = "["+key3+"]";}
                    keysSentence = key3;
                }*/
                template = template + "<"+schema.split(":")[1]+" _key='"+key+"' _operation='"+operation+"' "+
                        valuesString + " />";
            }

            System.out.println(template);
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
                        sessionToken, securityToken);

            if (respEntity != null) {
                resp = EntityUtils.toString(respEntity);

                //prints whole response
                logger.log(Level.INFO,resp);
                logger.log(Level.INFO, "FILE -> The number of data to insert is: {0} ", countInsert);
                logger.log(Level.INFO, "FILE -> The number of data to update is: {0} ", countUpdate);
                logger.log(Level.INFO, "FILE -> The number of data to delete is: {0} ", countDelete);
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
     * This function sends a SOAP request to write (Insert, update and delete) a collection of entries
     *
     * @param recipient The recipient object
     * @param sessionToken Token of the session (__sessiontoken)
     * @param securityToken Security Token of the session (X-Security-Token)
     * @throws Exception Throws exception when failure
     */
    @Override
    public void postSOAPWrite(Recipient recipient, String sessionToken, String securityToken) throws Exception {
        String resp;
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
                    sessionToken, securityToken);

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
