import kwm.Drawing;
import kwm.graphics.CellMatrix;

/*-----------------------------------------------------------------------------
 * CellMatrix.java
 * Author: Hanna Rodler
 * Date: 21.04.2021
 * Task: OOP Aufgabe 5
 *
 * This class is solely for testing CellMatrix.java. It executes the
 * so-called "Game of Life".
 * --------------------------------------------------------------------------*/
public class GameOfLife{
  public static void main(String[] args){
    Drawing.begin("Game of Life", 800, 800);
    new CellMatrix(35,30,27);
    Drawing.end();
  }
}