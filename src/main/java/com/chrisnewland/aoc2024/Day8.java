package com.chrisnewland.aoc2024;

import com.chrisnewland.aoc2024.common.Grid;
import com.chrisnewland.aoc2024.common.Position;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day8
{
    public static void main(String[] args) throws IOException
    {
        List<String> lines = Files.readAllLines(Paths.get("src/main/resources/2024/day8.txt"));

        Day8 day8 = new Day8(lines);

        long result1 = day8.part1();
        System.out.printf("Part 1: %d\n", result1);

        long result2 = day8.part2();
        System.out.printf("Part 2: %d\n", result2);
    }

    private List<String> lines;

    public Day8(List<String> lines)
    {
        this.lines = lines;
    }

    public long part1()
    {
        return countAntinodes(true);
    }

    public long part2()
    {
        return countAntinodes(false);
    }

    private long countAntinodes(boolean oneEachSide)
    {
        Grid grid = new Grid(lines);

        Set<Character> antennas = grid.getUniqueCharacters();

        Set<Position> antinodes = new HashSet<>();

        for (char a : antennas)
        {
            if (a != '.')
            {
                List<Position> positions = grid.getPositionsOf(a);

                if (positions.size() > 1)
                {
                    antinodes.addAll(findAntinodes(grid, positions, oneEachSide));

                    if (!oneEachSide)
                    {
                        antinodes.addAll(positions);
                    }
                }
            }
        }

        return antinodes.size();
    }

    private Set<Position> findAntinodes(Grid grid, List<Position> positions, boolean oneEachSide)
    {
        Set<Position> antinodes = new HashSet<>();

        int count = positions.size();

        for (int i = 0; i < count; i++)
        {
            Position first = positions.get(i);

            for (int j = 0; j < count; j++)
            {
                Position second = positions.get(j);

                if (first.equals(second))
                {
                    continue;
                }

                Position vector = new Position(second.getX() - first.getX(), second.getY() - first.getY());

                Position start = new Position(first);

                do
                {
                    Position antinodeBefore = start.subtract(vector);

                    if (grid.valid(antinodeBefore))
                    {
                        antinodes.add(antinodeBefore);
                        start = antinodeBefore;
                    }
                    else
                    {
                        break;
                    }
                } while (!oneEachSide);
                
                start = new Position(second);

                do
                {
                    Position antinodeAfter = start.add(vector);

                    if (grid.valid(antinodeAfter))
                    {
                        antinodes.add(antinodeAfter);
                        start = antinodeAfter;
                    }
                    else
                    {
                        break;
                    }
                } while (!oneEachSide);
            }
        }

        return antinodes;
    }
}