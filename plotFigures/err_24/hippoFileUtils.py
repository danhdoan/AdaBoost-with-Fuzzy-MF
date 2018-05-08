def readTrainingData(fileName):
	data = []
	with open(fileName, 'r') as file:
		while True:
			line = file.readline()
			if line == "":
				break
			if line.find("Time") != -1:
				_, time = line.split()
				data.append(float(time))
	return data

def readErr(fileName):
	data = []
	with open(fileName, 'r') as file:
		for line in file:
			length, err = line.split()
			data.append(float(err))

	return data
