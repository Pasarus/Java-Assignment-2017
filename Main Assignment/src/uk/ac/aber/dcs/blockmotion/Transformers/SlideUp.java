package uk.ac.aber.dcs.blockmotion.Transformers;

import uk.ac.aber.dcs.blockmotion.model.IFrame;

/**
 * Implements the transformer interface and is the subclass of the TransformerSuperClass. For the slide
 * up transformation.
 * @author Srj12 - Samuel Jones
 * @version 0.2 created on 08/05/2017
 */
public class SlideUp extends TransformerSuperClass implements Transformer {
    /**
     * Transform the frame
     * @param frame the frame to transform
     */
    public void transform(IFrame frame){
        //Create new IFrame called newFrame
        //Copy frame into newFrame as a new distinct object
        //for each value of newFrame rows iterate using the value i
        //for each value of newFrame columns iterate using the value j
        //decrease the index of all values on the rows by 1
        //then take the first row and put that at the bottom by taking position 0 from newFrame and set that
        //as the final values of the frame i.e. the last row. Therefore the first row is moved to bottom
        IFrame newFrame = frame.copy();

        //Actually moving all the values down by one row.
        for (int i = 1; i < frame.getNumRows(); i++){
            for (int j = 0; j < frame.getNumRows(); j++){
                newFrame.setChar(i-1, j, frame.getChar(i, j));
            }
        }
        //Put the values of the frame's first row to the end of newFrame
        for (int c = 0; c < frame.getNumRows(); c++){
            newFrame.setChar(frame.getNumRows()-1, c, frame.getChar(0, c));
        }

        //Then copy to the original input object, the values of newFrame exactly
        for (int i = 0; i < frame.getNumRows(); i++){
            for (int j = 0; j < frame.getNumRows(); j++){
                frame.setChar(i, j, newFrame.getChar(i, j));
            }
        }

    }
}
