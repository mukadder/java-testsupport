
public class greatMethodsjava8 {


	public static <T> Stream<T> enumerationAsStream(Enumeration<T> e) {
	    return StreamSupport.stream(
	        Spliterators.spliteratorUnknownSize(
	            new Iterator<T>() {
	                public T next() {
	                    return e.nextElement();
	                }
	                public boolean hasNext() {
	                    return e.hasMoreElements();
	                }
	            },
	            Spliterator.ORDERED), false);
	}
}
@FunctionalInterface
public interface ThrowingPredicate<T, E extends Exception>{

    boolean test(T t) throws E;
}

@FunctionalInterface
public interface ThrowingSupplier<T, E extends Exception>{

    T get() throws E;
}

public static <T, E extends Exception> Predicate<T> wrap(ThrowingPredicate<T, E> th) {
    return t -> {
        try {
            return th.test(t);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    };
}

public static <T, E extends Exception> Supplier<T> wrap(ThrowingSupplier<T, E> th) {
    return () -> {
        try {
            return th.get();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    };
}
String hostName = "localhost";
try {
    InetAddress addr = InetAddress.getLocalHost();
    String suggestedName = addr.getCanonicalHostName();
    // Rough test for IP address, if IP address assume a local lookup
    // on VPN
    if (!suggestedName.matches("(\\d{1,3}\\.?){4}") && !suggestedName.contains(":")) {
        hostName = suggestedName;
    }
} catch (UnknownHostException ex) {
}

System.out.println(hostName);
String hostName = stream(wrap(NetworkInterface::getNetworkInterfaces).get())
// Only alllow interfaces that are functioning
.filter(wrap(NetworkInterface::isUp))
// Flat map to any bound addresses
.flatMap(n -> stream(n.getInetAddresses()))
// Fiter out any local addresses
.filter(ia -> !ia.isAnyLocalAddress() && !ia.isLinkLocalAddress() && !ia.isLoopbackAddress())
// Map to a name
.map(InetAddress::getCanonicalHostName)
// Ignore if we just got an IP back
.filter(suggestedName -> !suggestedName.matches("(\\d{1,3}\\.?){4}")
                         && !suggestedName.contains(":"))
.findFirst()
// In my case default to localhost
.orElse("localhost");

System.out.println(hostName);

So most of the time you are probably aware of method references that either reference instance of static methods directly, and they are useful but not necessarily that interesting.

Integer example = 1;

// We can bind the instance variables, in this case just the return type is bound but there couple be method parameters also
Supplier<String> s = example::toString;

// We can bind to static method, here the generic parameter are the method parameter and return type
Supplier<Long> f = System::currentTimeMillis;
Function<Integer,Integer> f = example::compareTo;


A less common formulation is to pass in instance methods and find the first generic parameter of of the function to be the type, this allows you to easily pass in a range of actions to operate on a common type:

Function<Integer,String> f = Integer::toString

For example you can create an equals method that works on a subset of properties using functions mapped to instance methods as in the above example:

public static <T> boolean equals(T one, T two, Function<? super T, ?>... accessors) {

    if (one == two) {
        return true;
    } else  if (one==null || two==null) {
        return false;
    }

    return Stream.of(accessors).allMatch(accessor ->
        Objects.equals(accessor.apply(one),accessor.apply(two)));
}

if (equals(one, two, Thing::getName, Thing:getOtherProperty)) ...;



Finally you can also bind the exception thrown from the method to one of the generic parameters. (Here I am using ThrowingException and ThrowingSupplier my home brew interfaces that are like there namesakes but have a generic parameter E for the exception thrown) This allows you to make you "closure" transparent to exceptions. This is more useful in a lot of cases when compared to the Stream throw nothing and "throws Exception" extremes. 

ThrowingException<String,Integer,NumberFormatException> te = Integer::parseInt;


You can write funky closure methods that will throw different exceptions based on the passed in method reference does, no more catch (Exception).

public static <T, E extends Exception> T withCC(Class<?> contextClass, 
   ThrowingSupplier<T,E> action) throws E {

    Thread t = Thread.current();
    ClassLoader cl = t.getContextClassLoader();
    try {
        t.setContextClassLoader(contextClass.getClassLoader());
        return action.get();
    } finally {
        s.setContextClassLoader(cl);
    }
}
       

// Throws IOException, complier knows that this method call throws IOException

String value = withCC(Example.class, () -> {
    ...
    ... new FileOutpuStream(file); ...
    ...
});

// Throws another exception, complier knows that this method call throws RMIExeption

String value = withCC(Example.class, () -> {
        ...
        throw new RMIException();
    });
@FunctionalInterface
public interface ThrowingRunnable<E extends Throwable>  {
    
    public void run() throws E;
}



Optional<Credential> credential = ....

credential.<ThrowingRunnable<IOException>>map(auth -> () -> {
                PasswordWrapper pw = auth.getToken();
                ... // something that might throw an IOException
            }).orElseGet(() -> () -> {
                        response.setStatus(401);
                        LOGGER.log(Level.INFO, "credential is not found");
                    }
            ).run();
credential.<ThrowingRunnable<IOException>>map(auth -> () -> {
    PasswordWrapper pw = auth.getToken();
    ... // something that might throw an IOException
}).orElse(() -> {
            response.setStatus(401);
            LOGGER.log(Level.INFO, "credential is not found");
        }
).run();
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.function.ToIntFunction;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import org.junit.runner.Runner;
import org.junit.runners.Suite;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.RunnerBuilder;

/**
 * Try to proportion a suites tests into "SPLIT" number of equals sections and
 * then just run "SECTION" rather than all of the tests. Defined by
 * "SplitSuite.split" and "SplitSuite.section" respectively
 */
public class SplitSuite
        extends Suite {

    private static int SECTIONS
            = Integer.getInteger("SplitSuite.split", -1);
    private static int SECTION
            = Integer.getInteger("SplitSuite.section", -1);

    public SplitSuite(RunnerBuilder builder, Class<?>[] classes) throws InitializationError {
        super(builder, classes);
    }

    public SplitSuite(Class<?> klass, RunnerBuilder builder) throws InitializationError {
        super(klass, builder);
    }

    public List<Runner> getChildren() {
        if (SECTIONS < 0) {
            return super.getChildren();
        } else if (SECTIONS == 0) {
            throw new IllegalArgumentException("SplitSuite.split needs to be a positive integer, it cannot be zero. A negative valid will disable this feature.");
        } else if (SECTION >= SECTIONS) {
            throw new IllegalArgumentException("SplitSuite.section parameter " + SECTION + " needs to be less than SplitSuite.split " + SECTIONS);
        }

        final Map<Integer, List<Runner>> collect = originalList.stream().collect(
                assignToGroups(SECTIONS, Runner::testCount, originalList));

        return collect.get(SECTION);
    }

    
    /**
     * Assigns object to buckets, moving to the next when filled, whilst preserving
     * the original order.
     * @param <Type> The type that has some kind of size property
     * @param sections The number of sections to split the code across
     * @param sizer A function to return the size of the given object
     * @param originalList The original list to provide a base line size
     * @return A map that contains an order list of sections
     */
    public static <Type> Collector<Type, ?, Map<Integer, List<Type>>> assignToGroups(
            int sections, 
            final ToIntFunction<Type> sizer,
            Collection<Type> originalList) {

        // Get length
        int count = originalList.stream().collect(Collectors.summingInt(sizer));
        // Workout section length
        int sectionLength = count / sections;
        
        Function<Type, Integer> grouping = new Function<Type, Integer>() {
            
            int counter = 0;
            
            @Override
            public Integer apply(Type t) {
                int section = Math.min(counter / sectionLength, sections - 1);
                counter += sizer.applyAsInt(t);                
                return section; 
            }
        };
        
        
        return Collectors.groupingBy(grouping, TreeMap::new, Collectors.toList());
    }
    
    
}



So a quick change to the suite class....

@RunWith(SplitSuite.class)
@Suite.SuiteClasses({
    TableCRUD.class,
    TableCRUDChild.class,
    TableFilterSort.class,
    TableDefaultQuery.class,
    TableActions.class,
    ....
})
public class DevSuite {

}

Now the actual test step looks like this, note that this is running under hudson but you should be able to achieve something similar on the CI server of your choice. In order to make my life easier I derive the section number from the job name for so 3 sections I have ..._0 ..._1 and ..._2. In hudson the later jobs all cascade from the the first sharing all properties which makes maintenance a lot easier.

java ... -DSplitSuite.sections=${SECTIONS} -DSplitSuite.section=`echo ${JOB_NAME} | sed -e s/.*_//` ...

And it appears to work, obviously the algorithm to split the suites could be better to produce more balanced results; but that is an effort for another day.....
POSTED BY GERARD DAVISON AT 3:54 PM 0 COMMENTS
LABELS: HUDSON, JAVA, JENKINS, JUNIT
FRIDAY, JULY 31, 2015

Creating a shared library for Jersey 2.19 to use with Weblogic 12.1.3
Weblogic server comes with a shared library so you can deploy JAX-RS 2.0 applications; but is limited to Jersey version 2.5.1 and the instructions for changing this are not entirely obvious or straightforward. I have recently joined a new team at Oracle and one of the first things I did was to look at upgrading the dependent libraries. Now I have talked to the Jersey team and they don't support this combination; but it maybe useful enough to get you out of a bind until the next version of Weblogic is released.

I am going to do this using Maven because it means all the packaging and downloading is done for you. The basic structure of the project is as follows:

|-pom.xml
|-src
| |-main
| | |-java
| | |-resources
| | | |-META-INF
| | | | |-MANIFEST.MF
| | |-webapp
| | | |-WEB-INF
| | | | |-web.xml
| | | | |-weblogic.xml

I just generated a vanilla Maven project using Netbeans and then added in the Jersey dependencies I needed, it is likely this file could be cut down a little bit more with some determination. But it worked well enough for me:

<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    

    <groupId>com.example</groupId>
    <artifactId>JerseyLibrary</artifactId>
    <version>2.19</version>
    <packaging>war</packaging>

    <name>Jersey Library</name>

    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
         
        <jersey.version>2.19</jersey.version>
    </properties>
    <dependencies>
        <dependency>
            <groupId>org.glassfish.jersey.containers</groupId>
            <artifactId>jersey-container-servlet</artifactId>
            <version>${jersey.version}</version>
            <scope>compile</scope>
        </dependency>
        <dependency>
            <groupId>org.glassfish.jersey.core</groupId>
            <artifactId>jersey-client</artifactId>
            <version>${jersey.version}</version>
        </dependency>
        <dependency>
            <groupId>org.glassfish.jersey.media</groupId>
            <artifactId>jersey-media-multipart</artifactId>
            <version>${jersey.version}</version>
            <type>jar</type>
        </dependency>
        <dependency>
            <groupId>org.glassfish.jersey.media</groupId>
            <artifactId>jersey-media-moxy</artifactId>
            <version>${jersey.version}</version>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.1</version>
                <configuration>
                    <source>1.7</source>
                    <target>1.7</target>
                </configuration>
            </plugin>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-war-plugin</artifactId>
                <version>2.3</version>
                <configuration>
                    <failOnMissingWebXml>false</failOnMissingWebXml>
                    <archive>
                        <manifestFile>src/main/resources/META-INF/MANIFEST.MF</manifestFile>
                    </archive>
                </configuration>
            </plugin>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-dependency-plugin</artifactId>
                <version>2.6</version>
                <executions>
                    <execution>
                        <phase>validate</phase>
                        <goals>
                            <goal>copy</goal>
                        </goals>
                        <configuration>
                            <outputDirectory>${endorsed.dir}</outputDirectory>
                            <silent>true</silent>
                            <artifactItems>
                                <artifactItem>
                                    <groupId>javax</groupId>
                                    <artifactId>javaee-endorsed-api</artifactId>
                                    <version>7.0</version>
                                    <type>jar</type>
                                </artifactItem>
                            </artifactItems>
                        </configuration>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>

</project>

The next most important file in the MANIFEST.MF file, this tells Weblogic when you try to deploy the .war that this is a library and also contains the versions it supplies:

Specification-Title: Weblogic JAX-RS 2.0
Specification-Version: 2.0
Implementation-Title: Weblogic JAX-RS 2.0 Reference Implementation
Implementation-Version: 2.19
Extension-Name: jax-rs

Finally, you have to include a weblogic.xml file to tell the server that some classes you need to take from here rather than the server class loader. I got the basis of this from the file that comes with the 2.5.1 shares library that ships with 12.1.3 and then added a few more lines to take in account how the code has moved on. Depending on what your code is doing you may have to add a few more.

<?xml version="1.0" encoding="UTF-8"?>
<weblogic-web-app
        xmlns="http://xmlns.oracle.com/weblogic/weblogic-web-app"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xsi:schemaLocation="http://xmlns.oracle.com/weblogic/weblogic-web-app http://xmlns.oracle.com/weblogic/weblogic-web-app/1.4/weblogic-web-app.xsd">

  <container-descriptor>
        <prefer-application-packages>
            <!-- apis -->
            <package-name>javax.ws.rs.*</package-name>

            <!-- guava -->
            <package-name>com.google.common.*</package-name>

            <!-- jersey providers -->
            <package-name>com.sun.jersey.*</package-name>
            <package-name>org.glassfish.jersey.*</package-name>
            
            <package-name>jersey.repackaged.*</package-name>
            
            <!-- hk2 -->
            <package-name>org.jvnet.hk2.*</package-name>
            <package-name>org.jvnet.hk2.tiger_types.*</package-name>
            <package-name>org.glassfish.hk2.*</package-name>

            <package-name>javassist.*</package-name>

            <!-- media providers -->
            <package-name>org.eclipse.persistence.jaxb.rs.*</package-name>
            <package-name>org.codehaus.jackson.jaxrs.*</package-name>

            <!-- wls -->
            <package-name>weblogic.jaxrs.api.client.*</package-name>
            <package-name>weblogic.jaxrs.internal.api.client.*</package-name>
            <package-name>weblogic.jaxrs.dispatch.*</package-name>
            <package-name>weblogic.jaxrs.monitoring.util.*</package-name>
        </prefer-application-packages>
    </container-descriptor>
</weblogic-web-app>

Now all this needs is a quick mvn install and in the target directory there will be a nice complete shared library called JerseyLibrary-2.19.war that you can deploy in the normal way. Remember of course to update the library entries for the .war that is going to depend on this to have the right versions in it so it does pick up the 2.5.1 version.
POSTED BY GERARD DAVISON AT 4:32 PM 0 COMMENTS
LABELS: JERSEY, MAVEN, WLS
MONDAY, MARCH 9, 2015

Quick and dirty test for thread leakage in java
I was looking at a bit of code was leaking class loaders when deployed to tomcat - so I wanted to quick rule out that a background Thread was holding onto the loader. Pain by eye, it is quite easy with a few unix commands to process the stack trace, filter for the lines that contain the thread name, filter for just the thread names and sort:
sudo -u tomcat jstack 1853 | grep " prio=" | sed -e 's/"\(.*\)".*/\1/' | sort
Once this is done it is a simple matter to pipe the output to a file, say /tmp/before.threads, then run the deploy task a small prime number of times in order to create a clear signal, and then compare with the original thread list
[gdavison ~]$ diff /tmp/before.threads /tmp/after.threads
26a27
> RMI TCP Connection(11)-127.0.0.1
[gdavison ~]$
As with any server there will be a lot going on, in this case I saw a spurious RMI thread we can probably ignore, but if there was any application specific thread leakage the problem should repeat a number of times that is cleanly divisible with the prime number you picked before, for example where the prime is three with an example issue:
[gdavison@slc01htu ~]$ diff /tmp/before.threads /tmp/after.threads
8a9,11
> Problem Thread
> Problem Thread
> Problem Thread
26a30
> RMI TCP Connection(11)-127.0.0.1
[gdavison ~]$

As I say quick and dirty; but might be worth writing down for later use.
POSTED BY GERARD DAVISON AT 11:50 AM 0 COMMENTS
LABELS: JAVA
THURSDAY, FEBRUARY 12, 2015

How long did I sleep last night: using Cordova, HealthKit, and JavaScript, and a handful of Promises
Now that we can know how much activity I did yesterday, we can look at whether I am getting enough sleep.

A quick look at the Apple documentation lets us see that HKCategoryTypeIdentifierSleepAnalysis is the right measure to be using. This is a subtype of SampleType so we can use the querySampleType function.

First of all here is the same boilerplate code, with the minor modification to allow for access to the sleep data rather than the step data we used before. You could of course combine the two.

var wearable = {

    avaliable: _.once(function() {

        return new Promise(function(resolve, reject) {
        
            if (window.plugins.healthkit) {
                window.plugins.healthkit.available(
                        function() {
                            console.log("Healthkit is avaliable");
                            resolve(true);
                        },
                        function() {
                            console.log("Healthkit is not avaliable");
                            reject(Error(false));
                        }
                );
            } else {
                reject(Error("HealthKit Not Available"));
            }
        });
    }),


    getHealthKit : _.once(function() {

        return wearable.avaliable().then(function() {

            return new Promise(function(resolve, reject) {
                window.plugins.healthkit.requestAuthorization(
                        {
                            'readTypes': ['HKCategoryTypeIdentifierSleepAnalysis'],
                            'writeTypes': []
                        },
                function() {
                    console.log("HealthKit authorisation accepted");
                    resolve(window.plugins.healthkit);
                },
                function() {
                    reject(Error("HealthKit authorisation rejected"));
                });
            });
        });
    }),

    ...

}



Then it is s simple matter of querying HeathKit with a useful date range. Note I use limit and ascending to access the last data point - it is possible that different tools will report multiple entires for a single night. (In particular new parents) I need to do a little bit more investigation here.

wearable = {

    ... 

    querySleep: function() {

        wearable.getHealthKit().then(function(healthkit) {

                var startDate = moment().subtract('d', 1).toDate();

                healthkit.querySampleType({
                        'startDate': startDate,
                        'endDate': moment().toDate(),
                        'sampleType': "HKCategoryTypeIdentifierSleepAnalysis",
                        'ascending': "NO",
                        'limit': 1
                    },
                    function(value) {
                        console.log("Success for runing sleep query");

                        // Debug output for the momment
                        value.forEach(function(next) {

                            console.dir(next);
                            var measure = next.value === 1 ? "Sleeping" : "In Bed";
                            var minutesSleep = moment(next.endDate).diff(next.startDate, "minutes");
                            var hoursSleep = moment(next.endDate).diff(next.startDate, "hours");
                            console.log("Entry got " + minutesSleep + " minutes " + measure);
                            console.log("Entry got " + hoursSleep + " hours " + measure);

                        });

                        // Use data in some way
                    });

            }
        }
    },
}


Finally it would be really useful to be able to be told when data is added to HealthKit by another app for example the client app for a wearable. You can do this using the monitorSampleType function. Ideally this would code would then use something called an anchored query so you only access the most recently added data; but I haven't had time to implement this yet so we simply call querySleep().

Now making sure you app wakes up in the background to receive this data is a whole other blog....

var wearable = {

    ...

    monitorSleep: _.once(function() {

        console.log("Starting to monitor sleep");


        wearable.getHealthKit().then(function(healthkit) {
            healthkit.monitorSampleType({
                    'sampleType': "HKCategoryTypeIdentifierSleepAnalysis"
                },
                _.debounce(function(value) {
                    console.log("Sleep data has been updated, lets see if anything interesting has been added");
                    wearable.querySleep();
                }, 2000),
                function() {
                    console.log("Failed to monitor sample data");
                    console.dir(arguments);
                });

        });
    })
}




POSTED BY GERARD DAVISON AT 12:29 PM 0 COMMENTS
LABELS: CORDOVA, HEALTHKIT, JAVASCRIPT
TUESDAY, FEBRUARY 10, 2015

Per client cookie handling with Jersey
A lot of REST services will use cookies as part of the authentication / authorisation scheme. This is a problem because by default the old Jersey client will use the singleton CookieHandler.getDefault which is most cases will be null and if not null will not likely work in a multithreaded server environment. (This is because in the background the default Jersey client will use URL.openConnection)

Now you can work around this by using the Apache HTTP Client adapter for Jersey; but this is not always available. So if you want to use the Jersey client with cookies in a server environment you need to do a little bit of reflection to ensure you use your own private cookie jar.

final CookieHandler ch = new CookieManager();
    
    Client client = new Client(new URLConnectionClientHandler(
       new HttpURLConnectionFactory() {

        @Override
        public HttpURLConnection getHttpURLConnection(URL uRL) throws IOException {
            HttpURLConnection connect = (HttpURLConnection) uRL.openConnection();
        
            try {
                
                Object toModify = connect;
                
                if (!(toModify instanceof sun.net.www.protocol.http.HttpURLConnection)) {
        
                    Field delegateField = connect.getClass().getDeclaredField("delegate");
                    delegateField.setAccessible(true);
                    toModify = MethodHandles.lookup().unreflectGetter(delegateField)
                        .bindTo(toModify).invoke();
                    
                }
        
                Field cookieField = sun.net.www.protocol.http.HttpURLConnection.class.getDeclaredField("cookieHandler");
                cookieField.setAccessible(true);
                MethodHandle mh = MethodHandles.lookup().unreflectSetter(cookieField);
                mh.bindTo(toModify).invoke(ch);
            } catch (Throwable e) {
                e.printStackTrace();
            }
        
            return connect;

        }
    }));
    

This will only work if your environment is using the internal implementation of sun.net.www.protocol.http.HttpURLConnection that comes with the JDK. This appears to be the case for modern versions of WLS.

For JAX-RS 2.0 you can do a similar change using Jersey 2.x specific ClientConfig class and HttpUrlConnectorProvider.

final CookieHandler ch = new CookieManager();


    Client client =
        ClientBuilder.newClient(new ClientConfig().connectorProvider(new HttpUrlConnectorProvider().connectionFactory(new HttpUrlConnectorProvider.ConnectionFactory() {
            @Override
            public HttpURLConnection getConnection(URL uRL) throws IOException {
                HttpURLConnection connect = (HttpURLConnection) uRL.openConnection();
            
                try {
                    
                    Object toModify = connect;
                    
                    if (!(toModify instanceof sun.net.www.protocol.http.HttpURLConnection)) {
            
                        Field delegateField = connect.getClass().getDeclaredField("delegate");
                        delegateField.setAccessible(true);
                        toModify = MethodHandles.lookup().unreflectGetter(delegateField)
                            .bindTo(toModify).invoke();
                        
                    }
            
                    Field cookieField = sun.net.www.protocol.http.HttpURLConnection.class.getDeclaredField("cookieHandler");
                    cookieField.setAccessible(true);
                    MethodHandle mh = MethodHandles.lookup().unreflectSetter(cookieField);
                    mh.bindTo(toModify).invoke(ch);
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            
                return connect;

            }
        })));



Update 11th Feb 2015: It seems in some cases, in particular using https, I have seen the HttpURLConnection wrapped in another class, to work around this just use reflection to access the value of the delegate field. I have updated the code examples to reflect this issue.
POSTED BY GERARD DAVISON AT 4:47 PM 0 COMMENTS
LABELS: JAX-RS, JERSEY
SUNDAY, FEBRUARY 1, 2015

How many steps did I walk yesterday: using Cordova, HealthKit, and JavaScript, and a handful of Promises
As people who know me might know I have been playing around with various wearables for some years now, starting with a Nike Fuel Band, and also trying out products from Misfit and UP along the way. I have mostly just watched with interest as my exercise levels, and indeed sleep now we have a little one in the house, has gone up and down over time. 

More recently with the arrival of HealthKit there comes a standard method of accessing this information from multiple products on the iPhone, I needed to brush up on my JavaScript for work so I decided to have a play in Cordova to build something in my spare time. One of the cool things about that Cordova community when compared to other app development frameworks is the large and healthy plugin community - and quick google and I found what I needed to support HealthKit HealthKit. This took only a small amount of patching from me to get at the information I wanted. In this blog I will just look at number of steps per day as a proxy for activity.

I am going to assume the rest of the Cordova App is in place and start with a simple wearable object which exposes a promise that allows you to check whether HealthKit is available on this platform, you might have iOS 8 but HealthKit is iPhone only. Remember that most of Cordova is interacting with a bunch of asynchronous native systems so you have to make sure you consider this as you work with it. Hence the liberal use of promises. (If you are wanting to support < iOS 8 then you will need to use a different promise library / shim) In this case I am also using the underscorejs library to create a promise who's result won't be calculated until it is invoked for the first time.

var wearable = {

    avaliable: _.once(function() {

        return new Promise(function(resolve, reject) {
        
            if (window.plugins.healthkit) {
                window.plugins.healthkit.available(
                        function() {
                            console.log("Healthkit is avaliable");
                            resolve(true);
                        },
                        function() {
                            console.log("Healthkit is not avaliable");
                            reject(Error(false));
                        }
                );
            } else {
                reject(Error("HealthKit Not Available"));
            }
        });
    }),

...

}

The next stage of the code, once you are sure that the system is available, is to get verify that you can access the data you want to access. Now Apple is clear in there developer guide that you should only request the data you might want to access once. So no asking for step data one time then heath rate data in a different part of the UI. The Cordova HealthKit Plugin will just return with a success if those values are already authorised, so we will use another promise as a gateway to actually working with the data to make one consistent entry point for authorisation.

var wearable = {

...


    getHealthKit : _.once(function() {

        return wearable.avaliable().then(function() {

            return new Promise(function(resolve, reject) {
                window.plugins.healthkit.requestAuthorization(
                        {
                            'readTypes': ['HKQuantityTypeIdentifierStepCount'],
                            'writeTypes': []
                        },
                function() {
                    console.log("HealthKit authorisation accepted");
                    resolve(window.plugins.healthkit);
                },
                function() {
                    reject(Error("HealthKit authorisation rejected"));
                });
            });
        });
    }),

...

}

Finally now we have all the ground work in place, lets put in place a simple method to work out how many steps were recorded yesterday. I am then using special function called sumQuantityType that performs some rather clever statistical leg work for you. Consider if you have a iPhone 6 along with step monitoring wrist band of some kind like a Apple Watch, in HealthKit you will have two lots of data that inconsistently overlaps. HealthKit provides methods to deal with this, more information can be found in the WWDC presentation from 2014, but basically it works out for each time segment which data source(s) to use to get the value for the time range requested.

var wearable = {

...


    querySteps : function() {
        
        return wearable.getHealthKit().then(function (healthkit)
        {
            return new Promise(function(resolve, reject) {

                var m = moment().startOf('day');
                var endDate = m.toDate();
                var startDate = moment(m).subtract('d', 1).toDate();

                healthkit.sumQuantityType({
                        'startDate': startDate,
                        'endDate': endDate,
                        'sampleType': "HKQuantityTypeIdentifierStepCount"
                    },
                    function(value)
                    {
                        resolve(value)
                    },
                    function()
                    {
                        reject(Error("Problem queuing steps"));
                    });
        });
    }

}

Now we have this object, we can access the data and show it to the user. Note that the promise objects chain so the catch function will tell you if something went wrong at any stage of the process, if you had tried to wire this up with event handlers you would have had an ungodly mess of code and callbacks.

wearable.querySteps()
       .then(function(value){
           console.log("Number of steps "  + value);
           ... update UI
       })
       .catch(function (error) {
           ... update UI
       };


This is of course just the start, there is a lot more interesting stuff you can get from this API; but that is for another day. I like HealthKit because unlike GoogleFit all your health data is stored locally by default. There is a lot of potential here for doing interesting stuff.

Finally a quick shout out of Eddy Verbruggen who created HealthKit plugin along with a whole host of other interesting libraries.

I am going to walk around for a bit now, gotta meet those targets. :-)
POSTED BY GERARD DAVISON AT 10:10 AM 0 COMMENTS
LABELS: CORDOVA, HEALTHKIT, JAVASCRIPT, PROMISE, WEARABLE
Newer Posts Older Posts Home
Subscribe to: Posts (Atom)
FOLLOWERS


  
  
WEB CODE GEEK

 Web Code Geek 
BLOG ARCHIVE

▼  2016 (7)
▼  August (1)
What is bound to generic types for method referenc...
►  June (1)
►  April (1)
►  March (2)
►  January (2)
►  2015 (6)
►  2014 (12)
►  2013 (14)
►  2012 (14)
►  2011 (13)
►  2010 (13)
►  2009 (87)
►  2008 (74)
►  2007 (25)
TWITTER UPDATES

follow me on Twitter
ABOUT ME

My photo
GERARD DAVISON
Currently working at Oracle in Reading, UK working on web services for Application Builder Cloud Service.
VIEW MY COMPLETE PROFILE


