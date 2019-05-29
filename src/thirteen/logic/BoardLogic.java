package thirteen.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BoardLogic {

    private int size;

    // possible moves, all four directions
    private int[] xMove = {0, 0, 1, -1};
    private int[] yMove = {1, -1, 0, 0};
    /**
     * The matrix containing a flag for each Tile in the grid to check if the Tile at same position was visited by the algorithm or not
     */
    private int[][] visited;
    /**
     * The matrix which will hold the group of selected Tiles if there are any, will be used to update the grid
     */
    private List<List<Integer>> result;

    /**
     * Holds the level of the game, random numbers will be calculated depending on level
     */
    private int level = 4;

    /**
     * Random number generator to get new values for ne Tiles
     */
    private static Random random = new Random();

    public BoardLogic(int size) {
        this.size = size;
        visited = new int[size][size];
        result = new ArrayList<List<Integer>>();

        for (int i = 0; i < size; i++) {
            result.add(new ArrayList<Integer>());
            for (int j = 0; j < size; j++) {
                result.get(i).add(j, 0);
            }
        }
    }
    /**
     * Checks a neighboured Tile in a matrix if the value of it equals the value of the selected one
     *
     * @param posX  the x position in the 2D matrix
     * @param posY  the y position in the 2D matrix
     * @param value the value of the selected Tile
     * @param grid  the 2D matrix containing the Tiles of the Board
     * @return boolean, true if the value of the Tile ad x,y equals the value of the selected Tile, false otherwise
     */
    private boolean isValid(int posX, int posY, int value, List<List<Integer>> grid) {
        if (posX < size && posY < size && posX >= 0 && posY >= 0) {
            return (visited[posX][posY] == 0 && grid.get(posX).get(posY) == value);
        } else
            return false;
    }

    /**
     * Sets the result matrix, which will be used to update the board
     *
     * @param value the value o the selected Tile
     * @param grid  the 2D matrix containing the Tiles
     */
    private void setResult(int value, List<List<Integer>> grid) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                // If the Tile at i,j was visited by the bfs and the value of it equals the value of the selected Tile,
                // then the position i,j in the result matrix will be set to the value, others will be set 0.
                // So the group which shall be deleted gets highlighted.
                if (visited[i][j] == 1 && grid.get(i).get(j) == value) {
                    result.get(i).set(j, value);
                } else
                    result.get(i).set(j, 0);
            }
        }
    }

    /**
     * This Algorithm checks the neighboured Tiles of the Tile selected, as long as the isValid method returns true (recursive)
     * If The valueNeighbour is not equal to value, the recursion never starts.
     *
     * @param value          the value of the selected Tile
     * @param valueNeighbour the value of the Tile which is next to the Tile selected
     * @param posX           the x position of the selected Tile
     * @param posY           the y position of the selected Tile
     * @param grid           the 2D matrix which contains the Tiles
     */
    private void bfs(int value, int valueNeighbour, int posX, int posY, List<List<Integer>> grid) {
        // Checks on the first run if the first neighbour has the same value, if yes the next Tile will although be checked in recursive call.
        if (value != valueNeighbour)
            return;

        // mark the actual Tile as visited in the visited matrix
        visited[posX][posY] = 1;

        // start recursion as long as the next Tile has the same value ( isValid return true )
        for (int move = 0; move < 4; move++)
            if ((isValid(posX + xMove[move], posY + yMove[move], value, grid)))
                bfs(value, valueNeighbour, posX + xMove[move], posY + yMove[move], grid);
    }

    /**
     * Clear the result and visited matrix
     */
    private void clear() {
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++) {
                visited[i][j] = 0;
                result.get(i).set(j, 0);
            }
    }

    /**
     * Looks in each direction Up, Right, Down, Left of the selected Tile to get the group of same Tiles
     *
     * @param grid the 2D matrix containing each Tile
     * @param x    the x position in the matrix of the Tile selected
     * @param y    the y position in the matrix of the Tile selected
     * @return List<List < AtomicInteger>> 2D matrix with the group of the Tile selected
     */
    public List<List<Integer>> getConnected(List<List<Integer>> grid, int x, int y) {
        // clear the calculations of previous round
        clear();
        // call bfs for each direction
        for (int move = 0; move < 4; move++)
            if ((isValid(x + xMove[move], y + yMove[move], grid.get(x).get(y), grid))) {
                bfs(grid.get(x).get(y), grid.get(x + xMove[move]).get(y + yMove[move]), x, y, grid);
            }
        // set the result matrix
        setResult(grid.get(x).get(y), grid);
        return result;
    }

    /**
     * Generic function to calculate and return any column with index col of a 2D matrix
     *
     * @param grid the matrix of which the column shall be returned
     * @param col  the index of the column wanted
     * @param <T>  generic type ( in this case for the AtomicInteger and Tile )
     * @return The column in the matrix grid specified with col
     */
    public static <T> List<T> getColumn(List<List<T>> grid, int col) {
        List column = new ArrayList();
        for (int i = 0; i < grid.get(col).size(); ++i) {
            column.add(grid.get(i).get(col));
        }
        return column;
    }

    /**
     * This will delete the Tiles which are the same as the value ( compared with result, because then only the ones of the group get deleted )
     * and adds them in front of the list (top of grid) to simulate "falling" Tiles
     *
     * @param gridCol the column of the grid with same position in a same sized matrix as result
     * @param resCol  the column of the result matrix with same position in a same sized matrix as grid
     * @param value   the value of the selected Tile
     */
    private void gravity(List<Integer> gridCol, List<Integer> resCol, int value) {
        // If they are not the same size return
        if (gridCol.size() != resCol.size())
            return;
        // else walk through the column and apply gravity effect
        for (int i = 0; i < gridCol.size(); ++i) {
            // because result only holds the group which should be deleted the check is done with result (other values of result are zero)
            if (resCol.get(i) == value) {
                // remove the ones which should be deleted
                resCol.remove(i);
                gridCol.remove(i);
                // add them on "top"
                resCol.add(0, value);
                gridCol.add(0, value);
            }
        }
    }

    /**
     * Holds the update rules, if a Tile is selected the Tiles connected to this Tile ( not in edges ) should be marked and to be assigned a new random value
     * except the one selected, this one will increase its value by 1
     *
     * @param grid the 2D matrix containing the Tiles
     * @param posX the x position of the selected Tile
     * @param posY the y position of the selected Tile
     * @return the updated matrix
     */
    public List<List<Integer>> updateBoard(List<List<Integer>> grid, int posX, int posY) {

        //Get the group of connected Tiles with same value
        getConnected(grid, posX, posY);

        int value = grid.get(posX).get(posY);

        // newX is used to calculate the new position of the selected Tile, it falls down if beneath him are others of same value
        int newX = posX;

        // Loop to define new position X
        while (newX < size - 1) {
            if (result.get(newX + 1).get(posY) != 0) {
                newX++;
            } else
                break;
        }
        // Assign the new Position with the value + 1
        if (result.get(newX).get(posY) == value) {
            grid.get(newX).set(posY, value + 1);
            result.get(newX).set(posY, 0);
        }

        // Use "gravity" on all columns, Tiles which are on top of one which gets destroyed should fall down
        for (int col = 0; col < size; ++col) {
            // Get all the columns of the grid and the result
            List<Integer> grd = getColumn(grid, col);
            List<Integer> res = getColumn(result, col);
            // Apply gravity
            gravity(grd, res, value);
            /*
            //DEBUG
            for( int i = 0;  i < grd.size(); ++i){
                System.out.print(grd.get(i).getValue());
            }
            System.out.print("\n");
            for( int i = 0;  i < res.size(); ++i){
                System.out.print(res.get(i).intValue());
            }
            System.out.print("\n");
            */
            assignGravity(grid, grd, res, col);
            /*
            //DEBUG
            for( int i = 0;  i < res.size(); ++i){
                System.out.print(result.get(i).get(col).intValue());
            }
            System.out.print("\n");
            for( int i = 0;  i < grd.size(); ++i){
                System.out.print(grid.get(i).get(col).getValue());
            }
            System.out.print("\n");
            */
        }
        update(grid);

        return grid;
    }

    /**
     * Updates the grid for the next round
     *
     * @param grid the matrix containing the Tiles
     */
    private void update(List<List<Integer>> grid) {
        setLevel(grid);
        // All Tiles which are the same value as the result now get a new random value
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (grid.get(i).get(j) == result.get(i).get(j).intValue()) {
                    grid.get(i).set(j, getRandInRange(1, level - 1));
                }
            }
        }
        setLevel(grid);
    }

    /**
     * Apply the gravity which was calculated on the grid
     *
     * @param grid    the matrix containing the Tiles
     * @param gridCol the column of the grid which shall be assigned to the grid matrix
     * @param resCol  the column of the result which shall be assigned to the result matrix
     * @param col     the index of the column in the matrix which shall be updated
     */
    private void assignGravity(List<List<Integer>> grid, List<Integer> gridCol, List<Integer> resCol, int col) {
        // Assign the gravity outcome to the Grid and the Result
        for (int i = 0; i < size; ++i) {
            //result.get(i).get(col).set(res.get(i).intValue()); not working BUT WHY??????
            //grid.get(i).get(col).setValue(grd.get(i).getValue()); not working BUT WHY??????
            result.get(i).remove(col);
            result.get(i).add(col, resCol.get(i));
            grid.get(i).remove(col);
            grid.get(i).add(col, gridCol.get(i));
        }
    }

    /**
     * The bomb function removes the selected Tile from the board
     *
     * @param grid the matrix containing the Tiles
     * @param row  the row index of the matrix containing the selected Tile
     * @param col  the column index of the matrix containing the selected Tile
     */
    public List<List<Integer>> bomb(List<List<Integer>> grid, int row, int col) {
        clear();
        int value = grid.get(row).get(col);
        result.get(row).set(col, value);
        // Get the correct columns
        List<Integer> grdCol = getColumn(grid, col);
        List<Integer> resCol = getColumn(result, col);
        // Apply gravity on them
        gravity(grdCol, resCol, value);
        assignGravity(grid, grdCol, resCol, col);
        // Update the board
        update(grid);
        return grid;
    }

    /**
     * Checks the whole board if there are moves or not, game is over if they are none left
     *
     * @param grid the matrix containing the Tiles
     * @return true if there are moves left, false otherwise
     */
    public boolean hasMoves(List<List<Integer>> grid) {
        clear();
        for (int x = 0; x < size; ++x) {
            for (int y = 0; y < size; ++y) {
                for (int move = 0; move < 4; move++)
                    if ((isValid(x + xMove[move], y + yMove[move], grid.get(x).get(y), grid))) {
                        bfs(grid.get(x).get(y), grid.get(x + xMove[move]).get(y + yMove[move]), x, y, grid);
                    }
                setResult(grid.get(x).get(y), grid);
                if (result.get(x).get(y) != 0) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Calculates the level -> highest value on the board
     *
     * @param grid the matrix containing the Tiles
     */
    private void setLevel(List<List<Integer>> grid) {
        for (int x = 0; x < size; ++x) {
            for (int y = 0; y < size; ++y) {
                int val = grid.get(x).get(y);
                if (level < val)
                    level = val;
            }
        }
    }

    public int getLevel() {
        return level;
    }

    public static int getLevelFromGrid( List<List<Integer>> grid ){
        int lvl = 0;
        for (int x = 0; x < grid.get(0).size(); ++x) {
            for (int y = 0; y < grid.get(0).size(); ++y) {
                int val = grid.get(x).get(y);
                if (lvl < val)
                    lvl = val;
            }
        }
        return lvl;
    }

    /**
     * Calculates a random number in a range from lower to upper
     *
     * @param lower the lower bound of the random number
     * @param upper the upper bound of the random number
     * @return random number in the defined range ( int )
     */
    public static int getRandInRange(int lower, int upper) {
        return random.nextInt((upper - lower) + 1) + lower;
    }

    /**
     * Saves the current state to a list at beginning, so i can be removed and applied
     *
     * @param grid the matrix which shall be saved
     */
    public int[] saveGridToUndo(List<List<Integer>> grid) {

        int[] undo = new int[size*size];
        int ctr = 0;
        for (int x = 0; x < size; ++x) {
            for (int y = 0; y < size; ++y) {
                undo[ctr] = grid.get(x).get(y);
                ctr++;
            }
        }
        return undo;
    }


    /**
     * applies the history to a grid and returns it
     * @param history the history array
     * @return a new grid which holds the old state
     */
    public List<List<Integer>> undo(int[] history) {
        List<List<Integer>> grid = new ArrayList<List<Integer>>();
        int ctr = 0;
        for (int x = 0; x < size; ++x) {
            grid.add(new ArrayList<Integer>());
            for (int y = 0; y < size; ++y) {
                grid.get(x).add(y, history[ctr]);
                ctr++;
            }
        }
        setLevel(grid);
        return grid;
    }
}