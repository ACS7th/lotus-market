FROM docker.elastic.co/elasticsearch/elasticsearch:8.10.2

RUN elasticsearch-plugin install analysis-nori

EXPOSE 9200 9300

ENV discovery.type=single-node \
    ES_JAVA_OPTS="-Xms4g -Xmx4g" \
    xpack.security.enabled=false

COPY elasticsearch.yml /usr/share/elasticsearch/config/elasticsearch.yml

CMD ["eswrapper"]
