package com.devmina.droid_ant.getdata




enum class YoutubeApiStatus { LOADING, ERROR, DONE }

/**
 * update the date format
 */
fun UpdateDate(date:String):String{

    var newDate=""
    var i=0
    do{
        newDate+=date[i]
        i++
    }while (i<10)

    return newDate
}

/**
 * convert number like 2000 to 2k
 */

fun UpdateNumber(number:Int):String {
    var numberS=""
    if (Math.abs(number / 1000000) > 1) {
        numberS = (number / 1000000).toString() + "m";

    } else if (Math.abs(number / 1000) > 1) {
        numberS = (number / 1000).toString() + "k";

    } else {
        numberS = number.toString();


    }
    return numberS
}

