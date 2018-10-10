package lib;

public interface Callback<T> {

    void onSuccess(T t);

    void onError(String error);

}
