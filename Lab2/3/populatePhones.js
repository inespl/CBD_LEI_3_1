populatePhones = function (country, start, stop) {

  var prefixes = [21, 22, 231, 232, 233, 234 ];
  for (var i = start; i <= stop; i++) {

    var prefix = prefixes[(Math.random() * 6) << 0]
    var countryNumber = (prefix * Math.pow(10, 9 - prefix.toString().length)) + i;
    var num = (country * 1e9) + countryNumber;
    var fullNumber = "+" + country + "-" + countryNumber;

    db.phones.insert({
      _id: num,
      components: {
        country: country,
        prefix: prefix,
        number: i
      },
      display: fullNumber
    });
    print("Inserted number " + fullNumber);
  }
  print("Done!");
}

findPalindromes = function(){
	var fullNumber = db.phones.find({},{"display": 1, "_id": 0}).toArray();
	var palindromes = []
	
	for (var i = 0 ; i < fullNumber.length ; i++){
        	var number = fullNumber[i].display
	        number = number.split("-")[1]
		print(number)
		numberReversed = number.split("").reverse().join("");
		if (number == numberReversed)
			palindromes.push(number)
	}
	return palindromes
}
