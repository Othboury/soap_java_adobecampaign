package com.soapadoberequest.eps;

import org.w3c.dom.Node;

import java.text.SimpleDateFormat;
import java.util.*;
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
        FileHandler fh = new FileHandler("Logs-"+formatter.format(date)+".log", true);
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
        String rEmail;
        String prefix;
        String schemaName;
        String wkInternalName;
        String filename;

        //Vars for authentication
        String login;
        String password;

        //Log into to adobe server to retrieve sessionToken and sessionSecurity
        System.out.println("\nPlease enter your adobe campaign credentials : \n");
        System.out.println("\nPort : \n");
        HttpClientClass.port = sc.nextLine();
        String port = HttpClientClass.port;
        System.out.println("\nLogin : \n");
        login = sc.nextLine();
        System.out.println("\nPassword : \n");
        password = sc.nextLine();
        ArrayList<Node> authTokens = soapAuth.postSOAPAuth(login, password,port);
        sessionToken = authTokens.get(0).getTextContent();
        securityToken = authTokens.get(1).getTextContent();
        if (!sessionToken.isEmpty() && !sessionToken.isEmpty()) {
            //Menu to choose which SOAP requests to execute
            do {

                System.out.println("\nPlease choose which SOAP requests to execute (Choose the number): \n");
                System.out.println("1. Deliveries \n");
                System.out.println("2. Execute queries \n");
                System.out.println("3. Workflows \n");
                entry = Integer.parseInt(sc.nextLine());

                //Deliveries section's menu
                if (entry == 1) {
                    System.out.println("\nDeliveries SOAP requests (Choose the number):\n");
                    System.out.println("1. Create with template\n");
                    System.out.println("2. Select delivery\n");
                    System.out.println("3. Prepare target\n ");
                    System.out.println("4. Prepare message\n");
                    System.out.println("5. Prepare and start\n");
                    System.out.println("6. Submit delivery\n");
                    System.out.println("0. Go back\n");
                    choice = Integer.parseInt(sc.nextLine());
                    switch (choice) {
                        case 1, 3, 4, 5, 6 -> System.out.println("Under development...\n");
                        case 2 -> {
                            System.out.println("\nEnter delivery's internal name:\n");
                            String internalName = sc.nextLine();
                            soapDelivery.postSOAPSelectDelivery(internalName, sessionToken, securityToken);
                        }
                        default -> System.out.println(defaultMessage);
                    }

                    //Queries section's menu
                } else if (entry == 2) {
                    System.out.println("\nQueries SOAP requests (Choose the number):\n");
                    System.out.println("1. Select recipient with email\n");
                    System.out.println("2. Write Collection (to apply CRUD methods on recipients, deliveries and workflows)\n");
                    System.out.println("3. Select count \n");
                    System.out.println("4. Select last entry \n");
                    System.out.println("0. Go back\n");
                    choice = Integer.parseInt(sc.nextLine());
                    switch (choice) {
                        case 1 -> {
                            System.out.println("\nEnter recipient's email:\n");
                            rEmail = sc.nextLine();
                            soapQuery.postSOAPSelect(rEmail, sessionToken, securityToken);
                        }
                        case 2 -> {
                            System.out.println("\nEnter filename:\n");
                            filename = sc.nextLine();
                            String userHomeFolder = System.getProperty("user.home");
                            String directoryPath = userHomeFolder + "\\Desktop\\Files\\";
                            String filepath = directoryPath + filename;
                            logger.log(Level.INFO, filepath);
                            soapQuery.postSOAPWriteCollection(filepath, sessionToken, securityToken);
                        }
                        case 3 -> {
                            System.out.println("\nEnter schema prefix (ex: nms, xtk):\n");
                            prefix = sc.nextLine();
                            System.out.println("\nEnter schema name:\n");
                            schemaName = sc.nextLine();
                            System.out.println("\nIt contains " + soapQuery.postSOAPSelectCount(prefix, schemaName, sessionToken,
                                    securityToken) + " rows\n");
                        }
                        case 4 -> {
                            System.out.println("\nEnter schema prefix (ex: nms, xtk):\n");
                            prefix = sc.nextLine();
                            System.out.println("\nEnter schema name:\n");
                            schemaName = sc.nextLine();
                            System.out.println(soapQuery.postSOAPSelectLast(prefix, schemaName, sessionToken, securityToken));
                        }
                        default -> System.out.println(defaultMessage);
                    }

                    //Workflows section's menu
                } else if (entry == 3) {
                    System.out.println("\n***************************\n");
                    System.out.println("ALERT: The system will, every 10 minutes, automatically start a scan of workflows " +
                            "that are paused and stopped in order to analyse their failures...The system will generate a log" +
                            "file containing the failure data\n");
                    System.out.println("***************************\n");
                    System.out.println("\nWorkflows SOAP requests (Choose the number):\n");
                    System.out.println("1. Start Workflow\n");
                    System.out.println("2. Start with parameters\n");
                    System.out.println("3. Post Event Workflow\n");
                    System.out.println("4. Pause Workflow\n");
                    System.out.println("5. Kill Workflow\n");
                    System.out.println("6. Wake Up Workflow\n");
                    System.out.println("7. Get workflow Logs\n");
                    System.out.println("8. Get workflow state\n");
                    System.out.println("9. Get workflow failure state\n");
                    System.out.println("0. Go back\n");
                    Timer timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            List<String> internalNames;
                            try {
                                internalNames = soapWorkflow.postSOAPPausedAndStoppedWKF(sessionToken, securityToken);

                                for (String internalName : internalNames) {
                                    soapWorkflow.checkWorkflowStatus(internalName, sessionToken, securityToken);
                                }
                                System.out.println("\n***************************\n");
                                System.out.println("ALERT: Scan of Workflows ended. Logs are now available!\n");
                                System.out.println("***************************\n");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    }, 600000, 600000);//do it after 600000ms (10 minutes)
                    choice = Integer.parseInt(sc.nextLine());

                    switch (choice) {
                        case 1 -> {
                            System.out.println(workflowInternal);
                            wkInternalName = sc.nextLine();
                            soapWorkflow.postSOAPStartWorkflow(wkInternalName, sessionToken, securityToken);

                            Timer timer_wkf = new Timer();
                            String finalWkInternalName = wkInternalName;
                            timer_wkf.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    List<String> internalNames;
                                    try {
                                        internalNames = soapWorkflow.postSOAPPausedAndStoppedWKF(sessionToken, securityToken);
                                        for (String internalName : internalNames) {
                                            if (internalName.equals(finalWkInternalName)) {
                                                System.out.println("The workflow '" + finalWkInternalName + "' you have started is " +
                                                        "paused or suspended");
                                            }
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                }
                            }, 600000, 600000);//do it after 600000ms (10 minutes)
                            timer_wkf.cancel();
                        }
                        case 2 -> {
                            String stop;
                            ArrayList<String> varName = new ArrayList<>();
                            ArrayList<String> varValue = new ArrayList<>();
                            System.out.println(workflowInternal);
                            wkInternalName = sc.nextLine();
                            do {
                                System.out.println("\nEnter variable:\n");
                                String var = sc.nextLine();
                                varName.add(var);
                                System.out.println("\nEnter value");
                                String value = sc.nextLine();
                                varValue.add(value);
                                System.out.println("\nEnter 'stop' to stop inserting or press any key to add a variable:");
                                stop = sc.nextLine();
                            } while (!stop.equals("stop"));
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
                            System.out.println("\nEnter activity:\n");
                            activity = sc.nextLine();
                            do {
                                System.out.println("\nEnter variable:\n");
                                String variables = sc.nextLine();
                                varName.add(variables);
                                System.out.println("\nEnter value:\n");
                                String value = sc.nextLine();
                                varValue.add(value);
                                System.out.println("\nEnter 'stop' to stop inserting or press any key to add a variable:\n");
                                stop = sc.nextLine();
                            } while (!stop.equals("stop"));
                            soapWorkflow.postSOAPPostEvent(wkInternalName, activity, varName, varValue, sessionToken,
                                    securityToken);
                        }
                        case 4 -> {
                            System.out.println(workflowInternal);
                            wkInternalName = sc.nextLine();
                            soapWorkflow.postSOAPPauseWorkflow(wkInternalName, sessionToken, securityToken);
                        }
                        case 5 -> {
                            System.out.println(workflowInternal);
                            wkInternalName = sc.nextLine();
                            soapWorkflow.postSOAPKillWorkflow(wkInternalName, sessionToken, securityToken);
                        }
                        case 6 -> {
                            System.out.println(workflowInternal);
                            wkInternalName = sc.nextLine();
                            soapWorkflow.postSOAPWakeUpWorkflow(wkInternalName, sessionToken, securityToken);
                        }
                        case 7 -> {
                            System.out.println(workflowInternal);
                            wkInternalName = sc.nextLine();
                            soapWorkflow.postSOAPWorkflowLogs(wkInternalName, sessionToken, securityToken);
                        }
                        case 8 -> {
                            System.out.println(workflowInternal);
                            wkInternalName = sc.nextLine();
                            soapWorkflow.postSOAPWorkflowState(wkInternalName, sessionToken, securityToken);
                        }
                        case 9 -> {
                            System.out.println(workflowInternal);
                            wkInternalName = sc.nextLine();
                            soapWorkflow.postSOAPWorkflowFailed(wkInternalName, sessionToken, securityToken);
                        }
                        default -> System.out.println(defaultMessage);
                    }
                    timer.cancel();//stop the timer
                }

            } while (entry > 0 && entry < 4);
        }else{
            System.out.println("Connexion failed! Login or password incorrect.");
            logger.log(Level.INFO,"Connexion failed! Login or password incorrect.");
        }
        logger.log(Level.INFO, "END SOAP REQUESTS...");
    }
}