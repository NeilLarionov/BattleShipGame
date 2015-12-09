/*
 * Programmer: Neil Larionov and Isak Lisoff
 * Chemeketa Community College
 * Class: CIS 234J
 * Assignment: Java 3 final
 * File Name: BattleShip.java
 */

package battleship;

import java.util.Random;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
//import dialog.*;
//import edu.chemeketa.javafx.dialog.*;

/**
 *
 * @author iliso_000
 */

/**
 * This is a BattleShip program. This will create a GUI form of battleship.
 * This class creates multiple 2D arrays that are layered in a parralell form.
 * The program features a text box that you are unable to edit, but is used to
 * log the coordinates and other actions. There is a "Nuke" options in the
 * menu tabs to reveal the entire grid. This is used for debugging purposes
 * during the development process. The labels near the bottom keep track of the
 * number shots total and the number of ships hit.
 */
public class BattleShip extends Application
{
    
    // C R E A T I N  G   V A R I A B L E S
    //////// Arrays /////////
    String[] letter_coor = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};
    String[] number_coor = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
    /////////////////////////
    
    ////// Widgets //////////
    final ComboBox letter_coor_cbox = new ComboBox();
    final ComboBox number_coor_cbox = new ComboBox();
    GridPane mainGrid = new GridPane();
    BorderPane rightPane = new BorderPane();
    BorderPane bottomPane = new BorderPane();
    BorderPane root = new BorderPane();
    VBox box = new VBox();
    MenuBar mainMenu = new MenuBar();
    Menu file = new Menu("File");
    Menu help = new Menu("Help");
    Menu cheats = new Menu("Cheats");
    Button[][]  grid;
    Button fire = new Button("FIRE");
    Label shotslbl = new Label("TEST");
    Label hitslbl = new Label("TEST");
    TextArea console = new TextArea();
    ///////////////////////
    
    ////// Variables /////
    int hits = 0;
    int shots = 0;
    int shot_x;
    int shot_y;
    int aim_x;
    int aim_y;
    int WIDTH = 10;
    int LENGTH = 10;
     Random rand = new Random();
    ///////////////////
    
   ///// Layers of Grid////
    boolean[][] ship;
    boolean[][] shot;
    
   
	
	 // ship layer
    /*
    boolean[][] destroyer_bool;
    boolean[][] aircraft_bool;
    boolean[][] patrol_bool;
    boolean[][] submarine_bool;
    boolean[][] battleship_bool;
    */

   ///////////////////////
   
   
 //////////////////////////////////////////////////////////////////////
 ////////////////////////////S T A R T//////////////////////////////// 
 /////////////////////////////////////////////////////////////////////
    
    @Override
    public  void start(Stage stage)
    {
         
       
         // Menu Bar
         menu();
         
         // ComboBox
         comboBox();
         
         // Button Grid
         buttonGrid();
         
         // Ships
         ship();
  
        // Fire Button 
        fire();
   
        // Coordinate Labels
        coorLabel();
        
        // Text Field
        consoleField();
        
        score();
       
     
        // Sets up the stage and stuff
        //root.setBackground("ocean_c");
        mainGrid.setAlignment(Pos.CENTER);
       // mainGrid.setHgap(2);
         
         mainGrid.add(shotslbl, 14, 9);
        mainGrid.add(hitslbl, 14, 10);
       // mainGrid.setVgap(2);
        root.setId("pane");
        stage.getIcons().add(new Image("Crosshair.png"));
        root.setCenter(mainGrid);
        root.setRight(rightPane);
        root.setBottom(bottomPane);
        Scene scene = new Scene(root, 1000, 600);
        scene.getStylesheets().add("ControlStyle1.css");
        stage.setTitle("BattleShip");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Runs the program
     */
    public static
            void main(String[] args)
    {
        launch(args);
    }
            
    ////////////////////////////////////////////////////////////////////////
    //////////////////////////C O M B O   B O X/////////////////////////////
    ///////////////////////////////////////////////////////////////////////
            
    
	/**
	 * Creates combo boxes used for selecting coordinates.
	 */
	public void comboBox()
    {
         // C R E A T I N G   C O M B O   B O X
    for (int i = 0; i < letter_coor.length; i++)
    {
        // Passing the arrays into the ComboBoxs
        letter_coor_cbox.getItems().add(letter_coor[i]);
        number_coor_cbox.getItems().add(number_coor[i]);
        
    }
    
    // S E T T I N G   S T A R T I N G   V A L U E S
    // Do this so we cant shoot at null coordinates
    letter_coor_cbox.setValue(letter_coor[0]);
    number_coor_cbox.setValue(number_coor[0]);
    
    // A D D I N G   C O M B O  B O X E S   T O   T H E  G R I D   &    L A B E L S
         Label xcoor = new Label("     X-Coordinate   ");
         Label ycoor = new Label("     Y-Coordinate   ");
         
         ycoor.setId("labels");
         xcoor.setId("labels");
         
         mainGrid.add(xcoor, 13, 2);
         mainGrid.add(number_coor_cbox, 14, 2);
         mainGrid.add(ycoor, 13, 3);
         mainGrid.add(letter_coor_cbox, 14, 3);
     
     
     // A C T I O N   L I S T E N E R S   F O R   C O M B O  B O X S
         
     // Letter Coordinate Combo Box
     // Couldnt use lambda cause it didnt like it for some reason
     letter_coor_cbox.setOnAction(new EventHandler<ActionEvent>() 
         {
             @Override
             public void handle(ActionEvent event)
             {
                 redraw();
                 // Looping through the array until we get the index value
                 for (int i = 0; i < letter_coor.length; i++)
                 {
                     
                     if (letter_coor_cbox.getValue() == letter_coor[i])
                     {
                         aim_y = i;
                         
                         //setting ID to aim
                         grid[aim_x][aim_y].setId("aim");
                     }
                 }
                 
             }
         });
     
     // Number Coordinate Combo Box
     // Couldnt use Lambda cause it doesnt like it for some reason
      number_coor_cbox.setOnAction(new EventHandler<ActionEvent>()
     {
         @Override
         public void handle(ActionEvent event)
         {
             redraw();
             // Looping through the array until we get the index value
             for (int i = 0; i < number_coor.length; i++)
             {
                 if (number_coor_cbox.getValue() == number_coor[i])
                 {
                     aim_x = i;
                     
                     // setting ID to aim
                     grid[aim_x][aim_y].setId("aim");
                 }
             }
             
         }
     });
    }
    
    ////////////////////////////////////////////////////////////////////////
    ///////////////////////B U T T O N   G R I D////////////////////////////
    ///////////////////////////////////////////////////////////////////////
    
    
	/**
	 * This is a method used for creating 2D arrays. The forloop then fill the
	 * 2D array with buttons that parrallel the other arrays.
	 */
	public void buttonGrid()
    {
        
        
          // A D D I N G   B U T T O N   G R I D
        
        // Setting up the layers for the button
        grid = new Button[WIDTH][LENGTH];
        ship = new boolean[WIDTH][LENGTH];
        shot = new boolean[WIDTH][LENGTH];
        //destroyer_bool = new boolean[WIDTH][LENGTH];
       // aircraft_bool = new boolean[WIDTH][LENGTH];
       // patrol_bool = new boolean[WIDTH][LENGTH];
      //  submarine_bool = new boolean[WIDTH][LENGTH];
      //  battleship_bool = new boolean[WIDTH][LENGTH];
        
        
        for (int y = 0; y < LENGTH; y++)
        {
            for (int x = 0; x < WIDTH; x++)
            {
      
                grid[x][y] = new Button("");
                //grid[x][y].setMinSize(10, 10);    
                grid[x][y].setPrefWidth(50);
                grid[x][y].setPrefHeight(50);
                grid[x][y].setId("grid"); // Setting the IDs of the buttons to Grid
                mainGrid.add(grid[x][y], x + 1, y + 1);
                ship[x][y] = false;
                shot[x][y] = false;
                
                
                // Setting all the ships to false
               //  destroyer_bool[x][y] = false;
                // aircraft_bool[x][y] = false;
                // patrol_bool[x][y] = false;
               //  submarine_bool[x][y] = false;
               //  battleship_bool[x][y] = false;
            }
        }
        
   
        
        
     // B U T T O N   G R I D  L I S T E N E R S
        for (int y = 0; y < LENGTH; y++)
        {
            for (int x = 0; x < WIDTH; x++)
            {
                // Passing values into variables
                // I did this because it didnt like passing in the value directly from the for loop
                int click_x = x;
                int click_y = y;
                
                grid[x][y].setOnAction((ActionEvent event) ->
                {
                    // SET VALUE OF COMBO BOXES TO COORIDNATES
                    number_coor_cbox.setValue(number_coor[click_x]);
                    letter_coor_cbox.setValue(letter_coor[click_y]);
                    
                    // Setting the aim variables
                    aim_x = click_x;
                    aim_y = click_y;
                    
                    
                    
                   
                });
                
            }
        }
    }
    
    
     ////////////////////////////////////////////////////////////////////////
    ///////////////////////////S H I P /////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////
    
   
	/**
	 * Create ships and then uses a for loop to check a spot that was randomly
	 * selected for availability for how big the ship is. Then the for loop will
	 * continue through the other ships aswell.
	 */
	public void ship()
    {
 //       boolean shipPlaced = false;
 //       boolean goodCoor = false;
 //       ship battleship = new ship("Battleship", 4);
 //       ship destroyer = new ship("Destroyer" , 3);
 //       ship aircraft = new ship("Aircraft Carrier", 5);
 //       ship submarine = new ship("Submarine", 3);
 //       ship patrol = new ship("Patrol Boat" , 2);
 //       
 //       ship[] shipArray;
 //       shipArray = new ship[5];
 //       shipArray[0] = aircraft;
 //       shipArray[1] = battleship;
 //       shipArray[2] = destroyer;
 //       shipArray[3] = submarine;
 //       shipArray[4] = patrol;
 //       
 //       int randomX;
 //       int randomY;
 //       
 //       // S E T T I N G   U P   S H I P S
 //       for (battleship.ship shipArray1 : shipArray)
 //       {   goodCoor = true;
 //           do
 //           {   // Getting the coordinates
 //               randomX = rand.nextInt((WIDTH - shipArray1.getSize()) + 1) + 0;
 //               randomY = rand.nextInt((LENGTH - 0) + 1) + 0;
 //               
 //               for (int i = 0; i < shipArray1.getSize(); i++)
 //               {
 //                   if (ship[randomX + i][randomY] == true)
 //                   {
 //                       goodCoor = false;
 //                   }
 //               }
 //           }
 //           while (goodCoor == true);
 //           
 //           // placing the ships
 //           for (int i = 0; i < shipArray1.getSize(); i++)
 //           {
 //               ship[randomX + i][randomY] = true;
 //               
 //               switch (shipArray1.shipName)
 //               {
 //                   case "Aircraft Carrier":
 //                        aircraft_bool[randomX + i][randomY] = true;
 //                       break;
 //                   case "Destroyer":
 //                       destroyer_bool[randomX + i][randomY] = true;
 //                       break;
 //                   case "Patrol Boat":
 //                        patrol_bool[randomX + i][randomY] = true;
 //                       break;
 //                   case "Submarine":
 //                        submarine_bool[randomX + i][randomY] = true;
 //                       break;
 //                   case "Battleship":
 //                        battleship_bool[randomX + i][randomY] = true;
 //                       break;
 //                   default:
 //                       // something that happened that shouldnt have
 //                       System.out.println("Something went wrong");
 //               }
 //           }
 //       }
 //      

                
                
                
                
        
        //DO Loop
        // Find random numbers
        // for loop
        // check if all spots are availble
        // activate boolean if okay
        // jump out do loop if boolean okay
        
        // place ships
        boolean shipPlaced;
        for (int i = 0; i < 15; i++)
        {
            shipPlaced = false;
            do
            {
                int randomX = rand.nextInt((9 - 0) + 1) + 0;
                int randomY = rand.nextInt((9 - 0) + 1) + 0;
                
                if (ship[randomX][randomY] == false)
                { 
                   ship[randomX][randomY] = true;
                   shipPlaced = true;
                }
            }
            while (shipPlaced == false);
            
           
           
         
            
           
        }
       
    }
    
     ////////////////////////////////////////////////////////////////////////
    ////////////////////////// F I R E //////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////
    
   
	/**
	 * Places the fire button on the mainGrid and hold the action listerner.
	 * When the action listener is activated the forloops run throught the combo
	 * boxes and retrieve the values for both the X and Y. These values are then 
	 * used to activate the selected button and reveal if it is a ship or water.
	 * These buttons are then set ID's so that the CSS file knows what color or 
	 * icon to turn the button into.
	 */
    public void fire()
    {
        fire.setId("fire");
        
         // P L A C I N G   T H E   F I R E   B U T T O N
     mainGrid.add(fire, 14, 4);
     
     
     // L I S T E N I N G   F O R   T H E   F I R E   B U T T O N
     fire.setOnAction((ActionEvent event) ->
        {
            // Passing the value of the comboBoxes into interger variables
            for (int i = 0; i < letter_coor.length; i++) // looping though until values match
            {
                if (letter_coor_cbox.getValue() == letter_coor[i])
                {
                    shot_y = i; // placing Y coordinate
                }
                
                if (number_coor_cbox.getValue() == number_coor[i])
                {
                    shot_x = i; // placing X coordinate
                }
                
            }
            
            
            if (shot[shot_x][shot_y] == false) // if button hasnt been shot at before
            {
                shots++;
                shotslbl.setText("Total Shots: " + shots);
                hitslbl.setText("Total Hit: " + hits);
              
               // System.out.println("You shot at cooridnate: ( " + number_coor_cbox.getValue() + " , " + letter_coor_cbox.getValue() + " )");
                shot[shot_x][shot_y] = true; // setting button to shot at to true
                grid[shot_x][shot_y].setDisable(true); //disabling the button
               
                // Checking if hit
                if (ship[shot_x][shot_y] == true) // It hit a ship
                {
                   // System.out.println("You hit a ship!");
                    hits++;
                    grid[shot_x][shot_y].setOpacity(1);
                    grid[shot_x][shot_y].setId("hit"); //setting ID of button
                      console.appendText("You hit a ship at the cooridnate: ( " + number_coor_cbox.getValue() + " , " + letter_coor_cbox.getValue() + " ) \n");
                    
                    //grid[shot_x][shot_y].setStyle("-fx-background-color: #FF0000; ");
                }
                
                if(ship[shot_x][shot_y] == false) // It missed a ship
                {
					  console.appendText("You shot at cooridnate: ( " + number_coor_cbox.getValue() + " , " + letter_coor_cbox.getValue() + " ) \n");
                    grid[shot_x][shot_y].setId("miss"); // setting ID of button
                    //  grid[shot_x][shot_y].setStyle("-fx-background-color: #2E2EFE; ");
                }
                
                score();
            }
        });
    }
    
    
      ////////////////////////////////////////////////////////////////////////
    ///////////////C O O R D I N A T E S    L A B E L S//////////////////////
    ////////////////////////////////////////////////////////////////////////
    
    
	/**
	 * Creates the lables across the top and sides of the grid. The top lables
     * are numbers and the sides are letters. These coordinates are used to help
	 * help the user determine the coordinate location of a button.

	 */
	public void coorLabel()
    {
        
        // Creating Labels for the Cooridnates
     for (int i = 0; i < letter_coor.length; i++)
     {
       
       Label letter_coor_labels = new Label(letter_coor[i]); // creating the label
       letter_coor_labels.setId("coorLabel"); // setting ID of Label
       mainGrid.add(letter_coor_labels, 0, i + 1); // placing Labels in grid layout
       
       Label number_coor_labels = new Label(number_coor[i]); // creating the label
       number_coor_labels.setId("coorLabel"); // setting ID of label
       mainGrid.add(number_coor_labels, i + 1, 0); // placing Labels in Grid Layout
     }
    
    }
    
      ////////////////////////////////////////////////////////////////////////
    ////////////////////////////M E N U  B A R //////////////////////////////
    ///////////////////////////////////////////////////////////////////////
    
    
	/**
	 * Creates the menu bar across the top of the program. This menu bar houses
	 * a "File", "Cheats", and "About" tab.
	 */
	public void menu()
    {
        // Creating Menu Items
         MenuItem newGame = new MenuItem("_New Game");
         newGame.setAccelerator(KeyCombination.keyCombination("SHORTCUT+N"));
         
         MenuItem exitApp = new MenuItem("_Exit");
         exitApp.setAccelerator(KeyCombination.keyCombination("SHORTCUT+E"));
         
         MenuItem about = new MenuItem("_About");
         about.setAccelerator(KeyCombination.keyCombination("SHORTCUT+A"));
         
         MenuItem nuke = new MenuItem("_Nuke");
         nuke.setAccelerator(KeyCombination.keyCombination("SHORTCUT+Z"));
        
         // Nuke Listener
        nuke.setOnAction((ActionEvent event) ->
         {
             for (int x = 0; x < WIDTH; x++)
             {
                 for (int y = 0; y < LENGTH; y++)
                 {
                     grid[x][y].setDisable(true);
                     shot[x][y] = true;
                     
                     if (ship[x][y] == true)
                     {
                         grid[x][y].setId("hit");
                         hits++;
                     }
                     
                     else
                     {
                         grid[x][y].setId("miss");
                     }
                 }
             }
             
             
         });
        
        // Exit Listener
        exitApp.setOnAction((ActionEvent event) ->
         {
            System.exit(0); 
         });
        
        // New Game Listener
         newGame.setOnAction((ActionEvent event) ->
         {
           // Resets the game   
             restart();
         });
         
        // About Listener
        about.setOnAction((ActionEvent event) ->
         {
             // Make a pop up of the information of the game
             console.clear();
             console.appendText("Created by: Isak Lisoff & Neil Larionov \n How to Play: \n Choose a coordinate by either selecting a button or placing coordinate values with the drop down boxes and hit fire to shoot at that coordinate \n How to Win: Sink all the ships \n Color Code : \n If the square is Blue that means miss,\n if the square is red means hit, \n if the square is yellow that means currently selected square to shoot at.");
         });
         
         
        
         //adding MenuItems to the Menus
         file.getItems().addAll(newGame, exitApp);
         help.getItems().add(about);
         cheats.getItems().add(nuke);
         
         // Adding Menus to MenuBar
         mainMenu.getMenus().addAll(file,cheats, help);
         
         // Adding MenuBar to VBox
         box.getChildren().add(mainMenu);
         
         //set Vbox to top of BorderLayout
         root.setTop(box);
    }
    
    
    /////////////////////////////////////////////////////////
    ////////////////// R E D R A W //////////////////////////
    ////////////////////////////////////////////////////////
    
    
	/**
	 * Redraws the grid to be able to reset the selected button. This prevents
	 * the grid to be filled with a bunch of yellow squares. The for loops go 
	 * through the grid and find the buttons and find the selected button and
	 * resetting its color and ignoring the shot at buttons.
	 */
	public void redraw()
    {
        for (int x = 0; x < WIDTH; x++)
        {
            for (int y = 0; y < LENGTH; y++)
            {
                // Filter Through All the buttons and set the IDs to what they need to be
                if (shot[x][y] == true) // if button was shot at
                {
                    if (ship[x][y] == true) // if it was a hit
                    {
                        grid[x][y].setId("hit"); // set button ID to hit
                    }
                    
                    else // if it was a miss
                    {
                        grid[x][y].setId("miss"); // set button ID to miss
                    }
                    
                } // end of If location has been shot at
                
                else // if button wasnt shot at
                {
                    grid[x][y].setId("grid"); //set button ID to grid
                }
            }
        }
    } 
    
    //////////////////////////////////////////////////////////
    ///////////////// C O N S O L E   F I E L D /////////////
    ////////////////////////////////////////////////////////
    
   
	/**
	 * Creates an uneditable text area to display all output results.
	 */
	public void consoleField()
    {
        console.minHeight(10);
        console.minWidth(10);
        console.prefWidth(10);
        console.prefHeight(10);
        console.setPrefColumnCount(20);
        console.setPrefRowCount(10);
        //console.setDisable(true);
        console.setEditable(false);
        console.setWrapText(true);
       
        console.setId("console");
        
       // console.set
        
        rightPane.setCenter(console);
       // Text field is called "console"
       // Place and size the text field
       // Have other methods Appendto the text field
        // disable the text field
    }
    
    
    ///////////////////////////////////////////////////////////
    /////////////////// R E S T A R T ////////////////////////
    /////////////////////////////////////////////////////////
            
                   
            
 
	/**
	 * This resets the grid of buttons and shot counters. The ship placements
	 * re-randomized and the console display is cleared.
	 */
	public void restart()
    {
        
        
        for (int x = 0; x < WIDTH; x++)
        {
            for (int y = 0; y < LENGTH; y++)
            {
                grid[x][y].setId("grid");
                grid[x][y].setDisable(false);
                ship[x][y] = false;
                shot[x][y] = false;
                
                // Setting all the ships to false
               //  destroyer_bool[x][y] = false;
              //   aircraft_bool[x][y] = false;
               //  patrol_bool[x][y] = false;
              //   submarine_bool[x][y] = false;
              //   battleship_bool[x][y] = false;
                 
                score();
                console.clear();
                shots = 0;
                hits = 0;
            }
        }
        ship();
        // Set all buttons to not shot at and reset ships
        // Random new ships
        // Reset counters
        // reset all buttons to enabled
    }
    
    
    ///////////////////////////////////////////////////////////
    ///////////////////// S C O R E  /////////////////////////
    /////////////////////////////////////////////////////////
    
	/**
	 * These labeles are used to display the shots and total hits and updates
	   them every time a shot is fired.
	 */
	public void score()
    {
     
        shotslbl.setText("Total Shots: " + shots);
        hitslbl.setText("Total Hit: " + hits);
        
        
        shotslbl.setId("score"); 
        hitslbl.setId("score");
       
       
    }
    
}
