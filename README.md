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

If you want to create a web application you should also add the *NeoGroup-Sparks-WebModule* dependency ..

```xml
<dependency>
    <groupId>com.github.luismanuelamengual</groupId>
    <artifactId>NeoGroup-Sparks-WebModule</artifactId>
    <version>1.0</version>
</dependency>
```

You can also add dependencies for view factories. Actually there are 2 supported View Factories: VelocityViewFactory and FreemarkerViewFactory

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

Example 1 - Hello World
---------

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

