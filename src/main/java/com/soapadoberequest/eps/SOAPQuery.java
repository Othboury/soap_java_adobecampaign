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
 * This class contains the SOAP requests for different query functions, it implements its methods from the interface
 * ISOAPQuery
 */
public class SOAPQuery implements ISOAPQuery {
    //Logger to initiate the logs
    Logger logger = Logger.getLogger("logger");

    /**
     * This function sends a SOAP request to select the count of table
     *
     * @param prefix Prefix of the schema (ex: nms)
     * @param tableName The name of the schema (ex: recipient)
     * @param sessionToken Token of the session (__sessiontoken)
     * @param securityToken Security token of the session (X-Security-Token)
     * @return Returns the count
     * @throws Exception Throws exception when failure
     */
    @Override
    public String postSOAPSelectCount(String prefix, String tableName, String sessionToken, String securityToken)
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

                //Convert response to SOAP Message
                InputStream is = new ByteArrayInputStream(resp.getBytes());
                SOAPMessage soapResp = MessageFactory.newInstance().createMessage(null, is);

                //Check the firstChild of the SOAPResponse to determine whether the response has a fault envelope or not
                String firstChild = soapResp.getSOAPBody().getFirstChild().getNodeName();
                if(!firstChild.equals("SOAP-ENV:Fault")){
                    System.out.println("The count of '"+prefix+tableName+"' has been successfully retrieved.\n");
                    //Print logs
                    String loggerInfo = Formatter.prettyPrintByDom4j(resp,4, true);
                    logger.log(Level.INFO,"Select count query SOAP request XML response:");
                    logger.log(Level.INFO,loggerInfo);
                } else{
                    System.out.println("The count of '"+prefix+tableName+"' has not been successfully retrieved, " +
                            "please check the logs for further " +
                            "details.\n");
                    //Print logs
                    String loggerInfo = Formatter.prettyPrintByDom4j(resp,4, true);
                    logger.log(Level.INFO,"Select count query SOAP request XML response (with errors):");
                    logger.log(Level.INFO,loggerInfo);
                }

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
     * This function sends a SOAP request to select last entry's ID
     *
     * @param prefix Prefix of the schema (ex: nms)
     * @param tableName The name of the schema (ex: recipient)
     * @param sessionToken Token of the session (__sessiontoken)
     * @param securityToken Security token of the session (X-Security-Token)
     * @return The last entry
     * @throws Exception Throws exception when failure
     */
    @Override
    public String postSOAPSelectLast(String prefix, String tableName, String sessionToken,
                                     String securityToken) throws Exception {
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

                //Convert response to SOAP Message
                InputStream is = new ByteArrayInputStream(resp.getBytes());
                SOAPMessage soapResp = MessageFactory.newInstance().createMessage(null, is);

                //Check the firstChild of the SOAPResponse to determine whether the response has a fault envelope or not
                String firstChild = soapResp.getSOAPBody().getFirstChild().getNodeName();
                if(!firstChild.equals("SOAP-ENV:Fault")){
                    System.out.println("The last entry in '"+prefix+tableName+"' has been successfully retrieved.\n");
                    //Print logs
                    String loggerInfo = Formatter.prettyPrintByDom4j(resp,4, true);
                    logger.log(Level.INFO,"Last entry query SOAP request XML response:");
                    logger.log(Level.INFO,loggerInfo);
                } else{
                    System.out.println("The last entry in '"+prefix+tableName+"' has not been successfully retrieved, " +
                            "please check the logs for further " +
                            "details.\n");
                    //Print logs
                    String loggerInfo = Formatter.prettyPrintByDom4j(resp,4, true);
                    logger.log(Level.INFO,"Last entry query SOAP request XML response (with errors):");
                    logger.log(Level.INFO,loggerInfo);
                }

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
     * @param securityToken Security token of the session (X-Security-Token)
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

                //Convert response to SOAP Message
                InputStream is = new ByteArrayInputStream(resp.getBytes());
                SOAPMessage soapResp = MessageFactory.newInstance().createMessage(null, is);

                //Check the firstChild of the SOAPResponse to determine whether the response has a fault envelope or not
                String firstChild = soapResp.getSOAPBody().getFirstChild().getNodeName();
                if(!firstChild.equals("SOAP-ENV:Fault")){
                    System.out.println("The recipient has been successfully retrieved.\n");
                    String emailRetrieved = soapResp.getSOAPBody().getElementsByTagName("recipient").item(0)
                            .getAttributes().getNamedItem("email").getNodeValue();
                    String firstNameRetrieved = soapResp.getSOAPBody().getElementsByTagName("recipient").item(0)
                            .getAttributes().getNamedItem("firstName").getNodeValue();
                    String lastNameRetrieved = soapResp.getSOAPBody().getElementsByTagName("recipient").item(0)
                            .getAttributes().getNamedItem("lastName").getNodeValue();
                    System.out.println("Recipient's email: "+ emailRetrieved+"\n Recipient's firstname: "+
                            firstNameRetrieved+ "\n Recipient's lastname: "+ lastNameRetrieved + "\n");

                    //Print logs
                    String loggerInfo = Formatter.prettyPrintByDom4j(resp,4, true);
                    logger.log(Level.INFO,"Select recipient query SOAP request XML response:");
                    logger.log(Level.INFO,loggerInfo);
                } else{
                    System.out.println("The recipient was not successfully retrieved, please check the logs for further " +
                            "details.\n");
                    //Print logs
                    String loggerInfo = Formatter.prettyPrintByDom4j(resp,4, true);
                    logger.log(Level.INFO,"Select recipient query SOAP request XML response (with errors):");
                    logger.log(Level.INFO,loggerInfo);
                }

            } else {
                logger.log(Level.WARNING,"No Response");
            }

        } catch (Exception e) {
            throw new Exception("WebService SOAP exception = " + e);
        }
    }

    /**
     * This function sends a SOAP request to write(Insert) a collection of entries (recipient - deliveries -
     * workflow) from csv file
     *
     * @param filename The name of the file containing the entries
     * @param sessionToken Token of the session (__sessiontoken)
     * @param securityToken Security token of the session (X-Security-Token)
     * @throws Exception Throws exception when failure
     */
    @Override
    public void postSOAPWriteCollection(String filename, String sessionToken, String securityToken) throws Exception {
        String[] varNames, values;
        String schema ="";
        String template = "";
        String valuesString;
        String resp, operation, key;
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
                valuesString ="";
                for(int i=4; i<values.length; i++){
                    valuesString = valuesString + varNames[i] + "='" + values[i] + "'\n";
                }
                template = template + "<"+schema.split(":")[1]+" _key='"+key+"' _operation='"+operation+"' "+
                        valuesString + " />";
            }

            logger.log(Level.INFO,"The entity collection:");
            logger.log(Level.INFO, template);

            try {
            int currentCount = Integer.parseInt(postSOAPSelectCount(schema.split(":")[0], schema.split(":")[1],
                    sessionToken, securityToken));
            String soapBody = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" " +
                    "xmlns:urn=\"urn:xtk:session\">\n" +
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

                //Convert response to SOAP Message
                InputStream is = new ByteArrayInputStream(resp.getBytes());
                SOAPMessage soapResp = MessageFactory.newInstance().createMessage(null, is);

                //Check the firstChild of the SOAPResponse to determine whether the response has a fault envelope or not
                String firstChild = soapResp.getSOAPBody().getFirstChild().getNodeName();
                if(!firstChild.equals("SOAP-ENV:Fault")){
                    //prints whole response
                    String loggerInfo = Formatter.prettyPrintByDom4j(resp,4, true);

                    logger.log(Level.INFO, "FILE -> The number of rows to insert is: {0} ", countInsert);
                    System.out.println("FILE -> The number of rows to insert is: "+ countInsert+"\n");
                    logger.log(Level.INFO, "FILE -> The number of rows to update is: {0} ", countUpdate);
                    System.out.println("FILE -> The number of rows to update is: "+ countUpdate+"\n");
                    logger.log(Level.INFO, "FILE -> The number of rows to delete is: {0} ", countDelete);
                    System.out.println("FILE -> The number of rows to delete is: "+ countDelete+"\n");
                    int secondCount = Integer.parseInt(postSOAPSelectCount(schema.split(":")[0],
                            schema.split(":")[1] ,sessionToken, securityToken));

                    if (currentCount == secondCount && countInsert == countDelete) {
                        logger.log(Level.INFO,"The numbers of lines in the database didn't change because the file " +
                                "contain a number of rows to insert equal to the number of rows to delete");
                        System.out.println("The numbers of lines in the database didn't change because the file" +
                                "contain a number of rows to insert equal to the number of rows to delete.\n");


                    } else if (currentCount == secondCount) {
                        logger.log(Level.INFO,"No entry has been saved in the datatable");
                        System.out.println("No entry has been saved in the datatable.\n");
                    } else if (secondCount > currentCount) {
                        int entriesNumber = secondCount - currentCount;
                        logger.log(Level.INFO,"{0} entries were registered in database", String.valueOf(entriesNumber));
                        System.out.println(String.valueOf(entriesNumber)+" entries were registered in database\n");
                    }

                    logger.log(Level.INFO,"WriteCollection query SOAP request XML response:");
                    logger.log(Level.INFO,loggerInfo);
                } else{
                    System.out.println("The writeCollection wasn't successful, please check the logs for further " +
                            "details.\n");
                    //Print logs
                    String loggerInfo = Formatter.prettyPrintByDom4j(resp,4, true);
                    logger.log(Level.INFO,"Last entry query SOAP request XML response (with errors):");
                    logger.log(Level.INFO,loggerInfo);
                }


            } else {
                logger.log(Level.WARNING,"No Response");
            }

        } catch (Exception e){
                throw new Exception("WebService SOAP exception = " + e);
        }
    } catch (CsvValidationException e){
            throw new Exception("CsvValidationException = "+ e);
        } catch (IOException e){
            throw new Exception("IOException = " + e);
        }
    }

    /**
     * This function sends a SOAP request to write (Insert, update and delete) a collection of entries
     *
     * @param recipient The recipient object
     * @param sessionToken Token of the session (__sessiontoken)
     * @param securityToken Security token of the session (X-Security-Token)
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
            String soapBody = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" " +
                    "xmlns:urn=\"urn:xtk:session\">\n" +
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

                //Convert response to SOAP Message
                InputStream is = new ByteArrayInputStream(resp.getBytes());
                SOAPMessage soapResp = MessageFactory.newInstance().createMessage(null, is);

                //Check the firstChild of the SOAPResponse to determine whether the response has a fault envelope or not
                String firstChild = soapResp.getSOAPBody().getFirstChild().getNodeName();
                if(!firstChild.equals("SOAP-ENV:Fault")){
                    int secondCount = Integer.parseInt(postSOAPSelectCount(prefix, schema,sessionToken, securityToken));

                    if (currentCount == secondCount){
                        logger.log(Level.INFO,"No entry has been saved in the datatable");
                        System.out.println("No entry has been saved in the datatable. \n");
                    }else if(secondCount > currentCount){
                        int entriesNumber = secondCount - currentCount;
                        logger.log(Level.INFO,"Last entry ID before insertion: {0}", lastId);
                        System.out.println("Last entry ID before insertion: "+lastId+" \n");
                        logger.log(Level.INFO,"{0} entries were registered in database", String.valueOf(entriesNumber));
                        System.out.println(String.valueOf(entriesNumber)+" entries were registered in database. \n");
                        String lastIdInserted = postSOAPSelectLast("nms", "recipient",
                                sessionToken, securityToken);
                        logger.log(Level.INFO,"Last entry ID after insertion: {0}", lastIdInserted);
                        System.out.println("Last entry ID after insertion: "+lastIdInserted+" \n");
                    }
                    //Print logs
                    String loggerInfo = Formatter.prettyPrintByDom4j(resp,4, true);
                    logger.log(Level.INFO,"Write query SOAP request XML response:");
                    logger.log(Level.INFO,loggerInfo);
                } else{
                    System.out.println("Writing the recipient into the DB was not successful, please check the logs for further " +
                            "details.\n");
                    //Print logs
                    String loggerInfo = Formatter.prettyPrintByDom4j(resp,4, true);
                    logger.log(Level.INFO,"Write query SOAP request XML response (with errors):");
                    logger.log(Level.INFO,loggerInfo);
                }

            } else {
                logger.log(Level.WARNING,"No Response");
            }

        } catch (Exception e) {
            throw new Exception("WebService SOAP exception = " + e);
        }
    }

}