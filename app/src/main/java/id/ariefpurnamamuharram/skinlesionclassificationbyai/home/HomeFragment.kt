package id.ariefpurnamamuharram.skinlesionclassificationbyai.home

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.ariefpurnamamuharram.skinlesionclassificationbyai.R
import id.ariefpurnamamuharram.skinlesionclassificationbyai.scanner.ScannerActivity
import kotlinx.android.synthetic.main.element_nav_cardview.view.*
import org.jetbrains.anko.support.v4.startActivity

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        val rootView = inflater.inflate(R.layout.fragment_home, container, false)

        rootView.btnScanAbdomen.setOnClickListener {
            startActivity<ScannerActivity>()
        }

        return rootView

    }

    companion object {
        fun newInstance(): HomeFragment = HomeFragment()
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
//    external fun stringFromJNI(): String
//
//    companion object {
//
//        // Used to load the 'native-lib' library on application startup.
//        init {
//            System.loadLibrary("native-lib")
//        }
//    }
}
