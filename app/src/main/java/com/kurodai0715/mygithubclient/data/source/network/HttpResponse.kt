package com.kurodai0715.mygithubclient.data.source.network

import com.kurodai0715.mygithubclient.R

enum class HttpResponse(val code: Int, private val messageResId: Int) {

    OK(200, R.string.response_normal),
    NOT_MODIFIED(304, R.string.response_normal),
    REQUIRES_AUTHENTICATION(401, R.string.response_unauthorized),
    FORBIDDEN(403, R.string.response_forbidden);

    companion object {
        /**
         * 文字列リソース ID に対応する文字列を返します.
         */
        fun getMessageResIdBy(code: Int): Int {
            for (item: HttpResponse in entries) {
                if (code == item.code) {
                    return item.messageResId
                }
            }
            return R.string.illegal_argument_exception
        }

    }

}