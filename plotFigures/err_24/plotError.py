import matplotlib.pyplot as plt
import hippoFileUtils as hfu

time_vj_5000 = hfu.readTrainingData('20180126_vj_5000_24_train.txt')
for i in range(1, len(time_vj_5000)):
	time_vj_5000[i] += time_vj_5000[i-1]

time_hvj = hfu.readTrainingData('20180124_hvj_24_train.txt')
time_hvj[0] /= 1000
for i in range(1, len(time_hvj)):
	time_hvj[i] /= 1000
	time_hvj[i] += time_hvj[i-1]

time_tri = hfu.readTrainingData('20180119_fuzzy_tri_std3_24_train.txt')
for i in range(1, len(time_tri)):
	time_tri[i] += time_tri[i-1]

def sumUp(lst):
	for i in range(1, len(lst)):
		lst[i] += lst[i-1]
	return lst

err_vj_5000 = hfu.readErr('20180126_vj_5000_24_test.txt')
err_hvj = hfu.readErr('20180124_hvj_24_err_test.txt')
err_tri = hfu.readErr('20180124_fuzzy_tri_std3_24_err_test.txt')

err_vj_5000 = [100*x for x in err_vj_5000]
err_hvj = [100*x for x in err_hvj]
err_tri = [100*x for x in err_tri]

plt.figure(1)
plt.subplot(111)
plt.plot(time_vj_5000, err_vj_5000, 'k:', linewidth = 1.5, label = 'Viola-Jones with K\' = 5000')
plt.plot(time_tri, err_tri, 'b--', linewidth = 1.5, label = r'Triangular MF - $\gamma = 3$')
plt.plot(time_hvj, err_hvj, 'm-', linewidth = 1.5, label = r'Improved Gaussian MF $\gamma = 2$')
plt.title('Error vs. Training time by Yi-Qing dataset')
plt.xlim(0, 3000)
plt.xlabel('Training time (second)')
plt.ylabel('Error (%)')
plt.legend()
#plt.show()

plt.savefig('20180124_all_err_24.png')