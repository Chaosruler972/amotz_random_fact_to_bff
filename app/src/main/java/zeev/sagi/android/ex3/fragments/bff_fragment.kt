@file:Suppress("ClassName")

package zeev.sagi.android.ex3.fragments



import android.Manifest
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_bff.*
import zeev.sagi.android.ex3.MainActivity
import zeev.sagi.android.ex3.R
import zeev.sagi.android.ex3.bff_finder
import zeev.sagi.android.ex3.global_stuff.fragment_helper


/**
 * A simple [Fragment] subclass.
 */
class bff_fragment : Fragment()
{

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? = inflater?.inflate(R.layout.fragment_bff, container, false)

    @Suppress("RedundantOverride")
    override fun onActivityCreated(savedInstanceState: Bundle?)
    {
        super.onActivityCreated(savedInstanceState)
        if (!fragment_helper.check_premission(Manifest.permission.READ_CALL_LOG, context) ||
                !fragment_helper.check_premission(Manifest.permission.READ_CONTACTS, context))
        {
            Toast.makeText(MainActivity.act,getString(R.string.permission_read_call_log_and_contacts_failed), Toast.LENGTH_SHORT).show()
            bff_name.text = getString(R.string.permission_read_call_log_and_contacts_failed)
            return
        }
        bff_find.setOnClickListener {
            bff_find.isEnabled = false
            bff_progressbar.visibility = ProgressBar.VISIBLE

            Thread {
                val result = bff_finder().compute_bff(context)
                if (result != null)
                    Log.d("Bff", result.toString())
                else
                {
                    Log.d("Bff", "Got nothing")
                    MainActivity.act.runOnUiThread {
                        bff_name.text = getString(R.string.couldnt_find_call_logs)
                    }
                    bff_progressbar.visibility = ProgressBar.INVISIBLE
                    return@Thread
                }
                MainActivity.bff = result
                MainActivity.act.runOnUiThread {
                    @Suppress("SENSELESS_COMPARISON")
                    if(result!=null)
                    {
                        bff_name.text = result.toString()
                        @Suppress("LocalVariableName")
                        val vector = fragment_helper.get_contact_by_id(result.phone_no,context)
                        Log.d("Bff","Result amount ${vector.size}")
                        if(vector.size != 0)
                        {
                            bff_pic.visibility = ImageView.VISIBLE
                            bff_pic.setImageURI(Uri.parse(vector.elementAt(0)))
                        }
                    }
                    bff_progressbar.visibility = ProgressBar.GONE
                }
            }.start()

        }
        bff_find.callOnClick()
    }

    companion object
    {
        fun newInstance(): bff_fragment = bff_fragment()
    }
}// Required empty public constructor
