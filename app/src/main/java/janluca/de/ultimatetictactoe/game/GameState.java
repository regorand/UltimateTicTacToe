package janluca.de.ultimatetictactoe.game;

import java.util.ArrayList;

public class GameState {

    private PlayerType[] outerFieldStatus;

    private InnerFieldState[]  innerFieldStates;

    private int activeOuterField;

    public GameState(PlayerType[] outerFields, PlayerType[][] innerFields, int activeOuterField, boolean[] innerFieldsOver){
        outerFieldStatus = outerFields;
        innerFieldStates = new InnerFieldState[9];
        for (int i = 0; i < innerFieldStates.length; i++){
            innerFieldStates[i] = new InnerFieldState(innerFields[i], innerFieldsOver[i]);
        }
        this.activeOuterField = activeOuterField;
    }

    public InnerFieldState getInnerFieldStateById(int id){
        return innerFieldStates[id];
    }

    public PlayerType[] getOuterFieldStatus() {
        return outerFieldStatus;
    }

    public PlayerType getOuterFieldStatusById(int id) {
        return outerFieldStatus[id];
    }

    public InnerFieldState[] getInnerFieldStates() {
        return innerFieldStates;
    }

    public int getActiveOuterField() {
        return activeOuterField;
    }

    public void addMoveToGameState(Move move, PlayerType playerType){
        int innerFieldId = move.getInnerFieldId();
        innerFieldStates[move.getOuterFieldId()].setStateById(playerType, innerFieldId);

        if(!innerFieldStates[innerFieldId].isOver()){
            activeOuterField = innerFieldId;
        } else {
            activeOuterField = -1;
        }
    }

    public Move[] getAllowedMoves(){
        ArrayList<Move> allowedMoves = new ArrayList<>();
        if(activeOuterField != -1){
            PlayerType[] innerPlayerTypes = innerFieldStates[activeOuterField].getStates();
            for(int i = 0; i < innerPlayerTypes.length; i++){
                if(innerPlayerTypes[i] == PlayerType.EMPTY){
                    allowedMoves.add(new Move(activeOuterField, i));
                }
            }
        } else {
            for (int i = 0; i < innerFieldStates.length ; i++) {
                if(!innerFieldStates[i].isOver()){
                    PlayerType[] innerPlayerTypes = innerFieldStates[i].getStates();
                    for(int j = 0; j < innerPlayerTypes.length; j++){
                        if(innerPlayerTypes[j] == PlayerType.EMPTY){
                            allowedMoves.add(new Move(i, j));
                        }
                    }
                }
            }{

            }
        }
        return (Move[]) allowedMoves.toArray();
    }
}
