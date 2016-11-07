/**
 FileName: Checker.java
 
 Description: This class contains the main method and should not need to be instantiated.
 
 @author: Eugene Turkov, {eturkov@email.arc.nasa.gov}
 @version: 1.0
 
 NASA Ames Research Center
 Computational Sciences Division
 Intelligent Data Understanding Group (IDU)
 Moffett Field, CA 94035
 */
package checker;
import java.util.Arrays;
import turkov.io.TUCSVFile;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import turkov.lang.TUStringBuilder;

       
/**
    This class contains the main method and should not need to be instantiated.
 */
public abstract class Checker
{
    /**
     This method is called when it is neccessary to print application useage.
     
     @param errorMessage the error message that will be displayed if this method
     deems neccessary.
     */
    private static void printUseage(String errorMessage)
    {
        println();
        println(errorMessage);
        println();
        println("Useage Example: ");
        println("java -jar Checker.jar -t targetValuesCSVFile -p predictionValuesCSVFile " +
                "-c predictionConfidenceCSVFile");
        println("");
    }    
    
    /**
     This method's sole purpose is to take the input arguments and break them up
     amongs their appropriate variables as neccessary.
     
     @param args the input arguments
     @throws IOException if there are any problems performing IO
     @throws Exception if there are any other unforseen problems.
     */
    private static void processInputArguments(String[] args)
    throws IOException, Exception
    {
        if(args.length == 0)
        {
            printUseage("ERROR: No input arguments detected!");
            System.exit(1);
        }
        
        for(int i = 0; i < args.length; i++)
        {
            if(args[i].startsWith("-"))
            {
                if(args[i].length() != 2)
                {
                    printUseage("Unrecognized argument enetered.");
                    System.exit(1);
                }
                                
                if(args[i].toLowerCase().charAt(1) == 't')
                {
                    if(args.length - 1 < i + 1)
                    {
                        printUseage(args[i] + " not followed by input argument.");
                        System.exit(1);
                    }
                    
                    else
                    {
                        if(targetValuesCSVFile != null)
                            throw new Exception("targetValuesCSVFile has already been specified!");
                            
                        targetValuesCSVFile = new TUCSVFile(new File(args[i + 1]));
                        if(!targetValuesCSVFile.exists())
                        {
                            println("The specified file " +
                                    targetValuesCSVFile.getAbsolutePath() + " does not exist.");
                            System.exit(1);
                        }
                        
                        else if(targetValuesCSVFile.isDirectory())
                        {
                            println("You specified a directory for input. This" +
                                    " feature is not yet supported.");
                            System.exit(1);
                        }
                        
                        else
                        {
                            println("Using target values from file: "
                                    + targetValuesCSVFile.getAbsolutePath());                            
                        }

                        i++;
                    }
                    
                }
                              
                else if(args[i].toLowerCase().charAt(1) == 'p')
                {
                    if(args.length - 1 < i + 1)
                    {
                        printUseage(args[i] + " not followed by input argument.");
                        System.exit(1);
                    }
                    
                    else
                    {
                        if(predictedValuesCSVFile != null)
                            throw new Exception("predictedValuesCSVFile has already been specified!");
                        
                        predictedValuesCSVFile = new TUCSVFile(new File(args[i + 1]));
                        if(!predictedValuesCSVFile.exists())
                        {
                            println("The specified file " +
                                    predictedValuesCSVFile.getAbsolutePath() + " does not exist.");
                            System.exit(1);
                        }
                        
                        else if(predictedValuesCSVFile.isDirectory())
                        {
                            println("You specified a directory for input. This" +
                                    " feature is not yet supported.");
                            System.exit(1);
                        }
                        
                        else
                        {
                            println("Using predicted values from file: "
                                    + predictedValuesCSVFile.getAbsolutePath());                            
                        }

                        i++;
                    }
                    
                }
                
                else if(args[i].toLowerCase().charAt(1) == 'c')
                {
                    if(args.length - 1 < i + 1)
                    {
                        printUseage(args[i] + " not followed by input argument.");
                        System.exit(1);
                    }
                    
                    else
                    {
                        if(predictionConfidenceCSVFile != null)
                            throw new Exception("predictionConfidenceCSVFile has already been specified!");
                                                
                        predictionConfidenceCSVFile = new TUCSVFile(new File(args[i + 1]));                                                
                        
                        if(!predictionConfidenceCSVFile.exists())
                        {
                            println("The specified file " +
                                    predictionConfidenceCSVFile.getAbsolutePath() + " does not exist.");
                            System.exit(1);
                        }
                        
                        else if(predictionConfidenceCSVFile.isDirectory())
                        {
                            println("You specified a directory for input. This" +
                                    " feature is not yet supported.");
                            System.exit(1);
                        }
                        
                        else
                        {
                            println("Using prediction confidence information from file: "
                                    + predictionConfidenceCSVFile.getAbsolutePath());                            
                        }

                        i++;
                    }                    
                }    
                
                else if(args[i].toLowerCase().charAt(1) == 'd')
                {
                    if(debugMode)
                        throw new Exception("debug mode has already been requested!");
                    debugMode = true;
                }                 
                
                else
                {
                    
                    printUseage("Unrecognized argument entered: " + args[i]);
                    System.exit(1);
                }                                
            }                      
        }
    }
        
    private static void println(String s)
    {
        print(s);
        println();
    }
    
    private static void println()
    {
        print(System.getProperty("line.separator"));
    }
    
    private static void print(String s)
    {
        System.err.print(s);
        System.err.flush();
    }    
    
    /**
     This method computes the figure for a given collection of labelItems. Figure
     is denoted as "F" in the formula given in the rules. Check step (4) under
     the cost function section.
     
     @param labelItems the collection of labeles.
     @return the figure (as described in the rules section 10 step 4).
     */
    public static int getFigure(Label[] labelItems)
    {
        int figure = 0;
        for(int i = 0; i < labelItems.length; i++)
        {
            figure += labelItems[i].getFigure();
        }
        
        return figure;
    }
    
    /**
     This method computes the figure of merit
     (FOM as described in the rules section 10 step 4).
     
     @param labelItems the collection of labeles.
     @return the figure of merit (FOM)
     */    
    public static double getFigureOfMerit(Label[] labelItems)
    {
        double figureOfMerit = 0;
        double figure = getFigure(labelItems);
        for(int i = 0; i < labelItems.length; i++)
        {
            figureOfMerit += (((figure - labelItems[i].getFigure()) / figure)
                * labelItems[i].getCost()) / labelItems.length;      
        }
        
        return figureOfMerit;
    }
    
    /**
     This method computes the total cost (Q as described in the rules section 10 step 2).
     
     @param labelItems the collection of labeles.
     @return the value of Q.
     */
    public static double getTotalCost(Label[] labelItems)
    {
        double totalCost = 0;
        
        for(int i = 0; i < labelItems.length; i++)
            totalCost += labelItems[i].getCost();

        return totalCost / labelItems.length;
    }

    public static void main(String[] args)    
    {
        try
        {  
            processInputArguments(args);

            if(predictedValuesCSVFile == null)
            {
                printUseage("Please specify the predicted values CSV file by using the" +
                        " -p option.");
                System.exit(1);
            }
            
            if(targetValuesCSVFile == null)
            {
                printUseage("Please specify the target values CSV file by using the" +
                        " -t option.");
                System.exit(1);
            }
            
            if(predictionConfidenceCSVFile == null)
            {
                printUseage("Please specify the prediction confidence CSV file by using the" +
                        " -c option.");
                System.exit(1);
            }            
            
            // instantiate the collections            
            int totalNumberOfLabeles = predictedValuesCSVFile.getColumns();
            int totalNumberOfDocuments = predictedValuesCSVFile.getRows();
            
            Label[] labelItems = new Label[totalNumberOfLabeles];
            for(int i = 0; i < labelItems.length; i++)
            {
                labelItems[i] = new Label();
                DataItem[] tempLabelItem = new DataItem[totalNumberOfDocuments];
                for(int j = 0; j < totalNumberOfDocuments; j++)
                {                    
                    tempLabelItem[j] = new DataItem(targetValuesCSVFile.getInteger(j, i).intValue(),
                            predictedValuesCSVFile.getInteger(j, i).intValue(),
                            predictionConfidenceCSVFile.getDouble(j, i).doubleValue());                    
                }                              
                
                
                Arrays.sort(tempLabelItem);
                
                for(int j = totalNumberOfDocuments - 1; j >= 0; j--)     
                    labelItems[i].add(tempLabelItem[j]);                                

            }      

            double totalCost = getTotalCost(labelItems);
            double figureOfMerit = getFigureOfMerit(labelItems);                        
            
            System.out.println("The figure of merit is: " + TUStringBuilder.format(figureOfMerit, 6, 8));
            System.out.println("The total cost is: " + TUStringBuilder.format(totalCost, 6, 8));
            
            if(debugMode)
            {
                for(int i = 0; i < labelItems.length; i++)
                {
                    println("For label " + i + " the area under the ROC curve (A) is:" +
                            " " + labelItems[i].getArea() + " the cost(Q) is: "
                            + labelItems[i].getCost() + " and the figure of merit" +
                            " (F) is: " + labelItems[i].getFigure());
                    
                    if(i + 1 < labelItems.length)
                            println();
                }
            }
        }
        
        catch(Throwable t)
        {
            println();
            println("ERROR: " + t.getMessage());
            t.printStackTrace();
        }
    }        
    
    private static boolean debugMode;
    private static TUCSVFile predictedValuesCSVFile;
    private static TUCSVFile targetValuesCSVFile;
    private static TUCSVFile predictionConfidenceCSVFile;
}
