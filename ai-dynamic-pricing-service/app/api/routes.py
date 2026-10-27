from fastapi import APIRouter

from app.schemas.pricing_request import PricingRequest

from app.model.predictor import calculate_dynamic_price

router = APIRouter()

@router.post("/")
def calculate_price(
    request: PricingRequest
):
    recommended_price = calculate_dynamic_price(
        request.product_id,
        request.current_price,
        request.demand_level,
        request.competitor_price
    )

    price_change = ((recommended_price - request.current_price) / request.current_price) * 100

    return {
        "product_id": request.product_id,
        "current_price": request.current_price,
        "recommended_price": round(recommended_price, 2),
        "price_change_percentage": round(price_change, 2),
        "competitor_price": request.competitor_price,
        "demand_level": request.demand_level
    }
