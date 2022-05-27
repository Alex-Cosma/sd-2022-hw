using System;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Localization;

namespace Hospital.Localizer
{
    public class SqlStringLocalizerFactory: IStringLocalizerFactory
    {
        private readonly IServiceProvider _serviceProvider;

        public SqlStringLocalizerFactory(IServiceProvider serviceProvider)
        {
            _serviceProvider = serviceProvider;
        }

        public IStringLocalizer Create(Type resourceSource)
        {
            return CreateStringLocalizer();
        }

        public IStringLocalizer Create(string baseName, string location)
        {
            return CreateStringLocalizer();
        }

        #region Private

        private IStringLocalizer CreateStringLocalizer()
        {
            var obj = _serviceProvider.GetRequiredService(typeof(IStringLocalizer));

            return (IStringLocalizer)obj;
        }

        #endregion
    }
}
