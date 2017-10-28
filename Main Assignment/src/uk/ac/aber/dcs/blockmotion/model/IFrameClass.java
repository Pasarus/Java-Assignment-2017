package uk.ac.aber.dcs.blockmotion.model;

import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * This class implements the IFrame interface.
 * @author Srj12 - Samuel Jones
 * @version 0.2 created on 02/05/2017
 */
public class IFrameClass implements IFrame {

    private ArrayList<ArrayList<String>> frame = new ArrayList<>();

    /**
     * The constructor for this class, which is blank but utilizes the instance variables.
     */
    public IFrameClass(){
    }

    /**
     * Inserts the lines from the array of strings into the frame, at the end of the frame.
     * @param lines an array of lines
     */
    public void insertLines(String lines[]){
        for(String C: lines){ //For each String in addline
            ArrayList<String> addLine = new ArrayList<>();
            for(int j = 0; j < C.length(); j++){ //Iterate over each char and add it to a new string
                addLine.add(String.valueOf(C.charAt(j)));
            }
            frame.add(addLine);
        }
    }

    /**
     * Returns the number of rows
     * @return int - number of rows
     */
    public int getNumRows() {
        return frame.size();
    }

    /**
     * Saves the frame to a file
     * @param outfile the open file to write to
     */
    public void tofile(PrintWriter outfile){
        for(int c = 0; c < frame.size(); c++){
            outfile.println();
            for(int i = 0; i < frame.size(); i++){
                outfile.print(frame.get(c).get(i));
            }
        }
        outfile.close();
    }

    /**
     * set the char at the position given in row and col to the char ch
     * @param row the row or arraylist(string) to edit
     * @param col the position in the arraylist(string) to edit.
     * @param ch the character to set
     */
    public void setChar(int row, int col, char ch){
        frame.get(row).set(col, String.valueOf(ch));
    }

    /**
     * Returns the char at the position given
     * @param row the arraylist(string) to access
     * @param col the position in the arraylist(string) to return
     * @return char - the char at the position given
     */
    public char getChar(int row, int col){
        return frame.get(row).get(col).charAt(0);
    }

    /**
     * Copies the frame as an exact copy as a distinct object
     * @return IFrame - as a distinct new object
     */
    public IFrame copy(){
        //Create initial frame
        IFrame newFrame = new IFrameClass();
        //Create a blank frame to be edited later
        //Create a blank array of strings to the correct size.
        String[] blankLines = new String[this.getNumRows()];
        for (int i = 0; i < this.getNumRows(); i++){
            String newLine = "b";
            for (int j = 1; j < this.getNumRows(); j++){
                newLine += "b";
            }
            blankLines[i] = newLine;
        }
        //Insert the string array into the frame to create the frame as a blank frame
        newFrame.insertLines(blankLines);

        //Then copy all the details of the current object frame into this new frame
        for (int i = 0; i < this.getNumRows(); i++){
            for (int j = 0; j < this.getNumRows(); j++){
                newFrame.setChar(i, j, this.getChar(i, j));
            }
        }
        //Then return this new object to be assigned to something when used.
        return newFrame;
    }

    /**
     * replaces the current frame with the given frame
     * @param f the frame to insert
     */
    public void replace(IFrame f){
        frame.clear();
        for(int i = 0; i < f.getNumRows(); i++){ //Line of a frame
            for(int j = 0; j < f.getNumRows(); i++){ //index of second array list.
                frame.get(i).set(j, String.valueOf(f.getChar(i, j)));
            }
        }
    }
}
