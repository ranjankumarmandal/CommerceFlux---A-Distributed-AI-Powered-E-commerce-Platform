from fastapi import APIRouter

from app.schemas.recommendation_request import RecommendationRequest

from app.model.predictor import get_recommendations

router = APIRouter()

@router.post("/")
def generate_recommendations(
    request: RecommendationRequest
):
    recommendations = get_recommendations(
        request.customer_id,
        request.num_recommendations
    )

    return {
        "customer_id": request.customer_id,
        "recommendations": recommendations,
        "count": len(recommendations)
    }
