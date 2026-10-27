from fastapi import APIRouter
from datetime import datetime, timedelta

from app.schemas.forecast_request import ForecastRequest

from app.model.predictor import predict_demand

router = APIRouter()

@router.post("/")
def generate_forecast(
    request: ForecastRequest
):
    demand_values = predict_demand(
        request.product_id,
        request.days
    )

    forecasts = []
    base_date = datetime.now()
    
    for i, demand in enumerate(demand_values):
        future_date = base_date + timedelta(days=i+1)
        forecasts.append({
            "date": future_date.strftime("%Y-%m-%d"),
            "predicted_demand": int(demand),
            "confidence_lower": int(demand * 0.85),
            "confidence_upper": int(demand * 1.15)
        })

    return {
        "product_id": request.product_id,
        "forecasts": forecasts,
        "count": len(forecasts)
    }
