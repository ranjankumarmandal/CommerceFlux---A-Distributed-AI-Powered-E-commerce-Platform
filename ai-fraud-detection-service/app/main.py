from fastapi import FastAPI

from app.api.routes import router

app = FastAPI(
    title="CommerceFlux Fraud Detection Service"
)

app.include_router(
    router,
    prefix="/api/v1/detect-fraud"
)

@app.get("/health")
def health():
    return {"status": "UP"}
