package com.danielme.jakartaee.cdi;

import com.danielme.jakartaee.cdi.injection.commandline.CommandLine;
import com.danielme.jakartaee.cdi.decorators.MessageDecorator;
import com.danielme.jakartaee.cdi.events.FileStorageEventListener;
import com.danielme.jakartaee.cdi.injection.file.FileStorage;
import com.danielme.jakartaee.cdi.interceptors.LoggerProducer;
import com.danielme.jakartaee.cdi.interceptors.LoggingInterceptor;
import com.danielme.jakartaee.cdi.injection.movie.MovieProvider;
import com.danielme.jakartaee.cdi.producers.ListsProducer;
import com.danielme.jakartaee.cdi.producers.PropertiesProducer;
import com.danielme.jakartaee.cdi.scope.ScopesServlet;
import com.danielme.jakartaee.cdi.injection.shapes.Shape;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.FileAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;

import java.io.File;

public final class Deployments {

    private static final String WAR_NAME = "cdi-test.war";

    private Deployments() {
    }

    public static WebArchive scopes() {
        return ShrinkWrap.create(WebArchive.class, WAR_NAME)
                .addPackage(ScopesServlet.class.getPackage());
    }

    public static WebArchive interceptors() {
        return ShrinkWrap.create(WebArchive.class, WAR_NAME)
                .addPackage(LoggingInterceptor.class.getPackage())
                .addAsWebInfResource(new FileAsset(new File("src/test/resources/beans-interceptors.xml")), "beans.xml")
                .addAsLibraries(getAssertjFiles());
    }

    public static WebArchive filesAndEvents() {
        return ShrinkWrap.create(WebArchive.class, WAR_NAME)
                .addPackage(FileStorage.class.getPackage())
                .addPackage(FileStorageEventListener.class.getPackage())
                .addAsWebInfResource(new FileAsset(new File("src/main/webapp/WEB-INF/beans.xml")), "beans.xml")
                .addAsLibraries(getAssertjFiles())
                .addAsLibraries(getAwaitibilityFiles());
    }

    public static WebArchive commandLine() {
        return ShrinkWrap.create(WebArchive.class, WAR_NAME)
                .addPackage(CommandLine.class.getPackage())
                .addAsLibraries(getAssertjFiles());
    }

    public static WebArchive movies() {
        return ShrinkWrap.create(WebArchive.class, WAR_NAME)
                .addPackage(MovieProvider.class.getPackage())
                .addAsLibraries(getAssertjFiles());
    }

    public static WebArchive decorators() {
        return ShrinkWrap.create(WebArchive.class, WAR_NAME)
                .addPackage(MessageDecorator.class.getPackage())
                .addClass(LoggerProducer.class)
                .addAsWebInfResource(new FileAsset(new File("src/test/resources/beans-decorators.xml")), "beans.xml")
                .addAsLibraries(getAssertjFiles());
    }

    public static WebArchive moviesAlternatives() {
        return ShrinkWrap.create(WebArchive.class, WAR_NAME)
                .addPackage(MovieProvider.class.getPackage())
                .addAsWebInfResource(new FileAsset(new File("src/test/resources/beans-movie-alternatives.xml")), "beans.xml")
                .addAsLibraries(getAssertjFiles());
    }

    public static WebArchive producers() {
        return ShrinkWrap.create(WebArchive.class, WAR_NAME)
                .addClass(ListsProducer.class)
                .addPackage(FileStorage.class.getPackage())
                .addPackage(FileStorageEventListener.class.getPackage())
                .addAsWebInfResource(new FileAsset(new File("src/main/webapp/WEB-INF/beans.xml")), "beans.xml")
                .addAsLibraries(getAssertjFiles());
    }

    public static WebArchive producersAlternatives() {
        return ShrinkWrap.create(WebArchive.class, WAR_NAME)
                .addPackage(ListsProducer.class.getPackage())
                .addPackage(FileStorage.class.getPackage())
                .addAsWebInfResource(new FileAsset(new File("src/test/resources/beans-producer-alternatives.xml")), "beans.xml")
                .addAsLibraries(getAssertjFiles());
    }

    public static WebArchive injectionPoint() {
        return ShrinkWrap.create(WebArchive.class, WAR_NAME)
                .addPackage(PropertiesProducer.class.getPackage())
                .addAsResource(new File("src/main/resources/"), "")
                .addAsLibraries(getAssertjFiles());
    }

    public static WebArchive shapes() {
        return shapes("src/main/webapp/WEB-INF/beans.xml");
    }

    public static WebArchive shapesAlternative() {
        return shapes("src/test/resources/beans-shapes-alternatives.xml");
    }


    private static WebArchive shapes(String beansFile) {
        return ShrinkWrap.create(WebArchive.class, WAR_NAME)
                .addPackage(Shape.class.getPackage())
                .addAsWebInfResource(new FileAsset(new File(beansFile)), "beans.xml")
                .addAsLibraries(getAssertjFiles());
    }

    private static File[] getAssertjFiles() {
        return Maven.resolver()
                .loadPomFromFile("pom.xml")
                .resolve("org.assertj:assertj-core")
                .withTransitivity()
                .asFile();
    }

    private static File[] getAwaitibilityFiles() {
        return Maven.resolver()
                .loadPomFromFile("pom.xml")
                .resolve("org.awaitility:awaitility")
                .withTransitivity()
                .asFile();
    }

}
