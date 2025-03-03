package com.webxela.backend.coupit.rest.static

fun getOauthRedirectPage(redirectUri: String): String {
    return """
        <!DOCTYPE html>
        <html>
        <head>
            <script>
                window.location.href = "$redirectUri";
                setTimeout(function() {
                    window.close();
                }, 1000);
            </script>
        </head>
        <body>
            Redirecting...
        </body>
        </html>
    """.trimIndent()
}