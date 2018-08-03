copy appfabs-log-configuration.conf to logstash config folder

START logstash

TO test logstash 

# logstash -e 'input { stdin { } } output { stdout {} }'

#Verify conf file
logstash -f appfabs-log-configuration.conf --config.test_and_exit

# RUN
logstash -f appfabs-log-configuration.conf --config.reload.automatic

#Verify in ES
http://localhost:9200/_cat/indices?v&pretty