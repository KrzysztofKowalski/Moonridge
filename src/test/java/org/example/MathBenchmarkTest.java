package org.example;

import jdk.incubator.vector.IntVector;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class MathBenchmarkTest {

    public record BenchmarkResult(int iterationCount,
                                  long benchmarkTime,
                                  String operationName) {
    }


    private static final Vector<BenchmarkResult> out = new Vector<>();

    private static final int[] ITERATIONS = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10
            , 20, 30, 40, 50, 60, 70,
            100, 1000,
            100_000, 1_000_000, 100_000_000

    };
    private static final Vector<Integer> arrayList = new Vector<>(100_000_000);
    private static final Vector<Integer> vectorList = new Vector<>(100_000_000);

    private static MathBenchmark benchmark;
    private static float[] a = new float[100_000_000], b = new float[100_000_000], c = new float[100_000_000];

    @BeforeAll
    public static void setUp() {
        // Initialize resources or set up the test environment
        benchmark = new MathBenchmark();

        int length = ITERATIONS.length;
        Random random = new Random();
        for (int j = 0; j < 100_000_000; j++) {
            int i = random.nextInt();
            vectorList.add(j, i);
            arrayList.add(j, i);
            a[j] = random.nextFloat();
            b[j] = random.nextFloat();
            c[j] = random.nextFloat();
        }
    }

    @AfterAll
    public static void afterAll() {
        Map<Integer, List<BenchmarkResult>> collect = out.stream().collect(Collectors.groupingBy(BenchmarkResult::iterationCount));


        // Create a data file to store performance data for gnuplot
        try {
            FileWriter writer = null;
            writer = new FileWriter("performance_data.txt");
            Integer integer = collect.keySet().stream().findFirst().orElseThrow();
            List<BenchmarkResult> collect1 = collect.get(integer);
            List<String> collect2 = collect1.stream().map(BenchmarkResult::operationName).sorted().collect(Collectors.toList());

            StringJoiner stringJoiner = new StringJoiner(", ");

            for (String s : collect2) {
                stringJoiner.add(s);
            }

            writer.write("# " + stringJoiner.toString());
            writer.write("\n");
//            Vector<String> vector = new Vector<>();

            for (Integer e : collect.keySet().stream().sorted().toList()) {
                Vector<Integer> vi = new Vector<>();
                List<BenchmarkResult> benchmarkResults = collect.get(e);
                List<BenchmarkResult> benchmarkResultList = benchmarkResults.stream().sorted(Comparator.comparing(BenchmarkResult::operationName)).toList();
                StringJoiner stringJoiner1 = new StringJoiner(", ");
                stringJoiner1.add(String.valueOf(e));
                for (BenchmarkResult benchmarkResult : benchmarkResultList) {
                    stringJoiner1.add(String.valueOf(benchmarkResult.benchmarkTime));
                }
//                vector.add(stringJoiner1.toString());
                writer.write(stringJoiner1.toString());
                writer.write("\n");

            }
            writer.flush();
            writer.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testBenchmarkAddition() {

        int length = ITERATIONS.length;

        for (int j = 0; j < length; j++) {
            long startTime = System.nanoTime();
            int prev = 0;

            for (int i = 0; i < ITERATIONS[j]; i++) {
                int cur = arrayList.get(i);
                benchmark.benchmarkAddition(cur, prev);
                prev = cur;
            }
            long endTime = System.nanoTime();
            long result = endTime - startTime;

            System.out.println("Computation time for Addition: " + result + " nanoseconds @ " + ITERATIONS[j]);


        }

        // You can add assertions if you want to ensure certain performance thresholds are met
        // For example:
        // assertEquals(expectedDuration, durationAddition, tolerance);
        // assertEquals(expectedDuration, durationMultiplication, tolerance);

    }

    @Test
    public void testBenchmarkMultiplication() {

        int length = ITERATIONS.length;


        for (int j = 0; j < length; j++) {
            long startTime = System.nanoTime();
            int prev = 0;
            for (int i = 0; i < ITERATIONS[j]; i++) {
                int cur = arrayList.get(i);
                benchmark.benchmarkMultiplication(cur, prev);
                prev = cur;
            }
            long endTime = System.nanoTime();
            long result = endTime - startTime;
            System.out.println("Computation time for Multiplication: " + result + " nanoseconds @" + ITERATIONS[j]);

        }

        // You can add assertions if you want to ensure certain performance thresholds are met
        // For example:
        // assertEquals(expectedDuration, durationAddition, tolerance);
        // assertEquals(expectedDuration, durationMultiplication, tolerance);

    }

    @Test
    public void testScalar() {
        int length = ITERATIONS.length;

        for (int j = 0; j < length; j++) {

            float[] ax = Arrays.copyOfRange(a, 0, ITERATIONS[j]);
            float[] bx = Arrays.copyOfRange(b, 0, ITERATIONS[j]);
            float[] cx = Arrays.copyOfRange(c, 0, ITERATIONS[j]);
            long startTime = System.nanoTime();
            benchmark.scalarComputation(ax, bx, cx);
            long endTime = System.nanoTime();
            long result = endTime - startTime;
            System.out.println("Computation time for Scalar: " + result + " nanoseconds @" + ITERATIONS[j]);
        }
    }

    @Test
    public void testVector() {
        int length = ITERATIONS.length;
        for (int j = 0; j < length; j++) {
            float[] ax = Arrays.copyOfRange(a, 0, ITERATIONS[j]);
            float[] bx = Arrays.copyOfRange(b, 0, ITERATIONS[j]);
            float[] cx = Arrays.copyOfRange(c, 0, ITERATIONS[j]);
            long startTime = System.nanoTime();

            benchmark.vectorComputation(ax, bx, cx);
            long endTime = System.nanoTime();
            long result = endTime - startTime;
            System.out.println("Computation time for Vector: " + result + " nanoseconds @ " + ITERATIONS[j]);
        }
    }

    @Test
    public void testVectorSpeed() {
        int length = ITERATIONS.length;
        for (int j = 0; j < length; j++) {
            Vector<Integer> vx = new Vector<>();
            Object[] objects = Arrays.copyOfRange(vectorList.toArray(), 0, ITERATIONS[j]);
            long startTime = System.nanoTime();
            for (int i = 0; i < objects.length; i++) {
                vx.add(i, (Integer) objects[i]);
            }
            long endTime = System.nanoTime();
            long result = endTime - startTime;
            System.out.println("Execution time for Vector: " + result + " @ " + ITERATIONS[j]);
            vx.clear();
            out.add(new BenchmarkResult(ITERATIONS[j], result, "Vector"));
        }
    }


    @Test
    public void testArrayListSpeed() {
        int length = ITERATIONS.length;
        for (int j = 0; j < length; j++) {
            ArrayList<Integer> vx = new ArrayList<>();
            Object[] objects = Arrays.copyOfRange(arrayList.toArray(), 0, ITERATIONS[j]);
            long startTime = System.nanoTime();
            for (int i = 0; i < objects.length; i++) {
                vx.add(i, (Integer) objects[i]);
            }
            long endTime = System.nanoTime();
            long result = endTime - startTime;
            System.out.println("Execution time for ArrayList: " + result + " @ " + ITERATIONS[j]);
            vx.clear();
            out.add(new BenchmarkResult(ITERATIONS[j], result, "ArrayList"));
        }
    }

    @Test
    public void testHashMapSpeed() {
        int length = ITERATIONS.length;
        for (int j = 0; j < length; j++) {
            HashMap<Integer, Integer> vx = new HashMap<>();
            Object[] objects = Arrays.copyOfRange(arrayList.toArray(), 0, ITERATIONS[j]);
            long startTime = System.nanoTime();
            for (int i = 0; i < objects.length; i++) {
                vx.put(i, (Integer) objects[i]);
            }
            long endTime = System.nanoTime();
            long result = endTime - startTime;
            System.out.println("Execution time for HashMap: " + result + " @ " + ITERATIONS[j]);
            vx.clear();
            out.add(new BenchmarkResult(ITERATIONS[j], result, "HashMap"));
        }
    }


    @Test
    public void testLinkedListSpeed() {
        int length = ITERATIONS.length;
        for (int j = 0; j < length; j++) {
            LinkedList<Integer> vx = new LinkedList<>();
            Object[] objects = Arrays.copyOfRange(arrayList.toArray(), 0, ITERATIONS[j]);
            long startTime = System.nanoTime();
            for (int i = 0; i < objects.length; i++) {
                vx.add((Integer) objects[i]);
            }
            long endTime = System.nanoTime();
            long result = endTime - startTime;
            System.out.println("Execution time for LinkedList: " + result + " @ " + ITERATIONS[j]);
            vx.clear();
            out.add(new BenchmarkResult(ITERATIONS[j], result, "LinkedList"));
        }
    }

//    @Test
//    public void testProtoVectorSpeed() {
//
//        int length = ITERATIONS.length;
//        for (int j = 0; j < length; j++) {
//            Arrays.copyOf(
//                    arrayList.
//            );
//            Arrays.copyOfRange(, 0,
//                    ITERATIONS[j]);
//            long startTime = System.nanoTime();
//            var x = IntVector.fromArray(
//                    Arrays.copyOfRange()
//            )
//            long endTime = System.nanoTime();
//            long result = endTime - startTime;
//            System.out.println("Execution time for ArrayList: " + result
//                    + " @ " + ITERATIONS[j]);
//            vx.clear();
//        }
//    }
}