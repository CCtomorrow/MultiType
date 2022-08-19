package me.drakeet.multitype.sample.web.impl;

import android.app.Activity;
import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.Toast;

public class UMWebChromeClient extends WebChromeClient {

    private final Activity mContext;
    /**
     * 全屏容器界面
     */
    static class FullscreenHolder extends FrameLayout {

        public FullscreenHolder(Context ctx) {
            super(ctx);
            setBackgroundColor(ctx.getResources().getColor(android.R.color.black));
        }

        @Override
        public boolean onTouchEvent(MotionEvent evt) {
            return true;
        }
    }

    public UMWebChromeClient(Activity context) {
        mContext = context;
    }

    private String getString(int resId) {
        return mContext.getString(resId);
    }

    private void toastShort(String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }

    private void toastLong(String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_LONG).show();
    }

    private void saveImage() {
//        MPermissions.with(mContext)
//                .permission(UMJsCallJava.PERMISSIONS_STORAGE)
//                .setPermissionsDesc(mContext.getString(R.string.permission_storage))
//                .setPermissionsPurpose(mContext.getString(R.string.permission_storage_purpose))
//                .request(new OnPerCallback() {
//                    @Override
//                    public void onGranted(List<String> permissions, boolean all) {
//                        realSaveImage();
//                    }
//
//                    @Override
//                    public void onDenied(List<String> permissions, boolean never) {
//                        toastShort("保存图片需要给APP授予存储权限");
//                    }
//                });
    }

    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        super.onProgressChanged(view, newProgress);
    }

    @Override
    public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
        return super.onJsAlert(view, url, message, result);
    }

    @Override
    public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
        return super.onJsPrompt(view, url, message, defaultValue, result);
    }

    @Override
    public View getVideoLoadingProgressView() {
        FrameLayout frameLayout = new FrameLayout(mContext);
        frameLayout.setLayoutParams(new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT)
        );
        return frameLayout;
    }

}
