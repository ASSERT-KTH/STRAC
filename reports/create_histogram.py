import matplotlib.pyplot as plt
from json import loads
import sys




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



    num_bins = len(d.keys())
    n, bins, patches = plt.hist(x, num_bins, facecolor='blue', alpha=0.1)
    plt.show()