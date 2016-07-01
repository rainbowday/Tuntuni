/*
 * Copyright 2016 Sudipto Chandra.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.tuntuni.models;

/**
 * Lists some log messages used through-out the application.
 */
public abstract class Logs {

    public static final String //--- begin : log message list ---
            // Used in Server.java
            SERVER_BIND_FAILS = "Failed to bind server with port ",
            SERVER_BIND_SUCCESS = "Server is bound to port ",
            SERVER_LISTENING = "Waiting for clients.",
            SERVER_LISTENING_STOPPED = "Stopped waiting for clients.",
            SERVER_SELECT_FAILED = "Failed to select or process keys.",
            SERVER_CHANNEL_ACCEPT_FAILED = "Failed to accept client channel.",
            SERVER_CHANNEL_READ_FAILED = "Failed to read from client channel.",
            SERVER_CHANNEL_WRITE_FAILED = "Failed to write in client channel.",
            SERVER_CLOSING_SELECTOR = "Closing selector.",
            SERVER_CLOSING_FAILED = "Failed to stop server.",
            SERVER_CLOSING_SELECTOR_ERROR = "Failed to close selector",
            SERVER_CLOSING_CHANNEL_ERROR = "Failed to close a socket channel",
            //--- end : log message list ---
            FINALIZE = "Log finalized ";

}
