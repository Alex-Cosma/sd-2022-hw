using System.Collections.Generic;

namespace Hospital.BusinessLogic.Interfaces
{
    public interface ILocalizationLogic
    {
        Dictionary<string, string> PrepareForLocalization(string composedKey);
    }
}
