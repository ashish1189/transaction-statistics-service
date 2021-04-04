FROM adoptopenjdk/openjdk11:latest

#Image metadata
LABEL author = "Ashish Deshpande"
LABEL email = "ashishdeshpande123@gmail.com"
LABEL description = "Transaction statistics service is ReST API to to calculate realtime \
                     statistics for the last 60 seconds of transactions. "

#Landing directory for app.jar
RUN mkdir /home/app

# Environment port
EXPOSE 9093

#Add app.jar from local to destination in container
ADD target/coding-challenge-1.0.3.jar /home/app/coding-challenge-1.0.3.jar

#Create softlink to make ap.jar executable from anywhere in container
RUN ln -s /home/app/coding-challenge-1.0.3.jar /bin/coding-challenge-1.0.3.jar

#Set working directory as bin
WORKDIR /bin

ENTRYPOINT ["java", "-jar", "coding-challenge-1.0.3.jar"]