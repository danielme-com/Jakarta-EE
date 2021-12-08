package com.danielme.jakartaee.jpa;

import com.danielme.jakartaee.jpa.entities.Expense;
import com.danielme.jakartaee.jpa.extensions.ArquillianDBUnitExtension;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.asset.FileAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;

import java.io.File;

final class Deployments {

    private static final String ARTIFACT_NAME = "jpa-relations.war";

    private Deployments() {
    }


    static WebArchive relations() {
        return ShrinkWrap.create(WebArchive.class, ARTIFACT_NAME)
                .addPackages(true, Expense.class.getPackage())
                .addClass(ArquillianDBUnitExtension.class)
                .addClass(BaseRelationsTest.class)
                .addClass(Datasets.class)
                .addAsWebInfResource(new File("src/test/resources/datasets/invoices.yml"), "classes/datasets/invoices.yml")
                .addAsWebInfResource(new File("src/test/resources/datasets/users.yml"), "classes/datasets/users.yml")
                .addAsWebInfResource(new File("src/test/resources/datasets/expenses.yml"), "classes/datasets/expenses.yml")
                .addAsWebInfResource(new File("src/test/resources/datasets/coupons.yml"), "classes/datasets/coupons.yml")
                .addAsWebInfResource(new File("src/test/resources/datasets/categories.yml"), "classes/datasets/categories.yml")
                .addAsWebInfResource(new File("src/test/resources/datasets/budgets.yml"), "classes/datasets/budgets.yml")
                .addAsWebInfResource(new File("src/test/resources/datasets/contacts.yml"), "classes/datasets/contacts.yml")
                .addAsWebInfResource(new File("src/test/resources/dbunit.yml"), "classes/dbunit.yml")
                .addAsResource(new FileAsset(new File("src/main/resources/META-INF/persistence.xml")),
                        "/META-INF/persistence.xml")
                .addAsLibraries(testLibraries())
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    private static File[] testLibraries() {
        return Maven.resolver()
                .loadPomFromFile("pom.xml")
                .resolve("org.assertj:assertj-core", "com.github.database-rider:rider-junit5")
                .withTransitivity()
                .asFile();
    }

}
