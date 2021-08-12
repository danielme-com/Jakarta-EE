package com.danielme.jakartaee.jpa;

import com.danielme.jakartaee.jpa.db.DbInfo;
import jakarta.inject.Inject;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit5.ArquillianExtension;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(ArquillianExtension.class)
class DbInfoArquillianTest {

    @Inject
    private DbInfo dbInfo;

    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class, "jpa-test.war")
                .addClass(DbInfo.class);
    }

    @Test
    void testVersion8() {
        assertTrue(dbInfo.getVersion().startsWith("8"));
    }

}