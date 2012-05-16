Use [`java.lang.instrument`](http://docs.oracle.com/javase/6/docs/api/java/lang/instrument/package-summary.html)
without writing an agent.

This library allows to use the [`Instrumentation`](http://docs.oracle.com/javase/6/docs/api/java/lang/instrument/Instrumentation.html)
API transparently, without explicitly starting the application with an agent, attaching one at runtime or even implementing one.

Prerequisites
-------------

* Run the application on a JDK.

Usage
-----

1. Grab the latest release at [Maven Central Repository](http://search.maven.org/#search%7Cga%7C1%7Ca%3A%22auto-instrument%22).
2. Add it to your application classpath.
3. Call it wherever you need to:


    Instrumentation inst = AutoInstrumentation.getInstance();

Disclaimer
---------
This implementation is a proof of concept using several hacks that are not guaranteed to be consistent across systems and JVMs. Do not rely on it for production applications !
