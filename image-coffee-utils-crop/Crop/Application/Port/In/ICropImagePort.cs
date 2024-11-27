namespace image_coffee_utils_crop.Crop.Application.Port.In
{
    /// <summary>
    /// Crop image port.
    /// </summary>
    public interface ICropImagePort
    {
        /// <summary>
        /// Crop image from byte array.
        /// </summary>
        /// <param name="byteArray">The byte array of the image</param>
        /// <param name="x">The x coordinate of the top left corner of the rectangle to crop</param>
        /// <param name="y">The y coordinate of the top left corner of the rectangle to crop</param>
        /// <param name="width">The width of the rectangle to crop</param>
        /// <param name="height">The height of the rectangle to crop</param>
        /// <returns>The byte array of the image cropped.</returns>
        byte[] Crop(byte[] byteArray, int x, int y, int? width, int? height);
    }
}
