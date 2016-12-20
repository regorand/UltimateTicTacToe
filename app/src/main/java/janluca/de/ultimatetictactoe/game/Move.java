package janluca.de.ultimatetictactoe.game;


public class Move{

    private int outerFieldId,
            innerFieldId;

    public Move(int outerFieldId, int innerFieldId){
        this.outerFieldId = outerFieldId;
        this.innerFieldId = innerFieldId;
    }

    public int getOuterFieldId(){
        return outerFieldId;
    }

    public int getInnerFieldId(){
        return innerFieldId;
    }

}
