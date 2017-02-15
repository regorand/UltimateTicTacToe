package janluca.de.ultimatetictactoe.AI;


import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Random;

import janluca.de.ultimatetictactoe.game.Game;
import janluca.de.ultimatetictactoe.game.InnerField;
import janluca.de.ultimatetictactoe.game.Move;
import janluca.de.ultimatetictactoe.game.PlayerType;

class MoveEvaluator {

    private Difficulty difficulty;

    final private Game currentGame;

    private PlayerType playerExecutingMove;

    private PlayerType enemy;

    /*
    TODO possible complete refactoring of AI (not actually needed):
     -get states of the other fields at each level of recursion to determine if winning (and trading) an innerField might be worth due to already existing two in rows in other fields
     needed for win
     */



    //TODO maybe let certain criteria only be used by certain difficulties
    //Hard bot takes incredibly long to decide on move
    //TODO only use some of the criteria when deeper in recursion => maybe
    //TODO exclude certain moves for further recursion when its a bad move already (may enable 4 deep recursion for hard bot and may make an extreme bot with 5 recursion possible)
    MoveEvaluator(Difficulty difficulty, PlayerType playerExecutingMove, final Game game) {
        this.difficulty = difficulty;
        currentGame = game;
        this.playerExecutingMove = playerExecutingMove;
        enemy = playerExecutingMove.getEnemy();
    }

    Move evaluateMoveScores(Move[] moves) {
        int recursionDepth;
        switch (difficulty) {
            case VERY_EASY:
                recursionDepth = 0;
                break;
            case EASY:
                recursionDepth = 1;
                break;
            case MEDIUM:
                recursionDepth = 2;
                break;
            case HARD:
                recursionDepth = 3;
                break;
            default:
                recursionDepth = 1;
        }
        return getMoveFromEvaluatedArray(evaluateMoveScoresAlt(moves, recursionDepth));
        //return evaluateMoveScores(moves, recursionDepth);
    }

    private Move[] evaluateMoveScoresAlt(Move[] moves, int remainingRecursion) {
        for (Move move : moves) {
            int score = 0;
            score += getInnerFieldPlacementScore(move);
            score += winsInnerField(move);
            score += getTwoInRowScore(move);
            if (difficulty.isEqualOrHigherDifficultyThan(Difficulty.MEDIUM)) {
                score += destroyTwoInRowForEnemy(move);
                if (difficulty.isEqualOrHigherDifficultyThan(Difficulty.HARD)) {
                    score += destroyTwoInRowPossiblity(move);
                    score += getOuterFieldPlacementScores(move, false);
                }
            }

            if (remainingRecursion > 0) {
                final Game gameCopy = currentGame.createCopyForPrediction();
                gameCopy.doMove(move);
                if (gameCopy.isStillActive()) {
                    final MoveEvaluator moveEvaluator = new MoveEvaluator(this.difficulty, playerExecutingMove.getEnemy(), gameCopy);
                    Move[] evaluatedRecursionMoves = moveEvaluator.evaluateMoveScoresAlt(gameCopy.getAllowedMoves(), remainingRecursion - 1);
                    if(!(evaluatedRecursionMoves == null)){
                        score -= 0.8 * getMoveFromEvaluatedArray(evaluatedRecursionMoves).getScore();
                    }
                }
            }

            move.setScore(score);
        }

        Arrays.sort(moves, new Comparator<Move>() {
            @Override
            public int compare(Move move, Move t1) {
                return move.getScore() > t1.getScore() ? -1 : move.getScore() == t1.getScore() ? 0 : 1;
            }
        });

        return moves;
    }

    private Move evaluateMoveScores(Move[] moves, int remainingRecursion) {
        int highestScore = Integer.MIN_VALUE;
        Move bestMove = moves[0];
        for (Move move : moves) {
            int score = 0;
            score += getInnerFieldPlacementScore(move);
            score += winsInnerField(move);
            score += getTwoInRowScore(move);
            if (difficulty.isEqualOrHigherDifficultyThan(Difficulty.MEDIUM)) {
                score += destroyTwoInRowForEnemy(move);
                if (difficulty.isEqualOrHigherDifficultyThan(Difficulty.HARD)) {
                    score += destroyTwoInRowPossiblity(move);
                    score += getOuterFieldPlacementScores(move, false);
                }
            }

            if (remainingRecursion > 0) {
                final Game gameCopy = currentGame.createCopyForPrediction();
                gameCopy.doMove(move);
                if (gameCopy.isStillActive()) {
                    final MoveEvaluator moveEvaluator = new MoveEvaluator(this.difficulty, playerExecutingMove.getEnemy(), gameCopy);
                    score -= 0.8 * moveEvaluator.evaluateMoveScores(gameCopy.getAllowedMoves(), remainingRecursion - 1).getScore();
                }
            }

            move.setScore(score);
            if (score > highestScore) {
                highestScore = score;
                bestMove = move;
            }
        }
        return bestMove;
    }

    private Move getMoveFromEvaluatedArray(Move[] moves){
        Random r = new Random();
        int amountMoves = moves.length;
        if(amountMoves < 1){
            System.out.println("stop");
        }
        if(r.nextInt(difficulty.getErrorRate()) == 0){
            amountMoves *= difficulty.getErrorImpact();
            if(amountMoves < 1){
                amountMoves = 1;
            }
            return moves[r.nextInt(amountMoves)];
        } else {
            return moves[0];
        }
    }


    //------
    //evaluating Methods

    //outerField: win
    private int winsGame(Move move) {
        final int SCORE_TO_RECEIVE = 1000000;
        int score = 0;
        int outerFieldId = move.getOuterFieldId();

        if (currentGame.getOuterFieldById((outerFieldId + 3) % 9) == playerExecutingMove &&
                currentGame.getOuterFieldById((outerFieldId + 6) % 9) == playerExecutingMove) {
            //vertical winning
            score += SCORE_TO_RECEIVE;
        }

        int row = outerFieldId / 3;
        if (currentGame.getOuterFieldById((outerFieldId + 1) % 3 + (3 * row)) == playerExecutingMove &&
                currentGame.getOuterFieldById((outerFieldId + 2) % 3 + (3 * row)) == playerExecutingMove) {
            score += SCORE_TO_RECEIVE;
        }

        if (outerFieldId == 0 || outerFieldId == 4 || outerFieldId == 8) {
            if (currentGame.getOuterFieldById((outerFieldId + 4) % 12) == playerExecutingMove &&
                    currentGame.getOuterFieldById((outerFieldId + 8) % 12) == playerExecutingMove) {
                score += SCORE_TO_RECEIVE;
            }
        }

        if (outerFieldId == 2 || outerFieldId == 4 || outerFieldId == 6) {
            if (currentGame.getOuterFieldById((outerFieldId + 2) < 8 ? outerFieldId + 2 : outerFieldId - 4) == playerExecutingMove &&
                    currentGame.getOuterFieldById((outerFieldId + 4) < 8 ? outerFieldId + 4 : outerFieldId - 2) == playerExecutingMove) {
                score += SCORE_TO_RECEIVE;
            }
        }


        return score;
    }

    //outerField: get 2 in row
    private int getTwoOuterFieldInRow(Move move) {
        final int SCORE_TO_RECEIVE = 100000;

        int score = 0;
        int outerFieldId = move.getOuterFieldId();

        if ((currentGame.getOuterFieldById((outerFieldId + 3) % 9) == playerExecutingMove && currentGame.getOuterFieldById((outerFieldId + 6) % 9) == PlayerType.EMPTY) ||
                (currentGame.getOuterFieldById((outerFieldId + 6) % 9) == playerExecutingMove && currentGame.getOuterFieldById((outerFieldId + 3) % 9) == PlayerType.EMPTY)) {
            //vertical winning
            score += SCORE_TO_RECEIVE;
        }

        int row = outerFieldId / 3;
        if ((currentGame.getOuterFieldById((outerFieldId + 1) % 3 + (3 * row)) == playerExecutingMove &&
                currentGame.getOuterFieldById((outerFieldId + 2) % 3 + (3 * row)) == PlayerType.EMPTY) ||
                (currentGame.getOuterFieldById((outerFieldId + 2) % 3 + (3 * row)) == playerExecutingMove &&
                        currentGame.getOuterFieldById((outerFieldId + 1) % 3 + (3 * row)) == PlayerType.EMPTY)) {
            score += SCORE_TO_RECEIVE;
        }

        if (outerFieldId == 0 || outerFieldId == 4 || outerFieldId == 8) {
            if ((currentGame.getOuterFieldById((outerFieldId + 4) % 12) == playerExecutingMove &&
                    currentGame.getOuterFieldById((outerFieldId + 8) % 12) == PlayerType.EMPTY) ||
                    (currentGame.getOuterFieldById((outerFieldId + 8) % 12) == playerExecutingMove &&
                            currentGame.getOuterFieldById((outerFieldId + 4) % 12) == PlayerType.EMPTY)) {
                score += SCORE_TO_RECEIVE;
            }
        }

        if (outerFieldId == 2 || outerFieldId == 4 || outerFieldId == 6) {
            if ((currentGame.getOuterFieldById((outerFieldId + 2) < 8 ? outerFieldId + 2 : outerFieldId - 4) == playerExecutingMove &&
                    currentGame.getOuterFieldById((outerFieldId + 4) < 8 ? outerFieldId + 4 : outerFieldId - 2) == PlayerType.EMPTY) ||
                    (currentGame.getOuterFieldById((outerFieldId + 4) < 8 ? outerFieldId + 4 : outerFieldId - 2) == playerExecutingMove &&
                            currentGame.getOuterFieldById((outerFieldId + 2) < 8 ? outerFieldId + 2 : outerFieldId - 4) == PlayerType.EMPTY)) {
                score += SCORE_TO_RECEIVE;
            }
        }


        return score;
    }

    //outerField: deny win possibility
    private int outerFieldDestroyTwoInRow(Move move) {
        final int SCORE_TO_RECEIVE = 100000;

        int score = 0;
        int outerFieldId = move.getOuterFieldId();

        if (currentGame.getOuterFieldById((outerFieldId + 3) % 9) == enemy &&
                currentGame.getOuterFieldById((outerFieldId + 6) % 9) == enemy) {
            //vertical winning
            score += SCORE_TO_RECEIVE;
        }

        int row = outerFieldId / 3;
        if (currentGame.getOuterFieldById((outerFieldId + 1) % 3 + (3 * row)) == enemy &&
                currentGame.getOuterFieldById((outerFieldId + 2) % 3 + (3 * row)) == enemy) {
            score += SCORE_TO_RECEIVE;
        }

        if (outerFieldId == 0 || outerFieldId == 4 || outerFieldId == 8) {
            if (currentGame.getOuterFieldById((outerFieldId + 4) % 12) == enemy &&
                    currentGame.getOuterFieldById((outerFieldId + 8) % 12) == enemy) {
                score += SCORE_TO_RECEIVE;
            }
        }

        if (outerFieldId == 2 || outerFieldId == 4 || outerFieldId == 6) {
            if (currentGame.getOuterFieldById((outerFieldId + 2) < 8 ? outerFieldId + 2 : outerFieldId - 4) == enemy &&
                    currentGame.getOuterFieldById((outerFieldId + 4) < 8 ? outerFieldId + 4 : outerFieldId - 2) == enemy) {
                score += SCORE_TO_RECEIVE;
            }
        }


        return score;
    }

    ////possiblity to deny a possibility of two outerFIelds in a row
    private int destroyTwoInRowOuterPossiblity(Move move) {
        final int SCORE_TO_RECEIVE = 50000;

        int score = 0;
        int outerFieldId = move.getOuterFieldId();

        if ((currentGame.getOuterFieldById((outerFieldId + 3) % 9) == enemy && currentGame.getOuterFieldById((outerFieldId + 6) % 9) == PlayerType.EMPTY) ||
                (currentGame.getOuterFieldById((outerFieldId + 6) % 9) == enemy && currentGame.getOuterFieldById((outerFieldId + 3) % 9) == PlayerType.EMPTY)) {
            //vertical winning
            score += SCORE_TO_RECEIVE;
        }

        int row = outerFieldId / 3;
        if ((currentGame.getOuterFieldById((outerFieldId + 1) % 3 + (3 * row)) == enemy &&
                currentGame.getOuterFieldById((outerFieldId + 2) % 3 + (3 * row)) == PlayerType.EMPTY) ||
                (currentGame.getOuterFieldById((outerFieldId + 2) % 3 + (3 * row)) == enemy &&
                        currentGame.getOuterFieldById((outerFieldId + 1) % 3 + (3 * row)) == PlayerType.EMPTY)) {
            score += SCORE_TO_RECEIVE;
        }

        if (outerFieldId == 0 || outerFieldId == 4 || outerFieldId == 8) {
            if ((currentGame.getOuterFieldById((outerFieldId + 4) % 12) == enemy &&
                    currentGame.getOuterFieldById((outerFieldId + 8) % 12) == PlayerType.EMPTY) ||
                    (currentGame.getOuterFieldById((outerFieldId + 8) % 12) == enemy &&
                            currentGame.getOuterFieldById((outerFieldId + 4) % 12) == PlayerType.EMPTY)) {
                score += SCORE_TO_RECEIVE;
            }
        }

        if (outerFieldId == 2 || outerFieldId == 4 || outerFieldId == 6) {
            if ((currentGame.getOuterFieldById((outerFieldId + 2) < 8 ? outerFieldId + 2 : outerFieldId - 4) == enemy &&
                    currentGame.getOuterFieldById((outerFieldId + 4) < 8 ? outerFieldId + 4 : outerFieldId - 2) == PlayerType.EMPTY) ||
                    (currentGame.getOuterFieldById((outerFieldId + 4) < 8 ? outerFieldId + 4 : outerFieldId - 2) == enemy &&
                            currentGame.getOuterFieldById((outerFieldId + 2) < 8 ? outerFieldId + 2 : outerFieldId - 4) == PlayerType.EMPTY)) {
                score += SCORE_TO_RECEIVE;
            }
        }
        return score;
    }

    //outerField: PlacementScores
    private int getOuterFieldPlacementScores(Move move, boolean innerFieldWon) {
        int multiplier = innerFieldWon ? 1000 : 1;
        switch (move.getOuterFieldId()) {
            case 4:
                //middle field
                return 10 * multiplier;
            case 0:
            case 2:
            case 6:
            case 8:
                //corner fields
                return 5 * multiplier;
            default:
                //side fields
                return multiplier;
        }
    }

    //-innerField: win
    private int winsInnerField(Move move) {
        final int SCORE_TO_RECEIVE = 10000;

        int score = 0;
        int innerFieldId = move.getInnerFieldId();
        boolean winsInnerField = false;
        InnerField innerField = currentGame.getInnerFieldStateById(move.getOuterFieldId());

        if (innerField.getFieldStatusById((innerFieldId + 3) % 9) == playerExecutingMove &&
                innerField.getFieldStatusById((innerFieldId + 6) % 9) == playerExecutingMove) {
            //vertical winning
            score += SCORE_TO_RECEIVE;
            winsInnerField = true;
        }

        int row = innerFieldId / 3;
        if (innerField.getFieldStatusById((innerFieldId + 1) % 3 + (3 * row)) == playerExecutingMove &&
                innerField.getFieldStatusById((innerFieldId + 2) % 3 + (3 * row)) == playerExecutingMove) {
            //horizontal winning
            score += SCORE_TO_RECEIVE;
            winsInnerField = true;
        }

        if (innerFieldId == 0 || innerFieldId == 4 || innerFieldId == 8) {
            if (innerField.getFieldStatusById((innerFieldId + 4) % 12) == playerExecutingMove &&
                    innerField.getFieldStatusById((innerFieldId + 8) % 12) == playerExecutingMove) {
                score += SCORE_TO_RECEIVE;
                winsInnerField = true;
            }
        }

        if (innerFieldId == 2 || innerFieldId == 4 || innerFieldId == 6) {
            if (innerField.getFieldStatusById((innerFieldId + 2) < 8 ? innerFieldId + 2 : innerFieldId - 4) == playerExecutingMove &&
                    innerField.getFieldStatusById((innerFieldId + 4) < 8 ? innerFieldId + 4 : innerFieldId - 2) == playerExecutingMove) {
                score += SCORE_TO_RECEIVE;
                winsInnerField = true;
            }
        }

        if (winsInnerField) {
            score += winsGame(move);
            score += getTwoOuterFieldInRow(move);
            if (difficulty.isEqualOrHigherDifficultyThan(Difficulty.MEDIUM)) {
                score += outerFieldDestroyTwoInRow(move);
                if(difficulty.isEqualOrHigherDifficultyThan(Difficulty.HARD)){
                    score += destroyTwoInRowOuterPossiblity(move);
                    score += getOuterFieldPlacementScores(move, true);
                }
            }
        }

        return score;
    }

    //-innerField: get 2 in row
    private int getTwoInRowScore(Move move) {
        final int SCORE_TO_RECEIVE = 100;
        int score = 0;
        int innerFieldId = move.getInnerFieldId();
        InnerField innerField = currentGame.getInnerFieldStateById(move.getOuterFieldId());

        if ((innerField.getFieldStatusById((innerFieldId + 3) % 9) == playerExecutingMove && innerField.getFieldStatusById((innerFieldId + 6) % 9) == PlayerType.EMPTY)
                ||
                (innerField.getFieldStatusById((innerFieldId + 6) % 9) == playerExecutingMove && innerField.getFieldStatusById((innerFieldId + 3) % 9) == PlayerType.EMPTY)) {
            //vertical winning
            score += SCORE_TO_RECEIVE;
        }

        int row = innerFieldId / 3;
        if ((innerField.getFieldStatusById((innerFieldId + 1) % 3 + (3 * row)) == playerExecutingMove &&
                innerField.getFieldStatusById((innerFieldId + 2) % 3 + (3 * row)) == PlayerType.EMPTY) ||
                (innerField.getFieldStatusById((innerFieldId + 2) % 3 + (3 * row)) == playerExecutingMove &&
                        innerField.getFieldStatusById((innerFieldId + 1) % 3 + (3 * row)) == PlayerType.EMPTY)) {
            //horizontal winning
            score += SCORE_TO_RECEIVE;
        }

        if (innerFieldId == 0 || innerFieldId == 4 || innerFieldId == 8) {
            if ((innerField.getFieldStatusById((innerFieldId + 4) % 12) == playerExecutingMove && innerField.getFieldStatusById((innerFieldId + 8) % 12) == PlayerType.EMPTY) ||
                    (innerField.getFieldStatusById((innerFieldId + 8) % 12) == playerExecutingMove && innerField.getFieldStatusById((innerFieldId + 4) % 12) == PlayerType.EMPTY)) {
                score += SCORE_TO_RECEIVE;
            }
        }

        if (innerFieldId == 2 || innerFieldId == 4 || innerFieldId == 6) {
            if ((innerField.getFieldStatusById((innerFieldId + 2) < 8 ? innerFieldId + 2 : innerFieldId - 4) == playerExecutingMove &&
                    innerField.getFieldStatusById((innerFieldId + 4) < 8 ? innerFieldId + 4 : innerFieldId - 2) == PlayerType.EMPTY) ||
                    (innerField.getFieldStatusById((innerFieldId + 4) < 8 ? innerFieldId + 4 : innerFieldId - 2) == playerExecutingMove &&
                            innerField.getFieldStatusById((innerFieldId + 2) < 8 ? innerFieldId + 2 : innerFieldId - 4) == PlayerType.EMPTY)) {
                score += SCORE_TO_RECEIVE;
            }
        }

        return score;
    }

    //-innerField: deny win possibility
    private int destroyTwoInRowForEnemy(Move move) {
        final int SCORE_TO_RECEIVE = 100;
        //TODO maybe consider if 2 TwoInRow possible for enemy, maybe make him focus more on himself than destroing the "Zwickmühle"

        int score = 0;
        int innerFieldId = move.getInnerFieldId();
        InnerField innerField = currentGame.getInnerFieldStateById(move.getOuterFieldId());

        if (innerField.getFieldStatusById((innerFieldId + 3) % 9) == enemy &&
                innerField.getFieldStatusById((innerFieldId + 6) % 9) == enemy) {
            //vertical winning
            score += SCORE_TO_RECEIVE;
        }

        int row = innerFieldId / 3;
        if (innerField.getFieldStatusById((innerFieldId + 1) % 3 + (3 * row)) == enemy &&
                innerField.getFieldStatusById((innerFieldId + 2) % 3 + (3 * row)) == enemy) {
            //horizontal winning
            score += SCORE_TO_RECEIVE;
        }

        if (innerFieldId == 0 || innerFieldId == 4 || innerFieldId == 8) {
            if (innerField.getFieldStatusById((innerFieldId + 4) % 12) == enemy &&
                    innerField.getFieldStatusById((innerFieldId + 8) % 12) == enemy) {
                score += SCORE_TO_RECEIVE;
            }
        }

        if (innerFieldId == 2 || innerFieldId == 4 || innerFieldId == 6) {
            if (innerField.getFieldStatusById((innerFieldId + 2) < 8 ? innerFieldId + 2 : innerFieldId - 4) == enemy &&
                    innerField.getFieldStatusById((innerFieldId + 4) < 8 ? innerFieldId + 4 : innerFieldId - 2) == enemy) {
                score += SCORE_TO_RECEIVE;
            }
        }

        return score;
    }

    //possiblity to deny a possibility of two in a row
    private int destroyTwoInRowPossiblity(Move move) {
        final int SCORE_TO_RECEIVE = 50;
        int score = 0;
        int innerFieldId = move.getInnerFieldId();
        InnerField innerField = currentGame.getInnerFieldStateById(move.getOuterFieldId());

        if ((innerField.getFieldStatusById((innerFieldId + 3) % 9) == enemy && innerField.getFieldStatusById((innerFieldId + 6) % 9) == PlayerType.EMPTY)
                ||
                (innerField.getFieldStatusById((innerFieldId + 6) % 9) == enemy && innerField.getFieldStatusById((innerFieldId + 3) % 9) == PlayerType.EMPTY)) {
            //vertical winning
            score += SCORE_TO_RECEIVE;
        }

        int row = innerFieldId / 3;
        if ((innerField.getFieldStatusById((innerFieldId + 1) % 3 + (3 * row)) == enemy &&
                innerField.getFieldStatusById((innerFieldId + 2) % 3 + (3 * row)) == PlayerType.EMPTY) ||
                (innerField.getFieldStatusById((innerFieldId + 2) % 3 + (3 * row)) == enemy &&
                        innerField.getFieldStatusById((innerFieldId + 1) % 3 + (3 * row)) == PlayerType.EMPTY)) {
            //horizontal winning
            score += SCORE_TO_RECEIVE;
        }

        if (innerFieldId == 0 || innerFieldId == 4 || innerFieldId == 8) {
            if ((innerField.getFieldStatusById((innerFieldId + 4) % 12) == enemy && innerField.getFieldStatusById((innerFieldId + 8) % 12) == PlayerType.EMPTY) ||
                    (innerField.getFieldStatusById((innerFieldId + 8) % 12) == enemy && innerField.getFieldStatusById((innerFieldId + 4) % 12) == PlayerType.EMPTY)) {
                score += SCORE_TO_RECEIVE;
            }
        }

        if (innerFieldId == 2 || innerFieldId == 4 || innerFieldId == 6) {
            if ((innerField.getFieldStatusById((innerFieldId + 2) < 8 ? innerFieldId + 2 : innerFieldId - 4) == enemy &&
                    innerField.getFieldStatusById((innerFieldId + 4) < 8 ? innerFieldId + 4 : innerFieldId - 2) == PlayerType.EMPTY) ||
                    (innerField.getFieldStatusById((innerFieldId + 4) < 8 ? innerFieldId + 4 : innerFieldId - 2) == enemy &&
                            innerField.getFieldStatusById((innerFieldId + 2) < 8 ? innerFieldId + 2 : innerFieldId - 4) == PlayerType.EMPTY)) {
                score += SCORE_TO_RECEIVE;
            }
        }

        return score;
    }

    //innerField: PlacementScores
    private int getInnerFieldPlacementScore(Move move) {
        //TODO maybe apply multiplicative instead of additive
        switch (move.getInnerFieldId()) {
            case 4:
                //middle field
                return 10;
            case 0:
            case 2:
            case 6:
            case 8:
                //corner fields
                return 5;
            default:
                //side fields
                return 1;
        }
    }
}
