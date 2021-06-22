package com.danielme.jakartaee.cdi.injection.commandline;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
@CommandLineQualifier(CommandLineSupported.BASH)
public class BashCommandLine implements CommandLine {
}
