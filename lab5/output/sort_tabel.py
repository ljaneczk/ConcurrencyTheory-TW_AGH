#!/usr/bin/python3
import sys


def main(args):
    file_name = args[0]
    with open(file_name) as file:
        lines = file.readlines()
    with open(file_name[:file_name.rfind(".")] + "_sorted.txt", "w") as file:
        file.write(lines[0])
        i = 1
        while i < len(lines):
            file.write(lines[i])
            j = i+1
            group_lines = []
            while j < len(lines) and lines[j][0] != "-":
                fourth = lines[j].split("|")[3]
                group_lines.append((fourth.split("+")[1], fourth, lines[j]))
                j += 1
            group_lines = sorted(group_lines)
            for line_tuple in group_lines:
                file.write(line_tuple[2])
            i = j


if __name__ == "__main__":
    main(sys.argv[1:])
