package com.globant.test.exceptions;

public class CelebrityNotFound extends Exception {

    public enum CelebrityMessage {

        CELEBRITY_NOT_FOUND("There is not no one celebrity"),
        SEVERAL("it can have several celebrities");

        private String message;

        CelebrityMessage(String message) {
            this.message = message;
        }

        public CelebrityNotFound build() {
            return new CelebrityNotFound(this.message);
        }

        public String getMessage() {
            return message;
        }
    }

    private CelebrityNotFound(String message) {
        super(message);
    }
}
