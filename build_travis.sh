
mvn clean package -DskipTests
cp STRACAlign/target/STRAC-align-0.1.jar dist/STRAC.jar
cp -R STRACAlign/target/dependency-jars dist/dependency-jars
ls dist