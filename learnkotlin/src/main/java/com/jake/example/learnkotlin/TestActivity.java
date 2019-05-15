package com.jake.example.learnkotlin;


import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;

/**
 * @author
 * @version 1.0
 * @date 19-2-18
 */
public class TestActivity   {
    public static void main(String[] args) {

//        Test1.INSTANCE.sayHi("hello");
//        System.out.print("nihao");
        final String a[] = new String[]{"4", "0", "7", "i", "f", "w", "0", "9"};
        final Integer[] index = new Integer[]{5, 3, 9, 4, 8, 3, 1, 9, 2, 1, 7};

        Observable.just(index)
        .flatMap(new Function<Integer[], ObservableSource<Integer>>() {
            @Override
            public ObservableSource<Integer> apply(Integer[] integers) throws Exception {
                return Observable.fromArray(integers);
            }
        })
        .filter(new Predicate<Integer>() {
            @Override
            public boolean test(Integer integer) throws Exception {
                return integer< a.length;
            }
        })
        .map(new Function<Integer, String>() {
            @Override
            public String apply(Integer integer) {
                return a[integer];
            }
        })
        .reduce(new BiFunction<String, String, String>() {
            @Override
            public String apply(String s, String s2) throws Exception {
                return s+s2;
            }
        })
        .subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                System.out.print("密码是"+s);
            }
        })
        ;
        
    }
}
