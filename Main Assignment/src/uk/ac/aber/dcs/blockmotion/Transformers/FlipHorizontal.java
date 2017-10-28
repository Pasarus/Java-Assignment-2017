package uk.ac.aber.dcs.blockmotion.Transformers;

import uk.ac.aber.dcs.blockmotion.model.IFrame;

/**
 * Implements the transformer interface and is the subclass of the TransformerSuperClass. For the flip
 * horizontal transformation.
 * @Author Srj12 - Samuel Jones
 * @Version 0.2 created on 08/05/2017
 */
public class FlipHorizontal extends TransformerSuperClass implements Transformer {
    /**
     * transform the input frame
     * @param frame the frame to transform
     */
    public void transform(IFrame frame){
        //Declare new IFrame newFrame
        //make the newFrame the same size as the old frame by copying over the the frame.
        //Then for each row of the frame we need to iterate over the value i
        //then for each value in the columns of the frame in the double nested arraylist we need to iterate over the value j
        //then we set the newFrame value of index i with the column of ColRow - j with the value of initial frame
        //then overwrite the older frame with the newFrame
        IFrame newFrame = frame.copy();
        for (int i = 0; i < newFrame.getNumRows(); i++){
            for (int j = 0; j < newFrame.getNumRows(); j++){
                newFrame.setChar(i, (newFrame.getNumRows() - j - 1), frame.getChar(i, j));
            }
        }
        //Then we need to copy exactly to the original object
        for (int c = 0; c < newFrame.getNumRows(); c++){
            for (int k = 0; k < newFrame.getNumRows(); k++){
                frame.setChar(c, k, newFrame.getChar(c, k));
            }
        }
        //Then the originally fed in object is changed itself, because it is fed in as a pointer.
    }
}
