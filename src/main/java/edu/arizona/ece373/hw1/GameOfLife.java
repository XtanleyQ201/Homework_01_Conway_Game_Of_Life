//------------------------------------------------------
// Homework 1
// Written by: (your name and student ID)
// ECE 373 Object-Oriented Software Design, Section 001 - Fall 2026
// The University of Arizona
//------------------------------------------------------
package edu.arizona.ece373.hw1;

/**
 * A Conway's Game of Life field.
 *
 * <p>Task 4 of the homework. The field <em>is a</em> {@link Matrix} in which {@code 1} means the
 * cell is alive and {@code 0} means it is dead. Cells outside the field always count as dead, so
 * the field does not wrap around at the edges.</p>
 *
 * <p>The rules for one generation are:</p>
 * <ul>
 *   <li>a live cell with fewer than two live neighbours dies;</li>
 *   <li>a live cell with two or three live neighbours survives;</li>
 *   <li>a live cell with more than three live neighbours dies;</li>
 *   <li>a dead cell with exactly three live neighbours becomes alive.</li>
 * </ul>
 */
public class GameOfLife extends Matrix {

    /** Value of a cell that is alive. */
    public static final int ALIVE = 1;

    /** Value of a cell that is dead. */
    public static final int DEAD = 0;

    /**
     * Creates an empty game field of the given size; every cell starts dead.
     *
     * @param rows    the height of the field, must be at least 1
     * @param columns the width of the field, must be at least 1
     * @throws IllegalArgumentException if {@code rows} or {@code columns} is less than 1
     */
    public GameOfLife(int rows, int columns) {
        super(rows, columns);
    }

    /**
     * Stamps a shape onto the field with its top-left cell at the given coordinate.
     *
     * @param shape  the shape to place, must not be {@code null}
     * @param row    the field row that receives row 0 of the shape
     * @param column the field column that receives column 0 of the shape
     * @throws IllegalArgumentException if {@code shape} is {@code null}, if the coordinate is
     *                                  negative, or if the shape does not fit entirely on the field
     */
    public void addShape(Shape shape, int row, int column) {
        // TODO: reject null, negative coordinates, and shapes that would stick out of the field;
        // TODO: then copy every cell of the shape onto the field.
        if (shape == null) {
            throw new IllegalArgumentException("Shape is null");
        }
        if (row < 0 || shape.getRows() + row >= this.getRows()) {
            throw new IllegalArgumentException("Input row is outside the game field");
        }
        if (column < 0 || shape.getColumns() + column >= this.getColumns()) {
            throw new IllegalArgumentException("Input column is outside the game field");
        }

        for (int i = 0; i < shape.getRows(); i++) {
            for (int j = 0; j < shape.getColumns(); j++) {
                this.getData()[i+row][j+column] = shape.getData()[i][j];
            }
        }

    }

    /**
     * Advances the field by one generation.
     *
     * <p>Compute the whole next generation into a local two-dimensional array first, and only
     * then install it with the inherited {@link Matrix#setData(int[][])}.</p>
     */
    public void step() {
        // TODO: allocate a local int[getRows()][getColumns()], fill it by applying the rules to
        // TODO: the current generation, then call setData(next).

        //Task:
        /*
        Cells outside the field always count as dead (the field does not wrap around).
        a live cell with fewer than 2 live neighbours dies;
        a live cell with 2 or 3 live neighbours survives;
        a live cell with more than 3 live neighbours dies;
        a dead cell with exactly 3 live neighbours becomes alive.
         */

        //Local int[][]
        int[][] nextGen = new int[this.getRows()][this.getColumns()];
        int NumOfLiveNeighbours;
        for (int i = 0; i < this.getRows(); i++) {
            for (int j = 0; j < this.getColumns(); j++) {
                // Rules for LIVE cells
                //System.out.print("Coordinate " + i + "," + j + " Status: " + this.get(i,j) + "\n");
                if (this.get(i,j) == ALIVE) {
                    NumOfLiveNeighbours = countLiveNeighbours(i,j);

                    if (NumOfLiveNeighbours >= 2 && NumOfLiveNeighbours <= 3) {
                        nextGen[i][j] = ALIVE;
                    }
                    else {
                        nextGen[i][j] = DEAD;
                    }
                }
                // Rules for DEAD cells
                else {
                    NumOfLiveNeighbours = countLiveNeighbours(i,j);
                    if (NumOfLiveNeighbours == 3) {
                        nextGen[i][j] = ALIVE;
                    }
                    else {
                        nextGen[i][j] = DEAD;
                    }

                }
            }
        }

        this.setData(nextGen);
    }

    /**
     * Counts how many of the eight cells around a coordinate are alive.
     *
     * @param row    the row of the cell whose neighbours are counted
     * @param column the column of the cell whose neighbours are counted
     * @return a number between 0 and 8
     */
    public int countLiveNeighbours(int row, int column) {
        // TODO: look at the eight surrounding coordinates and count the live ones,
        // TODO: skipping coordinates that fall outside the field.

        int totalLive = 0;

        for (int i = -1; i < 2; i++) {
            if (i+row < 0 || i+row >= this.getRows()) {
                continue;
            }
            for (int j = -1; j < 2; j++) {
                if ((j+column < 0 || j+column >= this.getColumns()) || (j==0 && i==0)) {
                    continue;
                }
                if (this.getData()[i+row][j+column] == ALIVE) {
                    //System.out.print("Alive found at coordinate " + (row+i) + "," + (column+j) + "\n");
                    totalLive += 1;
                }
            }
        }
        //if (totalLive > 0)
            //System.out.print("Total Alive: " + totalLive + " at coord " + row+","+column+"\n");
        return totalLive;
    }

    /**
     * Showcase for Task 4: builds a field holding a blinker and a glider and steps it a fixed
     * number of generations.
     *
     * @param args optional; args[0] is the number of generations to run, default 20
     * @throws InterruptedException if the animation is interrupted while sleeping between frames
     */
    public static void main(String[] args) throws InterruptedException {
        // TODO: create a game field, add at least a Blinker and a Glider, then run a fixed
        // TODO: number of generations - read it from args[0] if present, else use 20:
        // TODO:   print the field with a BoolMatrixPrinter, call step(), and Thread.sleep a little.
        GameOfLife newGame = new GameOfLife(12,12);

        newGame.addShape(Shape.blinker(), 1,1);
        newGame.addShape(Shape.glider(), 5,5);

        for (int gen = 0; gen < 10; gen++) {
            System.out.print(new BoolMatrixPrinter().print(newGame));
            newGame.step();
            Thread.sleep(1000);
        }
        System.out.print(new BoolMatrixPrinter().print(newGame));
    }
}
