package com.example.online_quizz_ritzy_system.exception;

public class ErrorDetailsAPI{

    private String message;
    private String details;
    private Object data;

    public ErrorDetailsAPI(String message, String details, Object data) {
        this.message = message;
        this.details = details;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder{
        private String message;
        private String details;
        private Object data;

        public Builder details(String details) {
            this.details = details;
            return this;
        }
        public Builder message(String message) {
            this.message = message;
            return this;
        }
        public Builder data(Object data) {
            this.data = data;
            return this;
        }

        public ErrorDetailsAPI build() {
            return new ErrorDetailsAPI(message, details, data);
        }
    }
}
