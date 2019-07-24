mvn clean package -DskipTests
cp target/STRAC-0.1.jar dist/STRAC.jar
cp -R target/dependency-jars dist/dependency-jars