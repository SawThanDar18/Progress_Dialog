package myapp.com.progress_dialog

import android.app.ProgressDialog
import android.os.Handler
import android.os.Message
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class Example : AppCompatActivity() {
    private lateinit var b1: Button
    private lateinit var b2: Button
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.example)

        b1 = findViewById(R.id.button)
        b2 = findViewById(R.id.button2)

        b1.setOnClickListener {
            progressDialog = ProgressDialog(this@Example)
            progressDialog.setMessage("Loading...") // Setting Message
            progressDialog.setTitle("ProgressDialog") // Setting Title
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER) // Progress Dialog Style Spinner
            progressDialog.show() // Display Progress Dialog
            progressDialog.setCancelable(false)
            Thread(Runnable {
                try {
                    Thread.sleep(1000)
                } catch (e: Exception) {
                    e.printStackTrace()
                }

                progressDialog.dismiss()
            }).start()
        }

        b2.setOnClickListener(object : View.OnClickListener {
            var handle: Handler = object : Handler() {
                override fun handleMessage(msg: Message) {
                    super.handleMessage(msg)
                    progressDialog.incrementProgressBy(2) // Incremented By Value 2
                }
            }

            override fun onClick(v: View) {
                progressDialog = ProgressDialog(this@Example)
                progressDialog.max = 100 // Progress Dialog Max Value
                progressDialog.setMessage("Loading...") // Setting Message
                progressDialog.setTitle("ProgressDialog") // Setting Title
                progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL) // Progress Dialog Style Horizontal
                progressDialog.show() // Display Progress Dialog
                progressDialog.setCancelable(false)
                Thread(Runnable {
                    try {
                        while (progressDialog.progress <= progressDialog.max) {
                            Thread.sleep(200)
                            handle.sendMessage(handle.obtainMessage())
                            if (progressDialog.progress == progressDialog.max) {
                                progressDialog.dismiss()
                            }
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }).start()
            }
        })
    }
}
