/*
 *
 * This file is part of arche-quickstart.
 *  Copyright (c) 2023.
 * arche-quickstart is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * [Project Name] is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with arche-quickstart. If not, see <http://www.gnu.org/licenses/>.
 *
 */

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

    private static final int[] ITERATIONS = {
            1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
            20, 30, 40, 50, 60, 70, 80, 90, 100,
            200, 300, 400, 500, 600, 700, 800, 900, 1000,
            2000, 3000, 4000, 5000, 6000, 7000, 8000, 9000,
            20_000, 40_000, 60_000, 80_000, 100_000,
            1_000_000, 2_000_000
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
        processLog(collect, "performance_data.txt");
        Map<Integer, List<BenchmarkResult>> noTree = out.stream().filter(e -> {
            return !e.operationName.toLowerCase().contains("tree");
        }).collect(Collectors.groupingBy(BenchmarkResult::iterationCount));
        processLog(noTree, "performance_data_without_trees.txt");
    }

    private static void processLog(Map<Integer,
            List<BenchmarkResult>> collect, String fileName) {

        // Create a data file to store performance data for gnuplot
        try {
            FileWriter writer = null;
            writer = new FileWriter(fileName);
            Integer integer = collect.keySet().stream().findFirst().orElseThrow();
            List<BenchmarkResult> collect1 = collect.get(integer);
            List<String> collect2 = collect1.stream().map(BenchmarkResult::operationName).sorted().toList();

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
    public void testHashSetSpeed() {
        int length = ITERATIONS.length;
        for (int j = 0; j < length; j++) {
            HashSet<Integer> vx = new HashSet<>();
            Object[] objects = Arrays.copyOfRange(arrayList.toArray(), 0, ITERATIONS[j]);
            long startTime = System.nanoTime();
            for (int i = 0; i < objects.length; i++) {
                vx.add((Integer) objects[i]);
            }
            long endTime = System.nanoTime();
            long result = endTime - startTime;
            System.out.println("Execution time for HashSet: " + result + " @ " + ITERATIONS[j]);
            vx.clear();
            out.add(new BenchmarkResult(ITERATIONS[j], result, "HashSet"));
        }
    }

    @Test
    public void testTreeSetSpeed() {
        int length = ITERATIONS.length;
        for (int j = 0; j < length; j++) {
            SortedSet<Integer> vx = new TreeSet<>();
            Object[] objects = Arrays.copyOfRange(arrayList.toArray(), 0, ITERATIONS[j]);
            long startTime = System.nanoTime();
            for (int i = 0; i < objects.length; i++) {
                vx.add((Integer) objects[i]);
            }
            long endTime = System.nanoTime();
            long result = endTime - startTime;
            System.out.println("Execution time for TreeSet: " + result + " @ " + ITERATIONS[j]);
            vx.clear();
            out.add(new BenchmarkResult(ITERATIONS[j], result, "TreeSet"));
        }
    }

    @Test
    public void testStackSpeed() {
        int length = ITERATIONS.length;
        for (int j = 0; j < length; j++) {
            Stack<Integer> vx = new Stack<>();
            Object[] objects = Arrays.copyOfRange(arrayList.toArray(), 0, ITERATIONS[j]);
            long startTime = System.nanoTime();
            for (int i = 0; i < objects.length; i++) {
                vx.add((Integer) objects[i]);
            }
            long endTime = System.nanoTime();
            long result = endTime - startTime;
            System.out.println("Execution time for Stack: " + result + " @ " + ITERATIONS[j]);
            vx.clear();
            out.add(new BenchmarkResult(ITERATIONS[j], result, "Stack"));
        }
    }

    @Test
    public void testLinkedHashSetSpeed() {
        int length = ITERATIONS.length;
        for (int j = 0; j < length; j++) {
            LinkedHashSet<Integer> vx = new LinkedHashSet<>();
            Object[] objects = Arrays.copyOfRange(arrayList.toArray(), 0, ITERATIONS[j]);
            long startTime = System.nanoTime();
            for (int i = 0; i < objects.length; i++) {
                vx.add((Integer) objects[i]);
            }
            long endTime = System.nanoTime();
            long result = endTime - startTime;
            System.out.println("Execution time for LinkedHashSet: " + result + " @ " + ITERATIONS[j]);
            vx.clear();
            out.add(new BenchmarkResult(ITERATIONS[j], result, "LinkedHashSet"));
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

    @Test
    public void testPriorityQueueSpeed() {
        int length = ITERATIONS.length;
        for (int j = 0; j < length; j++) {
            PriorityQueue<Integer> vx = new PriorityQueue<>();
            Object[] objects = Arrays.copyOfRange(arrayList.toArray(), 0, ITERATIONS[j]);
            long startTime = System.nanoTime();
            for (int i = 0; i < objects.length; i++) {
                vx.add((Integer) objects[i]);
            }
            long endTime = System.nanoTime();
            long result = endTime - startTime;
            System.out.println("Execution time for PriorityQueue: " + result + " @ " + ITERATIONS[j]);
            vx.clear();
            out.add(new BenchmarkResult(ITERATIONS[j], result, "PriorityQueue"));
        }
    }

    @Test
    public void testTreeMapSpeed() {
        int length = ITERATIONS.length;
        for (int j = 0; j < length; j++) {
            TreeMap<Integer, Integer> vx = new TreeMap<>();
            Object[] objects = Arrays.copyOfRange(arrayList.toArray(), 0, ITERATIONS[j]);
            long startTime = System.nanoTime();
            for (int i = 0; i < objects.length; i++) {
                vx.put(i, (Integer) objects[i]);
            }
            long endTime = System.nanoTime();
            long result = endTime - startTime;
            System.out.println("Execution time for TreeMap: " + result + " @ " + ITERATIONS[j]);
            vx.clear();
            out.add(new BenchmarkResult(ITERATIONS[j], result, "TreeMap"));
        }
    }

    @Test
    public void testArrayDequeSpeed() {
        int length = ITERATIONS.length;
        for (int j = 0; j < length; j++) {
            ArrayDeque<Integer> vx = new ArrayDeque<>();
            Object[] objects = Arrays.copyOfRange(arrayList.toArray(), 0, ITERATIONS[j]);
            long startTime = System.nanoTime();
            for (int i = 0; i < objects.length; i++) {
                vx.add((Integer) objects[i]);
            }
            long endTime = System.nanoTime();
            long result = endTime - startTime;
            System.out.println("Execution time for ArrayDeque: " + result + " @ " + ITERATIONS[j]);
            vx.clear();
            out.add(new BenchmarkResult(ITERATIONS[j], result, "ArrayDeque"));
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