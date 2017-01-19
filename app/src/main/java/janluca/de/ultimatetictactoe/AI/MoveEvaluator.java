package janluca.de.ultimatetictactoe.AI;

import janluca.de.ultimatetictactoe.game.Game;
import janluca.de.ultimatetictactoe.game.InnerField;
import janluca.de.ultimatetictactoe.game.Move;
import janluca.de.ultimatetictactoe.game.PlayerType;

public class MoveEvaluator {

    private Difficulty difficulty;

    final private Game currentGame;

    final private Game gameDouble;

    private PlayerType playerExecutingMove;

    private PlayerType enemy;

    public MoveEvaluator(Difficulty difficulty, PlayerType playerExecutingMove, final Game game){
        this.difficulty = difficulty;
        currentGame = game;
        gameDouble = game.createCopyForPrediction();
        this.playerExecutingMove = playerExecutingMove;
        enemy = PlayerType.getEnemy(playerExecutingMove);
    }

    public int[] evaluateMoveScores(Move[] moves){
        int recursionDepth;
        switch (difficulty){
            case EASY:
                recursionDepth = 1;
                break;
            case MEDIUM:
                recursionDepth = 3;
                break;
            case HARD:
                recursionDepth = 5;
                break;
            default:
                recursionDepth = 1;
        }
        return evaluateMoveScores(moves, recursionDepth, false);

    }

    int[] evaluateMoveScores(Move[] moves, int remainingRecursion, boolean enemyMoveCalculation){

        int [] scores = new int[moves.length];
        for(int i = 0; i < moves.length; i++) {
            int score = 0;
            score += getInnerFieldPlacementScore(moves[i]);
            score += winsInnerField(moves[i]);
            score += getTwoInRowScore(moves[i]);
            score += destroyTwoInRowForEnemy(moves[i]);


            //score += recursiveScore by means of new MoveEvaluator


            scores[i] = score;

        }



        if(!enemyMoveCalculation){

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
    TODO figure out better evaluation scores -> order by importance and create numbers so they wont mess with higher importance options
    possible evalutaion methods:



     */
    //outerField: win
    private int winsGame(Move move){
        int score = 0;
        int outerFieldId = move.getOuterFieldId();

        if(currentGame.getOuterFieldById((outerFieldId + 3) % 9) == playerExecutingMove &&
                currentGame.getOuterFieldById((outerFieldId + 6) % 9) == playerExecutingMove){
            //vertical winning
            score += 100000;
        }

        int row = outerFieldId / 3;
        if(currentGame.getOuterFieldById((outerFieldId + 1) % 3 + (3 * row) ) == playerExecutingMove &&
                currentGame.getOuterFieldById((outerFieldId + 2) % 3 + (3 * row) ) == playerExecutingMove){
            score += 100000;
        }

        if(outerFieldId == 0 || outerFieldId == 4 || outerFieldId == 8){
            if(currentGame.getOuterFieldById((outerFieldId + 4) % 12) == playerExecutingMove &&
                    currentGame.getOuterFieldById((outerFieldId + 8) % 12) == playerExecutingMove){
                score += 100000;
            }
        }

        if(outerFieldId == 2 || outerFieldId == 4 || outerFieldId == 6){
            if(currentGame.getOuterFieldById((outerFieldId + 2) < 8 ? outerFieldId + 2 : outerFieldId - 4) == playerExecutingMove &&
                    currentGame.getOuterFieldById((outerFieldId + 4) < 8 ? outerFieldId + 4 : outerFieldId - 2) == playerExecutingMove){
                score += 100000;
            }
        }


        return score;
    }

    //outerField: get 2 in row
    private int getTwoOuterFieldInRow(Move move){
        int score = 0;
        int outerFieldId = move.getOuterFieldId();

        if(currentGame.getOuterFieldById((outerFieldId + 3) % 9) == playerExecutingMove ||
                currentGame.getOuterFieldById((outerFieldId + 6) % 9) == playerExecutingMove){
            //vertical winning
            score += 1000;
        }

        int row = outerFieldId / 3;
        if(currentGame.getOuterFieldById((outerFieldId + 1) % 3 + (3 * row) ) == playerExecutingMove ||
                currentGame.getOuterFieldById((outerFieldId + 2) % 3 + (3 * row) ) == playerExecutingMove){
            score += 1000;
        }

        if(outerFieldId == 0 || outerFieldId == 4 || outerFieldId == 8){
            if(currentGame.getOuterFieldById((outerFieldId + 4) % 12) == playerExecutingMove ||
                    currentGame.getOuterFieldById((outerFieldId + 8) % 12) == playerExecutingMove){
                score += 1000;
            }
        }

        if(outerFieldId == 2 || outerFieldId == 4 || outerFieldId == 6){
            if(currentGame.getOuterFieldById((outerFieldId + 2) < 8 ? outerFieldId + 2 : outerFieldId - 4) == playerExecutingMove ||
                    currentGame.getOuterFieldById((outerFieldId + 4) < 8 ? outerFieldId + 4 : outerFieldId - 2) == playerExecutingMove){
                score += 1000;
            }
        }


        return score;
    }

    //outerField: deny win possibility
    private int outerFieldDestroyTwoInRow(Move move){
        int score = 0;
        int outerFieldId = move.getOuterFieldId();

        if(currentGame.getOuterFieldById((outerFieldId + 3) % 9) == enemy &&
                currentGame.getOuterFieldById((outerFieldId + 6) % 9) == enemy){
            //vertical winning
            score += 1000;
        }

        int row = outerFieldId / 3;
        if(currentGame.getOuterFieldById((outerFieldId + 1) % 3 + (3 * row) ) == enemy &&
                currentGame.getOuterFieldById((outerFieldId + 2) % 3 + (3 * row) ) == enemy){
            score += 1000;
        }

        if(outerFieldId == 0 || outerFieldId == 4 || outerFieldId == 8){
            if(currentGame.getOuterFieldById((outerFieldId + 4) % 12) == enemy &&
                    currentGame.getOuterFieldById((outerFieldId + 8) % 12) == enemy){
                score += 1000;
            }
        }

        if(outerFieldId == 2 || outerFieldId == 4 || outerFieldId == 6){
            if(currentGame.getOuterFieldById((outerFieldId + 2) < 8 ? outerFieldId + 2 : outerFieldId - 4) == enemy &&
                    currentGame.getOuterFieldById((outerFieldId + 4) < 8 ? outerFieldId + 4 : outerFieldId - 2) == enemy){
                score += 1000;
            }
        }


        return score;
    }

    //outerField: PlacementScores
    private int getOuterFieldPlacementScores(Move move){
        switch (move.getOuterFieldId()){
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

    //-innerField: win
    private int winsInnerField(Move move){
        int score = 0;
        int innerFieldId = move.getInnerFieldId();
        boolean winsInnerField = false;
        InnerField innerField = currentGame.getInnerFieldStateById(move.getOuterFieldId());

        if(innerField.getFieldStatusById((innerFieldId + 3) % 9) == playerExecutingMove &&
                innerField.getFieldStatusById((innerFieldId + 6) % 9) == playerExecutingMove){
            //vertical winning
            score += 100;
            winsInnerField = true;
        }

        int row = innerFieldId / 3;
        if(innerField.getFieldStatusById((innerFieldId + 1) % 3 + (3 * row) ) == playerExecutingMove &&
                innerField.getFieldStatusById((innerFieldId + 2) % 3 + (3 * row) ) == playerExecutingMove){
            //horizontal winning
            score += 100;
            winsInnerField = true;
        }

        if(innerFieldId == 0 || innerFieldId == 4 || innerFieldId == 8){
            if(innerField.getFieldStatusById((innerFieldId + 4) % 12) == playerExecutingMove &&
                    innerField.getFieldStatusById((innerFieldId + 8) % 12) == playerExecutingMove){
                score += 100;
                winsInnerField = true;
            }
        }

        if(innerFieldId == 2 || innerFieldId == 4 || innerFieldId == 6){
            if(innerField.getFieldStatusById((innerFieldId + 2) < 8 ? innerFieldId + 2 : innerFieldId - 4) == playerExecutingMove &&
                    innerField.getFieldStatusById((innerFieldId + 4) < 8 ? innerFieldId + 4 : innerFieldId - 2) == playerExecutingMove){
                score += 100;
                winsInnerField = true;
            }
        }

        if(winsInnerField){
            score += winsGame(move);
            score += getTwoOuterFieldInRow(move);
            score += outerFieldDestroyTwoInRow(move);
            score += getOuterFieldPlacementScores(move);
        }

        return score;
    }

    //-innerField: get 2 in row
    private int getTwoInRowScore(Move move){
        int score = 0;
        int innerFieldId = move.getInnerFieldId();
        InnerField innerField = currentGame.getInnerFieldStateById(move.getOuterFieldId());

        if(innerField.getFieldStatusById((innerFieldId + 3) % 9) == playerExecutingMove ||
                innerField.getFieldStatusById((innerFieldId + 6) % 9) == playerExecutingMove){
            //vertical winning
            score += 30;
        }

        int row = innerFieldId / 3;
        if(innerField.getFieldStatusById((innerFieldId + 1) % 3 + (3 * row) ) == playerExecutingMove ||
                innerField.getFieldStatusById((innerFieldId + 2) % 3 + (3 * row) ) == playerExecutingMove){
            //horizontal winning
            score += 30;
        }

        if(innerFieldId == 0 || innerFieldId == 4 || innerFieldId == 8){
            if(innerField.getFieldStatusById((innerFieldId + 4) % 12) == playerExecutingMove ||
                    innerField.getFieldStatusById((innerFieldId + 8) % 12) == playerExecutingMove){
                score += 30;
            }
        }

        if(innerFieldId == 2 || innerFieldId == 4 || innerFieldId == 6){
            if(innerField.getFieldStatusById((innerFieldId + 2) < 8 ? innerFieldId + 2 : innerFieldId - 4) == playerExecutingMove ||
                    innerField.getFieldStatusById((innerFieldId + 4) < 8 ? innerFieldId + 4 : innerFieldId - 2) == playerExecutingMove){
                score += 30;
            }
        }

        return score;
    }

    //-innerField: deny win possibility
    private int destroyTwoInRowForEnemy(Move move){
        int score = 0;
        int innerFieldId = move.getInnerFieldId();
        boolean winsInnerField = false;
        InnerField innerField = currentGame.getInnerFieldStateById(move.getOuterFieldId());

        if(innerField.getFieldStatusById((innerFieldId + 3) % 9) == enemy &&
                innerField.getFieldStatusById((innerFieldId + 6) % 9) == enemy){
            //vertical winning
            score += 30;
            winsInnerField = true;
        }

        int row = innerFieldId / 3;
        if(innerField.getFieldStatusById((innerFieldId + 1) % 3 + (3 * row) ) == enemy &&
                innerField.getFieldStatusById((innerFieldId + 2) % 3 + (3 * row) ) == enemy){
            //horizontal winning
            score += 30;
            winsInnerField = true;
        }

        if(innerFieldId == 0 || innerFieldId == 4 || innerFieldId == 8){
            if(innerField.getFieldStatusById((innerFieldId + 4) % 12) == enemy &&
                    innerField.getFieldStatusById((innerFieldId + 8) % 12) == enemy){
                score += 30;
                winsInnerField = true;
            }
        }

        if(innerFieldId == 2 || innerFieldId == 4 || innerFieldId == 6){
            if(innerField.getFieldStatusById((innerFieldId + 2) < 8 ? innerFieldId + 2 : innerFieldId - 4) == enemy &&
                    innerField.getFieldStatusById((innerFieldId + 4) < 8 ? innerFieldId + 4 : innerFieldId - 2) == enemy){
                score += 30;
                winsInnerField = true;
            }
        }

        if(winsInnerField){
            score += winsGame(move);
        }

        return score;
    }

    //innerField: PlacementScores
    private int getInnerFieldPlacementScore(Move move){
        switch (move.getInnerFieldId()){
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
