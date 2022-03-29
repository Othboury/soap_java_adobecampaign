package com.soapadoberequest.eps;

import org.w3c.dom.Node;

import java.io.File;
import java.util.ArrayList;

public class SoapCalls {

    public static void main(String[] args) {
        System.out.println("START SOAP REQUESTS...");

        //Declare the soap objects for each functionality
        SoapAuth soapAuth = new SoapAuth();
        SoapQuery soapQuery = new SoapQuery();
        SoapDelivery soapDelivery = new SoapDelivery();
        SoapWorkflow soapWorkflow = new SoapWorkflow();

        System.out.println("********AUTHENTICATE TO A SESSION********");
        ArrayList<Node> tokens =  soapAuth.postSOAPAUTH();
        Recipient recipient = new Recipient("Fatim zahra","Bachri","fatbac@gmail.com");

        //Params config
        ArrayList<String> varName= new ArrayList<>();
        ArrayList<String> varValue= new ArrayList<>();
        varName.add("email");
        varValue.add("othboury@gmail.com");

        /*System.out.println("-------QUERY CALLS-------");
        System.out.println("********INSERT NEW RECIPIENT INTO DB********");
        soapQuery.postSOAPInsert("Messi", "loko", "treiue@xyz.com",
                tokens.get(0).getTextContent(), tokens.get(1).getTextContent());
        System.out.println("********SELECT RECIPIENT FROM DB********");
        soapQuery.postSOAPSelect("othboury@gmail.com",tokens.get(0).getTextContent(),
                tokens.get(1).getTextContent());
        System.out.println("********SUBSCRIBE EXISTING RECIPIENT TO AN EXISTING SERVICE********");
        soapAuth.postSOAPSubscribe(recipient, "SVC1",tokens.get(0).getTextContent(),
                tokens.get(1).getTextContent());
        System.out.println("********WRITE A NEW RECIPIENT********");
        String filename = "C:\\Users\\othboury\\Desktop\\Files\\recipient.csv";
        soapQuery.postSOAPWriteCollection( filename, tokens.get(0).getTextContent(),
                tokens.get(1).getTextContent());*/

        System.out.println("-------WORKFLOW CALLS-------");
        /*System.out.println("********START AN EXISTING WORKFLOW********");
        soapWorkflow.postSOAPStartWorkflow("WKF33",tokens.get(0).getTextContent(),
                tokens.get(1).getTextContent());
        System.out.println("********TRIGGER A WORKFLOW START FROM SIGNAL********");
        soapWorkflow.postSOAPPostEvent("WKF12", "signal", varName,varValue,
                tokens.get(0).getTextContent(), tokens.get(1).getTextContent());
        System.out.println("********KILL A WORKFLOW********");
        soapWorkflow.postSOAPKillWorkflow("WKF8",tokens.get(0).getTextContent(),
                tokens.get(1).getTextContent() );*/
        System.out.println("********PAUSE A WORKFLOW********");
        soapWorkflow.postSOAPPauseWorkflow("WKF33",tokens.get(0).getTextContent(),
                tokens.get(1).getTextContent() );
        /*System.out.println("********WAKE UP A WORKFLOW********");
        soapWorkflow.postSOAPWakeUpWorkflow("WKF8",tokens.get(0).getTextContent(),
                tokens.get(1).getTextContent() );
        System.out.println("********START A WORKFLOW WITH PARAMETERS********");
        soapWorkflow.postSOAPStartWithParams("WKF13", varName, varValue, tokens.get(0).getTextContent(),
                tokens.get(1).getTextContent() );
        soapWorkflow.postSOAPWorkflowLogs("16873",tokens.get(0).getTextContent(),
                tokens.get(1).getTextContent());*/

        System.out.println("-------DELIVERY CALLS-------");
       /* System.out.println("********SELECT A DELIVERY********");
        soapDelivery.postSOAPSelectDelivery("DM33",tokens.get(0).getTextContent(),
                tokens.get(1).getTextContent());*/
        /*soapDelivery.postSOAPCreateWithTemplate("DM7",varName, varValue,"",
                tokens.get(0).getTextContent(), tokens.get(1).getTextContent());*/
       /* soapDelivery.postSOAPPrepareAndStart("DM40",tokens.get(0).getTextContent(),
                tokens.get(1).getTextContent());
        soapDelivery.postSOAPPrepareTarget(114886,tokens.get(0).getTextContent(),
                tokens.get(1).getTextContent());/
        soapDelivery.postSOAPPrepareMessage("DM40",tokens.get(0).getTextContent(),
                tokens.get(1).getTextContent());
        soapDelivery.postSOAPSubmitDelivery("DM7",tokens.get(0).getTextContent(),
                tokens.get(1).getTextContent());*/
        System.out.println("END SOAP REQUESTS...");
    }
}
