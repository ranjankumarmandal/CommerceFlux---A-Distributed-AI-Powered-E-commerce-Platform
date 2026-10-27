import pandas as pd
import joblib
import numpy as np

from sklearn.ensemble import RandomForestRegressor
from sklearn.preprocessing import StandardScaler

# Load training data
df = pd.read_csv("data/demand_data.csv")

# Features
X = df[[
    "product_id",
    "historical_sales",
    "seasonality",
    "trend",
    "promotional_events"
]]

# Target
y = df["future_demand"]

# Scale features
scaler = StandardScaler()
X_scaled = scaler.fit_transform(X)

# Train model
model = RandomForestRegressor(n_estimators=100, random_state=42)
model.fit(X_scaled, y)

# Save model
joblib.dump(
    model,
    "app/model/forecast_model.pkl"
)

# Save scaler
joblib.dump(
    scaler,
    "app/model/scaler.pkl"
)

print("Demand forecast model trained successfully")
