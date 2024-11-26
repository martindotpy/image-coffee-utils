from __future__ import annotations

from colors.domain.model import MostCommonColors  # noqa: TC002
from pydantic import BaseModel


class ErrorResponse(BaseModel):
    """Error response model."""

    detail: str


class MostCommonColorsResponse(BaseModel):
    """Most common colors response model."""

    message: str
    content: list[MostCommonColors]
