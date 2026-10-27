import joblib
import numpy as np

model = joblib.load(
    "app/model/recommendation_model.pkl"
)

def get_recommendations(
    customer_id,
    num_recommendations
):
    # Get customer purchase history features
    features = np.array([[customer_id]])
    
    # Get predictions from model
    predictions = model.predict(features)
    
    # Return top N recommendations
    recommendations = predictions[:num_recommendations]
    
    return recommendations.tolist()
