package com.chrisnewland.aoc2024.common;

import java.util.Objects;

public class Position
{
    private int x;
    private int y;

    public Position(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public Position(Position pos)
    {
        this(pos.x, pos.y);
    }

    public Position move(Direction direction)
    {
        return switch (direction)
        {
            case NORTH -> new Position(x, y - 1);
            case SOUTH -> new Position(x, y + 1);
            case EAST -> new Position(x + 1, y);
            case WEST -> new Position(x - 1, y);
            case NORTHEAST -> new Position(x + 1, y - 1);
            case NORTHWEST -> new Position(x - 1, y - 1);
            case SOUTHEAST -> new Position(x + 1, y + 1);
            case SOUTHWEST -> new Position(x - 1, y + 1);
        };
    }

    public Position add(Position vector)
    {
        return new Position(x + vector.x, y + vector.y);
    }

    public Position subtract(Position vector)
    {
        return new Position(x - vector.x, y - vector.y);
    }

    @Override
    public String toString()
    {
        return "Position{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }
        Position position = (Position) o;
        return x == position.x && y == position.y;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(x, y);
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public int straightLineDistance(Position position)
    {
        if (position.x == x)
        {
            return Math.abs(y - position.y);
        }
        else if (position.y == y)
        {
            return Math.abs(x - position.x);
        }
        else {
            throw new RuntimeException("Not a straight line between " + this +" and " + position);
        }
    }

    public boolean adjacent(Position other)
    {
        return move(Direction.NORTH).equals(other) || move(Direction.SOUTH).equals(other) || move(Direction.EAST).equals(other) || move(Direction.WEST).equals(other);
    }
}