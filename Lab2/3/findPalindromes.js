findPalindromes = function(){
	var fullNumber = db.phones.find({},{"display": 1, "_id": 0}).toArray();
	var palindromes = []
	
	for (var i = 0 ; i < fullNumber.length ; i++){
        	var number = fullNumber[i].display
	        number = number.split("-")[1]
		numberReversed = number.split("").reverse().join("");
		if (number == numberReversed)
			palindromes.push(number);
	}
	return palindromes
}
