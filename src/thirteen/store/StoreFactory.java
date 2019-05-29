package thirteen.store;

import java.util.ArrayList;
import java.util.List;

public class StoreFactory {
    static private Store store;

    public StoreFactory(int size){

        List<List<Integer>> gameboard = new ArrayList<List<Integer>>(size);

        for (int i = 0; i < size; i++) {
             gameboard.add(new ArrayList<Integer>(size));

             for (int j = 0; j < size; j++) {

                 int value = 0;

                 gameboard.get(i).add(j, value);

             }
        }
            store = new Store(gameboard, 0, 0, new ArrayList<int[]>());
    }
    public static Store getStore(){

        return store;
    }
}

