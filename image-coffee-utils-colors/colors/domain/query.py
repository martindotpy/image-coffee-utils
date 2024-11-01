from __future__ import annotations

from enum import Enum
from typing import TypeVar

from pydantic import BaseModel


class Failure(Enum):
    """Failure base class."""


F = TypeVar("F", bound=Failure)


class Result[T, F](BaseModel):
    """Result of a operation.

    Attributes:
        failure (F | None): Failure object
        success (T | None): Success object

    """

    failure: F | None
    success: T | None

    @classmethod
    def ok(cls, success: T) -> Result[T, F]:
        """Create a success result.

        Args:
            success (T): Success object

        Returns:
            Result[T]: Result object

        """
        return cls(failure=None, success=success)

    @classmethod
    def fail(cls, failure: F) -> Result[T, F]:
        """Create a failure result.

        Args:
            failure (Failure): Failure object

        Returns:
            Result[T]: Result object

        """
        return cls(failure=failure, success=None)

    @property
    def is_failure(self) -> bool:
        """Check if result is a failure.

        Returns:
            bool: True if result is a failure, False otherwise

        """
        return self.failure is not None

    @property
    def is_success(self) -> bool:
        """Check if result is a success.

        Returns:
            bool: True if result is a success, False otherwise

        """
        return self.success is not None

    def get_failure(self) -> F:
        """Get failure object.

        Returns:
            Failure: Failure object

        Raises:
            ValueError: If result is not a failure

        """
        if self.failure is None:
            msg = "Result is not a failure"
            raise ValueError(msg)

        return self.failure

    def get_success(self) -> T:
        """Get success object.

        Returns:
            T: Success object

        Raises:
            ValueError: If result is not a success

        """
        if self.success is None:
            msg = "Result is not a success"
            raise ValueError(msg)

        return self.success


class ColorsFailure(Failure):
    """Colors failure base class."""

    IMAGE_NOT_IN_BASE64 = "Image not in base64 format"
