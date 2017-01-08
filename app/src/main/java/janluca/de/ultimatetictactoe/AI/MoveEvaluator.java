package janluca.de.ultimatetictactoe.AI;

import janluca.de.ultimatetictactoe.game.GameState;
import janluca.de.ultimatetictactoe.game.Move;

public class MoveEvaluator {

    private Difficulty difficulty;

    private GameState currentGameState;

    public MoveEvaluator(Difficulty difficulty){
        this.difficulty = difficulty;
    }

    public void setGameState(GameState gameState){
        this.currentGameState = gameState;
    }

    public int[] evaluateMoveScores(Move[] moves){
        int [] scores = new int[moves.length];
        for(Move move: moves){
            int score = 0;
            score += getInnerFieldPlacementScore(move.getInnerFieldId());
        }
        return scores;
    }

    /*

    possible evalutaion methods:
    -win or lose -> recursively
    -prediction of enemy moves -> recursively
    -innerFieldWin -> recursively with enemy move prediction
    -innerField denying innerFieldWin possibilites from enemy
    -innerField getting 2 in row
    -innerField denying 2 in row from enemy

     */



    private int getInnerFieldPlacementScore(int innerFieldId){
        switch (innerFieldId){
            case 4:
                //middle field
                return 10;
            case 0:case 2:case 6:case 8:
                //corner fields
                return 5;
            default:
                //side fields
                return 1;
        }
    }

}
