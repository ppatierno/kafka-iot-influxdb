FROM centos:7

RUN yum -y install java-1.8.0-openjdk-headless openssl && yum -y clean all

# Set JAVA_HOME env var
ENV JAVA_HOME /usr/lib/jvm/java

COPY ./scripts/ /bin

ADD target/device-app.jar /

CMD ["/bin/run.sh", "/device-app.jar"]
