"""Routes package for image-coffee-utils-colors."""

from __future__ import annotations

from typing import TYPE_CHECKING

if TYPE_CHECKING:
    from fastapi import APIRouter

routes: list[APIRouter] = []

__all__ = ["routes"]
