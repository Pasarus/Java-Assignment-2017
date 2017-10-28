package uk.ac.aber.dcs.blockmotion.Transformers;

import uk.ac.aber.dcs.blockmotion.model.IFrame;

/**
 * Implements the transformer interface and is the subclass of the TransformerSuperClass. For the slide
 * down transformation.
 * @author Srj12 - Samuel Jones
 * @version 0.2 created on 08/05/2017
 */
public class SlideDown extends TransformerSuperClass implements Transformer {
    /**
     * transform the input frame
     * @param frame the frame to transform
     */
    public void transform (IFrame frame){
        //Create new IFrame called newFrame
        //Copy frame into newFrame as a new distinct object
        //for each value of newFrame rows iterate using the value i
        //for each value of newFrame columns iterate using the value j
        //decrease the index of all values on the rows by 1
        //then take the last row and put that at the top by taking frame.getNumRows() + 1 from newFrame and set that
        //as the last values of the frame i.e. the first row. Therefore the last row is moved to the top
        IFrame newFrame = frame.copy();

        //Actually moving all the values down by one row.
        for (int i = 0; i < frame.getNumRows()-1; i++){
            for (int j = 0; j < frame.getNumRows(); j++){
                newFrame.setChar(i+1, j, frame.getChar(i, j));
            }
        }
        //Put the values of the frame's last row to the start of newFrame
        for (int c = 0; c < frame.getNumRows(); c++){
            newFrame.setChar(0, c, frame.getChar(frame.getNumRows()-1, c));
        }

        //Then copy to the original input object, the values of newFrame exactly
        for (int i = 0; i < frame.getNumRows(); i++){
            for (int j = 0; j < frame.getNumRows(); j++){
                frame.setChar(i, j, newFrame.getChar(i, j));
            }
        }
    }
}
