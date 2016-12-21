package janluca.de.ultimatetictactoe.game;

public class GameState {

    private FieldState[] outerFieldStatus;

    private InnerFieldState[]  innerFieldStates;

    private int activeOuterField;

    public GameState(FieldState[] outerFields, FieldState[][] innerFields, int activeOuterField, boolean[] innerFieldsOver){
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

    public FieldState[] getOuterFieldStatus() {
        return outerFieldStatus;
    }

    public InnerFieldState[] getInnerFieldStates() {
        return innerFieldStates;
    }

    public int getActiveOuterField() {
        return activeOuterField;
    }
}
