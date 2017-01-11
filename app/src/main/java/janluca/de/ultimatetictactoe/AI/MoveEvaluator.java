package janluca.de.ultimatetictactoe.AI;

import janluca.de.ultimatetictactoe.game.GameState;
import janluca.de.ultimatetictactoe.game.InnerFieldState;
import janluca.de.ultimatetictactoe.game.Move;
import janluca.de.ultimatetictactoe.game.PlayerType;

public class MoveEvaluator {

    private Difficulty difficulty;

    private GameState currentGameState;

    private PlayerType playerExecutingMove;

    private PlayerType enemy;

    public MoveEvaluator(Difficulty difficulty){
        this.difficulty = difficulty;
    }

    public void setGameState(GameState gameState){
        this.currentGameState = gameState;
    }

    public void setPlayerExecutingMove(PlayerType playerExecutingMove){
        this.playerExecutingMove = playerExecutingMove;
        if(playerExecutingMove == PlayerType.PLAYER_O){
            enemy = PlayerType.PLAYER_X;
        } else if(playerExecutingMove == PlayerType.PLAYER_X){
            enemy = PlayerType.PLAYER_O;
        }
    }



    public int[] evaluateMoveScores(Move[] moves){

        int [] scores = new int[moves.length];
        for(Move move: moves){
            int score = 0;
            score += getInnerFieldPlacementScore(move.getInnerFieldId());
        }
        /*
        recursion to get mulitiple possible moves; something like this:

        int recursiveScores = evaluateMoveScores(predictedMoves);
        for(int i = 0; i < scores.length; i++){
            scores[i] += recursiveScores[i];
        }


         */


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

    private int winsGame(){
        //called when innerFieldWin is true


        return 0;
    }






    public int winsInnerField(Move move){
        int score = 0;
        int innerFieldId = move.getInnerFieldId();
        InnerFieldState innerFieldState = currentGameState.getInnerFieldStateById(move.getOuterFieldId());

        if(innerFieldState.getStateById((innerFieldId + 3) % 9) == playerExecutingMove &&
                innerFieldState.getStateById((innerFieldId + 6) % 9) == playerExecutingMove){
            //vertical winning
            score += 100;
        }
        int[] horizontalIds = new int[2];
        for(int i = 0; i <= 6; i+=3){
            //find a way to check for horizontal winning
        }



        return score;
    }

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
