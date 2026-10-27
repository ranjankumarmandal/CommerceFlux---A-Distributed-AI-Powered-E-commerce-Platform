import pandas as pd
import joblib
import numpy as np

from sklearn.ensemble import RandomForestClassifier
from sklearn.preprocessing import LabelEncoder

# Load training data
df = pd.read_csv("data/fraud_data.csv")

# Features
X = df[[
    "amount",
    "customer_id",
    "merchant_id",
    "location",
    "time_of_day"
]]

# Target
y = df["is_fraud"]

# Encode categorical variables
le_location = LabelEncoder()
X["location"] = le_location.fit_transform(X["location"])

le_time = LabelEncoder()
X["time_of_day"] = le_time.fit_transform(X["time_of_day"])

# Train model
model = RandomForestClassifier(n_estimators=100, random_state=42)
model.fit(X, y)

# Save model
joblib.dump(
    model,
    "app/model/fraud_detection_model.pkl"
)

# Save encoders
joblib.dump(
    le_location,
    "app/model/location_encoder.pkl"
)

joblib.dump(
    le_time,
    "app/model/time_encoder.pkl"
)

print("Fraud detection model trained successfully")
