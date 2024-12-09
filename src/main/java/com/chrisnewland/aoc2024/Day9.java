package com.chrisnewland.aoc2024;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Day9
{
	public static void main(String[] args) throws IOException
	{
		List<String> lines = Files.readAllLines(Paths.get("src/main/resources/2024/day9.txt"));

		Day9 day9 = new Day9(lines);

		long result1 = day9.part1();
		System.out.printf("Part 1: %d\n", result1);

		long result2 = day9.part2();
		System.out.printf("Part 2: %d\n", result2);
	}

	private List<String> lines;

	interface FileItem
	{
		int length();
	}

	static class Block implements FileItem
	{
		int id;
		int length;

		public Block(int id, int length)
		{
			this.id = id;
			this.length = length;
		}

		@Override
		public int length()
		{
			return length;
		}

		@Override
		public String toString()
		{
			return "{" + id + "," + length + "}";
		}
	}

	static class Space implements FileItem
	{
		int length;

		public Space(int length)
		{
			this.length = length;
		}

		@Override
		public int length()
		{
			return length;
		}

		@Override
		public String toString()
		{
			return "{" + length + "}";
		}
	}

	public Day9(List<String> lines)
	{
		this.lines = lines;
	}

	private List<FileItem> mapToBlocks(String input, boolean wholeFiles)
	{
		List<FileItem> items = new ArrayList<>(input.length());

		int id = 0;

		int pos = 0;

		while (pos < input.length())
		{
			int blockLength = input.charAt(pos++) - '0';

			if (wholeFiles)
			{
				items.add(new Block(id, blockLength));
			}
			else
			{
				for (int b = 0; b < blockLength; b++)
				{
					items.add(new Block(id, 1));
				}
			}

			if (pos < input.length())
			{
				int freeLength = input.charAt(pos++) - '0';

				if (wholeFiles)
				{
					if (freeLength > 0)
					{
						items.add(new Space(freeLength));
					}
				}
				else
				{
					for (int f = 0; f < freeLength; f++)
					{
						items.add(new Space(1));
					}
				}
			}

			id++;
		}

		return items;
	}

	private List<FileItem> compact(List<FileItem> items)
	{
		List<FileItem> result = new ArrayList<>();

		int leftPosition = 0;
		int rightPosition = items.size() - 1;

		while (leftPosition <= rightPosition)
		{
			FileItem left = items.get(leftPosition);

			FileItem right = items.get(rightPosition);

			if (left instanceof Block)
			{
				result.add(left);
				leftPosition++;
			}
			else
			{
				if (right instanceof Block)
				{
					result.add(right);
					rightPosition--;
					leftPosition++;
				}
				else
				{
					rightPosition--;
				}
			}
		}

		return result;
	}

	private void compactWholeFiles(List<FileItem> items)
	{
		for (int rightPos = items.size() - 1; rightPos >= 0; rightPos--)
		{
			FileItem rightItem = items.get(rightPos);

			if (rightItem instanceof Space)
			{
				continue;
			}

			for (int i = 0; i < rightPos; i++)
			{
				FileItem leftItem = items.get(i);

				if (leftItem instanceof Space)
				{
					Space space = (Space) leftItem;

					Block rightBlock = (Block) rightItem;

					if (space.length >= rightBlock.length)
					{
						space.length -= rightBlock.length;
						items.remove(rightPos);
						items.add(rightPos, new Space(rightBlock.length));
						items.add(i, rightBlock);
						rightPos++;

						break;
					}
				}
			}
		}
	}

	private long checksum(List<FileItem> items)
	{
		long sum = 0;

		int pos = 0;

		for (FileItem item : items)
		{
			if (item instanceof Block)
			{
				sum += ((Block) item).id * pos++;
			}
		}

		return sum;
	}

	private long checksumWholeFiles(List<FileItem> items)
	{
		long sum = 0;

		int pos = 0;

		for (FileItem item : items)
		{
			for (int i = 0; i < item.length(); i++)
			{
				if (item instanceof Block)
				{
					sum += ((Block) item).id * pos;
				}

				pos++;
			}
		}

		return sum;
	}

	public long part1()
	{
		List<FileItem> items = mapToBlocks(lines.get(0), false);

		List<FileItem> compacted = compact(items);

		long checksum = checksum(compacted);

		return checksum;
	}

	public long part2()
	{
		List<FileItem> items = mapToBlocks(lines.get(0), true);

		compactWholeFiles(items);

		long checksum = checksumWholeFiles(items);

		return checksum;
	}

}