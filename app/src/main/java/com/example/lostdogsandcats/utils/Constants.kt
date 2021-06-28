package com.example.lostdogsandcats.utils

import java.util.regex.Pattern

const val USER_ID = "user_id"
const val EMAIL_ID = "email_id"

const val FB_PATH_ALL_PETS = "allpets"

val VALID_EMAIL_REGEX: Pattern = Pattern.compile(
    "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
        "\\@" + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
        "(" + "\\." + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" + ")+"
)