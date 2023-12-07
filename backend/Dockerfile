FROM openjdk:21-jdk

WORKDIR /app

COPY . ./
RUN curl https://dlcdn.apache.org/maven/maven-3/3.9.6/binaries/apache-maven-3.9.6-bin.tar.gz -o apache-maven-3.9.6-bin.tar.gz && tar -xvf apache-maven-3.9.6-bin.tar.gz && mv apache-maven-3.9.6 /opt/

EXPOSE 8005

ENV JAVA_HOME='/usr/java/openjdk-21'
# ENV PATH="$JAVA_HOME/bin:$PATH"

ENV M2_HOME='/opt/apache-maven-3.9.6'
ENV PATH="$M2_HOME/bin:$PATH"

ENV PORT=8005  
ENV MONGODB_URI=mongodb+srv://JobHunting:jobhunting@cluster0.fwskbmd.mongodb.net/JobHuntingTest
ENV EMAIL_HOST=smtp.gmail.com
ENV EMAIL_PORT=587
ENV EMAIL_USERNAME=JobHuntingWebsite@gmail.com
ENV EMAIL_PASSWORD=dhkcjdrqywyjhzdc