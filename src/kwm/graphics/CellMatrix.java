package kwm.graphics;

import kwm.Drawing;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

/*-----------------------------------------------------------------------------
 * CellMatrix.java
 * Author: Hanna Rodler
 * Date: 21.04.2021
 * Task: OOP Aufgabe 5
 *
 * This class creates the so-called "Game of Life" by John Conway. The "Game
 *  of Life" consists of a matrix in which cells can be either alive (true)
 * or dead (false). The matrix has a random initial condition (the first
 * generation) and goes through infinite sequences of generations following
 * these rules:
 *  Birth:    a dead cell with exactly three neighbors is brought to live.
 *  Survival: a living cell with two or three neighbors survives.
 *  Death by solitude or overpopulation:
 *            in every other case a cell dies or stays dead.
 *
 * CellMatrix.java contains the following methods:
 * - init(int p): [initializes gameMatrix with the probability p of the cell
 *                being alive (true)]
 * - draw():      [draws the matrix]
 * - generateCellGeneration(): [generates the new cell generation with the
 *                help of an auxiliary matrix.]
 * - replaceOldGen(boolean[][] newGen): [auxiliary matrix replaces (gets
 *                copied) into gameMatrix.]
 * - countNeighbors(int row, int col): [counts the neighbors of a cell.]
 * - startAnimation(): [starts the animation.]
 * - actionPerformed(ActionEvent e): [performs generateCellGeneration() and
 *                draw() every second (delay = 1000).]
 * --------------------------------------------------------------------------*/
public class CellMatrix implements ActionListener{
  public Timer timer;
  public static int delay=1000;
  
  public int width;
  public int height;
  public boolean[][] gameMatrix;
  public static final int SIZE=25;
  
  public static final Color BORDER=Color.black;
  public static final Color ALIVE=Color.orange;
  public static final Color DEAD=Color.white;
  
  /**
   * [This constructor generates a matrix with the size width x height in
   * which every cell has the probability p of being alive. It also draws
   * the matrix for the first time and starts the animation. ]
   * @param p [probability of cell being alive]
   * @param width [width of gameMatrix]
   * @param height [height of gameMatrix]
   */
  public CellMatrix(int p, int width, int height){
    this.width=width;
    this.height=height;
    this.gameMatrix=new boolean[this.width][this.height];
    
    this.init(p);
    this.draw();
    this.startAnimation();
  }
  
  /**
   * [Initializes gameMatrix. A field is alive (true) if a random number r is
   * smaller the the percentage. Else it is dead (false).]
   *
   * @param p [probability of cell being alive]
   */
  public void init(int p){
    for(int i=0; i<this.width; i++){
      for(int j=0; j<this.height; j++){
        double r=Math.random();
        this.gameMatrix[i][j]=r<(p/100.0);
      }
    }
  }
  
  /**
   * [Draws the matrix. The colors ALIVE and DEAD convey the state of the
   * cell.]
   */
  public void draw(){
    for(int i=0; i<this.width; i++){
      for(int j=0; j<this.height; j++){
        Drawing.graphics.setColor(
          this.gameMatrix[i][j] ? CellMatrix.ALIVE : CellMatrix.DEAD);
        Drawing.graphics.fillRect(i*SIZE, j*SIZE, SIZE, SIZE);
        Drawing.graphics.setColor(CellMatrix.BORDER);
        Drawing.graphics.drawRect(i*SIZE, j*SIZE, SIZE, SIZE);
      }
    }
    Drawing.paint();
  }
  
  
  /**
   * [Generates the new generation from the current generation. New generation
   * will replace the current one.
   * Specification neighbor of a cell: If neighbor is at [i][j] neighbors
   * range from [i-1][j-1] to [i+1][j+1]. (North, East, South, West as well
   * as northeast, southeast, southwest and northwest.)
   * A cell survives if it has two or three neighbors.
   * A cell is born when it has exactly two neighbors.
   * In all other cases the cell dies of overpopulation or solitude.]
   */
  public void generateCellGeneration(){
    boolean[][] newGen;
    newGen=new boolean[width][height];
    for(int i=0; i<this.width; i++){
      for(int j=0; j<this.height; j++){
        int nrOfNeighbors=countNeighbors(i, j);
        if(this.gameMatrix[i][j] &&
          (nrOfNeighbors == 2 || nrOfNeighbors == 3)){
          newGen[i][j]=true;// stays alive
        }else if(!this.gameMatrix[i][j] && nrOfNeighbors == 3)
          newGen[i][j]=true; //birth
        else newGen[i][j]=false; // inevitable death
      }
    }
    replaceOldGen(newGen);
  }
  
  /**
   * [copies the new generation into the old generation. So the old becomes
   * the new one.]
   *
   * @param newGen [the newly generated generation]
   */
  public void replaceOldGen(boolean[][] newGen){
    for(int i=0; i<this.width; i++){
      for(int j=0; j<this.height; j++){
        gameMatrix[i][j]=newGen[i][j];
      }
    }
  }
  
  /**
   * [Counts the neighbors of a cell.]
   *
   * @param row [current i of gameMatrix[i][j]]
   * @param col [current j of gameMatrix[i][j]]
   * @return [number of neighbors]
   */
  public int countNeighbors(int row, int col){
    int numNeighbors=0;
    int minRow=row-1;
    int maxRow=row+1;
    int minCol=col-1;
    int maxCol=col+1;
    
    // [case: North]
    if((minRow>=0) && (col<height)){
      if(gameMatrix[minRow][col]) numNeighbors++;
    }
    // [case: North-East]
    if((minRow>=0) && (maxCol<height)){
      if(gameMatrix[minRow][maxCol]) numNeighbors++;
    }
    // [case: East]
    if((row<width) && (maxCol<height)){
      if(gameMatrix[row][maxCol]) numNeighbors++;
    }
    //[case: South-East]
    if((maxRow<width) && (maxCol<height)){
      if(gameMatrix[maxRow][maxCol]) numNeighbors++;
    }
    //[case: South]
    if((maxRow<width) && (col<height)){
      if(gameMatrix[maxRow][col]) numNeighbors++;
    }
    //[case: South-West]
    if((maxRow<width) && (minCol>=0)){
      if(gameMatrix[maxRow][minCol]) numNeighbors++;
    }
    //[case: West]
    if((row>=0) && (minCol>=0)){
      if(gameMatrix[row][minCol]) numNeighbors++;
    }
    // [case: North-West]
    if((minRow>=0) && (minCol>=0)){
      if(gameMatrix[minRow][minCol]) numNeighbors++;
    }
    
    return numNeighbors;
  }
  
  
  /**
   * [Starts animation with the timer.]
   */
  public void startAnimation(){
    this.timer=new Timer(CellMatrix.delay, this);
    this.timer.start();
  }
  
  /**
   * [Executes this.generateCellGeneration(); and this.draw(); when last
   * action was performed.]
   *
   * @param e [event.]
   */
  public void actionPerformed(ActionEvent e){
    this.generateCellGeneration();
    this.draw();
  }
}
