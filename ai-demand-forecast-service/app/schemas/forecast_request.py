from pydantic import BaseModel

class ForecastRequest(BaseModel):
    product_id: int
    days: int = 30
