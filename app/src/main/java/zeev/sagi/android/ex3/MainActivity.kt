package zeev.sagi.android.ex3

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import zeev.sagi.android.ex3.dataclass.contact
import zeev.sagi.android.ex3.fragments.bff_fragment
import zeev.sagi.android.ex3.fragments.quote_fragment
import zeev.sagi.android.ex3.global_stuff.fragment_helper
import java.util.*


class MainActivity : AppCompatActivity() {

    /**
     * The [android.support.v4.view.PagerAdapter] that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * [android.support.v4.app.FragmentStatePagerAdapter].
     */
    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null
    @Suppress("PrivatePropertyName")

    companion object
    {
        @Suppress("unused")
        var bff:contact? = null
        lateinit var act:MainActivity // kotlin has some bugs with .getActivity
    }

    @Suppress("PrivatePropertyName")
    private var premission_array:Vector<String> = Vector()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        act = this // kotlin has some bgus with .getActivity
        /*
            premission subroutine
         */
        init_premission_array()

        if(!fragment_helper.check_premissions(premission_array,baseContext))
            fragment_helper.request_premissions(this,premission_array)
        else
            continue_init_mechanism()




    }

    @Suppress("FunctionName")
    private fun continue_init_mechanism()
    {
        setSupportActionBar(toolbar)
        /*
        hides top action bar for visibility
         */
        if(supportActionBar!=null)
            supportActionBar!!.hide()
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)

        // Set up the ViewPager with the sections adapter.
        container.adapter = mSectionsPagerAdapter

        container.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabs))
        tabs.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(container))
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray)
    {
        when (requestCode)
        {
            act.resources.getInteger(R.integer.premission_request_code) ->
            {

            // If request is cancelled, the result arrays are empty.
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                //restart_app()
                continue_init_mechanism()
                // permission was granted, yay! Do the
                // contacts-related task you need to do.
            }
            else
            {

                // permission denied, boo! Disable the
                // functionality that depends on this permission.
                //Toast.makeText(this@MainActivity, getString(R.string.permission_denied), Toast.LENGTH_SHORT).show()
                //finish()
            }
            return
            }
        }// other 'case' lines to check for other
        // permissions this app might request
    }

    /*
          restarts entire app after style change
*/
    /*
    private fun restart_app()
    {
        startActivity(Intent(this.baseContext, MainActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
    }
*/
    /*
        inits entire premission array
     */

    @Suppress("FunctionName")
    private fun init_premission_array()
    {
        premission_array.addElement(Manifest.permission.SEND_SMS)
        premission_array.addElement(Manifest.permission.READ_CONTACTS)
        premission_array.addElement(Manifest.permission.READ_CALL_LOG)
    }

    /**
     * A [FragmentPagerAdapter] that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    inner class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm)
    {

        override fun getItem(position: Int): Fragment =
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        when(position)
        {
            0->bff_fragment.newInstance()
            1->quote_fragment.newInstance()
            else->bff_fragment.newInstance()
        }




        override fun getCount(): Int {
            // Show 2 total pages.
            return 2
        }
    }

}
