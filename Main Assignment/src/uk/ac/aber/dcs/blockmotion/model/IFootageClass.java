package uk.ac.aber.dcs.blockmotion.model;

import uk.ac.aber.dcs.blockmotion.Transformers.TransformerSuperClass;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class implements the IFootage interface
 * @author srj12 - Samuel Jones
 * @version 0.1 created on 02/05/2017.
 */
public class IFootageClass implements IFootage {
    private int numberOfFrames;
    private int numberOfColRow;
    private ArrayList<IFrame> frames = new ArrayList<>();

    /**
     * Returns the number of frames in the footage.
     * @return int - number of frames
     */
    public int getNumFrames(){
        return numberOfFrames;

    }

    /**
     * Returns the number of rows
     * @return int - number of rows
     */
    public int getNumRows(){
        return numberOfColRow;
    }

    /**
     * Returns the frame in the index given
     * @param num the index to retrieve the frame from
     * @return IFrame - frame at the index position
     */
    public IFrame getFrame(int num) {
        return frames.get(num);
    }

    /**
     * Adds the frame to the footage
     * @param f the frame to add
     */
    public void add(IFrame f){
        frames.add(f);
    }

    /**
     * Loads data from the input filename
     * @param filename String - of the filename
     * @throws IOException if the filename doesn't exist
     */
    public void load(String filename) throws IOException{
        IFrame newFrame;
        Scanner infile = new Scanner(new FileReader(filename));
        numberOfFrames = Integer.parseInt(infile.nextLine());
        numberOfColRow = Integer.parseInt(infile.nextLine());
        frames.clear();

        //Add frames
        String[] nextLine = new String[numberOfColRow];
        for (int c = 0; c < numberOfFrames; c++) { //For each frame that needs to be loaded
            newFrame = new IFrameClass();
            for (int i = 0; i < numberOfColRow; i++) { //For each row that needs to be traversed
                nextLine[i] = infile.nextLine();
            }
            newFrame.insertLines(nextLine);
            frames.add(newFrame);
        }
    }

    /**
     * Save data to the input filename
     * @param filename String - of the filename
     * @throws IOException if the filename doesn't exist
     */
    public void save(String filename) throws IOException{
        PrintWriter outfile = new PrintWriter(new FileWriter(filename));
        outfile.println(numberOfFrames);
        outfile.println(numberOfColRow);
        for(int i = 0; i < numberOfFrames; i++){ //Print each frame one after the other
            for(int j = 0; j < numberOfColRow; j++){ //Every row start new line
                for(int c = 0; c < numberOfColRow; c++){ //Print out a row of strings/chars
                    outfile.print(frames.get(i).getChar(j, c)); //Get the frame from the ArrayList and then get the char for the position from that frame
                }
                outfile.println();
            }
        }
        outfile.close();
    }

    /**
     * Transforms the footage using the input transformer
     * @param t the transformer to use on the footage
     */
    public void transform(TransformerSuperClass t){
        //Have the footage act on the transformer that is fed into the method.
        for(int i = 0; i < frames.size(); i++){
            IFrame newFrame = frames.get(i);
            t.transform(newFrame);
            frames.set(i, newFrame);
        }
    }
}
