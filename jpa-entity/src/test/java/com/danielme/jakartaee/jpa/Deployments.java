package com.danielme.jakartaee.jpa;

import com.danielme.jakartaee.jpa.converters.BooleanAttributeConverter;
import com.danielme.jakartaee.jpa.converters.ShapeAttributeConverter;
import com.danielme.jakartaee.jpa.entities.Expense;
import com.danielme.jakartaee.jpa.entities.Language;
import com.danielme.jakartaee.jpa.entities.UserNaturalId;
import com.danielme.jakartaee.jpa.extensions.ArquillianDBUnitExtension;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.asset.FileAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;

import java.io.File;

final class Deployments {

    private static final String ARTIFACT = "jpa-entity.war";

    private Deployments() {
    }

    public static WebArchive asClient() {
        return ShrinkWrap.create(WebArchive.class, ARTIFACT)
                .addClass(Expense.class)
                .addAsResource(new FileAsset(new File("src/main/resources/META-INF/persistence.xml")),
                        "/META-INF/persistence.xml");
    }

    static WebArchive hibernate() {
        return ShrinkWrap.create(WebArchive.class, ARTIFACT)
                .addClass(UserNaturalId.class)
                .addClass(ArquillianDBUnitExtension.class)
                .addAsWebInfResource(new File("src/test/resources/datasets/users_naturalid.yml"), "classes/datasets/users_naturalid.yml")
                .addAsWebInfResource(new File("src/test/resources/dbunit.yml"), "classes/dbunit.yml")
                .addAsResource(new FileAsset(new File("src/main/resources/META-INF/persistence.xml")),
                        "/META-INF/persistence.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsLibraries(assertjLibs())
                .addAsLibraries(dbRiderLibs());
    }

    static WebArchive jpa() {
        return ShrinkWrap.create(WebArchive.class, ARTIFACT)
                .addPackage(Expense.class.getPackage())
                .addClass(BooleanAttributeConverter.class)
                .addClass(ShapeAttributeConverter.class)
                .addClass(ArquillianDBUnitExtension.class)
                .addClass(Datasets.class)
                .addAsWebInfResource(new File("src/test/resources/datasets/expenses.yml"), "classes/datasets/expenses.yml")
                .addAsWebInfResource(new File("src/test/resources/datasets/users.yml"), "classes/datasets/users.yml")
                .addAsWebInfResource(new File("src/test/resources/datasets/cities.yml"), "classes/datasets/cities.yml")
                .addAsWebInfResource(new File("src/test/resources/datasets/geometries.yml"), "classes/datasets/geometries.yml")
                .addAsWebInfResource(new File("src/test/resources/dbunit.yml"), "classes/dbunit.yml")
                .addAsResource(new FileAsset(new File("src/main/resources/META-INF/persistence.xml")),
                        "/META-INF/persistence.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsLibraries(assertjLibs())
                .addAsLibraries(dbRiderLibs());
    }

    static WebArchive jpaLifecycle() {
        return ShrinkWrap.create(WebArchive.class, ARTIFACT)
                .addClass(Expense.class)
                .addClass(Language.class)
                .addClass(Datasets.class)
                .addClass(ArquillianDBUnitExtension.class)
                .addAsWebInfResource(new File("src/test/resources/datasets/expenses.yml"), "classes/datasets/expenses.yml")
                .addAsWebInfResource(new File("src/test/resources/dbunit.yml"), "classes/dbunit.yml")
                .addAsResource(new FileAsset(new File("src/main/resources/META-INF/persistence.xml")),
                        "/META-INF/persistence.xml")
                .addAsLibraries(assertjLibs())
                .addAsLibraries(dbRiderLibs())
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml").addAsLibraries(dbRiderLibs());
    }

    private static File[] assertjLibs() {
        return Maven.resolver()
                .loadPomFromFile("pom.xml")
                .resolve("org.assertj:assertj-core")
                .withTransitivity()
                .asFile();
    }

    private static File[] dbRiderLibs() {
        return Maven.resolver()
                .loadPomFromFile("pom.xml")
                .resolve("com.github.database-rider:rider-junit5")
                .withTransitivity()
                .asFile();
    }

}
