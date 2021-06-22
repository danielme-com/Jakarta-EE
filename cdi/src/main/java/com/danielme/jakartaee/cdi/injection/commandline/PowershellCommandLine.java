package com.danielme.jakartaee.cdi.injection.commandline;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
@CommandLineQualifier(CommandLineSupported.POWERSHELL)
public class PowershellCommandLine implements CommandLine {
}
