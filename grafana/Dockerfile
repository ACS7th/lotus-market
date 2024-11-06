FROM grafana/grafana:main-ubuntu
USER root
RUN useradd -ms  /bin/bash grafanauser
COPY ./grafana.ini /etc/grafana/grafana.ini
COPY ./prometheus-datasource.yml /etc/grafana/provisioning/datasources/prometheus-datasource.yml
RUN chown -R grafanauser:grafanauser /etc/grafana && chmod -R 755 /etc/grafana
USER grafanauser
