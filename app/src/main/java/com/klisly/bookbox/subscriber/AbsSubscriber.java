package com.klisly.bookbox.subscriber;

import android.content.Context;

import com.google.gson.JsonParseException;
import com.klisly.bookbox.BookBoxApplication;
import com.klisly.bookbox.BusProvider;
import com.klisly.bookbox.R;
import com.klisly.bookbox.ottoevent.ToLoginEvent;
import com.klisly.bookbox.utils.RxUtils;
import com.klisly.bookbox.widget.progress.ProgressCancelListener;
import com.klisly.bookbox.widget.progress.ProgressDialogHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;
import timber.log.Timber;

public abstract class AbsSubscriber<T> extends Subscriber<T> implements ProgressCancelListener {

    //对应HTTP的状态码
    private static final int INVALIDPARAM = 400; // 参数异常
    private static final int UNAUTHORIZED = 401; // 未授权
    private static final int FORBIDDEN = 403; // 无权限
    private static final int NOT_FOUND = 404;
    private static final int REQUEST_TIMEOUT = 408;
    private static final int INTERNAL_SERVER_ERROR = 500;
    private static final int BAD_GATEWAY = 502;
    private static final int SERVICE_UNAVAILABLE = 503;
    private static final int GATEWAY_TIMEOUT = 504;

    private ProgressDialogHandler mProgressDialogHandler;

    private boolean showProgress = true;
    //出错提示
    // private final String networkMsg;
    // private final String parseMsg;
    private final String unknownMsg = BookBoxApplication.getInstance().getString(R.string.unknown_error);

    public AbsSubscriber(Context context) {
        mProgressDialogHandler = new ProgressDialogHandler(context, this, true);
    }

    public AbsSubscriber(Context context , boolean isShowProgress) {
        showProgress = isShowProgress;
        if(showProgress){
            mProgressDialogHandler = new ProgressDialogHandler(context, this, true);
        }
    }


    @Override
    public void onError(Throwable e) {
        dismissProgressDialog();
        Throwable throwable = e;
        //获取最根源的异常
        Timber.e(e, "get data from server error");
        while(throwable.getCause() != null){
            e = throwable;
            throwable = throwable.getCause();
        }
        ApiException ex;
        if (e instanceof HttpException){             //HTTP错误
            HttpException httpException = (HttpException) e;
            Timber.i("url:"+httpException);
            ex = new ApiException(e, httpException.code());
            try {
                String str = httpException.response().errorBody().string();
                JSONObject jsonObject = new JSONObject(str);
                int status = jsonObject.getInt("status");
                String msg = jsonObject.getString("msg");
                ex.setMessage(msg);
                ex.setCode(status);
            } catch (IOException e1) {
                e1.printStackTrace();
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
            switch(httpException.code()){
                case UNAUTHORIZED:
                    BusProvider.getInstance().post(new ToLoginEvent());
                    onPermissionError(ex);          //权限错误，需要实现
                    break;
                case FORBIDDEN:
                case INVALIDPARAM:
                case NOT_FOUND:
                case REQUEST_TIMEOUT:
                    onError(ex);
                    break;
                case GATEWAY_TIMEOUT:
                case INTERNAL_SERVER_ERROR:
                case BAD_GATEWAY:
                case SERVICE_UNAVAILABLE:
                default:
                    ex.setMessage(BookBoxApplication.getInstance().getString(R.string.net_error));  //均视为网络错误
                    onError(ex);
                    break;
            }
        } else if (e instanceof SocketTimeoutException
                || e instanceof ConnectException) {
            ex = new ApiException(e, ApiException.UNKNOWN);
            ex.setMessage(BookBoxApplication.getInstance().getString(R.string.net_disconnect));  //均视为网络错误
            onError(ex);
        }else if (e instanceof JsonParseException
                || e instanceof JSONException){
            ex = new ApiException(e, ApiException.PARSE_ERROR);
            ex.setMessage(BookBoxApplication.getInstance().getString(R.string.parse_error));            //均视为解析错误
            onError(ex);
        } else {
            ex = new ApiException(e, ApiException.UNKNOWN);
            ex.setMessage(unknownMsg);          //未知错误
            onError(ex);
        }
    }


    /**
     * 错误回调
     */
    protected abstract void onError(ApiException ex);

    /**
     * 权限错误，需要实现重新登录操作
     */
    protected abstract void onPermissionError(ApiException ex);

    @Override
    public void onStart() {
        super.onStart();
        showProgressDialog();
    }

    @Override
    public void onCompleted() {
        dismissProgressDialog();
    }



    private void showProgressDialog(){
        if (mProgressDialogHandler != null && showProgress) {
            mProgressDialogHandler.obtainMessage(ProgressDialogHandler.SHOW_PROGRESS_DIALOG).sendToTarget();
        }
    }

    private void dismissProgressDialog(){
        if (mProgressDialogHandler != null && showProgress) {
            mProgressDialogHandler.obtainMessage(ProgressDialogHandler.DISMISS_PROGRESS_DIALOG).sendToTarget();
        }
    }

    @Override
    public void onCancelProgress() {
        RxUtils.unsubscribe(this);
    }

}