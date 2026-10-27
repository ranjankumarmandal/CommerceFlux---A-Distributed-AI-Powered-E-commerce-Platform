import pandas as pd
import joblib
import numpy as np

from sklearn.ensemble import RandomForestRegressor
from sklearn.preprocessing import LabelEncoder

# Load training data
df = pd.read_csv("data/pricing_data.csv")

# Features
X = df[[
    "product_id",
    "current_price",
    "demand_level",
    "competitor_price"
]]

# Target
y = df["optimal_price"]

# Encode demand level
le_demand = LabelEncoder()
X["demand_level"] = le_demand.fit_transform(X["demand_level"])

# Train model
model = RandomForestRegressor(n_estimators=100, random_state=42)
model.fit(X, y)

# Save model
joblib.dump(
    model,
    "app/model/pricing_model.pkl"
)

# Save encoder
joblib.dump(
    le_demand,
    "app/model/demand_encoder.pkl"
)

print("Dynamic pricing model trained successfully")
