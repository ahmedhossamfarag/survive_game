package ai;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;

public class Algorithm {
    private final List<AIAction> actions;
    private final ArrayList<Predicate<AIAction>>  filters;
    private final ArrayList<ToIntFunction<AIAction>> minimums, maximums;

    public Algorithm(List<AIAction> actions) {
        this.actions = actions;
        filters = new ArrayList<>();
        minimums = new ArrayList<>();
        maximums = new ArrayList<>();
    }


    public AIAction operate(){
        List<AIAction> list = actions;
//        for (AIAction action : list)
//        {
//            System.out.println(action);
//            System.out.println(action.directResult);
//            System.out.println(action.inDirectResult);
//        }
//        System.out.println(".......................................................................");
        for (int i = 0; i < filters.size(); i++) {
            List<AIAction> tmp = filter(list, filters.get(i));
//            System.out.println("## " + i);
//            for (AIAction action : tmp)
//            {
//                System.out.println(action);
//                System.out.println(action.directResult);
//                System.out.println(action.inDirectResult);
//            }
//            System.out.println(".......................................................................");
            if (tmp.isEmpty()) continue;
            if (tmp.size() == 1) return tmp.get(0);
            if (minimums.get(i) != null)
            {
                tmp = min(tmp, minimums.get(i));
//                for (AIAction action : tmp)
//                {
//                    System.out.println(action);
//                    System.out.println(action.directResult);
//                    System.out.println(action.inDirectResult);
//                }
//                System.out.println(".......................................................................");
                if (tmp.size() == 1) return tmp.get(0);
            }
            if (maximums.get(i) != null)
            {
                tmp = max(tmp, maximums.get(i));
//                for (AIAction action : tmp)
//                {
//                    System.out.println(action);
//                    System.out.println(action.directResult);
//                    System.out.println(action.inDirectResult);
//                }
//                System.out.println(".......................................................................");
                if (tmp.size() == 1) return tmp.get(0);
            }
            list = tmp;
        }
        if (list.isEmpty()) return new End();
        return list.get(0);
    }


    public void add(Predicate<AIAction> predicate){
        filters.add(predicate);
        minimums.add(null);
        maximums.add(null);
    }

    public void addWithMin(Predicate<AIAction> predicate, ToIntFunction<AIAction> function){
        filters.add(predicate);
        minimums.add(function);
        maximums.add(null);
    }

    public void addWithMax(Predicate<AIAction> predicate, ToIntFunction<AIAction> function){
        filters.add(predicate);
        maximums.add(function);
        minimums.add(null);
    }

    private List<AIAction> filter(List<AIAction> actions, Predicate<AIAction> predicate){
        return actions.stream().filter(predicate).toList();
    }

    private List<AIAction> min(List<AIAction> actions, ToIntFunction<AIAction> function){
        ArrayList<AIAction> result = new ArrayList<>();
        for (AIAction action : actions)
        {
            if (result.isEmpty())
                result.add(action);
            else
            {
                int t1 = function.applyAsInt(action);
                int t2 = function.applyAsInt(result.get(0));
                if (t1 == t2)
                    result.add(action);
                else if (t1 < t2) {
                    result.clear();
                    result.add(action);
                }
            }
        }
        return result;
    }

    private List<AIAction> max(List<AIAction> actions, ToIntFunction<AIAction> function){
        ArrayList<AIAction> result = new ArrayList<>();
        for (AIAction action : actions)
        {
            if (result.isEmpty())
                result.add(action);
            else
            {
                int t1 = function.applyAsInt(action);
                int t2 = function.applyAsInt(result.get(0));
                if (t1 == t2)
                    result.add(action);
                else if (t1 > t2) {
                    result.clear();
                    result.add(action);
                }
            }
        }
        return result;
    }

}
