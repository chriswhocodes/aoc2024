package com.chrisnewland.aoc2024;

import com.chrisnewland.aoc2024.common.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Day12
{
	public static void main(String[] args) throws IOException
	{
		List<String> lines = Files.readAllLines(Paths.get("src/main/resources/2024/day12.txt"));

		Day12 day12 = new Day12(lines);

		long result1 = day12.part1();
		System.out.printf("Part 1: %d\n", result1);

		long result2 = day12.part2();
		System.out.printf("Part 2: %d\n", result2);
	}

	private List<String> lines;

	public Day12(List<String> lines)
	{
		this.lines = lines;
	}

	public long part1()
	{
		Grid grid = new Grid(lines);

		RegionManager regionManager = new RegionManager(grid, true);

		Set<Character> identifiers = grid.getUniqueCharacters();

		long cost = 0;

		for (char c : identifiers)
		{
			List<Region> regions = regionManager.getForIdentifier(c);

			for (Region region : regions)
			{
				cost += region.getPerimeter() * region.getArea();
			}
		}

		return cost;
	}

	public long part2()
	{
		Grid grid = new Grid(lines);

		RegionManager regionManager = new RegionManager(grid, true);

		Set<Character> identifiers = grid.getUniqueCharacters();

		long cost = 0;

		for (char c : identifiers)
		{
			List<Region> regions = regionManager.getForIdentifier(c);

			for (Region region : regions)
			{
				cost += region.getArea() * region.getSides();
			}
		}

		return cost;
	}
}