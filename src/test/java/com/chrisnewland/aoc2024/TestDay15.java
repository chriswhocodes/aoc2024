package com.chrisnewland.aoc2024;

import com.chrisnewland.aoc2024.common.Grid;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class TestDay15
{

	@Test
	public void testGpsSumWideClosestLeft()
	{
		List<String> lines = new ArrayList<>();

		lines.add("####################");
		lines.add("##[]..............##");

		Grid grid = new Grid(lines);

		assertEquals(102, new Day15.Puzzle(lines, true).getGpsSum(grid, true));
	}

	@Test
	public void testGpsSumWideClosestRight()
	{
		List<String> lines = new ArrayList<>();

		lines.add("####################");
		lines.add("##..............[]##");

		Grid grid = new Grid(lines);

		assertEquals(116, new Day15.Puzzle(lines, true).getGpsSum(grid, true));
	}

	@Test
	public void testGpsSumMiddle()
	{
		List<String> lines = new ArrayList<>();

		lines.add("############");
		lines.add("##...[]...##");

		Grid grid = new Grid(lines);

		assertEquals(105, new Day15.Puzzle(lines, true).getGpsSum(grid, true));
	}

	@Test
	public void testGpsSumWideExample()
	{
		List<String> lines = new ArrayList<>();

		lines.add("####################");
		lines.add("##[].......[].[][]##");
		lines.add("##[]...........[].##");
		lines.add("##[]........[][][]##");
		lines.add("##[]......[]....[]##");
		lines.add("##..##......[]....##");
		lines.add("##..[]............##");
		lines.add("##..@......[].[][]##");
		lines.add("##......[][]..[]..##");
		lines.add("####################");

		Grid grid = new Grid(lines);

		assertEquals(9021, new Day15.Puzzle(lines, true).getGpsSum(grid, true));
	}
}