@file:Suppress("ClassName")

package zeev.sagi.android.ex3.fragments




import android.os.Bundle
import android.support.v4.app.Fragment
import android.telephony.SmsManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import com.github.kittinunf.fuel.httpGet
import kotlinx.android.synthetic.main.fragment_quote.*
import org.json.JSONObject
import zeev.sagi.android.ex3.MainActivity
import zeev.sagi.android.ex3.R
import zeev.sagi.android.ex3.dataclass.quote


/**
 * A simple [Fragment] subclass.
 */
class quote_fragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? = inflater?.inflate(R.layout.fragment_quote, container, false)

    @Suppress("RedundantOverride", "UNUSED_VARIABLE")
    override fun onActivityCreated(savedInstanceState: Bundle?)
    {
        super.onActivityCreated(savedInstanceState)

        quote_generate.setOnClickListener {
            quote_generate.isEnabled = false // disable button
            val url = context.getString(R.string.random_quote_url)
            Log.d("Quote fragment","URL is : $url")
            quote_progressbar.visibility = ProgressBar.VISIBLE
            Thread{
                /*
                send request
                 */
                val (request, response, result) = url.httpGet().responseString() // result is Result<String, FuelError>
                /*
                    check response is good
                 */

                if(response.statusCode != 200)
                {
                    MainActivity.act.runOnUiThread {
                        Log.d("Quote fragment","response code is ${response.statusCode}")
                        Log.d("Quote fragment", "Result is ${result.get()}")
                        Toast.makeText(context,getString(R.string.URL_response_not_200),Toast.LENGTH_SHORT).show()
                        quote_generate.isEnabled = true
                        quote_progressbar.visibility = ProgressBar.GONE
                        quote_send_to_bff.isEnabled = false
                    }
                    return@Thread
                }

                /*
                    attempt json parsing
                 */
                try
                {
                    val json = JSONObject(String(response.data))
                    quote.author = json.getString(getString(R.string.json_author))
                    quote.text = json.getString(getString(R.string.json_text))
                    quote.category = json.getString(getString(R.string.json_category))
                }
                catch (e:Exception)
                {
                    MainActivity.act.runOnUiThread {
                        Log.d("Quote fragment","response is ${String(response.data)}")
                        Toast.makeText(context,getString(R.string.parsing_error),Toast.LENGTH_SHORT).show()
                        quote_generate.isEnabled = true
                        quote_progressbar.visibility = ProgressBar.GONE
                        quote_send_to_bff.isEnabled = false
                    }
                    return@Thread
                }
                /*
                change views
                 */
                MainActivity.act.runOnUiThread{
                    quote_text.text = quote.text
                    quote_author.text = quote.author
                    quote_generate.isEnabled = true
                    quote_progressbar.visibility = ProgressBar.GONE
                    if(MainActivity.bff!=null)
                        quote_send_to_bff.isEnabled = true
                }
            }.start()
        }

        quote_send_to_bff.setOnClickListener {
            val con = MainActivity.bff
            if(con!=null)
                sendSMS(con.phone_no,"Did you know that ${quote.text}? ${quote.author} said that about the category of ${quote.category}")
        }

    }


    @Suppress("unused")
    fun sendSMS(phoneNo: String, msg: String)
    {
        try
        {
            val smsManager = SmsManager.getDefault()
            smsManager.sendTextMessage(phoneNo, null, msg, null, null)
            Toast.makeText(context, getString(R.string.sms_message_sent),
                    Toast.LENGTH_LONG).show()
        }
        catch (ex: Exception)
        {
            Toast.makeText(context, ex.message.toString(),
                    Toast.LENGTH_LONG).show()
            ex.printStackTrace()
        }

    }
    companion object {
        fun newInstance(): quote_fragment = quote_fragment()
    }

}// Required empty public constructor
