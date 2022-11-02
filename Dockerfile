FROM openjdk:17
ADD target/productlist-0.0.1-SNAPSHOT.jar productlist.jar
#COPY init.sql /docker-entrypoint-initdb.d/
#ENV POSTGRES_DB deposits
#EXPOSE 8080
ENTRYPOINT ["java","-jar","productlist.jar"]