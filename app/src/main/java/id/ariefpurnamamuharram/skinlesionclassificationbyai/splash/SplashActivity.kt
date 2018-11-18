package id.ariefpurnamamuharram.skinlesionclassificationbyai.splash

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import id.ariefpurnamamuharram.skinlesionclassificationbyai.MainActivity
import id.ariefpurnamamuharram.skinlesionclassificationbyai.R
import org.jetbrains.anko.startActivity

class SplashActivity : AppCompatActivity() {
    private var mDelayHandler: Handler? = null
    private val splashDelay: Long = 3000 // 3 seconds

    private val mRunnable: Runnable = Runnable {
        if (!isFinishing) {
            startActivity<MainActivity>()
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Initialize the handler
        mDelayHandler = Handler()

        // Navigate with delay
        mDelayHandler!!.postDelayed(mRunnable, splashDelay)
    }

    public override fun onDestroy() {

        if (mDelayHandler != null) {
            mDelayHandler!!.removeCallbacks(mRunnable)
        }

        super.onDestroy()
    }
}