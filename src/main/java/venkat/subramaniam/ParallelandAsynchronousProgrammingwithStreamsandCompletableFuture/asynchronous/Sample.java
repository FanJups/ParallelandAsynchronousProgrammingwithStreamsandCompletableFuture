package venkat.subramaniam.ParallelandAsynchronousProgrammingwithStreamsandCompletableFuture.asynchronous;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class Sample {

    //Non Blocking

    public static CompletableFuture<Integer> create(){
        return CompletableFuture.supplyAsync(()->2);
    }

    public  static int compute(){

//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
       return 3;
    }

    public static CompletableFuture<Integer> createCompute(){
        return CompletableFuture.supplyAsync(()->compute());
    }

    public  static  void main(String[] args) throws ExecutionException, InterruptedException {



        //Famous or popular functional interfaces

                                             //Stream
        //Supplier<T> T get()                - factories
        //Predicate boolean test(T)          - filter
        //Function <T,R> R apply(T)          - map
        //Consumer<T> void accept(T)         - forEach

        create()
                .thenAccept(System.out::println)
                .thenRun(()-> System.out.println("This never dies"))
                .thenRun(()-> System.out.println("Really, this never dies"))
                .thenRun(()-> System.out.println("Really, really, this never dies"));

        System.out.println("got it");

        Thread.sleep(100);

        System.out.println(createCompute().getNow(0)); //Bad idea, the best thing to do with get is to forget.blocking call

        System.out.println("here");



    }
}
