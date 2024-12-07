package com.chrisnewland.aoc2024.common;

import java.util.List;

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
        x = lines.size();
        y = lines.get(0).length();
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
        data[position.x][position.y] = c;
    }

    public char getCharAt(Position position)
    {
        return data[position.x][position.y];
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

    public boolean valid(Position pos)
    {
        return pos.x >= 0 && pos.x < x && pos.y >= 0 && pos.y < y;
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