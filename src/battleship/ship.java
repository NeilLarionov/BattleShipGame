/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship;

/**
 *
 * @author Neil
 */
public class ship 
{
    int shipSize;
    String shipName;
    int hits = 0;
    public ship(String name, int size)
    {
         shipName = name;
         shipSize = size;
    }
    
    public int getSize()
    {
        return shipSize;
    }
    
    public String getName()
    {
        return shipName;
    }
    
    public void sunk()
    {
        System.out.println(shipName + " has been sunk!");
    }
    
    public void hit()
    {
        hits++;
        if (hits == shipSize) 
        {
            sunk();
        }
    }
}


