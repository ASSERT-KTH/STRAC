rm -rf /home/travis/build/Jacarte/STRAC/STRACAlign/target
rm -rf /home/travis/build/Jacarte/STRAC/STRACcore/target
rm -rf /home/travis/build/Jacarte/STRAC/target
rm -rf /home/travis/build/Jacarte/STRAC/dist/
mkdir dist

mvn clean package -DskipTests
cp /home/travis/build/Jacarte/STRAC/STRACAlign/target/STRAC-align-0.1.jar dist/STRAC.jar
cp -R /home/travis/build/Jacarte/STRAC/STRACAlign/target/dependency-jars dist/dependency-jars
ls