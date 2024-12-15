package com.chrisnewland.aoc2024;

import com.chrisnewland.aoc2024.common.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Day15
{
	public static void main(String[] args) throws IOException
	{
		List<String> lines = Files.readAllLines(Paths.get("src/main/resources/2024/day15.txt.test"));

		Day15 day15 = new Day15(lines);

		long result1 = day15.part1();
		System.out.printf("Part 1: %d\n", result1);

		long result2 = day15.part2();
		System.out.printf("Part 2: %d\n", result2);
	}

	private List<String> lines;

	public Day15(List<String> lines)
	{
		this.lines = lines;
	}

	static class Puzzle
	{
		Grid grid;

		String moves = "";

		private static final char OBSTACLE = '#';
		private static final char BOX = 'O';
		private static final char BOX_LEFT = '[';
		private static final char BOX_RIGHT = ']';
		private static final char SPACE = '.';
		private static final char ROBOT = '@';

		public Puzzle(List<String> lines, boolean wide)
		{
			List<String> gridLines = new ArrayList<>();

			boolean seenBlank = false;

			for (String line : lines)
			{
				if (line.trim().length() == 0)
				{
					seenBlank = true;
					continue;
				}

				if (!seenBlank)
				{
					if (wide)
					{
						line = widen(line);
					}

					gridLines.add(line);
				}
				else
				{
					moves += line;
				}
			}

			grid = new Grid(gridLines);
		}

		private String widen(String line)
		{
			StringBuilder result = new StringBuilder();

			for (int i = 0; i < line.length(); i++)
			{
				char c = line.charAt(i);

				switch (c)
				{
				case BOX -> result.append(BOX_LEFT).append(BOX_RIGHT);
				case OBSTACLE -> result.append(OBSTACLE).append(OBSTACLE);
				case SPACE -> result.append(SPACE).append(SPACE);
				case ROBOT -> result.append(ROBOT).append(SPACE);
				}
			}

			return result.toString();
		}

		public void solve(boolean wide)
		{
			int length = moves.length();

			List<Position> positions = grid.getPositionsOf(ROBOT);

			Position robot = positions.get(0);

			System.out.println(grid);

			for (int i = 0; i < length; i++)
			{
				char c = moves.charAt(i);

				System.out.println("move: " + c);

				Direction direction = switch (c)
				{
					case '<' -> Direction.WEST;
					case '>' -> Direction.EAST;
					case '^' -> Direction.NORTH;
					case 'v' -> Direction.SOUTH;
					default -> throw new IllegalStateException("Unexpected value: " + c);
				};

				Position next = robot.move(direction);

				char nextChar = grid.charAt(next);

				if (nextChar == SPACE)
				{
					grid.set(robot, SPACE);
					grid.set(next, ROBOT);
					robot = next;
				}
				else if (nextChar == OBSTACLE)
				{
					// do nothing
				}
				else
				{
					boolean pushed = push(next, direction, wide);

					System.out.println("pushed: " + pushed);

					if (pushed)
					{
						grid.set(robot, SPACE);
						grid.set(next, ROBOT);
						robot = next;
					}
				}

				System.out.println(grid);
			}
		}

		private boolean push(Position next, Direction direction, boolean wide)
		{
			switch (direction)
			{
			case NORTH ->
			{
				if (!wide)
				{
					for (int y = next.getY() - 1; y > 0; y--)
					{
						char c = grid.charAt(new Position(next.getX(), y));

						if (c == OBSTACLE)
						{
							return false;
						}
						else if (c == SPACE)
						{
							for (int m = y; m < next.getY(); m++)
							{
								Position pTop = new Position(next.getX(), m);
								Position pBottom = new Position(next.getX(), m + 1);

								char top = grid.charAt(pTop);
								char bottom = grid.charAt(pBottom);
								grid.set(pTop, bottom);
								grid.set(pBottom, top);
							}
							return true;
						}
					}
				}
				else
				{
// find cascading width when swapping down
				}
			}
			case SOUTH ->
			{
				if (!wide)
				{
					for (int y = next.getY() + 1; y < grid.getY(); y++)
					{
						char c = grid.charAt(new Position(next.getX(), y));

						if (c == OBSTACLE)
						{
							return false;
						}
						else if (c == SPACE)
						{
							for (int m = y; m > next.getY(); m--)
							{
								Position pTop = new Position(next.getX(), m - 1);
								Position pBottom = new Position(next.getX(), m);

								char top = grid.charAt(pTop);
								char bottom = grid.charAt(pBottom);
								grid.set(pTop, bottom);
								grid.set(pBottom, top);
							}
							return true;
						}
					}
				}
				else
				{

				}
			}
			case EAST ->
			{
				for (int x = next.getX() + 1; x < grid.getX(); x++)
				{
					char c = grid.charAt(new Position(x, next.getY()));

					if (c == OBSTACLE)
					{
						return false;
					}
					else if (c == SPACE)
					{
						for (int m = x; m > next.getX(); m--)
						{
							Position pRight = new Position(m, next.getY());
							Position pLeft = new Position(m - 1, next.getY());

							char right = grid.charAt(pRight);
							char left = grid.charAt(pLeft);
							grid.set(pRight, left);
							grid.set(pLeft, right);
						}
						return true;
					}
				}
			}
			case WEST ->
			{
				for (int x = next.getX() - 1; x > 0; x--)
				{
					char c = grid.charAt(new Position(x, next.getY()));

					if (c == OBSTACLE)
					{
						return false;
					}
					else if (c == SPACE)
					{
						for (int m = x; m < next.getX(); m++)
						{
							Position pLeft = new Position(m, next.getY());
							Position pRight = new Position(m + 1, next.getY());

							char right = grid.charAt(pRight);
							char left = grid.charAt(pLeft);
							grid.set(pRight, left);
							grid.set(pLeft, right);
						}
						return true;
					}
				}
			}
			}

			return false;
		}

		public long getGpsSum(boolean wide)
		{
			long result = 0;

			if (wide)
			{
				List<Position> positions = grid.getPositionsOf('[');

				for (Position position : positions)
				{
					if (position.getX() < grid.getX()/2)
					{
						result += position.getY() * 100 + position.getX();
					}
					else
					{
						result += position.getY() * 100 + position.getX()+1;
					}
				}
			}
			else
			{
				List<Position> positions = grid.getPositionsOf('O');

				for (Position position : positions)
				{
					result += position.getY() * 100 + position.getX();
				}
			}

			return result;
		}

		@Override
		public String toString()
		{
			return "Puzzle{" + "grid=" + grid + ", moves='" + moves + '\'' + '}';
		}
	}

	public long part1()
	{
		boolean wide = false;

		Puzzle puzzle = new Puzzle(lines, wide);

		puzzle.solve(wide);

		return puzzle.getGpsSum(wide);
	}

	public long part2()
	{
		boolean wide = true;

		Puzzle puzzle = new Puzzle(lines, wide);

		puzzle.solve(wide);

		return puzzle.getGpsSum(wide);
	}
}