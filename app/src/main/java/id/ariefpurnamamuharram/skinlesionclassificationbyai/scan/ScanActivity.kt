package id.ariefpurnamamuharram.skinlesionclassificationbyai.scan

import android.graphics.Bitmap
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.wonderkiln.camerakit.CameraKitImage
import id.ariefpurnamamuharram.skinlesionclassificationbyai.R
import id.ariefpurnamamuharram.skinlesionclassificationbyai.tensorflow.Classifier
import id.ariefpurnamamuharram.skinlesionclassificationbyai.tensorflow.TFImageClassifier
import kotlinx.android.synthetic.main.activity_scan.*
import kotlinx.android.synthetic.main.element_results.*
import kotlinx.android.synthetic.main.element_scan.*
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.launch

class ScanActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "ScanActivity"
        private const val INPUT_SIZE = 224
    }

    private lateinit var modelPath: String
    private lateinit var labelPath: String

    private var classifier: Classifier? = null
    private var initializeJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan)
        element_scan.visibility = View.VISIBLE
        element_results.visibility = View.GONE
        modelPath = intent.getStringExtra("modelPath")
        labelPath = intent.getStringExtra("labelPath")
        initializeTensorClassifier()
        btnTakePicture.setOnClickListener { _ ->
            setVisibilityOnCaptured(false)
            camera.captureImage {
                onImageCaptured(it)
            }
        }
    }

    private fun onImageCaptured(it: CameraKitImage) {
        val bitmap = Bitmap.createScaledBitmap(it.bitmap, INPUT_SIZE, INPUT_SIZE, false)
        showCapturedImage(bitmap)
        classifier?.let {
            try {
                showRecognizedResult(it.recognizeImage(bitmap))
            } catch (e: java.lang.RuntimeException) {
                Log.e(TAG, "Crashing due to classification.closed() before the recognizer finishes!")
            }
        }
    }

    private fun showRecognizedResult(results: MutableList<Classifier.Recognition>) {
        runOnUiThread {
            setVisibilityOnCaptured(true)
            if (results.isEmpty()) {
                most_probable_lesion.text = getString(R.string.nothing_found)
                most_probable_confidence_level.text = getString(R.string.none)
            } else {
                val skinLesion = results[0].title
                val confidence = results[0].confidence
                most_probable_lesion.text = skinLesion
                most_probable_confidence_level.text = confidence.toString()
            }
        }
    }

    private fun showCapturedImage(bitmap: Bitmap?) {
        runOnUiThread {
            img_preview.visibility = View.VISIBLE
            img_preview.setImageBitmap(bitmap)
        }
    }

    private fun setVisibilityOnCaptured(isDone: Boolean) {
        btnTakePicture.isEnabled = isDone
        if (isDone) {
            element_scan.visibility = View.GONE
            element_results.visibility = View.VISIBLE
            progressBar.visibility = View.GONE
            img_preview.visibility = View.VISIBLE
            most_probable_lesion.visibility = View.VISIBLE
            most_probable_confidence_level.visibility = View.VISIBLE
        } else {
            element_scan.visibility = View.VISIBLE
            element_results.visibility = View.GONE
            progressBar.visibility = View.VISIBLE
            img_preview.visibility = View.GONE
            most_probable_lesion.visibility = View.GONE
            most_probable_confidence_level.visibility = View.GONE
        }
    }

    private fun initializeTensorClassifier() {
        initializeJob = launch {
            try {
                classifier = TFImageClassifier.create(
                    assets, modelPath, labelPath, INPUT_SIZE
                )
                runOnUiThread {
                    btnTakePicture.isEnabled = true
                }
            } catch (e: Exception) {
                throw RuntimeException("Error initializing TensorFlow!", e)
            }
        }
    }

    private fun clearTensorClassifier() {
        initializeJob?.cancel()
        classifier?.close()
    }

    override fun onResume() {
        super.onResume()
        camera.start()
    }

    override fun onPause() {
        super.onPause()
        camera.stop()
    }

    override fun onDestroy() {
        super.onDestroy()
        clearTensorClassifier()
    }
}