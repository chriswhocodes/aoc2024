package com.chrisnewland.aoc2024;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Day3
{
	public static void main(String[] args) throws IOException
	{
		Day3 day3 = new Day3(Files.readString(Paths.get("src/main/resources/2024/day3.txt")));

		long result1 = day3.part1();
		System.out.printf("Part 1: %d\n", result1);

		long result2a = day3.part2Simple();
		System.out.printf("Part 2 simple: %d\n", result2a);
	}

	private String line;

	public Day3(String line)
	{
		this.line = line;
	}

	public long part1()
	{
		return processLine(line, false);
	}

	public long part2Simple()
	{
		return processLine(line, true);
	}

	public long processLine(String line, boolean checkEnables)
	{
		int length = line.length();

		int pos = 0;

		long sum = 0;

		boolean enabled = true;

		int lastMulPos = 0;

		while (pos < length)
		{
			int mul = line.indexOf("mul", pos);

			if (mul == -1)
			{
				break;
			}

			pos = mul + 3;

			char c = line.charAt(pos);

			if (c == '(')
			{
				boolean in1 = true;

				StringBuilder num1 = new StringBuilder();
				StringBuilder num2 = new StringBuilder();

				while (true)
				{
					pos++;

					c = line.charAt(pos);

					if (Character.isDigit(c))
					{
						if (in1)
						{
							num1.append(c);
						}
						else
						{
							num2.append(c);
						}
					}
					else if (in1 && c == ',')
					{
						in1 = false;
					}
					else if (!in1 && c == ')')
					{
						if (checkEnables && lastMulPos > 0)
						{
							String betweenMul = line.substring(lastMulPos, mul);

							int lastDo = betweenMul.lastIndexOf("do()");
							int lastDont = betweenMul.lastIndexOf("don't()");

							if (lastDont > lastDo)
							{
								enabled = false;
							}
							else if (lastDo > lastDont)
							{
								enabled = true;
							}
						}

						if (enabled)
						{
							sum += Long.parseLong(num1.toString()) * Long.parseLong(num2.toString());
						}

						lastMulPos = mul;
						break;
					}
					else
					{
						break;
					}
				}
			}
		}

		return sum;
	}
}