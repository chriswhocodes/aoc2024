package com.chrisnewland.aoc2024;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestDay10
{
	@Test
	public void test1Trailhead2Peaks()
	{
		List<String> lines = new ArrayList<>();

		lines.add("...0...");
		lines.add("...1...");
		lines.add("...2...");
		lines.add("6543456");
		lines.add("7.....7");
		lines.add("8.....8");
		lines.add("9.....9");

		Day10 day10 = new Day10(lines);

		assertEquals(2, day10.part1());
	}

	@Test
	public void test1Trailhead4Peaks()
	{
		List<String> lines = new ArrayList<>();

		//         0123456
		lines.add("..90..9"); // 0
		lines.add("...1.98"); // 1
		lines.add("...2..7"); // 2
		lines.add("6543456"); // 3
		lines.add("765.987"); // 4
		lines.add("876...."); // 5
		lines.add("987...."); // 6

		Day10 day10 = new Day10(lines);

		assertEquals(4, day10.part1());
	}

	@Test
	public void test2Trailhead2Peaks()
	{
		List<String> lines = new ArrayList<>();

		lines.add("10..9..");
		lines.add("2...8..");
		lines.add("3...7..");
		lines.add("4567654");
		lines.add("...8..3");
		lines.add("...9..2");
		lines.add(".....01");

		Day10 day10 = new Day10(lines);

		assertEquals(3, day10.part1());
	}

	@Test
	public void test9Trailhead6Peaks()
	{
		List<String> lines = new ArrayList<>();

		lines.add("89010123");
		lines.add("78121874");
		lines.add("87430965");
		lines.add("96549874");
		lines.add("45678903");
		lines.add("32019012");
		lines.add("01329801");
		lines.add("10456732");

		Day10 day10 = new Day10(lines);

		assertEquals(36, day10.part1());
	}

	@Test
	public void testRatings1()
	{
		List<String> lines = new ArrayList<>();

		lines.add(".....0.");
		lines.add("..4321.");
		lines.add("..5..2.");
		lines.add("..6543.");
		lines.add("..7..4.");
		lines.add("..8765.");
		lines.add("..9....");

		Day10 day10 = new Day10(lines);

		assertEquals(3, day10.part2());
	}

	@Test
	public void testRatings2()
	{
		List<String> lines = new ArrayList<>();

		lines.add("..90..9");
		lines.add("...1.98");
		lines.add("...2..7");
		lines.add("6543456");
		lines.add("765.987");
		lines.add("876....");
		lines.add("987....");

		Day10 day10 = new Day10(lines);

		assertEquals(13, day10.part2());
	}

	@Test
	public void testRatings3()
	{
		List<String> lines = new ArrayList<>();

		lines.add("012345");
		lines.add("123456");
		lines.add("234567");
		lines.add("345678");
		lines.add("4.6789");
		lines.add("56789.");

		Day10 day10 = new Day10(lines);

		assertEquals(227, day10.part2());
	}

	@Test
	public void testRatings4()
	{
		List<String> lines = new ArrayList<>();

		lines.add("89010123");
		lines.add("78121874");
		lines.add("87430965");
		lines.add("96549874");
		lines.add("45678903");
		lines.add("32019012");
		lines.add("01329801");
		lines.add("10456732");

		Day10 day10 = new Day10(lines);

		assertEquals(81, day10.part2());
	}
}