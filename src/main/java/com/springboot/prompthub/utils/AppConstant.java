package com.springboot.prompthub.utils;

public class AppConstant {
    public static final String DEFAULT_ADMIN_EMAIL = "fi.kwiatkowski@gmail.com";
    public static final String DEFAULT_ADMIN_PASSWORD = "Password@1!";
    public static final String DEFAULT_USER_PASSWORD = "Password@1!";
    public static final String[] LM_ROLES = { "assistant", "system", "user" };
    public static final String[] LM_MODELS = { "gpt-3.5", "gpt-4" };
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_USER = "ROLE_USER";
    public static final String ERROR_JWT_INVALID_TOKEN = "Invalid JWT token.";
    public static final String ERROR_JWT_EXPIRED_TOKEN = "Expired JWT token.";
    public static final String ERROR_JWT_UNSUPPORTED_TOKEN = "Unsupported JWT token.";
    public static final String ERROR_JWT_CLAIMS_EMPTY = "JWT claims string is empty.";
    public static final String API_VERSION = "/api/v1";
    public static final String ERROR_API_DEFAULT_MESSAGE = "At least one error has occurred.";
    public static final String ERROR_API_USER_NOT_FOUND = "User not found.";
    public static final String ERROR_API_INVALID_USERNAME_OR_PASSWORD = "Invalid email or password.";
    public static final String ERROR_API_USER_EXIST = "User with this email already exist.";
    public static final String ERROR_API_ACCESS_DENIED = "Access denied.";
    public static final String ERROR_API_AUTHENTICATION_REQUIRED = "Authentication is required to access this resource.";
    public static final String MESSAGE_API_DEFAULT_MESSAGE = "The request was completed successfully.";
    public static final String MESSAGE_API_USER_LOGGED_IN_SUCCESSFUL = "User logged in successful.";
    public static final String MESSAGE_API_USER_CREATED_SUCCESSFUL = "User created successful.";
    public static final String MESSAGE_REFRESH_TOKEN_ROTATE = "Rotate refresh token.";
    public static final String ERROR_REFRESH_TOKEN_REVOKE_FAILED = "The refresh token could not be revoked.";
    public static final String MESSAGE_REFRESH_TOKEN_REUSE_REVOKED_TOKEN = "Attempted reuse of revoked ancestor refresh token.";
    public static final String ERROR_REFRESH_TOKEN_ROTATE_FAILED = "The refresh token could not be rotate.";
    public static final String ERROR_REFRESH_TOKEN_NOT_EXIST = "The refresh token not exist.";
    public static final String MESSAGE_REFRESH_TOKEN_USER_LOGOUT = "User logged out.";
    public static final String MESSAGE_API_USER_LOGOUT = "User logged out.";
    public static final String ERROR_REFRESH_TOKEN_COOKIE_EMPTY_OR_NULL = "The refresh token cookie is empty or null.";
    public static final String MESSAGE_REFRESH_TOKEN_ROTATED_SUCCESSFUL = "Refresh token rotated successful.";
    public static final String ERROR_API_ROLE_NOT_EXIST = "Role not exist.";
    public static final String ERROR_API_SOFT_DELETE_FAIL = "There was a problem when soft deleting entities.";
}
