#!/usr/bin/bash
set -e

echo "Testing if javac in JAVA_HOME works..."
"$JAVA_HOME/bin/javac" -version
echo "...success"

mkdir -p build
pushd build

echo "Testing javac if it can target 1.3..."
cat > Test.java << EOF
public class Test {
	public static int add(int a, int b) {
		return a + b;
	}
}
EOF
"$JAVA_HOME/bin/javac" -source 1.3 -target 1.3 Test.java
echo "...success"

if [[ ! -f main.tar.gz ]]
then
	echo "Downloading SDK libraries from GitHub..."
	wget https://github.com/gtrxAC/j2me-template/archive/refs/heads/main.tar.gz
fi
tar zxf main.tar.gz j2me-template-main/sdk/lib

rm -rf lib
mv j2me-template-main/sdk/lib/ lib
rm -r j2me-template-main

mkdir -p class
rm -rf class/*

BOOTCLASSPATH="./lib/cldc11.jar:./lib/midp20.jar"
if [[ `uname -o` == "Msys" ]]
then
	BOOTCLASSPATH="./lib/cldc11.jar;./lib/midp20.jar"
fi

echo "Compiling source"
"$JAVA_HOME/bin/javac" `find ../src -name '*'.java` -d class -source 1.3 -target 1.3 -bootclasspath $BOOTCLASSPATH
echo "Packing to JAR"
"$JAVA_HOME/bin/jar" cvfm loveme-unobfuscated.jar ../META-INF/MANIFEST.MF -C class . -C ../res .

echo "Build finished"
popd # build
