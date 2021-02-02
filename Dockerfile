FROM maven:3-jdk-8-slim

RUN apt-get update && \
    apt-get install -y \
    git locales

RUN locale-gen pt_BR.UTF-8

ENV TZ America/Sao_Paulo
ENV LANG pt_BR.UTF-8
ENV LANGUAGE pt_BR.UTF-8
RUN ln -sf /usr/share/zoneinfo/America/Sao_Paulo /etc/localtime
RUN echo "America/Sao_Paulo" >  /etc/timezone

VOLUME /projeto-rest
COPY target/projeto-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]