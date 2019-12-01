# [projects](http://idugalic.github.io/projects)/axon-vanilla-java-demo

To make the configuration of the infrastructure components easier and to define their relationship with each of the functional components, Axon provides a Java Configuration API. 

This [Axon][axon] demo project demonstrates use of Axon (vanilla Java) Configuration API with Axon Server.

## Development

This project is driven using [maven].

### Run Axon Server

Axon Server is used for message routing (JPA / H2 DB is used as event store/bus).
You can [download](https://download.axoniq.io/axonserver/AxonServer.zip) a ZIP file with AxonServer as a standalone JAR. This will also give you the AxonServer CLI and information on how to run and configure the server.

Alternatively, you can run the following command to start AxonServer in a Docker container:

```
$ docker run -d --name axonserver -p 8024:8024 -p 8124:8124 axoniq/axonserver
```

> NOTE: To remove Axon Server completely and fallback to `simple` message buses (non-axon-server components): remove `axon-server-connector` maven dependency in [pom file](pom.xml).


### Run locally

You can run the following command to start your project locally:

```
$ ./mvnw clean verify exec:java -Dexec.mainClass="com.demo.AxonVanillaJavaDemoApplication" 
```

---

[maven]: https://maven.apache.org/ (Maven)
[axon]: https://axoniq.io/ (Axon)
