package com.example.a7minuteworkoutapp

import android.app.Dialog
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a7minuteworkoutapp.databinding.ActivityExcerciseactivityBinding
import com.example.a7minuteworkoutapp.databinding.DialogcustombackconfirBinding
import java.util.Locale


class excerciseactivity : AppCompatActivity() ,TextToSpeech.OnInitListener{
    private var binding:ActivityExcerciseactivityBinding?=null

    private var restTimer:CountDownTimer?=null
    private var restProgess=0
    private var restTimerDuration:Long=1

    private var excerciseTimer:CountDownTimer?=null
    private var excerciseProgess=0
    private var excerciseTimerDuration:Long=1

    private var excerciselist:ArrayList<excercise>?=null
    private var currexcercise=-1

    private var tts:TextToSpeech?=null
    private var player:MediaPlayer?=null

    private var excerciseadapter :Excercisestatusadapter?=null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityExcerciseactivityBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbar)
        //backbutton at the toolbar
        if(supportActionBar!=null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        binding?.toolbar?.setNavigationOnClickListener{
            customDialogForBackButton()
        }


        excerciselist=constants.defaultExcerciseList()

        tts=TextToSpeech(this,this)

        //backbotton inthe app

        setupRestView()
        setupExcerciseStatusRecyclerView()
    }

    override fun onBackPressed() {
        customDialogForBackButton()

    }
    private fun customDialogForBackButton(){
        val customDialog=Dialog(this)
        val dialogBinding=DialogcustombackconfirBinding.inflate(layoutInflater)
        customDialog.setContentView(dialogBinding.root)
        customDialog.setCanceledOnTouchOutside(false)
        dialogBinding.btnYes.setOnClickListener{
            this@excerciseactivity.finish()
            customDialog.dismiss()

        }
        dialogBinding.btnNo.setOnClickListener{
            customDialog.dismiss()


        }
        customDialog.show()
    }


    private fun setupExcerciseStatusRecyclerView(){
        binding?.rvexcercisestatus?.layoutManager=
            LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)

        excerciseadapter =Excercisestatusadapter(excerciselist!!)
        binding?.rvexcercisestatus?.adapter=excerciseadapter
    }
    private fun setupRestView(){

        try{
            val soundURI=Uri.parse("android.resource://com.example.a7minuteworkoutapp/"+R.raw.startaudio)
            player=MediaPlayer.create(applicationContext,soundURI)
            player?.isLooping=false
            player?.start()
        }catch(e:Exception){
            e.printStackTrace()

        }

        binding?.flrestview?.visibility=View.VISIBLE
        binding?.tvtitle?.visibility=View.VISIBLE
        binding?.tvexcercisename?.visibility=View.INVISIBLE
        binding?.flexcerciseView?.visibility=View.INVISIBLE
        binding?.ivimage?.visibility=View.INVISIBLE
        binding?.tvupcoming?.visibility=View.VISIBLE
        binding?.tvupcomingexcercisename?.visibility=View.VISIBLE
        if(restTimer!=null){
            restTimer?.cancel()
            restProgess=0
        }

        binding?.tvupcomingexcercisename?.text=excerciselist!![currexcercise+1].getName()

        setrestProgressBar()

    }
    private fun setupexcerciseView(){
        binding?.flrestview?.visibility=View.INVISIBLE
        binding?.tvtitle?.visibility=View.INVISIBLE
        binding?.tvexcercisename?.visibility=View.VISIBLE
        binding?.flexcerciseView?.visibility=View.VISIBLE
        binding?.ivimage?.visibility=View.VISIBLE
        binding?.tvupcoming?.visibility=View.INVISIBLE
        binding?.tvupcomingexcercisename?.visibility=View.INVISIBLE

        if(excerciseTimer!=null){
            excerciseTimer?.cancel()
            excerciseProgess=0
        }

        speakOut(excerciselist!![currexcercise].getName())

        binding?.ivimage?.setImageResource(excerciselist!![currexcercise].getImage())
        binding?.tvexcercisename?.text=excerciselist!![currexcercise].getName()


        setexcerciseProgressBar()

    }


    private fun setrestProgressBar(){
        binding?.progressbar?.progress=restProgess
        restTimer= object:CountDownTimer(restTimerDuration*10000 , 1000){
            override fun onTick(p0: Long) {
                restProgess++
                 binding?.progressbar?.progress=10-restProgess
                 binding?.timer?.text=(10-restProgess).toString()
            }
            override fun onFinish() {
                currexcercise++

                excerciselist!![currexcercise].setIsSelected(true)
                excerciseadapter!!.notifyDataSetChanged()

                setupexcerciseView()
            }

        } .start()
    }
    private fun setexcerciseProgressBar(){
        binding?.progressbarexcercise?.progress=excerciseProgess
        excerciseTimer= object:CountDownTimer(excerciseTimerDuration*30000, 1000){
            override fun onTick(p0: Long) {
                excerciseProgess++
                binding?.progressbarexcercise?.progress=30-excerciseProgess
                binding?.tvtimerexcercise?.text=(30-excerciseProgess).toString()
            }
            override fun onFinish() {


                if(currexcercise<excerciselist?.size!!-1){
                    excerciselist!![currexcercise].setIsSelected(false)
                    excerciselist!![currexcercise].setIsCompleted(true)
                    excerciseadapter!!.notifyDataSetChanged()
                    setupRestView()
                }else{
                    finish()
                    val intent=Intent(this@excerciseactivity,finishactivity::class.java)
                    startActivity(intent)
                }
            }

        } .start()
    }

    override fun onDestroy() {
        super.onDestroy()

        if(restTimer!=null){
            restTimer?.cancel()
            restProgess=0
        }
        if(excerciseTimer!=null){
            excerciseTimer?.cancel()
            excerciseProgess=0
        }

        if(tts!=null){
            tts!!.stop()
            tts!!.shutdown()
        }
        if(player!=null){
            player!!.stop()
        }

        binding=null
    }

    override fun onInit(status: Int) {
        if(status==TextToSpeech.SUCCESS){
            val result= tts?.setLanguage(Locale.US)

            if(result==TextToSpeech.LANG_MISSING_DATA||result==TextToSpeech.LANG_NOT_SUPPORTED){
                Log.e("TTS","The language specified is not supported")
            }
        }else{
            Log.e("TTS","initialisztion Failed!")
        }
    }

   private fun speakOut(text:String){
       tts!!.speak(text,TextToSpeech.QUEUE_FLUSH, null, "")
   }
}