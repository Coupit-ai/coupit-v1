package com.webxela.backend.coupit.api.dto


import com.fasterxml.jackson.annotation.JsonProperty

data class PaymentWebhookRequest(
    @JsonProperty("created_at")
    val createdAt: String?,
    @JsonProperty("data")
    val data: Data,
    @JsonProperty("event_id")
    val eventId: String?,
    @JsonProperty("merchant_id")
    val merchantId: String,
    @JsonProperty("type")
    val type: String?
) {
    data class Data(
        @JsonProperty("id")
        val id: String?,
        @JsonProperty("object")
        val objectX: Object,
        @JsonProperty("type")
        val type: String?
    ) {
        data class Object(
            @JsonProperty("payment")
            val payment: Payment
        ) {
            data class Payment(
                @JsonProperty("amount_money")
                val amountMoney: AmountMoney?,
                @JsonProperty("approved_money")
                val approvedMoney: ApprovedMoney?,
                @JsonProperty("card_details")
                val cardDetails: CardDetails?,
                @JsonProperty("created_at")
                val createdAt: String?,
                @JsonProperty("delay_action")
                val delayAction: String?,
                @JsonProperty("delay_duration")
                val delayDuration: String?,
                @JsonProperty("delayed_until")
                val delayedUntil: String?,
                @JsonProperty("id")
                val id: String,
                @JsonProperty("location_id")
                val locationId: String?,
                @JsonProperty("order_id")
                val orderId: String?,
                @JsonProperty("receipt_number")
                val receiptNumber: String?,
                @JsonProperty("receipt_url")
                val receiptUrl: String?,
                @JsonProperty("risk_evaluation")
                val riskEvaluation: RiskEvaluation?,
                @JsonProperty("source_type")
                val sourceType: String?,
                @JsonProperty("status")
                val status: String?,
                @JsonProperty("total_money")
                val totalMoney: TotalMoney?,
                @JsonProperty("updated_at")
                val updatedAt: String?,
                @JsonProperty("version_token")
                val versionToken: String?
            ) {
                data class AmountMoney(
                    @JsonProperty("amount")
                    val amount: Int?,
                    @JsonProperty("currency")
                    val currency: String?
                )

                data class ApprovedMoney(
                    @JsonProperty("amount")
                    val amount: Int?,
                    @JsonProperty("currency")
                    val currency: String?
                )

                data class CardDetails(
                    @JsonProperty("avs_status")
                    val avsStatus: String?,
                    @JsonProperty("card")
                    val card: Card?,
                    @JsonProperty("card_payment_timeline")
                    val cardPaymentTimeline: CardPaymentTimeline?,
                    @JsonProperty("cvv_status")
                    val cvvStatus: String?,
                    @JsonProperty("entry_method")
                    val entryMethod: String?,
                    @JsonProperty("statement_description")
                    val statementDescription: String?,
                    @JsonProperty("status")
                    val status: String?
                ) {
                    data class Card(
                        @JsonProperty("bin")
                        val bin: String?,
                        @JsonProperty("card_brand")
                        val cardBrand: String?,
                        @JsonProperty("card_type")
                        val cardType: String?,
                        @JsonProperty("exp_month")
                        val expMonth: Int?,
                        @JsonProperty("exp_year")
                        val expYear: Int?,
                        @JsonProperty("fingerprint")
                        val fingerprint: String?,
                        @JsonProperty("last_4")
                        val last4: String?,
                        @JsonProperty("prepaid_type")
                        val prepaidType: String?
                    )

                    data class CardPaymentTimeline(
                        @JsonProperty("authorized_at")
                        val authorizedAt: String?,
                        @JsonProperty("captured_at")
                        val capturedAt: String?
                    )
                }

                data class RiskEvaluation(
                    @JsonProperty("created_at")
                    val createdAt: String?,
                    @JsonProperty("risk_level")
                    val riskLevel: String?
                )

                data class TotalMoney(
                    @JsonProperty("amount")
                    val amount: String?,
                    @JsonProperty("currency")
                    val currency: String?
                )
            }
        }
    }
}