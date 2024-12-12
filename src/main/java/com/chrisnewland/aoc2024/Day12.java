package com.chrisnewland.aoc2024;

import com.chrisnewland.aoc2024.common.Direction;
import com.chrisnewland.aoc2024.common.Grid;
import com.chrisnewland.aoc2024.common.Position;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Day12
{
	public static void main(String[] args) throws IOException
	{
		List<String> lines = Files.readAllLines(Paths.get("src/main/resources/2024/day12.txt"));

		Day12 day12 = new Day12(lines);

		long result1 = day12.part1();
		System.out.printf("Part 1: %d\n", result1);

		long result2 = day12.part2();
		System.out.printf("Part 2: %d\n", result2);
	}

	private List<String> lines;

	public Day12(List<String> lines)
	{
		this.lines = lines;
	}

	static class RegionManager
	{
		private Grid grid;

		public RegionManager(Grid grid)
		{
			this.grid = grid;

			Set<Position> visited = new HashSet<>();

			for (int y = 0; y < grid.getY(); y++)
			{
				for (int x = 0; x < grid.getX(); x++)
				{
					Position pos = new Position(x, y);

					char c = grid.charAt(pos);

					walk(pos, c, visited);
				}
			}
		}

		private void walk(Position pos, char c, Set<Position> visited)
		{
			if (!grid.valid(pos) || visited.contains(pos) || grid.charAt(pos) != c)
			{
				return;
			}

			visited.add(pos);

			add(pos, c);

			walk(pos.move(Direction.NORTH), c, visited);
			walk(pos.move(Direction.SOUTH), c, visited);
			walk(pos.move(Direction.EAST), c, visited);
			walk(pos.move(Direction.WEST), c, visited);
		}

		private Map<Character, List<Region>> regionMap = new HashMap<>();

		public void add(Position position, Character identifier)
		{
			List<Region> regions = regionMap.computeIfAbsent(identifier, k -> new ArrayList<>());

			boolean added = false;

			for (Region region : regions)
			{
				if (region.inside(position))
				{
					region.add(position);
					added = true;
					break;
				}
			}

			if (!added)
			{
				Region region = new Region(grid);
				region.add(position);
				regions.add(region);
			}
		}

		public List<Region> getForIdentifier(Character identifier)
		{
			return regionMap.get(identifier);
		}
	}

	static class Region
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

	public long part1()
	{
		Grid grid = new Grid(lines);

		Day12.RegionManager regionManager = new Day12.RegionManager(grid);

		Set<Character> identifiers = grid.getUniqueCharacters();

		long cost = 0;

		for (char c : identifiers)
		{
			List<Day12.Region> regions = regionManager.getForIdentifier(c);

			for (Day12.Region region : regions)
			{
				cost += region.getPerimeter() * region.getArea();
			}
		}

		return cost;
	}

	public long part2()
	{
		Grid grid = new Grid(lines);

		Day12.RegionManager regionManager = new Day12.RegionManager(grid);

		Set<Character> identifiers = grid.getUniqueCharacters();

		long cost = 0;

		for (char c : identifiers)
		{
			List<Day12.Region> regions = regionManager.getForIdentifier(c);

			for (Day12.Region region : regions)
			{
				cost += region.getArea() * region.getSides();
			}
		}

		return cost;
	}
}