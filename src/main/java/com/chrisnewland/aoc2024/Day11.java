package com.chrisnewland.aoc2024;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day11
{
	public static void main(String[] args) throws IOException
	{
		List<String> lines = Files.readAllLines(Paths.get("src/main/resources/2024/day11.txt"));

		Day11 day11 = new Day11(lines);

		long result1 = day11.part1();
		System.out.printf("Part 1: %d\n", result1);

		long result2 = day11.part2();
		System.out.printf("Part 2: %d\n", result2);
	}

	private List<String> lines;

	public Day11(List<String> lines)
	{
		this.lines = lines;
	}

	static class Stone
	{
		private long value;

		private static final Map<String, Long> CACHE = new HashMap<>();

		public static List<Stone> parse(String line)
		{
			List<Stone> stones = new ArrayList<>();

			String[] parts = line.split("\s+");

			for (String part : parts)
			{
				stones.add(new Stone(Long.parseLong(part)));
			}

			return stones;
		}

		public Stone(long value)
		{
			this.value = value;
		}

		@Override
		public String toString()
		{
			return "{" + value + "}";
		}

		public long blink(int count)
		{
			long sum = 0;

			count--;

			String key = value + "_" + count;

			Long cached = CACHE.get(key);

			if (cached != null)
			{
				return cached;
			}

			if (value == 0)
			{
				if (count > 0)
				{
					Stone stone = new Stone(1);

					sum += stone.blink(count);
				}
				else
				{
					sum++;
				}
			}
			else
			{
				String valueString = Long.toString(value);

				if (valueString.length() % 2 == 0)
				{
					if (count > 0)
					{
						int splitLength = valueString.length() / 2;

						Stone left = new Stone(Long.valueOf(valueString.substring(0, splitLength)));
						sum += left.blink(count);

						Stone right = new Stone(Long.valueOf(valueString.substring(splitLength)));
						sum += right.blink(count);
					}
					else
					{
						sum += 2;
					}
				}
				else
				{
					if (count > 0)
					{
						Stone stone = new Stone(value * 2024);
						sum += stone.blink(count);
					}
					else
					{
						sum++;
					}
				}
			}

			CACHE.put(key, sum);

			return sum;
		}
	}

	public long part1()
	{
		List<Stone> stones = Stone.parse(lines.get(0));

		long sum = 0;

		for (Stone stone : stones)
		{
			sum += stone.blink(25);
		}

		return sum;
	}

	public long part2()
	{
		List<Stone> stones = Stone.parse(lines.get(0));

		long sum = 0;

		for (Stone stone : stones)
		{
			sum += stone.blink(75);
		}

		return sum;
	}
}