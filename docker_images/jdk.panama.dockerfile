FROM jacarte/jdk:panama



RUN apt-get install software-properties-common curl -y
RUN curl -O https://download.java.net/java/GA/jdk14/076bab302c7b4508975440c56f6cc26a/36/GPL/openjdk-14_linux-x64_bin.tar.gz
RUN tar xvf openjdk-14_linux-x64_bin.tar.gz
RUN mv jdk-14 /opt/
RUN tee /etc/profile.d/jdk14.sh <<EOF
RUN export JAVA_HOME=/opt/jdk-14
RUN export PATH=\$PATH:\$JAVA_HOME/bin

RUN ls /opt/jdk-14

#RUN apt -y install oracle-java14-set-default
RUN hg checkout vectorIntrinsics

RUN bash configure --disable-warnings-as-errors --with-boot-jdk=/opt/jdk-14
RUN make images