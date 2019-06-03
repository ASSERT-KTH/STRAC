import matplotlib.pyplot as plt
from json import loads
import sys
import numpy as np


radial = False

if __name__ == '__main__':
    js = loads(open(sys.argv[1], 'r').read())

    d = dict()
    index = 0
    x = []

    for h in js:
        if h not in d:
            d[h] = index
            index += 1
        x.append(d[h])



    if not radial:

        num_bins = len(d.keys())
        n, bins, patches = plt.hist(x, num_bins, facecolor='blue', alpha=0.1)
        plt.show()
    else:
        N = len(d.keys())
        theta = np.linspace(0.0, 2 * np.pi, N, endpoint=False)
        radii = 10 * np.random.rand(N)
        width = np.pi / 4 * np.random.rand(N)

        ax = plt.subplot(111, projection='polar')
        bars = ax.bar(theta, radii, width=width, bottom=0.0)

        # Use custom colors and opacity
        for r, bar in zip(radii, bars):
            #bar.set_facecolor(plt.cm.viridis(r / 10.))
            bar.set_alpha(0.5)

        plt.show()