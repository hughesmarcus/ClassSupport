package com.supporter.marcus.classsupport.ui.donations


import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Editable
import android.text.Selection
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import com.supporter.marcus.classsupport.R
import com.supporter.marcus.classsupport.util.DateInputMask
import kotlinx.android.synthetic.main.fragment_add_donation.*
import org.koin.android.architecture.ext.viewModel
import java.text.SimpleDateFormat
import java.util.*


/**
 * A simple [Fragment] subclass.
 *
 */
class AddDonationFragment : Fragment() {
    private val viewModel by viewModel<AddDonationViewModel>()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_donation, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        edit_amount.addTextChangedListener(tw)
        DateInputMask(edit_date).listen()
        add_donation.setOnClickListener {
            val amount = edit_amount.text.toString().replace("$", "").toFloat()
            edit_amount.text
            edit_date.text.toString()
            edit_project.text
            val sdf = SimpleDateFormat("MM/dd/yyyy", Locale.US)
            val d = sdf.parse(edit_date.text.toString())
            Log.d("Date", d.toString())
            viewModel.addDonation(amount, d, edit_project.text.toString())
            val action = AddDonationFragmentDirections.next_action()
            NavHostFragment.findNavController(this).navigate(action)
        }
        add_cancel.setOnClickListener {
            val action = AddDonationFragmentDirections.next_action()
            NavHostFragment.findNavController(this).navigate(action)
        }
    }

    var tw: TextWatcher = object : TextWatcher {

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun afterTextChanged(s: Editable) {

            if (!s.toString().matches("^\\$(\\d{1,3}(\\,\\d{3})*|(\\d+))(\\.\\d{2})?$".toRegex())) {
                val userInput = "" + s.toString().replace("[^\\d]".toRegex(), "")
                val cashAmountBuilder = StringBuilder(userInput)

                while (cashAmountBuilder.length > 3 && cashAmountBuilder[0] == '0') {
                    cashAmountBuilder.deleteCharAt(0)
                }
                while (cashAmountBuilder.length < 3) {
                    cashAmountBuilder.insert(0, '0')
                }
                cashAmountBuilder.insert(cashAmountBuilder.length - 2, '.')

                edit_amount.removeTextChangedListener(this)
                edit_amount.setText(cashAmountBuilder.toString())

                edit_amount.setTextKeepState("$" + cashAmountBuilder.toString())
                Selection.setSelection(edit_amount.text, cashAmountBuilder.toString().length + 1)

                edit_amount.addTextChangedListener(this)
            }
        }
    }



}
