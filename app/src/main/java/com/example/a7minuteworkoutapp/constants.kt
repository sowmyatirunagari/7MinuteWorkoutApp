package com.example.a7minuteworkoutapp

object constants {

    fun defaultExcerciseList():ArrayList<excercise>{

        val excerciselist=ArrayList<excercise>()

        val jumpingjacks=excercise(
             1, "Jumping Jacks", R.drawable.ic_jumping_jacks, false, false
        )
        excerciselist.add(jumpingjacks)

        val wallsit=excercise(
            2, "Wall Sit", R.drawable.ic_wall_sit, false, false
        )
        excerciselist.add(wallsit)

        val pushup=excercise(
            3, "Push Up", R.drawable.ic_push_up, false, false
        )
        excerciselist.add(pushup)

        val abdominalcrunch=excercise(
            4, "Abdominal Crunch", R.drawable.ic_abdominal_crunch, false, false
        )
        excerciselist.add(abdominalcrunch)

        val stepuponchair=excercise(
            5, "Step Up On Chair", R.drawable.ic_step_up_onto_chair, false, false
        )
        excerciselist.add(stepuponchair)

        val squat=excercise(
            6, "Squat", R.drawable.ic_squat, false, false
        )
        excerciselist.add(squat)

        val tricepdiponchair=excercise(
            7, "Tricep Dip On Chair", R.drawable.ic_triceps_dip_on_chair, false, false
        )
        excerciselist.add(tricepdiponchair)

        val plank=excercise(
            8, "Plank", R.drawable.ic_plank, false, false
        )
        excerciselist.add(plank)

        val highkneesruninginplace=excercise(
            9, "Step Up On Chair", R.drawable.ic_high_knees_running_in_place,  false, false
        )
        excerciselist.add(highkneesruninginplace)

        val lunges=excercise(
            10, "Lunges", R.drawable.ic_lunge, false, false
        )
        excerciselist.add(lunges)

        val pushupandrotation=excercise(
            11, "Push Up And Rotation", R.drawable.ic_push_up_and_rotation, false, false
        )
        excerciselist.add(pushupandrotation)

        val sideplank=excercise(
            12, "Side Plank", R.drawable.ic_side_plank, false, false
        )
        excerciselist.add(sideplank)






        return excerciselist


    }
}