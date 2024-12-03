package com.chrisnewland.aoc2024;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestDay2
{
	@Test
	public void testPart1Safe()
	{
		Day2 day2 = new Day2(new ArrayList<String>());

		assertTrue(day2.safe("1 2 3 4 5"));

		assertTrue(day2.safe("5 4 3 2 1"));

		assertTrue(day2.safe("1 4 7 10"));

		assertTrue(day2.safe("10 7 4 1"));
	}

	@Test
	public void testPart1Unsafe()
	{
		Day2 day2 = new Day2(new ArrayList<String>());

		assertFalse(day2.safe("1 5"));

		assertFalse(day2.safe("5 1"));

		assertFalse(day2.safe("1 3 1"));

		assertFalse(day2.safe("3 1 3"));
	}

	@Test
	public void testPart2Safe()
	{
		Day2 day2 = new Day2(new ArrayList<String>());

		assertTrue(day2.safeWithDampener("1 2 3 4 1"));

		assertTrue(day2.safeWithDampener("5 4 3 2 5"));

		assertTrue(day2.safeWithDampener("1 5 3 4"));

		assertTrue(day2.safeWithDampener("9 6 7 4 1"));

		assertTrue(day2.safeWithDampener("9 6 7 4 1"));

		assertTrue(day2.safeWithDampener("1 5 3 2 1"));

		assertTrue(day2.safeWithDampener("4 3 1 2 1"));

		assertTrue(day2.safeWithDampener("9 8 7 8 5 2"));

		assertTrue(day2.safeWithDampener("9 7 8 6 5 2"));

		assertTrue(day2.safeWithDampener("1 2 1 5"));

	}

	@Test
	public void testPart2Unsafe()
	{
		Day2 day2 = new Day2(new ArrayList<String>());

		assertFalse(day2.safeWithDampener("1 5 1"));

		assertFalse(day2.safeWithDampener("5 1 5"));

		assertFalse(day2.safeWithDampener("1 1 2 2 4"));

		assertFalse(day2.safeWithDampener("4 2 2 1 1"));

		assertFalse(day2.safeWithDampener("86 83 84 87 88 91 98 96"));

		assertFalse(day2.safeWithDampener("9 1 6 1"));
	}
}