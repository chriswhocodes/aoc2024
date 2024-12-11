package com.chrisnewland.aoc2024;

import com.chrisnewland.aoc2024.common.Direction;
import com.chrisnewland.aoc2024.common.Grid;
import com.chrisnewland.aoc2024.common.Position;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Day10
{
	public static void main(String[] args) throws IOException
	{
		List<String> lines = Files.readAllLines(Paths.get("src/main/resources/2024/day10.txt"));

		Day10 day10 = new Day10(lines);

		long result1 = day10.part1();
		System.out.printf("Part 1: %d\n", result1);

		long result2 = day10.part2();
		System.out.printf("Part 2: %d\n", result2);
	}

	private List<String> lines;

	public Day10(List<String> lines)
	{
		this.lines = lines;
	}

	public long part1()
	{
		Grid grid = new Grid(lines);

		List<Position> starts = grid.getPositionsOf('0');

		long sum = 0;

		for (Position start : starts)
		{
			sum += countTrailheads(grid, start, null);
		}

		return sum;
	}

	long countTrailheads(Grid grid, Position start, Direction travelling)
	{
		Set<Position> visitedPeaks = new HashSet<>();

		countTrailheads(grid, start, travelling, visitedPeaks);

		return visitedPeaks.size();
	}

	void countTrailheads(Grid grid, Position start, Direction travelling, Set<Position> visitedPeaks)
	{
		Direction[] directions = new Direction[] { Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST };

		int currentHeight = grid.charAt(start) - '0';

		if (currentHeight == 9)
		{
			visitedPeaks.add(start);

			return ;
		}

		for (Direction direction : directions)
		{
			if (direction != null && direction.opposite() == travelling)
			{
				continue;
			}

			Position next = start.move(direction);

			if (grid.valid(next))
			{
				int nextHeight = grid.charAt(next) - '0';

				if (nextHeight == currentHeight + 1)
				{
					 countTrailheads(grid, next, direction, visitedPeaks);
				}
			}
		}
	}

	public long part2()
	{
		Grid grid = new Grid(lines);

		List<Position> starts = grid.getPositionsOf('0');

		long sum = 0;

		Map<Position, Long> memo = new HashMap<>();

		for (Position start : starts)
		{
			sum += countRatings(grid, start, memo, null);
		}

		return sum;
	}

	long countRatings(Grid grid, Position start, Map<Position, Long> memo, Direction travelling)
	{
		long rating = 0;

		Direction[] directions = new Direction[] { Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST };

		int currentHeight = grid.charAt(start) - '0';

		if (currentHeight == 9)
		{
			return 1;
		}

		for (Direction direction : directions)
		{
			if (direction != null && direction.opposite() == travelling)
			{
				continue;
			}

			Position next = start.move(direction);

			if (grid.valid(next))
			{
				int nextHeight = grid.charAt(next) - '0';

				if (nextHeight == currentHeight + 1)
				{
					rating += countRatings(grid, next, memo, direction);
				}
			}
		}

		memo.put(start, rating);

		return rating;
	}
}