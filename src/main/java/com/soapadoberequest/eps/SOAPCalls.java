package com.soapadoberequest.eps;

import io.github.cdimascio.dotenv.Dotenv;
import org.w3c.dom.Node;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This is the main class
 */
public class SOAPCalls {
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
        SOAPAuth soapAuth = new SOAPAuth();
        SOAPQuery soapQuery = new SOAPQuery();
        SOAPDelivery soapDelivery = new SOAPDelivery();
        SOAPWorkflow soapWorkflow = new SOAPWorkflow();

        //Menu to choose which SOAP requests to execute
        Scanner sc = new Scanner(System.in);
        int entry;
        int choice;
        String sessionToken;
        String securityToken;
        String rFname, rLname, rEmail;
        String prefix, schemaName;
        String wkInternalName;
        String filename;

        do{
            ArrayList<Node> authTokens =  soapAuth.postSOAPAuth();
            sessionToken= authTokens.get(0).getTextContent();
            securityToken=authTokens.get(1).getTextContent();
            System.out.println("Choose SOAP requests to execute: (Choose the number): \n");
            System.out.println("1. Deliveries \n");
            System.out.println("2. Execute queries \n");
            System.out.println("3. Workflows \n");
            /*logger.log(Level.CONFIG, "Choose SOAP requests to execute: (Choose the number): %n");
            logger.log(Level.CONFIG, "1. Deliveries %n");
            logger.log(Level.CONFIG, "2. Execute queries %n");
            logger.log(Level.CONFIG, "3. Workflows %n");*/
            entry = Integer.parseInt(sc.nextLine());

            if(entry == 1){
                System.out.println("Deliveries SOAP requests:\n");
                System.out.println("1. Create with template\n");
                System.out.println("2. Select delivery\n");
                System.out.println("3. Prepare target\n ");
                System.out.println("4. Prepare message\n");
                System.out.println("5. Prepare and start\n");
                System.out.println("6. Submit delivery\n");
                choice = Integer.parseInt(sc.nextLine());
                switch (choice) {
                    case 1, 3, 4, 5, 6 -> System.out.println("In development phase...\n");
                    case 2 -> {
                        System.out.println("Enter delivery's internal name:\n");
                        String internalName = sc.nextLine();
                        soapDelivery.postSOAPSelectDelivery(internalName, sessionToken, securityToken);
                    }
                    default -> System.out.println("Something went wrong. Try again!\n");
                }
            }else if(entry == 2){
                System.out.println("Queries SOAP requests:\n");
                System.out.println("1. Insert one basic recipient (firstname, lastname, email)\n");
                System.out.println("2. Select recipient with email\n");
                System.out.println("3. Write Collection (to apply CRUD methods on recipients, deliveries and workflows)\n");
                System.out.println("4. Select count \n");
                System.out.println("5. Select last entry \n");
                choice = Integer.parseInt(sc.nextLine());
                switch (choice){
                    case 1 -> {
                        System.out.println("Enter recipient's first name:\n");
                        rFname = sc.nextLine();
                        System.out.println("Enter recipient's last name:\n");
                        rLname = sc.nextLine();
                        System.out.println("Enter recipient's email:\n");
                        rEmail = sc.nextLine();
                        soapQuery.postSOAPInsert(rFname, rLname, rEmail, sessionToken, securityToken);
                    }
                    case 2 -> {
                        System.out.println("Enter recipient's email:\n");
                        rEmail = sc.nextLine();
                        soapQuery.postSOAPSelect(rEmail,sessionToken, securityToken);
                    }
                    case 3 ->{
                        System.out.println("Enter filename:\n");
                        filename = sc.nextLine();
                        String directoryPath = dotenv.get("FILE_LOCATION");
                        String filepath = directoryPath + filename;
                        soapQuery.postSOAPWriteCollection( filepath, sessionToken, securityToken);
                    }
                    case  4 ->{
                        System.out.println("Enter schema prefix (ex: nms, xtk):\n");
                        prefix = sc.nextLine();
                        System.out.println("Enter schema name:\n");
                        schemaName = sc.nextLine();
                        soapQuery.postSOAPSelectCount(prefix,schemaName,sessionToken, securityToken);
                    }
                    case 5 ->{
                        System.out.println("Enter schema prefix (ex: nms, xtk):\n");
                        prefix = sc.nextLine();
                        System.out.println("Enter schema name:\n");
                        schemaName = sc.nextLine();
                        soapQuery.postSOAPSelectLast(prefix,schemaName,sessionToken, securityToken);
                    }
                    default -> System.out.println("Something went wrong. Try again!\n");
                }
            }else if(entry ==3){
                System.out.println("Worfklows SOAP requests:\n");
                System.out.println( "1. Start Workflow\n");
                System.out.println("2. Start with parameters\n");
                System.out.println( "3. Post Event Workflow\n");
                System.out.println( "4. Pause Workflow\n");
                System.out.println( "5. Kill Workflow\n");
                System.out.println( "6. Wake Up Workflow\n");
                System.out.println( "7. Get workflow Logs\n");
                System.out.println( "8. Get workflow state\n");
                choice = Integer.parseInt(sc.nextLine());
                switch (choice){
                    case 1 -> {
                        System.out.println("Enter workflow's internal name:\n");
                        wkInternalName = sc.nextLine();
                        soapWorkflow.postSOAPStartWorkflow(wkInternalName,sessionToken, securityToken);
                    }
                    case 2 ->{
                        String stop;
                        ArrayList<String> varName = new ArrayList<>();
                        ArrayList<String> varValue = new ArrayList<>();
                        System.out.println("Enter workflow's internal name:\n");
                        wkInternalName = sc.nextLine();
                        do {
                            System.out.println("Enter variable:\n");
                            String var = sc.nextLine();
                            varName.add(var);
                            System.out.println("Enter value");
                            String value = sc.nextLine();
                            varValue.add(value);
                            System.out.println("Enter 'STOP' to stop inserting or press any key to continue:");
                            stop=sc.nextLine();
                        }while(!stop.equals("STOP"));
                        soapWorkflow.postSOAPStartWithParams(wkInternalName, varName, varValue, sessionToken, securityToken);
                    }
                    case 3 ->{
                        String stop;
                        ArrayList<String> varName = new ArrayList<>();
                        ArrayList<String> varValue = new ArrayList<>();
                        String activity;
                        System.out.println("Enter workflow's internal name:\n");
                        wkInternalName = sc.nextLine();
                        System.out.println("Enter activity:\n");
                        activity = sc.nextLine();
                        do {
                            System.out.println("Enter variable:\n");
                            String var = sc.nextLine();
                            varName.add(var);
                            System.out.println("Enter value");
                            String value = sc.nextLine();
                            varValue.add(value);
                            System.out.println("Enter 'STOP' to stop inserting or press any key to continue:");
                            stop=sc.nextLine();
                        }while(!stop.equals("STOP"));
                        soapWorkflow.postSOAPPostEvent(wkInternalName,activity, varName, varValue, sessionToken, securityToken);
                    }
                    case 4 ->{
                        System.out.println("Enter workflow's internal name:\n");
                        wkInternalName = sc.nextLine();
                        soapWorkflow.postSOAPPauseWorkflow(wkInternalName,sessionToken,securityToken);
                    }
                    case 5 ->{
                        System.out.println("Enter workflow's internal name:\n");
                        wkInternalName = sc.nextLine();
                        soapWorkflow.postSOAPKillWorkflow(wkInternalName,sessionToken,securityToken);
                    }
                    case 6 ->{
                        System.out.println("Enter workflow's internal name:\n");
                        wkInternalName = sc.nextLine();
                        soapWorkflow.postSOAPWakeUpWorkflow(wkInternalName,sessionToken,securityToken);
                    }
                    case 7 ->{
                        System.out.println("Enter workflow's internal name:\n");
                        wkInternalName = sc.nextLine();
                        soapWorkflow.postSOAPWorkflowLogs(wkInternalName,sessionToken,securityToken);
                    }
                    case 8 ->{
                        System.out.println("Enter workflow's internal name:\n");
                        wkInternalName = sc.nextLine();
                        soapWorkflow.postSOAPWorkflowState(wkInternalName,sessionToken,securityToken);
                    }
                }
            }
        }while(entry > 0 && entry < 4);

        //Params config
        //varName.add("email");
        //varValue.add("othboury@gmail.com");

        /*logger.log(Level.INFO,"-------QUERY CALLS-------");
        logger.log(Level.INFO,"********INSERT NEW RECIPIENT INTO DB********");
        soapQuery.postSOAPInsert("Messi", "loko", "treiue@xyz.com",
                tokens.get(0).getTextContent(), tokens.get(1).getTextContent());
        logger.log(Level.INFO,"********SELECT RECIPIENT FROM DB********");
        soapQuery.postSOAPSelect("othboury@gmail.com",tokens.get(0).getTextContent(),
                tokens.get(1).getTextContent());
        logger.log(Level.INFO,"********SUBSCRIBE EXISTING RECIPIENT TO AN EXISTING SERVICE********");
        soapAuth.postSOAPSubscribe(recipient, "SVC1",tokens.get(0).getTextContent(),
                tokens.get(1).getTextContent());
        logger.log(Level.INFO,"********WRITE COLLECTION********");
        String filename = dotenv.get("FILE_LOCATION");
        soapQuery.postSOAPWriteCollection( filename, tokens.get(0).getTextContent(),
                tokens.get(1).getTextContent());*/

        /*logger.log(Level.INFO,"********SELECT COUNT OF TABLE********");
        soapQuery.postSOAPSelectCount("nms","recipient",tokens.get(0).getTextContent(),
                tokens.get(1).getTextContent());

        logger.log(Level.INFO,"-------WORKFLOW CALLS-------");
        /*logger.log(Level.INFO,"********START AN EXISTING WORKFLOW********");
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
        logger.log(Level.INFO,"********GET THE LOGS OF A WORKLFOW********");
        soapWorkflow.postSOAPWorkflowLogs("WKF38",tokens.get(0).getTextContent(),
                tokens.get(1).getTextContent());
        logger.log(Level.INFO,"********GET WORKLFOW'S STATUS********");
        soapWorkflow.postSOAPWorkflowState("WKF38",tokens.get(0).getTextContent(),
                tokens.get(1).getTextContent());

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
