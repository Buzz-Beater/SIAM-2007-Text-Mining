/**
 FileName: DataItem.java
 
 Description: An instance of this class represents one entry in a label containing all neccessary
 data and/or metadata.
 
 @author: Eugene Turkov, {eturkov@email.arc.nasa.gov}
 @version: 1.0
 
 NASA Ames Research Center
 Computational Sciences Division
 Intelligent Data Understanding Group (IDU)
 Moffett Field, CA 94035
 */
package checker;

/**
    An instance of this class represents one entry in a Label object containing all neccessary
    data and/or metadata.
 */
public class DataItem implements Comparable
{
    
    /**
     Creates a new instance of DataItem.
     
     @param targetValue the target value.
     @param predictedValue the predicted value.
     @param confidence the preciction confidence value.
     
     @throws Exception if targetValue value is neither +1 nor -1 or predictedValue
     is neither +1 nor -1 or confidence is less than zero.
     */
    public DataItem(int targetValue, int predictedValue, double confidence)
    throws Exception
    {
        if(predictedValue != 1 && predictedValue != -1)
            throw new Exception("Predicted values must be +1 or -1");                        
        
        if(targetValue != 1 && targetValue != -1)
            throw new Exception("Target values must be +1 or -1");    
        
        if(confidence < 0)
            throw new Exception("Confidences must be greater than or equal to zero!");        
        
        this.targetValue = targetValue;
        this.predictedValue = predictedValue;               
        this.confidence = confidence;               
        this.predictedConfidence = predictedValue * confidence;
    }
    
    /**
     This method is used to compare this dataItem instance to another object. 
     
     @return -1 if the foreign object's predictedConfidence is greater than this
     object's predictedConfidence. 0 if both predictedConfidence values are equal.
     1 in all other cases.
     
     @throws UnsupportedOperationException if an uncompatible type is compared to
     or if the foreign item is null.   
     */
    public int compareTo(Object object)
    {
        if(!(object instanceof DataItem))
        {
            if(object == null)
                throw new UnsupportedOperationException("Can't compare to a null object!");
            
            throw new UnsupportedOperationException("Can't compare to object of " +
                    " class: " + object.getClass());
        }
        
        DataItem dataItem = (DataItem)object;
        if(dataItem.getPredictedConfidence() == this.predictedConfidence
                && this.targetValue == dataItem.getTargetValue())
            return 0;
        
        else if(dataItem.getPredictedConfidence() == this.predictedConfidence
                && this.targetValue > dataItem.getTargetValue())
            return -1;
        
        else if(dataItem.getPredictedConfidence() == this.predictedConfidence
                && this.targetValue < dataItem.getTargetValue())
            return 1;        
        
        else if(dataItem.getPredictedConfidence() > this.predictedConfidence)
            return -1;
        else
            return 1;
    }
    
    /**
     This method returns this dataItem's targetValue component.
     
     @return the tragetValue of this label.
     */    
    public int getTargetValue()
    {
        return this.targetValue;
    }
    
    /**
     This method returns this dataItem's predictedValue component.
     
     @return the predictedValue component of this label.
     */
    public int getPredictedValue()
    {
        return this.predictedValue;
    }
    
    /**
     This method returns this dataItem's confidence value component.
     
     @return this label's confidence value component.
     */
    public double getConfidence()
    {
        return this.confidence;
    }
    
    /**
     This method returns this dataItem's predicted confidence value.
     
     @return this dataItem's predicted confidence value.
     */
    public double getPredictedConfidence()
    {
        return this.predictedConfidence;
    }
            
    /**
     Use this method to compare this objec to another object.
     
     @return true if both this object and the foreign object have the same
     confidence, predicted confidence, predictedValue, and targetValue; false
     otherwise.
     */
    public boolean equals(Object object)
    {
        if(object instanceof DataItem)
        {
            DataItem foreignDataItem = (DataItem)object;
            if(foreignDataItem.getConfidence() == this.getConfidence()
            && foreignDataItem.getPredictedConfidence() == this.getConfidence()
            && foreignDataItem.getPredictedValue() == this.getPredictedValue()
            && foreignDataItem.getTargetValue() == this.getTargetValue())
                return true;
        }
        
        return false;
    }
    
    private int targetValue;
    private int predictedValue;
    private double confidence;
    private double predictedConfidence;
    
}
