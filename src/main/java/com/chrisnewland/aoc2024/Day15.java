package com.chrisnewland.aoc2024;

import com.chrisnewland.aoc2024.common.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Day15
{
	public static void main(String[] args) throws IOException
	{
		List<String> lines = Files.readAllLines(Paths.get("src/main/resources/2024/day15.txt"));

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

			for (int i = 0; i < length; i++)
			{
				char c = moves.charAt(i);

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
					// blocked
				}
				else
				{
					boolean pushed = push(next, direction, wide);

					if (pushed)
					{
						grid.set(robot, SPACE);
						grid.set(next, ROBOT);
						robot = next;
					}
				}
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

								grid.swapChar(pTop, pBottom);
							}
							return true;
						}
					}
				}
				else
				{
					char boxChar = grid.charAt(next);

					int searchLeft;
					int searchRight;

					if (boxChar == BOX_LEFT)
					{
						searchLeft = next.getX();
						searchRight = next.getX() + 1;
					}
					else
					{
						searchLeft = next.getX() - 1;
						searchRight = next.getX();
					}

					List<Position> boxesToShift = new LinkedList<>();
					boxesToShift.add(new Position(searchLeft, next.getY()));

					for (int y = next.getY() - 1; y > 0; y--)
					{
						boolean allSpaces = true;

						Set<Position> boxesThisRow = new HashSet<>();

						for (int x = searchLeft; x <= searchRight; x++)
						{
							char c = grid.charAt(new Position(x, y));

							if (c == OBSTACLE)
							{
								return false;
							}
							else if (c == BOX_LEFT)
							{
								allSpaces = false;
								if (x == searchRight)
								{
									searchRight++;
								}

								boxesThisRow.add(new Position(x, y));
							}
							else if (c == BOX_RIGHT)
							{
								allSpaces = false;
								if (x == searchLeft)
								{
									searchLeft--;
								}
								boxesThisRow.add(new Position(x-1, y));

							}
						}

						if (!boxesThisRow.isEmpty())
						{
							int nextSearchLeft = Integer.MAX_VALUE;
							int nextSearchRight = Integer.MIN_VALUE;

							for (Position position : boxesThisRow)
							{
								nextSearchLeft = Math.min(nextSearchLeft, position.getX());
								nextSearchRight = Math.max(nextSearchRight, position.getX());
							}

							searchLeft = nextSearchLeft;
							searchRight = nextSearchRight+1;

							boxesToShift.addAll(boxesThisRow);
						}

						if (allSpaces)
						{
							Collections.reverse(boxesToShift);

							for (Position position : boxesToShift)
							{
								grid.swapChar(position, position.move(Direction.NORTH));
								grid.swapChar(position.move(Direction.EAST), position.move(Direction.EAST).move(Direction.NORTH));
							}

							return true;
						}
					}
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

								grid.swapChar(pTop, pBottom);
							}
							return true;
						}
					}
				}
				else
				{
					char boxChar = grid.charAt(next);

					int searchLeft;
					int searchRight;

					if (boxChar == BOX_LEFT)
					{
						searchLeft = next.getX();
						searchRight = next.getX() + 1;
					}
					else
					{
						searchLeft = next.getX() - 1;
						searchRight = next.getX();
					}

					List<Position> boxesToShift = new LinkedList<>();
					boxesToShift.add(new Position(searchLeft, next.getY()));

					for (int y = next.getY() + 1; y < grid.getY(); y++)
					{
						boolean allSpaces = true;

						Set<Position> boxesThisRow = new HashSet<>();

						for (int x = searchLeft; x <= searchRight; x++)
						{
							char c = grid.charAt(new Position(x, y));

							if (c == OBSTACLE)
							{
								return false;
							}
							else if (c == BOX_LEFT)
							{
								allSpaces = false;
								if (x == searchRight)
								{
									searchRight++;
								}

								boxesThisRow.add(new Position(x, y));
							}
							else if (c == BOX_RIGHT)
							{
								allSpaces = false;
								if (x == searchLeft)
								{
									searchLeft--;
								}
								boxesThisRow.add(new Position(x-1, y));

							}
						}

						if (!boxesThisRow.isEmpty())
						{
							int nextSearchLeft = Integer.MAX_VALUE;
							int nextSearchRight = Integer.MIN_VALUE;

							for (Position position : boxesThisRow)
							{
								nextSearchLeft = Math.min(nextSearchLeft, position.getX());
								nextSearchRight = Math.max(nextSearchRight, position.getX());
							}

							searchLeft = nextSearchLeft;
							searchRight = nextSearchRight+1;

							boxesToShift.addAll(boxesThisRow);
						}

						if (allSpaces)
						{
							Collections.reverse(boxesToShift);

							for (Position position : boxesToShift)
							{
								grid.swapChar(position, position.move(Direction.SOUTH));
								grid.swapChar(position.move(Direction.EAST), position.move(Direction.EAST).move(Direction.SOUTH));
							}

							return true;
						}
					}
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

							grid.swapChar(pRight, pLeft);
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

							grid.swapChar(pRight, pLeft);
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
			return getGpsSum(grid, wide);
		}

		long getGpsSum(Grid grid, boolean wide)
		{
			long result = 0;

			char box = wide ? BOX_LEFT : BOX;

			List<Position> positions = grid.getPositionsOf(box);

			for (Position position : positions)
			{
				result += position.getY() * 100 + position.getX();
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