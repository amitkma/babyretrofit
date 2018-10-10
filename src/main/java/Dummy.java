import lib.Call;
import lib.Callback;
import lib.Retrofit;

public class Dummy {
    public static void main(String[] args) {

        RetrofitReflect retrofitReflect = Retrofit.create(RetrofitReflect.class);
        Call<String> call = retrofitReflect.loadPosts();
        call.enqueue(new Callback<String>() {
            @Override
            public void onSuccess(String s) {
                System.out.println(s);
            }

            @Override
            public void onError(String error) {
                System.out.println(error);
            }
        });

    }

}
