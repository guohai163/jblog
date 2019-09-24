FROM maven:3-jdk-8 as MVN_BUILD

WORKDIR /opt/jblog/
ADD . /tmp
RUN cd /tmp && mvn package -DskipTests && cp -f /tmp/target/*.jar /opt/jblog/jblog.jar && cp -f /tmp/src/main/resources/application.yml /opt/jblog/application.yml

FROM mysql:5.7

ENV MYSQL_ROOT_PASSWORD=password

WORKDIR /opt/jblog/
COPY --from=MVN_BUILD /opt/jblog/ /opt/jblog/
ADD init.sql /docker-entrypoint-initdb.d/
RUN ls -l /opt/jblog && mkdir -p /usr/share/man/man1 && apt-get update && apt-get install -y openjdk-8-jre && echo "service mysql start\njava -jar /opt/jblog/jblog.jar --spring.config.location=/opt/jblog/application.yml" > /opt/jblog/start.sh && chmod +x /opt/jblog/start.sh



CMD ["/opt/jblog/start.sh"]
EXPOSE 8002

