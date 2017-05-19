![](https://img.shields.io/travis/luismanuelamengual/NeoGroup-Sparks.svg) 
![](https://img.shields.io/github/license/luismanuelamengual/NeoGroup-Sparks.svg)
![](https://img.shields.io/maven-central/v/com.github.luismanuelamengual/NeoGroup-Sparks.svg)
![](https://img.shields.io/github/forks/luismanuelamengual/NeoGroup-Sparks.svg?style=social&label=Fork)
![](https://img.shields.io/github/stars/luismanuelamengual/NeoGroup-Sparks.svg?style=social&label=Star)
![](https://img.shields.io/github/watchers/luismanuelamengual/NeoGroup-Sparks.svg?style=social&label=Watch)
![](https://img.shields.io/github/followers/luismanuelamengual.svg?style=social&label=Follow)

# NeoGroup-Sparks

NeoGroup-Sparks is an open source, easy to use framework for java. Its MVC oriented and supports application modules to expand the framework capabilities

Getting started
---------------

For maven users, just add the following dependency

```xml
<dependency>
    <groupId>com.github.luismanuelamengual</groupId>
    <artifactId>NeoGroup-Sparks</artifactId>
    <version>1.0</version>
</dependency>
```

Example 1 - Basic Web Usage
---------

In this example we are going te create a web application with the famous "Hello World" message ..

For web application we should add the *NeoGroup-Sparks-WebModule* dependency ..

```xml
<dependency>
    <groupId>com.github.luismanuelamengual</groupId>
    <artifactId>NeoGroup-Sparks-WebModule</artifactId>
    <version>1.0</version>
</dependency>
```
For more information check https://github.com/luismanuelamengual/NeoGroup-Sparks-WebModule

1) First we must create a sparks application with a web module listening at port 80 and register a new processor named HelloWorldProcessor.class

```java
package example;

import example.processors.*;
import org.neogroup.sparks.Application;
import org.neogroup.sparks.web.WebModule;

public class Main {
    public static void main(String[] args) {
        Application application = new Application();
        application.addModule(new WebModule(application, 80));
        application.registerProcessor(HelloWorldProcessor.class);
        application.start();
    }
}
```
2) Creating the *HelloWorldProcessor* ..

```java
package example.processors;

import org.neogroup.httpserver.HttpRequest;
import org.neogroup.httpserver.HttpResponse;
import org.neogroup.sparks.web.processors.WebProcessor;
import org.neogroup.sparks.web.routing.Route;
import org.neogroup.sparks.web.routing.RouteAction;

@Route(path="/helloworld/")
public class HelloWorldProcessor extends WebProcessor {

    @RouteAction
    public HttpResponse indexAction (HttpRequest request) {
        return createResponse("Hello world !!");
    }
    
    @RouteAction(name="sayHello")
    public HttpResponse sayHelloAction (HttpRequest request) {
        return createResponse("Hello " + request.getParameter("name") + " !!");
    }
}
```
The route annotation tells the framework how to access the processor. In this case, to access the processor to the default action you should enter the url http://localhost/helloworld/. The RouteAction indicates with method should be called inside the processor. If the user enters the url http://localhost/helloworld/sayHello then the method with the RouteAction with name "sayHello" will be executed

Example 2 - Working with View Factories
---------

In this example we are going to use Velocity to render a view in the sparks framework

Actually there are 2 supported View Factory implementations: VelocityViewFactory and FreemarkerViewFactory. They can be added using the following dependencies

```xml
<dependency>
    <groupId>com.github.luismanuelamengual</groupId>
    <artifactId>NeoGroup-Sparks-VelocityViewFactory</artifactId>
    <version>1.0</version>
</dependency>
<dependency>
    <groupId>com.github.luismanuelamengual</groupId>
    <artifactId>NeoGroup-Sparks-FreemarkerViewFactory</artifactId>
    <version>1.0</version>
</dependency>

```
1) In the application we should register a view factory with a name ...

```java
package example;

import example.processors.*;
import org.neogroup.sparks.Application;
import org.neogroup.sparks.web.WebModule;
import org.neogroup.sparks.views.velocity.VelocityViewFactory;

public class Main {
    public static void main(String[] args) {
        Application application = new Application();
        application.addModule(new WebModule(application, 80));
        application.addViewFactory("velocity", new VelocityViewFactory());
        application.registerProcessor(ViewFactoriesProcessor.class);
        application.start();
    }
}
```
2) Create a processor that uses the view factory

```java
package example.processors;

import org.neogroup.httpserver.HttpRequest;
import org.neogroup.httpserver.HttpResponse;
import org.neogroup.sparks.web.processors.WebProcessor;
import org.neogroup.sparks.web.routing.Route;
import org.neogroup.sparks.web.routing.RouteAction;

@Route(path="/viewfactories/")
public class ViewFactoriesProcessor extends WebProcessor {
    @RouteAction ()
    public HttpResponse templateAction (HttpRequest request) {
        ViewHttpResponse response  = createViewResponse("example.tutorial");
        response.setParameter("name", request.getParameter("name"));
        return response;
    }
}
```
Method createViewResponse will try to create a view with the given name. In this case we added a Velocity View Factory to the sparks application so the framework will try to read the file example/tutorial.vm in the classpath by default. 

If more than 1 view factory is added to the application then the framework will read the property "defaultViewFactory" to choose a view factory or the user can call the method createViewResponse specifying which view factory should be used.

Example 3 - Working with Entities (local storage)
---------

In this example we are going to create an entity named "User" and 2 processors: A CRUDProcessor that will manage the "User" persistence and a WebProcessor that will make crud operations with this entity

1) Create an application registering the 2 processors

```java
package example;

import example.processors.*;
import org.neogroup.sparks.Application;
import org.neogroup.sparks.web.WebModule;

public class Main {
    public static void main(String[] args) {
        Application application = new Application();
        application.addModule(new WebModule(application, 80));
        application.registerProcessors(
            UserCRUDProcessor.class
            TestProcessor.class
        );
        application.start();
    }
}
```
2) Create the "User" entity

```java
package example.models;

import org.neogroup.sparks.model.Entity;

public class User extends Entity<Integer> {

    private Integer id;
    private String name;
    private String lastName;

    public Integer getId() { 
        return id; 
    }
    
    public void setId(Integer id) { 
        this.id = id; 
    }
    
    public String getName() { 
        return name; 
    }
    
    public void setName(String name) { 
        this.name = name; 
    }
    
    public String getLastName() { 
        return lastName; 
    }
    
    public void setLastName(String lastName) { 
        this.lastName = lastName; 
    } 
}
```
3) Create a CRUDProcessor that will manage the "User" entity persistance

```java
package example.processors;

import example.models.User;
import org.neogroup.sparks.model.EntityQuery;
import org.neogroup.sparks.processors.crud.CRUDProcessor;
import java.util.*;

public class UserCRUDProcessor extends CRUDProcessor<User> {

    private Map<Integer, User> users;
    private int nextId;

    public UserCRUDProcessor() {
        this.users = new HashMap<>();
        nextId = 1;
    }

    @Override
    protected User create(User entity, Map<String, Object> params) {
        entity.setId(nextId++);
        users.put(entity.getId(), entity);
        return entity;
    }

    @Override
    protected User update(User entity, Map<String, Object> params) {
        users.put(entity.getId(), entity);
        return entity;
    }

    @Override
    protected User delete(User entity, Map<String, Object> params) {
        users.remove(entity);
        return entity;
    }

    @Override
    protected Collection<User> retrieve(EntityQuery query, Map<String, Object> params) {
        List<User> usersList = new ArrayList<User>(users.values());
        return usersList;
    }
}
```
4) Create a web processor that makes crud operations over the entity "User"
```java
package example.processors;

import example.models.User;
import org.neogroup.httpserver.HttpHeader;
import org.neogroup.httpserver.HttpRequest;
import org.neogroup.httpserver.HttpResponse;
import org.neogroup.sparks.web.processors.WebProcessor;
import org.neogroup.sparks.web.routing.Route;
import org.neogroup.sparks.web.routing.RouteAction;
import org.neogroup.util.MimeUtils;
import java.util.List;

@Route(path="/test/")
public class TestProcessor extends WebProcessor {

    @RouteAction(name="createUser")
    public HttpResponse createUserAction(HttpRequest request) {

        User user = new User();
        user.setName(request.getParameter("name"));
        user.setLastName(request.getParameter("lastName"));
        createEntity(user);
        return showUsersAction(request);
    }

    @RouteAction(name="showUsers")
    public HttpResponse showUsersAction(HttpRequest request) {

        StringBuilder str = new StringBuilder();
        List<User> users = retrieveEntities(User.class);
        for (User user : users) {
            str.append("Name: ").append(user.getName());
            str.append("|");
            str.append("LastName: ").append(user.getLastName());
            str.append("<br>");
        }

        HttpResponse response = new HttpResponse();
        response.addHeader(HttpHeader.CONTENT_TYPE, MimeUtils.TEXT_HTML);
        response.setBody(str.toString());
        return response;
    }
}
```
Processors can have access to entities through the methods "createEntity", "updateEntity", "deleteEntity" and "retrieveEntities". 
If the user wants to retrieve entities with filters or sorted by a certain property, the class EntityQuery should be used. This is an example of how the EntityQuery is used:

```java
EntityQuery query = new EntityQuery();
query.addSorter("id");
query.addFilter("age", EntityPropertyOperator.LESS_THAN, 50);
List<User> users = retrieveEntities(User.class, query);
```
Example 4 - Working with Entities (postgresql datasource storage)
---------
This example will be the same as example 3 but we are going to use a postgresql datasource as a persitance method for entity "User"

1. In the application configuration we add a postgresql data source
```java
package example;

import example.processors.*;
import org.neogroup.sparks.Application;
import org.neogroup.sparks.web.WebModule;
import org.postgresql.ds.PGPoolingDataSource;

public class Main {
    public static void main(String[] args) {
        
        //Create a postgresql data source
        PGPoolingDataSource postgreDataSource = new PGPoolingDataSource();
        postgreDataSource.setServerName("localhost");
        postgreDataSource.setDatabaseName("testdb");
        postgreDataSource.setUser("postgres");
        postgreDataSource.setPassword("postgres");
        
        //Create a sparks application
        Application application = new Application();
        application.addModule(new WebModule(application, 80));
        application.addDataSource("main", postgreDataSource);
        application.registerProcessors(
            UserCRUDProcessor.class
            TestProcessor.class
        );
        application.start();
    }
}
```
2. Create entity "User" with annotations to link the entity to the data source
```java
package example.models;

import org.neogroup.sparks.model.Entity;
import org.neogroup.sparks.model.annotations.Column;
import org.neogroup.sparks.model.annotations.GeneratedValue;
import org.neogroup.sparks.model.annotations.Id;
import org.neogroup.sparks.model.annotations.Table;

@Table(name = "user")
public class User extends Entity<Integer> {

    @Id
    @GeneratedValue
    @Column(name = "userid")
    private Integer id;
    
    @Column(name = "name")
    private String name;
    
    @Column(name = "lastname")
    private String lastName;

    public Integer getId() { 
        return id; 
    }
    
    public void setId(Integer id) { 
        this.id = id; 
    }
    
    public String getName() { 
        return name; 
    }
    
    public void setName(String name) { 
        this.name = name; 
    }
    
    public String getLastName() { 
        return lastName; 
    }
    
    public void setLastName(String lastName) { 
        this.lastName = lastName; 
    } 
}
```
Here we are using the following annotations:

- Table: Indicates the name of a table in the datasource
- Column: Indicates the property matches a column in a datasource table
- Id: Indicates the property is an Id
- GeneratedValue: Indicates the property is genereated by the datasource

3. Create a DataSourceCRUDProcessor for the entity User
```java
package example.processors;

import example.models.User;
import org.neogroup.sparks.processors.crud.DataSourceCRUDProcessor;

public class UserCRUDProcessor extends DataSourceCRUDProcessor<User> {
    
    //The user is encouraged to override any of the crud operations by default
}
```
Example 5 - Working with Modules
---------
Modules in sparks lets you work in an isolated context that is not shared by other modules in the application. In this example we are going to create an application with 2 web modules and assign processors to each of the modules to demonstrate how processors added in 1 module are not visible in the other one

1. Create an application with 2 modules
```java
package example;

import example.processors.*;
import org.neogroup.sparks.Application;
import org.neogroup.sparks.web.WebModule;

public class Main {

    public static void main(String[] args) {

        Application application = new Application();
        
        //Add a web module listening at port 1408
        WebModule module1 = new WebModule(application, 1408);
        module1.registerProcessor(AAAProcessor.class);
        application.addModule(module1);

        //Add a web module listening at port 1409
        WebModule module2 = new WebModule(application, 1409);
        module2.registerProcessor(BBBProcessor.class);
        application.addModule(module2);

        application.registerProcessor(CCCProcessor.class);
        application.start();
    }
}
```
In this example we have 3 processors ...

- the "AAAProcessor" was registered to the module 1
- the "BBBProcessor" was registered to the module 2
- the "CCCProcessor" was registered to the application

so CCCProcessor will be accesible to all modules but AAAProcessor will only be accesible in module 1 and BBBProcessor will only be accesible in module 2. The same can be applied to bundles, properties, view factories, data sources, processors, etc.
