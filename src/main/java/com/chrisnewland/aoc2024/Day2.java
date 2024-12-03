package com.chrisnewland.aoc2024;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Day2
{
	public static void main(String[] args) throws IOException
	{
		List<String> lines = Files.readAllLines(Paths.get("src/main/resources/2024/day2.txt"));

		Day2 day2 = new Day2(lines);

		int result1 = day2.part1();
		System.out.printf("Part 1: %d\n", result1);

		int result2a = day2.part2Simple();
		System.out.printf("Part 2 simple: %d\n", result2a);

		int result2b = day2.part2BruteForce();
		System.out.printf("Part 2 bruteforce: %d\n", result2b);
	}

	private List<String> lines;

	public Day2(List<String> lines)
	{
		this.lines = lines;
	}

	public int part1()
	{
		int safe = 0;

		for (String line : lines)
		{
			if (safe(line))
			{
				safe++;
			}
		}

		return safe;
	}

	public boolean safe(String line)
	{
		String[] levels = line.split(" ");

		int previous = Integer.parseInt(levels[0]);

		boolean increasing = true;

		for (int i = 1; i < levels.length; i++)
		{
			int current = Integer.parseInt(levels[i]);

			int delta = Math.abs(current - previous);

			if (delta < 1 || delta > 3)
			{
				return false;
			}

			boolean thisIncreasing = current > previous;

			if (i == 1)
			{
				increasing = thisIncreasing;
			}
			else
			{
				if (increasing != thisIncreasing)
				{
					return false;
				}
			}

			previous = current;

		}

		return true;
	}

	public boolean safeWithDampener(String line)
	{
		if (safeWithDampener(line, false))
		{
			return true;
		}
		else if (safeWithDampener(line, true))
		{
			return true;
		}
		else if (safe(line.substring(line.indexOf(' ')).trim()))
		{
			return true;
		}

		return false;
	}

	public boolean safeWithDampener(String line, boolean first)
	{
		String[] levels = line.split(" ");

		int previous = Integer.parseInt(levels[0]);

		boolean increasing = true;

		boolean skipped = false;

		for (int i = 1; i < levels.length; i++)
		{
			int current = Integer.parseInt(levels[i]);

			int delta = Math.abs(current - previous);

			if (delta < 1 || delta > 3)
			{
				if (!skipped)
				{
					skipped = true;
					continue;
				}
				else
				{
					return false;
				}
			}

			boolean thisIncreasing = current > previous;

			if (i == 1)
			{
				increasing = thisIncreasing;
			}
			else
			{
				if (increasing != thisIncreasing)
				{
					if (!skipped)
					{
						skipped = true;

						if (first)
						{
							previous = current;
						}
						continue;
					}
					else
					{
						return false;
					}
				}
			}

			previous = current;
		}

		return true;
	}

	public int part2Simple()
	{
		int safe = 0;

		for (String line : lines)
		{
			if (safeWithDampener(line))
			{
				safe++;
			}
		}

		return safe;
	}

	public int part2BruteForce()
	{
		int safe = 0;

		for (String line : lines)
		{
			if (safe(line))
			{
				safe++;
			}
			else
			{
				String[] parts = line.split("\\s+");

				for (int i = 0; i < parts.length; i++)
				{
					StringBuilder builder = new StringBuilder();

					for (int j = 0; j < parts.length; j++)
					{
						if (i != j)
						{
							builder.append(parts[j]).append(" ");
						}
					}

					if (safe(builder.toString()))
					{
						safe++;
						break;
					}
				}
			}
		}

		return safe;
	}
}