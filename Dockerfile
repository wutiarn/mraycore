FROM openjdk:8

WORKDIR /code
ADD . /code

RUN ./gradlew build \
    && rm -r /root/.gradle \
    && mv build/dist/mray-core.jar .

CMD java -jar mray-core.jar