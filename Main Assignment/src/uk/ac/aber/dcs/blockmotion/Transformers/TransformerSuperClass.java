package uk.ac.aber.dcs.blockmotion.Transformers;

import uk.ac.aber.dcs.blockmotion.model.IFrame;

/**
 * Implements the transformer and is the abstract version of the subclasses as well as implements
 * the transformer interface.
 * @author Srj12 - Samuel Jones
 * @version 0.1 created on 07/05/2017
 */
public abstract class TransformerSuperClass implements Transformer {

    /**
     * Transform the input frame
     * @param frame the frame to transform
     */
    public void transform(IFrame frame){
        //Do nothing
    }
}
