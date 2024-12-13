package com.chrisnewland.aoc2024;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class TestDay13
{
	@Test
	public void testSolveClaw1()
	{
		Day13.Claw claw = new Day13.Claw(94, 34, 22, 67);

		long cost = claw.solveSimple(8400, 5400, 100);

		assertEquals(280, cost);
	}

	@Test
	public void testSolveClaw2()
	{
		Day13.Claw claw = new Day13.Claw(26, 66, 67, 21);

		long cost = claw.solveSimple(12748, 12176, 100);

		assertEquals(0, cost);
	}

	@Test
	public void testSolveClaw3()
	{
		Day13.Claw claw = new Day13.Claw(17, 86, 84, 37);

		long cost = claw.solveSimple(7870, 6450, 100);

		assertEquals(200, cost);
	}

	@Test
	public void testSolveClaw4()
	{
		Day13.Claw claw = new Day13.Claw(69, 23, 27, 71);

		long cost = claw.solveSimple(18641, 10279, 100);

		assertEquals(0, cost);
	}

	@Test
	public void testParseAndSolve()
	{
		List<String> lines = new ArrayList<>();

		lines.add("Button A: X+94, Y+34");
		lines.add("Button B: X+22, Y+67");
		lines.add("Prize: X=8400, Y=5400");

		Day13 day13 = new Day13(lines);

		assertEquals(280, day13.part1());
	}

	@Test
	public void testSolveClaw2Part2()
	{
		List<String> lines = new ArrayList<>();

		lines.add("Button A: X+26, Y+66");
		lines.add("Button B: X+67, Y+21");
		lines.add("Prize: X=12748, Y=12176");

		Day13 day13 = new Day13(lines);

		assertNotEquals(0, day13.part2());
	}

	@Test
	public void testSolveClaw4Part2()
	{
		List<String> lines = new ArrayList<>();

		lines.add("Button A: X+69, Y+23");
		lines.add("Button B: X+27, Y+71");
		lines.add("Prize: X=18641, Y=10279");

		Day13 day13 = new Day13(lines);

		assertNotEquals(0, day13.part2());
	}
}