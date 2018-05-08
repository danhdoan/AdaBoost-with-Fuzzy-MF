import matplotlib.pyplot as plt
import hippoFileUtils as hfu


time_vj_5000 = hfu.readTrainingData('20171025_vj_5000_19_train.txt')
for i in range(1, len(time_vj_5000)):
	time_vj_5000[i] += time_vj_5000[i-1]

time_gauss = hfu.readTrainingData('20180125_fuzzy_gauss2_train.txt')
time_gauss[0] /= 1000
for i in range(1, len(time_gauss)):
	time_gauss[i] /= 1000
	time_gauss[i] += time_gauss[i-1]

time_tri = hfu.readTrainingData('20180118_fuzzy_tri_std3_19_train.txt')
for i in range(1, len(time_tri)):
	time_tri[i] += time_tri[i-1]

err_vj_5000 = hfu.readErr('20171025_vj_5000_19_test.txt')
err_gauss = hfu.readErr('20180125_fuzzy_gauss2_test.txt')
err_tri = hfu.readErr('20180118_fuzzy_tri_std3_19_test.txt')

err_vj_5000 = [100*x for x in err_vj_5000]
err_gauss = [100*x for x in err_gauss]
err_tri = [100*x for x in err_tri]

plt.figure(1)
plt.subplot(111)
plt.plot(time_vj_5000, err_vj_5000, 'k:', linewidth = 1.5, label = 'Viola-Jones with K\' = 5000')
plt.plot(time_tri, err_tri, 'b--', linewidth = 1.5, label = r'Triangular MF - $\gamma = 3$')
plt.plot(time_gauss, err_gauss, 'm-', linewidth = 1.5, label = r'Improved Gaussian MF $\gamma = 2$')
plt.title('Error vs. Training time by MIT cbcl dataset')
plt.xlim(0, 3500)
plt.xlabel('Training time (second)')
plt.ylabel('Error (%)')
plt.legend()
#plt.show()

plt.savefig('20180124_all_err_19.png')