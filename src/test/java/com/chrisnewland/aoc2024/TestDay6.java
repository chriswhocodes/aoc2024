package com.chrisnewland.aoc2024;

import com.chrisnewland.aoc2024.common.Direction;
import com.chrisnewland.aoc2024.common.Position;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestDay6
{
    @Test
    public void testCompleteSquare1()
    {
        List<String> lines = new ArrayList<>();
        lines.add("....#.....");
        lines.add("....0---1#");
        lines.add("....|...|.");
        lines.add("..#.|...|.");
        lines.add("....|..#|.");
        lines.add("....|...|.");
        lines.add(".#.O^---2.");
        lines.add("........#.");
        lines.add("#.........");
        lines.add("......#...");

        Day6 day6 = new Day6(lines);

        assertEquals(new Position(3, 6), day6.completeSquare(List.of(new Position(4, 1), new Position(8, 1), new Position(8, 6)), Direction.WEST));
    }

    @Test
    public void testCompleteSquare2()
    {
        List<String> lines = new ArrayList<>();
        lines.add("....#.....");
        lines.add("....+---+#");
        lines.add("....|...|.");
        lines.add("..#.|...|.");
        lines.add("..1-+-2#|.");
        lines.add("..|.|.|.|.");
        lines.add(".#0-^-+-+.");
        lines.add("......O.#.");
        lines.add("#.........");
        lines.add("......#...");

        Day6 day6 = new Day6(lines);

        assertEquals(new Position(6, 7), day6.completeSquare(List.of(new Position(2, 6), new Position(2, 4), new Position(6, 4)), Direction.SOUTH));
    }

    @Test
    public void testCompleteSquare3()
    {
        List<String> lines = new ArrayList<>();
        lines.add("....#.....");
        lines.add("....+---+#");
        lines.add("....|...|.");
        lines.add("..#.|...|.");
        lines.add("..+-+-+#|.");
        lines.add("..|.|.|.|.");
        lines.add(".#+-^-+-+.");
        lines.add(".+----+O#.");
        lines.add("#+----+...");
        lines.add("......#...");

        Day6 day6 = new Day6(lines);

        assertEquals(new Position(7, 7), day6.completeSquare(List.of(new Position(6, 8), new Position(1, 8), new Position(1, 7)), Direction.EAST));
    }

    @Test
    public void testCompleteSquare4()
    {
        List<String> lines = new ArrayList<>();
        lines.add("....#.....");
        lines.add("....+---+#");
        lines.add("....|...|.");
        lines.add("..#.|...|.");
        lines.add("..+-+-+#|.");
        lines.add("..|.|.|.|.");
        lines.add(".#+-^-+-+.");
        lines.add("..|...|.#.");
        lines.add("#O+---+...");
        lines.add("......#...");

        Day6 day6 = new Day6(lines);

        assertEquals(new Position(1, 8), day6.completeSquare(List.of(new Position(2, 4), new Position(6, 4), new Position(6, 8)), Direction.WEST));
    }

    @Test
    public void testCompleteSquare5()
    {
        List<String> lines = new ArrayList<>();
        lines.add("....#.....");
        lines.add("....+---+#");
        lines.add("....|...|.");
        lines.add("..#.|...|.");
        lines.add("..+-+-+#|.");
        lines.add("..|.|.|.|.");
        lines.add(".#+-^-+-+.");
        lines.add("....|.|.#.");
        lines.add("#..O+-+...");
        lines.add("......#...");

        Day6 day6 = new Day6(lines);

        assertEquals(new Position(3, 8), day6.completeSquare(List.of(new Position(4, 4), new Position(6, 4), new Position(6, 8)), Direction.WEST));
    }

    @Test
    public void testCompleteSquare6()
    {
        List<String> lines = new ArrayList<>();
        lines.add("....#.....");
        lines.add("....+---+#");
        lines.add("....|...|.");
        lines.add("..#.|...|.");
        lines.add("..+-+-+#|.");
        lines.add("..|.|.|.|.");
        lines.add(".#+-^-+-+.");
        lines.add(".+----++#.");
        lines.add("#+----++..");
        lines.add("......#O..");

        Day6 day6 = new Day6(lines);

        assertEquals(new Position(7, 9), day6.completeSquare(List.of(new Position(1, 8), new Position(1, 7), new Position(7, 7)), Direction.SOUTH));
    }

}