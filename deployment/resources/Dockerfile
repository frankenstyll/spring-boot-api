FROM adoptopenjdk:11-openj9
COPY jenkins-subProjectPath/target/jenkins-jar /jenkins-jar
ENV TZ=Asia/Bangkok
ENV DEBIAN_FRONTEND=noninteractive
RUN apt update && apt install -y tzdata
COPY jenkins-subProjectPath/src/main/resources /src/main/resources
ENTRYPOINT exec java -jar /jenkins-jar --spring.config.location=/home/application.properties