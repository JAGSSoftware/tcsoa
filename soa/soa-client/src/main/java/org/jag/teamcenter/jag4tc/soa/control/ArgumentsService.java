/*
 * MIT License
 *
 * Copyright (c) 2019 José A. García Sánchez
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.jag.teamcenter.jag4tc.soa.control;

import java.util.ArrayList;
import java.util.List;

import com.google.inject.Inject;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class ArgumentsService implements ArgumentsServiceBA {

    private static final Logger LOGGER = LoggerFactory.getLogger(ArgumentsService.class);

    private static final Option HOST_OPTION =
            Option.builder("host").hasArg().desc("Teamcenter instance/host").build();
    private static final Option USERNAME_OPTION =
            Option.builder("u").longOpt("username").hasArg().desc("Teamcenter username").build();
    private static final Option PASSWORD_OPTION =
            Option.builder("p").longOpt("password").hasArg().desc("Teamcenter user password").build();
    private static final Option GROUP_OPTION =
            Option.builder("g").longOpt("group").hasArg().desc("Teamcenter user group").build();
    private static final Option ROLE_OPTION =
            Option.builder("r").longOpt("role").hasArg().desc("Teamcenter user role").build();
    private static final Option HELP_OPTION = Option.builder("h").desc("Help").build();

    private static final List<Option> MANDATORY_FIELDS = new ArrayList<>();
    private static final String CMDLINE_SYNTAX = "soa-client";

    static {
        MANDATORY_FIELDS.add(HOST_OPTION);
        MANDATORY_FIELDS.add(USERNAME_OPTION);
        MANDATORY_FIELDS.add(PASSWORD_OPTION);
    }

    @Inject
    private OptionsHelpFormatter optionsHelpFormatter;

    @Override
    public Arguments parse(final String[] args) {
        final Options options = new Options();
        options.addOption(HOST_OPTION);
        options.addOption(USERNAME_OPTION);
        options.addOption(PASSWORD_OPTION);
        options.addOption(GROUP_OPTION);
        options.addOption(ROLE_OPTION);
        options.addOption(HELP_OPTION);

        try {
            final CommandLine commandLine = new DefaultParser().parse(options, args);

            if (commandLine.hasOption(HELP_OPTION.getOpt())) {
                optionsHelpFormatter.printHelp(CMDLINE_SYNTAX, options);
                return null;
            }

            final Arguments arguments = new Arguments();
            if (hasAllMandatoryOptions(commandLine)) {
                arguments.setHost(commandLine.getOptionValue(HOST_OPTION.getOpt()));
                arguments.setUsername(commandLine.getOptionValue(USERNAME_OPTION.getOpt()));
                arguments.setPassword(commandLine.getOptionValue(PASSWORD_OPTION.getOpt()));
            } else {
                optionsHelpFormatter.printHelp(CMDLINE_SYNTAX, options);
                return null;
            }

            if (commandLine.hasOption(GROUP_OPTION.getOpt())) {
                arguments.setGroup(commandLine.getOptionValue(GROUP_OPTION.getOpt()));
            } else {
                arguments.setGroup("");
            }
            if (commandLine.hasOption(ROLE_OPTION.getOpt())) {
                arguments.setRole(commandLine.getOptionValue(ROLE_OPTION.getOpt()));
            } else {
                arguments.setRole("");
            }

            return arguments;
        } catch (ParseException e) {
            LOGGER.warn("Incorrect arguments", e);
            optionsHelpFormatter.printHelp(CMDLINE_SYNTAX, options);
        }

        return null;
    }

    private boolean hasAllMandatoryOptions(final CommandLine commandLine) {
        boolean allOptions = true;
        for (final Option option : MANDATORY_FIELDS) {
            allOptions &= commandLine.hasOption(option.getOpt());
        }
        return allOptions;
    }
}
