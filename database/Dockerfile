FROM mongo:4.0
RUN useradd -ms /bin/bash user && \
    mkdir -p /data/db && \
    chown -R user:user /data/db
WORKDIR /data/db
USER user
LABEL "acs_7th_lst-team"="Taehoon"
EXPOSE 27017
CMD ["mongod", "--dbpath", "/data/db"]
