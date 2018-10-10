import lib.Call;
import lib.Callback;
import lib.Url;

public interface RetrofitReflect {

    @Url("https://jsonplaceholder.typicode.com/todos/1")
    String doSomething();

    @Url("https://jsonplaceholder.typicode.com/posts")
    Call<String> loadPosts();
}
