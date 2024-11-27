namespace image_coffee_utils_crop.Crop.Adapter.In.Utils
{
    /// <summary>
    /// Configuration utilities.
    /// </summary>
    public static class ConfigurationUtils
    {
        /// <summary>
        /// Use the environment variable if it exists, otherwise use the default value.
        /// </summary>
        public static void UseEnvVariableOrDefault(
            string variableName,
            string setting,
            ConfigurationManager configurationManager
        )
        {
            var envVariable = Environment.GetEnvironmentVariable(variableName);
            if (!string.IsNullOrEmpty(envVariable))
            {
                configurationManager.AddInMemoryCollection(
                    initialData: new Dictionary<string, string?> { { setting, envVariable } }
                );
            }
            else
            {
                configurationManager.AddInMemoryCollection(
                    new Dictionary<string, string?> { { setting, configurationManager[setting] } }
                );
            }
        }
    }
}
