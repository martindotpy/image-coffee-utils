"""Routes package for image-coffee-utils-colors."""

from __future__ import annotations

from typing import TYPE_CHECKING

from colors.adapter.in_.controller import route as colors_route
from colors.application.service import get_most_common_colors_from_image_port
from fastapi import Depends

if TYPE_CHECKING:
    from fastapi import APIRouter

routes: list[APIRouter] = [colors_route]
dependencies: list = [Depends(get_most_common_colors_from_image_port)]

__all__ = ["routes"]
