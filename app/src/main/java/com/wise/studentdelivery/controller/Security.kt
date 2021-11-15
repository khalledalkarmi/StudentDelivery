package com.wise.studentdelivery.controller

import org.jetbrains.annotations.TestOnly

class Security {
    fun checkPassword(password: String): Boolean {
        var haveLater = false
        var haveSpecialChar = false
        var capitalChar = false
        if (password.length < 8) {
            return false
        }

        for (c in password) {
            if (c in 'a'..'z')
                haveLater = true
            else if (c in 'A'..'Z')
                capitalChar = true
            else if (c in '!'..'/' || c in ':'..'@' || c in '['..'`' || c in '{'..'~')
                haveSpecialChar = true
        }

        return haveLater && haveSpecialChar && capitalChar
    }
    //ss+123456

}
