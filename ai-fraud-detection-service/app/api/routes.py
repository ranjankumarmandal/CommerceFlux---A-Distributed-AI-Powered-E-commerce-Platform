from fastapi import APIRouter

from app.schemas.fraud_request import FraudRequest

from app.model.predictor import detect_fraud

router = APIRouter()

@router.post("/")
def detect_transaction_fraud(
    request: FraudRequest
):
    fraud_probability = detect_fraud(
        request.transaction_id,
        request.amount,
        request.customer_id,
        request.merchant_id,
        request.location,
        request.time_of_day,
        request.device_id
    )

    is_fraudulent = fraud_probability > 0.5
    
    # Determine risk score
    if fraud_probability < 0.3:
        risk_score = "LOW"
    elif fraud_probability < 0.6:
        risk_score = "MEDIUM"
    elif fraud_probability < 0.8:
        risk_score = "HIGH"
    else:
        risk_score = "CRITICAL"

    # Generate risk factors
    risk_factors = []
    if request.amount > 5000:
        risk_factors.append("High transaction amount")
    if request.time_of_day in ["night", "late_night"]:
        risk_factors.append("Unusual transaction time")
    if request.location not in ["US", "UK", "CA", "AU"]:
        risk_factors.append("International transaction")
    
    if not risk_factors:
        risk_factors.append("No significant risk factors detected")

    return {
        "transaction_id": request.transaction_id,
        "is_fraudulent": is_fraudulent,
        "fraud_probability": round(fraud_probability, 3),
        "risk_score": risk_score,
        "risk_factors": risk_factors
    }
