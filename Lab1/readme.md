# REDIS
Redis is an open-source NoSQL key-value datastore where instead of storing just one type of value, such as a string, more complex types of values can be stored. 
 -> Memoria associativa
 
$ redis-server
	- run Redis
$ redis-cli
	- built-in client
	
**Data-Types:** http://intro2libsys.info/introduction-to-redis/redis-data-types

## 1.2
Usei um python script:
1. Ler o ficheiro de nomes
2. A cada linha inserir num dicionário a letra (key) e o número (value)
3. Escrever num ficheiro (initials4redis.txt) "Set {key} {value}" para todos os elems do dicionário
4. No terminal, executar os comandos:
```
python3 ex2.py
sudo cat initials4redis.txt | redis-cli --pipe
```
O primeiro executa o script, escrevendo no ficheiro, o segundo põe executa-os no redis, podendo nós aceder a eles no redis-cli.

## 1.3, 1.4 e 1,5
Criar projeto Maven
adicionar as dependencias do Jedis
no pom.xml passar nas properties para a 11 em vez de 1.7

**Atenção!!**
Ter o package correto nas classes

Para correr o 1.3:
```
mvn exec:java -Dexec.mainClass="cbd.app.SimplePost"
mvn exec:java -Dexec.mainClass="cbd.app.ListImpl"
mvn exec:java -Dexec.mainClass="cbd.app.HashImpl"

```
Para correr o 1.4 a):
```
mvn exec:java -Dexec.mainClass="cbd.ex.app.App"

```
Para correr o 1.4 b):
```
mvn exec:java -Dexec.mainClass="cbd.ex4.app.App"

```
Para correr o 1.5:
```
mvn exec:java -Dexec.mainClass="app.App"

```

## 1.4
**zscan(key, cursor, parameters)** -- devolve um ScanResult<Tuple>, é acedido através de um getResult(), de tipo Tuple.
Iteramos o Tuple e, neste caso, para obter o nome, usamos um getElement().
```
ScanResult<Tuple> ret = jedis.zscan("Names", cursor, sp);
        for(Tuple nextElem : ret.getResult()){
            keys.add(nextElem.getElement());
        }
```
Para a alínea b), como queremos o nome e o score, passa-se o elemento Tuple para String, e usamos um split para retirar esses dados para inseri-los no mapa.
```
ScanResult<Tuple> ret = jedis.zscan("Names", cursor, sp);
        for(Tuple nextElem : ret.getResult()){
            list = new ArrayList<>();

            String[] elems = nextElem.toString().replace("[","").replace("]","").split(",");
            double score = Double.parseDouble(elems[1]);

            if(map.containsKey(score)){
                list = map.get(score);
                list.add(elems[0]);
                map.put(score, list);    
            }else{
                list.add(elems[0]);
                map.put(score, list);
            }
        }
```
Nesta alínea, usei um TreeMap onde fiz um Comparator para me dar o elem com o score mais alto primeiro.
```
public int compare(Double one, Double two) {
        if(one < two){
            return 1;
        } else {
            return -1;
        }
    }
```

## 1.5
Cada user tem 1 hash e 1 list. 
- O Hash, onde a key do tipo "<user>:info", tem informações sobre o user, o seu username no field **username** e as subscriptions no field **subs**. 
- A List, com a key do tipo "<user>:messages", guarda as mensagens que o user escreve, sendo que a 1ª apresentada foi a última enviada (**lpush**).


### Ajuda para todos os exercícios:
https://javadoc.io/doc/redis.clients/jedis/2.9.0/redis/clients/jedis/Commands.html
**Ver exemplos aqui:** https://www.codota.com/code/java/classes/redis.clients.jedis.Jedis
https://www.baeldung.com/jedis-java-redis-client-library
 	
 	
