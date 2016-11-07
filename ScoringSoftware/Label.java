/**
 FileName: Label.java
 
 Description: An instance of this object represents a label object as specified in the rules.
 
 @author: Eugene Turkov, {eturkov@email.arc.nasa.gov}
 @version: 1.0
 
 NASA Ames Research Center
 Computational Sciences Division
 Intelligent Data Understanding Group (IDU)
 Moffett Field, CA 94035
 */
package checker;
import com.sun.org.apache.bcel.internal.verifier.statics.DOUBLE_Upper;
import java.util.Vector;
import java.util.Arrays;

/**
    An instance of this object represents a label object as specified in the rules.
 */
public class Label
{
    /**
     This method returns the figure value for this label instance as specified in
     in the rules section 10 step 4 as Fj.
     
     @return the figure value of this label Fj.
     */
    public int getFigure()
    {
        if(figure < 0)
        {
            figure = 0;
            for(int i = 0; i < this.size(); i++)
            {
                if(this.get(i).getTargetValue() > 0)
                    figure++;
            }
        }                
        
        return figure;
    }
    
    /**
     This method returns the cost value Q for this label instance as specified in
     the rules section 10 step 1 Qj
     
     @return the cost vlaue of this label Qj.  
     */
    public double getCost()
    {
        if(cost == Double.NEGATIVE_INFINITY)
        {
            cost = 0;
            for(int i = 0; i < this.size(); i++)        
                cost += this.get(i).getPredictedConfidence() * this.get(i).getTargetValue();        
            
            cost /= this.size();     
            cost += 2 * this.getArea() - 1;
        }   

        return cost;
    }    
        
    private void reset()
    {
        this.cost = Double.NEGATIVE_INFINITY;
        this.area = Double.NEGATIVE_INFINITY;
        this.figure = -1; 
    }
    
    /**     
        Here, ROC is used to generate a summary statistic.
     
        visit: <a href="http://en.wikipedia.org/wiki/ROC_curve">wikipedia</a>
        for more information.
     
        @return the area under the ROC curve (AUC) for this label object.
     */
    public double getArea()
    {
        if(area != Double.NEGATIVE_INFINITY)
            return area;
        
        area = 0;
        
        int[] truePositives = new int[this.size()];
        int[] falsePositives = new int[this.size()];
        int positiveCount = 0;
        int negativeCount = 0;
        
        for(int i = 0; i < this.size(); i++)
        {
            if(this.get(i).getTargetValue() == 1)           
                positiveCount++;
                      
            else
                negativeCount++;

            truePositives[i] = positiveCount;
            falsePositives[i] = negativeCount;
        }

        double truePositiveRate = 0;
        double falsePositiveRate = 0;
        double nextFalsePositiveRate = 0;
        double previousTruePositiveRate = 0;        
        
        for(int i = 0; i < this.size() - 1; i++)
        {
            truePositiveRate = truePositives[i + 1] / (double)positiveCount;

            nextFalsePositiveRate = falsePositives[i + 1] / (double)negativeCount;
            area += (truePositiveRate)
                * (nextFalsePositiveRate - falsePositiveRate);
            
            double temp = (truePositiveRate)
                * (nextFalsePositiveRate - falsePositiveRate);

            falsePositiveRate = nextFalsePositiveRate;
            previousTruePositiveRate = truePositiveRate;
        }

        return area;
    }
    
    /**
     * Creates a new instance of Label
     */
    public Label()
    {
        reset();
        collection = new Vector();
    }
    
    /**
     This method returns the total number of dataItems in this label.
     
     @return the total number of dataItems in this label object.
     */
    public int size()
    {
        return collection.size();
    }
    
    /**
     Use this method to add dataItem objects to this label object.
     
     @param dataItem a valid dataItem object to add to this label.
     */
    public void add(DataItem dataItem)
    {
        reset();
        collection.add(dataItem);
    }
    
    /**
     This method returns this label's dataItem at index i.
     
     @return a dataItem.
     */
    public DataItem get(int i)
    {
        return (DataItem)collection.get(i);
    }
    
    private void clear()
    {
        reset();
        collection.clear();
    }        

    public boolean equals(Object object)
    {
        return collection.equals(object);
    }        
    
    private Vector collection;   
    private double area;
    private double cost;
    private int figure;
}
