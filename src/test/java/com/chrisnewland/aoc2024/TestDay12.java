package com.chrisnewland.aoc2024;

import com.chrisnewland.aoc2024.common.Grid;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestDay12
{
	@Test
	public void testFindRegions()
	{
		List<String> lines = new ArrayList<>();

		lines.add("AAAA");
		lines.add("BBCD");
		lines.add("BBCC");
		lines.add("EEEC");

		Grid grid = new Grid(lines);

		Day12.RegionManager regionManager = new Day12.RegionManager(grid);

		assertEquals(4, regionManager.getForIdentifier('A').get(0).getArea());
		assertEquals(4, regionManager.getForIdentifier('B').get(0).getArea());
		assertEquals(4, regionManager.getForIdentifier('C').get(0).getArea());
		assertEquals(1, regionManager.getForIdentifier('D').get(0).getArea());
		assertEquals(3, regionManager.getForIdentifier('E').get(0).getArea());

		assertEquals(10, regionManager.getForIdentifier('A').get(0).getPerimeter());
		assertEquals(8, regionManager.getForIdentifier('B').get(0).getPerimeter());
		assertEquals(10, regionManager.getForIdentifier('C').get(0).getPerimeter());
		assertEquals(4, regionManager.getForIdentifier('D').get(0).getPerimeter());
		assertEquals(8, regionManager.getForIdentifier('E').get(0).getPerimeter());
	}

	@Test
	public void testSimplePlots()
	{
		List<String> lines = new ArrayList<>();

		lines.add("AAAA");
		lines.add("BBCD");
		lines.add("BBCC");
		lines.add("EEEC");

		Day12 day12 = new Day12(lines);

		assertEquals(140, day12.part1());
	}

	@Test
	public void testComplexPlots()
	{
		List<String> lines = new ArrayList<>();

		lines.add("OOOOO");
		lines.add("OXOXO");
		lines.add("OOOOO");
		lines.add("OXOXO");
		lines.add("OOOOO");

		Day12 day12 = new Day12(lines);

		assertEquals(772, day12.part1());
	}

	@Test
	public void testLargerPlots()
	{
		List<String> lines = new ArrayList<>();

		lines.add("RRRRIICCFF");
		lines.add("RRRRIICCCF");
		lines.add("VVRRRCCFFF");
		lines.add("VVRCCCJFFF");
		lines.add("VVVVCJJCFE");
		lines.add("VVIVCCJJEE");
		lines.add("VVIIICJJEE");
		lines.add("MIIIIIJJEE");
		lines.add("MIIISIJEEE");
		lines.add("MMMISSJEEE");

		Day12 day12 = new Day12(lines);

		assertEquals(1930, day12.part1());
	}

	@Test
	public void testGetSides()
	{
		List<String> lines = new ArrayList<>();

		lines.add("AAAA");
		lines.add("BBCD");
		lines.add("BBCC");
		lines.add("EEEC");

		Grid grid = new Grid(lines);

		Day12.RegionManager regionManager = new Day12.RegionManager(grid);

		assertEquals(4, regionManager.getForIdentifier('A').get(0).getSides());
		assertEquals(4, regionManager.getForIdentifier('B').get(0).getSides());
		assertEquals(8, regionManager.getForIdentifier('C').get(0).getSides());
		assertEquals(4, regionManager.getForIdentifier('D').get(0).getSides());
		assertEquals(4, regionManager.getForIdentifier('E').get(0).getSides());

		Day12 day12 = new Day12(lines);

		assertEquals(80, day12.part2());
	}

	@Test
	public void testGetSides2()
	{
		List<String> lines = new ArrayList<>();

		lines.add("EEEEE");
		lines.add("EXXXX");
		lines.add("EEEEE");
		lines.add("EXXXX");
		lines.add("EEEEE");

		Grid grid = new Grid(lines);

		Day12.RegionManager regionManager = new Day12.RegionManager(grid);

		assertEquals(12, regionManager.getForIdentifier('E').get(0).getSides());
		assertEquals(4, regionManager.getForIdentifier('X').get(0).getSides());
		assertEquals(4, regionManager.getForIdentifier('X').get(1).getSides());

		Day12 day12 = new Day12(lines);

		assertEquals(236, day12.part2());
	}

	@Test
	public void testGetSides3()
	{
		List<String> lines = new ArrayList<>();

		lines.add("OOOOO");
		lines.add("OXOXO");
		lines.add("OOOOO");
		lines.add("OXOXO");
		lines.add("OOOOO");

		Day12 day12 = new Day12(lines);

		assertEquals(436, day12.part2());
	}

	@Test
	public void testGetSides4()
	{
		List<String> lines = new ArrayList<>();

		lines.add("AAAAAA");
		lines.add("AAABBA");
		lines.add("AAABBA");
		lines.add("ABBAAA");
		lines.add("ABBAAA");
		lines.add("AAAAAA");

		Day12 day12 = new Day12(lines);

		assertEquals(368, day12.part2());
	}

	@Test
	public void testLargerPlotsPart2()
	{
		List<String> lines = new ArrayList<>();

		lines.add("RRRRIICCFF");
		lines.add("RRRRIICCCF");
		lines.add("VVRRRCCFFF");
		lines.add("VVRCCCJFFF");
		lines.add("VVVVCJJCFE");
		lines.add("VVIVCCJJEE");
		lines.add("VVIIICJJEE");
		lines.add("MIIIIIJJEE");
		lines.add("MIIISIJEEE");
		lines.add("MMMISSJEEE");

		Day12 day12 = new Day12(lines);

		assertEquals(1206, day12.part2());
	}
}