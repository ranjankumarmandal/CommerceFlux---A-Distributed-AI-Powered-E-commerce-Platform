import joblib
import numpy as np

model = joblib.load(
    "app/model/forecast_model.pkl"
)

def predict_demand(
    product_id,
    days
):
    # Generate features for prediction
    features = np.array([[product_id]])
    
    # Get predictions from model
    predictions = model.predict(features)
    
    # Return forecast for specified days
    forecast = predictions[:days]
    
    return forecast.tolist()
