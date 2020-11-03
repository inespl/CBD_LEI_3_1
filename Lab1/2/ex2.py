letter_dict = {} 

nameFile = open("female-names.txt", "r")
for line in nameFile:
	if line[0].upper() not in letter_dict:
		letter_dict[line[0].upper()] = 1
	else:
		letter_dict[line[0].upper()] +=1

writeFile = open("initials4redis.txt", "w")
for key, value in letter_dict.items():
	writeFile.write(f"SET {key} {value}\n")
