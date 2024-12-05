package com.chrisnewland.aoc2024;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Day5
{
	public static void main(String[] args) throws IOException
	{
		List<String> lines = Files.readAllLines(Paths.get("src/main/resources/2024/day5.txt"));

		Day5 day5 = new Day5(lines);

		long result1 = day5.part1();
		System.out.printf("Part 1: %d\n", result1);

		long result2 = day5.part2();
		System.out.printf("Part 2: %d\n", result2);
	}

	private List<String> lines;

	public Day5(List<String> lines)
	{
		this.lines = lines;
	}

	static class ComesBefore extends HashMap<Integer, Set<Integer>>
	{
		public void add(int first, int second)
		{
			computeIfAbsent(first, k -> new HashSet<>()).add(second);
		}

		public boolean mustPrecede(int first, int second)
		{
			return containsKey(first) && get(first).contains(second);
		}

	}

	static class PageOrdering
	{
		ComesBefore comesBefore = new ComesBefore();

		List<List<Integer>> updates = new ArrayList<>();

		public PageOrdering(List<String> lines)
		{
			for (String line : lines)
			{
				if (!line.isEmpty())
				{
					if (line.contains("|"))
					{
						String[] parts = line.split("\\|");
						comesBefore.add(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
					}
					else
					{
						String[] parts = line.split(",");

						List<Integer> pages = new ArrayList<>(parts.length);

						for (String part : parts)
						{
							pages.add(Integer.valueOf(part));
						}

						updates.add(pages);
					}
				}
			}
		}

		public long getMiddleSumOfCorrectlyOrdered()
		{
			long sum = 0;

			for (List<Integer> pages : updates)
			{
				sum += getMiddleSum(pages);
			}

			return sum;
		}

		public long getMiddleSumOfFixedOrder()
		{
			long sum = 0;

			for (List<Integer> pages : updates)
			{
				int middle = getMiddleSum(pages);

				if (middle == 0)
				{
					pages.sort((o1, o2) -> {
						if (comesBefore.mustPrecede(o1, o2))
						{
							return -1;
						}

						return 0;
					});

					sum += getMiddleSum(pages);
				}
			}

			return sum;
		}

		private int getMiddleSum(List<Integer> pages)
		{
			Set<Integer> seen = new HashSet<>();

			for (Integer page : pages)
			{
				Set<Integer> laterPages = comesBefore.get(page);

				if (laterPages != null && !laterPages.isEmpty())
				{
					for (Integer later : laterPages)
					{
						if (seen.contains(later))
						{
							return 0;
						}
					}
				}

				seen.add(page);
			}

			return pages.get((int) Math.floor((double) pages.size() / 2.0));
		}
	}

	public long part1()
	{
		PageOrdering pageOrdering = new PageOrdering(lines);

		return pageOrdering.getMiddleSumOfCorrectlyOrdered();
	}

	public long part2()
	{
		PageOrdering pageOrdering = new PageOrdering(lines);

		return pageOrdering.getMiddleSumOfFixedOrder();
	}
}