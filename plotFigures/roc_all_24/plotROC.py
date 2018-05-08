import matplotlib.pyplot as plt	

fileData_vj = open("20180126_vj_5000_24.txt", "r")
detectRate_vj = []
falsePos_vj = []
for line in fileData_vj:
	strRate, strDR, strFPR = line.split(" ")
	rate = float(strRate)
	DR = float(strDR)
	FPR = float(strFPR)
	detectRate_vj.append(DR)
	falsePos_vj.append(FPR)

fileData_hvj = open("20180125_fuzzy_gauss2_24.txt", "r")
detectRate_hvj = []
falsePos_hvj = []
for line in fileData_hvj:
	strRate, strDR, strFPR = line.split(" ")
	rate = float(strRate)
	DR = float(strDR)
	FPR = float(strFPR)
	detectRate_hvj.append(DR)
	falsePos_hvj.append(FPR)

fileData_fz_tri3 = open("20180119_fuzzy_tri_std3_24.txt", "r")
detectRate_fz_tri3 = []
falsePos_fz_tri3 = []
for line in fileData_fz_tri3:
	strRate, strDR, strFPR = line.split(" ")
	rate = float(strRate)
	DR = float(strDR)
	FPR = float(strFPR)
	detectRate_fz_tri3.append(DR)
	falsePos_fz_tri3.append(FPR)

fig = plt.figure()
ax = fig.add_subplot(111)
ax.plot(falsePos_vj, detectRate_vj, color = 'k', marker = '.', label = 'Viola-Jones with K\' = 5000')
ax.plot(falsePos_fz_tri3, detectRate_fz_tri3, color = 'b', marker = '^', label = r'Triangular MF - $\gamma = 3$')
ax.plot(falsePos_hvj, detectRate_hvj, color = 'm', marker = 'v', label = r'Improved Gaussian MF - $\gamma = 2$')

plt.title('Yi-Qing dataset')
plt.legend(loc = 'lower right')
plt.xlabel('False Postive Rate (%)')
plt.xlim(0, 100)
plt.ylabel('Detection Rate (%)')
plt.ylim(95, 100)
plt.grid()
plt.show()
#plt.savefig('20180123_roc_all_24.png')