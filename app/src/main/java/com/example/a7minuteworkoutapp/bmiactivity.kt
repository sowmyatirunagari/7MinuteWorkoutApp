package com.example.a7minuteworkoutapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.a7minuteworkoutapp.databinding.ActivityBmiactivityBinding
import java.math.BigDecimal
import java.math.RoundingMode

class bmiactivity : AppCompatActivity() {
    companion object{
        private const val METRIC_UNITS_VIEW="METRIC_UNIT_VIEW"
        private const val US_UNITS_VIEW="US_UNIT_VIEW"

    }
    private var currentvisibleview:String=
        METRIC_UNITS_VIEW


    private var binding:ActivityBmiactivityBinding?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBmiactivityBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbarBmiActivity)
        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = "CALCULATE BMI"
        }
        binding?.toolbarBmiActivity?.setNavigationOnClickListener {
            onBackPressed()
        }
        makevisiblemetricunitsview()


        binding?.rgunits?.setOnCheckedChangeListener { _, checkedId: Int ->

            if (checkedId == R.id.rbmetricunits) {
                makevisiblemetricunitsview()
            } else {
                makevisibleusunitsview()
            }
        }
        binding?.btnCalculateUnits?.setOnClickListener {
            calculateunits()


        }
    }







    private fun makevisiblemetricunitsview(){
            currentvisibleview= METRIC_UNITS_VIEW
            binding?.tilMetricUnitWeight?.visibility=View.VISIBLE
            binding?.tilMetricUnitHeight?.visibility=View.VISIBLE
            binding?.tilUsMetricUnitWeight?.visibility=View.GONE
            binding?.tilMetricUsUnitHeightFeet?.visibility=View.GONE
            binding?.tilMetricUsUnitHeightInch?.visibility=View.GONE

            binding?.etMetricUnitHeight?.text!!.clear()
            binding?.etMetricUnitWeight?.text!!.clear()

            binding?.llDiplayBMIResult?.visibility=View.INVISIBLE

    }
    private fun makevisibleusunitsview(){
        currentvisibleview= US_UNITS_VIEW
        binding?.tilMetricUnitWeight?.visibility=View.INVISIBLE
        binding?.tilMetricUnitHeight?.visibility=View.INVISIBLE
        binding?.tilUsMetricUnitWeight?.visibility=View.VISIBLE
        binding?.tilMetricUsUnitHeightFeet?.visibility=View.VISIBLE
        binding?.tilMetricUsUnitHeightInch?.visibility=View.VISIBLE

        binding?.etMetricUnitHeight?.text!!.clear()
        binding?.etMetricUnitWeight?.text!!.clear()

        binding?.llDiplayBMIResult?.visibility=View.INVISIBLE

    }




    private fun displaybmiresult(bmi:Float){

        val bmilabel:String
        val bmiDescription:String
        if(bmi.compareTo(15f)<=0){
            bmilabel="very severely underweight"
            bmiDescription="oops!take care of yourself!Eat more!"

        }
        else if(bmi.compareTo(15f) >0 && bmi.compareTo(16f)<=0){
            bmilabel="severely underweight"
            bmiDescription="oops!take care of yourself!Eat more!"
        }
        else if(bmi.compareTo(16f) >0 && bmi.compareTo(18.5f)<=0){
            bmilabel="underweight"
            bmiDescription="oops!take care of yourself!Eat more!"
        }
        else if(bmi.compareTo(18.5f) >0 && bmi.compareTo(25f)<=0){
            bmilabel="Normal"
            bmiDescription="congratulations!you are in a good shape"
        }
        else {
            bmilabel="obese"
            bmiDescription="omg!you are in very dangerous shape! act now"
        }
        val bmivalue=BigDecimal(bmi.toDouble()).setScale(2,RoundingMode.HALF_EVEN).toString()



        binding?.llDiplayBMIResult?.visibility= View.VISIBLE
        binding?.tvBMIValue?.text=bmivalue
        binding?.tvBMIType?.text=bmilabel
        binding?.tvBMIDescription?.text=bmiDescription
    }
    private fun validateusmetricunits():Boolean{
        var isValid=true
        when{
            binding?.etUsMetricUnitWeight?.text.toString().isEmpty()->{
                isValid=false
            }
            binding?.etUsMetricUnitHeightFeet?.text.toString().isEmpty()->{
                isValid=false

            }
            binding?.etUsMetricUnitHeightInch?.text.toString().isEmpty()->{
                isValid=false

            }
        }
        return isValid

    }
    private fun calculateunits(){
        if(currentvisibleview== METRIC_UNITS_VIEW){
            if (validatemetricunits()) {


                val heightValue: Float = binding?.etMetricUnitHeight?.text.toString().toFloat() / 100


                val weightValue: Float = binding?.etMetricUnitWeight?.text.toString().toFloat()


                val bmi = weightValue / (heightValue * heightValue)

                displaybmiresult(bmi)
            } else {
                Toast.makeText(this@bmiactivity, "Please enter valid values.", Toast.LENGTH_SHORT)
                    .show()
            }

        }
        else{
            if(validateusmetricunits()){
                val usunitheightvaluefeet:String=
                    binding?.etUsMetricUnitHeightFeet?.text.toString()
                val usunitheightvalueinch:String=
                    binding?.etUsMetricUnitHeightInch?.text.toString()
                val usunitweightvalue:Float=binding?.etUsMetricUnitWeight?.text.toString().toFloat()


                val heightvalue=
                    usunitheightvalueinch.toFloat()+usunitheightvaluefeet.toFloat() *12

                val bmi=703*(usunitweightvalue/(heightvalue*heightvalue))

                displaybmiresult(bmi)



            }else {
                Toast.makeText(this@bmiactivity, "Please enter valid values.", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }




    private fun validatemetricunits():Boolean{
        var isValid=true
        if(binding?.etMetricUnitWeight?.text.toString().isEmpty()){
            isValid=false
        }
        else if(binding?.etMetricUnitHeight?.text.toString().isEmpty()){
            isValid=false

        }
        return isValid

    }
}