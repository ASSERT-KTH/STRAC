import sys
from json import loads
import matplotlib.pyplot as plt
import numpy as np
import matplotlib

def adjacent_values(vals, q1, q3):
    upper_adjacent_value = q3 + (q3 - q1) * 1.5
    upper_adjacent_value = np.clip(upper_adjacent_value, q3, vals[-1])

    lower_adjacent_value = q1 - (q3 - q1) * 1.5
    lower_adjacent_value = np.clip(lower_adjacent_value, vals[0], q1)
    return lower_adjacent_value, upper_adjacent_value


def set_axis_style(ax, labels):
    ax.get_xaxis().set_tick_params(direction='out')
    ax.xaxis.set_ticks_position('bottom')
    ax.set_xticks(np.arange(1, len(labels) + 1))
    ax.set_xticklabels(labels)
    ax.set_xlim(0.25, len(labels) + 0.75)
    ax.set_xlabel('Sample name')

def process(file):
    js = loads(open(file, 'r').read())

    values = [js["results"]]

    red_square = dict(markerfacecolor='r', marker='s')
    fig5, ax5 = plt.subplots()
    ax5.set_title('Distance distribution DTW output comparing n executions of the same script')

    quartile1, medians, quartile3 = np.percentile(values, [25, 50, 75], axis=1)
    ax5.violinplot(values, showmedians=False, showmeans=False )

    inds = np.arange(1, len(medians) + 1)

    whiskers = np.array([
    adjacent_values(sorted_array, q1, q3)
    for sorted_array, q1, q3 in zip(values, quartile1, quartile3)])
    whiskersMin, whiskersMax = whiskers[:, 0], whiskers[:, 1]

    ax5.vlines(inds, quartile1, quartile3, color='k', linestyle='-', lw=2)
    #ax5.vlines(inds, whiskersMin, whiskersMax, color='k', linestyle='-', lw=1)


    ax5.scatter(inds, medians, marker='o', color='red')
    ax5.set_ylim([-0.3, 1])

   # ax5.scatter([q1, q2, q3])




if __name__ == '__main__':
    process(sys.argv[1])

    plt.show()