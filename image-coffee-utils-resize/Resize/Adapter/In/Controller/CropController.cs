using System.ComponentModel;
using System.ComponentModel.DataAnnotations;
using ImageCoffeeUtilsCrop.Crop.Adapter.In.Response;
using ImageCoffeeUtilsCrop.Crop.Application.Port.In;
using Microsoft.AspNetCore.Mvc;

namespace ImageCoffeeUtilsCrop.Crop.Adapter.In.Controller
{
    /// <summary>
    /// Crop controller.
    /// </summary>
    [ApiController]
    [Route("/")]
    public class CropController(ILogger<CropController> logger, ICropImagePort cropImagePort)
        : ControllerBase
    {
        private readonly ILogger<CropController> _logger = logger;
        private readonly ICropImagePort _cropImagePort = cropImagePort;

        /// <summary>
        /// Crop an image and use x, y, width and height to define the crop area.
        /// </summary>
        /// <param name="file">The image file to crop</param>
        /// <param name="x">The x coordinate of the top-left corner of the crop area (min 0)</param>
        /// <param name="y">The y coordinate of the top-left corner of the crop area (min 0)</param>
        /// <param name="width">The width of the crop area, if null or greater than, the image width will be used (min 1)</param>
        /// <param name="height">The height of the crop area, if null or greater than, the image height will be used (min 1)</param>
        /// <returns>The cropped image</returns>
        /// <response code="200">The cropped image</response>
        /// <response code="400">No file provided or invalid crop area</response>
        /// <response code="500">Error processing image</response>
        [HttpPost]
        [ProducesResponseType(
            typeof(FileStreamResult),
            StatusCodes.Status200OK,
            "image/jpeg",
            "image/png",
            "image/jpg"
        )]
        [ProducesResponseType(
            typeof(ValidationProblemDetails),
            StatusCodes.Status400BadRequest,
            "application/json"
        )]
        [ProducesResponseType(
            typeof(FailureResponse),
            StatusCodes.Status500InternalServerError,
            "application/json"
        )]
        [Consumes("multipart/form-data")]
        public async Task<IActionResult> Crop(
            [Required] IFormFile file,
            [FromForm, Range(0, int.MaxValue), DefaultValue(0), Required] int x,
            [FromForm, Range(0, int.MaxValue), DefaultValue(0), Required] int y,
            [FromForm, Range(1, int.MaxValue), DefaultValue(1)] int width,
            [FromForm, Range(1, int.MaxValue), DefaultValue(1)] int height
        )
        {
            try
            {
                _logger.LogInformation("Cropping image");

                using var memoryStream = new MemoryStream();
                await file.CopyToAsync(memoryStream);
                var imageData = memoryStream.ToArray();

                var croppedImage = _cropImagePort.Crop(imageData, x, y, width, height);

                _logger.LogInformation("Sending cropped image");

                return File(croppedImage, file.ContentType, "cropped_" + file.FileName);
            }
            catch (Exception e)
            {
                _logger.LogError(e, "Error processing image");

                return StatusCode(500, new FailureResponse($"Error processing image: {e.Message}"));
            }
        }
    }
}
