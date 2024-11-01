from __future__ import annotations

from abc import abstractmethod
from typing import TYPE_CHECKING, Protocol

if TYPE_CHECKING:
    from colors.domain.model import MostCommonColors
    from colors.domain.query import ColorsFailure, Result


class GetMostCommonColorsFromImagePort(Protocol):
    """Interface for getting the most common colors from an image."""

    @abstractmethod
    def get(
        self,
        image_bytes: bytes,
        n: int,
    ) -> Result[list[MostCommonColors], ColorsFailure]:
        """Get the most common colors from an image.

        Args:
            image_bytes (bytes): Image in bytes format
            n (int): Number of colors

        Returns:
            list[MostCommonColors]: List of most common colors

        """
