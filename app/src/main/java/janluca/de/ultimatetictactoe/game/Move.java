package janluca.de.ultimatetictactoe.game;


public class Move{

    private int outerFieldId,
            innerFieldId,
            score;

    public Move(int outerFieldId, int innerFieldId){
        this.outerFieldId = outerFieldId;
        this.innerFieldId = innerFieldId;
        score = 0;
    }

    public void setScore(int score){
        this.score = score;
    }

    public int getScore(){
        return score;
    }

    public int getOuterFieldId(){
        return outerFieldId;
    }

    public int getInnerFieldId(){
        return innerFieldId;
    }

}
