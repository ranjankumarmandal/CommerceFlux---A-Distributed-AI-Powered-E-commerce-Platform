from pydantic import BaseModel

class FraudRequest(BaseModel):
    transaction_id: str
    amount: float
    customer_id: int
    merchant_id: int
    location: str
    time_of_day: str
    device_id: str
