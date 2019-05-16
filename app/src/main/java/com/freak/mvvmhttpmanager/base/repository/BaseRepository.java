package com.freak.mvvmhttpmanager.base.repository;

import com.freak.httpmanager.AbstractRepository;
import com.freak.httpmanager.HttpMethods;
import com.freak.httpmanager.event.LiveBus;
import com.freak.mvvmhttpmanager.app.ApiServer;

/**
 * @author Freak
 * @date 2019/5/15.
 */

public class BaseRepository extends AbstractRepository {
    protected ApiServer apiService;


    public BaseRepository() {
        if (null == apiService) {
            apiService = HttpMethods.getInstance().create(ApiServer.class);
        }
    }


    protected void postData(Object eventKey, Object t) {
        postData(eventKey, null, t);
    }


    protected void showPageState(Object eventKey, String state) {
        postData(eventKey, state);
    }

    protected void showPageState(Object eventKey, String tag, String state) {
        postData(eventKey, tag, state);
    }

    protected void postData(Object eventKey, String tag, Object t) {
        LiveBus.getDefault().postEvent(eventKey, tag, t);
    }
}
