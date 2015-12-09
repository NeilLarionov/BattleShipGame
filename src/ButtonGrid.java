/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author iliso_000
 */
public class ButtonGrid
{
    Button[][]  grid;
    GridPane mainGrid = new GridPane();
   
    Scene scene = new Scene(mainGrid, 400,400);
    
    public ButtonGrid(int width, int length)
    {
        grid = new Button[width][length];
        for (int y = 0; y < length; y++)
        {
            for (int x = 0; x < width; x++)
            {
                grid[x][y] = new Button("(" + x + "," + y + ")");
                mainGrid.add(grid[x][y], x, y);
            }
        }
    }
    
   
    
}
