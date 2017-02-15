package janluca.de.ultimatetictactoe.AI;

public enum Difficulty {
    VERY_EASY(1, 2, 1),
    EASY(2, 6, 0.6),
    MEDIUM(3, 10, 0.4),
    HARD(4, 12, 0.1);

    /*
    1000 games:
    VERY_EASY(1, 6, 0.5) vs EASY(2, 6, 1):
    VERY_EASY: 130 wins
    EASY: 669 wins
    DRAWS: 201
    */

    private int difficultyValue;

    //errorRate is a little counter-intuitive. A value of 1 will mean no errors, then the chance of an error will decrease with increasing errorRate
    private int errorRate;

    private double errorImpact;

    Difficulty(int difficultyValue, int errorRate, double errorImpact){
        this.difficultyValue = difficultyValue;
        if(errorRate < 1){
            this.errorRate = 1;
        } else {
            this.errorRate = errorRate;
        }
        this.errorImpact = errorImpact;
    }

    public int getErrorRate(){
        return errorRate;
    }

    public double getErrorImpact(){
        return errorImpact;
    }

    public int getDifficultyValue(){
        return difficultyValue;
    }

    public boolean isEqualOrHigherDifficultyThan(Difficulty compareDifficulty){
        return this.difficultyValue >= compareDifficulty.getDifficultyValue();
    }
}
