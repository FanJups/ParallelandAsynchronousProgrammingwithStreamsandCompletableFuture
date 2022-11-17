package venkat.subramaniam.ParallelandAsynchronousProgrammingwithStreamsandCompletableFuture.asynchronous;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;

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
     *
     *
     *
     * */

        CompletableFuture<Integer> future =
                new CompletableFuture<>();

                    future.thenApply(data -> data *2 )
                        .thenApply(data-> data+1).thenAccept(data -> System.out.println(data));

        System.out.println("built the pipeline");
        
        sleep(1000);

        future.complete(2);

        sleep(1000);




    }
}
