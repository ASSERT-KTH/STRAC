mvn clean package -DskipTests

cp log4j.properties STRACAlign/target/

rm $1/STRAC.zip
cd STRACAlign/target
zip -rv $1/STRAC.zip .

