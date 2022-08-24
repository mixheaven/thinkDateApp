package utils;

// TODO : comprendre cette interface
public interface ApiCallback {

    void fail(String json);
    void success(String json);
}
