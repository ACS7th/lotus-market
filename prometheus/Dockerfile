FROM bitnami/prometheus:2.55.0
USER root
RUN useradd -ms /bin/false prometheususer

# prometheus.yml 파일 복사 및 권한 설정
COPY ./prometheus.yml /opt/bitnami/prometheus/conf/prometheus.yml

# 권한 설정을 하나의 RUN 명령으로 통합
RUN chown -R prometheususer:prometheususer /opt/bitnami/prometheus && \
    chmod -R 755 /opt/bitnami/prometheus

USER prometheususer
