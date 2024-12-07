package com.chrisnewland.aoc2024;

import com.chrisnewland.aoc2024.common.Direction;
import com.chrisnewland.aoc2024.common.Grid;
import com.chrisnewland.aoc2024.common.Position;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Day4
{
	public static void main(String[] args) throws IOException
	{
		List<String> lines = Files.readAllLines(Paths.get("src/main/resources/2024/day4.txt"));

		Day4 day4 = new Day4(lines);

		long result1 = day4.part1();
		System.out.printf("Part 1: %d\n", result1);

		long result2 = day4.part2();
		System.out.printf("Part 2: %d\n", result2);
	}

	private List<String> lines;

	public Day4(List<String> lines)
	{
		this.lines = lines;
	}

	public long part1()
	{
		Grid grid = new Grid(lines);

		int count = 0;

		for (int ypos = 0; ypos < grid.getY(); ypos++)
		{
			for (int xpos = 0; xpos < grid.getX(); xpos++)
			{
				Position start = new Position(xpos, ypos);

				if (grid.charAt(start) == 'X')
				{
					for (Direction direction : Direction.values())
					{
						String string = grid.getString(start, direction, 4);

						if ("XMAS".equals(string))
						{
							count++;
						}
					}
				}
			}
		}

		return count;
	}

	public long part2()
	{
		Grid grid = new Grid(lines);

		int count = 0;

		for (int ypos = 0; ypos < grid.getY(); ypos++)
		{
			for (int xpos = 0; xpos < grid.getX(); xpos++)
			{
				Position start = new Position(xpos, ypos);

				if (grid.charAt(start) == 'A')
				{
					Position nw = start.move(Direction.NORTHWEST);
					Position ne = start.move(Direction.NORTHEAST);
					Position sw = start.move(Direction.SOUTHWEST);
					Position se = start.move(Direction.SOUTHEAST);

					if (grid.valid(nw) && grid.valid(ne) && grid.valid(sw) && grid.valid(se))
					{
						boolean mas1 = (grid.charAt(nw) == 'M' && grid.charAt(se) == 'S') || (grid.charAt(nw) == 'S'
								&& grid.charAt(se) == 'M');
						boolean mas2 = (grid.charAt(ne) == 'M' && grid.charAt(sw) == 'S') || (grid.charAt(ne) == 'S'
								&& grid.charAt(sw) == 'M');

						if (mas1 && mas2)
						{
							count++;
						}
					}

				}
			}
		}

		return count;
	}
}
