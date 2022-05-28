using System;
using System.Collections.Generic;
using System.Globalization;
using System.Linq;
using Hospital.BusinessLogic.Interfaces;
using Hospital.Data;
using Microsoft.AspNetCore.Http;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Localization;

namespace Hospital.Localizer
{
    public class SqlStringLocalizer : IStringLocalizer
    {
        private readonly IServiceProvider _serviceProvider;

        public SqlStringLocalizer(IServiceProvider serviceProvider)
        { 
            _serviceProvider = serviceProvider;
        }

        public LocalizedString this[string name]
        {
            get
            {
                string translatedValue = GetText(name);
               
                if (translatedValue == null)
                {
                    return new LocalizedString(name, name);
                
                }
                return new LocalizedString(name, GetText(name));
            }
        }

        public LocalizedString this[string name, params object[] arguments]
        {
            get
            {
                string translatedValue = GetText(name);
                if (translatedValue == null)
                {
                    return new LocalizedString(name, name);
                }

                return new LocalizedString(name, GetText(name));
            }
        }

        public IEnumerable<LocalizedString> GetAllStrings(bool includeParentCultures)
        {
            using (var scope = _serviceProvider.CreateScope())
            {
                var db = scope.ServiceProvider.GetRequiredService<ApplicationDbContext>();
                var localizationLogic = scope.ServiceProvider.GetRequiredService<ILocalizationLogic>();
                var culture = GetCulture();

                return localizationLogic.PrepareForLocalization("HomeController").Select(r => new LocalizedString(r.Key, r.Value));
            }
        }

        public IStringLocalizer WithCulture(CultureInfo culture)
        {
            CultureInfo.DefaultThreadCurrentCulture = culture;
            
            return _serviceProvider.GetRequiredService<IStringLocalizer>();
        }

        #region Private

        private string GetText(string composedKey)
        {
            var culture = GetCulture();

            string[] words = composedKey.Split('.');

            using (var scope = _serviceProvider.CreateScope())
            {
                var db = scope.ServiceProvider.GetRequiredService<ApplicationDbContext>();
                var localizationLogic = scope.ServiceProvider.GetRequiredService<ILocalizationLogic>();

                return localizationLogic.PrepareForLocalization(composedKey).Where(element => element.Key.Contains(culture)).FirstOrDefault().Value;
            }
        }

        private string GetCulture()
        {
            var context = _serviceProvider.GetService<IHttpContextAccessor>().HttpContext;
            var culture = context.Items.TryGetValue(Constants.Constants.AspNetCoreCulture, out var cultureNameFromContext);

            if (string.IsNullOrWhiteSpace((string)cultureNameFromContext))
            {
                return CultureInfo.CurrentCulture.Name;
            }

            return (string)cultureNameFromContext;
        }

        #endregion
    }
}
