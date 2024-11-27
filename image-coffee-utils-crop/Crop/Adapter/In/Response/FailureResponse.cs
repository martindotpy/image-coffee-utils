namespace image_coffee_utils_crop.Crop.Adapter.In.Response
{
    /// <summary>
    /// Failure response.
    /// </summary>
    /// <param name="message">The failure message</param>
    public class FailureResponse(string message)
    {
        /// <summary>
        /// The failure message.
        /// </summary>
        public string Message { get; } = message;
    }
}
