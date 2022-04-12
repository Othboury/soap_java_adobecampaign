package com.soapadoberequest.eps;

import io.github.cdimascio.dotenv.Dotenv;
import org.w3c.dom.Node;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This is the main class
 */
public class SoapCalls {
    /**
     * The main method that unable us to run the methods
     *
     * @param args Args for the main methods
     * @throws Exception Throws exception when failure
     */
    public static void main(String[] args) throws Exception {
        Dotenv dotenv=Dotenv.configure().ignoreIfMissing().ignoreIfMalformed().load();
        Logger logger = Logger.getLogger("logger");
        logger.log(Level.INFO, "START SOAP REQUESTS...");

        //Declare the soap objects for each functionality
        SoapAuth soapAuth = new SoapAuth();
        SoapQuery soapQuery = new SoapQuery();
        SoapDelivery soapDelivery = new SoapDelivery();
        SoapWorkflow soapWorkflow = new SoapWorkflow();

        logger.log(Level.INFO,"********AUTHENTICATE TO A SESSION********");
        ArrayList<Node> tokens =  soapAuth.postSOAPAuth();
        Recipient recipient = new Recipient("Fatim zahra","Bachri","fatbac@gmail.com");

        //Params config
        ArrayList<String> varName= new ArrayList<>();
        ArrayList<String> varValue= new ArrayList<>();
        varName.add("email");
        varValue.add("othboury@gmail.com");

        logger.log(Level.INFO,"-------QUERY CALLS-------");
        /*logger.log(Level.INFO,"********INSERT NEW RECIPIENT INTO DB********");
        soapQuery.postSOAPInsert("Messi", "loko", "treiue@xyz.com",
                tokens.get(0).getTextContent(), tokens.get(1).getTextContent());
        logger.log(Level.INFO,"********SELECT RECIPIENT FROM DB********");
        soapQuery.postSOAPSelect("othboury@gmail.com",tokens.get(0).getTextContent(),
                tokens.get(1).getTextContent());
        logger.log(Level.INFO,"********SUBSCRIBE EXISTING RECIPIENT TO AN EXISTING SERVICE********");
        soapAuth.postSOAPSubscribe(recipient, "SVC1",tokens.get(0).getTextContent(),
                tokens.get(1).getTextContent());
        logger.log(Level.INFO,"********WRITE A NEW RECIPIENT********");
        String filename = dotenv.get("FILE_LOCATION");
        soapQuery.postSOAPWriteCollection( filename, tokens.get(0).getTextContent(),
                tokens.get(1).getTextContent());*/

        /*logger.log(Level.INFO,"********SELECT COUNT OF TABLE********");
        soapQuery.postSOAPSelectCount("nms","recipient",tokens.get(0).getTextContent(),
                tokens.get(1).getTextContent());*/

        logger.log(Level.INFO,"-------WORKFLOW CALLS-------");
        logger.log(Level.INFO,"********START AN EXISTING WORKFLOW********");
        soapWorkflow.postSOAPStartWorkflow("WKF38",tokens.get(0).getTextContent(),
                tokens.get(1).getTextContent());
        /*logger.log(Level.INFO,"********TRIGGER A WORKFLOW START FROM SIGNAL********");
        soapWorkflow.postSOAPPostEvent("WKF12", "signal", varName,varValue,
                tokens.get(0).getTextContent(), tokens.get(1).getTextContent());
        logger.log(Level.INFO,"********KILL A WORKFLOW********");
        soapWorkflow.postSOAPKillWorkflow("WKF8",tokens.get(0).getTextContent(),
                tokens.get(1).getTextContent() );*/
        /*logger.log(Level.INFO,"********PAUSE A WORKFLOW********");
        soapWorkflow.postSOAPPauseWorkflow("WKF37",tokens.get(0).getTextContent(),
                tokens.get(1).getTextContent() );
        /*logger.log(Level.INFO,"********WAKE UP A WORKFLOW********");
        soapWorkflow.postSOAPWakeUpWorkflow("WKF8",tokens.get(0).getTextContent(),
                tokens.get(1).getTextContent() );
        logger.log(Level.INFO,"********START A WORKFLOW WITH PARAMETERS********");
        soapWorkflow.postSOAPStartWithParams("WKF13", varName, varValue, tokens.get(0).getTextContent(),
                tokens.get(1).getTextContent() );
        soapWorkflow.postSOAPWorkflowLogs("16873",tokens.get(0).getTextContent(),
                tokens.get(1).getTextContent());*/

        /*logger.log(Level.INFO,"-------DELIVERY CALLS-------");
        logger.log(Level.INFO,"********SELECT A DELIVERY********");
        soapDelivery.postSOAPSelectDelivery("DM33",tokens.get(0).getTextContent(),
                tokens.get(1).getTextContent());
        soapDelivery.postSOAPCreateWithTemplate("DM7",varName, varValue,"",
                tokens.get(0).getTextContent(), tokens.get(1).getTextContent());
        soapDelivery.postSOAPPrepareAndStart("DM40",tokens.get(0).getTextContent(),
                tokens.get(1).getTextContent());
        soapDelivery.postSOAPPrepareTarget("DM40",tokens.get(0).getTextContent(),
                tokens.get(1).getTextContent());
        soapDelivery.postSOAPPrepareMessage("DM40",tokens.get(0).getTextContent(),
                tokens.get(1).getTextContent());
        soapDelivery.postSOAPSubmitDelivery("DM7",tokens.get(0).getTextContent(),
                tokens.get(1).getTextContent());*/
        logger.log(Level.INFO, "END SOAP REQUESTS...");
    }
}
