package uk.ac.aber.dcs.blockmotion.Transformers;

import uk.ac.aber.dcs.blockmotion.model.IFrame;

/**
 * Implements the transformer interface and is the subclass of the TransformerSuperClass. For the slide
 * up transformation.
 * @author Srj12 - Samuel Jones
 * @version 0.2 created on 08/05/2017
 */
public class SlideLeft extends TransformerSuperClass implements Transformer {
    /**
     * transform the input frame
     * @param frame the frame to transform
     */
    public void transform(IFrame frame){
        //Create new IFrame called newFrame
        //Copy frame into newFrame as a new distinct object
        //for each value of newFrame rows iterate using the value i
        //for each value of newFrame columns iterate using the value j
        //Assign the values of every column to a value one lesser and then transfer
        //Right most column of the input frame to the left most column of the newFrame
        //then assign the values to the input frame as an exact copy.
        IFrame newFrame = frame.copy();

        for (int i = 1; i < frame.getNumRows(); i++){
            for (int j = 1; j < frame.getNumRows(); j++){
                newFrame.setChar(i, j - 1, frame.getChar(i, j));
            }
        }

        for (int c = 0; c < frame.getNumRows(); c++){
            newFrame.setChar(c, frame.getNumRows()-1, frame.getChar(c, 0));
        }

        //Then copy the IFrame newFrame into the old object
        for (int i = 0; i < frame.getNumRows(); i++){
            for (int j = 0; j < frame.getNumRows(); j++){
                frame.setChar(i, j, newFrame.getChar(i, j));
            }
        }
    }
}
