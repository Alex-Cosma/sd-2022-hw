using Hospital.BusinessLogic.Interfaces;
using Hospital.Repository.Interfaces;
using System.Collections.Generic;

namespace Hospital.BusinessLogic.Implementations
{
    public class LocalizationLogic : ILocalizationLogic
    {
        private ILocalizationResourceRepository _localizationResourceRepository;

        public LocalizationLogic(ILocalizationResourceRepository localizationResourceRepository)
        {
            _localizationResourceRepository = localizationResourceRepository;
        }

        public Dictionary<string, string> PrepareForLocalization(string resourceKey)
        {
            return _localizationResourceRepository.GetAllFromDatabaseForResource(resourceKey);
        }
    }
}
