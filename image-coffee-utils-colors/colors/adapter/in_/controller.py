from __future__ import annotations

from typing import TYPE_CHECKING, Annotated

from colors.application.service import (
    GetMostCommonColorsFromImagePort,  # noqa: TCH002
    get_most_common_colors_from_image_port,  # noqa: TCH002
)
from fastapi import APIRouter, Depends, File, Query, UploadFile

from .response import ErrorResponse, MostCommonColors, MostCommonColorsResponse

if TYPE_CHECKING:
    from colors.domain.query import ColorsFailure, Result

route: APIRouter = APIRouter()


@route.post(
    "",
    response_model=MostCommonColorsResponse | ErrorResponse,
    responses={
        200: {
            "model": MostCommonColorsResponse,
            "description": "Colors extracted successfully",
        },
        400: {"model": ErrorResponse, "description": "Bad request"},
    },
    tags=["colors"],
)
async def get_colors(  # noqa: D417
    image: Annotated[
        UploadFile, File(..., description="Image to extract colors from")
    ],
    get_most_common_colors_from_image: Annotated[
        GetMostCommonColorsFromImagePort,
        Depends(get_most_common_colors_from_image_port),
    ],
    n: Annotated[int, Query(..., description="Number of colors to get")] = 5,
) -> ErrorResponse | MostCommonColorsResponse:
    """Extract the most common colors from an image.

    Args:

        image (UploadFile): Image to extract colors from
        n (int): Number of colors

    Returns:

        ErrorResponse | MostCommonColorsResponse: Response object

    """  # noqa: D412
    if not image:
        return ErrorResponse(detail="Image not received")

    image_contents = await image.read()
    result: Result[list[MostCommonColors], ColorsFailure] = (
        get_most_common_colors_from_image.get(image_contents, n)
    )

    if result.is_failure:
        return ErrorResponse(detail=result.get_failure().value)

    return MostCommonColorsResponse(
        message="Colors extracted successfully",
        content=result.get_success(),
    )