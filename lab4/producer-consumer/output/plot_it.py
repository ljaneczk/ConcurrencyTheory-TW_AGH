#!/usr/bin/python3
import matplotlib.pyplot as plt
import sys


def main(file_name):
    with open(file_name) as file:
        lines = file.readlines()
        c_lines = [x for x in lines if x[0] == 'C']
        p_lines = [x for x in lines if x[0] == 'P']
        c_x = list(sorted(set([int(x.split()[1]) for x in c_lines])))
        c_x = [str(x) for x in c_x]
        c_x_y = [[] for x in range(int(c_x[-1])+1)]
        for x in c_lines:
            c_x_y[int(x.split()[1])].append(int(x.split()[2]))
        c_y = []
        for i in c_x:
            elms = c_x_y[int(i)]
            c_y.append(sum(elms) / len(elms))

        p_x = list(sorted(set([int(x.split()[1]) for x in p_lines])))
        p_x = [str(x) for x in p_x]
        p_x_y = [[] for x in range(int(p_x[-1])+1)]
        for x in p_lines:
            p_x_y[int(x.split()[1])].append(int(x.split()[2]))
        p_y = []
        for i in p_x:
            elms = p_x_y[int(i)]
            p_y.append(sum(elms) / len(elms))
            
        plt.plot(c_x, c_y, c='r')
        plt.title("Consumers times")
        plt.savefig('plots/c_chart_' + file_name[:file_name.rfind(".")] + ".png")
        plt.show()

        plt.plot(p_x, p_y, c='r')
        plt.savefig('plots/p_chart_' + file_name[:file_name.rfind(".")] + ".png")
        plt.title("Producers times")
    plt.show()


if __name__ == "__main__":
    main(sys.argv[1])