# jdk:panama
FROM ubuntu:18.04

RUN apt-get update \
    && apt-get -y install make libasound2-dev libfontconfig1-dev libcups2-dev libx11-dev libxext-dev libxrender-dev libxrandr-dev libxtst-dev libxt-dev build-essential openjdk-11-jdk zip autoconf mercurial


RUN mkdir panama

WORKDIR panama

RUN hg clone http://hg.openjdk.java.net/panama/dev/
WORKDIR dev
