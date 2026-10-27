from fastapi import FastAPI

from app.api.routes import router

app = FastAPI(
    title="CommerceFlux Recommendation Service"
)

app.include_router(
    router,
    prefix="/api/v1/recommendations"
)

@app.get("/health")
def health():
    return {"status": "UP"}
