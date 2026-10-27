from pydantic import BaseModel

class PricingRequest(BaseModel):
    product_id: int
    current_price: float
    demand_level: str
    competitor_price: float
