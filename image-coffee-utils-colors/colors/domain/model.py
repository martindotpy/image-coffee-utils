from pydantic import BaseModel


class MostCommonColors(BaseModel):
    """Most common colors model."""

    order: int
    percentage: float
    color: str
