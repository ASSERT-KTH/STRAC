
mvn clean package -DskipTests
cp /home/travis/build/Jacarte/STRAC/STRACAlign/target/STRAC-align-0.1.jar /home/travis/build/Jacarte/STRAC/dist/STRAC.jar
cp -R /home/travis/build/Jacarte/STRAC/STRACAlign/target/dependency-jars /home/travis/build/Jacarte/STRAC/dist/dependency-jars

ls /home/travis/build/Jacarte/STRAC/dist