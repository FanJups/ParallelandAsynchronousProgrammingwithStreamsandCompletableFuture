package venkat.subramaniam.ParallelandAsynchronousProgrammingwithStreamsandCompletableFuture.asynchronous;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

public class Sample {

    //Non Blocking

    private static boolean sleep(int ms) {

        try {
            Thread.sleep(ms);
            return true;
        } catch (InterruptedException e) {
            return false;
        }

    }

    public static CompletableFuture<Integer> create(){

        return CompletableFuture.supplyAsync(()->compute());
    }


    public  static int compute(){
        //sleep(1000);
        return 2;
    }


    public  static  void main(String[] args) throws ExecutionException, InterruptedException {

        //Famous or popular functional interfaces

        //Stream
        //Supplier<T> T get()                - factories
        //Predicate boolean test(T)          - filter
        //Function <T,R> R apply(T)          - map
        //Consumer<T> void accept(T)         - forEach


     /*
     * Stream                                                   CompletableFuture
     * zero, one, or more data                                  zero or one
     * only data channel                                        data channel and error channel
     * forEach                                                  thenAccept
     * map                                                      thenApply
     * pipeline                                                 pipeline
     * lazy                                                     lazy
     *exception - oops                                          error channel
     *
     *
     *
     * data channel
     *      return                  blow up                             return
     * ----------------------f---f-----f                           ------f------- thens
     *                                  \                         / return
     *                                   -------f-----f----f------f    exceptionals
     * error channel                       blow up
     *
     *
     * Both in life and programming never do something without timeout
     * */

        CompletableFuture<Integer> future =
                new CompletableFuture<>();

        future.thenApply(data -> data *2 )
                .thenApply(data-> data+1).thenAccept(data -> System.out.println(data));

        System.out.println("built the pipeline");

      //  sleep(1000);

       // future.complete(2);
        //future.completeExceptionally()
        future.completeOnTimeout(0,2, TimeUnit.SECONDS);

        sleep(1000);

      future.complete(2);

      //future.orTimeout(2,TimeUnit.SECONDS);


    }
}
