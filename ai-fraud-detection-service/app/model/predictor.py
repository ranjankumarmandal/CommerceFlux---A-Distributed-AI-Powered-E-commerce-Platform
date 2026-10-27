import joblib
import numpy as np

model = joblib.load(
    "app/model/fraud_detection_model.pkl"
)

def detect_fraud(
    transaction_id,
    amount,
    customer_id,
    merchant_id,
    location,
    time_of_day,
    device_id
):
    # Encode categorical features
    location_map = {"US": 0, "UK": 1, "CA": 2, "AU": 3, "OTHER": 4}
    location_encoded = location_map.get(location, 4)
    
    # Encode time of day
    time_map = {"morning": 0, "afternoon": 1, "evening": 2, "night": 3}
    time_encoded = time_map.get(time_of_day.lower(), 1)
    
    # Create features
    features = np.array([[
        amount,
        customer_id,
        merchant_id,
        location_encoded,
        time_encoded
    ]])
    
    # Get prediction from model
    fraud_probability = model.predict_proba(features)[0][1]
    
    return float(fraud_probability)
