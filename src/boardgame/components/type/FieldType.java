package boardgame.components.type;

public enum FieldType {
    START('S',true),
    STANDARD('#',false),
    META('M',true),
    EVENT('E', false),
    EMPTY((char)0,true);

    private char view;
    private boolean encountered;

    FieldType(char view, boolean encountered) {
        this.view =view;
        this.encountered = encountered;

    }

    public char getView() {
        return view;
    }

    public boolean isEncountered() {
        return encountered;
    }

    @Override
    public String toString() {
        return view + "";
    }
}
