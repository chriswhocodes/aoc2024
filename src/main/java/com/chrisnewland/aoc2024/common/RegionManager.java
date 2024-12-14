package com.chrisnewland.aoc2024.common;

import java.util.*;

public class RegionManager
{
	private Grid grid;

	public RegionManager(Grid grid, boolean depthFirst)
	{
		this.grid = grid;

		Set<Position> visited = new HashSet<>();

		for (int y = 0; y < grid.getY(); y++)
		{
			for (int x = 0; x < grid.getX(); x++)
			{
				Position pos = new Position(x, y);

				char c = grid.charAt(pos);

				if (depthFirst)
				{
					walkDepthFirst(pos, c, visited);
				}
				else
				{
					walkBreadthFirst(pos, c, visited);
				}
			}
		}
	}

	private void walkDepthFirst(Position pos, char c, Set<Position> visited)
	{
		if (!grid.valid(pos) || visited.contains(pos) || grid.charAt(pos) != c)
		{
			return;
		}

		visited.add(pos);

		add(pos, c);

		walkDepthFirst(pos.move(Direction.NORTH), c, visited);
		walkDepthFirst(pos.move(Direction.SOUTH), c, visited);
		walkDepthFirst(pos.move(Direction.EAST), c, visited);
		walkDepthFirst(pos.move(Direction.WEST), c, visited);
	}

	private void walkBreadthFirst(Position pos, char c, Set<Position> visited)
	{
		LinkedList<Position> queue = new LinkedList<>();

		queue.add(pos);

		while (!queue.isEmpty())
		{
			Position p = queue.poll();

			if (!grid.valid(p) || visited.contains(p) || grid.charAt(p) != c)
			{

			}
			else
			{
				visited.add(p);

				add(p, c);

				queue.add(p.move(Direction.NORTH));
				queue.add(p.move(Direction.SOUTH));
				queue.add(p.move(Direction.EAST));
				queue.add(p.move(Direction.WEST));
			}
		}
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