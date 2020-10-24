FROM tomcat:9.0.30-jdk11-openjdk

LABEL maintainer="yisasthemanuel@gmail.com"

ENV CATALINA_HOME /usr/local/tomcat

ADD ./target/*.war $CATALINA_HOME/webapps/

EXPOSE 8080

CMD ["catalina.sh","run"]