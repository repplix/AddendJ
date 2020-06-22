# AddendJ - Crosscutting Concerns for Domain Driven Design 

![Java CI](https://github.com/repplix/AddendJ/workflows/Java%20CI/badge.svg)


## Supported Java environments
*   Java 8 (or higher)

## Add dependency

### Maven

```xml      
<dependencies>
    <dependency>
      <groupId>io.jexxa.addendj</groupId>
      <artifactId>AddendJ</artifactId>
      <version>1.0.0-SNAPSHOT</version>
    </dependency>
    
    <dependency>
        <groupId>org.aspectj</groupId>
        <artifactId>aspectjtools</artifactId>
        <version>1.9.5</version>
    </dependency>
    
    <dependency>
        <groupId>org.aspectj</groupId>
        <artifactId>aspectjrt</artifactId>
        <version>1.9.5</version>
    </dependency>
</dependencies>
 
```

### Gradle

```groovy
compile "io.jexxa.addendj:AddendJ:1.0.0-SNAPSHOT"
compile "org.aspectj:aspectjtools:1.9.5"
compile "org.aspectj.aspectjrt:1.9.5"
```          

### Weave dependencies

In case you use Java 11 (or higher) together with maven we recommend AspectJ-Maven plugin [here](https://github.com/nickwongdev/aspectj-maven-plugin).  

An example to configure this maven-plugin looks as follows.    
```xml
<plugin>
    <groupId>com.nickwongdev</groupId>
    <artifactId>aspectj-maven-plugin</artifactId>
    <executions>
        <execution>
            <id>compile</id>
            <goals>
                <goal>compile</goal>
            </goals>
            <configuration>
                <weaveDependencies>
                    <weaveDependency>
                        <groupId>io.jexxa.addendj</groupId>
                        <artifactId>AddendJ</artifactId>
                    </weaveDependency>
                </weaveDependencies>
                <complianceLevel>1.11</complianceLevel>
                <source>1.11</source>
                <target>1.11</target>
                <XaddSerialVersionUID>true</XaddSerialVersionUID>
                <encoding>UTF-8</encoding>
            </configuration>
        </execution>
    </executions>
    <dependencies>
        <dependency>
            <groupId>org.aspectj</groupId>
            <artifactId>aspectjtools</artifactId>
            <version>1.9.5</version>
        </dependency>
    </dependencies>
</plugin>
 ```
