package ai;

import java.util.ArrayList;
import java.util.function.IntPredicate;

public class Result {
    private final ArrayList<Score> results;
    public Result() {
        results = new ArrayList<>();
    }

    public boolean satisfy(ResultType type, IntPredicate predicate){
        for (Score pair : results)
            if (pair.type() == type)
                return predicate.test(pair.value());
        return predicate.test(0);
    }

    public boolean satisfy(ResultType type){
        return satisfy(type, v -> v > 0);
    }


    public int getValue(ResultType type) {
        for (Score pair : results)
            if (pair.type() == type)
                return pair.value();
        return 0;
    }
    public int getDepth(ResultType type){
        for (Score pair : results)
            if (pair.type() == type)
                return pair.depth();
        return Integer.MAX_VALUE;
    }

    public void add(ResultType type, int i){
        add(type, i, 0);
    }

    public void add(ResultType type, int i, int d){
        if(i == 0)  return;
        for (Score score : results)
            if (score.type() == type)
            {
                score.setValue(score.value() + i);
                score.setDepth(Math.max(score.depth(), d));
               return;
            }
        results.add(new Score(type, i, d));
    }

    public boolean isEmpty(){
        return results.isEmpty();
    }

    @Override
    public String toString() {
        return results.toString();
    }
}
