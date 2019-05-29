package thirteen.store;

import java.util.List;
import java.util.Observable;

import thirteen.logic.BoardLogic;

public class Store extends Observable {

    private List<List<Integer>> gameboardState;
    private int scoreState;
    private int moveState;
    private List<int[]> historyState;
    private boolean bomb = false;


    Store(List<List<Integer>> initialGameboardState, int initialScoreState, int initialMovestate, List<int[]> initialHistoryState){
        this.gameboardState = initialGameboardState;
        this.scoreState = initialScoreState;
        this.moveState = initialMovestate;
        this.historyState = initialHistoryState;
    }

    public List<List<Integer>> getGameboardState() {
        return this.gameboardState;
    }

    public int getScoreState() {
        return this.scoreState;
    }

    public List<int[]> getHistoryState() {
        return this.historyState;
    }


    public void incrementTileAction(int[] position){

        BoardLogic logic = new BoardLogic(gameboardState.get(0).size());
        if( logic.hasMoves(gameboardState) ){

            saveToHistory(logic.saveGridToUndo(getGameboardState()));

            this.gameboardState = logic.updateBoard(gameboardState,position[0],position[1]);
            this.scoreState = logic.getLevel();
            this.moveState++;

        }

        this.notifyOberver();
    }

    public void updateGameboardAction(List<List<Integer>>gameboard){

        this.gameboardState = gameboard;
        this.scoreState = BoardLogic.getLevelFromGrid(gameboard);

        this.notifyOberver();
    }

    public void undoGameBoardAction(){

        if( historyState.size() > 0) {
            BoardLogic logic = new BoardLogic(gameboardState.get(0).size());

            this.gameboardState = logic.undo(historyState.get(0));
            historyState.remove(0);

            this.scoreState = logic.getLevel();
            this.moveState++;

            this.notifyOberver();
        }
    }

    public void bombGameBoardAction(int[] position){

        BoardLogic logic = new BoardLogic(gameboardState.get(0).size());
        saveToHistory(logic.saveGridToUndo(getGameboardState()));

        this.gameboardState = logic.bomb(gameboardState, position[0], position[1]);
        this.moveState++;
        this.scoreState = BoardLogic.getLevelFromGrid(gameboardState);
        setBomb(false);

        this.notifyOberver();
    }

    private void notifyOberver(){
        this.setChanged();
        this.notifyObservers();
    }

    public void setBomb(boolean bomb){
        this.bomb = bomb;
    }

    public boolean isBomb() {
        return bomb;
    }

    private void saveToHistory( int[] board ){

        this.historyState.add(0, board);

        if( historyState.size() > 10 ){
            historyState.remove(historyState.size()-1 );
        }
    }
}
