from __future__ import annotations

from io import BytesIO
from typing import override

import numpy as np
from PIL import Image
from sklearn.cluster import KMeans

from colors.domain.model import MostCommonColors
from colors.domain.query import ColorsFailure, Result

from .port.in_ import GetMostCommonColorsFromImagePort


class GetMostCommonColorsFromImageService(GetMostCommonColorsFromImagePort):
    """Service for getting the most common colors from an image."""

    @override
    def get(
        self,
        image_bytes: bytes,
        n: int,
    ) -> Result[list[MostCommonColors], ColorsFailure]:
        """Get the most common colors from an image.

        Args:
            image_bytes (bytes): Image to extract colors from
            n (int): Number of colors

        Returns:
            Result[list[MostCommonColors], ColorsFailure]: The result of the
                operation.

        """
        with Image.open(BytesIO(image_bytes)).convert("RGB") as image:
            ...

        data_image: np.ndarray = np.array(image)
        data_image = data_image.reshape(
            (data_image.shape[0] * data_image.shape[1], 3)
        )

        kmeans_model: KMeans = KMeans(n)
        kmeans_model.fit(data_image)

        common_colors: np.ndarray = kmeans_model.cluster_centers_.astype(int)
        tags: np.ndarray = kmeans_model.labels_

        count_tags: np.ndarray = np.bincount(tags)
        orders: np.ndarray = np.argsort(-count_tags)
        dominant_colors: np.ndarray = common_colors[orders]
        total_count: int = count_tags.sum()

        most_common_colors: list[MostCommonColors] = []

        for i, order in enumerate(orders, start=1):
            color: tuple[int, int, int] = tuple(dominant_colors[order])
            count: int = int(count_tags[order])
            percentage: float = round(count / total_count * 100, 2)

            hexadecimal_color: str = (
                f"#{color[0]:02x}{color[1]:02x}{color[2]:02x}"
            )

            most_common_colors.append(
                MostCommonColors(
                    color=hexadecimal_color,
                    order=i,
                    percentage=percentage,
                )
            )

        return Result.ok(most_common_colors)


service: GetMostCommonColorsFromImagePort = (
    GetMostCommonColorsFromImageService()
)


def get_most_common_colors_from_image_port() -> (
    GetMostCommonColorsFromImagePort
):
    """Return the most common colors from an image port.

    Returns:
        GetMostCommonColorsFromImagePort: Most common colors from an image port

    """
    return service
