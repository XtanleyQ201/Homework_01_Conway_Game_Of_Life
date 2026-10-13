//------------------------------------------------------
// Homework 1
// Written by: (your name and student ID)
// ECE 373 Object-Oriented Software Design, Section 001 - Fall 2026
// The University of Arizona
//------------------------------------------------------
package edu.arizona.ece373.hw1;

/**
 * Prints the outline (the frame) of a matrix and leaves the inside blank.
 *
 * <p>A 2x4 matrix must render as</p>
 *
 * <pre>
 * +----+
 * |    |
 * |    |
 * +----+
 * </pre>
 *
 * <p>with a {@code '\n'} after every line, including the last one.</p>
 */
public class MatrixOutlinePrinter implements MatrixPrinter {

    /** The character drawn at the four corners of the frame. */
    public static final char CORNER = '+';

    /** The character drawn along the top and bottom edges of the frame. */
    public static final char HORIZONTAL = '-';

    /** The character drawn along the left and right edges of the frame. */
    public static final char VERTICAL = '|';

    /**
     * Renders {@code matrix} as a framed block of text.
     *
     * @param matrix the matrix to render, must not be {@code null}
     * @return the framed rendering of {@code matrix}
     * @throws IllegalArgumentException if {@code matrix} is {@code null}, or if
     *                                  {@link #printRow(Matrix, int)} rejects a row
     */
    @Override
    public String print(Matrix matrix) {
        // TODO: reject a null matrix, then build the frame with a StringBuilder:
        // TODO:   a border line, one line per row, a border line, each ending in '\n'.
        // TODO: the inside of each line must come from printRow(matrix, row) so that
        // TODO: BoolMatrixPrinter can change it by overriding that one method.
        //Creation of StringBuilder printMatrix to make the matrix string and return it.
        StringBuilder printMatrix = new StringBuilder();

        //Checker if the matrix inputted for this function is null and throws an IllegalArgumentException if it does
        if (matrix == null) {
            throw new IllegalArgumentException("Input matrix is null");
        }
        //Appends the top borderline to the printMatrix
        printMatrix.append(CORNER);
        for (int i = 0; i < matrix.getRows(); i++) {
            printMatrix.append(HORIZONTAL);
        }
        printMatrix.append(CORNER).append("\n");

        //Appends each row of the matrix into the string alongside the outer VERITCAL characters
        for (int i = 0; i < matrix.getColumns(); i++) {
            if (matrix.getData()[i] == null) {
                throw new IllegalArgumentException("Row of matrix is null");
            }
            printMatrix.append(VERTICAL).append(printRow(matrix, i)).append(VERTICAL).append("\n");
        }
        printMatrix.append(CORNER);

        //Appends the bottom borderline to the printMatrix
        for (int i = 0; i < matrix.getRows(); i++) {
            printMatrix.append(HORIZONTAL);
        }
        printMatrix.append(CORNER).append("\n");

        return printMatrix.toString();
    }


    /**
     * Renders the inside of one row, without the surrounding {@link #VERTICAL} characters.
     *
     * <p>The returned string must be exactly {@code matrix.getColumns()} characters long,
     * otherwise the frame will not line up. Subclasses override this method.</p>
     *
     * @param matrix the matrix being printed, never {@code null}
     * @param row    the index of the row to render
     * @return the characters that belong between the two border characters of this line
     */
    protected String printRow(Matrix matrix, int row) {
        // TODO: return one blank character per column.
        //creates StringBuilder matrixRow to add it to stringMatrix;
        StringBuilder matrixRow = new StringBuilder();

        //For loop to append the data values of the matrix to matrixRow
        for (int j = 1; j < matrix.getColumns() - 1; j++) {
            matrixRow.append(matrix.getData()[row][j]);
        }

        return matrixRow.toString();
    }
}
