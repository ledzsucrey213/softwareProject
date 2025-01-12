package com.autocare.SessionManager;

import com.autocare.user.User;
import java.util.Optional;

public class SessionManager {

    // Holds the current user object (only one active session at a time), wrapped in an Optional
    private static Optional<User> currentUser = Optional.empty();

    /**
     * Starts a session for the given user.
     *
     * @param user The logged-in user object.
     */
    public static void startSession(Optional<User> user) {
        currentUser = user;  // Wrap the user in Optional, allowing null to be represented
    }

    /**
     * Retrieves the current user object in the session.
     *
     * @return The current user object if a session is active, or an empty Optional if no session exists.
     */
    public static Optional<User> getCurrentUser() {
        return currentUser;  // Return the Optional directly
    }

    /**
     * Ends the session by clearing the current user object.
     */
    public static void endSession() {
        currentUser = Optional.empty();  // Clear the current user by setting to an empty Optional
    }

    /**
     * Checks if a session is currently active.
     *
     * @return True if a session is active (i.e., currentUser is not empty), false otherwise.
     */
    public static boolean isSessionActive() {
        return currentUser.isPresent();  // Check if the Optional contains a user
    }
}
