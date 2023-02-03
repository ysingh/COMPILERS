apt-get update
apt-get install --assume-yes \
    build-essential \
    gdb \
    ant \
    cmake \
    git \
    maven \
    openjdk-17-jdk \
    pkg-config \
    unzip \
    uuid-dev \
    openssh-server \
    sudo
#
# Retrieve antlr
ANTLR_BUILD_DIR=$(mktemp -d /tmp/antlr.XXX)
cd $ANTLR_BUILD_DIR
wget http://www.antlr.org/download/antlr-4.11.1-complete.jar
wget http://www.antlr.org/download/antlr4-cpp-runtime-4.11.1-source.zip
wget http://www.antlr.org/download/antlr-runtime-4.11.1.jar
#
# Build c++ Antlr lib
mkdir tmp
cd tmp
unzip ../antlr4-cpp-runtime-4.11.1-source.zip
mkdir build && cd build
cmake ..
make -j 2
DESTDIR=/ make install
cd ../..
rm -rf tmp
#
# Install Java Antlr lib and set Classpath
mv antlr-4.11.1-complete.jar /usr/local/lib/
mv antlr-runtime-4.11.1.jar /usr/local/lib/
echo 'export CLASSPATH=".:/usr/local/lib/antlr-4.11.1-complete.jar:$CLASSPATH"' >> /etc/profile.d/setClassPath.sh
#
# Create antlr link
echo '#!/bin/sh -ue' > /usr/bin/antlr
echo 'java -jar /usr/local/lib/antlr-4.11.1-complete.jar $@' >> /usr/bin/antlr
chmod 755 /usr/bin/antlr
#
# Build spim with keepstats
apt-get install --assume-yes bison flex
git clone https://github.com/rudyjantz/spim-keepstats
cd spim-keepstats
patch -p1 < /tmp/spimfixes.patch
cd spim
make && make install
apt-get remove --purge --assume-yes bison flex
#
#Clean up
cd /tmp
rm -rf $ANTLR_BUILD_DIR
ldconfig
#
# create user
useradd -rm -d /home/user -s /bin/bash -G sudo user
echo 'user:password' | chpasswd
#
# start ssh
service ssh start
