import joblib
import numpy as np

model = joblib.load(
    "app/model/pricing_model.pkl"
)

def calculate_dynamic_price(
    product_id,
    current_price,
    demand_level,
    competitor_price
):
    # Encode demand level
    demand_map = {"low": 0, "medium": 1, "high": 2}
    demand_encoded = demand_map.get(demand_level.lower(), 1)
    
    # Create features
    features = np.array([[
        product_id,
        current_price,
        demand_encoded,
        competitor_price
    ]])
    
    # Get prediction from model
    predicted_price = model.predict(features)
    
    return float(predicted_price[0])
