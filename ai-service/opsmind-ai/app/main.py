from fastapi import FastAPI

app = FastAPI(
    title="OpsMind AI Service",
    description="AI and Machine Learning service for the OpsMind platform.",
    version="0.1.0",
)


@app.get("/health")
def health_check():
    return {
        "status": "UP",
        "service": "opsmind-ai",
    }