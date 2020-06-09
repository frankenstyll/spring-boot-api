FROM kcsdslcim01.ktb:5000/dsl/dslbase-report:1.0
ENV JAVA_OPTS="-Xmx2048m"
COPY target/jasperreport-service-0.0.1-SNAPSHOT.jar /jasperreport-service-0.0.1-SNAPSHOT.jar
COPY src/main/resources /src/main/resources
ENTRYPOINT exec java $JAVA_OPTS -jar /jasperreport-service-0.0.1-SNAPSHOT.jar