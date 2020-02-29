mvn clean package -DskipTests

cp log4j.properties /STRACAlign/target/
zip -rv $1/STRAC.zip STRACAlign/target
