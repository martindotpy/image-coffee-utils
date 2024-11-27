using ImageCoffeeUtilsCrop.Crop.Application.Port.In;
using SixLabors.ImageSharp;
using SixLabors.ImageSharp.Formats;
using SixLabors.ImageSharp.Formats.Jpeg;
using SixLabors.ImageSharp.Formats.Png;
using SixLabors.ImageSharp.Processing;

namespace ImageCoffeeUtilsCrop.Crop.Application.UseCase
{
    /// <summary>
    /// Crop image use case, implements <see cref="ICropImagePort"/>.
    /// </summary>
    /// <param name="logger">Logger</param>
    public class CropImageUseCase(ILogger<CropImageUseCase> logger) : ICropImagePort
    {
        private readonly ILogger<CropImageUseCase> _logger = logger;

        /// <inheritdoc/>
        public byte[] Crop(byte[] byteArray, int x, int y, int? width, int? height)
        {
            using var image = Image.Load(byteArray);

            _logger.LogInformation("Image loaded");

            int cropWidth = Math.Min(width ?? image.Width, image.Width);
            int cropHeight = Math.Min(height ?? image.Height, image.Height);

            var croppedRect = new Rectangle(x, y, cropWidth, cropHeight);
            image.Mutate(x => x.Crop(croppedRect));

            _logger.LogInformation(
                "Image cropped with x={x}, y={y}, width={cropWidth}, height={cropHeight}",
                x,
                y,
                cropWidth,
                cropHeight
            );

            var format = Image.DetectFormat(byteArray);
            IImageEncoder encoder = format switch
            {
                JpegFormat _ => new JpegEncoder(),
                PngFormat _ => new PngEncoder(),
                _ => throw new NotSupportedException("Format not supported"),
            };

            using var memoryStream = new MemoryStream();
            image.Save(memoryStream, encoder);

            _logger.LogInformation("Image saved");

            return memoryStream.ToArray();
        }
    }
}
