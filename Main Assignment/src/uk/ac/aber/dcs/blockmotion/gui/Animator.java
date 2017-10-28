package uk.ac.aber.dcs.blockmotion.gui;

/**
 * This was the template Animator to display the animation. Fully working
 * and operational.
 *
 * @author Chris Loftus  Samuel Jones
 * @version 2.0 (10th May 2017)
 */

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import uk.ac.aber.dcs.blockmotion.Transformers.*;
import uk.ac.aber.dcs.blockmotion.model.IFootage;
import uk.ac.aber.dcs.blockmotion.model.IFootageClass;
import uk.ac.aber.dcs.blockmotion.model.IFrame;

import java.io.IOException;
import java.util.Scanner;

import static java.lang.System.exit;

public class Animator extends Application {

    private Button[][] gridArray;
    private GridPane grid;
    private Thread animation;
    private boolean doRun;
    private IFootage footage = new IFootageClass();
    private Stage stage;
    private Scene scene;


    //Added a scanner to the class for ease of use in multiple methods
    private Scanner scanner = new Scanner(System.in);

    //Tracking for the times it has been moved
    private int numberRight = 0;
    private int numberLeft = 0;
    private int numberUp = 0;
    private int numberDown = 0;

    /**
     * This is the main method it runs everything
     * @param args the arguments from the console
     */
    public static void main(String[] args) {
        // This is how a javafx class is run.
        // This will cause the start method to run.
        // You don't need to change main.
        launch(args);
    }

    // This is the javafx main starting method. The JVM will run this
    // inside an object of this class (Animator). You do not need to
    // create that object yourself.

    /**
     * The JavaFX main starting method
     * @param primaryStage the stage to be used
     */
    @Override
    public void start(Stage primaryStage) {

        // The Stage is where we place GUI stuff, such as a GridPane (see later).
        // I'll look more at this after Easter, but please read the
        // following comments
        stage = primaryStage;

        // In this version of the app we will drive
        // the app using a command line menu.
        // YOU ARE REQUIRED TO IMPLEMENT THIS METHOD
        runMenu();

        // This is how we can handle a window close event. I will talk
        // about the strange syntax later (lambdas), but essentially it's a Java 8
        // was of providing a callback function: when someone clicks the
        // close window icon then this bit of code is run, passing in the event
        // object that represents the click event.
        primaryStage.setOnCloseRequest((event) -> {
            // Prevent window from closing. We only want quitting via
            // the command line menu.
            event.consume();

            System.out.println("Please quit the application via the menu");
        });
    }

    private void runMenu() {
        // The GUI runs in its own thread of control. Here
        // we start by defining the function we want the thread
        // to call using that Java 8 lambda syntax. The Thread
        // constructor takes a Runnable

        Runnable commandLineTask = () -> {

            // YOUR MENU CODE GOES HERE
            String selection;
            String filename = "footage.txt";

            do{
                printMenu();
                selection = scanner.nextLine();
                selection = selection.toUpperCase();
                switch(selection){
                    case "L":
                        //Load footage file
                        System.out.println("Which file do you want to load from?");
                        filename = scanner.nextLine();
                        try{
                            footage.load(filename);
                        }
                        catch(IOException e){
                            System.out.println("Loading file footage failed, no file called" + filename);
                        }
                        break;
                    case "S":
                        //Save footage file
                        try{
                            footage.save(filename);
                        }
                        catch(IOException e){
                            //It shouldn't actually get to this point as it will just create the file.
                            System.out.println("Saving file footage failed, no file called" + filename);
                        }
                        break;
                    case "SA":
                        //Save as footage file
                        System.out.println("What to call new file? (Don't forget the .txt)");
                        filename = scanner.nextLine();
                        try{
                            footage.save(filename);
                        }
                        catch(IOException e){
                            //It shouldn't actually get to this point as it will just create the file.
                            System.out.println("Saving file footage failed, no file called" + filename);
                        }
                        break;
                    case "A":
                        //Run footage animation
                        try{
                            createGrid(); //What I have found is that if I input an int to change the size of the grid it fails to run.
                            runAnimation();
                        }catch(IndexOutOfBoundsException e){
                            System.out.println("Try loading something first!");
                        }
                        break;
                    case "T":
                        //Stop footage animation
                        terminateAnimation();
                        break;
                    case "E":
                        //Edit current footage
                        //Run using a method submenu
                        editMenu();
                        break;
                }
            }while(!selection.equals("Q"));
            System.out.println("Do you want to save before you exit (y/n)");
            selection = scanner.nextLine();
            if(selection == "y"){
                try {
                    footage.save(filename);
                }catch(IOException e){}
            }
            Platform.exit();
            //Cause the application to finish here, after the menu's execution.
            exit(0);
            // At some point you will call createGrid.
            // Here's an example
            //createGrid();

        };
        Thread commandLineThread = new Thread(commandLineTask);
        // This is how we start the thread.
        // This causes the run method to execute.
        commandLineThread.start();

        // You can stop javafx with the command
        // Platform.exit();
    }

    private void printMenu(){
        System.out.println("l - Load footage file \n s - Save footage file \n sa - Save as footage file \n a - Run footage animation \n t - Stop footage animation \n e - Edit current footage \n q - Quit \n Enter Option:");
    }

    //The edit menu stuck elsewhere for ease of editing.
    private void editMenu(){
        String editSelection;

        TransformerSuperClass transformer = new SlideLeft();

        do {
            printEditMenu();

            editSelection = scanner.nextLine();
            editSelection = editSelection.toUpperCase();


            switch (editSelection) {
                case "FH":
                    //Flip horizontal
                    transformer = new FlipHorizontal();
                    footage.transform(transformer);
                    break;
                case "FV":
                    //Flip vertical
                    transformer = new FlipVertical();
                    footage.transform(transformer);
                    break;
                case "SL":
                    //Slide left
                    transformer = new SlideLeft();
                    footage.transform(transformer);
                    if(numberRight > 0) {
                        numberRight -= 1;
                    }
                    else{
                        numberLeft += 1;
                    }
                    break;
                case "SR":
                    //Slide right
                    transformer = new SlideRight();
                    footage.transform(transformer);
                    if(numberLeft > 0){
                        numberLeft -= 1;
                    }
                    else{
                        numberRight += 1;
                    }
                    break;
                case "SU":
                    //Slide up
                    transformer = new SlideUp();
                    footage.transform(transformer);
                    if(numberDown > 0){
                        numberDown -= 1;
                    }else{
                        numberUp += 1;
                    }
                    break;
                case "SD":
                    //Slide down
                    transformer = new SlideDown();
                    footage.transform(transformer);
                    if(numberUp > 0){
                        numberUp -= 1;
                    }else{
                        numberDown += 1;
                    }
                    break;
                case "R":
                    //Repeat last operation
                    footage.transform(transformer);
                    //Check what the class of transformer is and see whether or not we need to change the numbers moved variables.
                    if(SlideLeft.class.isInstance(transformer)){
                        if(numberRight > 0) {
                            numberRight -= 1;
                        }
                        else{
                            numberLeft += 1;
                        }
                    }
                    else if(SlideRight.class.isInstance(transformer)){
                        if(numberLeft > 0){
                            numberLeft -= 1;
                        }
                        else{
                            numberRight += 1;
                        }
                    }
                    else if(SlideUp.class.isInstance(transformer)){
                        if(numberDown > 0){
                            numberDown -= 1;
                        }else{
                            numberUp += 1;
                        }
                    }
                    else if(SlideDown.class.isInstance(transformer)){
                        if(numberUp > 0){
                            numberUp -= 1;
                        }else{
                            numberDown += 1;
                        }
                    }
                    break;
            }
        }while(!editSelection.equals("Q"));

    }

    //Reduce the size of the edit menu method, by using this instead.
    private void printEditMenu(){
        System.out.println("fh - Flip horizontal \n fv - Flip vertical \n sl - Slide left \n sr - Slide right \n su - Slide up \n sd - Slide down \n nr - Slide number right. Currently=" + numberRight + "\n nl - Slide number left. Currently=" + numberLeft + "\n nd - Slide number down. Currently=" + numberDown + "\n nu - Slide number up. Currently=" + numberUp + "\n r - Repeat last operation (Default slide left) \n q - Quit (Return to main menu)");
    }

    // An example method that you might like to call from your
    // menu. Whenever you need to do something in the GUI thread
    // from another thread you have to call Platform.runLater
    // This is a javafx method that queues your code ready for the GUI
    // thread to run when it is ready. We use that strange lambda Java 8 syntax
    // again although this time there are no parameters hence ()
    private void terminateAnimation() {
        doRun = false;
    }

    // Here is another example. This one is useful because it creates
    // the GUI grid. You will need to call this from the menu, e.g. when starting
    // an animation.
    private void createGrid() {
        Platform.runLater(() -> {

            // Update UI here
            //createGrid(numRows);   // E.g. let's create a 20 x 20 grid
            createGrid(20);
        });
    }

    // I'll give you this method since I haven't done
    // much on javafx. This also creates a scene and adds it to the stage;
    // all very theatrical.
    private void createGrid(int numRows) {
        // Creates a grid so that we can display the animation. We will see
        // other layout panes in the lectures.
        grid = new GridPane();

        // We need to create a 2D array of javafx Buttons that we will
        // add to the grid. The 2D array makes it much easier to change
        // the colour of the buttons in the grid; easy lookup using
        // 2D indicies. Note that we make this read-only for this display
        // onlt grid. If you go for flair marks, then I'd imagine that you
        // could create something similar that allows you to edits frames
        // in the footage.
        gridArray = new Button[numRows][numRows];
        Button displayButton = null;
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numRows; col++) {  // The display is square
                displayButton = new Button();
                gridArray[row][col] = displayButton;
                displayButton.setDisable(true);
                grid.add(displayButton, col, row);
            }
        }

        // Create a scene to hold the grid of buttons
        // The stage will "shrink wrap" around the grid of buttons,
        // so we don't need to set its height and width.
        scene = new Scene(grid);
        stage.setScene(scene);
        scene.getStylesheets().add(Animator.class.getResource("styling.css").toExternalForm());

        // Make it resizable so that the window shrinks to fit the scene grid
        stage.setResizable(true);
        stage.sizeToScene();
        // Raise the curtain on the stage!
        stage.show();
        // Stop the user from resizing the window
        stage.setResizable(false);
    }

    // I'll also give you this one too. This does the animation and sets colours for
    // the grid buttons. You will have to call this from the menu. You should not
    // need to change this code, unless you want to add more colours
    private void runAnimation() {
        if (footage == null) {
            System.out.println("Load the footage first");
        } else {
            Runnable animationToRun = () -> {

                Background yellowBg = new Background(new BackgroundFill(Color.YELLOW, CornerRadii.EMPTY, Insets.EMPTY));
                Background blackBg = new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY));
                Background blueBg = new Background(new BackgroundFill(Color.BLUE, CornerRadii.EMPTY, Insets.EMPTY));
                Background whiteBg = new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY));

                doRun = true;
                int numFrames = footage.getNumFrames();
                int currentFrameIndex = 0;
                Background bck = null;
                while (doRun) {
                    if (currentFrameIndex >= numFrames - 1) currentFrameIndex = 0;
                    IFrame currentFrame = footage.getFrame(currentFrameIndex);
                    // Iterate through the current frame displaying appropriate colour
                    for (int row = 0; row < footage.getNumRows(); row++) {
                        for (int col = 0; col < footage.getNumRows(); col++) {
                            switch (currentFrame.getChar(row, col)) {
                                case 'l':
                                    bck = yellowBg;
                                    break;
                                case 'r':
                                    bck = blackBg;
                                    break;
                                case 'b':
                                    bck = blueBg;
                                    break;
                                default:
                                    bck = whiteBg;
                            }
                            final int theRow = row;
                            final int theCol = col;
                            final Background backgroundColour = bck;
                            // This is another lambda callback. When the Platform
                            // GUI thread is ready it will run this code.
                            Platform.runLater(() -> {

                                // Update UI here
                                // We have to make this request on a queue that the GUI thread
                                // will run when ready.
                                gridArray[theRow][theCol].setBackground(backgroundColour);
                            });

                        }
                    }
                    try {
                        // This is how we delay for 200th of a second
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                    }
                    currentFrameIndex++;
                }
            };
            animation = new Thread(animationToRun);
            animation.start();
        }
    }
}

