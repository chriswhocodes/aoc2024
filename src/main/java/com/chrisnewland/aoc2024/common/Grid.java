package com.chrisnewland.aoc2024.common;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Grid
{
    private int x;
    private int y;

    private char[][] data;
    private char[][] originalData;

    public Grid(int x, int y)
    {
        this.x = x;
        this.y = y;
        data = new char[x][y];
        originalData = new char[x][y];
    }

    public Grid(List<String> lines)
    {
        x = lines.get(0).length();
        y = lines.size();
        data = new char[x][y];
        originalData = new char[x][y];

        int ypos = 0;

        for (String line : lines)
        {
            char[] chars = line.toCharArray();

            for (int xpos = 0; xpos < chars.length; xpos++)
            {
                data[xpos][ypos] = chars[xpos];
            }

            save();

            ypos++;
        }
    }

    private void save()
    {
        for (int ypos = 0; ypos < y; ypos++)
        {
            for (int xpos = 0; xpos < x; xpos++)
            {
                originalData[xpos][ypos] = data[xpos][ypos];
            }
        }
    }

    public void swapChar(Position pos0, Position pos1)
    {
        char char0 = charAt(pos0);
        char char1 = charAt(pos1);
        
        set(pos0, char1);
        set(pos1, char0);
    }


    public void reset()
    {
        for (int ypos = 0; ypos < y; ypos++)
        {
            for (int xpos = 0; xpos < x; xpos++)
            {
                data[xpos][ypos] = originalData[xpos][ypos];
            }
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

    public String toStringInt()
    {
        StringBuilder sb = new StringBuilder();

        for (int ypos = 0; ypos < y; ypos++)
        {
            for (int xpos = 0; xpos < x; xpos++)
            {
                sb.append((int)(data[xpos][ypos]));
            }
            sb.append("\n");
        }

        return sb.toString();
    }

    public int countOccurrences(char c)
    {
        int count = 0;

        for (int ypos = 0; ypos < y; ypos++)
        {
            for (int xpos = 0; xpos < x; xpos++)
            {
                if (data[xpos][ypos] == c)
                {
                    count++;
                }
            }
        }

        return count;
    }

    public List<Position> getPositionsOf(char c)
    {
        List<Position> positions = new ArrayList<>();

        for (int ypos = 0; ypos < y; ypos++)
        {
            for (int xpos = 0; xpos < x; xpos++)
            {
                if (data[xpos][ypos] == c)
                {
                    positions.add(new Position(xpos, ypos));
                }
            }
        }

        return positions;
    }

    public Set<Character> getUniqueCharacters()
    {
        Set<Character> chars = new HashSet<>();

        for (int ypos = 0; ypos < y; ypos++)
        {
            for (int xpos = 0; xpos < x; xpos++)
            {
                chars.add(data[xpos][ypos]);
            }
        }

        return chars;
    }

    public Position getStartingPosition(char c)
    {
        for (int ypos = 0; ypos < y; ypos++)
        {
            for (int xpos = 0; xpos < x; xpos++)
            {
                if (data[xpos][ypos] == c)
                {
                    return new Position(xpos, ypos);
                }
            }
        }

        throw new RuntimeException("No start position found");
    }

    public void set(Position position, char c)
    {
        data[position.getX()][position.getY()] = c;
    }

    public char getCharAt(Position position)
    {
        return data[position.getX()][position.getY()];
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
        return data[pos.getX()][pos.getY()];
    }

    public boolean valid(Position pos)
    {
        return pos.getX() >= 0 && pos.getX() < x && pos.getY() >= 0 && pos.getY() < y;
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }
}