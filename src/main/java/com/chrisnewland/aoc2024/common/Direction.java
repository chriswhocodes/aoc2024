package com.chrisnewland.aoc2024.common;

public enum Direction
{
    NORTH,
    SOUTH,
    EAST,
    WEST,
    NORTHEAST,
    NORTHWEST,
    SOUTHEAST,
    SOUTHWEST;

    public Direction rotateRight()
    {
        return switch(this)
        {
            case NORTH -> EAST;
            case EAST -> SOUTH;
            case SOUTH -> WEST;
            case WEST -> NORTH;
            default -> throw new IllegalStateException("Unexpected value: " + this);
        };
    }

    public Direction rotateLeft()
    {
        return switch(this)
        {
            case NORTH -> WEST;
            case WEST -> SOUTH;
            case SOUTH -> EAST;
            case EAST -> NORTH;
            default -> throw new IllegalStateException("Unexpected value: " + this);
        };
    }

    public char getSymbol()
    {
        return switch (this)
        {
            case NORTH -> '^';
            case WEST -> '<';
            case SOUTH -> 'v';
            case EAST -> '>';
            default -> throw new IllegalStateException("Unexpected value: " + this);
        };
    }
}