import requests
import uuid
import json

def send_payment_webhook(merchant_id):
    url = "https://api.coupit.ai/v1/square/webhook/payment"
    
    payment_id = str(uuid.uuid4())  # Generate a random payment ID
    
    payload = {
        "merchant_id": merchant_id,
        "type": "payment.updated",
        "event_id": "6a8f5f28-54a1-4eb0-a98a-3111513fd4fc",
        "created_at": "2020-02-06T21:27:34Z",
        "data": {
            "type": "payment",
            "id": "hgjgjhgjg89989986gj",
            "object": {
                "payment": {
                    "amount_money": {"amount": 100, "currency": "USD"},
                    "approved_money": {"amount": 100, "currency": "USD"},
                    "card_details": {
                        "avs_status": "AVS_ACCEPTED",
                        "card": {
                            "bin": "540988",
                            "card_brand": "MASTERCARD",
                            "card_type": "CREDIT",
                            "exp_month": 11,
                            "exp_year": 2022,
                            "fingerprint": "sq-1-Tvruf3vPQxlvI6n0IcKYfBukrcv6IqWr8UyBdViWXU2yzGn5VMJvrsHMKpINMhPmVg",
                            "last_4": "9029",
                            "prepaid_type": "NOT_PREPAID"
                        },
                        "card_payment_timeline": {
                            "authorized_at": "2020-11-22T21:16:51.198Z",
                            "captured_at": "2020-11-22T21:19:00.832Z"
                        },
                        "cvv_status": "CVV_ACCEPTED",
                        "entry_method": "KEYED",
                        "statement_description": "SQ *DEFAULT TEST ACCOUNT",
                        "status": "CAPTURED"
                    },
                    "created_at": "2020-11-22T21:16:51.086Z",
                    "delay_action": "CANCEL",
                    "delay_duration": "PT168H",
                    "delayed_until": "27020-11-29T21:16:51.086Z",
                    "id": payment_id,
                    "location_id": "S8GWD59R9QB376",
                    "order_id": "03O3USaPaAaFnI6kkwB1JxGgBsUZY",
                    "receipt_number": "hYy9",
                    "receipt_url": "https://squareup.com/receipt/preview/hYy9pRFVxpDsO1FB05SunFWUe9JZY",
                    "risk_evaluation": {"created_at": "2020-11-22T21:16:51.198Z", "risk_level": "NORMAL"},
                    "source_type": "CARD",
                    "status": "COMPLETED",
                    "total_money": {"amount": 100, "currency": "USD"},
                    "updated_at": "2020-11-22T21:19:00.831Z",
                    "version_token": "bhC3b8qKJvNDdxqKzXaeDsAjS1oMFuAKxGgT32HbE6S6o"
                }
            }
        }
    }
    
    headers = {"Content-Type": "application/json"}
    response = requests.post(url, data=json.dumps(payload), headers=headers)
    
    print(f"Status Code: {response.status_code}")
    print(f"Response: {response.text}")

if __name__ == "__main__":
    merchant_id = input("Enter Merchant ID: ")
    send_payment_webhook(merchant_id)
