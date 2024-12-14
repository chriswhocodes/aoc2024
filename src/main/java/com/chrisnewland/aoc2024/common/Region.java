package com.chrisnewland.aoc2024.common;

import java.util.HashSet;
import java.util.Set;

public class Region
{
	private Grid grid;

	public Region(Grid grid)
	{
		this.grid = grid;
	}

	private Set<Position> positions = new HashSet<>();

	public boolean inside(Position pos)
	{
		for (Position p : positions)
		{
			if (p.adjacent(pos))
			{
				return true;
			}
		}

		return false;
	}

	public void add(Position pos)
	{
		positions.add(pos);
	}

	public int getPerimeter()
	{
		int perimeter = 0;

		for (Position p : positions)
		{
			int neighbours = 4;

			for (Position q : positions)
			{
				if (p.adjacent(q))
				{
					neighbours--;
				}
			}

			perimeter += neighbours;
		}

		return perimeter;
	}

	public int getArea()
	{
		return positions.size();
	}

	public int getSides()
	{
		// horizontal sides

		int sides = 0;

		for (int y = 0; y < grid.getY(); y++)
		{
			boolean continuousAbove = false;
			boolean continuousBelow = false;

			for (int x = 0; x < grid.getX(); x++)
			{
				Position pos = new Position(x, y);

				if (positions.contains(pos))
				{
					Position north = pos.move(Direction.NORTH);
					Position south = pos.move(Direction.SOUTH);

					if (!positions.contains(north))
					{
						if (!continuousAbove)
						{
							sides++;
							continuousAbove = true;
						}
					}
					else
					{
						continuousAbove = false;
					}

					if (!positions.contains(south))
					{
						if (!continuousBelow)
						{
							sides++;
							continuousBelow = true;
						}
					}
					else
					{
						continuousBelow = false;
					}
				}
				else
				{
					continuousAbove = false;
					continuousBelow = false;
				}
			}
		}

		// vertical sides
		for (int x = 0; x < grid.getX(); x++)
		{
			boolean continuousLeft = false;
			boolean continuousRight = false;

			for (int y = 0; y < grid.getY(); y++)
			{
				Position pos = new Position(x, y);

				if (positions.contains(pos))
				{
					Position east = pos.move(Direction.EAST);
					Position west = pos.move(Direction.WEST);

					if (!positions.contains(east))
					{
						if (!continuousRight)
						{
							sides++;
							continuousRight = true;
						}
					}
					else
					{
						continuousRight = false;
					}

					if (!positions.contains(west))
					{
						if (!continuousLeft)
						{
							sides++;
							continuousLeft = true;
						}
					}
					else
					{
						continuousLeft = false;
					}
				}
				else
				{
					continuousLeft = false;
					continuousRight = false;
				}
			}
		}

		return sides;
	}

	@Override
	public String toString()
	{
		return "Region{" + positions + "}";
	}
}