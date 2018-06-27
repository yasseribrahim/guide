package com.azhar.university.guide.domain.interactors;

import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Yasser.Ibrahim on 6/27/2018.
 */

public abstract class BaseRxInteractor {
    protected final CompositeDisposable disposables = new CompositeDisposable();

    protected synchronized void prepare(Observable observable, BaseObserver observer) {
        observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void onDestroy() {
        if (disposables != null && !disposables.isDisposed()) {
            disposables.dispose();
        }
    }

    public abstract class BaseObserver<T> implements Observer<T> {
        private MainInteractor.CallbackStates callback;

        public BaseObserver(MainInteractor.CallbackStates callback) {
            this.callback = callback;
        }

        @Override
        public void onSubscribe(Disposable d) {
            Log.i("BaseObserver", "onSubscribe");
        }

        @Override
        public void onNext(T t) {
            Log.i("BaseObserver", "onNext");
            callback.hideProgress();
        }

        @Override
        public void onError(Throwable e) {
            Log.i("BaseObserver", "onError");
            callback.hideProgress();
        }

        @Override
        public void onComplete() {
            Log.i("BaseObserver", "onComplete");
            callback.hideProgress();
            callback = null;
        }
    }
}
