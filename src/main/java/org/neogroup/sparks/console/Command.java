
package org.neogroup.sparks.console;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Command {

    private static final String SHORT_PARAMETER_PREFIX = "-";
    private static final String LONG_PARAMETER_PREFIX = "--";

    private final String command;

    private final String name;
    private final List<String> parameters;
    private final Map<String,String> namedParameters;

    public Command(String command) {
        this.command = command;
        List<String> tokens = parseCommand(command);
        name = tokens.get(0);
        parameters = new ArrayList<>();
        namedParameters = new HashMap<>();

        String token = null;
        String parameterName = null;
        String parameterValue = null;
        for (int i = 1; i < tokens.size(); i++) {
            token = tokens.get(i);
            if (token.startsWith(LONG_PARAMETER_PREFIX)) {
                parameterName = token.substring(LONG_PARAMETER_PREFIX.length());
                if (!parameterName.isEmpty()) {
                    parameterValue = null;
                    if (tokens.size() != i + 1) {
                        String nextToken = tokens.get(i + 1);
                        if (!nextToken.startsWith(LONG_PARAMETER_PREFIX) && !nextToken.startsWith(SHORT_PARAMETER_PREFIX)) {
                            parameterValue = nextToken;
                            namedParameters.put(parameterName, parameterValue);
                        }
                    }
                }
            }
            else if (token.startsWith(SHORT_PARAMETER_PREFIX)) {
                parameterName = token.substring(1,2);
                if (!parameterName.isEmpty()) {
                    parameterValue = null;
                    if (token.length() > 2) {
                        parameterValue = token.substring(2);
                        parameters.add(parameterValue);
                        namedParameters.put(parameterName, parameterValue);
                    }
                    else if (tokens.size() != i + 1) {
                        String nextToken = tokens.get(i + 1);
                        if (!nextToken.startsWith(LONG_PARAMETER_PREFIX) && !nextToken.startsWith(SHORT_PARAMETER_PREFIX)) {
                            parameterValue = nextToken;
                            namedParameters.put(parameterName, parameterValue);
                        }
                    }
                }
            }
            else {
                parameters.add(token);
            }
        }
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

    public List<String> getParameters() {
        return parameters;
    }

    public Map<String,String> getNamedParameters() {
        return namedParameters;
    }

    public String getParameter (String parameterName) {
        return namedParameters.get(parameterName);
    }

    public boolean hasParameter (String parameterName) {
        return namedParameters.containsKey(parameterName);
    }

    private static List<String> parseCommand (final String command) {

        List<String> tokens = new ArrayList<String>(0);
        try {
            int tokenStart = -1;
            boolean inJoinedToken = false;
            char joinChar = 0;
            for (int index = 0; index < command.length(); index++) {
                char commandChar = command.charAt(index);
                if (commandChar == '\\') {
                    index++;
                }
                else {
                    if (!inJoinedToken) {
                        if (Character.isWhitespace(commandChar)) {
                            if (tokenStart != -1) {
                                tokens.add(command.substring(tokenStart, index).trim());
                                tokenStart = -1;
                            }
                        }
                        else if (commandChar == '"' || commandChar == '\'') {
                            if (tokenStart != -1) {
                                tokens.add(command.substring(tokenStart, index).trim());
                            }
                            tokenStart = index+1;
                            joinChar = commandChar;
                            inJoinedToken = true;
                        }
                        else if (tokenStart == -1) {
                            tokenStart = index;
                        }
                    }
                    else if (commandChar == joinChar) {
                        tokens.add(command.substring(tokenStart, index));
                        inJoinedToken = false;
                        tokenStart = -1;
                    }
                }
            }
            if (tokenStart != -1 && !inJoinedToken) {
                tokens.add(command.substring(tokenStart).trim());
            }
        }
        catch (Exception exception) {}
        return tokens;
    }
}
