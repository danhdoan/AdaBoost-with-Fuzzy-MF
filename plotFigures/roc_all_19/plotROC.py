import matplotlib.pyplot as plt	

file_hvj = open("20180125_fuzzy_gauss2_19.txt", "r")
DR_hvj = []
FP_hvj = []
for line in file_hvj:
	rate, DR, FPR = [float(x) for x in line.split(" ")]
	DR_hvj.append(DR)
	FP_hvj.append(FPR)

file_tri = open("20180118_fuzzy_tri_std3_19.txt", "r")
DR_tri = []
FP_tri = []
for line in file_tri:
	rate, DR, FPR = [float(x) for x in line.split(" ")]
	DR_tri.append(DR)
	FP_tri.append(FPR)

file_vj = open("20171025_vj_5000_19.txt", "r")
DR_vj = []
FP_vj = []
for line in file_vj:
	rate, DR, FPR = [float(x) for x in line.split(" ")]
	DR_vj.append(DR)
	FP_vj.append(FPR)

fig = plt.figure()
ax = fig.add_subplot(111)
ax.plot(FP_vj, DR_vj, color = 'k', marker = '.', label = 'Viola-Jones with K\' = 5000')
ax.plot(FP_tri, DR_tri, color = 'b', marker = '^', label = r'Triangular MF - $\gamma = 3$')
ax.plot(FP_hvj, DR_hvj, color = 'm', marker = 'v', label = r'Improved Gaussian MF - $\gamma = 2$')

plt.title('MIT cbcl dataset')
plt.legend(loc = 'lower right')
plt.xlabel('False Postive Rate (%)')
plt.xlim(0, 100)
plt.ylabel('Detection Rate (%)')
plt.ylim(70, 100)
plt.grid()
#plt.show()
plt.savefig('20180123_roc_all_19.png')