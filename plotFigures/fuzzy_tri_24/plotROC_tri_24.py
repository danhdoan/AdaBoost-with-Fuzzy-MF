import matplotlib.pyplot as plt
import hippoFileUtils as hfu

DR_tri1, FP_tri1 = hfu.readClassificationRate('20180119_fuzzy_tri_std1_24.txt')
DR_tri15, FP_tri15 = hfu.readClassificationRate('20180119_fuzzy_tri_std15_24.txt')
DR_tri2, FP_tri2 = hfu.readClassificationRate('20180117_fuzzy_tri_std2_24.txt')
DR_tri25, FP_tri25 = hfu.readClassificationRate('20180119_fuzzy_tri_std25_24.txt')
DR_tri3, FP_tri3 = hfu.readClassificationRate('20180119_fuzzy_tri_std3_24.txt')

plt.figure(1)
plt.subplot(111)
plt.title('Yi-Qing dataset')
plt.xlabel('False Positive Rate (%)')
plt.ylabel('Detection Rate (%)')


plt.plot(FP_tri1, DR_tri1, color = 'k', marker = '.', label = r'Triangular MF $\gamma = 1$')
plt.plot(FP_tri15, DR_tri15, color = 'b', marker = 'v', label = r'Triangular MF $\gamma = 1.5$')
plt.plot(FP_tri2, DR_tri2, color = 'r', marker = 'o', label = r'Triangular MF $\gamma = 2$')
plt.plot(FP_tri25, DR_tri25, color = 'g', marker = 's', label = r'Triangular MF $\gamma = 2.5$')
plt.plot(FP_tri3, DR_tri3, color = 'm', marker = 'v', label = r'Triangular MF $\gamma = 3$')

plt.ylim(95, 100)
plt.legend(loc = 'lower right')
plt.grid()
#plt.show()

plt.savefig('20180125_fuzzy_tri_24.png')
