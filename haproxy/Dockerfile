FROM haproxytech/haproxy-alpine:2.4
RUN adduser -D -s /bin/false haproxyuser
COPY ./haproxy.cfg /usr/local/etc/haproxy/haproxy.cfg
RUN chown -R haproxyuser:haproxyuser /usr/local/ && \
    chmod -R 755 usr/local/
USER haproxyuser
