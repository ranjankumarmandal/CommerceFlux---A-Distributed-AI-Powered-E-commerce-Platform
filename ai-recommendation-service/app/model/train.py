import pandas as pd
import joblib
import numpy as np

from sklearn.ensemble import RandomForestClassifier
from sklearn.preprocessing import LabelEncoder

# Load training data
df = pd.read_csv("data/recommendation_data.csv")

# Features
X = df[[
    "customer_id",
    "product_category",
    "purchase_frequency",
    "avg_order_value",
    "days_since_last_purchase"
]]

# Target
y = df["product_id"]

# Encode categorical variables
le_category = LabelEncoder()
X["product_category"] = le_category.fit_transform(X["product_category"])

# Train model
model = RandomForestClassifier(n_estimators=100, random_state=42)
model.fit(X, y)

# Save model
joblib.dump(
    model,
    "app/model/recommendation_model.pkl"
)

# Save encoders
joblib.dump(
    le_category,
    "app/model/category_encoder.pkl"
)

print("Recommendation model trained successfully")
