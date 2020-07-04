# AddendJ - Crosscutting Concerns for Domain Driven Design 

![Java CI](https://github.com/repplix/AddendJ/workflows/Java%20CI/badge.svg) [![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=io.jexxa.addendj%3AAddendJ&metric=alert_status)](https://sonarcloud.io/dashboard?id=io.jexxa.addendj%3AAddendJ)

## About
AddendJ supports the implementation of a technology agnostic application core in terms of Domain Driven Design by integrating crosscutting concerns into the application core with AspectJ. 

AddendJ provides a valid implementation of following methods for Aggregate/Entity, DomainEvent and ValueObject:
* equals()
* hashCode()
* toString()

In order to use these features you have to annotate your classes with corresponding annotation of project [Addend](https://github.com/repplix/Addend).                 

## Supported Java environments
*   Java 10 (or higher)

## Quickstart

### Start programming 

AddendJ provides valid implementation of methods equals(), hashCode() and toString() as soon as annotation @ValueObject is added. As you can see in the following example, the code focuses on the domain aspects and does not require any technical extensions for correct comparison or output operation. 


```Java
@ValueObject
public class Customer
{
    private final String firstName; 
    private final String lastName; 

    public Customer(String firstName, String lastName)
    {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName()
    {
        return firstName;
    }
    public String getLastName()
    {
        return lastName;
    }
}
```

### Add dependency

#### Maven

```xml      
<dependencies>
    <dependency>
      <groupId>io.jexxa.addendj</groupId>
      <artifactId>AddendJ</artifactId>
      <version>1.0.0</version>
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

#### Gradle

```gradle
compile "io.jexxa.addendj:AddendJ:1.0.0"
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
