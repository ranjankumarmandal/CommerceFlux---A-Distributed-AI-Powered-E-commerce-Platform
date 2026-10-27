from pydantic import BaseModel

class RecommendationRequest(BaseModel):
    customer_id: int
    num_recommendations: int = 10
