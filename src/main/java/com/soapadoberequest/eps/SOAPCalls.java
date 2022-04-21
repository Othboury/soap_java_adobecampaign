package com.soapadoberequest.eps;

import org.w3c.dom.Node;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

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
        //Logger to initiate the logs
        Logger logger = Logger.getLogger("logger");
        SimpleDateFormat formatter = new SimpleDateFormat("dd_MM_yyyy-HH_mm_ss");
        Date date = new Date();
        FileHandler fh = new FileHandler("Logs-"+formatter.format(date)+".log", true);   // true forces append mode
        SimpleFormatter sf = new SimpleFormatter();
        fh.setFormatter(sf);
        logger.addHandler(fh);

        logger.log(Level.INFO, "START SOAP REQUESTS...");

        //Declare the soap objects for each functionality
        SOAPAuth soapAuth = new SOAPAuth();
        SOAPQuery soapQuery = new SOAPQuery();
        SOAPDelivery soapDelivery = new SOAPDelivery();
        SOAPWorkflow soapWorkflow = new SOAPWorkflow();

        //Constant variables for message that are repeated in the menu scenario
        String defaultMessage = "Something went wrong. Try again!\n";
        String workflowInternal = "Enter workflow's internal name:\n";

        //Scanner to ger the user inputs
        Scanner sc = new Scanner(System.in);

        //Vars related to the Menu navigation
        int entry;
        int choice;

        //Vars to store session token in order to make SOAP calls
        String sessionToken;
        String securityToken;

        //Vars related to the SOAP calls
        String rFname;
        String rLname;
        String rEmail;
        String prefix;
        String schemaName;
        String wkInternalName;
        String filename;

        //Retrieve session tokens and store them in sessionToken and securityToken
        ArrayList<Node> authTokens =  soapAuth.postSOAPAuth();
        sessionToken= authTokens.get(0).getTextContent();
        securityToken=authTokens.get(1).getTextContent();

        //Menu to choose which SOAP requests to execute
        do{
            System.out.println("Please choose which SOAP requests to execute (Choose the number): \n");
            System.out.println("1. Deliveries \n");
            System.out.println("2. Execute queries \n");
            System.out.println("3. Workflows \n");
            entry = Integer.parseInt(sc.nextLine());

            //Deliveries section's menu
            if(entry == 1){
                System.out.println("Deliveries SOAP requests (Choose the number):\n");
                System.out.println("1. Create with template\n");
                System.out.println("2. Select delivery\n");
                System.out.println("3. Prepare target\n ");
                System.out.println("4. Prepare message\n");
                System.out.println("5. Prepare and start\n");
                System.out.println("6. Submit delivery\n");
                choice = Integer.parseInt(sc.nextLine());
                switch (choice) {
                    case 1, 3, 4, 5, 6 -> System.out.println("Under development...\n");
                    case 2 -> {
                        System.out.println("Enter delivery's internal name:\n");
                        String internalName = sc.nextLine();
                        soapDelivery.postSOAPSelectDelivery(internalName, sessionToken, securityToken);
                    }
                    default -> System.out.println(defaultMessage);
                }

            //Queries section's menu
            }else if(entry == 2){
                System.out.println("Queries SOAP requests (Choose the number):\n");
                System.out.println("1. Select recipient with email\n");
                System.out.println("2. Write Collection (to apply CRUD methods on recipients, deliveries and workflows)\n");
                System.out.println("3. Select count \n");
                System.out.println("4. Select last entry \n");
                choice = Integer.parseInt(sc.nextLine());
                switch (choice){
                    case 1 -> {
                        System.out.println("Enter recipient's email:\n");
                        rEmail = sc.nextLine();
                        soapQuery.postSOAPSelect(rEmail,sessionToken, securityToken);
                    }
                    case 2 -> {
                        System.out.println("Enter filename:\n");
                        filename = sc.nextLine();
                        String userHomeFolder = System.getProperty("user.home");
                        String directoryPath = userHomeFolder+"\\Desktop\\Files\\";
                        String filepath = directoryPath + filename;
                        logger.log(Level.INFO, filepath);
                        soapQuery.postSOAPWriteCollection( filepath, sessionToken, securityToken);
                    }
                    case  3 -> {
                        System.out.println("Enter schema prefix (ex: nms, xtk):\n");
                        prefix = sc.nextLine();
                        System.out.println("Enter schema name:\n");
                        schemaName = sc.nextLine();
                        soapQuery.postSOAPSelectCount(prefix,schemaName,sessionToken, securityToken);
                    }
                    case 4 -> {
                        System.out.println("Enter schema prefix (ex: nms, xtk):\n");
                        prefix = sc.nextLine();
                        System.out.println("Enter schema name:\n");
                        schemaName = sc.nextLine();
                        soapQuery.postSOAPSelectLast(prefix,schemaName,sessionToken, securityToken);
                    }
                    default -> System.out.println(defaultMessage);
                }

            //Workflows section's menu
            }else if(entry ==3){
                System.out.println("Workflows SOAP requests (Choose the number):\n");
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
                        System.out.println(workflowInternal);
                        wkInternalName = sc.nextLine();
                        soapWorkflow.postSOAPStartWorkflow(wkInternalName,sessionToken, securityToken);
                    }
                    case 2 -> {
                        String stop;
                        ArrayList<String> varName = new ArrayList<>();
                        ArrayList<String> varValue = new ArrayList<>();
                        System.out.println(workflowInternal);
                        wkInternalName = sc.nextLine();
                        do {
                            System.out.println("Enter variable:\n");
                            String var = sc.nextLine();
                            varName.add(var);
                            System.out.println("Enter value");
                            String value = sc.nextLine();
                            varValue.add(value);
                            System.out.println("Enter 'stop' to stop inserting or press any key to add a variable:");
                            stop=sc.nextLine();
                        }while(!stop.equals("stop"));
                        soapWorkflow.postSOAPStartWithParams(wkInternalName, varName, varValue, sessionToken,
                                securityToken);
                    }
                    case 3 -> {
                        String stop;
                        ArrayList<String> varName = new ArrayList<>();
                        ArrayList<String> varValue = new ArrayList<>();
                        String activity;
                        System.out.println(workflowInternal);
                        wkInternalName = sc.nextLine();
                        System.out.println("Enter activity:\n");
                        activity = sc.nextLine();
                        do {
                            System.out.println("Enter variable:\n");
                            String variables = sc.nextLine();
                            varName.add(variables);
                            System.out.println("Enter value");
                            String value = sc.nextLine();
                            varValue.add(value);
                            System.out.println("Enter 'stop' to stop inserting or press any key to add a variable:");
                            stop=sc.nextLine();
                        }while(!stop.equals("stop"));
                        soapWorkflow.postSOAPPostEvent(wkInternalName,activity, varName, varValue, sessionToken,
                                securityToken);
                    }
                    case 4 -> {
                        System.out.println(workflowInternal);
                        wkInternalName = sc.nextLine();
                        soapWorkflow.postSOAPPauseWorkflow(wkInternalName,sessionToken,securityToken);
                    }
                    case 5 -> {
                        System.out.println(workflowInternal);
                        wkInternalName = sc.nextLine();
                        soapWorkflow.postSOAPKillWorkflow(wkInternalName,sessionToken,securityToken);
                    }
                    case 6 -> {
                        System.out.println(workflowInternal);
                        wkInternalName = sc.nextLine();
                        soapWorkflow.postSOAPWakeUpWorkflow(wkInternalName,sessionToken,securityToken);
                    }
                    case 7 -> {
                        System.out.println(workflowInternal);
                        wkInternalName = sc.nextLine();
                        soapWorkflow.postSOAPWorkflowLogs(wkInternalName,sessionToken,securityToken);
                    }
                    case 8 -> {
                        System.out.println(workflowInternal);
                        wkInternalName = sc.nextLine();
                        soapWorkflow.postSOAPWorkflowState(wkInternalName,sessionToken,securityToken);
                    }
                    default -> System.out.println(defaultMessage);
                }
            }
        }while(entry > 0 && entry < 4);

        logger.log(Level.INFO, "END SOAP REQUESTS...");
    }
}
