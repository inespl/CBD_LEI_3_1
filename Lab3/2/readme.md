**Inês Leite - 92928**
*CBD, LEI 3º ano*   
---

# Cassandra

## Ex 2

**CQL data types:** https://docs.datastax.com/en/cql-oss/3.x/cql/cql_reference/cql_data_types_c.html

### Tabelas

**Utilizadores**
**Vídeos**
**Comentários**
**Followers**
**Eventos**
**Ratings**

```
CREATE KEYSPACE VideoSharing WITH replication = {'class': 'SimpleStrategy', 'replication_factor':3};
```

1. **Gestão de utilizadores: entre outros, registar o username, nome, email, selo temporal de registo na plataforma;**
```
CREATE TABLE users (username text, name text, email text, red_timestamp timestamp, PRIMARY KEY (username));
```
2. **Gestão de vídeos: entre outros, registar o autor da partilha, nome do vídeo, descrição, uma ou mais etiquetas (tag) e selo temporal de upload/partilha;**
```
CREATE TABLE videos (id int, author text, videoname text, description text, tag list<text>, up_timestamp timestamp, PRIMARY KEY (id, up_timestamp)) WITH CLUSTERING ORDER BY (up_timestamp DESC);

CREATE TABLE videosPerAuthor (id int, author text, up_timestamp timestamp, PRIMARY KEY (author, id, up_timestamp)) WITH CLUSTERING ORDER BY (id ASC, up_timestamp DESC);

CREATE TABLE tagsPerVideo (id int, tag list<text>, up_timestamp timestamp, PRIMARY KEY (id));

CREATE TABLE videosPerTag (id int, tag text, PRIMARY KEY (tag));
```
- videosPerAuthor: para permitir a pesquisa de todos os vídeos de determinado autor
- tagsPerVideo: para ter uma lista das tags de determinado vídeo
- videosPerTag: para ter todos os vídeos com a tag Aveiro

3. **Gestão de comentários: realizados para um vídeo em determinado momento temporal e tem como autor um utilizador;**
```
CREATE TABLE commentsPerUser (comment text, author text, videoID text, comment_date date, PRIMARY KEY (author, comment_date) WITH CLUSTERING ORDER BY (comment_date DESC);

CREATE TABLE commentsPerVideo (comment text, author text, videoID int, comment_date date, PRIMARY KEY (videoID, comment_date), comment_date) WITH CLUSTERING ORDER BY (comment_date DESC);
```
- commentsPerUser: para permitir a pesquisa de comentários por utilizador, ordenado inversamente pela data
- commentsPerVideo: para permitir a pesquisa de comentários por vídeos, ordenado inversamente pela data


4. **Gestão de vídeo followers: permitir o registo de utilizadores que seguem determinado vídeo. Num sistema de informação, permitirá por exemplo notificar todos os followers de um novo comentário inserido para o vídeo;**
```
CREATE TABLE video_followers (user text, videoID text, PRIMARY KEY (videoID));
```

5. **Registo de eventos: por vídeo e utilizador e podem ser do tipo play/pause/stop, incluindo o registo temporal do evento e o momento (temporal) em que ocorre no vídeo. Por exemplo, o utilizador XPTO fez play no vídeo YGZ às 2:35:54 do dia 2 de outubro de 2017, ao 300 segundo de tempo do vídeo;**
```
CREATE TABLE events (user text, videoID int, event_type text, event_timestamp timestamp,  event_duration int, PRIMARY KEY ((user, videoID), event_timestamp)) WITH CLUSTERING ORDER BY (event_timestamp DESC );
```

6. **Rating de vídeos: valor inteiro de 1-5, por vídeo e não necessita de registo do autor.**
```
CREATE TABLE ratingPerVideo (id int, videoID int, rating int, PRIMARY KEY (videoID, rating)) WITH CLUSTERING ORDER BY (rating DESC);
```
- Permitir a pesquisa do rating médio de um vídeo e quantas vezes foi votado;


7. **Permitir a pesquisa de todos os vídeos de determinado autor;**
```
SELECT * FROM videosperauthor WHERE author = 'joana';
```

8. **Permitir a pesquisa de comentários por utilizador, ordenado inversamente pela data;**
```
SELECT * FROM commentsperuser WHERE author='joana' ;
```

9. **Permitir a pesquisa de comentários por vídeos, ordenado inversamente pela data;**
```
SELECT * FROM commentspervideo WHERE videoID=3;
```

10. **Permitir a pesquisa do rating médio de um vídeo e quantas vezes foi votado;**
```
SELECT avg(rating) as AVG, count(rating) as NReviews FROM ratingpervideo WHERE videoID = 3;
```

1. **Os últimos 3 comentários introduzidos para um vídeo;**
```
SELECT * FROM commentspervideo LIMIT 3;
```

2. **Lista das tags de determinado vídeo;**
```
SELECT * FROM tagspervideo WHERE id=3;
```

3. **Todos os vídeos com a tag Aveiro;**
```
SELECT * FROM videospertag WHERE tag='Aveiro';
```

4. **Os últimos 5 eventos de determinado vídeo realizados por um utilizador;**
```
SELECT * FROM videosperauthor WHERE author='joana' LIMIT 5;
```

5. **Vídeos partilhados por determinado utilizador (maria1987, por exemplo) num determinado período de tempo (Agosto de 2017, por exemplo);**
```
SELECT * FROM videosperauthor WHERE author='joana' AND up_timestamp< '2020-11-04 14:30:00.000';
```

6. **Os últimos 10 vídeos, ordenado inversamente pela data da partilha;**
```
SELECT * FROM videos LIMIT 10;
```

7. **Todos os seguidores (followers) de determinado vídeo;**
```
SELECT * FROM video_followers WHERE videoID=3;
```

8. **Todos os comentários (dos vídeos) que determinado utilizador está a seguir (following);**
Não é possível fazer num só select porque utiliza duas tabelas.

9. **Os 5 vídeos com maior rating;**
```
SELECT * FROM ratingpervideo LIMIT 5;
```

10. **Uma query que retorne todos os vídeos e que mostre claramente a forma pela qual estão ordenados;**
```
SELECT * FROM videos;
```

11. **Lista com as Tags existentes e o número de vídeos catalogados com cada uma delas;**
Tentativas:
```
SELECT tag, count(id) as Count FROM videospertag ;
SELECT tag, count(id) as Count FROM videospertag WHERE tag='Fun';
SELECT tag, COUNT(tag) as Count FROM videospertag GROUP BY tag;
```
Não percebo porque é que principalemente o último não funciona.

--

**Nota:** Os meus order cluster não funcionam não percebo porquê, mas acho que estão corretos, por isso deixei. As alíneas que pedem queries ordenadas não estão devido a isso.

----------
https://docs.datastax.com/en/dse/5.1/cql/cql/cql_reference/cqlAggregates.html

SELECT count(cyclist_name) AS Row_Count FROM cycling.team_average
WHERE team_name = 'UnitedHealthCare Pro Cycling Womens Team';

SELECT AVG(cyclist_time_sec) AS Average FROM cycling.team_average
WHERE team_name = 'UnitedHealthCare Pro Cycling Womens Team';

