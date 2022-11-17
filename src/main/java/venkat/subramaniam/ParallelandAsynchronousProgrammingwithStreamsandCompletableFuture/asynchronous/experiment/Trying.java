package venkat.subramaniam.ParallelandAsynchronousProgrammingwithStreamsandCompletableFuture.asynchronous.experiment;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class Trying {

    // https://stackoverflow.com/questions/49839557/completablefuture-several-tasks

    public static CompletableFuture<String> method(String s){
        return CompletableFuture.supplyAsync(()->s);
    }

    public  static  void main(String[] args) throws ExecutionException, InterruptedException {


        //method("A");

//        CompletableFuture.runAsync(()->{
//
//            for(int i=0;i<10;i++){
//                System.out.println("A");
//
//            }
//        }).get();



        CompletableFuture<Void> future1 = CompletableFuture.runAsync(()->{

            for(int i=0;i<10;i++){
             System.out.println("A");

           }

        });
        CompletableFuture<Void> future2 = CompletableFuture.runAsync(()->{

            for(int i=0;i<10;i++){
                System.out.println("B");

            }

        });
        CompletableFuture<Void> future3 = CompletableFuture.runAsync(()->{

            for(int i=0;i<10;i++){
                System.out.println("C");

            }

        });

        CompletableFuture<Void> combined = CompletableFuture.allOf(future1, future2, future3);

        combined.get();







    }
}
