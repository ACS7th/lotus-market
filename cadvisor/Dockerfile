FROM gcr.io/cadvisor/cadvisor:latest
RUN adduser -D -s /bin/false cadvuser && \
mkdir -p /rootfs
RUN chown -R cadvuser:cadvuser /rootfs  && \
chmod -R 755 /rootfs
USER cadvuser

