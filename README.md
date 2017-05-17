# NeoGroup-Sparks

NeoGroup-Sparks is an open source, easy to use framework for java. Its MVC oriented and supports applications modules to expand the framework capabilities

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

public class Main {
    public static void main(String[] args) {
        Application application = new Application();
        application.addModule(new WebModule(application, 80);
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

import java.util.Locale;

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
The route annotation tells the framework how to access the processor. In this case to access the processor to the default action you should enter the url http://localhost/helloworld/. The RouteAction indicates with method should be called inside the controller. If the user enters the url http://localhost/helloworld/sayHello then the method with the RouteAction with name "sayHello" will be executed

Example 2 - Working with View Factories
---------

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

public class Main {
    public static void main(String[] args) {
        Application application = new Application();
        application.addModule(new WebModule(application, 80);
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

If more than 1 view factory is added to the application then the framework will read the property "defaultViewFactory" to choose a view factory or the user can call the method createViewResponse specifying which view factory to use.
