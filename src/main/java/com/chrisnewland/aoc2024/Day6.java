package com.chrisnewland.aoc2024;

import com.chrisnewland.aoc2024.common.Direction;
import com.chrisnewland.aoc2024.common.Grid;
import com.chrisnewland.aoc2024.common.Position;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Day6
{
    public static void main(String[] args) throws IOException
    {
        List<String> lines = Files.readAllLines(Paths.get("src/main/resources/2024/day6.txt"));

        Day6 day6 = new Day6(lines);

        long result1 = day6.part1();
        System.out.printf("Part 1: %d\n", result1);

        long result2 = day6.part2ExhaustiveSearch();
        System.out.printf("Part 2: %d\n", result2);
    }

    private Grid grid;

    private static final char OBSTACLE = '#';

    public Day6(List<String> lines)
    {
        grid = new Grid(lines);
    }

    public long part1()
    {
        Position pos = grid.getStartingPosition('^');

        char visited = 'X';

        grid.set(pos, visited);

        boolean onGrid = true;

        Direction direction = Direction.NORTH;

        while (onGrid)
        {
            Position next = pos.move(direction);

            if (!grid.valid(next))
            {
                onGrid = false;
            }
            else if (grid.charAt(next) != OBSTACLE)
            {
                pos = next;
                grid.set(pos, visited);
            }
            else if (grid.charAt(next) == OBSTACLE)
            {
                direction = direction.rotateRight();
            }
        }

        return grid.countOccurrences(visited);
    }

    public long part2ExhaustiveSearch()
    {
        long obstaclePositions = 0;

        for (int y = 0; y < grid.getY(); y++)
        {
            for (int x = 0; x < grid.getX(); x++)
            {
                Position testObstacle = new Position(x, y);

                grid.reset();

                char contents = grid.charAt(testObstacle);

                if (contents == '.')
                {
                    grid.set(testObstacle, OBSTACLE);

                    long steps = solvesInSteps();

                    if (steps == -1)
                    {
                        obstaclePositions++;
                    }
                }
            }
        }

        return obstaclePositions;
    }

    private long solvesInSteps()
    {
        Position pos = grid.getStartingPosition('^');

        char visited = 'X';

        boolean onGrid = true;

        Direction direction = Direction.NORTH;

        Set<Position> turningPoints = new HashSet<>();

        boolean moved = false;

        while (onGrid)
        {
            Position next = pos.move(direction);

            if (!grid.valid(next))
            {
                onGrid = false;
            }
            else if (grid.charAt(next) != OBSTACLE)
            {
                pos = next;
                grid.set(pos, visited);
                moved = true;
            }
            else if (grid.charAt(next) == OBSTACLE)
            {
                direction = direction.rotateRight();

                if (moved && turningPoints.contains(pos))
                {
                    return -1;
                }
                else
                {
                    turningPoints.add(pos);
                }
                moved = false;
            }
        }

        return grid.countOccurrences(visited);
    }

    public long part2CompleteSquares()
    {
        Position pos = grid.getStartingPosition('^');

        boolean onGrid = true;

        Direction direction = Direction.NORTH;

        grid.set(pos, direction.getSymbol());

        List<Position> turningPoints = new LinkedList<>();

        Set<Position> addedPositions = new HashSet<>();

        while (onGrid)
        {
            Position next = pos.move(direction);

            if (!grid.valid(next))
            {
                onGrid = false;
            }
            else if (grid.charAt(next) != OBSTACLE)
            {
                pos = next;

                if (direction == Direction.NORTH || direction == Direction.SOUTH)
                {
                    grid.set(pos, '|');
                }
                else
                {
                    grid.set(pos, '-');
                }
            }
            else if (grid.charAt(next) == OBSTACLE)
            {
                grid.set(pos, '+');

                direction = direction.rotateRight();

                turningPoints.add(pos);

                if (turningPoints.size() == 3)
                {
                    Position added = completeSquare(turningPoints, direction);

                    addedPositions.add(added);

                    grid.set(added, 'O');

                    turningPoints.clear();
                }
            }
        }

        return addedPositions.size();
    }

    Position completeSquare(List<Position> turningPoints, Direction direction)
    {
        Position pos0 = turningPoints.get(0);
        Position pos1 = turningPoints.get(1);
        Position pos2 = turningPoints.get(2);

        int distance0to1 = pos0.straightLineDistance(pos1);

        Position result = new Position(pos2);

        for (int i = 0; i < distance0to1 + 1; i++)
        {
            result = result.move(direction);
        }

        return result;
    }
}