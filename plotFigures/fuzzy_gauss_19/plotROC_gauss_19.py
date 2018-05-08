import matplotlib.pyplot as plt
import hippoFileUtils as hfu

DR_gauss1, FP_gauss1 = hfu.readClassificationRate('20180125_fuzzy_gauss1_19.txt')
DR_gauss2, FP_gauss2 = hfu.readClassificationRate('20171102_hvj_gauss_19.txt')
DR_gauss3, FP_gauss3 = hfu.readClassificationRate('20180125_fuzzy_gauss3_19.txt')

plt.figure(1)
plt.subplot(111)
plt.title('MIT cbcl dataset')
plt.xlabel('False Positive Rate (%)')
plt.ylabel('Detection Rate (%)')


plt.plot(FP_gauss1, DR_gauss1, color = 'k', marker = '.', label = r'Improved Gaussian MF $\gamma = 1$')
plt.plot(FP_gauss2, DR_gauss2, color = 'm', marker = 'v', label = r'Improved Gaussian MF $\gamma = 2$')
plt.plot(FP_gauss3, DR_gauss3, color = 'b', marker = '^', label = r'Improved Gaussian MF $\gamma = 3$')

plt.ylim(70, 100)
plt.legend(loc = 'lower right')
plt.grid()
#plt.show()

plt.savefig('20180125_fuzzy_gauss_19.png')
