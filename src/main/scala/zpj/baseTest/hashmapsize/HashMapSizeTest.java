package zpj.baseTest.hashmapsize;

import java.util.HashSet;
import java.util.Set;

class TestHashSet1 {
    private static int NUMBER_OF_THREADS = 5;
    private static int COLLECTION_SIZE = 100000;
    //HashMap.size()会返回负数的
    public static void main(String[] arg) {
        final Set<Integer> toRemoveElement = new HashSet<Integer>();
        final Set<Integer> toStoreElements = new HashSet<Integer>();
        // populate collections for test
        for (int i = 0; i < COLLECTION_SIZE; i++) {
            Integer obj = new Integer(i);
            toRemoveElement.add(obj);
            toStoreElements.add(obj);
        }
        // two threads that will be using collection2
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                for (Integer o : toStoreElements) {
                    removeObject(toRemoveElement, o);
                }
            }
        };
        Thread[] treads = new Thread[NUMBER_OF_THREADS];
        for (int i = 0; i < treads.length; i++) {
            treads[i] = new Thread(runnable);
        }
        for (Thread t : treads) {
            t.start();
        }
    }

    private static void removeObject(Set<Integer> toRemoveElement, Integer o) {
        toRemoveElement.remove(o);
        int size = toRemoveElement.size();
        if (size < 0) {
            System.out.println(size);
            try {
                toRemoveElement.toArray(new Integer[toRemoveElement.size()]);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
