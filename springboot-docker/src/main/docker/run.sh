
# mvn clean package -DskipTests docker:build

docker -itd -p 80:8080 -v /home/data/logs:/data/logs test-docker:0.1
