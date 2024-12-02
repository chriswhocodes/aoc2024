package com.chrisnewland.aoc2024;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Day1
{
	public static void main(String[] args) throws IOException
	{
		List<String> lines = Files.readAllLines(Paths.get("src/main/resources/2024/day1.txt"));

		Day1 day1 = new Day1(lines);

		int result1 = day1.part1();
		System.out.printf("Part 1: %d\n", result1);

		int result2a = day1.part2Simple();
		System.out.printf("Part 2    simple: %d\n", result2a);

		int result2b = day1.part2Optimised();
		System.out.printf("Part 2 optimised: %d\n", result2b);
	}

	private List<String> lines;

	public Day1(List<String> lines)
	{
		this.lines = lines;
	}

	public int part1()
	{
		List<Integer> left = new ArrayList<>();
		List<Integer> right = new ArrayList<>();

		for (String line : lines)
		{
			String[] split = line.split("\\s+");

			left.add(Integer.parseInt(split[0]));
			right.add(Integer.parseInt(split[1]));
		}

		left.sort(Integer::compareTo);
		right.sort(Integer::compareTo);

		int sum = 0;

		for (int i = 0; i < left.size(); i++)
		{
			int diff = Math.abs(left.get(i) - right.get(i));

			sum += diff;
		}

		return sum;
	}

	public int part2Simple()
	{
		List<Integer> left = new ArrayList<>();
		List<Integer> right = new ArrayList<>();

		for (String line : lines)
		{
			String[] split = line.split("\\s+");

			left.add(Integer.parseInt(split[0]));
			right.add(Integer.parseInt(split[1]));
		}

		int sum = 0;

		for (int l : left)
		{
			long count = right.stream().filter(v -> v == l).count();

			sum += l * count;
		}

		return sum;
	}

	/*
	Optimisations:
	Built counts from right list in 1 pass using a sparse array
	Avoid regex by parsing using 5 digit substrings
	 */
	public int part2Optimised()
	{
		int[] counts = new int[100000];

		int[] left = new int[lines.size()];

		int pos = 0;

		for (String line : lines)
		{
			int l = Integer.parseInt(line.substring(0,5));
			int r = Integer.parseInt(line.substring(8));

			left[pos++] = l;

			counts[r]++;
		}

		int sum = 0;

		for (int i = 0; i < left.length; i++)
		{
			int l = left[i];

			sum += l * counts[l];
		}

		return sum;
	}
}