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

    public static  int compute(){
        throw new RuntimeException("something went wrong");
        //return 2;
    }

    public static CompletableFuture<Integer> create(){

        return CompletableFuture.supplyAsync(()->compute());
    }

    public   static  Void  handleException(Throwable throwable){

        System.out.println("ERROR: "+throwable);
        throw new RuntimeException("it is beyond all hope");
    }

    public   static  int  handleException2(Throwable throwable){

        System.out.println("ERROR: "+throwable);
        return -1;
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
     * */

        create().thenApply(data->data*2)
                .exceptionally(throwable -> handleException2(throwable))
                .thenAccept(data->System.out.println(data))
                .exceptionally(throwable -> handleException(throwable));


    }
}
