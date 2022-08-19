package me.drakeet.multitype.sample.web.impl

import android.Manifest
import android.graphics.Bitmap
import android.os.*
import android.util.Log
import android.view.*
import android.webkit.PermissionRequest
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.widget.Button
import android.widget.ProgressBar
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.hjq.permissions.OnPermissionCallback
import com.hjq.permissions.XXPermissions
import me.drakeet.multitype.sample.R
import java.util.*

class UMWebFragment : Fragment() {

    companion object {
        private const val TAG = "UMWebFragment"
    }

    private lateinit var button: Button
    private lateinit var loadView: ProgressBar
    private lateinit var webview: UMWebView

    /**
     * 网页数据
     */
    private var inFaultHappen = false
    //private var jsjava: UMJsCallJava? = null

    private var mHandler: Handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            try {
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_web, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
        initData()
    }

    private fun initView(rootView: View) {
        rootView.apply {
            button = findViewById(R.id.req_storage)
            loadView = findViewById(R.id.progress_bar)
            loadView.max = 100
            webview = findViewById(R.id.id_webview)
        }
        button.setOnClickListener {
            loadUrl()
//            XXPermissions.with(this)
//                //.permission(UMJsCallJava.PERMISSIONS_STORAGE)
//                .permission(Manifest.permission.CAMERA)
//                .permission(Manifest.permission.RECORD_AUDIO)
//                .request(object : OnPermissionCallback {
//
//                    override fun onGranted(permissions: MutableList<String>?, all: Boolean) {
//                        if (all) {
//                            loadUrl()
//                        }
//                    }
//
//                    override fun onDenied(permissions: MutableList<String>?, never: Boolean) {
//                        Toast.makeText(activity, "拒绝了权限", Toast.LENGTH_SHORT).show()
//                    }
//
//                })
        }
    }

    private fun initData() {
        initChromeClient()
        //WebView配置
        val webviewController = UMWebViewController(webview)
        webviewController.setupWebView()
        webviewController.setWebViewClient(viewClient)
        webviewController.setWebChromeClient(chromeClient)
        //jsjava = UMJsCallJava(activity, mHandler, webview)
        //webviewController.addJavascriptInterface(jsjava, "H5ToNative")
    }

    private fun loadUrl() {
        /*
        val origin = "/index.html"
        val downloadDir =
            Environment.getExternalStoragePublicDirectory(DIRECTORY_DOWNLOADS)
        val destDir = File(downloadDir, "local_dir")
        var targetDir: File? = null
        if (destDir.isDirectory) {
            val listFiles = destDir.listFiles()
            if (listFiles != null && listFiles.isNotEmpty()) {
                listFiles[0].apply {
                    if (isDirectory) {
                        targetDir = this
                    }
                }
            }
        }
        // targetDir = /sdcard/Download/local_dir/ZIJhOHlVDwnJsEoSRPt
        // 还需要进入到index.bundle文件夹 + origin
        targetDir?.apply {
            val finalDest = File(this, "index.bundle").absolutePath + origin
            Log.e(TAG, "finalDest:$finalDest")
            val index: Int = finalDest.indexOf(DIRECTORY_DOWNLOADS)
            if (index > 0) {
                val url = "file:///sdcard/" + finalDest.substring(index)
                webview.loadUrl(url)
            }
        }*/
        webview.loadUrl("https://www.itisyue.fun/#video")
    }

    override fun onResume() {
        super.onResume()
        webview.onResume()
    }

    override fun onPause() {
        super.onPause()
        webview.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        val parent: ViewParent = webview.parent
        (parent as ViewGroup).removeView(webview)
        webview.stopLoading()
        webview.removeJavascriptInterface("H5ToNative")
        // 退出时调用此方法，移除绑定的服务，否则某些特定系统会报错
        webview.settings.javaScriptEnabled = false
        webview.clearView()
        webview.removeAllViews()
        try {
            webview.destroy()
        } catch (ex: Throwable) {
            ex.printStackTrace()
        }
//        jsjava?.destory()
//        jsjava = null
    }

    fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (webview.canGoBack()) {
            webview.goBack()
            return true
        }
        return false
    }

    private val viewClient = object : UMWebViewClient(activity) {

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            Log.d(TAG, "onPageStarted")
            loadView.progress = 0
            inFaultHappen = false
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            Log.d(TAG, "onPageFinished")
            loadView.progress = 100
            loadView.visibility = View.GONE
        }

        override fun onReceivedError(
            view: WebView?,
            request: WebResourceRequest?,
            error: WebResourceError?
        ) {
            Log.d(TAG, "onReceivedError")
            super.onReceivedError(view, request, error)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (error!!.errorCode == -2 || error.errorCode == -1) {
                    return
                }
            }
        }

    }

    private fun initChromeClient() {
        chromeClient = chromeClient ?: object : UMWebChromeClient(activity) {

            override fun onReceivedTitle(view: WebView?, title: String?) {
                super.onReceivedTitle(view, title)
            }

            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                loadView.progress = newProgress
            }

//            override fun onJsAlert(
//                view: WebView?,
//                url: String?,
//                message: String?,
//                result: JsResult?
//            ): Boolean {
//                result?.confirm()
//                return true
//            }

            @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
            override fun onPermissionRequest(request: PermissionRequest) {
                Log.e(TAG, "onPermissionRequest:${Arrays.toString(request.resources)}")
                //直接把申请权限的代码提前到点击按钮的时候，这里直接通过
                //request.grant(request.resources)
                val list = arrayListOf<String>()
                request.resources.forEach {
                    if (it == PermissionRequest.RESOURCE_AUDIO_CAPTURE) {
                        list.add(Manifest.permission.CAMERA)
                    }
                    if (it == PermissionRequest.RESOURCE_VIDEO_CAPTURE) {
                        list.add(Manifest.permission.CAMERA)
                        list.add(Manifest.permission.RECORD_AUDIO)
                    }
                }
                XXPermissions.with(activity)
                    .permission(list)
                    .request(object : OnPermissionCallback {
                        override fun onGranted(permissions: MutableList<String>?, all: Boolean) {
                            request.grant(request.resources)
                        }

                        override fun onDenied(permissions: MutableList<String>?, never: Boolean) {
                        }
                    })
            }
        }
    }

    private var chromeClient: UMWebChromeClient? = null
}