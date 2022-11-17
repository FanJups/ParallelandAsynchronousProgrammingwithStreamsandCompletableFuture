package venkat.subramaniam.ParallelandAsynchronousProgrammingwithStreamsandCompletableFuture.asynchronous;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;

public class Sample {

    //Non Blocking

    public static CompletableFuture<Integer> create(){
        ForkJoinPool pool = new ForkJoinPool(10);
        return CompletableFuture.supplyAsync(()->compute(),pool);
    }

    public  static int compute(){
       //sleep(1000);
       return 2;
    }

    private static boolean sleep(int ms) {

        try {
            Thread.sleep(ms);
            return true;
        } catch (InterruptedException e) {
            return false;
        }

    }
    public static void printIt (int value){
        System.out.println(value + " -- "+ Thread.currentThread());
    }

    public  static  void main(String[] args) throws ExecutionException, InterruptedException {

        //Famous or popular functional interfaces

        //Stream
        //Supplier<T> T get()                - factories
        //Predicate boolean test(T)          - filter
        //Function <T,R> R apply(T)          - map
        //Consumer<T> void accept(T)         - forEach

     create().thenAccept(System.out::println).thenRun(()->System.out.println("That went well"));


    }
}
