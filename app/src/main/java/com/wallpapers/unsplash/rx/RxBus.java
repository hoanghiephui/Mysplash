package com.wallpapers.unsplash.rx;

/**
 * Created by hoanghiep on 1/17/18.
 */

import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

/**
 * A class that allows callers to register {@link PublishSubject} objects by passing in the class
 * type that you wish to emit as an event. It is intended to be instantiated as a Singleton via
 * Dagger2.
 */
public class RxBus {
    private static final String TAG = RxBus.class.getSimpleName();
    /**
     * A threadsafe map of lists of {@link PublishSubject} objects, where their type is used as the
     * key for lookups.
     */
    @SuppressWarnings("WeakerAccess")
    @VisibleForTesting
    ConcurrentHashMap<Object, List<Subject>> subjectsMap = new ConcurrentHashMap<>();

    /**
     * Registers a new {@link PublishSubject} whose type matches the class {@code type} passed to
     * the method. Returns the PublishSubject so it can be subscribed to, events acted upon and
     * threading applied.
     *
     * @param type The class type of the events you wish to emit
     * @return A {@link PublishSubject} with type {@code type}
     */
    @SuppressWarnings("Java8MapApi")
    public <T> Observable<T> register(@NonNull Class<T> type) {
        List<Subject> subjects = subjectsMap.get(type);
        if (subjects == null) {
            subjects = new ArrayList<>();
            subjectsMap.put(type, subjects);
        }

        Subject<T> subject = PublishSubject.create();
        subjects.add(subject);

        return subject;
    }

    /**
     * Removes a {@link PublishSubject} of type {@code type} from the list of registered
     * PublishSubjects.
     *
     * @param type       The class type of the {@link PublishSubject} to be removed
     * @param observable An {@link Observable} of type {@code type} which is currently subscribed to
     *                   it's associated {@link PublishSubject}
     */
    @SuppressWarnings("SuspiciousMethodCalls")
    public void unregister(@NonNull Class type, @NonNull Observable observable) {
        List<Subject> subjects = subjectsMap.get(type);
        if (subjects != null) {
            subjects.remove(observable);

            if (subjects.isEmpty()) {
                subjectsMap.remove(type);
            }
        } else {
            Log.e(TAG, "unregister of type " + type.getSimpleName() + " failed, as no PublishSubject with a matching type was found");
        }
    }

    /**
     * Emits an event of type {@code type} to any registered {@link PublishSubject} objects with a
     * matching type.
     *
     * @param type    The class type of object to be emitted and of the {@link PublishSubject} to be
     *                emitted from
     * @param content The actual object to be emitted
     */
    @SuppressWarnings("unchecked")
    public void emitEvent(@NonNull Class type, @NonNull Object content) {
        List<Subject> subjects = subjectsMap.get(type);
        if (subjects != null && !subjects.isEmpty()) {
            for (Subject subject : subjects) {
                subject.onNext(content);
            }
        } else {
            Log.i(TAG, "emitEvent of type " + type.getSimpleName() + " failed, as no PublishSubject was registered");
        }
    }
}
