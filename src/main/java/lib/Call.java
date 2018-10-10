package lib;

public interface Call<T> {

    void enqueue(Callback<T> callback);
}
