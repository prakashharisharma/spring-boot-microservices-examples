Start Zipkin server

#InMemory
java -jar zipkin-server-2.10.4-exec.jar

#Elastic Search
java -DSTORAGE_TYPE=elasticsearch -DES_HOSTS=http://127.0.0.1:9200 -jar zipkin-server-2.10.4-exec.jar

Verify Zipkin indices in elastic
http://localhost:9200/_cat/indices?v&pretty

Zipkin Default URL : http://localhost:9411/zipkin/

Kibana URL :  http://localhost:5601

IMportant Link
https://dzone.com/articles/distributed-tracing-with-zipkin-and-elk