import matplotlib.pyplot as plt	
import hippoFileUtils as hfu

timeGauss = hfu.readTrainingData('20180125_fuzzy_gauss2_train.txt')
timeGauss.insert(0, 0);
for i in range(1, len(timeGauss)):
	timeGauss[i] /= 1000
	timeGauss[i] += timeGauss[i-1]

timeTri = hfu.readTrainingData('20180118_fuzzy_tri_std3_19_train.txt')
timeTri.insert(0, 0)
for i in range(1, len(timeTri)):
	timeTri[i] += timeTri[i-1]

timeVJ_5000 = hfu.readTrainingData('20171025_vj_5000_19_train.txt')
timeVJ_5000.insert(0, 0)
for i in range(1, len(timeVJ_5000)):
	timeVJ_5000[i] += timeVJ_5000[i-1]

idx = [i for i in range(len(timeVJ_5000))]

plt.figure(1)
plt.subplot(111)
plt.plot(idx, timeVJ_5000, 'k:', linewidth = 1.5, label = 'Viola - Jones with K\' = 5000')
plt.plot(idx, timeTri, 'b--', linewidth = 1.5, label = r'Triangular MF - $\gamma = 3$')
plt.plot(idx, timeGauss, 'm-', linewidth = 1.5, label = r'Improved Gaussian MF - $\gamma = 2$')

plt.title('Training time Comparison with MIT cbcl dataset')
plt.legend(loc = 'upper left')
plt.xlabel('Number of Weak Classifiers')
plt.xlim(0, 200)
plt.ylabel('Training time (second)')
plt.ylim(0, 6400)
plt.grid()
#plt.show()
plt.savefig('20180124_runtime_19.png')