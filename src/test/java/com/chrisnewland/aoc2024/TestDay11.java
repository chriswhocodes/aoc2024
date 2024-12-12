package com.chrisnewland.aoc2024;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestDay11
{
	@Test
	public void testRule0()
	{
		assertEquals(1, new Day11.Stone(0).blink(1));
	}

	@Test
	public void testRuleEven()
	{
		assertEquals(2, new Day11.Stone(4080).blink(1));
		assertEquals(2, new Day11.Stone(4002).blink(1));
	}

	@Test
	public void testRuleOther()
	{
		assertEquals(1, new Day11.Stone(123).blink(1));
	}

	private long count(List<Day11.Stone> stones, int blinks)
	{
		long sum = 0;

		for (Day11.Stone stone : stones)
		{
			sum += stone.blink(blinks);
		}

		return sum;
	}

	@Test
	public void testExample1()
	{
		String input = "0 1 10 99 999";

		List<Day11.Stone> stones = Day11.Stone.parse(input);

		assertEquals(7, count(stones, 1));
	}

	@Test
	public void testExample2()
	{
		String input = "125 17";

		List<Day11.Stone> stones = Day11.Stone.parse(input);

		assertEquals(3, count(stones, 1));
		assertEquals(4, count(stones, 2));
		assertEquals(5, count(stones, 3));
		assertEquals(9, count(stones, 4));
		assertEquals(13, count(stones, 5));
		assertEquals(22, count(stones, 6));
		assertEquals(55312, count(stones, 25));
	}
}