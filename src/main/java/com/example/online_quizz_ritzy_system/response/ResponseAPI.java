package com.example.online_quizz_ritzy_system.response;

public class ResponseAPI<T>{
    private int statusCode;
    private String message;
    private T data;

    public ResponseAPI(Builder<T> builder) {
        this.statusCode = builder.statusCode;
        this.message = builder.message;
        this.data = builder.data;
    }

    public static <T> Builder<T> builder() {
        return new Builder<>();
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

     public static class Builder<T>{
        private int statusCode;
        private String message;
        private T data;

        public Builder<T> statusCode(int statusCode) {
            this.statusCode = statusCode;
            return this;
        }
        public Builder<T> message(String message) {
            this.message = message;
            return this;
        }
        public Builder<T> data(T data){
            this.data = data;
            return this;
        }

        public ResponseAPI<T> build(){
            return new ResponseAPI<>(this);
        }

    }
}
