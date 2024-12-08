package com.chrisnewland.aoc2024;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;

public class Day7
{
    public static void main(String[] args) throws IOException
    {
        List<String> lines = Files.readAllLines(Paths.get("src/main/resources/2024/day7.txt"));

        Day7 day7 = new Day7(lines);

        long result1 = day7.part1();
        System.out.printf("Part 1: %d\n", result1);

        long result2 = day7.part2();
        System.out.printf("Part 2: %d\n", result2);
    }

    static class Equation
    {
        enum Operation
        {
            ADD((a, b) -> a + b),
            MULTIPLY((a, b) -> a * b),
            JOIN((a, b) -> Long.valueOf(a.toString() + b.toString()));

            BiFunction<Long, Long, Long> function;

            Operation(BiFunction<Long, Long, Long> function)
            {
                this.function = function;
            }

            long perform(long a, long b)
            {
                return function.apply(a, b);
            }
        }

        private long result;
        private long[] inputs;

        static class PermutationGenerator
        {
            private Operation[] operations;

            private int[] barrels;

            public PermutationGenerator(Operation[] operations, int inputs)
            {
                this.operations = operations;
                this.barrels = new int[inputs];
            }

            public Operation get(int barrel)
            {
                return operations[barrels[barrel]];
            }

            public void inc()
            {
                barrels[0]++;

                for (int i = 0; i < barrels.length; i++)
                {
                    if (barrels[i] == operations.length)
                    {
                        barrels[i] = 0;

                        if (i < barrels.length - 1)
                        {
                            barrels[i + 1]++;
                        }
                    }
                    else
                    {
                        break;
                    }
                }
            }
        }

        public Equation(String line)
        {
            String[] parts = line.split(" ");
            result = Long.parseLong(parts[0].substring(0, parts[0].length() - 1));

            inputs = new long[parts.length - 1];

            for (int i = 1; i < parts.length; i++)
            {
                inputs[i - 1] = Long.parseLong(parts[i]);
            }
        }

        public boolean isValid(Operation... operations)
        {
            long operatorPermutations = (long) Math.pow(operations.length, inputs.length - 1);

            PermutationGenerator permutationGenerator = new PermutationGenerator(operations, inputs.length - 1);

            for (int o = 0; o < operatorPermutations; o++)
            {
                long r = inputs[0];

                for (int i = 1; i < inputs.length; i++)
                {
                    Operation operation = permutationGenerator.get(i - 1);

                    r = operation.perform(r, inputs[i]);
                }

                permutationGenerator.inc();

                if (result == r)
                {
                    return true;
                }
            }

            return false;
        }

        public long getResult()
        {
            return result;
        }

        @Override
        public String toString()
        {
            return "Equation{" +
                    "result=" + result +
                    ", inputs=" + Arrays.toString(inputs) +
                    '}';
        }
    }

    private List<String> lines;

    public Day7(List<String> lines)
    {
        this.lines = lines;
    }

    public long part1()
    {
        long sum = 0;

        for (String line : lines)
        {
            Equation equation = new Equation(line);

            if (equation.isValid(Equation.Operation.ADD, Equation.Operation.MULTIPLY))
            {
                sum += equation.getResult();
            }
        }

        return sum;
    }

    public long part2()
    {
        long sum = 0;

        for (String line : lines)
        {
            Equation equation = new Equation(line);

            if (equation.isValid(Equation.Operation.ADD, Equation.Operation.MULTIPLY, Equation.Operation.JOIN))
            {
                sum += equation.getResult();
            }
        }

        return sum;
    }
}