package com.chrisnewland.aoc2024;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
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

	enum Direction
	{
		NORTH,
		SOUTH,
		EAST,
		WEST,
		NORTHEAST,
		NORTHWEST,
		SOUTHEAST,
		SOUTHWEST
	}

	static class Position
	{
		int x;
		int y;

		public Position(int x, int y)
		{
			this.x = x;
			this.y = y;
		}

		public Position move(Direction direction)
		{
			return switch (direction)
			{
				case NORTH -> new Position(x, y - 1);
				case SOUTH -> new Position(x, y + 1);
				case EAST -> new Position(x + 1, y);
				case WEST -> new Position(x - 1, y);
				case NORTHEAST -> new Position(x + 1, y - 1);
				case NORTHWEST -> new Position(x - 1, y - 1);
				case SOUTHEAST -> new Position(x + 1, y + 1);
				case SOUTHWEST -> new Position(x - 1, y + 1);
			};
		}

	}

	static class Grid
	{
		int x;
		int y;

		char[][] data;

		public Grid(int x, int y)
		{
			this.x = x;
			this.y = y;
			data = new char[x][y];
		}

		public Grid(List<String> lines)
		{
			x = lines.size();
			y = lines.get(0).length();
			data = new char[x][y];

			int ypos = 0;

			for (String line : lines)
			{
				char[] chars = line.toCharArray();

				for (int xpos = 0; xpos < chars.length; xpos++)
				{
					data[xpos][ypos] = chars[xpos];
				}

				ypos++;
			}
		}

		@Override
		public String toString()
		{
			StringBuilder sb = new StringBuilder();

			for (int ypos = 0; ypos < y; ypos++)
			{
				for (int xpos = 0; xpos < x; xpos++)
				{
					sb.append(data[xpos][ypos]);
				}
				sb.append("\n");
			}

			return sb.toString();
		}

		public String getString(Position start, Direction direction, int length)
		{
			StringBuilder sb = new StringBuilder();

			sb.append(charAt(start));

			for (int i = 0; i < length - 1; i++)
			{
				Position next = start.move(direction);

				if (!valid(next))
				{
					return null;
				}
				else
				{
					sb.append(charAt(next));
					start = next;
				}
			}

			return sb.toString();
		}

		public char charAt(Position pos)
		{
			return data[pos.x][pos.y];
		}

		private boolean valid(Position pos)
		{
			return pos.x >= 0 && pos.x < x && pos.y >= 0 && pos.y < y;
		}
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

		for (int ypos = 0; ypos < grid.y; ypos++)
		{
			for (int xpos = 0; xpos < grid.x; xpos++)
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

		for (int ypos = 0; ypos < grid.y; ypos++)
		{
			for (int xpos = 0; xpos < grid.x; xpos++)
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
