from fastapi import FastAPI

from app.api.routes import router

app = FastAPI(
    title="CommerceFlux Demand Forecast Service"
)

app.include_router(
    router,
    prefix="/api/v1/forecast"
)

@app.get("/health")
def health():
    return {"status": "UP"}
