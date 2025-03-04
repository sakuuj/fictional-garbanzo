package by.sakuuj.agsr.parser;

import by.sakuuj.agsr.domain.UserArgs;
import by.sakuuj.agsr.domain.UserCredentials;

import java.net.URI;
import java.util.Objects;

public class ArgsParser {

    public static final URI DEFAULT_BASE_URI = URI.create("http://localhost:8080");

    public static final String USER_FLAG = "-u";

    public static final String BASE_URI_FLAG = "-b";

    public static final String PASSWORD_FLAG = "-p";

    public UserArgs parseArgs(String[] args) {

        String username = null;
        String password = null;
        URI baseUri = DEFAULT_BASE_URI;

        for (int i = 0; i < args.length; i += 2) {

            switch (args[i]) {
                case USER_FLAG -> username = getFlagValue(args, i, USER_FLAG);
                case PASSWORD_FLAG -> password = getFlagValue(args, i, PASSWORD_FLAG);
                case BASE_URI_FLAG -> {
                    String destinationAddressValue = getFlagValue(args, i, BASE_URI_FLAG);
                    baseUri = URI.create(destinationAddressValue);
                }
                default -> throw new IllegalStateException("Unrecognized input: " + args[i]);
            }
        }

        var userCredentials = UserCredentials.builder()
                .username(username)
                .password(password)
                .build();

        var userArgs = UserArgs.builder()
                .userCredentials(userCredentials)
                .baseUri(baseUri)
                .build();

        validateUserArgs(userArgs);

        return userArgs;
    }

    private static String getFlagValue(String[] args, int flagIndex, String flagName) {

        int flagValueIndex = flagIndex + 1;

        if (flagValueIndex >= args.length) {
            throw new IllegalStateException("Please, provide a value for the flag '%s'".formatted(flagName));
        }

        return args[flagValueIndex];
    }

    private static void validateUserArgs(UserArgs userArgs) {

        Objects.requireNonNull(userArgs);
        Objects.requireNonNull(userArgs.userCredentials());

        validateUserCredentials(userArgs.userCredentials());
    }

    private static void validateUserCredentials(UserCredentials userCredentials) {

        if (isNullOrBlank(userCredentials.username())) {
            throw new IllegalStateException("Username is not provided");
        }

        if (isNullOrBlank(userCredentials.password())) {
            throw new IllegalStateException("Username is not provided");
        }
    }

    private static boolean isNullOrBlank(String string) {
        return string == null || string.isBlank();
    }
}
