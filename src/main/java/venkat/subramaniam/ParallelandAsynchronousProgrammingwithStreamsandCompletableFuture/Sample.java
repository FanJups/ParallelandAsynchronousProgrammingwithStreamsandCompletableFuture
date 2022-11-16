package venkat.subramaniam.ParallelandAsynchronousProgrammingwithStreamsandCompletableFuture;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

public class Sample {

    // imperative style the structure of sequential code is very different
    // from the structure of concurrent style

    // using streams the structure of sequential code is identical to the structure of concurrent code
    public static void main(String[] args) throws Exception {

        //Martin Fowler: Collection Pipeline Pattern

       List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10,11, 12, 13, 14, 15, 16, 17, 18, 19, 20);

        //List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        //find the total of double of even numbers

        //  long start1= System.nanoTime();

        int total = 0;

        for (int e : numbers) {

            if (e % 2 == 0) {

                total += e * 2;
            }
        }

        System.out.println(total);

        //long end1= System.nanoTime();

        //long elapsedTime = end1 -start1;

        //System.out.println(end1 - start1);

        // imperative -> Synchronize and suffer model hahaahahah Venkat

        //imperative style has accidental complexity
        //function style has less complexity and is also easier to parallelize

        //function style - function composition

        // long start2= System.nanoTime();

        System.out.println(numbers.stream()
                .filter(e -> e % 2 == 0)
                .mapToInt(e -> e * 2)
                .sum());

        // long end2=System.nanoTime();

        //System.out.println(end2 - start2);

/*
        long startSeq=System.nanoTime();

        //System.out.println(end2 - start2);

        numbers.stream()
                .map(e->transform(e))
                .forEach(System.out::println);

        long endSeq=System.nanoTime();

        System.out.println(endSeq - startSeq);
*/

        // long startPar=System.nanoTime();

       // use(numbers.stream());


        //  long endPar=System.nanoTime();
        // System.out.println(endPar- startPar);

        //  System.out.println(((endPar- startPar)<(endSeq- startSeq)));


/*
        numbers.parallelStream()
                .map(e -> transform(e))
                .forEachOrdered(e->printIt(e));
*/

//        System.out.println(numbers.stream().reduce(30,(total1,e)->add(total1,e)));
//
//        System.out.println("-----------------------------------------------------------");
//
//        System.out.println(numbers.parallelStream().reduce(30,(total1,e)->add(total1,e)));



        //System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism","10");

        // How many cores I have on my machine
        System.out.println(Runtime.getRuntime().availableProcessors());

        // Details about the common pool
        System.out.println(ForkJoinPool.commonPool());

       // process(numbers.stream().map(e-> transform(e)));

        // find the first lady who is older than 30



        System.out.println( createPeople().parallelStream()
                .filter(p->p.getAge()>30)
                .filter(p->p.getGender().equals(Gender.FEMALE))

                .map(Person::getName).findFirst().orElse("no one else found"));







        /*
        *  https://medium.com/@peterlee2068/concurrency-and-parallelism-in-java-f625bc9b0ca4
        *
        *
        * */

        /*Configuring number of threads JVM wide
        *
        * -Djava.util.concurrent.ForkJoinPool.common.parallelism=100
        *
        * https://stackoverflow.com/questions/49009662/set-java-util-concurrent-forkjoinpool-common-parallelism-java-property
        * */



        /*
        * reduce does not take an initial value, it takes an identity value
        *
        * int  + identity is 0 x+0 =x
        * int  * identity is 1 x*1 =x
        *
        * what we work with should be a monoid
        * */

        /*
        * Computation intensive vs. IO intensive
        *
        * For computation intensive
        *
        * number of threads <= number of cores
        *
        * For IO intensive
        * number of threads may be greater than number of cores ????
        *
        *                      number of cores
        * nummberThreads <= --------------------------
        *                        1 - blocking factor
        *
        *                    0<= blocking factor < 1
        * */


        /*
        * If the stream is unordered there is no garantee of ordering -> Set
        * there is a garantee of ordering if the stream is ordered -> List
        * */


        /*
        Some methods are inherently ordered

        * Some methods are unordered but may have an ordered counterpart : foreach Ordered
        * */



    }

    public  static void process(Stream<Integer> stream) throws Exception {

        ForkJoinPool pool = new ForkJoinPool(100);
        pool.submit(()->stream.forEach(e->{}));

        pool.shutdown();
        pool.awaitTermination(10, TimeUnit.SECONDS);


    }

    public static int transform(int number) {
        /*
        * We solve one set of problems only to create a new set of problems, this has been my life lesson - Venkat S 33:21
        *
        * Java 1- Threads
        * Java 5 - ExecutorServices
        *
        * Pool induced deadlock
        *
        * Workstealing - fork join pool
        * Java 7 - Fork Join Pool FJP
        *
        * Common FJP
        * */

       System.out.println("t: "+number+"--"+ Thread.currentThread());
        sleep(1000);
        return number;
    }

    public static int add(int a,int b) {
       int result =a+b;

        System.out.println("a= "+a+" b= "+b+" result= "+result+" --"+ Thread.currentThread());

        return result;
    }

    public static boolean check(int number) {

        System.out.println("c: "+number+"--"+ Thread.currentThread());
        sleep(1000);
        return true;
    }

    public static void printIt(int number) {

        System.out.println("p: "+number+"--"+ Thread.currentThread());

    }

    private static boolean sleep(int ms) {

        try {
            Thread.sleep(ms);
            return true;
        } catch (InterruptedException e) {
            return false;
        }

    }

    public static void use(Stream<Integer> stream) {

 /*       stream.sequential().map(e -> transform(e))
                .forEach(System.out::println);
*/
     /*   stream.parallel().map(e -> transform(e))
                .forEach(System.out::println);
*/
/*
        stream.parallel() //no op because of sequential below
                .map(e -> transform(e))
               // .sequential()
                .forEach(System.out::println);
*/

        stream.parallel() //no op because of sequential below
                .map(e -> transform(e))
                .sequential()
                .forEach(System.out::println);

        /*
        *
        * Streams                                           Reactive Streams
        *
        * 1- sequential or parallel                         1-sync vs async
        *
        * 2- entire pipeline is seq or par                  2-  Depends
        * no segments                                        subscribeOn - no segments
        *                                                    observeOn - segments
        *
        * */
    }

    public static List<Person> createPeople(){

        return Arrays.asList(new Person("Sara",Gender.FEMALE,20),
                new Person("Sara",Gender.FEMALE,22),
                new Person("Bob",Gender.MALE,20),
                new Person("Paula",Gender.FEMALE,32),
                new Person("Paul",Gender.MALE,32),
                new Person("Jack",Gender.MALE,2),
                new Person("Jack",Gender.MALE,72),
                new Person("Jill",Gender.FEMALE,42));


    }

    static class Person{

        String name;
        Gender gender;
        int age;

        public Person(String name, Gender gender, int age) {
            this.name = name;
            this.gender = gender;
            this.age = age;
        }

        String getName() {
            return name;
        }

        Gender getGender() {
            return gender;
        }

        int getAge() {
            System.out.println("age for " +name);
            return age;
        }
    }

    enum Gender{

        FEMALE,MALE;
    }

}
