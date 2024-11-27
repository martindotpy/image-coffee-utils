using Microsoft.AspNetCore.Mvc;

namespace image_coffee_utils_crop.Crop.Adapter.In.Controller
{
    /// <summary>
    /// Crop controller.
    /// </summary>
    [ApiController]
    [Route("/")]
    public class CropController(ILogger<CropController> logger) : ControllerBase
    {
        private readonly ILogger<CropController> _logger = logger;

        /// <summary>
        /// Crop.
        /// </summary>
        [HttpGet]
        [Produces("application/json")]
        public string Crop()
        {
            _logger.LogInformation("Crop");

            return "Crop";
        }
    }
}