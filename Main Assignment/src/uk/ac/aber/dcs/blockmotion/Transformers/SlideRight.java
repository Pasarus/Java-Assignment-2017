package uk.ac.aber.dcs.blockmotion.Transformers;

import uk.ac.aber.dcs.blockmotion.model.IFrame;

/**
 * Implements the transformer interface and is the subclass of the TransformerSuperClass. For the slide
 * right transformation.
 * @author Srj12 - Samuel Jones
 * @version 0.2 created on 08/05/2017
 */
public class SlideRight extends TransformerSuperClass implements Transformer {
    /**
     * transform the input frame
     * @param frame the frame to transform
     */
    public void transform(IFrame frame) {
        //Create new IFrame called newFrame
        //Copy frame into newFrame as a new distinct object
        //for each value of newFrame rows iterate using the value i
        //for each value of newFrame columns iterate using the value j
        //Assign the values of every column to a value one greater and then transfer
        //Right most column of the input frame to the left most column of the newFrame
        //then assign the values to the input frame as an exact copy.
        IFrame newFrame = frame.copy();

        for (int i = 0; i < frame.getNumRows() - 1; i++){
            for (int j = 0; j < frame.getNumRows() - 1; j++){
                newFrame.setChar(i, j + 1, frame.getChar(i, j));
            }
        }

        for (int c = 0; c < frame.getNumRows(); c++){
            newFrame.setChar(c, 0, frame.getChar(c, frame.getNumRows()-1));
        }

        //Then copy the IFrame newFrame into the old object
        for (int i = 0; i < frame.getNumRows(); i++){
            for (int j = 0; j < frame.getNumRows(); j++){
                frame.setChar(i, j, newFrame.getChar(i, j));
            }
        }
    }
}
