package com.chrisnewland.aoc2024;

import com.chrisnewland.aoc2024.common.Grid;
import com.chrisnewland.aoc2024.common.Position;
import com.chrisnewland.aoc2024.common.Region;
import com.chrisnewland.aoc2024.common.RegionManager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Day14
{
	public static void main(String[] args) throws IOException
	{
		List<String> lines = Files.readAllLines(Paths.get("src/main/resources/2024/day14.txt"));

		Day14 day14 = new Day14(lines);

		long result1 = day14.part1();
		System.out.printf("Part 1: %d\n", result1);

		long result2 = day14.part2();
		System.out.printf("Part 2: %d\n", result2);
	}

	private List<String> lines;

	public Day14(List<String> lines)
	{
		this.lines = lines;
	}

	static class Robot
	{
		Position position;

		Position vector;

		Grid grid;

		public Robot(Position position, Position vector, Grid grid)
		{
			this.position = position;
			this.vector = vector;
			this.grid = grid;
		}

		public void move()
		{
			int nextX = position.getX() + vector.getX();
			int nextY = position.getY() + vector.getY();

			if (nextX < 0)
			{
				nextX += grid.getX();
			}
			else if (nextX >= grid.getX())
			{
				nextX -= grid.getX();
			}

			if (nextY < 0)
			{
				nextY += grid.getY();
			}
			else if (nextY >= grid.getY())
			{
				nextY -= grid.getY();
			}

			position = new Position(nextX, nextY);
		}

		@Override
		public String toString()
		{
			return "Robot{" + "position=" + position + ", vector=" + vector + '}';
		}
	}

	public List<Robot> parse(Grid grid, List<String> lines)
	{
		List<Robot> robots = new ArrayList<>();

		for (String line : lines)
		{
			int equals = line.indexOf('=');
			int comma = line.indexOf(',', equals);
			int space = line.indexOf(' ', comma);

			Position position = new Position(Integer.valueOf(line.substring(equals + 1, comma)),
					Integer.valueOf(line.substring(comma + 1, space)));

			equals = line.indexOf('=', space);
			comma = line.indexOf(',', equals);

			Position vector = new Position(Integer.valueOf(line.substring(equals + 1, comma)),
					Integer.valueOf(line.substring(comma + 1)));

			robots.add(new Robot(position, vector, grid));
		}

		return robots;
	}

	private long getQuadrantProduct(Grid grid)
	{
		int splitX = grid.getX() / 2;
		int splitY = grid.getY() / 2;

		long sum1 = 0;
		long sum2 = 0;
		long sum3 = 0;
		long sum4 = 0;

		for (int y = 0; y < splitY; y++)
		{
			for (int x = 0; x < splitX; x++)
			{
				sum1 += (int) grid.charAt(new Position(x, y));
			}
		}

		for (int y = 0; y < splitY; y++)
		{
			for (int x = splitX + 1; x < grid.getX(); x++)
			{
				sum2 += (int) grid.charAt(new Position(x, y));
			}
		}

		for (int y = splitY + 1; y < grid.getY(); y++)
		{
			for (int x = 0; x < splitX; x++)
			{
				sum3 += (int) grid.charAt(new Position(x, y));
			}
		}

		for (int y = splitY + 1; y < grid.getY(); y++)
		{
			for (int x = splitX + 1; x < grid.getX(); x++)
			{
				sum4 += (int) grid.charAt(new Position(x, y));
			}
		}

		return sum1 * sum2 * sum3 * sum4;
	}

	public long part1()
	{
		Grid grid = new Grid(101, 103);

		List<Robot> robots = parse(grid, lines);

		for (int i = 0; i < 100; i++)
		{
			for (Robot robot : robots)
			{
				robot.move();
			}
		}

		for (Robot robot : robots)
		{
			Position pos = robot.position;

			int count = grid.charAt(pos);

			grid.set(pos, (char) (count + 1));
		}

		return getQuadrantProduct(grid);
	}

	public long part2()
	{
		Grid grid = new Grid(101, 103);

		List<Robot> robots = parse(grid, lines);

		long moves = 0;

		boolean found = false;

		while (!found)
		{
			for (Robot robot : robots)
			{
				robot.move();
			}

			moves++;

			Grid maybeTree = new Grid(101, 103);

			for (Robot robot : robots)
			{
				Position pos = robot.position;

				maybeTree.set(pos, '.');
			}

			if (!maybeTree.toString().contains(".......")) // skip unlikely patterns as region check is slow
			{
				continue;
			}

			// if there is a region containing more than 33% of the robots then print it

			RegionManager regionManager = new RegionManager(maybeTree, false);

			List<Region> regions = regionManager.getForIdentifier('.');

			for (Region region : regions)
			{
				if (region.getArea() > robots.size() / 3)
				{
					System.out.println(maybeTree);
					found = true;
					break;
				}
			}
		}

		return moves;
	}
}