package com.chrisnewland.aoc2024;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Day13
{
	public static void main(String[] args) throws IOException
	{
		List<String> lines = Files.readAllLines(Paths.get("src/main/resources/2024/day13.txt"));

		Day13 day13 = new Day13(lines);

		long result1 = day13.part1();
		System.out.printf("Part 1: %d\n", result1);

		long result2 = day13.part2();
		System.out.printf("Part 2: %d\n", result2);
	}

	private List<String> lines;

	public Day13(List<String> lines)
	{
		this.lines = lines;
	}

	static class Claw
	{
		int costA = 3;
		int costB = 1;

		int aX;
		int aY;
		int bX;
		int bY;

		public Claw(int aX, int aY, int bX, int bY)
		{
			this.aX = aX;
			this.aY = aY;
			this.bX = bX;
			this.bY = bY;
		}

		public long solveSimple(long destX, long destY, int maxPresses)
		{
			long maxA = Math.min(maxPresses, Math.max(destX / aX, destY / aY));
			long maxB = Math.min(maxPresses, Math.max(destX / bX, destY / bY));

			// start from low a to minimise cost
			for (long a = 0; a < maxA; a++)
			{
				for (long b = 0; b < maxB; b++)
				{
					long x = a * aX + b * bX;
					long y = a * aY + b * bY;

					if (x == destX && y == destY)
					{
						return a * costA + b * costB;
					}
				}
			}

			return 0;
		}

		public long solveWithSimultaneousEquations(long destX, long destY)
		{
			// simultaneous equations

			// a.aX + b.bX = destX
			// a.aY + b.bY = destY

			long equation1 = bY * destX - bX * destY;

			long equation2 = aX * destY - aY * destX;

			long determinant = (aX * bY) - (aY * bX);

			// check determinant is non-zero and dividing both equations by determinant yields no remainder
			if (determinant == 0 || equation1 % determinant != 0 || equation2 % determinant != 0)
			{
				return 0;
			}
			else
			{
				return (costA * equation1 / determinant) + (costB * equation2 / determinant);
			}
		}
	}

	public long part1()
	{
		return solve(0, 100);
	}

	public long part2()
	{
		return solve(10000000000000L, -1);
	}

	private long solve(long offset, int maxPresses)
	{
		long cost = 0;

		int aX = 0;
		int aY = 0;
		int bX = 0;
		int bY = 0;

		for (String line : lines)
		{
			if (line.startsWith("Button A"))
			{
				int pos = line.indexOf('+');
				int comma = line.indexOf(',', pos);
				aX = Integer.parseInt(line.substring(pos + 1, comma));
				pos = line.indexOf('+', comma + 1);
				aY = Integer.parseInt(line.substring(pos + 1).trim());
			}
			else if (line.startsWith("Button B"))
			{
				int pos = line.indexOf('+');
				int comma = line.indexOf(',', pos);
				bX = Integer.parseInt(line.substring(pos + 1, comma));
				pos = line.indexOf('+', comma + 1);
				bY = Integer.parseInt(line.substring(pos + 1).trim());
			}
			else if (line.startsWith("Prize"))
			{
				int equals = line.indexOf('=');
				int comma = line.indexOf(',', equals);
				int destX = Integer.parseInt(line.substring(equals + 1, comma));
				equals = line.indexOf('=', comma + 1);
				int destY = Integer.parseInt(line.substring(equals + 1).trim());

				if (maxPresses != -1)
				{
					cost += new Claw(aX, aY, bX, bY).solveSimple(destX, destY, maxPresses);
				}
				else
				{
					cost += new Claw(aX, aY, bX, bY).solveWithSimultaneousEquations(destX + offset, destY + offset);
				}
			}
		}

		return cost;
	}
}