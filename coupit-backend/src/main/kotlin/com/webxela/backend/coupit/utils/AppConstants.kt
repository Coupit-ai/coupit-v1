package com.webxela.backend.coupit.utils

object AppConstants {

    const val SESSION_EXPIRY = 15L // 15 minutes

    const val JWT_EXPIRY = 7L // 7 days
    const val JWT_SECRET = "7abc8cb43000d70e407ab34290b8ebc8569765063a0dd44c7ca32a4098cb6ebb1c2637219409b3927a06f4f9d706348f40451871f09a55a18d1295e7e086b6dc390ae5f10e4a1bf377970146b364161aa415a41e23abe40388d07ac51d6fa8b2073efe09422f767f99efafe6c5124f6a7ec8beea50c1e7d25372a6f93f9ae8fc8453ba95707a4c9aac426a1667a8c356878ff4d4d84375e7c85d837a2eec173960219f50ccbcd4295929d2569dff3e1ab27e0e93f1353803a4d0de158a6c19a471afec114d48e5180aecc641efe8695c3972eae9185462f6e0255220a4df420b1c5b91627a2c9e1de9d8c81301fe7309c14afeb32c3af529f53ce1ac04a521f0"
    const val JWT_ISSUER = "webxela"

    const val SQUARE_SANDBOX_URI = "https://connect.squareupsandbox.com"
    const val SQUARE_PROD_URI = "https://connect.squareup.com"
    val SQUARE_CLIENT_SCOPES = listOf("MERCHANT_PROFILE_READ", "PAYMENTS_READ")

    const val EMAIL_PATTERN = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"

    const val DEEPLINK_URI = "coupit://callback/oauth"

}