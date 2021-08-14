package com.danielme.jakartaee.jpa.extensions;

import com.danielme.jakartaee.jpa.JpaArquillianTest;
import com.github.database.rider.junit5.DBUnitExtension;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestInstancePreDestroyCallback;

import javax.naming.InitialContext;
import javax.naming.NamingException;

public class ArquillianDBUnitExtension extends DBUnitExtension {

    private boolean isInside() {
        try {
            new InitialContext().lookup("java:comp/env");
            return true;
        } catch (NamingException ex) {
            return false;
        }
    }

    @Override
    public void beforeTestExecution(ExtensionContext extensionContext) throws Exception {
        if (isInside()) {
            super.beforeTestExecution(extensionContext);
        }
    }

    @Override
    public void afterTestExecution(ExtensionContext extensionContext) throws Exception {
        if (isInside()) {
                super.afterTestExecution(extensionContext);
        }
    }

    @Override
    public void beforeEach(ExtensionContext extensionContext) throws Exception {
        if (isInside()) {
            super.beforeEach(extensionContext);
        }
    }

    @Override
    public void afterEach(ExtensionContext extensionContext) throws Exception {
        if (isInside()) {
            super.afterEach(extensionContext);
        }
    }

    @Override
    public void beforeAll(ExtensionContext extensionContext) throws Exception {
        if (isInside()) {
            super.beforeAll(extensionContext);
        }
    }

    @Override
    public void afterAll(ExtensionContext extensionContext) throws Exception {
        if (isInside()) {
            super.afterAll(extensionContext);
        }
    }

}
