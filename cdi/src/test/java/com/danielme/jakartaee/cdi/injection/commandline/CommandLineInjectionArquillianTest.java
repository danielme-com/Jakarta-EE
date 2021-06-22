package com.danielme.jakartaee.cdi.injection.commandline;

import com.danielme.jakartaee.cdi.Deployments;
import jakarta.inject.Inject;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit5.ArquillianExtension;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(ArquillianExtension.class)
class CommandLineInjectionArquillianTest {

    @Inject
    @CommandLineQualifier(CommandLineSupported.BASH)
    private CommandLine bash;

    @Inject
    @CommandLineQualifier(CommandLineSupported.POWERSHELL)
    private CommandLine powershell;


    @Deployment
    public static WebArchive createDeployment() {
        return Deployments.commandLine();
    }

    @Test
    void testCommandLineIsBash() {
        assertThat(bash).isInstanceOf(BashCommandLine.class);
    }

    @Test
    void testCommandLineIsPowershell() {
        assertThat(powershell).isInstanceOf(PowershellCommandLine.class);
    }

}