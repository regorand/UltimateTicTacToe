package janluca.de.ultimatetictactoe.AI;

public enum Difficulty {
    VERY_EASY(1),
    EASY(2),
    MEDIUM(3),
    HARD(4);

    private int difficultyValue;

    Difficulty(int difficultyValue){
        this.difficultyValue = difficultyValue;
    }

    public int getDifficultyValue(){
        return difficultyValue;
    }

    public boolean isEqualOrHigherDifficultyThan(Difficulty compareDifficulty){
        return this.difficultyValue >= compareDifficulty.getDifficultyValue();
    }
}
