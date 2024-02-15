package ai;

public class Score {
    private final ResultType type ;
    private int value, depth;

    public Score(ResultType type, int value, int depth) {
        this.type = type;
        this.value = value;
        this.depth = depth;
    }

    public ResultType type(){
        return type;
    }

    public int value(){
        return value;
    }

    public int depth(){
        return depth;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    @Override
    public String toString() {
        return type + " $ " + value + " $ " + depth;
    }
}
