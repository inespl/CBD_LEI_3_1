## Lab 3

### a)
Abrir terminal na pasta com o ficheiro populatePhones.js e iniciar mongo.
Ir para a db cbd e criar a coleção phones. Fazer o comando load("populatePhones.js") 
**Nota: Isto carrega na "current shell", não armazena no servidor**

### b) 
Fazer `db.phones.drop()` para limpar a coleção
Criar 200 000 números com `populatePhones(351, 1, 200000)`

### c)
Construa uma função/expressão que conte o número de telefones existentes em cada um dos indicativos nacionais (prefix).
s
db.phones.aggregate( [ { $group: { _id: "$components.prefix", Total: { $sum: 1} } } ] )

### d)
Construa, e teste no servidor, uma função em JavaScript que encontre um tipo de padrão na lista (e.g., capicuas, sequências, dígitos não repetidos, etc.).

Função: findPalindromes.js

```
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
```





