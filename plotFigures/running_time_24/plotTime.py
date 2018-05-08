import matplotlib.pyplot as plt	
import hippoFileUtils as hfu

timeVJ_5000 = hfu.readTrainingData('20180126_vj_5000_24_train.txt')
timeVJ_5000.insert(0, 0)

timeGauss = hfu.readTrainingData('20180125_fuzzy_gauss2_24_train.txt')
timeGauss.insert(0, 0)
for i in range(1, len(timeGauss)):
	timeGauss[i] /= 1000

timeTri = hfu.readTrainingData('20180119_fuzzy_tri_std3_24_train.txt')
timeTri.insert(0, 0)

def sumUp(lst):
	for i in range(1, len(lst)):
		lst[i] += lst[i-1]
	return lst

timeVJ_5000 = sumUp(timeVJ_5000)
timeTri = sumUp(timeTri)
timeGauss = sumUp(timeGauss)

idx = [i for i in range(len(timeGauss))]

plt.figure(1)
plt.subplot(111)

plt.plot(idx, timeVJ_5000, 'k:', linewidth = 1.5, label = 'Viola-Jones with K\' = 5000')
plt.plot(idx, timeTri, 'b--', linewidth = 1.5, label = r'Triangular MF - $\gamma = 3$')
plt.plot(idx, timeGauss, 'm-', linewidth = 1.5, label = r'Improved Gaussian MF - $\gamma = 2$')

plt.legend(loc = 'upper left')
plt.xlabel('Number of Weak Classifiers')
plt.xlim(0, 200)
plt.ylabel('Training time (second)')
plt.ylim(0, 3580)
plt.grid()
#plt.show()
plt.savefig('20180124_runtime_24.png')